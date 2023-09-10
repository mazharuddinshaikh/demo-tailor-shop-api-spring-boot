package com.mazzee.dts.utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.entity.MeasurementImage;

@Service
public class MeasurementImageUtil {

	public static String getFileName(MultipartFile multipartFile, int userId, Dress dress, String measurementType,
			Measurement measurement) {
		String fileExtension = getFileExtension(multipartFile);
		String fileName = getFileNameFormat(userId, dress, measurementType);
		List<MeasurementImage> list = measurement.getMeasurementImageList();
		List<String> rawList = list.stream().filter(img -> img.getImageType().equalsIgnoreCase(measurementType))
				.map(img -> img.getImageName()).toList();
		int i = getIncrementCounter(rawList, fileName);
		fileName = fileName + i;
		fileName += "." + fileExtension;
		return fileName;
	}

	private static String getFileExtension(MultipartFile multipartFile) {
		return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
	}

	private static String getFileNameFormat(int userId, Dress dress, String measuremetnType) {
		final String currentDate = DtsUtils.convertDateToString(LocalDate.now(), DtsUtils.DATE_FORMAT_2);
		String fileName = DtsConstant.USER + userId + DtsConstant.UNDERSCORE + DtsConstant.CUSTOMER
				+ dress.getCustomer().getCustomerId() + DtsConstant.UNDERSCORE + DtsConstant.DRESS + dress.getDressId()
				+ DtsConstant.UNDERSCORE + DtsConstant.FILTER_BY_DRESS_TYPE + dress.getUserDressType().getId()
				+ DtsConstant.UNDERSCORE + measuremetnType + DtsConstant.UNDERSCORE + currentDate
				+ DtsConstant.UNDERSCORE;
		return fileName;
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
