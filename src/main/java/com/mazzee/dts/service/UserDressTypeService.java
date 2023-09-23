package com.mazzee.dts.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.UserDressTypeDto;
import com.mazzee.dts.entity.DressType;
import com.mazzee.dts.entity.User;
import com.mazzee.dts.entity.UserDressType;
import com.mazzee.dts.mapper.UserDressTypeDtoMapper;
import com.mazzee.dts.repo.UserDressTypeRepo;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class UserDressTypeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDressTypeService.class);
	private UserDressTypeRepo userDressTypeRepo;
	private UserDressTypeDtoMapper userDressTypeDtoMapper;
	private DressTypeService dressTypeService;
	private UserService userService;

	@Autowired
	public void setUserDressTypeRepo(UserDressTypeRepo userDressTypeRepo) {
		this.userDressTypeRepo = userDressTypeRepo;
	}

	@Autowired
	public void setUserDressTypeRepo(UserDressTypeDtoMapper userDressTypeDtoMapper) {
		this.userDressTypeDtoMapper = userDressTypeDtoMapper;
	}

	@Autowired
	public void setDressTypeService(DressTypeService dressTypeService) {
		this.dressTypeService = dressTypeService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
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

	public List<UserDressType> addAllDefaultUserDressType(int userId) {
		LOGGER.info("Adding user dress types for user id {}", userId);
		Optional<User> userOptional = userService.getUserByUserId(userId);
		List<DressType> dressTypeList = dressTypeService.getAllDressTypes();
		List<UserDressType> userDressTypeList = null;
		if (!DtsUtils.isNullOrEmpty(dressTypeList) && userOptional.isPresent()) {
			userDressTypeList = getDefaultUserDressTypeList(dressTypeList, userOptional.get());
		}
		if (!DtsUtils.isNullOrEmpty(userDressTypeList)) {
			userDressTypeList = userDressTypeRepo.saveAll(userDressTypeList);
		}
		return userDressTypeList;
	}

	private List<UserDressType> getDefaultUserDressTypeList(List<DressType> dressTypeList, User user) {
		List<UserDressType> userDressTypeList = new ArrayList<>();
		for (DressType dressType : dressTypeList) {
			UserDressType userDressType = new UserDressType();
			userDressType.setDressType(dressType);
			userDressType.setUser(user);
			userDressType.setCreatedAt(LocalDateTime.now());
			userDressType.setUpdatedAt(LocalDateTime.now());
			userDressType.setPrice(0.0);
			userDressTypeList.add(userDressType);
		}
		return userDressTypeList;

	}

	public List<UserDressTypeDto> getUserDressTypeDtoList(List<UserDressType> userDressTypeList) {
		List<UserDressTypeDto> userDressTypeDtoList = null;
		if (!DtsUtils.isNullOrEmpty(userDressTypeList)) {
			userDressTypeDtoList = userDressTypeList.stream().map(this::getUserDressTypeDto)
					.toList();
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
