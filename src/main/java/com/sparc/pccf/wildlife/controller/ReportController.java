package com.sparc.pccf.wildlife.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sparc.pccf.wildlife.Utility.SMSServices;
import com.sparc.pccf.wildlife.dto.AllReportCountDto;
import com.sparc.pccf.wildlife.dto.ApiResponseDto;
import com.sparc.pccf.wildlife.entity.ReportDO;
import com.sparc.pccf.wildlife.entity.SmSCounter;
import com.sparc.pccf.wildlife.repository.SMSCounterRepository;
import com.sparc.pccf.wildlife.request.Report_Request;
import com.sparc.pccf.wildlife.response.CircleCountResponse;
import com.sparc.pccf.wildlife.response.IncidentReportCountResponse;
import com.sparc.pccf.wildlife.response.LatLongResponse;
import com.sparc.pccf.wildlife.response.ReportResponse;
import com.sparc.pccf.wildlife.response.SuccessResponse;
import com.sparc.pccf.wildlife.service.ReportService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/reports")
public class ReportController {
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private SMSCounterRepository smsCounterRepository;

	@Autowired
	private MessageSource messageSource;

	@PostMapping(path = "/addReport")
	public SuccessResponse<String> addReport(@RequestBody Report_Request reportReq, @RequestParam String fieName) {
		
		ReportDO addReport = reportService.addReport(reportReq, fieName);
		if (addReport != null && !addReport.getReportType().equals("")) {
			/*
			 * if(( reportReq.getLongitude()!="")&&(reportReq.getLatitude()!="")) { try {
			 * if(reportReq.getReport_type()=="direct") {
			 * //reportService.triggerSMS(reportReq.getLongitude(),
			 * reportReq.getLatitude()); //should be uncomment after proper template
			 * reportService.sendSMS(reportReq.getLongitude(), reportReq.getLatitude()); }
			 * }catch(Exception ex) {
			 * 
			 * } }
			 */
			return new SuccessResponse<String>(messageSource.getMessage("add.report.successfully", null, Locale.getDefault()));
		}else if (addReport != null) {
			return new SuccessResponse<String>(messageSource.getMessage("add.report.successfully", null, Locale.getDefault()));
		}else {
			return new SuccessResponse<String>(messageSource.getMessage("add.report.unsuccessfull", null, Locale.getDefault()));
		}
	}

	@GetMapping(path = "/viewReport")
	public List<ReportResponse> viewReport(@RequestParam(value = "reportType") String reportType,
										   @RequestParam(value = "circle") String circle, 
										   @RequestParam(value = "division") String division,
										   @RequestParam(value = "range") String range, 
										   @RequestParam(value = "section") String section,
										   @RequestParam(value = "beat") String beat, 
										   @RequestParam(value = "startDate") String startDate,
										   @RequestParam(value = "endDate") String endDate) 
	{
		return reportService.viewReport(reportType, circle, division, range, section, beat, startDate, endDate);
	}

	@GetMapping(path = "/viewReportById")
	public ReportResponse viewReportById(@RequestParam String id) {
		return reportService.viewReportById(id);
	}

	@GetMapping(path = "/getLatLong")
	public List<LatLongResponse> addAllLatLong(@RequestParam String division) {
		return reportService.geAllLatlong(division);
	}

	@GetMapping(path = "/getLatLongg")
	public List<LatLongResponse> addAllLatLongg(@RequestParam String division, 
												@RequestParam String range,
												@RequestParam String section, 
												@RequestParam String beat, 
												@RequestParam String query,
												@RequestParam(value = "startDate") String startDate, 
												@RequestParam(value = "endDate") String endDate) 
	{
		return reportService.geAllLatlongg(division, range, section, beat, query, startDate, endDate);
	}

	@GetMapping(path = "/getAllReportCount")
	public AllReportCountDto getAllReportCount(@RequestParam String division) {
		AllReportCountDto count = new AllReportCountDto();
		int allReportCount = reportService.getAllReportCount(division);
		int allReportCountDirect = reportService.getAllReportCountDirect(division);
		int allReportCountInDirect = reportService.getAllReportCountInDirect(division);
		int allNillReportCount = reportService.getAllNillReportCount(division);
		count.setAllReportCount(allReportCount);
		count.setAllReportCountDirect(allReportCountDirect);
		count.setAllReportCountInDirect(allReportCountInDirect);
		count.setNillReportCount(allNillReportCount);
		return count;
	}

