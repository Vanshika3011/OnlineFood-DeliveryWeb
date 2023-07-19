package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CartDetails;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.CartService;
import com.narola.finalproject.service.OrderService;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class OrderServlet extends HttpServlet {
    private OrderService orderService = new OrderService();
    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");
            String addressId = request.getParameter("id");

            if (addressId == null || addressId.isEmpty()) {
                request.setAttribute("error", "Select valid Address to place order.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            } else {
                CartDetails cartDetails = cartService.getUserCartDetailsWithTotal(user.getUserId());
                int orderId = orderService.placeOrder(user.getUserId(), cartDetails, Integer.parseInt(addressId));
                cartService.deleteCartDetails(user.getUserId());
                httpSession.setAttribute("userOrderId",orderId);
                RazorpayClient razorpay = new RazorpayClient(getServletContext().getInitParameter("key_id"), getServletContext().getInitParameter("key_secret"));

                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", (cartDetails.getTotalPrice() * 100)); // amount in the smallest currency unit
                orderRequest.put("currency", "INR");

                Order order = razorpay.Orders.create(orderRequest);
                String razorPayOrderId = order.get("id");

                orderService.addOrderId(razorPayOrderId, orderId);

                response.sendRedirect("/doPayment?orderId="+ orderId);
            }
        } catch (DAOLayerException | RazorpayException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
