package com.sparc.pccf.wildlife.custom.repository.impl;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.sparc.pccf.wildlife.custom.repository.ReportRepositoryCustom;
@Component
public class ReportRepositoryCustomimpl implements ReportRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public List<Object> getLatLongByDivision(String division, String query,String startDate, String endDate) {
		String jpql = "select longitude,latitude,sighting_date from wildlife_oltp.t_sighting_report where division_id='"+division+"' and sighting_date >='"+startDate+"' and sighting_date<='"+endDate+"'";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}
	@Override
	public List<Object> getLatLongByDivisionAndRange(String division, String range, String query,String startDate, String endDate) {
		String jpql = "select longitude,latitude,sighting_date from wildlife_oltp.t_sighting_report where division_id='"+division+"' and range_id='"+range+"' and sighting_date >='"+startDate+"' and sighting_date<='"+endDate+"'";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}
	@Override
	public List<Object> getLatLongByDivisionAndRangeAndSec(String division, String range, String section, String query,String startDate, String endDate) {
		String jpql = "select longitude,latitude,sighting_date from wildlife_oltp.t_sighting_report where division_id='"+division+"' and range_id='"+range+"' and section_id='"+section+"' and sighting_date >='"+startDate+"' and sighting_date<='"+endDate+"'";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}
	@Override
	public List<Object> getLatLongByDivisionAndRangeAndSectAndBeat(String division, String range, String section,
			String beat, String query,String startDate, String endDate) {
		String jpql = "select longitude,latitude,sighting_date from wildlife_oltp.t_sighting_report where division_id='"+division+"' and range_id='"+range+"' and section_id='"+section+"' and beat_id='"+beat+"' and sighting_date >='"+startDate+"' and sighting_date<='"+endDate+"'";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}
	@Override
	public List<Object> getLatLong(String query,String stDate,String enDate) {
		String jpql = "select longitude,latitude,sighting_date from wildlife_oltp.t_sighting_report where sighting_date >='"+stDate+"' and sighting_date<='"+enDate+"'";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
	}
	@Override
	public List<Object> getAllReportCountIn24Hrsss(String circle, String division, String range,String startDate, String endDate) {
		String jpql = "SELECT * from wildlife_oltp.fn_getallreportcountin24hrs('"+circle+"','"+division+"','"+range+"','"+startDate+"','"+endDate+"')";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object> resultList = queryy.getResultList();
		return resultList;
		
	}
	@Override
	public List<Object[]> viewReportLast24hrs(String circle, String division, String range,String startDate, String endDate) {
		String jpql = "SELECT * from wildlife_oltp.fn_getallreportin24hrs('"+circle+"','"+division+"','"+range+"','"+startDate+"','"+endDate+"')";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}
	@Override
	public List<Object[]> viewElephantReport(String reportType, String circle, String division, String range,
			String section, String beat, String startDate, String endDate) {
		// TODO Auto-generated method stub
		String jpql = "SELECT * from wildlife_oltp.fn_getviewelephantsreports('"+circle+"','"+division+"','"+range+"','"+section+"','"+beat+"','"+reportType+"','"+startDate+"','"+endDate+"')";
		Query queryy = entityManager.createNativeQuery(jpql);
		List<Object[]> resultList = queryy.getResultList();
		return resultList;
	}
	@Override
	public List<Object[]> getduplicatereport(String report_type, String date, Integer beatid, String total,String Heard,String Tusker,String Mukhna,String Female,String Calf) {
		// TODO Auto-generated method stubdate
				String jpql = "SELECT * from wildlife_oltp.fn_getduplicatereport('"+report_type+"','"+date+"','"+beatid+"','"+total+"','"+Heard+"','"+Tusker+"','"+Mukhna+"','"+Female+"','"+Calf+"')";
				Query queryy = entityManager.createNativeQuery(jpql);
				List<Object[]> resultList = queryy.getResultList();
				return resultList;
	}
	@Override
	public List<Object> getVulnerableElephant(String circleCode, String divnCode, String rngCode, String startDate,String endDate) 
	{
		String jpql = "SELECT * FROM wildlife_oltp.fn_getvulnerable_elephant_count('"+circleCode+"','"+divnCode+"','"+rngCode+"','"+startDate+"','"+endDate+"')";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object> resultList = query.getResultList();
		return resultList;
	}
	@Override
	public List<Object> getVulenarableDivRailIntersect(String latitude, String longitude) {
		
		//String jpql = "select div_id,ST_Intersects (ST_Buffer(ST_MakePoint('\"+longitude+\"','\"+latitude+\"')::geography,5000)::geometry,layers_4326.railway_line.geom) as intersectdata from layers_4326.railway_line";
		String jpql="SELECT * FROM wildlife_oltp.fn_getVulenarableDivRailIntersect('"+longitude+"','"+latitude+"') where intersectdata=true";
		Query query = entityManager.createNativeQuery(jpql);
		List<Object> resultList = query.getResultList();
		return resultList;
		
		
	}

}
