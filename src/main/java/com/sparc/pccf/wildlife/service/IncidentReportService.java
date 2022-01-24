package com.sparc.pccf.wildlife.service;

import java.util.List;

import com.sparc.pccf.wildlife.entity.DamageDeatilsDO;
import com.sparc.pccf.wildlife.entity.ElephantDeathDO;
import com.sparc.pccf.wildlife.entity.ElephantDeathDetailsDO;
import com.sparc.pccf.wildlife.entity.FireDataDO;
import com.sparc.pccf.wildlife.request.IncidentReportRequest;
import com.sparc.pccf.wildlife.response.ElephantDeathRespnsezz;
import com.sparc.pccf.wildlife.response.IncidentCountResponse;
import com.sparc.pccf.wildlife.response.IncidentReportResponse;
import com.sparc.pccf.wildlife.response.IncidentResponseAll;

public interface IncidentReportService 
{
	List<IncidentCountResponse> getTotalIncidentCount(String circlreId, String divisionId, String rangeId, String sectionId, String role);

	FireDataDO addIncidentReport(IncidentReportRequest incidentReportRequest, String fileName);

	List<IncidentReportResponse> viewAllIncidentReportCount(String circlreId, String divisionId, String rangeId, String startDate, String endDate);

	ElephantDeathDO addElephantDeathReport(IncidentReportRequest incidentReportRequest, String fileName);

	List<IncidentReportResponse> viewAllElephantDeathReport(String circlreId, String divisionId, String rangeId, String startDate, String endDate);
	
	public List<IncidentResponseAll> viewAllIncidentReport(String circlreId, String divisionId,
			String rangeId, String sectionId, String beatId, String reportType, String startDate, String endDate);

	List<ElephantDeathRespnsezz> viewAllElephantDeathReportzz(String circlreId, String divisionId, String rangeId,
			String sectionId, String beatId, String startDate, String endDate);

	List<DamageDeatilsDO> getIncidentRepotzDetailsByReportId(String reportId);

	List<ElephantDeathDetailsDO> getElephantDEathDetailsByReportId(String reportId);
}
