package com.nhanph.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "sales_product")
public class SalesProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sales_date")
    private LocalDate salesDate;

    @Column(name = "store_name", length = 45)
    private String storeName;

    @Column(name = "product_name", length = 45)
    private String productName;

    @Column(name = "sales_unit")
    private Integer salesUnit;

    @Column(name = "sales_revenue", precision = 10)
    private BigDecimal salesRevenue;

}