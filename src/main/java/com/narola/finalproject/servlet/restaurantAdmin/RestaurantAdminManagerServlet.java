package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.RestaurantService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class RestaurantAdminManagerServlet extends HttpServlet {
    private RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");
            Restaurant restaurant = restaurantService.getRestaurantDetails(user.getUserId());
            request.setAttribute("restaurant", restaurant);
            httpSession.setAttribute("restaurant1", restaurant);
        } catch (DAOLayerException e) {
            request.setAttribute("error", "No data found.");
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/restaurantAdmin/restaurantAdminManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
