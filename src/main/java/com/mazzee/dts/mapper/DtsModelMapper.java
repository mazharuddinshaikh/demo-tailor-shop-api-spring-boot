/**
 * 
 */
package com.mazzee.dts.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import org.modelmapper.Converter;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 *
 */

@Service
public class DtsModelMapper {

	public Converter<LocalDateTime, String> getDateTimeConverter() {
		DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm:ss a");
		return conetxt -> {
			String localDateTime = null;
			if (Objects.nonNull(conetxt.getSource())) {
				localDateTime = defaultFormat.format(conetxt.getSource());
			}
			return localDateTime;
		};
	}

	public Converter<LocalDateTime, String> getDateConverter() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.US);
		return conetxt -> {
			String localDate = null;
			if (Objects.nonNull(conetxt.getSource())) {
				localDate = dateFormatter.format(conetxt.getSource());

			}
			return localDate;
		};
	}

	public Converter<LocalDateTime, String> getTimeConverter() {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
		return conetxt -> {
			String localTime = null;
			if (Objects.nonNull(conetxt.getSource())) {
				localTime = timeFormatter.format(conetxt.getSource());
			}
			return localTime;
		};
	}
}
