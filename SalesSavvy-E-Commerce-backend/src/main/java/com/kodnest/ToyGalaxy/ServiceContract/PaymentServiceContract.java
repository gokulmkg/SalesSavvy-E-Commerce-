package com.kodnest.ToyGalaxy.ServiceContract;

import com.kodnest.ToyGalaxy.entity.OrderItem;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentServiceContract {

    public String createOrder(int userId, BigDecimal totalAmount, List<OrderItem> cartItems) throws RazorpayException;
    public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, int userId);
    public void saveOrderItems(String orderId, List<OrderItem> items);
}
