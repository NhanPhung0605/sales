package com.nhanph.demo.service;

import com.nhanph.demo.payload.response.SalesProductResponse;

import java.io.File;
import java.util.List;

public interface SalesService {
    void saveData(File file);

    List<SalesProductResponse> getData();
}
