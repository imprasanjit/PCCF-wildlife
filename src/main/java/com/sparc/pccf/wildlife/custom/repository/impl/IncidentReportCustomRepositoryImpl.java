package com.sparc.pccf.wildlife.custom.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.sparc.pccf.wildlife.custom.repository.IncidentReportCustomRepository;
@Component
public class IncidentReportCustomRepositoryImpl implements IncidentReportCustomRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Object[]> getAllIncidentReport(String circlreId, String divisionId, String rangeId, String sectionId,
			String beatId,String reportType, String startDate, String endDate) {
		String jpql = "SELECT * from wildlife_oltp.fn_getincidentreports('"+circlreId+"','"+divisionId+"','"+rangeId+"','"+sectionId+"','"+beatId+"','"+reportType+"','"+startDate+"','"+endDate+"')";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> viewAllElephantDeathReportzz(String circlreId, String divisionId, String rangeId,
			String sectionId, String beatId, String startDate, String endDate) {
		String jpql = "SELECT * from wildlife_oltp.fn_getelephantdeathreports('"+circlreId+"','"+divisionId+"','"+rangeId+"','"+sectionId+"','"+beatId+"','"+startDate+"','"+endDate+"')";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}
	
}
