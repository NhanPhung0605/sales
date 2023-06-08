CREATE TABLE `sales_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sales_date` date DEFAULT NULL,
  `store_name` varchar(200) DEFAULT NULL,
  `product_name` varchar(200) DEFAULT NULL,
  `sales_unit` int DEFAULT NULL,
  `sales_revenue` decimal(10,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=639 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci