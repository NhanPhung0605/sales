package com.nhanph.demo.repository;

import com.nhanph.demo.entity.SalesProduct;
import com.nhanph.demo.payload.response.SalesProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesProductRepository extends JpaRepository<SalesProduct, Long> {

    @Query("SELECT new com.nhanph.demo.payload.response.SalesProductResponse(sp.storeName, sp.productName, SUM(sp.salesUnit), SUM(sp.salesRevenue)) FROM SalesProduct sp GROUP BY sp.storeName, sp.productName ORDER BY sp.productName ASC")
    List<SalesProductResponse> getData();
}