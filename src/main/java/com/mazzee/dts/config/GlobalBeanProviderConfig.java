package com.mazzee.dts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mazzee.dts.utils.JwtTokenUtils;

@Configuration
public class GlobalBeanProviderConfig {

	@Bean
	public JwtTokenUtils jwtTokenUtils() {
		return new JwtTokenUtils();
	}

}
