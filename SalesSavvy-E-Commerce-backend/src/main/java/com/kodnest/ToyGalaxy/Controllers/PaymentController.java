package com.kodnest.ToyGalaxy.Controllers;

import com.kodnest.ToyGalaxy.Service.PaymentService;
import com.kodnest.ToyGalaxy.entity.OrderItem;
import com.kodnest.ToyGalaxy.entity.User;
import com.razorpay.RazorpayException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5174" ,allowCredentials = "true")
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPaymentOrder(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("authenticatedUser");

            BigDecimal totalAmount = new BigDecimal(requestBody.get("totalAmount").toString());
            List<Map<String, Object>> cartItemsRaw = (List<Map<String, Object>>) requestBody.get("cartItems");

            List<OrderItem> cartItems = cartItemsRaw.stream().map(item -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId((Integer) item.get("productId"));
                orderItem.setQuantity((Integer) item.get("quantity"));
                BigDecimal pricePerUnit = new BigDecimal(item.get("price").toString());
                orderItem.setPricePerUnit(pricePerUnit);
                orderItem.setTotalPrice(pricePerUnit.multiply(BigDecimal.valueOf((Integer) item.get("quantity"))));
                return orderItem;
            }).collect(Collectors.toList());

            String razorpayOrderId = paymentService.createOrder(user.getUserId(), totalAmount, cartItems);

            return ResponseEntity.ok(razorpayOrderId);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Razorpay order: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data: " + e.getMessage());
        }
    }



        @PostMapping("/verify")
        public ResponseEntity<String> verifyPayment(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
            try {
                // Fetch authenticated user
                User user = (User) request.getAttribute("authenticatedUser");

                int userId=user.getUserId();
                // Extract Razorpay payment details from the request body
                String razorpayOrderId = (String) requestBody.get("razorpayOrderId");
                String razorpayPaymentId = (String) requestBody.get("razorpayPaymentId");
                String razorpaySignature = (String) requestBody.get("razorpaySignature");

                // Call the payment service to verify the payment
                boolean isVerified = paymentService.verifyPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature,userId);

                if (isVerified) {
                    return ResponseEntity.ok("Payment verified successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying payment: " + e.getMessage());
            }
        }

    }

