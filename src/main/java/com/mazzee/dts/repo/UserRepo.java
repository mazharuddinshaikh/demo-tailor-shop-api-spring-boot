/**
 * 
 */
package com.mazzee.dts.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.dto.User;

/**
 * @author mazhar
 *
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM dts_user WHERE email = :email AND password = :password", nativeQuery = true)
	Optional<User> getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE user_name = :userName AND password = :password", nativeQuery = true)
	Optional<User> getUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE mobile_no = :mobileNo AND password = :password", nativeQuery = true)
	Optional<User> getUserByMobileNoAndPassword(@Param("mobileNo") String mobileNo, @Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE (user_name = :userName OR mobile_no = :mobileNo OR email = :email) AND password = :password", nativeQuery = true)
	Optional<User> getUserByUserNameOrEmailORMobileNoAndPassword(@Param("userName") String userName,
			@Param("mobileNo") String mobileNo, @Param("email") String email, @Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE (user_name = :userName OR mobile_no = :userName OR email = :userName)", nativeQuery = true)
	Optional<User> getUserByUserNameOrEmailOrMobileNo(@Param("userName") String userName);
}
