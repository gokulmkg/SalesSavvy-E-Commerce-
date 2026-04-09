package com.kodnest.ToyGalaxy.ServiceContract;

import com.kodnest.ToyGalaxy.entity.User;

import java.util.Map;

public interface CartIServicesContract {

    public void addToCart(User user, int productId, int quantity);
    public Map<String, Object> getCartItems(User authenticatedUser);
    public void deleteCartItem(User user,int productId);
    public Integer getCartItemCount(int userid);
    public void updateCartItemQuantity(User user, int productId, int quantity);
}
