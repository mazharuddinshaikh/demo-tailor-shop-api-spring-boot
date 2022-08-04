/**
 * 
 */
package com.mazzee.dts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.DressType;

/**
 * @author mazhar
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Repository
public interface DressTypeRepo extends JpaRepository<DressType, Integer> {

	@Query(value = "SELECT d.* FROM dts_dress_type d ORDER BY d.type_name ASC", nativeQuery = true)
	List<DressType> getAllDressTypes();

	@Query(value = "SELECT d.* FROM dts_dress_type d WHERE d.type_id = :dressTypeId ORDER BY d.type_name ASC", nativeQuery = true)
	Optional<DressType> getDressTypeById(@Param("dressTypeId") int dressTypeId);
}
