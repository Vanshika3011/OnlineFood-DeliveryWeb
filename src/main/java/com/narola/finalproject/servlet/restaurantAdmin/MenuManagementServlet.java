package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.model.RestaurantMenu;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.service.RestaurantMenuService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class MenuManagementServlet extends HttpServlet {
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String cuisine = request.getParameter("cuisine");
            String availability = request.getParameter("availability");
            String isVeg = request.getParameter("type");
            String sortOption = request.getParameter("sort");
            if ( availability == null || availability.isEmpty()) {
                availability = "all";
            }
            if( isVeg == null || isVeg.isEmpty()){
                isVeg = "all";
            }
            int cuisineId;
            if (cuisine == null || cuisine.isEmpty()) {
                cuisineId = 0;
            } else {
                cuisineId = Integer.parseInt(cuisine);
            }

            getCuisines(request);
            HttpSession httpSession = request.getSession();
            Restaurant restaurant = (Restaurant) httpSession.getAttribute("restaurant1");
            List<RestaurantMenu> restaurantMenuList = restaurantMenuService.getRestaurantMenu(restaurant.getRestaurantId());
            List<RestaurantMenu> listByCuisineType = restaurantMenuService.getItemsByCategoryId(restaurantMenuList, cuisineId);
            List<RestaurantMenu> listByAvailability = restaurantMenuService.getItemsByAvailability(listByCuisineType, availability);
            List<RestaurantMenu> listByVegCategory = restaurantMenuService.getItemsByVegType(listByAvailability, isVeg );
            List<RestaurantMenu> sortedList = restaurantMenuService.getMenuListSorted(listByVegCategory, sortOption);
            request.setAttribute("restaurantMenuList", sortedList);

            getServletContext().getRequestDispatcher("/restaurantAdmin/menuManager.jsp").forward(request, response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void getCuisines(HttpServletRequest request) throws DAOLayerException {
        List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
        request.setAttribute("cuisineCategoryList", cuisineCategoryList);
    }
}
