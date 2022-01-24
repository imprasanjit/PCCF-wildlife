package com.sparc.pccf.wildlife.custom.repository;

import java.util.Date;
import java.util.List;

public interface ReportRepositoryCustom {

	List<Object> getLatLongByDivision(String division, String query, String startDate, String endDate);

	List<Object> getLatLongByDivisionAndRange(String division, String range, String query, String startDate, String endDate);

	List<Object> getLatLongByDivisionAndRangeAndSec(String division, String range, String section, String query,String startDate, String endDate);

	List<Object> getLatLongByDivisionAndRangeAndSectAndBeat(String division, String range, String section, String beat,
			String query,String startDate, String endDate);

	List<Object> getLatLong(String query, String startDate, String endDate);

	List<Object> getAllReportCountIn24Hrsss(String circle, String division, String range, String startDate,String endDate);

	List<Object[]> viewReportLast24hrs(String circle, String division,String range,String startDate,String endDate);
	
	List<Object[]> viewElephantReport(String reportType, String circle, String division, String range,
			String section, String beat, String startDate, String endDate);

	List<Object[]> getduplicatereport(String report_type, String dateString, Integer beatid, String total,String Heard,String Tusker,String Mukhna,String Female,String Calf);
	
	List<Object> getVulnerableElephant(String circleCode,String divnCode, String rngCode, String startDate,String endDate);

	List<Object> getVulenarableDivRailIntersect(String latitude, String longitude);

}
