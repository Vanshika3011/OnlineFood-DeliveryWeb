package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.model.RestaurantMenu;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.service.RestaurantMenuService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ViewMenuItemsServlet extends HttpServlet {
    RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String cuisine = request.getParameter("cuisineId");
            if(cuisine == null){
                getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            }else {
                int cuisineId = Integer.parseInt(cuisine);
                getCuisines(request, response);
                List<RestaurantMenu> restaurantMenuList = restaurantMenuService.getAllMenuItems();
                List<RestaurantMenu> desiredMenuList = restaurantMenuService.getItemsByCuisine(restaurantMenuList, cuisineId);
                request.setAttribute("restaurantMenu", desiredMenuList);
                getServletContext().getRequestDispatcher("/customer/customerHomePage.jsp").include(request,response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void getCuisines(HttpServletRequest request, HttpServletResponse response) throws DAOLayerException, ServletException, IOException {
        List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
        if (!cuisineCategoryList.isEmpty() && cuisineCategoryList != null) {
            request.setAttribute("cuisineCategoryList", cuisineCategoryList);
        } else {
            request.setAttribute("error", "No data found.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
        }
    }
}
