package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.RestaurantMenu;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.service.RestaurantMenuService;
import com.narola.finalproject.validation.MenuValidation;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class EditMenuItemServlet extends HttpServlet {
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("id"));
        try {
            RestaurantMenu restaurantMenu = restaurantMenuService.getMenuItemDetails(itemId);
            request.setAttribute("restaurantMenu", restaurantMenu);

            List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
            if (!cuisineCategoryList.isEmpty() && cuisineCategoryList != null) {
                request.setAttribute("cuisineCategoryList", cuisineCategoryList);
            } else {
                request.setAttribute("error", "No data found.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            }
            getServletContext().getRequestDispatcher("/restaurantAdmin/editMenuItem.jsp").forward(request, response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RestaurantMenu restaurantMenu = new RestaurantMenu();

            String itemId = request.getParameter("itemId");
            if (itemId.isEmpty()) {
                request.setAttribute("error", "Item Id can't be empty.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            } else {
                String itemName = request.getParameter("itemName");
                String priceValue = request.getParameter("price");
                String ingredients = request.getParameter("ingredients");
                String categoryId = request.getParameter("category");
                String foodType = (request.getParameter("foodType"));
                String isAvailable = (request.getParameter("availability"));

                List<Error> errorList = MenuValidation.validateMenuItems(itemName, priceValue, ingredients, categoryId, foodType);
                if (!errorList.isEmpty()) {
                    getErrors(errorList, request);
                    doGet(request, response);
                    getServletContext().getRequestDispatcher("/restaurantAdmin/editMenuItem.jsp").forward(request, response);
                } else {
                    restaurantMenu.setItemId(Integer.parseInt(itemId));
                    restaurantMenu.setItemName(itemName);
                    restaurantMenu.setPrice(Double.valueOf(priceValue));
                    restaurantMenu.setIngredients(ingredients);
                    restaurantMenu.setCategoryId(Integer.parseInt(categoryId));
                    restaurantMenu.setVeg(Boolean.parseBoolean(foodType));
                    restaurantMenu.setAvailability(Boolean.parseBoolean(isAvailable));

                    restaurantMenuService.editMenuItems(restaurantMenu);
                    response.sendRedirect("/menuManager");
                }
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    private void getErrors(List<Error> errorList, HttpServletRequest request) {
        for (Error error : errorList) {
            request.setAttribute(error.getFieldError(), error.getMessage());
        }
    }
}
