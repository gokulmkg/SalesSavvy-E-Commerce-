package com.kodnest.ToyGalaxy.Service;

import com.kodnest.ToyGalaxy.Repo.CartRepository;
import com.kodnest.ToyGalaxy.Repo.ProductImageRepository;
import com.kodnest.ToyGalaxy.Repo.ProductRepository;
import com.kodnest.ToyGalaxy.Repo.UserRepository;
import com.kodnest.ToyGalaxy.ServiceContract.CartIServicesContract;
import com.kodnest.ToyGalaxy.entity.CartItem;
import com.kodnest.ToyGalaxy.entity.Product;
import com.kodnest.ToyGalaxy.entity.ProductImage;
import com.kodnest.ToyGalaxy.entity.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartItemService implements CartIServicesContract {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    //    private final UserRepository userRepository;
    private final ProductImageRepository productImageRepository;

    public CartItemService(ProductRepository productRepository, CartRepository categoryRepository, UserRepository userRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.cartRepository = categoryRepository;
        this.productImageRepository = productImageRepository;
//        this.userRepository = userRepository;
    }

    @Override
    public void addToCart(User user, int productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(user.getUserId(), productId);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem(user, product, quantity);
            cartRepository.save(newItem);
        }

    }


    public Map<String, Object> getCartItems(User authenticatedUser) {
        List<CartItem> cartItems = cartRepository.findCartItemsWithProductDetails(authenticatedUser.getUserId());

        Map<String, Object> response = new HashMap<>();

//        User user = userRepository.findById(authenticatedUser.getUserId())
//                .orElseThrow(()->new IllegalArgumentException("User not found"));

        response.put("username", authenticatedUser.getUsername());
        response.put("role", authenticatedUser.getRole().toString());

        List<Map<String, Object>> products = new ArrayList<>();
        int overallTotalPrice = 0;

        for (CartItem cartItem : cartItems) {
            Map<String, Object> productDetails = new HashMap<>();

            Product product = cartItem.getProduct();

            List<ProductImage> productImages = productImageRepository.findByProduct_productId(product.getProductId());
            String imagUrl = null;

            if (productImages != null && !productImages.isEmpty()) {
                imagUrl = productImages.get(0).getImageUrl();
            } else {
                imagUrl = "default-image-url";
            }

            productDetails.put("product_id", product.getProductId());
            productDetails.put("image_Url", imagUrl);
            productDetails.put("name", product.getName());
            productDetails.put("description", product.getDescription());
            productDetails.put("price_per_unit", product.getPrice());
            productDetails.put("quantity", cartItem.getQuantity());
            productDetails.put("total_price", cartItem.getQuantity() * product.getPrice().doubleValue());

            products.add(productDetails);

            overallTotalPrice += cartItem.getQuantity() * product.getPrice().doubleValue();
        }
        Map<String, Object> cart = new HashMap<>();

        cart.put("products", products);
        cart.put("overall_total_price", overallTotalPrice);

        response.put("cart", cart);

        return response;

    }

    public void deleteCartItem(User user, int productId) {
        cartRepository.deleteCartItem(user.getUserId(), productId);
    }


    public Integer getCartItemCount(int userid) {
        int count = cartRepository.getCartItemCount(userid);
        return count;
    }

    public void updateCartItemQuantity(User user, int productId, int quantity) {
        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(user.getUserId(), productId);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            if (quantity == 0) {
                deleteCartItem(user, productId);
            } else {
                cartItem.setQuantity(quantity);
                cartRepository.save(cartItem);
            }
        }
    }
}
