package com.mazzee.dts.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AndroidAppServiceTest {

	@Test
	void testGetLatestUpdate() {
		AndroidAppService appService = new AndroidAppService();
		appService.getLatestUpdate();
	}

}
