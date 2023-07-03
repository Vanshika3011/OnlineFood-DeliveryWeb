package com.narola.finalproject.servlet.customer;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
import com.narola.finalproject.service.CuisineCategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CustomerHomePageServlet extends HttpServlet {

    CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getCuisines(request, response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/customer/customerHomePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void getCuisines(HttpServletRequest request, HttpServletResponse response) throws DAOLayerException, ServletException, IOException {
        List<CuisineCategory> cuisineCategoryList = cuisineCategoryService.getCuisineCategory();
        if (!cuisineCategoryList.isEmpty() && cuisineCategoryList != null) {
            request.setAttribute("cuisineCategoryList", cuisineCategoryList);
        } else {
            request.setAttribute("error", "No data found.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
        }
    }
}
