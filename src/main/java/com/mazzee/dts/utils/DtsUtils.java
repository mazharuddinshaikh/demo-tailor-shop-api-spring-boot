package com.mazzee.dts.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public final class DtsUtils {
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	public static final int MINIMUM_PASSWORD_LENGTH = 6;
	public static final String DATE_FORMAT_1 = "dd/MMM/yyyy"; 
	public static final String DATE_FORMAT_2 = "yyyyMMdd";

	public static <E> boolean isNullOrEmpty(Collection<E> collection) {
		return getResultFromFunction(collection, s -> Objects.isNull(s) || s.isEmpty());
	}

	public static boolean isNullOrEmpty(String str) {
		return getResultFromFunction(str, s -> Objects.isNull(s) || s.isEmpty());
	}

	public static boolean isNullOrEmpty(String[] str) {
		return getResultFromFunction(str, s -> Objects.isNull(s) || s.length == 0);
	}

	public static <K, V> boolean isNullOrEmpty(Map<K, V> map) {
		return getResultFromFunction(map, s -> Objects.isNull(s) || s.isEmpty());
	}

	public static String trim(String str) {
		return getResultFromFunction(str, String::trim);
	}

	public static List<String> splitToList(String data, String delimeter) {
		List<String> splittedList = null;
		if (!isNullOrEmpty(data)) {
			splittedList = getResultFromFunction(data, s -> Arrays.asList(s.split(delimeter)));
		}
		return splittedList;
	}

	public static boolean isValidEmail(String email) {
		boolean isValidEmail = false;
		if (!isNullOrEmpty(email)) {
			Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
			Matcher emailMatcher = emailPattern.matcher(email);
			isValidEmail = emailMatcher.matches();
		}
		return isValidEmail;
	}

	public static <S, V> V getResultFromFunction(S value, Function<S, V> functionResolver) {
		return functionResolver.apply(value);
	}

	public static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

	public static String getImagePath() {
		LocalDateTime now = getCurrentDateTime();
		return now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/" + now.getHour();
	}

	public static String getImagePath(String... pathList) {
		String path = "";
		if (isNullOrEmpty(pathList)) {
			return getImagePath();
		}
		for (String s : pathList) {
			path += s + "/";
		}
		path += getImagePath();
		return path;
	}

	public static LocalDateTime convertStringToDate(String date, String format) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		LocalDate parsedLocalDate = LocalDate.parse(date, dateTimeFormatter);
		LocalDateTime localDateTime = LocalDateTime.of(parsedLocalDate, getCurrentDateTime().toLocalTime());
		return localDateTime;
	}

	
	public static String convertDateToString(LocalDate localDate, String format) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
		final String currentDate = dateFormat.format(localDate);
		return currentDate;
	}
	
	public static List<MultipartFile> getMeasurementMultipartFiles(List<MultipartFile> files, String startName) {
		return files.stream().filter(file -> file.getOriginalFilename().startsWith(startName))
				.collect(Collectors.toList());
	}
}
