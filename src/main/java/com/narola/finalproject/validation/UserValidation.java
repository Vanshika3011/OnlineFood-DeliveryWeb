package com.narola.finalproject.validation;

import com.narola.finalproject.dao.LocationDao;
import com.narola.finalproject.dao.UserDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.User;
import com.narola.finalproject.model.UserAddress;

import java.util.ArrayList;
import java.util.List;

public class UserValidation {
    private static UserDao userDao = new UserDao();
    private static  LocationDao locationDao = new LocationDao();

    public static List<Error> validationForSignUp(User user) {
        List<Error> errorList = new ArrayList<>();


        String username = user.getUsername();
        if (!Validation.validateUserNameLength(username)) {
            errorList.add(new Error("userNameError", "Length of Username must be minimum 8 characters and maximum 20 characters."));
        }
        try {
            if (userDao.isUsernameExist(username)) {
                errorList.add(new Error("userNameError1", "Username already exist. PLease enter username."));
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

        if (!Validation.validatePassword(user.getPassword())) {
            errorList.add(new Error("passwordError", "Password must be of at least 8 characters."));
        }

        String firstName = user.getFirstName();
        if (Validation.isEmpty(firstName)) {
            errorList.add(new Error("firstNameError", "FirstName is mandatory."));
        }


        String contactNumber = user.getContact();
        if (!Validation.isDigitsOnly(contactNumber)) {
            errorList.add(new Error("contactNoError", "Enter valid contact details."));
        }
        if (!Validation.validateMobileNo(contactNumber)) {
            errorList.add(new Error("contactNoError1", "Contact number should be of exactly 10 digits."));
        }

        String email = user.getEmail();
        if (!Validation.validateEmail(email)) {
            errorList.add(new Error("emailError", "Enter valid email."));
        }
        try {
            if (userDao.isUserEmailExist(email)) {
                errorList.add(new Error("emailError1", "Email already exist."));
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

        return errorList;
    }

    public static List<Error> validationForLogin(User user) {
        List<Error> errorList = new ArrayList<>();
        String username = user.getUsername();
        if (Validation.isEmpty(username)) {
            errorList.add(new Error("userNameError", "UserName can't be empty."));
        }
        String password = user.getPassword();
        if (Validation.isEmpty(password)) {
            errorList.add(new Error("passwordError", "Password  can't be empty."));
        }
        return errorList;

    }

    public static List<Error> validationForAddAddress(UserAddress userAddress) throws DAOLayerException {
        List<Error> errorList = new ArrayList<>();

        if(Validation.isEmpty(userAddress.getAddressType())){
            errorList.add(new Error("addressTypeError", "Select valid Address Type can't be empty."));
        }
        if (Validation.isEmpty(userAddress.getStreetLine1())) {
            errorList.add(new Error("userAddError", "Street1 address can't be empty."));
        }

        if(Validation.isEmpty(userAddress.getLandmark())){
            errorList.add(new Error("landmarkError", "landmark can't be empty."));
        }
        if (!locationDao.isStateExists(userAddress.getStateId())) {
            errorList.add(new Error("stateIdError", "Select valid state "));
        }

        if (!locationDao.isCityExists(userAddress.getCityId(), userAddress.getStateId())) {
            errorList.add(new Error("cityIdError", "Select valid city "));
        }

        String pincode = String.valueOf(userAddress.getPincode());
        if (!Validation.isDigitsOnly(pincode) || pincode.equals("0")) {
            errorList.add(new Error("pincodeError", "Enter valid Pincode"));
        }
        if (!pincode.equals("0") && !Validation.validatePincode(pincode)) {
            errorList.add(new Error("pincodeError1", "Pincode number should be of exactly 6 digits"));
        }

        return errorList;
    }
}
