package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.dao.CuisineCategoryDao;
import com.narola.finalproject.exception.DAOLayerException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteCuisineServlet extends HttpServlet {

    private CuisineCategoryDao cuisineCategoryDao = new CuisineCategoryDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cuisineId = Integer.parseInt(request.getParameter("id"));
        try {
            cuisineCategoryDao.deleteCuisineItem(cuisineId);
            response.sendRedirect("/cuisineManagement");
        } catch (DAOLayerException e) {
            request.setAttribute("error","Error while deleting cuisine.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
