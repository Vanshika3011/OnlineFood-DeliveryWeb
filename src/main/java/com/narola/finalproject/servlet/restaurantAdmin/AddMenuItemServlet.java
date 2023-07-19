package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.model.RestaurantMenu;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.service.RestaurantMenuService;
import com.narola.finalproject.utility.Utility;
import com.narola.finalproject.validation.MenuValidation;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)

public class AddMenuItemServlet extends HttpServlet {
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
            if (!cuisineCategoryList.isEmpty() && cuisineCategoryList != null) {
                request.setAttribute("cuisineCategoryList", cuisineCategoryList);
                getServletContext().getRequestDispatcher("/restaurantAdmin/addMenuItem.jsp").include(request, response);
            } else {
                request.setAttribute("error", "No data found.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RestaurantMenu restaurantMenu = new RestaurantMenu();

        String itemName = request.getParameter("itemName");
        String priceValue = request.getParameter("price");
        String ingredients = request.getParameter("ingredients");
        String categoryId = request.getParameter("category");
        String foodType = request.getParameter("foodType");
        Part filePart = request.getPart("image");

        List<Error> errorList = MenuValidation.validateMenuItems(itemName, priceValue, ingredients, categoryId, foodType);
        if (!errorList.isEmpty()) {
            getErrors(errorList, request);
            doGet(request, response);
            getServletContext().getRequestDispatcher("/restaurantAdmin/addMenuItem.jsp").forward(request, response);
        } else {
            try {
                HttpSession httpSession = request.getSession();
                User user = (User) httpSession.getAttribute("user");
                int ownerId = user.getUserId();
                Restaurant restaurant = (Restaurant) httpSession.getAttribute("restaurant1");

                restaurantMenu.setItemName(itemName);
                restaurantMenu.setIngredients(ingredients);
                restaurantMenu.setRestaurantId(restaurant.getRestaurantId());
                restaurantMenu.setPrice(Double.parseDouble(priceValue));
                restaurantMenu.setCategoryId(Integer.parseInt(categoryId));
                restaurantMenu.setVeg(Boolean.parseBoolean(foodType));

                String fileName = Utility.getSubmittedFileName(filePart);
                String staticFolderPath = getServletContext().getRealPath(getServletContext().getInitParameter("imageFolderPath"));
                String filePath = staticFolderPath + File.separator + fileName;
                Utility.putImageToDirectory(filePath, filePart);
                String targetFilePath =getServletContext().getInitParameter("imageFolderPath")+ File.separator + fileName;

                restaurantMenuService.addMenuItems(restaurantMenu, ownerId, targetFilePath);
                response.sendRedirect("/menuManager");
            } catch (DAOLayerException e) {
                request.setAttribute("error", "Error while adding MenuItem");
                getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
                e.printStackTrace();
            }
        }
    }

    private void getErrors(List<Error> errorList, HttpServletRequest request) {
        for (Error error : errorList) {
            request.setAttribute(error.getFieldError(), error.getMessage());
        }
    }
}
