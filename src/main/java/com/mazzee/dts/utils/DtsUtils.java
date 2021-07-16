package com.mazzee.dts.utils;

import java.util.Collection;
import java.util.function.Predicate;

public class DtsUtils {

	Predicate<String> emptyOrNullPredicate = s -> s == null || s.length() == 0;
	Predicate<Collection<Object>> emptyOrNullCollectionPredicate = s -> s == null || s.isEmpty();
}
