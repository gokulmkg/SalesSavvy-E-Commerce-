package com.kodnest.ToyGalaxy.Controllers;

import com.kodnest.ToyGalaxy.Service.CartItemService;
import com.kodnest.ToyGalaxy.ServiceContract.CartIServicesContract;
import com.kodnest.ToyGalaxy.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(
        origins = {"http://localhost:5174", "http://localhost:5173"},
        allowCredentials = "true"
)
@RequestMapping("/api/cart")
public class CartItemController {
    private CartIServicesContract cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Object> req, HttpServletRequest request) {


        User user  = (User) request.getAttribute("authenticatedUser");
            //String username = (String) req.get("username");
            int product_id = (int) req.get("productId");
        int quantity = req.containsKey("quantity") ? (int) req.get("quantity") : 1;
        cartItemService.addToCart(user,product_id,quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/items")
    public ResponseEntity<Map<String,Object>> getCartItems(HttpServletRequest request) {
        User user = (User)request.getAttribute("authenticatedUser");
        Map<String,Object> cartItems = cartItemService.getCartItems(user);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItems(@RequestBody Map<String,Object> req,HttpServletRequest request) {
        User user = (User)request.getAttribute("authenticatedUser");
        int product_id = (int)req.get("productId");
        cartItemService.deleteCartItem(user,product_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/items/count")
    public ResponseEntity<Integer> getCartItemCount(HttpServletRequest request) {
//        // Fetch user by username to get the userId
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
//
//        // Call the service to get the total cart item count
//        int count = cartService.getCartItemCount(user.getUserId());
//        return ResponseEntity.ok(count);
        User user = (User)request.getAttribute("authenticatedUser");
        int count = cartItemService.getCartItemCount(user.getUserId());

        return ResponseEntity.ok(count);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCartItemQuantity(HttpServletRequest req,@RequestBody Map<String, Object> request) {
        User user = (User) req.getAttribute("authenticatedUser");
         int userid = user.getUserId();
         int productId = (int)request.get("productId");
         int quantity = (int) request.get("quantity");

             cartItemService.updateCartItemQuantity(user,productId,quantity);
             return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
