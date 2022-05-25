package com.mazzee.dts.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public final class DtsUtils {
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	public static final int MINIMUM_PASSWORD_LENGTH = 6;

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

	public static <S, V> V getResultFromFunction(S value, Function<S, V> functionResolver) {
		return functionResolver.apply(value);
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

}
