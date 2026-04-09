package com.kodnest.ToyGalaxy.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_per_unit", nullable = false)
    private BigDecimal pricePerUnit;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public OrderItem() {
        // TODO Auto-generated constructor stub
    }

    public OrderItem(int id, Order order, int productId, int quantity, BigDecimal pricePerUnit, BigDecimal totalPrice) {
        this.id = id;
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = totalPrice;
    }

    public OrderItem(Order order, int productId, int quantity, BigDecimal pricePerUnit, BigDecimal totalPrice) {
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = totalPrice;
    }
}