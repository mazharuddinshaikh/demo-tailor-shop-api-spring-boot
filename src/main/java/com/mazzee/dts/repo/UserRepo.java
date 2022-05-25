/**
 * 
 */
package com.mazzee.dts.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.User;

/**
 * @author mazhar
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM dts_user WHERE user_id = :userId", nativeQuery = true)
	Optional<User> getUserByUserId(@Param("userId") int userId);

	@Query(value = "SELECT * FROM dts_user WHERE user_name = :userName", nativeQuery = true)
	Optional<User> getUserByUserName(@Param("userName") String userName);

	@Query(value = "SELECT * FROM dts_user WHERE email = :email", nativeQuery = true)
	Optional<User> getUserByEmail(@Param("email") String email);

	@Query(value = "SELECT * FROM dts_user WHERE mobile_no = :mobileNo", nativeQuery = true)
	Optional<User> getUserByMobileNo(@Param("mobileNo") String mobileNo);

	@Query(value = "SELECT * FROM dts_user WHERE email = :email AND password = :password", nativeQuery = true)
	Optional<User> getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE user_name = :userName AND password = :password", nativeQuery = true)
	Optional<User> getUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE mobile_no = :mobileNo AND password = :password", nativeQuery = true)
	Optional<User> getUserByMobileNoAndPassword(@Param("mobileNo") String mobileNo, @Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE (user_name = :userName OR mobile_no = :userName OR email = :userName) AND password = :password", nativeQuery = true)
	Optional<User> getUserByUserNameOrEmailORMobileNoAndPassword(@Param("userName") String userName,
			@Param("password") String password);

	@Query(value = "SELECT * FROM dts_user WHERE (user_name = :userName OR mobile_no = :userName OR email = :userName)", nativeQuery = true)
	Optional<User> getUserByUserNameOrEmailOrMobileNo(@Param("userName") String userName);

}
