package com.sparc.pccf.wildlife.custom.repository;

import java.util.List;

public interface IncidentReportCustomRepository {

	List<Object[]> getAllIncidentReport(String circlreId, String divisionId, String rangeId, String sectionId,
			String beatId, String reportType, String startDate, String endDate);

	List<Object[]> viewAllElephantDeathReportzz(String circlreId, String divisionId, String rangeId, String sectionId,
			String beatId, String startDate, String endDate);
}
