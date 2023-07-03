package com.narola.finalproject.validation;

import com.narola.finalproject.dao.LocationDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.Restaurant;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class RestaurantValidation {

    public static List<Error> validateRestaurantDetails(Restaurant restaurant, Part filePart) throws DAOLayerException {
        List<Error> errorList = new ArrayList<>();
        LocationDao locationDao = new LocationDao();

        if (Validation.isEmpty(restaurant.getRestaurantName())) {
            errorList.add(new Error("restaurantNameError", "Restaurant name can't be empty."));
        }

        String contactNumber = restaurant.getRestaurantContact();
        if (!Validation.isDigitsOnly(contactNumber)) {
            errorList.add(new Error("contactNoError", "Enter valid contact details."));
        }
        if (!Validation.validateMobileNo(contactNumber) && !Validation.isEmpty(contactNumber)) {
            errorList.add(new Error("contactNoError1", "Contact number should be of exactly 10 digits."));
        }

        if (!Validation.validateEmail(restaurant.getRestaurantEmail())) {
            errorList.add(new Error("emailError", "Enter valid email."));
        }

        if(restaurant.getOpensAt().isEmpty() || !Validation.validateTime(restaurant.getOpensAt())){
            errorList.add(new Error("opensAtError", "Enter valid time format as hh:mm"));
        }

        if(restaurant.getClosesAt().isEmpty() || !Validation.validateTime(restaurant.getClosesAt())){
            errorList.add(new Error("closesAtError", "Enter valid time format as hh:mm"));
        }

        if (Validation.isEmpty(restaurant.getGstNo())) {
            errorList.add(new Error("gstNumError", "GST number can't be empty."));
        }

        if (Validation.isEmpty(restaurant.getRestaurantAddress().getRestaurantAddLine1())) {
            errorList.add(new Error("restaurantAddError", "Restaurant address can't be empty."));
        }

        if(!locationDao.isStateExists(restaurant.getRestaurantAddress().getStateId()) ){
            errorList.add(new Error("stateIdError", "Select valid state "));
        }

        if(!locationDao.isCityExists(restaurant.getRestaurantAddress().getCityId(),restaurant.getRestaurantAddress().getStateId()) ){
            errorList.add(new Error("cityIdError", "Select valid city "));
        }

        String pincode = String.valueOf(restaurant.getRestaurantAddress().getPincode());
        if (!Validation.isDigitsOnly(pincode) || pincode.equals("0") ) {
            errorList.add(new Error("pincodeError", "Enter valid Pincode"));
        }
        if (!pincode.equals("0") && !Validation.validatePincode(pincode)) {
            errorList.add(new Error("pincodeError1", "Pincode number should be of exactly 6 digits"));
        }

        if(filePart == null || filePart.getSize() ==0){
            errorList.add(new Error("imageError", "Image is mandatory to add."));
        }

        return errorList;
    }

    public static List<Error> validateRestaurantDetailsToEdit(Restaurant restaurant) throws DAOLayerException {
        List<Error> errorList = new ArrayList<>();

        if (Validation.isEmpty(restaurant.getRestaurantName())) {
            errorList.add(new Error("restaurantNameError", "Restaurant name can't be empty."));
        }

        String contactNumber = restaurant.getRestaurantContact();
        if (!Validation.isDigitsOnly(contactNumber)) {
            errorList.add(new Error("contactNoError", "Enter valid contact details."));
        }
        if (!Validation.validateMobileNo(contactNumber) && !Validation.isEmpty(contactNumber)) {
            errorList.add(new Error("contactNoError1", "Contact number should be of exactly 10 digits."));
        }

        if (!Validation.validateEmail(restaurant.getRestaurantEmail())) {
            errorList.add(new Error("emailError", "Enter valid email."));
        }

        String opensAt = restaurant.getOpensAt();
        if(restaurant.getOpensAt().isEmpty() || opensAt == null){
            errorList.add(new Error("opensAtError", "This field can't be empty. Enter valid time format as hh:mm"));
        }
        else if( !Validation.validateTime(opensAt.substring(0, 5))){
            errorList.add(new Error("opensAtError1", "This field can't be empty. Enter valid time format as hh:mm"));
        }

        String closesAt = restaurant.getClosesAt();
        if(restaurant.getClosesAt().isEmpty() || closesAt == null){
            errorList.add(new Error("closesAtError", "Enter valid time format as hh:mm"));
        }else if( !Validation.validateTime(closesAt.substring(0, 5))){
            errorList.add(new Error("closesAtError1", "Enter valid time format as hh:mm"));
        }

        if (Validation.isEmpty(restaurant.getRestaurantAddress().getRestaurantAddLine1())) {
            errorList.add(new Error("restaurantAddError", "Restaurant address can't be empty."));
        }

        String pincode = String.valueOf(restaurant.getRestaurantAddress().getPincode());
        if (!Validation.isDigitsOnly(pincode) || pincode.equals("0") ) {
            errorList.add(new Error("pincodeError", "Enter valid Pincode"));
        }
        if (!pincode.equals("0") && !Validation.validatePincode(pincode)) {
            errorList.add(new Error("pincodeError1", "Pincode number should be of exactly 6 digits"));
        }

        return errorList;
    }
}
