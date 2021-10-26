package com.mazzee.dts.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class DtsUtils {

	public static <E> boolean isNullOrEmpty(Collection<E> collection) {
		return getResultFromFunction(collection, s -> Objects.isNull(s) || s.isEmpty());
	}

	public static boolean isNullOrEmpty(String str) {
		return getResultFromFunction(str, s -> Objects.isNull(s) || s.isEmpty());
	}

	public static <S, V> V getResultFromFunction(S value, Function<S, V> functionResolver) {
		return functionResolver.apply(value);
	}
}
