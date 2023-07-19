package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.service.CuisineCategoryService;
import com.narola.finalproject.utility.Utility;
import com.narola.finalproject.validation.CuisineValidation;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class AddCuisineServlet extends HttpServlet {
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/admin/addCuisine.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String cuisineName = request.getParameter("cuisineName");
            Part filePart = request.getPart("image");

            List<Error> errorList = CuisineValidation.validateCuisineDetails(cuisineName, filePart);
            if (!errorList.isEmpty()) {
                getErrors(errorList, request);
                getServletContext().getRequestDispatcher("/admin/addCuisine.jsp").include(request, response);
            } else {
                String fileName = Utility.getSubmittedFileName(filePart);
                String staticFolderPath = getServletContext().getRealPath(getServletContext().getInitParameter("imageFolderPath"));
                String filePath = staticFolderPath + File.separator + fileName;
                Utility.putImageToDirectory(filePath, filePart);
                String targetFilePath = getServletContext().getInitParameter("imageFolderPath") + File.separator + fileName;

                cuisineCategoryService.addCuisine(cuisineName, targetFilePath);
                response.sendRedirect("/cuisineManagement");
            }
        } catch (DAOLayerException e) {
            request.setAttribute("error", "Error occurred while adding Cuisine.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request,response);
            e.printStackTrace();
        }
    }

    private void getErrors(List<Error> errorList, HttpServletRequest request) {
        for (Error error : errorList) {
            request.setAttribute(error.getFieldError(), error.getMessage());
        }
    }
}
