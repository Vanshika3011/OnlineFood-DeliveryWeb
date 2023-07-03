package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.service.RestaurantService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class RestaurantManagementServlet extends HttpServlet {
    private RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Restaurant> restaurantList = restaurantService.getRestaurantList();
            if (restaurantList != null && !restaurantList.isEmpty()) {
                request.setAttribute("restaurantList", restaurantList);
                getServletContext().getRequestDispatcher("/admin/restaurantManagement.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No restaurants Found");
                getServletContext().getRequestDispatcher("/admin/restaurantManagement.jsp").include(request, response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
