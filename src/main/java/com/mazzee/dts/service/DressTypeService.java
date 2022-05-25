/**
 * 
 */
package com.mazzee.dts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.DressFilterDto;
import com.mazzee.dts.dto.DressTypeDto;
import com.mazzee.dts.entity.DressType;
import com.mazzee.dts.mapper.DressTypeDtoMapper;
import com.mazzee.dts.repo.DressTypeRepo;
import com.mazzee.dts.utils.DtsConstant;
import com.mazzee.dts.utils.DtsUtils;

/**
 * @author mazhar
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class DressTypeService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressTypeService.class);
	private DressTypeRepo dressTypeRepo;
	private DressTypeDtoMapper dressTypeDtoMapper;

	@Autowired
	public void setDressTypeRepo(DressTypeRepo dressTypeRepo) {
		this.dressTypeRepo = dressTypeRepo;
	}

	public List<DressType> getAllDressTypes() {
		LOGGER.info("Get all dress types ");
		List<DressType> dressTypeList = dressTypeRepo.getAllDressTypes();
		if (!DtsUtils.isNullOrEmpty(dressTypeList)) {
			LOGGER.info("Get all dress types count {}", dressTypeList.size());
		}
		return dressTypeList;
	}

	public List<DressFilterDto> getAllFilterList() {
		List<DressFilterDto> filterlist = new ArrayList<>();
		DressFilterDto dressTypeFilter = new DressFilterDto();
		dressTypeFilter.setFilterType(DtsConstant.FILTER_BY_DRESS_TYPE);
		dressTypeFilter.setFilterMessage("Filter By Dress type");
		dressTypeFilter.setTypeList(getAllDressTypes());
		filterlist.add(dressTypeFilter);

		DressFilterDto dressTypeFilterStatus = new DressFilterDto();
		dressTypeFilterStatus.setFilterType(DtsConstant.FILTER_BY_DRESS_STATUS);
		dressTypeFilterStatus.setFilterMessage("Filter By Status");
		dressTypeFilterStatus.setTypeList(getStatusList().stream().map(status -> {
			DressType dresstype = new DressType();
			dresstype.setTypeName(status);
			return dresstype;
		}).collect(Collectors.toList()));
		filterlist.add(dressTypeFilterStatus);

		DressFilterDto dressTypeFilterMonth = new DressFilterDto();
		dressTypeFilterMonth.setFilterType(DtsConstant.FILTER_BY_DRESS_MONTH);
		dressTypeFilterMonth.setFilterMessage("Filter By Month");
		dressTypeFilterMonth.setTypeList(getMonthList().stream().map(status -> {
			DressType dresstype = new DressType();
			dresstype.setTypeName(status);
			return dresstype;
		}).collect(Collectors.toList()));
		filterlist.add(dressTypeFilterMonth);

		DressFilterDto dressTypeFilterYear = new DressFilterDto();
		dressTypeFilterYear.setFilterType(DtsConstant.FILTER_BY_DRESS_YEAR);
		dressTypeFilterYear.setFilterMessage("Filter By Year");
		dressTypeFilterYear.setTypeList(getYearList().stream().map(status -> {
			DressType dresstype = new DressType();
			dresstype.setTypeName(status);
			return dresstype;
		}).collect(Collectors.toList()));
		filterlist.add(dressTypeFilterYear);
		return filterlist;
	}

	private List<String> getStatusList() {
		return Arrays.asList("BOOKED", "PAID", "UNPAID", "MEASUREMENT_DONE", "MEASUREMENT_PENDING", "DELIVERED",
				"UNDELIVERED", "CANCELED");
	}

	private List<String> getMonthList() {
		return Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
	}

	private List<String> getYearList() {
		return Arrays.asList("2018", "2019", "2020", "2021");
	}

	public DressTypeDto getDressTypeDto(DressType dressType) {
		DressTypeDto dressTypeDto = null;
		if (Objects.nonNull(dressType)) {
			dressTypeDto = dressTypeDtoMapper.getDressTypeDto(dressType);
		}
		return dressTypeDto;
	}

	public List<DressTypeDto> getDressTypeDtoList(List<DressType> dressTypeList) {
		List<DressTypeDto> dressTypeDtoList = null;
		if (!DtsUtils.isNullOrEmpty(dressTypeList)) {
			dressTypeDtoList = dressTypeList.stream().map(dressType -> dressTypeDtoMapper.getDressTypeDto(dressType))
					.collect(Collectors.toList());
		}
		return dressTypeDtoList;
	}
}
