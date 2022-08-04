/**
 * 
 */
package com.mazzee.dts.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.UserDto;
import com.mazzee.dts.entity.User;

/**
 * @author Admin
 *
 */
@Service
public class UserDtoMapper {

	public UserDtoMapper() {
		super();
	}

	private ModelMapper getDtoToUserMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(UserDto.class, User.class).addMappings(mapper -> {
			mapper.map(UserDto::getFirstName, User::setFirstName);
			mapper.map(UserDto::getMiddleName, User::setMiddleName);
			mapper.map(UserDto::getLastName, User::setLastName);
			mapper.map(UserDto::getMobileNo, User::setMobileNo);
			mapper.map(UserDto::getAlternateMobileNo, User::setAlternateMobileNo);
			mapper.map(UserDto::getEmail, User::setEmail);
			mapper.map(UserDto::getUserName, User::setUserName);
			mapper.map(UserDto::getPassword, User::setPassword);
			mapper.map(UserDto::getShopName, User::setShopName);
			mapper.map(UserDto::getAddress, User::setAddress);
		});
		return modelMapper;
	}

	private ModelMapper getUserToDtoMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(User.class, UserDto.class).addMappings(mapper -> {
			mapper.map(User::getUserId, UserDto::setUserId);
			mapper.map(User::getFirstName, UserDto::setFirstName);
			mapper.map(User::getMiddleName, UserDto::setMiddleName);
			mapper.map(User::getLastName, UserDto::setLastName);
			mapper.map(User::getMobileNo, UserDto::setMobileNo);
			mapper.map(User::getAlternateMobileNo, UserDto::setAlternateMobileNo);
			mapper.map(User::getEmail, UserDto::setEmail);
			mapper.map(User::getUserName, UserDto::setUserName);
			mapper.map(User::getShopName, UserDto::setShopName);
			mapper.map(User::getAddress, UserDto::setAddress);
			mapper.map(User::getCreatedAt, UserDto::setCreatedAt);
			mapper.map(User::getUpdatedAt, UserDto::setUpdatedAt);
			mapper.skip(User::getPassword, UserDto::setPassword);
		});
		return modelMapper;
	}

	public User getUserFromDto(UserDto userDto) {
		User user = null;
		ModelMapper modellMapper = getDtoToUserMapper();
		if (Objects.nonNull(userDto)) {
			user = modellMapper.map(userDto, User.class);
		}

		return user;
	}

	public UserDto getDtoFromUser(User user) {
		UserDto userDto = null;
		ModelMapper modellMapper = getUserToDtoMapper();
		if (Objects.nonNull(user)) {
			userDto = modellMapper.map(user, UserDto.class);
		}

		return userDto;
	}

}
