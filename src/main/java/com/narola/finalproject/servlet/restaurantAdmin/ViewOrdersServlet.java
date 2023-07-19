package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Order;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.service.OrderService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ViewOrdersServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            Restaurant restaurant = (Restaurant) httpSession.getAttribute("restaurant1");

            List<Order> orderList = orderService.getAllRestaurantOrder(restaurant.getRestaurantId());
            request.setAttribute("orderList", orderList);
            getServletContext().getRequestDispatcher("/restaurantAdmin/viewOrders.jsp").forward(request,response);

        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
