package com.narola.finalproject.service;

import com.narola.finalproject.dao.CuisineCategoryDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;

import java.util.List;

public class CuisineCategoryService {

    private CuisineCategoryDao cuisineCategoryDao = new CuisineCategoryDao();

    public List<CuisineCategory> getCuisineCategory() throws DAOLayerException {
        List<CuisineCategory> cuisineCategoryList = cuisineCategoryDao.getCategory();
        return cuisineCategoryList;
    }

    public CuisineCategory getCuisineDetails(int cuisineId) throws DAOLayerException {
        CuisineCategory cuisineCategory = cuisineCategoryDao.getCuisineDetails(cuisineId);
        return cuisineCategory;
    }

    public void addCuisine(String cuisineName,  String filePath) throws DAOLayerException {
        cuisineCategoryDao.addCuisineCategory(cuisineName, filePath);
    }
}
