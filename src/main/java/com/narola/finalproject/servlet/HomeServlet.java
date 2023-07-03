package com.narola.finalproject.servlet;

import com.narola.finalproject.enums.UserRolesEnum;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.RestaurantService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeServlet extends HttpServlet {
    RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");
            request.setAttribute("user", user);
            if (user.getRoleId() == UserRolesEnum.ADMIN.getRoleIdValue()) {
                response.sendRedirect("/adminHomePage");
            } else if (user.getRoleId() == UserRolesEnum.RESTAURANTADMIN.getRoleIdValue()) {
                if (restaurantService.doesUserRestaurantExists(user.getUserId())) {
                    response.sendRedirect("/manageRestaurant");
                } else {
                    response.sendRedirect("/addRestaurant");
                }
            } else if (user.getRoleId() == UserRolesEnum.CUSTOMER.getRoleIdValue()) {
                response.sendRedirect("/customerHomePage");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

    }
}
