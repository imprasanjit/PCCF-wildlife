package com.sparc.pccf.wildlife.service;

import java.sql.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.sparc.pccf.wildlife.dto.AllReportCountDto;
import com.sparc.pccf.wildlife.dto.VulnerabilityDTO;
import com.sparc.pccf.wildlife.entity.ReportDO;
import com.sparc.pccf.wildlife.request.Report_Request;
import com.sparc.pccf.wildlife.response.CircleCountResponse;
import com.sparc.pccf.wildlife.response.IncidentReportCountResponse;
import com.sparc.pccf.wildlife.response.IncidentReportResponse;
import com.sparc.pccf.wildlife.response.LatLongResponse;
import com.sparc.pccf.wildlife.response.ReportResponse;

public interface ReportService 
{
	ReportDO addReport(Report_Request reportReq, String fieName);

	List<LatLongResponse> geAllLatlong(String division);
	
	//List<ReportResponse> viewReport(String reportType,String division, String startDate, String endDate, String userId);
	
	int getAllReportCount(String division);

	int getAllReportCountDirect(String division);

	int getAllReportCountInDirect(String division);

	int getAllNillReportCount(String division);

	int getAllReportCountIn24Hrs(String division);

	int getAllReportCountDirectIn24Hrs(String division);

	int getAllReportCountInDirectIn24Hrs(String division);

	int getAllNillReportCountIn24Hrs(String division);

	int getAllElephantDeathCountIn24Hrs(String division);

	int getAllFireAlertCountIn24Hrs(String division);

	List<LatLongResponse> geAllLatlongg(String division, String range, String section, String beat,String query, String startDate, String endDate);

	ReportResponse viewReportById(String id);

	void triggerSMS(String longitude, String latitude);
	
	List<LatLongResponse> getAllLatLong();

	List<ReportResponse> viewReport(String reportType, String division, String startDate,
			String endDate, String userId);

	AllReportCountDto getAllReportCountIn24Hrsss(String circle, String division, String range,String startDate, String endDate);

	List<ReportResponse> viewReportLast24hrs(String circle, String division, String range,String startDate, String endDate);

	List<ReportResponse> viewReport(String reportType, String circle, String division, String range, String section,String beat, String startDate, String endDate);
	
	List<ReportResponse> viewReports(Integer surveyorUserId, String sightingDateFrom,String sightingDateTo);
	
	List<IncidentReportCountResponse> ViewAllVulnerabilityReport(String circleCode,String divnCode,String rngCode,String startDate,String endDate);

	List<CircleCountResponse> getVulnerabilityCountReports(String circle, String division, String range,
			String startDate, String endDate);


	ResponseEntity<?> CorrectReportPositionByLatLong(int DivisionId);

	//void sendSMS(String longitude, String latitude);
}
