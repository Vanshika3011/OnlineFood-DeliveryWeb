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

public class MenuPageServlet extends HttpServlet {
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getCuisines(request, response);
            List<RestaurantMenu> menuList = restaurantMenuService.getAllMenuItems();
            if (!menuList.isEmpty()) {
                String cuisineId = request.getParameter("cuisineId");
                String sort = request.getParameter("sort");
                if (cuisineId == null) {
                    request.setAttribute("restaurantMenuList", menuList);
                    getServletContext().getRequestDispatcher("/customer/viewMenu&addToCart.jsp").forward(request, response);
                } else{
                List<RestaurantMenu> foodItems = restaurantMenuService.getItemsByCuisine(menuList,Integer.valueOf(cuisineId));
                List<RestaurantMenu> restaurantMenuList = restaurantMenuService.getMenuListSorted(foodItems, sort);
                request.setAttribute("restaurantMenuList", restaurantMenuList);
                getServletContext().getRequestDispatcher("/customer/viewMenu&addToCart.jsp").forward(request, response);}
            }

        } catch (DAOLayerException e) {
            request.setAttribute("error", "Error Displaying Menu Items.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
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
