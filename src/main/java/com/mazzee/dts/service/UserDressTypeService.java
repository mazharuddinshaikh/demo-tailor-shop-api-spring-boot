package com.mazzee.dts.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.UserDressTypeDto;
import com.mazzee.dts.entity.UserDressType;
import com.mazzee.dts.mapper.UserDressTypeDtoMapper;
import com.mazzee.dts.repo.UserDressTypeRepo;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class UserDressTypeService {
	private final static Logger LOGGER = LoggerFactory.getLogger(UserDressTypeService.class);
	private UserDressTypeRepo userDressTypeRepo;
	private UserDressTypeDtoMapper userDressTypeDtoMapper;

	@Autowired
	public void setUserDressTypeRepo(UserDressTypeRepo userDressTypeRepo) {
		this.userDressTypeRepo = userDressTypeRepo;
	}

	@Autowired
	public void setUserDressTypeRepo(UserDressTypeDtoMapper userDressTypeDtoMapper) {
		this.userDressTypeDtoMapper = userDressTypeDtoMapper;
	}

	public List<UserDressTypeDto> getUserDressTypeListByUserId(int userId) {
		LOGGER.info("Get user dress type by user id {}", userId);
		List<UserDressType> userDressTypeList = userDressTypeRepo.getDressTypeListByUserId(userId);
		List<UserDressTypeDto> userDressTypeDtoList = null;
		if (!DtsUtils.isNullOrEmpty(userDressTypeList)) {
			userDressTypeDtoList = getUserDressTypeDtoList(userDressTypeList);
		}
		return userDressTypeDtoList;
	}

	public List<UserDressTypeDto> getUserDressTypeDtoList(List<UserDressType> userDressTypeList) {
		List<UserDressTypeDto> userDressTypeDtoList = null;
		if (!DtsUtils.isNullOrEmpty(userDressTypeList)) {
			userDressTypeDtoList = userDressTypeList.stream().map(userDressType -> getUserDressTypeDto(userDressType))
					.collect(Collectors.toList());
		}
		return userDressTypeDtoList;
	}

	public UserDressTypeDto getUserDressTypeDto(UserDressType userDressType) {
		UserDressTypeDto userDressTypeDto = null;
		if (Objects.nonNull(userDressType)) {
			userDressTypeDto = userDressTypeDtoMapper.getUserDressTypeDto(userDressType);
		}
		return userDressTypeDto;
	}

	public Optional<UserDressType> getUserDressTypeByUserDressTypeId(int userDressTypeId) {
		return userDressTypeRepo.getUserDressTypeByTypeId(userDressTypeId);
	}

	public UserDressType updateUserDressType(UserDressTypeDto userDressTypeDto) {

		UserDressType userDressType = null;
		Optional<UserDressType> optionalUserDressType = getUserDressTypeByUserDressTypeId(
				userDressTypeDto.getUserDressTypeId());
		if (optionalUserDressType.isPresent()) {
			userDressType = optionalUserDressType.get();
			LOGGER.info("Updating user dress type {}", userDressType.getId());
			userDressType.setPrice(userDressTypeDto.getPrice());
			userDressType.setUpdatedAt(LocalDateTime.now());
			userDressType = userDressTypeRepo.save(userDressType);
		}
		return userDressType;
	}

}
