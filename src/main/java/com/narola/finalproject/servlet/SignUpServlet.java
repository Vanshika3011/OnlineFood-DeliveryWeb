package com.narola.finalproject.servlet;

import com.narola.finalproject.dao.UserDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.exception.MailException;
import com.narola.finalproject.model.Error;
import com.narola.finalproject.model.ErrorMessage;
import com.narola.finalproject.model.User;
import com.narola.finalproject.model.UserRole;
import com.narola.finalproject.service.UserService;
import com.narola.finalproject.utility.Utility;
import com.narola.finalproject.validation.UserValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SignUpServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<UserRole> userRoleList = userService.getUserRoleList();
            req.setAttribute("userRoleList", userRoleList);
            getServletContext().getRequestDispatcher("/signUp.jsp").include(req, resp);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            User user = new User();
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setFirstName(request.getParameter("firstname"));
            user.setLastName(request.getParameter("lastname"));
            user.setContact(request.getParameter("contact"));
            user.setEmail(request.getParameter("email"));

            List<Error> errorList = UserValidation.validationForSignUp(user);
            if (!errorList.isEmpty()) {
                getErrors(errorList, request);
                List<UserRole> userRoleList = userService.getUserRoleList();
                request.setAttribute("userRoleList", userRoleList);
                getServletContext().getRequestDispatcher("/signUp.jsp").include(request, response);
            } else {
                int roleId = Integer.parseInt(request.getParameter("role"));
                user.setRoleId(roleId);
                user.setVerificationCode(Utility.generateVerificationCode());
                userService.doSignUp(user);
                response.sendRedirect("/doLogin");
            }
        } catch (DAOLayerException | MailException e) {
            request.setAttribute("error","Error occurred while Sign-up");
            getServletContext().getRequestDispatcher("/errorPage.jsp").include(request, response);
            e.printStackTrace();
        }
    }

    private void getErrors(List<Error> errorList, HttpServletRequest request) {
        for (Error error : errorList) {
            request.setAttribute(error.getFieldError(), error.getMessage());
        }
    }
}
