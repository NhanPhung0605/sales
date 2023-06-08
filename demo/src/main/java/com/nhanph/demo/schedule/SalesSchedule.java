package com.nhanph.demo.schedule;

import com.nhanph.demo.payload.response.SalesProductResponse;
import com.nhanph.demo.producer.SalesProductProducer;
import com.nhanph.demo.service.SalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class SalesSchedule {

    private int lineNumber = 0;

    @Value("${application.schedule.path}")
    private String path;

    private final SalesService salesService;

    private final SalesProductProducer salesProductProducer;

    @Scheduled(cron = "${application.schedule.sale}")
    private void initData() {
        log.info("schedule send file ");

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            lineNumber++;

            List<File> files = paths.filter(Files::isRegularFile).map(Path::toFile)
                    .filter(file -> file.getName().endsWith(".psv")).collect(Collectors.toList());

            int key = 0;
            Map<Integer, File> map = new HashMap<>();

            for (File file : files) {
                key++;
                map.put(key, file);
            }

            File file = map.get(lineNumber);
            if (file != null) {
                salesService.saveData(file);
            }

            //reset lineNumber to repeat schedule
            if (lineNumber == files.size()) {
                lineNumber = 0;
            }

            List<SalesProductResponse> responses = salesService.getData();
            salesProductProducer.sendSalesProduct(responses);

        } catch (IOException e) {
            log.error("error file ", e);
        }
    }


}
