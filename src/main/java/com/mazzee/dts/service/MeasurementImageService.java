package com.mazzee.dts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mazzee.dts.dto.DtsImageProperties;
import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.entity.MeasurementImage;
import com.mazzee.dts.repo.MeasurementImageRepo;
import com.mazzee.dts.utils.DtsConstant;
import com.mazzee.dts.utils.DtsUtils;
import com.mazzee.dts.utils.MeasurementImageUtil;

@Service
public class MeasurementImageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementImageService.class);
	private MeasurementImageRepo measurementImageRepo;
	private DtsImageProperties dtsImageProperties;
	private FileOperationService fileOperationService;

	@Autowired
	public void setMeasurementImageRepo(MeasurementImageRepo measurementImageRepo) {
		this.measurementImageRepo = measurementImageRepo;
	}

	@Autowired
	public void setDtsImageProperties(DtsImageProperties dtsImageProperties) {
		this.dtsImageProperties = dtsImageProperties;
	}

	@Autowired
	public void setFileOperationService(FileOperationService fileOperationService) {
		this.fileOperationService = fileOperationService;
	}

	
	public List<MeasurementImage> getMeasurementImageListByMeasurement(Measurement measurement) {
		return measurementImageRepo.findByMeasurement(measurement);
	}

	public void uploadMeasurementImage(List<MultipartFile> files, int userId, Dress dress) {
		Measurement measurement = dress.getMeasurement();
		List<MultipartFile> rawImageList = DtsUtils.getMeasurementMultipartFiles(files, DtsConstant.DRESS
				+ DtsConstant.UNDERSCORE + dress.getDressId() + DtsConstant.UNDERSCORE + DtsConstant.RAW);
		List<MultipartFile> patternImageList = DtsUtils.getMeasurementMultipartFiles(files, DtsConstant.DRESS
				+ DtsConstant.UNDERSCORE + dress.getDressId() + DtsConstant.UNDERSCORE + DtsConstant.PATTERN);
		List<MultipartFile> seavedImageList = DtsUtils.getMeasurementMultipartFiles(files, DtsConstant.DRESS
				+ DtsConstant.UNDERSCORE + dress.getDressId() + DtsConstant.UNDERSCORE + DtsConstant.SEAVED);
		List<MultipartFile> measurementImageList = DtsUtils.getMeasurementMultipartFiles(files, DtsConstant.DRESS
				+ DtsConstant.UNDERSCORE + dress.getDressId() + DtsConstant.UNDERSCORE + DtsConstant.MEASUREMENT);
		LOGGER.info("Update images measurement for measurement id {}", measurement.getMeasurementId());
		if (!DtsUtils.isNullOrEmpty(rawImageList)) {
			uploadMeasurementFile(rawImageList, userId, dress, DtsConstant.RAW, measurement);
		}
		if (!DtsUtils.isNullOrEmpty(patternImageList)) {
			uploadMeasurementFile(patternImageList, userId, dress, DtsConstant.PATTERN, measurement);
		}
		if (!DtsUtils.isNullOrEmpty(seavedImageList)) {
			uploadMeasurementFile(seavedImageList, userId, dress, DtsConstant.SEAVED, measurement);
		}
		if (!DtsUtils.isNullOrEmpty(measurementImageList)) {
			uploadMeasurementFile(measurementImageList, userId, dress, DtsConstant.MEASUREMENT, measurement);
		}
	}

	private void uploadMeasurementFile(List<MultipartFile> multipartFileList, int userId, Dress dress,
			String measurementType, Measurement measurement) {
		for (MultipartFile multipartFile : multipartFileList) {
			uploadMeasurementFile(multipartFile, userId, dress, measurementType, measurement);
		}
	}

	private void uploadMeasurementFile(MultipartFile multipartFile, int userId, Dress dress, String measurementType,
			Measurement measurement) {
		String fileName = MeasurementImageUtil.getFileName(multipartFile, userId, dress, measurementType, measurement);
		String uploadPath = DtsUtils.getImagePath(dtsImageProperties.getSource(), dtsImageProperties.getBaseFolder());
		String imagePath = DtsUtils.getImagePath();
		fileOperationService.uploadFile(uploadPath, fileName, multipartFile);
		MeasurementImage image = getNewMeasurementIMageInstance(measurement, fileName, imagePath, measurementType);
		saveMeasurementImage(image);
	}

	public MeasurementImage saveMeasurementImage(MeasurementImage measurementImage) {
		return measurementImageRepo.save(measurementImage);
	}

	public MeasurementImage getNewMeasurementIMageInstance(Measurement measurement, String fileName, String imagePath,
			String imageType) {
		MeasurementImage image = new MeasurementImage();
		image.setMeasurement(measurement);
		image.setImageName(fileName);
		image.setImagePath(imagePath);
		image.setImageType(imageType);
		image.setIsActive("Y");
		image.setCreatedAt(DtsUtils.getCurrentDateTime());
		image.setUpdatedAt(DtsUtils.getCurrentDateTime());
		return image;
	}

}
