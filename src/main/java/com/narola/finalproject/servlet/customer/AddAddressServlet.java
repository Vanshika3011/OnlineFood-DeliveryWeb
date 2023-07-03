package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.User;
import com.narola.finalproject.model.UserAddress;
import com.narola.finalproject.service.UserService;
import com.narola.finalproject.validation.RestaurantValidation;
import com.narola.finalproject.validation.UserValidation;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AddAddressServlet extends HttpServlet {
    private UserAddress userAddress = new UserAddress();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/customer/addAddress.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");
            String addressType = request.getParameter("address-type");
            if (addressType == null || addressType.isEmpty()) {
                addressType = request.getParameter("other-address-type");
            }

            String cityId = request.getParameter("city");
            if (cityId == null || cityId.trim().isEmpty()) {
                cityId = "0";
            }

            String stateId = request.getParameter("state");
            if (stateId == null || stateId.trim().isEmpty()) {
                stateId = "0";
            }

            String pincode = request.getParameter("pincode");
            if (pincode == null || pincode.trim().isEmpty()) {
                pincode = "0";
            }
            userAddress.setUserId(user.getUserId());
            userAddress.setAddressType(addressType);
            userAddress.setStreetLine1(request.getParameter("street1"));
            userAddress.setStreetLine2(request.getParameter("street2"));
            userAddress.setLandmark(request.getParameter("landmark"));
            userAddress.setStateId(Integer.parseInt(stateId));
            userAddress.setCityId(Integer.parseInt(cityId));
            userAddress.setPincode(Integer.parseInt(pincode));

            List<Error> errorList = UserValidation.validationForAddAddress(userAddress);
            if (!errorList.isEmpty()) {
                getErrors(errorList, request);
                getServletContext().getRequestDispatcher("/customer/addAddress.jsp").include(request, response);
            } else {
                userService.addUserAddress(userAddress);
                response.sendRedirect("/addressPage");
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
