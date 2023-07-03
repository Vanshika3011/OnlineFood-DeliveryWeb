package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.model.RestaurantMenu;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.service.RestaurantMenuService;
import com.narola.finalproject.service.SortSpecification.RestaurantMenuSortSpecification;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortMenuItemsServlet  extends HttpServlet {
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    private CuisineCategoryService cuisineCategoryService= new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            Restaurant restaurant = (Restaurant) httpSession.getAttribute("restaurant1");

            List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
            request.setAttribute("cuisineCategoryList",cuisineCategoryList);

            int restId = restaurant.getRestaurantId();
            List<RestaurantMenu> restaurantMenuList = restaurantMenuService.getRestaurantMenu(restId);
            String sortOption = request.getParameter("sort");
            if (sortOption.equals("nameAsc")) {
                Collections.sort(restaurantMenuList);
                request.setAttribute("restaurantMenuList", restaurantMenuList);
            } else if (sortOption.equals("nameDesc")) {
                Collections.sort(restaurantMenuList, Comparator.reverseOrder());
                request.setAttribute("restaurantMenuList", restaurantMenuList);
            } else if (sortOption.equals("priceLow")) {
                Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByPriceAsc());
                request.setAttribute("restaurantMenuList", restaurantMenuList);
            } else if (sortOption.equals("priceHigh")) {
                Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByPriceDesc());
                request.setAttribute("restaurantMenuList", restaurantMenuList);
            } else if (sortOption.equals("latest")) {
                Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByLatestAdded());
                request.setAttribute("restaurantMenuList", restaurantMenuList);
            } else if (sortOption.equals("isVeg")) {
                Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByFoodType());
                request.setAttribute("restaurantMenuList", restaurantMenuList);
            }else {
                request.setAttribute("restaurantMenuList", restaurantMenuList);
            }
             getServletContext().getRequestDispatcher("/restaurantAdmin/menuManager.jsp").include(request, response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

    }
}
