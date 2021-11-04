package com.mazzee.dts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.Dress;
import com.mazzee.dts.repo.DressRepo;
import com.mazzee.dts.utils.DtsUtils;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class DressService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressService.class);
	private DressRepo dressRepo;

	@Autowired
	public void setDressRepo(DressRepo dressRepo) {
		this.dressRepo = dressRepo;
	}

	public List<Dress> getDressList(String dressType) {

		List<Dress> dressList = null;
		if (DtsUtils.isNullOrEmpty(dressType)) {
			LOGGER.info("Get dress list");
			dressList = dressRepo.getAllDress();
		} else {
			LOGGER.info("Get dress list for dress type {}", dressType);
			dressList = dressRepo.getAllDressByDressType(dressType);
		}
		return dressList;
	}

}
