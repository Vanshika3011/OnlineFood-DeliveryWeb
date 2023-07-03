package com.narola.finalproject.servlet;

import com.narola.finalproject.dao.UserDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.narola.finalproject.constant.AppConstant.MAX_TRIALS_FOR_VERIFICATION;

public class VerificationServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private int count = 1;//session

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("count", count);
        RequestDispatcher rd = request.getRequestDispatcher("verificationCode.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String verificationNum = request.getParameter("verificationCode");
            if (verificationNum.isEmpty()) {
                request.setAttribute("error", "Error while verifying.Please try later.");
                getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            } else {
                int verificationCode = Integer.parseInt(verificationNum);

                HttpSession httpSession = request.getSession();
                User user = (User) httpSession.getAttribute("user");
                int count = Integer.parseInt(String.valueOf(httpSession.getAttribute("count")));

                if (count < MAX_TRIALS_FOR_VERIFICATION) {
                    if (verificationCode == user.getVerificationCode()) {
                        userDao.updateIsVerified(user.getUsername());
                        response.sendRedirect("/homePage");
                    } else {
                        count++;
                        if (count == MAX_TRIALS_FOR_VERIFICATION) {
                            count = 1;
                            httpSession.setAttribute("count", count);
                            response.sendRedirect("/doLogin");
                        } else {
                            httpSession.setAttribute("count", count);
                            request.setAttribute("verificationCodeError", "Enter valid verification code.");
                            request.getRequestDispatcher("/verificationCode.jsp").forward(request, response);
                        }
                    }
                }
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}

