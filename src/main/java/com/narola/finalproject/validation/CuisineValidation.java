package com.narola.finalproject.validation;

import com.narola.finalproject.dao.CuisineCategoryDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Error;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class CuisineValidation {
    private static CuisineCategoryDao cuisineCategoryDao = new CuisineCategoryDao();

    public static List<Error> validateCuisineDetails(String cuisineName, Part filepart) throws DAOLayerException {
        List<Error> errorList = new ArrayList<>();
        if(cuisineName.isEmpty()){
            errorList.add(new Error("cuisineNameError", "Cuisine name is mandatory."));
        }
        if(cuisineCategoryDao.isCuisineExist(cuisineName)){
            errorList.add(new Error("cuisineNameError1", "Cuisine name already exist."));
        }
        if(filepart == null){
            errorList.add(new Error("cuisineImageError", "Cuisine image is mandatory."));
        }
        return errorList;
    }

    public static List<Error> validateCuisineName(String cuisineName) throws DAOLayerException {
        List<Error> errorList = new ArrayList<>();
        if(cuisineName.isEmpty()){
            errorList.add(new Error("cuisineNameError", "Cuisine name is mandatory."));
        }
        return errorList;
    }
}
