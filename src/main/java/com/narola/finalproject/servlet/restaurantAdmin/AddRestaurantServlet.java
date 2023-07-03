package com.narola.finalproject.servlet.restaurantAdmin;

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

public class AddRestaurantServlet extends HttpServlet {
    private RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/restaurantAdmin/addRestaurant.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Restaurant restaurant = new Restaurant();
            RestaurantAddress restaurantAddress = new RestaurantAddress();
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");

            String getStateId = request.getParameter("stateId");
            int stateId = 0;
            if (getStateId != null && !getStateId.isEmpty()) {
                stateId = Integer.parseInt(getStateId);
            }
            String getCityId = request.getParameter("cityId");
            int cityId = 0;
            if (getCityId != null && !getCityId.isEmpty()) {
                cityId = Integer.parseInt(getCityId);
            }
            String getPincode = request.getParameter("pincode");
            int pincode = 0;
            if (getPincode != null && !getPincode.trim().isEmpty() ) {
                pincode = Integer.parseInt(getPincode);
            }
            String isDineAvailable = request.getParameter("isDineInAvailable");
            boolean diningAvailable = false;
            if (isDineAvailable != null) {
                diningAvailable = Boolean.parseBoolean(isDineAvailable);
            }
            Part filePart = request.getPart("image");

            restaurant.setRestaurantName(request.getParameter("restaurantName"));
            restaurant.setRestaurantContact(request.getParameter("contact"));
            restaurant.setRestaurantEmail(request.getParameter("email"));
            restaurant.setOpensAt(request.getParameter("openingTime"));
            restaurant.setClosesAt(request.getParameter("closingTime"));
            restaurant.setGstNo(request.getParameter("gstNumber"));
            restaurant.setDiningAvailable(diningAvailable);
            restaurant.setRestaurantOwnerId(user.getUserId());

            restaurantAddress.setRestaurantAddLine1(request.getParameter("addressLine1"));
            restaurantAddress.setRestaurantAddLine2(request.getParameter("addressLine2"));
            restaurantAddress.setStateId(stateId);
            restaurantAddress.setCityId(cityId);
            restaurantAddress.setPincode(pincode);

            restaurant.setRestaurantAddress(restaurantAddress);
            
            List<Error> errorList = RestaurantValidation.validateRestaurantDetails(restaurant,filePart );
            if (!errorList.isEmpty()) {
                getErrors(errorList, request);
                getServletContext().getRequestDispatcher("/restaurantAdmin/addRestaurant.jsp").include(request, response);
            } else {
                String fileName = Utility.getSubmittedFileName(filePart);
                String staticFolderPath = getServletContext().getRealPath("/static/MenuItemsImgs");
                String filePath = staticFolderPath + File.separator + fileName;
                Utility.putImageToDirectory(filePath, filePart);
                String targetFilePath = "\\static\\MenuItemsImgs" + File.separator + fileName;
                restaurant.setRestaurantImageUrl(targetFilePath);

                restaurantService.addRestaurant(restaurant);

                response.sendRedirect("/manageRestaurant");
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
