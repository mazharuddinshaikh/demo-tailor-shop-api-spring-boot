package com.mazzee.dts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.Dress;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Repository
public interface DressRepo extends JpaRepository<Dress, Integer> {

	@Query(value = "SELECT * FROM dts_dress ORDER BY order_date DESC", nativeQuery = true)
	List<Dress> getAllDress();

	@Query(value = "select d.* from dts_dress d inner join dts_dress_type dt on d.dress_type = dt.type_id AND dt.type_name = :dressType", nativeQuery = true)
	List<Dress> getAllDressByDressType(@Param("dressType") String dressType);

	@Query(value = "select * from dts_dress where dress_id = :dressId", nativeQuery = true)
	Optional<Dress> getDressById(@Param("dressId") int dressId);

	@Query(value = "SELECT * FROM dts_dress WHERE CUSTOMER_ID = :customerId", nativeQuery = true)
	List<Dress> getDressListByCustomerId(@Param("customerId") int customerId);

	@Query(value = "SELECT DD.* FROM dts_dress DD INNER JOIN dts_customer DC ON DD.CUSTOMER_ID = DC.CUSTOMER_ID AND DC.CUSTOMER_ID = :customerId INNER JOIN dts_user DU ON DC.USER_ID = DU.USER_ID AND DU.USER_ID = :userId", nativeQuery = true)
	List<Dress> getDressListByUserIdAndCustomerId(@Param("userId") int userId, @Param("customerId") int customerId);

	@Query(value = "SELECT DD.* FROM dts_dress DD INNER JOIN dts_customer DC ON DD.CUSTOMER_ID = DC.CUSTOMER_ID AND DC.USER_ID = :userId", nativeQuery = true, countQuery = "SELECT COUNT(DD.DRESS_ID) FROM dts_dress DD INNER JOIN dts_customer DC ON DD.CUSTOMER_ID = DC.CUSTOMER_ID AND DC.USER_ID = :userId")
	Page<Dress> getDressListByUser(@Param("userId") int userId, Pageable pageable);

	@Query(value = "SELECT DD.* FROM dts_dress DD INNER JOIN dts_customer DC ON DD.CUSTOMER_ID = DC.CUSTOMER_ID AND DC.USER_ID = :userId INNER JOIN DTS_DRESS_TYPE DDT ON DD.DRESS_TYPE = DDT.TYPE_ID AND DDT.TYPE_NAME IN :dressType", nativeQuery = true, countQuery = "SELECT COUNT(DD.DRESS_ID) FROM dts_dress DD INNER JOIN dts_customer DC ON DD.CUSTOMER_ID = DC.CUSTOMER_ID AND DC.USER_ID = :userId INNER JOIN DTS_DRESS_TYPE DDT ON DD.DRESS_TYPE = DDT.TYPE_ID AND DDT.TYPE_NAME IN :dressType")
	Page<Dress> getDressListByUserAndDressType(@Param("userId") int userId, @Param("dressType") List<String> dressType,
			Pageable pageable);
}
