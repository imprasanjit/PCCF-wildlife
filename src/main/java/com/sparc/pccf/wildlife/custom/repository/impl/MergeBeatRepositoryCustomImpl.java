package com.sparc.pccf.wildlife.custom.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sparc.pccf.wildlife.custom.repository.MergeBeatRepositoryCustom;

public class MergeBeatRepositoryCustomImpl implements MergeBeatRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<Object> getALlCordinateDetails(double p1, double p2) {
		//String jpql = "select divn_name,rng_name,sec_name,beat_name FROM L̥ where ST_Within (ST_SetSRID(ST_MakePoint("+p1+","+p2+"),4326),ST_Transform(ST_SetSRID(wildlife_oltp.merge_beat_32645.geom,32645),4326))";
        String jpql="select division_id,range_id,section_id,beat_id FROM layers_4326.beat_boundary where\r\n" + 
		"ST_Within (ST_SetSRID(ST_MakePoint("+p1+","+p2+"),4326),\r\n" + 
		"ST_Transform(ST_SetSRID(layers_4326.beat_boundary.geom,4326),4326))";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}

}
