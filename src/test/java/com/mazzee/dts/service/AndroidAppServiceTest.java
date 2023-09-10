package com.mazzee.dts.service;

import org.junit.jupiter.api.Test;

class AndroidAppServiceTest {
@Test
	void testGetLatestUpdate() {
		AndroidAppService appService = new AndroidAppService();
		appService.getLatestUpdate();
		System.out.println();
	}

}