	@GetMapping(path = "/getAllReportCountIn24Hrs")
	public AllReportCountDto getAllReportCountIn24Hrs(@RequestParam String division) {
		AllReportCountDto count = new AllReportCountDto();
		int allReportCount = reportService.getAllReportCountIn24Hrs(division);
		int allReportCountDirect = reportService.getAllReportCountDirectIn24Hrs(division);
		int allReportCountInDirect = reportService.getAllReportCountInDirectIn24Hrs(division);
		int allNillReportCount = reportService.getAllNillReportCountIn24Hrs(division);
		int allElephantDeathReportCount = reportService.getAllElephantDeathCountIn24Hrs(division);
		int allFireAlertReportCount = reportService.getAllFireAlertCountIn24Hrs(division);
		count.setAllReportCount(allReportCount);
		count.setAllReportCountDirect(allReportCountDirect);
		count.setAllReportCountInDirect(allReportCountInDirect);
		count.setNillReportCount(allNillReportCount);
		count.setAllElephantDeathReportCount(allElephantDeathReportCount);
		count.setAllFireAlertReportCount(allFireAlertReportCount);
		return count;
	}

	@GetMapping(path = "/getAllLatLongIn24")
	public List<LatLongResponse> getAllLatLong() {
		return reportService.getAllLatLong();

	}

	@GetMapping(path = "/getAllReportCountIn24Hrsss")
	public AllReportCountDto getAllReportCountIn24Hrsss(@RequestParam String circle, 
														@RequestParam String division,
														@RequestParam String range, 
														@RequestParam(value = "startDate") String startDate,
														@RequestParam(value = "endDate") String endDate) 
	{
		AllReportCountDto allReportCountIn24Hrsss = reportService.getAllReportCountIn24Hrsss(circle, division, range,
				startDate, endDate);
		return allReportCountIn24Hrsss;
	}

	@GetMapping(path = "/viewReportLast24hrs")
	public List<ReportResponse> viewReportLast24hrs(@RequestParam(value = "circle") String circle,
													@RequestParam(value = "division") String division,
													@RequestParam(value = "range") String range,
													@RequestParam(value = "startDate") String startDate, 
													@RequestParam(value = "endDate") String endDate) 
	{
		return reportService.viewReportLast24hrs(circle, division, range, startDate, endDate);
	}

	/* not in used for now */
	@GetMapping(path = "/viewReportzz")
	public List<ReportResponse> viewReport(@RequestParam(value = "reportType") String reportType,
										   @RequestParam(value = "division") String division, 
										   @RequestParam(value = "userId") String userId,
										   @RequestParam(value = "startDate") String startDate, 
										   @RequestParam(value = "endDate") String endDate) 
	{
		return reportService.viewReport(reportType, division, startDate, endDate, userId);
	}

	@GetMapping("/viewReportsByReportsId")
	public List<ReportResponse> viewReports(@RequestParam Integer surveyorUserId, 
											@RequestParam String sightingDateFrom,
											@RequestParam String sightingDateTo) 
	{
		return reportService.viewReports(surveyorUserId, sightingDateFrom, sightingDateTo);

	}
	@GetMapping(path = "/getVulnerabilityCountReports")
	public  List<CircleCountResponse> getVulnerabilityCountReports(@RequestParam String circle, 
																   @RequestParam String division,
																   @RequestParam String range,
																   @RequestParam(value = "startDate") String startDate,
																   @RequestParam(value = "endDate") String endDate) 
	{
		return reportService.getVulnerabilityCountReports(circle, division, range,startDate, endDate);
	}
	
