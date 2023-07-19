package com.narola.finalproject.servlet.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narola.finalproject.constant.AppConstant;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.*;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.service.OrderService;
import com.narola.finalproject.utility.Utility;
import com.razorpay.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PostPaymentServlet extends HttpServlet {
    private OrderService orderService = new OrderService();
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/customerHomePage");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            getCuisines(request, response);
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");
//            int orderId = (Integer) httpSession.getAttribute("userOrderId");
            int orderId = orderService.getOrderId(user.getUserId());
            String razorPayOrderId = orderService.getRazorPayOrderId(user.getUserId(), orderId);

            RazorpayPaymentDetails razorpayPaymentDetails = new RazorpayPaymentDetails();
            razorpayPaymentDetails.setRazorPayOrderId(razorPayOrderId);
            razorpayPaymentDetails.setOrderId(orderId);

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestBody = sb.toString();
//            System.out.println(requestBody);

            Map<String, String> razorPayDetails = Utility.decodeUrlToMap(requestBody);
            for (Map.Entry<String, String> entry : razorPayDetails.entrySet()) {
                System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }
            String razorpay_payment_id = razorPayDetails.get("razorpay_payment_id");
            String razorpay_signature = razorPayDetails.get("razorpay_signature");
            String razorpay_order_id = razorPayDetails.get("razorpay_order_id");

            if (isSuccess(razorpay_payment_id, razorpay_order_id, razorpay_signature)) {
                if (verifySignature(razorpay_signature, razorpay_payment_id, razorPayOrderId)) {
                    request.setAttribute("orderStatus", "Order placed successfully.");
                } else {
                    request.setAttribute("orderStatus", "Order placed successfully, signatures not verified.");
                }

                razorpayPaymentDetails = getPaymentDetails(razorpay_payment_id, razorpayPaymentDetails);
                razorpayPaymentDetails.setPaymentSignature(razorpay_signature);
                razorpayPaymentDetails.setOrderStatusId(AppConstant.ORDER_STATUS_NEW);

            } else {

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonObject = razorPayDetails.get("error[metadata]");
                RazorPayError razorPayError = objectMapper.readValue(jsonObject, RazorPayError.class);

                String paymentId = razorPayError.getPayment_id();

                razorpayPaymentDetails = getPaymentDetails(paymentId, razorpayPaymentDetails);
                razorpayPaymentDetails.setOrderStatusId(AppConstant.ORDER_STATUS_PENDING);

            }
            orderService.updateOrderPaymentDetails(razorpayPaymentDetails);
            request.setAttribute("razorpayPaymentDetails", razorpayPaymentDetails);
            getServletContext().getRequestDispatcher("/customer/successPayment.jsp").forward(request, response);

        } catch (DAOLayerException | RazorpayException e) {
            e.printStackTrace();
        }
    }

    private boolean isSuccess(String paymentId, String razorpay_order_id, String signature) {
        return (paymentId != null && razorpay_order_id != null && signature != null);
    }

    public boolean verifySignature(String signature, String paymentId, String razorPayOrderId) throws RazorpayException {

        String secret = getServletContext().getInitParameter("key_secret");

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", razorPayOrderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        return Utils.verifyPaymentSignature(options, secret);
    }

    private void getCuisines(HttpServletRequest request, HttpServletResponse response) throws DAOLayerException, ServletException, IOException {
        List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
        if (!cuisineCategoryList.isEmpty() && cuisineCategoryList != null) {
            request.setAttribute("cuisineCategoryList", cuisineCategoryList);
        } else {
            request.setAttribute("error", "No data found.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
        }
    }

    private RazorpayPaymentDetails getPaymentDetails(String paymentId, RazorpayPaymentDetails razorpayPaymentDetails) throws RazorpayException, JsonProcessingException {
        RazorpayClient razorpay = new RazorpayClient(getServletContext().getInitParameter("key_id"), getServletContext().getInitParameter("key_secret"));
        Payment payment = razorpay.Payments.fetch(paymentId);

        Integer amount = payment.get("amount");
        amount /= 100;
        String currency = payment.get("currency");
        String status = payment.get("status");
        String contact = payment.get("contact");
        String email = payment.get("email");
        String paymentMethod = payment.get("method");

        razorpayPaymentDetails.setPaymentId(paymentId);
        razorpayPaymentDetails.setAmount(amount);
        razorpayPaymentDetails.setCurrency(currency);
        razorpayPaymentDetails.setStatus(status);
        razorpayPaymentDetails.setMobileNo(contact);
        razorpayPaymentDetails.setEmail(email);
        razorpayPaymentDetails.setPaymentMethod(paymentMethod);

        if (status.equals("failed")) {
            String source = payment.get("error_source");
            String details = payment.get("error_reason") + "|" + payment.get("error_description");
            razorpayPaymentDetails.setErrorSource(source);
            razorpayPaymentDetails.setErrorDetails(details);
        }
//        else {
        String value = null;
        if (paymentMethod.equals("card")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String razorCardDetails = payment.get("card").toString();
            CardDetails cardDetails = objectMapper.readValue(razorCardDetails, CardDetails.class);
            value = cardDetails.getType() + "|" + cardDetails.getNetwork() + "|" + cardDetails.getLast4();
        } else if (paymentMethod.equals("upi")) {
            value = payment.get("vpa");
        } else if (paymentMethod.equals("netbanking")) {
            value = payment.get("bank");
        }
        razorpayPaymentDetails.setPaymentDetails(value);
//        }
        return razorpayPaymentDetails;
    }
}
