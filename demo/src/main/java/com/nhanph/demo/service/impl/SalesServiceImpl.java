package com.nhanph.demo.service.impl;

import com.nhanph.demo.entity.SalesProduct;
import com.nhanph.demo.payload.response.SalesProductResponse;
import com.nhanph.demo.repository.SalesProductRepository;
import com.nhanph.demo.service.SalesService;
import com.nhanph.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesProductRepository salesProductRepository;

    @Override
    public void saveData(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            List<SalesProduct> salesroductList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                // ignore header row
                if (lineNumber == 1) {
                    lineNumber++;
                    continue;
                }

                String[] values = line.split("\\|");

                // Process each line of the PSV file
                String salesDate = values[0];
                String storeName = values[1];
                String productName = values[2];
                int salesUnit = Integer.parseInt(values[3]);
                BigDecimal salesRevenue = new BigDecimal(values[4]);

                SalesProduct saleProduct = SalesProduct.builder()
                        .salesDate(DateUtil.format(salesDate))
                        .storeName(storeName)
                        .productName(productName)
                        .salesUnit(salesUnit)
                        .salesRevenue(salesRevenue).build();
                salesroductList.add(saleProduct);
            }

            if (!CollectionUtils.isEmpty(salesroductList)) {
                saveDataSales(salesroductList);
            }

        } catch (IOException e) {
            log.error("error file : {}", e.getMessage());
        }
    }

    private void saveDataSales(List<SalesProduct> salesProducts) {
        try {
            salesProductRepository.saveAll(salesProducts);
        } catch (Exception e) {
            log.error("error when save data", e);
        }

    }

    @Override
    public List<SalesProductResponse> getData() {
        return salesProductRepository.getData();
    }
}
