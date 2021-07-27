package com.mazzee.dts.utils;

import java.util.Collection;
import java.util.function.Predicate;

public class DtsUtils {

	public static <T> boolean isNullOrEmpty(Collection<T> collection) {
		Predicate<Collection<T>> emptyOrNullCollectionPredicate = s -> s == null || s.isEmpty();
		return emptyOrNullCollectionPredicate.test(collection);
	}

	public static boolean isNullOrEmpty(String str) {
		Predicate<String> emptyOrNullPredicate = s -> s == null || s.isEmpty();
		return emptyOrNullPredicate.test(str);
	}
}
