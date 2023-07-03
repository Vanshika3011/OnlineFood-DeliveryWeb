package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.service.CuisineCategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CuisineManagementServlet extends HttpServlet {
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
            if(!cuisineCategoryList.isEmpty()){
                request.setAttribute("cuisineCategoryList", cuisineCategoryList);
                getServletContext().getRequestDispatcher("/admin/cuisineManagement.jsp").forward(request,response);
            } else {
                request.setAttribute("cuisineError","No cuisines found.Add cuisine.");
                getServletContext().getRequestDispatcher("/admin/addCuisine.jsp").include(request,response);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
