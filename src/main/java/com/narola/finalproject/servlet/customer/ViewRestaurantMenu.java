package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.*;
import com.narola.finalproject.service.CartService;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.service.RestaurantMenuService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ViewRestaurantMenu extends HttpServlet {

    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String restaurantId = request.getParameter("id");
            if (restaurantId == null) {
                request.setAttribute("error", "Try to add later");
                getServletContext().getRequestDispatcher("/customer/viewMenu&addToCart.jsp").include(request, response);
            } else {

                List<RestaurantMenu> restaurantMenuList = restaurantMenuService.getRestaurantMenu(Integer.parseInt(restaurantId));
                request.setAttribute("restaurantMenuList", restaurantMenuList);
                request.setAttribute("restaurantId", restaurantId);

                getServletContext().getRequestDispatcher("/customer/viewMenu&addToCart.jsp").forward(request, response);
            }
        } catch (DAOLayerException e) {
            request.setAttribute("error", "Error occurred while adding item to cart.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getCuisineList(request, response);
            getRestaurantMenuList(request);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    private void getCuisineList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOLayerException {
        List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
        if (!cuisineCategoryList.isEmpty()) {
            request.setAttribute("cuisineCategoryList", cuisineCategoryList);
        } else {
            request.setAttribute("cuisineError", "Error occurred");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    private void getRestaurantMenuList(HttpServletRequest request) throws DAOLayerException {
        List<RestaurantMenu> restaurantMenuList = restaurantMenuService.getAllMenuItems();
        request.setAttribute("restaurantMenuList", restaurantMenuList);
    }
}
