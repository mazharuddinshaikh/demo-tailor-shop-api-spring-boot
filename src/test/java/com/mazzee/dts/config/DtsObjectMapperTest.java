/**
 * 
 */
package com.mazzee.dts.config;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.mazzee.dts.dto.DressDto;
import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.DressType;
import com.mazzee.dts.mapper.DtsModelMapper;

/**
 * @author Admin
 *
 */
class DtsObjectMapperTest {

	/**
	 * Test method for
	 * {@link com.mazzee.dts.config.DtsObjectMapper#getDressToDtoMapper(org.modelmapper.ModelMapper)}.
	 */
//	@Test
//	void testGetDressToDtoMapper() {
//		DtsModelMapper dtsModelMapper = new DtsModelMapper();
//		ModelMapper mapper = dtsModelMapper.getDressToDtoMapper();
//		DressType dressType = new DressType();
//		dressType.setTypeId(1002);
//		Dress dress = new Dress();
//		dress.setDressId(1);
//		dress.setDressType(dressType);
//		dress.setOrderDate(LocalDateTime.now());
//		
//		System.out.println(LocalDateTime.now());
//		DressDto dto = dtsModelMapper.
//		System.out.println(dto);
//
//	}

//	@Test
//	public void testDate() {
//		DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
//		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
////		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.US);
//		LocalDateTime currentTime = LocalDateTime.now();
////		String data = defaultFormat.format(currentTime);
//		System.out.println("Current - " + currentTime);
////		LocalDateTime convertedTime = LocalDateTime.parse(data, defaultFormat);
////		System.out.println(convertedTime);
//		String result = timeFormatter.format(currentTime);
//		System.out.println(result);
//		LocalTime date = LocalTime.parse(result, timeFormatter);
//		System.out.println(date);
//	}
//
	@Test
	public void testDateConverter() {
		DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		System.out.println(defaultFormat.format(LocalDate.now()));
	}

}
