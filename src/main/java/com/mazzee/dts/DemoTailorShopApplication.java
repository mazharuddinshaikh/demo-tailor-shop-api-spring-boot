package com.mazzee.dts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.mazzee.dts.aws.AwsS3Util;

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
	private final static Logger LOGGER = LoggerFactory.getLogger(DemoTailorShopApplication.class);

	private AwsS3Util awsS3Util;

	@Autowired
	public void setAwsS3Util(AwsS3Util awsS3Util) {
		this.awsS3Util = awsS3Util;
	}

	public static void main(String[] args) {
		LOGGER.info("Demo tailor application started");
		SpringApplication.run(DemoTailorShopApplication.class, args);
	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(DemoTailorShopApplication.class);
//	}

	@Override
	public void run(String... args) throws Exception {
//		awsS3Util.listBucket();
//		awsS3Util.readFile();
//		awsS3Util.uploadFile();
//		awsS3Util.deleteFile();
	}

}
