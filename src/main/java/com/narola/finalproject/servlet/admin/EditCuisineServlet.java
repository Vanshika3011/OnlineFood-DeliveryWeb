package com.narola.finalproject.servlet.admin;

import com.narola.finalproject.dao.CuisineCategoryDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.CuisineCategory;
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
public class EditCuisineServlet extends HttpServlet {
    private CuisineCategoryService cuisineCategoryService = new CuisineCategoryService();
    private CuisineCategoryDao cuisineCategoryDao = new CuisineCategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cuisineId = request.getParameter("id");
        if (cuisineId == null) {
            cuisineId = (String) request.getAttribute("cuisineId");
        }
        try {
            CuisineCategory cuisineCategory = cuisineCategoryService.getCuisineDetails(Integer.parseInt(cuisineId));
            request.setAttribute("cuisineCategory", cuisineCategory);
            getServletContext().getRequestDispatcher("/admin/editCuisineName.jsp").forward(request, response);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String cuisineName = request.getParameter("cuisineName");
            Part filePart = request.getPart("image");
            String cuisineId = request.getParameter("cuisineId");
            if (cuisineId == null) {
                request.setAttribute("error", "Error occurred while editing cuisineItem");
                getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            } else {
                List<Error> errorList = CuisineValidation.validateCuisineName(cuisineName);
                if (!errorList.isEmpty()) {
                    CuisineCategory cuisineCategory = cuisineCategoryService.getCuisineDetails(Integer.parseInt(cuisineId));
                    request.setAttribute("cuisineCategory", cuisineCategory);
                    getErrors(errorList, request);
                    getServletContext().getRequestDispatcher("/admin/editCuisineName.jsp").include(request, response);
                } else {
                    if (cuisineId.isEmpty()) {
                        request.setAttribute("error", "Error occurred while editing cuisineItem");
                        doGet(request, response);
                    } else {
                        String filePath;
                        if (filePart == null || filePart.getSize() == 0) {
                            filePath = request.getParameter("imagePath");
                            cuisineCategoryDao.updateCuisineItem(Integer.parseInt(cuisineId), cuisineName, filePath);
                        } else {
                            String fileName = Utility.getSubmittedFileName(filePart);
                            String staticFolderPath = getServletContext().getRealPath("/static/MenuItemsImgs");
                            filePath = staticFolderPath + File.separator + fileName;
                            Utility.putImageToDirectory(filePath, filePart);
                            String targetFilePath = "\\static\\MenuItemsImgs" + File.separator + fileName;
                            cuisineCategoryDao.updateCuisineItem(Integer.parseInt(cuisineId), cuisineName, targetFilePath);
                        }
                        response.sendRedirect("/cuisineManagement");
                    }
                }
            }
        } catch (DAOLayerException e) {
            request.setAttribute("error", "Error occurred while updating Cuisine.");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    private void getErrors(List<Error> errorList, HttpServletRequest request) {
        for (Error error : errorList) {
            System.out.println(error.getMessage());
            request.setAttribute(error.getFieldError(), error.getMessage());
        }
    }
}
