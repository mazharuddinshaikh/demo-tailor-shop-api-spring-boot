package com.mazzee.dts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoTailorShopApplication {
	private final static Logger LOGGER = LoggerFactory.getLogger(DemoTailorShopApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Demo tailor application started");
		SpringApplication.run(DemoTailorShopApplication.class, args);
	}

}
