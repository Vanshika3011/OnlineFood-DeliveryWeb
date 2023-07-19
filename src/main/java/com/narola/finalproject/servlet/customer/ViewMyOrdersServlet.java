package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Order;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.OrderService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ViewMyOrdersServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");

            List<Order> userOrderList = orderService.getAllUserOrders(user.getUserId());
            request.setAttribute("userOrderList", userOrderList);
            getServletContext().getRequestDispatcher("/customer/orderPage.jsp").forward(request,response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
