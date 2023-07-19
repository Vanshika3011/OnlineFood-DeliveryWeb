package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.dao.OrderPaymentDetails;
import com.narola.finalproject.service.OrderService;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class PaymentServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String orderId = request.getParameter("orderId");
            if(orderId == null || orderId.isEmpty()){
                request.setAttribute("orderError","Something went wrong. Please place order again.");
                getServletContext().getRequestDispatcher("/customer/viewAddress.jsp").forward(request,response);
            }else{
                OrderPaymentDetails orderPaymentDetails = orderService.getOrderDetails(Integer.parseInt(orderId));
                request.setAttribute("orderPaymentDetails", orderPaymentDetails);
                getServletContext().getRequestDispatcher("/customer/payment.jsp").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }
}
