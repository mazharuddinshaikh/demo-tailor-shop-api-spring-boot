/**
 * 
 */
package com.mazzee.dts.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.dto.DressType;

/**
 * @author mazhar
 *
 */
@Repository
public interface DressTypeRepo extends JpaRepository<DressType, Integer> {

	@Query(value = "SELECT d.* FROM dts_dress_type d ORDER BY d.type_name ASC", nativeQuery = true)
	List<DressType> getAllDressTypes();
}
