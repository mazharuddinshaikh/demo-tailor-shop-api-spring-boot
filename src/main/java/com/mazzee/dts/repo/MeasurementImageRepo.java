package com.mazzee.dts.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.entity.MeasurementImage;

@Repository
public interface MeasurementImageRepo extends JpaRepository<MeasurementImage, Integer> {

	List<MeasurementImage> findByMeasurement(Measurement measurement);

}
