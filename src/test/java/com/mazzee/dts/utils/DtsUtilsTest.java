package com.mazzee.dts.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class DtsUtilsTest {

	@Test
	void testIsNullOrEmptyCollectionOfE() {
	}

	@Test
	void testIsNullOrEmptyString() {
	}

	@Test
	void testIsNullOrEmptyStringArray() {
	}

	@Test
	void testIsNullOrEmptyMapOfKV() {
		Map<String, File> map = new HashMap<>();
		boolean result = DtsUtils.isNullOrEmpty(map);
		assertTrue(result);
	}

	@Test
	void testGetResultFromFunction() {
	}

	@Test
	void testIsValidEmail() {
	}

}
