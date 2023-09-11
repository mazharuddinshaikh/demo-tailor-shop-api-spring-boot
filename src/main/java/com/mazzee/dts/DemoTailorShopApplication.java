package com.mazzee.dts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.mazzee.dts.aws.AwsS3Util;

/**
 * Main class use to start the application
 * 
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@SpringBootApplication
public class DemoTailorShopApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoTailorShopApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Demo tailor application started");
		SpringApplication.run(DemoTailorShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
