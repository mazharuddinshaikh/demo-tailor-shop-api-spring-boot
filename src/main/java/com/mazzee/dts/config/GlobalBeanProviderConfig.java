package com.mazzee.dts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mazzee.dts.jwt.JwtTokenUtils;

/**
 * Use to declare all beans
 * 
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Configuration
public class GlobalBeanProviderConfig {

	/**
	 * @return instance of <code>JwtTokenUtils</code>
	 */
	@Bean
	public JwtTokenUtils jwtTokenUtils() {
		return new JwtTokenUtils();
	}

}
