package com.mazzee.dts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.UserDressType;

@Repository
public interface UserDressTypeRepo extends JpaRepository<UserDressType, Integer> {
	@Query(value = "SELECT d.* FROM DTS_USER_DRESS_TYPE d WHERE d.USER_ID = :userId", nativeQuery = true)
	List<UserDressType> getDressTypeListByUserId(@Param("userId") int userId);

	@Query(value = "SELECT d.* FROM DTS_USER_DRESS_TYPE d WHERE d.ID = :userDressTypeId", nativeQuery = true)
	Optional<UserDressType> getUserDressTypeByTypeId(@Param("userDressTypeId") int userDressTypeId);

}
