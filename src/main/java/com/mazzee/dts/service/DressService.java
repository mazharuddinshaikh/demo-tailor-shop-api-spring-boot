package com.mazzee.dts.service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.Dress;
import com.mazzee.dts.repo.DressRepo;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class DressService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressService.class);
	private DressRepo dressRepo;

	@Autowired
	public void setDressRepo(DressRepo dressRepo) {
		this.dressRepo = dressRepo;
	}

	public List<Dress> getDressList(String dressType) {
		LOGGER.info("Get dress lis for dress type {}", dressType);
		List<Dress> dressList = null;
		if (Objects.isNull(dressType) || dressType.isEmpty()) {
			dressList = dressRepo.getAllDress();
		} else {
			dressList = dressRepo.getAllDressByDressType(dressType);
		}
		if (!DtsUtils.isNullOrEmpty(dressList)) {
			LOGGER.info("Dress list count {}", dressList.size());
		} else {
			LOGGER.info("No Dress list found for dress type {}", dressType);
		}
		return dressList;
	}

	public void testMethod() {
		Function.identity();
	}

}
