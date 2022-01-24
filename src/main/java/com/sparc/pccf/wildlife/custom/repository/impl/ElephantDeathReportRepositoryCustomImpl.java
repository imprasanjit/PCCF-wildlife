package com.sparc.pccf.wildlife.custom.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.sparc.pccf.wildlife.custom.repository.ElephantDeathReportRepositoryCustom;
@Component
public class ElephantDeathReportRepositoryCustomImpl implements ElephantDeathReportRepositoryCustom{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Object[]> getTotalIncidentCountByCircle(int circleId, String startDate, String endDate) {
		String jpql = "SELECT c.circle_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r join WILDLIFE_OLTP.master_circle c on r.circle_id=c.circle_id join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.circle_id='"+circleId+"' and r.incident_reported_on>='"+startDate+"' and r.incident_reported_on<='"+endDate+"'group by 1,2";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> getTotalIncidentCountByDivision(int divisionId, String startDate, String endDate) {
		String jpql = "SELECT c.division_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r join WILDLIFE_OLTP.master_division c on r.division_id=c.division_id join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.division_id='"+divisionId+"' and r.incident_reported_on>='"+startDate+"' and r.incident_reported_on<='"+endDate+"' group by 1,2";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> getTotalIncidentCountByDivisionAndRange(int divisionId, int rangeId, String startDate,
			String endDate) {
		String jpql = "SELECT c.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
				"join WILDLIFE_OLTP.master_division c on r.division_id=c.division_id \r\n" + 
				"join  WILDLIFE_OLTP.master_range rng on r.range_id=rng.range_id \r\n" + 
				"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.division_id='"+divisionId+"' and rng.range_id='"+rangeId+"' and r.incident_reported_on>='"+startDate+"' and r.incident_reported_on<='"+endDate+"' group by 1,2,3";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> getTotalIncidentCount(String startDate, String endDate) {
		// TODO Auto-generated method stub
		String jpql = "SELECT d.name,SUM(d.value) FROM WILDLIFE_OLTP.t_elephant_death_details d join WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
				"on r.death_id=d.death_id where r.incident_reported_on>='"+startDate+"' and r.incident_reported_on<='"+endDate+"' group by 1";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> getTotalIncidentCountByCircleAndDivision(int circleId, int divisionId, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		String jpql = "SELECT c.circle_id,div.division_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
				"join WILDLIFE_OLTP.master_circle c on r.circle_id=c.circle_id \r\n" + 
				"join  WILDLIFE_OLTP.master_division div on r.division_id=div.division_id \r\n" + 
				"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.circle_id='"+circleId+"' and div.division_id='"+divisionId+"' and r.incident_reported_on>='"+startDate+"' and r.incident_reported_on<='"+endDate+"' group by 1,2,3";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> getTotalIncidentCountByCircleAndDivisionAndRange(int circleId, int divisionId, int rangeId,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		String jpql = "SELECT c.circle_id,div.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
				"join WILDLIFE_OLTP.master_circle c on r.circle_id=c.circle_id \r\n" + 
				"join  WILDLIFE_OLTP.master_division div on r.division_id=div.division_id \r\n" + 
				"join  WILDLIFE_OLTP.master_range rng on r.range_id=rng.range_id\r\n" + 
				"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where c.circle_id='"+circleId+"' and\r\n" + 
				"div.division_id='"+divisionId+"' and rng.range_id='"+rangeId+"' and r.incident_reported_on>='"+startDate+"' and r.incident_reported_on<='"+endDate+"' group by 1,2,3,4";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> getTotalIncidentCountByRange(int rangeId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		String jpql = "SELECT div.division_id,rng.range_id,d.name,sum(d.value) from WILDLIFE_OLTP.t_elephant_death_report r \r\n" + 
				"join  WILDLIFE_OLTP.master_division div on r.division_id=div.division_id \r\n" + 
				"join  WILDLIFE_OLTP.master_range rng on r.range_id=rng.range_id\r\n" + 
				"join WILDLIFE_OLTP.t_elephant_death_details d on r.death_id=d.death_id where rng.range_id='"+rangeId+"' and r.incident_reported_on>='"+startDate+"' and r.incident_reported_on<='"+endDate+"' group by 1,2,3";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

	@Override
	public List<Object[]> viewAllReportData(String division, String range, String section, String beat) {
		String jpql = "SELECT * from wildlife_oltp.fn_getAllelephantsreportsByGajasathiUser('"+division+"','"+range+"','"+section+"','"+beat+"')";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}

}
