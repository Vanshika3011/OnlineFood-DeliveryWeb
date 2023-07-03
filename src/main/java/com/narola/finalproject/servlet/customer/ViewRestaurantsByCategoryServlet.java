package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.service.RestaurantMenuService;
import com.narola.finalproject.service.RestaurantService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ViewRestaurantsByCategoryServlet extends HttpServlet {
    RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String cuisineId = request.getParameter("cuisineId");
            if (cuisineId == null) {
                request.setAttribute("error", "Error occurred.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            } else {
                List<Restaurant> restaurantList = restaurantService.getRestaurantsByCategory(Integer.parseInt(cuisineId));
                request.setAttribute("restaurantList", restaurantList);
                getServletContext().getRequestDispatcher("/customer/ViewDesiredMenu.jsp").forward(request, response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
