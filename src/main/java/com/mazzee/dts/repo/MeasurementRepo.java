package com.mazzee.dts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.Measurement;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Integer> {

	@Query(value = "select dm.* from dts_measurement dm where dm.dress_id = :dressId", nativeQuery = true)
	Optional<Measurement> getMeasurementByDressId(@Param("dressId") int dressId);

	@Query(value = "select dm.* from dts_measurement dm where dm.dress_id in (:dressId)", nativeQuery = true)
	List<Measurement> getMeasurementByDressId(@Param("dressId") List<Integer> dressId);

}
