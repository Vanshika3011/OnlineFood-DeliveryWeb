package com.narola.finalproject.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean validateUserNameLength(String userName) {
        if (userName.length() < 8) {
            return false;
        }
        if (userName.length() > 20) {
            return false;
        }
        return true;
    }

    public static boolean validateMobileNo(String mobileNo) {
        if (mobileNo.length() < 10 || mobileNo.length() > 10) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isDigitsOnly(String mobileNo) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(mobileNo);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String temail) {
        String email = temail;
        String emailRegex = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean validatePincode(String pincode) {
        if (pincode.length() < 6 || pincode.length() > 6) {
            return false;
        }
        return true;
    }

    public static boolean validateTime(String time) {
        if (time.isEmpty()) {
            return true;
        }
        // Regular expression to validate time in HH:MM format
        String timeRegex = "^([01]\\d|2[0-3]):([0-5]\\d)$";
        return time.matches(timeRegex);
    }
}
