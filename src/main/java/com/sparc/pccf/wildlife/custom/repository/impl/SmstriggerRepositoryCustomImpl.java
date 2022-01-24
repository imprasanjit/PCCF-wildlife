package com.sparc.pccf.wildlife.custom.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.sparc.pccf.wildlife.custom.repository.SmstriggerRepositoryCustom;
@Component
public class SmstriggerRepositoryCustomImpl implements SmstriggerRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Object> triggerSms(String longitude, String latitude) {
		String jpql = "select * from wildlife_oltp.fn_triggersms_getdivcode("+longitude+","+latitude+")";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}

}
