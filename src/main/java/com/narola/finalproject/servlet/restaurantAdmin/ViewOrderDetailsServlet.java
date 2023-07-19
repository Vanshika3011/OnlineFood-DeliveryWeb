package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Order;
import com.narola.finalproject.service.OrderService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ViewOrderDetailsServlet extends HttpServlet {
    private OrderService orderService = new OrderService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String orderId= request.getParameter("id");
            if(orderId == null || orderId.isEmpty()){
                request.setAttribute("error", "No such order id found.");
                getServletContext().getRequestDispatcher("/restaurantAdmin/viewOrders.jsp").forward(request,response);
            }else{
            Order order = orderService.getOrdersDetails(Integer.parseInt(orderId));
            request.setAttribute("order",order);
            getServletContext().getRequestDispatcher("/restaurantAdmin/viewOrderDetails.jsp").forward(request,response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
