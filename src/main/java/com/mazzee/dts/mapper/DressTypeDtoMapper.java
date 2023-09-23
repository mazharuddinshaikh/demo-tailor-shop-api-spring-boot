package com.mazzee.dts.mapper;

import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.DressTypeDto;
import com.mazzee.dts.entity.DressType;
import com.mazzee.dts.entity.User;

@Service
public class DressTypeDtoMapper {

	private ModelMapper getDressTypeToDtoMapper() {
		ModelMapper modelMapper = new ModelMapper();

		Converter<User, Integer> userToIdConverter = context -> {
			User user = context.getSource();
			int userId = 0;
			if (Objects.nonNull(user)) {
				userId = user.getUserId();
			}
			return userId;
		};

		modelMapper.typeMap(DressType.class, DressTypeDto.class).addMappings(mapper -> {
			mapper.map(DressType::getTypeId, DressTypeDto::setTypeId);
			mapper.map(DressType::getTypeName, DressTypeDto::setTypeName);
			mapper.map(DressType::getTypeDescription, DressTypeDto::setTypeDescription);
			mapper.map(DressType::getComment, DressTypeDto::setComment);
			mapper.using(userToIdConverter).map(DressType::getUser, DressTypeDto::setCreatedBy);
			mapper.map(DressType::getCreatedAt, DressTypeDto::setCreatedAt);
			mapper.map(DressType::getUpdatedAt, DressTypeDto::setUpdatedAt);
		});
		return modelMapper;
	}

	public DressTypeDto getDressTypeDto(DressType dressType) {
		ModelMapper modelMapper = getDressTypeToDtoMapper();
		DressTypeDto dressTypeDto = null;
		if (Objects.nonNull(dressType)) {
			dressTypeDto = modelMapper.map(dressType, DressTypeDto.class);
		}
		return dressTypeDto;
	}
}
