package com.nhanph.demo.payload.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalesProductResponse {
    private Long id;
    private LocalDate saleDate;
    private String storeName;
    private String productName;
    private Long salesUnit;
    private BigDecimal salesRevenue;

    public SalesProductResponse(String storeName, String productName, Long salesUnit, BigDecimal salesRevenue) {
        this.storeName = storeName;
        this.productName = productName;
        this.salesUnit = salesUnit;
        this.salesRevenue = salesRevenue;
    }
}