	@GetMapping("/ViewAllVulnerabilityReport")
	public List<IncidentReportCountResponse> ViewAllVulnerabilityReport(@RequestParam String circleCode,
																		@RequestParam String divnCode,
																		@RequestParam String rngCode,
																		@RequestParam String startDate,
																		@RequestParam String endDate)
	{
		return reportService.ViewAllVulnerabilityReport(circleCode, divnCode, rngCode, startDate, endDate);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		
	}
	
	
	//////Testing API
	//
	@GetMapping(value = "/CorrectReportPositionByLatLong")
	public ResponseEntity<?> CorrectReportPositionByLatLong(@RequestParam Integer div) 
	{
		return reportService.CorrectReportPositionByLatLong(div);
	}
	//SMS
	@GetMapping(value = "/firesmsuniocode")
	public String firesmsuniocode() 
	{
		String VillageName="Vanibihar";
		String Division="City forest";
		String dfomobile="9437173290";
		String message="ଏକ ଜରୁରୀ ସୂଚନା: -\r\n" +Division+" ବନଖଣ୍ଡ ତରଫରୁ ଅବଗତ କରା ଯାଉଅଛି ଯେ ବଣୁଆ ହାତୀ ପଲ ଆଜି  "+VillageName+" (ନିକଟ ଗାଁ / ଜଙ୍ଗଲ) ରେ ଅଛନ୍ତି | ଘରୁ ବାହାରକୁ ବାହାରିଲେ ସତର୍କତା ଅବଲମ୍ବନ କରନ୍ତୁ | ଆପଣଙ୍କ ଗାଁ ପାଖରେ ହାତୀର ସୂଚନା ପାଇଲେ "+dfomobile+" ନମ୍ବର ରେ ତୁରନ୍ତ ଜଣାନ୍ତୁ |\r\n" + 
				"ଧନ୍ୟବାଦ,\r\n" + 
				"ଜଙ୍ଗଲ ଏବଂ ବନ୍ୟପ୍ରାଣୀ ବିଭାଗ, ଓଡିଶା ସରକାର |";
		String templateid="1407163886875136969";
	    String mobileNumber="9437173290";
		SMSServices service=new SMSServices();
		String sendOtpSMS = service.sendUnicodeSMS(message, mobileNumber, templateid);
		System.out.println(sendOtpSMS);
		return sendOtpSMS;
	}
	@GetMapping(value = "/fireblocksms")
	public String firebulksms() 
	{
		String Mobiles="9437173290,9040200745";
		String VillageName="Vanibihar";
		String Division="City forest";
		String dfomobile="9437173290";
		String message="ଏକ ଜରୁରୀ ସୂଚନା: -\r\n" +Division+" ବନଖଣ୍ଡ ତରଫରୁ ଅବଗତ କରା ଯାଉଅଛି ଯେ ବଣୁଆ ହାତୀ ପଲ ଆଜି  "+VillageName+" (ନିକଟ ଗାଁ / ଜଙ୍ଗଲ) ରେ ଅଛନ୍ତି | ଘରୁ ବାହାରକୁ ବାହାରିଲେ ସତର୍କତା ଅବଲମ୍ବନ କରନ୍ତୁ | ଆପଣଙ୍କ ଗାଁ ପାଖରେ ହାତୀର ସୂଚନା ପାଇଲେ "+dfomobile+" ନମ୍ବର ରେ ତୁରନ୍ତ ଜଣାନ୍ତୁ |\r\n" + 
				"ଧନ୍ୟବାଦ,\r\n" + 
				"ଜଙ୍ଗଲ ଏବଂ ବନ୍ୟପ୍ରାଣୀ ବିଭାଗ, ଓଡିଶା ସରକାର |";
		String templateid="1407163886875136969";
	    String mobileNumber=Mobiles;
		SMSServices service=new SMSServices();
		String sendOtpSMS = service.sendUnicodeSMS(message, mobileNumber, templateid);
		if(sendOtpSMS.contains("402")) {
			int count=2;
			SmSCounter sms=new SmSCounter();
			sms.setReportId(1);
			sms.setReportType("direct");
			sms.setSmsCount(count);
			smsCounterRepository.save(sms);
		}
		return sendOtpSMS;
	}
	@GetMapping(value = "/firesms")
	public String firesms() 
	{
		String otp="1234";
		String message="Welcome to iWLMS,\r\n" + 
				"Your OTP for forgotten password: "+otp+"\r\n" + 
				"Thank You,\r\n" + 
				"PCCF WL, Govt. Of Odisha.";
		
		String templateid="1407163886865077259";
	    String mobileNumber="9437173290";
		SMSServices service=new SMSServices();
		String sendOtpSMS = service.sendSingleSMS(message, mobileNumber, templateid);
		System.out.println(sendOtpSMS);
		return sendOtpSMS;
	}	
}
