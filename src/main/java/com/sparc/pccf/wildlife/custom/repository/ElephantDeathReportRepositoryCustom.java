package com.sparc.pccf.wildlife.custom.repository;

import java.util.List;

import com.sparc.pccf.wildlife.entity.ElephantReportDO;

public interface ElephantDeathReportRepositoryCustom {

	List<Object[]> getTotalIncidentCountByCircle(int circleId, String startDate, String endDate);

	List<Object[]> getTotalIncidentCountByDivision(int divisionId,String startDate, String endDate);

	List<Object[]> getTotalIncidentCountByDivisionAndRange(int divisionId, int rangeId,String startDate, String endDate);

	List<Object[]> getTotalIncidentCount( String startDate, String endDate);

	List<Object[]> getTotalIncidentCountByCircleAndDivision(int circleId, int divisionId,String startDate, String endDate);

	List<Object[]> getTotalIncidentCountByCircleAndDivisionAndRange(int circleId, int divisionId, int rangeId,String startDate, String endDate);

	List<Object[]> getTotalIncidentCountByRange(int rangeId,String startDate, String endDate);
	
	List<Object[]> viewAllReportData(String division, String range, String section,String beat);

}
