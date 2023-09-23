package com.mazzee.dts.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.AndroidAppDto;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class AndroidAppService {
	private final static Logger LOGGER = LoggerFactory.getLogger(AndroidAppService.class);

	@Value("${dts.android.app-update-properties}")
	private String appAupdatePropertiesPath;

	public void setAppAupdatePropertiesPath(String appAupdatePropertiesPath) {
		this.appAupdatePropertiesPath = appAupdatePropertiesPath;
	}

	public AndroidAppDto getLatestUpdate() {
		AndroidAppDto androidAppDto = null;
		Path path = Paths.get(appAupdatePropertiesPath);
		File file = path.toFile();
		InputStream inputStream = null;
		Properties properties = null;
		if (Files.exists(path)) {
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				LOGGER.error("Exception occurred with inputstream ", e1);
			}
		} else {
			LOGGER.info("Androdi app update file not present");
		}
		if (Objects.nonNull(inputStream)) {
			properties = new Properties();
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				LOGGER.error("Exception occurred with inputstream ", e);
			}
		}
		if (Objects.nonNull(properties) && !properties.isEmpty()) {
			androidAppDto = getAndroidAppDto(properties);
		}
		return androidAppDto;
	}

	private AndroidAppDto getAndroidAppDto(Properties properties) {
		AndroidAppDto androidAppDto = new AndroidAppDto();
		String updateTitle = null;
		String updateMessage = null;
		String updateVersion = null;
		boolean isUpdateCompulsory = false;
		List<String> whatsNewList = null;
		String whatsNew = null;
		List<String> improvementList = null;
		String improvement = null;
		String updateCompulsory = null;

		updateTitle = properties.getProperty("updateTitle");
		updateMessage = properties.getProperty("updateMessage");
		updateVersion = properties.getProperty("updateVersion");
		updateCompulsory = properties.getProperty("isUpdateCompulsory");
		if (!DtsUtils.isNullOrEmpty(updateCompulsory) && updateCompulsory.equalsIgnoreCase("true")) {
			isUpdateCompulsory = true;
		}
		whatsNew = properties.getProperty("whatsNewList");
		whatsNewList = getSplittedList(whatsNew);
		improvement = properties.getProperty("improvementList");
		improvementList = getSplittedList(improvement);

		androidAppDto.setUpdateTitle(updateTitle);
		androidAppDto.setUpdateMessage(updateMessage);
		androidAppDto.setUpdateVersion(updateVersion);
		androidAppDto.setUpdateCompulsory(isUpdateCompulsory);
		androidAppDto.setWhatsNewList(whatsNewList);
		androidAppDto.setImprovementList(improvementList);

		return androidAppDto;
	}

	private List<String> getSplittedList(final String result) {
		List<String> splittedList = null;
		if (!DtsUtils.isNullOrEmpty(result)) {
			splittedList = DtsUtils.splitToList(result, ",");
		}
		if (!DtsUtils.isNullOrEmpty(splittedList)) {
			splittedList = splittedList.stream().map(DtsUtils::trim).collect(Collectors.toList());
		}
		return splittedList;
	}
}
