package com.sparc.pccf.wildlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparc.pccf.wildlife.custom.repository.MergeBeatRepositoryCustom;
import com.sparc.pccf.wildlife.entity.MergeBeatDO;

public interface MergeBeatRepository extends JpaRepository<MergeBeatDO, Integer>, MergeBeatRepositoryCustom {
	@Query(value = "select ST_Within (ST_SetSRID(ST_MakePoint(84.9353,19.7357),4326),ST_Transform(ST_SetSRID(wildlife_layers.merge_beat.geom,32645),4326)) FROM wildlife_layers.merge_beat", nativeQuery = true)
	List<Object> getAllBeat();

	//List getALlCordinateDetails(double p1,double p2);
	List<MergeBeatDO> findAll();


    @Query(value = "SELECT ST_X(ST_Centroid(layers_4326.all_new_beat.geom)) as lon,ST_Y(ST_Centroid(layers_4326.all_new_beat.geom)) as lat from layers_4326.all_new_beat where layers_4326.all_new_beat.beat_id=:beat", nativeQuery = true)
	List<Object> getLatLongByBeatId(String beat);

}
