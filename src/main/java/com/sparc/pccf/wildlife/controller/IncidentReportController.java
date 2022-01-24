package com.sparc.pccf.wildlife.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparc.pccf.wildlife.entity.DamageDeatilsDO;
import com.sparc.pccf.wildlife.entity.ElephantDeathDO;
import com.sparc.pccf.wildlife.entity.ElephantDeathDetailsDO;
import com.sparc.pccf.wildlife.entity.FireDataDO;
import com.sparc.pccf.wildlife.repository.IncidentReportRepository;
import com.sparc.pccf.wildlife.request.IncidentReportRequest;
import com.sparc.pccf.wildlife.response.ElephantDeathRespnsezz;
import com.sparc.pccf.wildlife.response.IncidentCountResponse;
import com.sparc.pccf.wildlife.response.IncidentReportResponse;
import com.sparc.pccf.wildlife.response.IncidentResponseAll;
import com.sparc.pccf.wildlife.response.SuccessResponse;
import com.sparc.pccf.wildlife.service.IncidentReportService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/IncidentReport")
public class IncidentReportController {
	@Autowired
	IncidentReportRepository incidentReportRepository;

	@Autowired
	IncidentReportService incidentReportService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/getTotalIncidentCount")
	public List<IncidentCountResponse> getTotalIncidentCount(
			@RequestParam String circlreId, 
			@RequestParam String divisionId,
			@RequestParam String rangeId,
			@RequestParam String sectionId,
			@RequestParam String role) {		
		return incidentReportService.getTotalIncidentCount(circlreId,divisionId,rangeId,sectionId,role);
	}
	
	@GetMapping("/viewAllIncidentReport")
	public List<IncidentReportResponse> viewAllIncidentReport(
			@RequestParam String circlreId, 
			@RequestParam String divisionId,
			@RequestParam String rangeId,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate) {		
		return incidentReportService.viewAllIncidentReportCount(circlreId,divisionId,rangeId,startDate,endDate);
	}
	
	@PostMapping("/addIncidentReport")
	public SuccessResponse<String> addIncidentReport(@RequestBody IncidentReportRequest incidentReportRequest,@RequestParam String fileName) 
	{
		FireDataDO addIncidentReport = incidentReportService.addIncidentReport(incidentReportRequest,fileName);
		if(addIncidentReport!=null)
		return new SuccessResponse<String>(messageSource.getMessage("add.report.successfully", null, Locale.getDefault()));
		else
			return new  SuccessResponse<String>(messageSource.getMessage("add.report.unsuccessfull", null, Locale.getDefault()));		 
	}
	
	@PostMapping("/addElephantDeathReport")
	public SuccessResponse<String> addElephantDeathReport(@RequestBody IncidentReportRequest incidentReportRequest,@RequestParam String fileName) 
	{
		ElephantDeathDO addElephantDeathReport = incidentReportService.addElephantDeathReport(incidentReportRequest,fileName);
		if(addElephantDeathReport!=null)
		return new SuccessResponse<String>(messageSource.getMessage("add.report.successfully", null, Locale.getDefault()));
		else
			return new  SuccessResponse<String>(messageSource.getMessage("add.report.unsuccessfull", null, Locale.getDefault()));

	}
	
	@GetMapping("/viewAllElephantDeathReport")
	public List<IncidentReportResponse> viewAllElephantDeathReport(@RequestParam String circlreId, @RequestParam String divisionId,
			@RequestParam String rangeId,@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate) {		
		return incidentReportService.viewAllElephantDeathReport(circlreId,divisionId,rangeId,startDate,endDate);
	}
	
	@GetMapping("/viewAllIncidentReportzz")
	public List<IncidentResponseAll> viewAllIncidentReport(
			@RequestParam String circlreId, 
			@RequestParam String divisionId,
			@RequestParam String rangeId,
			@RequestParam String sectionId, 
			@RequestParam String beatId,
			@RequestParam String reportType,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate) {		
		return incidentReportService.viewAllIncidentReport(circlreId,divisionId,rangeId,sectionId,beatId,reportType,startDate,endDate);
	}
	
	@GetMapping("/viewAllElephantDeathReportzz")
	public List<ElephantDeathRespnsezz> viewAllElephantDeathReportzz(
			@RequestParam String circlreId, 
			@RequestParam String divisionId,
			@RequestParam String rangeId,
			@RequestParam String sectionId, 
			@RequestParam String beatId,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate) {		
		return incidentReportService.viewAllElephantDeathReportzz(circlreId,divisionId,rangeId,sectionId,beatId,startDate,endDate);
	}
	
	@GetMapping("/getIncidentRepotzDetailsByReportId")
	public List<DamageDeatilsDO> getIncidentRepotzDetailsByReportId(@RequestParam String reportId) {		
		return incidentReportService.getIncidentRepotzDetailsByReportId(reportId);
	}
	
	@GetMapping("/getElephantDEathDetailsByReportId")
	public List<ElephantDeathDetailsDO> getElephantDEathDetailsByReportId(@RequestParam String reportId) 
	{		
		return incidentReportService.getElephantDEathDetailsByReportId(reportId);
	}
}
