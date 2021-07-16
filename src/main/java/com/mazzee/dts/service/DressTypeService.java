/**
 * 
 */
package com.mazzee.dts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.DressType;
import com.mazzee.dts.repo.DressTypeRepo;

/**
 * @author mazhar
 *
 */
@Service
public class DressTypeService {
	private DressTypeRepo dressTypeRepo;

	@Autowired
	public void setDressTypeRepo(DressTypeRepo dressTypeRepo) {
		this.dressTypeRepo = dressTypeRepo;
	}

	public List<DressType> getAllDressTypes() {
		List<DressType> dressTypeList = dressTypeRepo.getAllDressTypes();
		return dressTypeList ;
	}

}
