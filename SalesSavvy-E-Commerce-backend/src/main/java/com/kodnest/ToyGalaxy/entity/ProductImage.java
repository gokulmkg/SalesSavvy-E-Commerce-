package com.kodnest.ToyGalaxy.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    // Getters and Setters

    public ProductImage() {
        // TODO Auto-generated constructor stub
    }

    public ProductImage(Integer imageId, Product product, String imageUrl) {
        super();
        this.imageId = imageId;
        this.product = product;
        this.imageUrl = imageUrl;
    }

    public ProductImage(Product product, String imageUrl) {
        super();
        this.product = product;
        this.imageUrl = imageUrl;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
