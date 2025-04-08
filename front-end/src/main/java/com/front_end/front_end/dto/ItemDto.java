package com.front_end.front_end.dto;

import java.math.BigDecimal;

public class ItemDto {

    private Long productId;

    private String productName;

    private int quantity;

    private BigDecimal unitPrice;

    public ItemDto() {}
    public ItemDto(String productName, int quantity, BigDecimal unitPrice) {

        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
