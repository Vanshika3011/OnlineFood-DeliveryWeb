package com.narola.finalproject.service;

import com.narola.finalproject.dao.UserAddressDao;
import com.narola.finalproject.dao.UserDao;
import com.narola.finalproject.dao.UserRoleDao;
import com.narola.finalproject.email.MailSender;
import com.narola.finalproject.enums.UserRolesEnum;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.exception.MailException;
import com.narola.finalproject.model.User;
import com.narola.finalproject.model.UserAddress;
import com.narola.finalproject.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private MailSender mailSender = new MailSender();
    private UserDao userDao = new UserDao();
    private UserRoleDao userRoleDao = new UserRoleDao();
    private UserAddressDao userAddressDao = new UserAddressDao();

    public User doLogin(String username, String password) throws DAOLayerException {
        User user = userDao.validateUser(username, password);
        return user;
    }

    public void sendMail(User user) throws MailException {
        String subject = "Verification code for your Foodies account";
        String body = "Hello " + user.getFirstName() + ",\nYour verification code is " + user.getVerificationCode() + ".";
        mailSender.sendEmail(user.getEmail(), subject, body);
    }

    public void doSignUp(User user) throws DAOLayerException, MailException {
        sendMail(user);
        userDao.createUser(user);
    }

    public List<UserRole> getUserRoleList() throws DAOLayerException {
        List<UserRole> userRoleList = userRoleDao.getAllUserRoles();
        List<UserRole> desiredUserRoleList = new ArrayList<>();

        for (UserRole userRole : userRoleList) {
            if ((userRole.getUserRoleId() != UserRolesEnum.ADMIN.getRoleIdValue()) && (userRole.getUserRoleId() != UserRolesEnum.DELIVERYAGENT.getRoleIdValue())) {
                desiredUserRoleList.add(userRole);
            }
        }
        return desiredUserRoleList;
    }

    public List<User> getUserList() throws DAOLayerException {
        return userDao.getUsersDetails();
    }

    public List<User> getFilteredUserList(String option, List<User> userList) {
        List<User> desiredUserList = new ArrayList<>();
        switch (option) {
            case "admin":
                for (User user : userList) {
                    if (user.getRoleId() == UserRolesEnum.ADMIN.getRoleIdValue()) {
                        desiredUserList.add(user);
                    }
                }
                System.out.println(desiredUserList.size());
                break;
            case "restaurantAdmin":
                for (User user : userList) {
                    if (user.getRoleId() == UserRolesEnum.RESTAURANTADMIN.getRoleIdValue()) {
                        desiredUserList.add(user);
                    }
                }
                break;
            case "customer":
                for (User user : userList) {
                    if (user.getRoleId() == UserRolesEnum.CUSTOMER.getRoleIdValue()) {
                        desiredUserList.add(user);
                    }
                }
                break;
            case "deliveryAgent":
                for (User user : userList) {
                    if (user.getRoleId() == UserRolesEnum.DELIVERYAGENT.getRoleIdValue()) {
                        desiredUserList.add(user);
                    }
                }
                break;
            default:
                desiredUserList.addAll(userList);
                break;

        }return desiredUserList;
    }

    public void deleteUser(int userId) throws DAOLayerException {
        userDao.deleteUser(userId);
    }

    public void addUserAddress(UserAddress userAddress) throws DAOLayerException{
        userAddressDao.doAddUserAddress(userAddress);
    }

    public List<UserAddress> getUserAddressList(int userId) throws DAOLayerException {
       return userAddressDao.getUserAddressDetails(userId);
    }
}
