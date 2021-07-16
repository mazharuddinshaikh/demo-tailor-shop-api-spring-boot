package com.mazzee.dts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.dto.Dress;

@Repository
public interface DressRepo extends JpaRepository<Dress, Integer> {

	@Query(value = "SELECT * FROM dts_dress ORDER BY order_date DESC", nativeQuery = true)
	List<Dress> getAllDress();

	@Query(value = "select d.* from dts_dress d inner join dts_dress_type dt on d.dress_type = dt.type_id AND dt.type_name = :dressType", nativeQuery = true)
	List<Dress> getAllDressByDressType(@Param("dressType") String dressType);

	@Query(value = "select * from dts_dress where dress_id = :dressId", nativeQuery = true)
	Optional<Dress> getDressById(@Param("dressId") int dressId);
}	
