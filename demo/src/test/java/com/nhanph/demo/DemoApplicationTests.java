package com.nhanph.demo;

import com.nhanph.demo.entity.SalesProduct;
import com.nhanph.demo.payload.response.SalesProductResponse;
import com.nhanph.demo.repository.SalesProductRepository;
import com.nhanph.demo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class DemoApplicationTests {

	private final SalesProductRepository salesProductRepository;

	@Autowired
	DemoApplicationTests(SalesProductRepository salesProductRepository) {
		this.salesProductRepository = salesProductRepository;
	}

	@Test
	void saveData() {
		//error sql exception when save error sales data
		SalesProduct saleProduct = SalesProduct.builder()
				.salesDate(DateUtil.format("22"))
				.storeName("33")
				.productName("22")
				.salesUnit(1)
				.salesRevenue(new BigDecimal(123123)).build();
		salesProductRepository.save(saleProduct);
	}

	@Test
	void getData() {
		List<SalesProductResponse> response = salesProductRepository.getData();
		log.info("response:  {} ", response);
	}

	@Test
	void testNoFileFound() {
		try (Stream<Path> pathStream = Files.walk(Paths.get("/test"))) {
			log.info("file found : {}", pathStream);
		} catch (IOException e) {
			log.error("not file found : {}", e.getMessage());
		}
	}

	@Test
	void testNotFilePSV() {
		try (Stream<Path> paths = Files.walk(Paths.get("D:/Projects/demo/src/main/resources"))) {

			List<File> files = paths.filter(Files::isRegularFile).map(Path::toFile)
					.filter(file -> file.getName().endsWith(".psv")).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(files)) {
				log.error("error not found psv file");
			}

		} catch (IOException e) {
			log.error("error file ", e);
		}
	}

}
