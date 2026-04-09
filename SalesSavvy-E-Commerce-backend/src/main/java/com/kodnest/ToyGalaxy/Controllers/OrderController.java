package com.kodnest.ToyGalaxy.Controllers;

import com.kodnest.ToyGalaxy.Service.OrderService;
import com.kodnest.ToyGalaxy.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5174" ,allowCredentials = "true") // Allow cross-origin requests
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrdersForUser(HttpServletRequest request) {
        try {


            User user = (User) request.getAttribute("authenticatedUser");

            Map<String, Object> response = orderService.getOrdersForUser(user);
            return ResponseEntity.ok(response);

        } catch (IllegalAccessError e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }
    }
}
