package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.service.RestaurantService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ViewRestaurantDetailsServlet extends HttpServlet {
    private RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String restaurantId = request.getParameter("id");
            if (restaurantId == null) {
                request.setAttribute("error","Error displaying Restaurant details");
                getServletContext().getRequestDispatcher("/errorPage.jsp").include(request,response);
            } else {
                Restaurant restaurant = restaurantService.getRestaurantDetailsByRestaurantId(Integer.parseInt(restaurantId));
                request.setAttribute("restaurant", restaurant);
                getServletContext().getRequestDispatcher("/admin/viewRestaurant.jsp").forward(request,response);
            }
        } catch (DAOLayerException e) {
            request.setAttribute("error", "No data found.");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
