package com.kodnest.ToyGalaxy.Service;

import com.kodnest.ToyGalaxy.Repo.OrderItemRepository;
import com.kodnest.ToyGalaxy.Repo.OrderRepository;
import com.kodnest.ToyGalaxy.Repo.ProductImageRepository;
import com.kodnest.ToyGalaxy.Repo.ProductRepository;
import com.kodnest.ToyGalaxy.ServiceContract.OrderServiceContract;
import com.kodnest.ToyGalaxy.entity.OrderItem;
import com.kodnest.ToyGalaxy.entity.Product;
import com.kodnest.ToyGalaxy.entity.ProductImage;
import com.kodnest.ToyGalaxy.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService implements OrderServiceContract {


    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public OrderService(OrderItemRepository orderItemRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Map<String, Object> getOrdersForUser(User user) {
        List<OrderItem> orderItems = orderItemRepository.findSuccessfulOrderItemsByUserId(user.getUserId());

        Map<String,Object> response = new HashMap<>();
        response.put("username",user.getUsername());
        response.put("role",user.getRole());


        List<Map<String, Object>> products = new ArrayList<>();

        for (OrderItem item:orderItems) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if(product == null) {
                continue;
            }

            List<ProductImage> images = productImageRepository.findByProduct_productId(product.getProductId());
            String imageUrl = images.isEmpty() ? null : images.get(0).getImageUrl();

            Map<String, Object> productDetails = new HashMap<>();
            productDetails.put("order_id", item.getOrder().getOrderId());
            productDetails.put("quantity", item.getQuantity());
            productDetails.put("total_price", item.getTotalPrice());
            productDetails.put("image_url", imageUrl);
            productDetails.put("product_id", product.getProductId());
            productDetails.put("name", product.getName());
            productDetails.put("description", product.getDescription());
            productDetails.put("price_per_unit", item.getPricePerUnit());

            products.add(productDetails);
        }
         response.put("products",products);
        return response;
    }
}
