package com.mazzee.dts.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileOperationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileOperationService.class);

	public boolean uploadFile(String path, String fileName, MultipartFile multipartFile) {
		boolean isFileUploaded = false;
		try {
			Files.createDirectories(Paths.get(path));
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Files.copy(inputStream, Paths.get(path, fileName), StandardCopyOption.REPLACE_EXISTING);
				isFileUploaded = true;
			}
		} catch (IOException e) {
			LOGGER.error("Exception occured while creating folder {}", path);
		}

		return isFileUploaded;
	}
}
