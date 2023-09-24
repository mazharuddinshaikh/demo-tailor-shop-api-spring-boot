package com.mazzee.dts.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.entity.MeasurementImage;

@Service
public class MeasurementImageUtil {

	private MeasurementImageUtil() {
		super();
	}

	public static String getFileName(MultipartFile multipartFile, int userId, Dress dress, String measurementType,
			Measurement measurement) {
		String fileExtension = getFileExtension(multipartFile);
		String fileName = getFileNameFormat(userId, dress, measurementType);
		List<MeasurementImage> list = measurement.getMeasurementImageList();
		List<String> rawList = null;
		int i = 1;
		if (!DtsUtils.isNullOrEmpty(list)) {
			rawList = list.stream().filter(img -> img.getImageType().equalsIgnoreCase(measurementType))
					.map(img -> img.getImageName()).toList();
		}
		if (!DtsUtils.isNullOrEmpty(rawList)) {
			i = getIncrementCounter(rawList, fileName);
		}
		fileName = fileName + i;
		fileName += "." + fileExtension;
		return fileName;
	}

	private static String getFileExtension(MultipartFile multipartFile) {
		String fileExtension = null;
		String originalFileName = null;
		if (Objects.nonNull(multipartFile)) {
			originalFileName = multipartFile.getOriginalFilename();
		}
		if (!DtsUtils.isNullOrEmpty(originalFileName)) {
			fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		}
		return fileExtension;
	}

	private static String getFileNameFormat(int userId, Dress dress, String measuremetnType) {
		final String currentDate = DtsUtils.convertDateToString(LocalDate.now(), DtsUtils.DATE_FORMAT_2);
		return DtsConstant.USER + userId + DtsConstant.UNDERSCORE + DtsConstant.CUSTOMER
				+ dress.getCustomer().getCustomerId() + DtsConstant.UNDERSCORE + DtsConstant.DRESS + dress.getDressId()
				+ DtsConstant.UNDERSCORE + DtsConstant.FILTER_BY_DRESS_TYPE + dress.getUserDressType().getId()
				+ DtsConstant.UNDERSCORE + measuremetnType + DtsConstant.UNDERSCORE + currentDate
				+ DtsConstant.UNDERSCORE;
	}

	private static int getIncrementCounter(List<String> rawImageList, String fileName) {
		int incrementCounter = 1;
		boolean isIncreasing = false;
		if (!DtsUtils.isNullOrEmpty(rawImageList)) {
			do {
				incrementCounter++;
				String tempFileName = fileName + incrementCounter;
				boolean nameFound = rawImageList.stream().anyMatch(img -> img.contains(tempFileName));
				isIncreasing = nameFound;
			} while (isIncreasing);
		}
		return incrementCounter;
	}
}
