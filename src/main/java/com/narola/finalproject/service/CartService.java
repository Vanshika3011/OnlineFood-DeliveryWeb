package com.narola.finalproject.service;

import com.narola.finalproject.dao.CartDao;
import com.narola.finalproject.dao.CartItemDao;
import com.narola.finalproject.dao.RestaurantMenuDao;
import com.narola.finalproject.exception.DAOLayerException;
import com.narola.finalproject.model.Cart;
import com.narola.finalproject.model.CartDetails;
import com.narola.finalproject.model.CartItem;

import java.util.List;

public class CartService {
    private CartDao cartDao = new CartDao();
    private CartItemDao cartItemDao = new CartItemDao();
    private RestaurantMenuDao restaurantMenuDao = new RestaurantMenuDao();

    public void doAddCartItem(Cart cart, CartItem cartItem) throws DAOLayerException {

        int restaurantId = cart.getRestaurantId();
        int customerId = cart.getCustomerId();
        int itemId = cartItem.getItemId();
        int itemPrice = restaurantMenuDao.getPriceForMenuItem(itemId);

        if (cartDao.doesUserCartAlreadyExist(restaurantId, customerId)) {
            int cartId = cartDao.getCartIdOfUser(customerId, restaurantId);

            if (cartItemDao.doesItemAlreadyExist(itemId, cartId)) {
                cartItemDao.updateItemCount(itemId, customerId);
            } else {
                cartDao.doAddItemsToCart(cartItem, cartId, customerId, itemPrice);
            }
        } else {
            cartDao.deleteUserCart(customerId);
            int generatedCartId = cartDao.createCart(cart);
            cartDao.doAddItemsToCart(cartItem, generatedCartId, customerId, itemPrice);
        }

    }

    public List<CartDetails> getUserCartDetails(int customerId) throws DAOLayerException {
        List<CartDetails> cartDetailsList = cartDao.getUserCartDetails(customerId);
        return cartDetailsList;
    }

    public void increaseItemCount(int itemId, int customerId) throws DAOLayerException {
        cartItemDao.updateItemCount(itemId, customerId);
    }

    public void decreaseItemCount(int itemId, int customerId) throws DAOLayerException {
        if (cartItemDao.getItemQuantity(itemId, customerId) == 1) {
            cartItemDao.removeItem(itemId, customerId);
        } else {
            cartItemDao.reduceItemCount(itemId, customerId);
        }
    }

    public void removeItem(int itemId, int customerId) throws DAOLayerException {
        cartItemDao.removeItem(itemId, customerId);
    }

    public  CartDetails getUserCartDetailsWithTotal(int customerId) throws DAOLayerException {
        return cartDao.getUserCartDetailsWithSum(customerId);
    }

    public void deleteCartDetails(int customerId) throws DAOLayerException {
        cartDao.deleteUserCart(customerId);
    }

    public int getCartItemCount(int customerId) throws DAOLayerException{
        return cartDao.getCartItemCount(customerId);
    }
}
