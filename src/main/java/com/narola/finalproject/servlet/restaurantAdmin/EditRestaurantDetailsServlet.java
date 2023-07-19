package com.narola.finalproject.servlet.restaurantAdmin;

import com.narola.finalproject.enums.UserRolesEnum;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.Restaurant;
import com.narola.finalproject.model.RestaurantAddress;
import com.narola.finalproject.model.User;
import com.narola.finalproject.service.RestaurantService;
import com.narola.finalproject.utility.Utility;
import com.narola.finalproject.validation.RestaurantValidation;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)

public class EditRestaurantDetailsServlet extends HttpServlet {
    private RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String restaurantId = request.getParameter("id");
            if (restaurantId == null) {
                getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            } else {
                Restaurant restaurant = restaurantService.getRestaurantDetailsByRestaurantId(Integer.parseInt(restaurantId));
                request.setAttribute("restaurant", restaurant);
                getServletContext().getRequestDispatcher("/restaurantAdmin/editRestaurantDetail.jsp").forward(request, response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Restaurant restaurant = new Restaurant();
            RestaurantAddress restaurantAddress = new RestaurantAddress();
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");

            String restId = request.getParameter("restaurantId");
            int restaurantId = 0;
            if (restId == null) {
                getServletContext().getRequestDispatcher("/restaurantAdmin/menuManager.jsp").forward(request, response);
            } else {
                restaurantId = Integer.parseInt(restId);
            }

            String getPincode = request.getParameter("pincode");
            int pincode = 0;
            if (getPincode != null && !getPincode.trim().isEmpty()) {
                pincode = Integer.parseInt(getPincode);
            }

            Part filePart = request.getPart("image");

            restaurant.setRestaurantName(request.getParameter("restaurantName"));
            restaurant.setRestaurantContact(request.getParameter("contact"));
            restaurant.setRestaurantEmail(request.getParameter("email"));
            restaurant.setOpensAt(request.getParameter("openingTime"));
            restaurant.setClosesAt(request.getParameter("closingTime"));
            if (user.getRoleId() == UserRolesEnum.ADMIN.getRoleIdValue()) {
                restaurant.setRestaurantOwnerId(restaurantService.getOwnerId(restaurantId));
            } else {
                restaurant.setRestaurantOwnerId(user.getUserId());
            }
            restaurantAddress.setRestaurantAddLine1(request.getParameter("addressLine1"));
            restaurantAddress.setRestaurantAddLine2(request.getParameter("addressLine2"));
            restaurantAddress.setPincode(pincode);

            restaurant.setRestaurantAddress(restaurantAddress);

            List<Error> errorList = RestaurantValidation.validateRestaurantDetailsToEdit(restaurant);
            if (!errorList.isEmpty()) {
                Restaurant restaurant1 = restaurantService.getRestaurantDetailsByRestaurantId(restaurantId);
                request.setAttribute("restaurant", restaurant1);
                getErrors(errorList, request);
                getServletContext().getRequestDispatcher("/restaurantAdmin/editRestaurantDetail.jsp").include(request, response);
            } else {
                String filePath;
                if (filePart == null || filePart.getSize() == 0) {
                    filePath = request.getParameter("imagePath");
                    restaurant.setRestaurantImageUrl(filePath);
                } else {
                    String fileName = Utility.getSubmittedFileName(filePart);
                    String staticFolderPath = getServletContext().getRealPath(getServletContext().getInitParameter("imageFolderPath"));
                    filePath = staticFolderPath + File.separator + fileName;
                    Utility.putImageToDirectory(filePath, filePart);
                    String targetFilePath = getServletContext().getInitParameter("imageFolderPath") + File.separator + fileName;
                    restaurant.setRestaurantImageUrl(targetFilePath);
                }
                restaurantService.editRestaurantDetails(restaurant, restaurantId);
                if (user.getRoleId() == UserRolesEnum.ADMIN.getRoleIdValue()) {
                    response.sendRedirect("/restaurantManagement");
                }else {
                    response.sendRedirect("/manageRestaurant");
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
