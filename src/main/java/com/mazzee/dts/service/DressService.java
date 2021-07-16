package com.mazzee.dts.service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.Dress;
import com.mazzee.dts.repo.DressRepo;

@Service
public class DressService {
	private DressRepo dressRepo;

	@Autowired
	public void setDressRepo(DressRepo dressRepo) {
		this.dressRepo = dressRepo;
	}

	public List<Dress> getDressList(String dressType) {
		System.out.println("Service started");
		List<Dress> dressList = null;
		if(Objects.isNull(dressType) || dressType.isEmpty()) {
			dressList = dressRepo.getAllDress();
		} else {
			dressList = dressRepo.getAllDressByDressType(dressType);
		}
		System.out.println("Service closed");
		return dressList;
	}
	
	public void testMethod() {
		Function.identity();
	}

}
