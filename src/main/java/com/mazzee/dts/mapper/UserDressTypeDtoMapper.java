package com.mazzee.dts.mapper;

import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.UserDressTypeDto;
import com.mazzee.dts.entity.DressType;
import com.mazzee.dts.entity.User;
import com.mazzee.dts.entity.UserDressType;

@Service
public class UserDressTypeDtoMapper {
	private final DtsModelMapper dtsModelMapper;

//
	@Autowired
	public UserDressTypeDtoMapper(DtsModelMapper dtsModelMapper) {
		this.dtsModelMapper = dtsModelMapper;
	}

	private ModelMapper getUserDressTypeToDtoMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

//		Converter<DressType, Integer> dressTypeIdConverter = new Converter<DressType, Integer>() {
//
//			@Override
//			public Integer convert(MappingContext<DressType, Integer> context) {
//				// TODO Auto-generated method stub
//				DressType dressType = context.getSource();
//				return dressType.getTypeId();
//			}
//		};

		Converter<User, Integer> userToIdConverter = context -> {
			User user = context.getSource();
			int userId = 0;
			if (Objects.nonNull(user)) {
				userId = user.getUserId();
			}
			return userId;
		};
		Converter<DressType, String> dressTypeNameConverter = new Converter<DressType, String>() {

			@Override
			public String convert(MappingContext<DressType, String> context) {
				// TODO Auto-generated method stub
				DressType dressType = context.getSource();
				return dressType.getTypeName();
			}
		};
		Converter<DressType, String> dressTypeDescriptionConverter = new Converter<DressType, String>() {

			@Override
			public String convert(MappingContext<DressType, String> context) {
				// TODO Auto-generated method stub
				DressType dressType = context.getSource();
				return dressType.getTypeDescription();
			}
		};
		Converter<DressType, String> dressTypeCommentConverter = new Converter<DressType, String>() {

			@Override
			public String convert(MappingContext<DressType, String> context) {
				// TODO Auto-generated method stub
				DressType dressType = context.getSource();
				return dressType.getComment();
			}
		};

		modelMapper.typeMap(UserDressType.class, UserDressTypeDto.class).addMappings(mapper -> {
			mapper.map(UserDressType::getId, UserDressTypeDto::setUserDressTypeId);
//			mapper.using(dressTypeIdConverter).map(UserDressType::getDressType, UserDressTypeDto::setDressTypeId);
			mapper.using(dressTypeNameConverter).map(UserDressType::getDressType, UserDressTypeDto::setTypeName);
			mapper.using(dressTypeDescriptionConverter).map(UserDressType::getDressType,
					UserDressTypeDto::setTypeDescription);
			mapper.using(dressTypeCommentConverter).map(UserDressType::getDressType, UserDressTypeDto::setComment);
			mapper.map(UserDressType::getCreatedAt, UserDressTypeDto::setCreatedAt);
			mapper.map(UserDressType::getUpdatedAt, UserDressTypeDto::setUpdatedAt);
			mapper.using(userToIdConverter).map(UserDressType::getUser, UserDressTypeDto::setCreatedBy);
			mapper.map(UserDressType::getPrice, UserDressTypeDto::setPrice);
		});
		return modelMapper;
	}

	public UserDressTypeDto getUserDressTypeDto(UserDressType userDressType) {
		ModelMapper modelMapper = getUserDressTypeToDtoMapper();
		UserDressTypeDto userDressTypeDto = null;
		if (Objects.nonNull(userDressType)) {
			userDressTypeDto = modelMapper.map(userDressType, UserDressTypeDto.class);
		}
		return userDressTypeDto;
	}
}
