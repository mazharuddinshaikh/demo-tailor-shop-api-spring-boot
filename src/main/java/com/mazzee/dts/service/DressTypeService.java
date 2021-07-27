/**
 * 
 */
package com.mazzee.dts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.controller.DressTypeController;
import com.mazzee.dts.dto.DressType;
import com.mazzee.dts.repo.DressTypeRepo;
import com.mazzee.dts.utils.DtsUtils;

/**
 * @author mazhar
 *
 */
@Service
public class DressTypeService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressTypeService.class);
	private DressTypeRepo dressTypeRepo;

	@Autowired
	public void setDressTypeRepo(DressTypeRepo dressTypeRepo) {
		this.dressTypeRepo = dressTypeRepo;
	}

	public List<DressType> getAllDressTypes() {
		List<DressType> dressTypeList = dressTypeRepo.getAllDressTypes();
		if (!DtsUtils.isNullOrEmpty(dressTypeList)) {
			LOGGER.info("Get Dress types count {}", dressTypeList.size());
		}
		return dressTypeList;
	}

}
