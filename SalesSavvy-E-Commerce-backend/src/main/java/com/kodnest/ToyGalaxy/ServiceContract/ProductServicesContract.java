package com.kodnest.ToyGalaxy.ServiceContract;

import com.kodnest.ToyGalaxy.entity.Product;

import java.util.List;

public interface ProductServicesContract {
    public List<Product> getProductsByCategory(String categoryName);
    public List<String> getProductImages(Integer productId);
}
