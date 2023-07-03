package com.narola.finalproject.validation;

import com.narola.finalproject.model.Error;
import java.util.ArrayList;
import java.util.List;

public class MenuValidation {

    public static List<Error> validateMenuItems(String itemName, String priceValue,String ingredients,String categoryId,String foodType){
        List<Error> errorList = new ArrayList<>();

        if (Validation.isEmpty(itemName)) {
            errorList.add(new Error("itemNameError", "Item name can't be empty."));
        }

        if(priceValue.isEmpty()){
            errorList.add(new Error("priceError", "price can't be empty."));
        }

        if (Validation.isEmpty(ingredients)) {
            errorList.add(new Error("ingredientsError", "Ingredients can't be empty."));
        }

        if(categoryId.isEmpty()){
            errorList.add(new Error("categoryIdError", "Category Id can't be empty."));
        }


        return errorList;
    }
}
