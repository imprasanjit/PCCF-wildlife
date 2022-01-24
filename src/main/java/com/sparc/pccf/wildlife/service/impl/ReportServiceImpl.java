package com.sparc.pccf.wildlife.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sparc.pccf.wildlife.Utility.SMSServices;
import com.sparc.pccf.wildlife.custom.repository.ReportRepositoryCustom;
import com.sparc.pccf.wildlife.custom.repository.SmstriggerRepositoryCustom;
import com.sparc.pccf.wildlife.dto.AllReportCountDto;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.ReportDO;
import com.sparc.pccf.wildlife.entity.SmSCounter;
import com.sparc.pccf.wildlife.entity.VulnerabilityDO;
import com.sparc.pccf.wildlife.exception.IncorrectPositionException;
import com.sparc.pccf.wildlife.exception.ReportException;
import com.sparc.pccf.wildlife.repository.AuthRepository;
import com.sparc.pccf.wildlife.repository.BeatChangeRepository;
import com.sparc.pccf.wildlife.repository.BeatMasterRepository;
import com.sparc.pccf.wildlife.repository.CircleMasterRepository;
import com.sparc.pccf.wildlife.repository.CitizenUserRepository;
import com.sparc.pccf.wildlife.repository.DivMasterRepository;
import com.sparc.pccf.wildlife.repository.MergeBeatRepository;
import com.sparc.pccf.wildlife.repository.RangeMasterRepository;
import com.sparc.pccf.wildlife.repository.ReportRepository;
import com.sparc.pccf.wildlife.repository.SMSCounterRepository;
import com.sparc.pccf.wildlife.repository.SectionMasterRepository;
import com.sparc.pccf.wildlife.repository.VillageRepository;
import com.sparc.pccf.wildlife.repository.VulnerabilityRepository;
import com.sparc.pccf.wildlife.request.Report_Request;
import com.sparc.pccf.wildlife.response.CircleCountResponse;
import com.sparc.pccf.wildlife.response.DivisionCountResponse;
import com.sparc.pccf.wildlife.response.IncidentReportCountResponse;
import com.sparc.pccf.wildlife.response.LatLongResponse;
import com.sparc.pccf.wildlife.response.MessageResponse;
import com.sparc.pccf.wildlife.response.RangeCountResponse;
import com.sparc.pccf.wildlife.response.ReportResponse;
import com.sparc.pccf.wildlife.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private CitizenUserRepository citizenUserRepo;
	
	@Autowired
	private SMSCounterRepository smsCounterRepository;
	
	@Autowired
	private VillageRepository villRepository;

	@Autowired
	private BeatMasterRepository beatRepository;

	@Autowired
	private SectionMasterRepository sectionRepository;

	@Autowired
	private RangeMasterRepository rangeRepository;

	@Autowired
	private DivMasterRepository divisionRepository;
	
	@Autowired
	private CircleMasterRepository circleMasterRepository;
	

	@Autowired
	private VulnerabilityRepository vulnerabilityRepository;

	@Autowired
	private MergeBeatRepository mergeBeatRepository;

	@Autowired
	private ReportRepositoryCustom reportRepositoryCustom;

	@Autowired
	private SmstriggerRepositoryCustom smstriggerRepositoryCustom;

	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private BeatChangeRepository beatChangeRepository;
	

	@Override
	public ReportDO addReport(Report_Request reportReq, String file) {
		ReportDO reportDO = new ReportDO();
		Integer beatid = null,sectionId=null,rangeId=null,divisionId=null;
		 String JSONresult="",postdata="",beat_boundary="",beat_id="",f_division_id = "",f_range_id="",f_section_id="";
		
		   if(( reportReq.getLongitude()!="")&&(reportReq.getLatitude()!="")) {
				try {
			    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			    body.add("buffer", 5000);
			    body.add("cordinates[0]", reportReq.getLongitude().toString());
			    body.add("cordinates[1]", reportReq.getLatitude().toString());

			    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body);

			    String uri = "https://odisha4kgeo.in/index.php/mapview/iwlms";
			    RestTemplate restTemplate = new RestTemplate();
			    JSONresult= restTemplate.postForEntity(uri, requestEntity,String.class).getBody().toString();
			    
			    JSONObject json =  new JSONObject(JSONresult);
			    if (json.has("post")) {
			    	postdata = json.getString("post");
			    }
			    JSONObject postjson =  new JSONObject(postdata);
				    if (postjson.has("beat_boundary")) {
				    	beat_boundary = postjson.getString("beat_boundary");		
				    } 
				    if(!beat_boundary.isEmpty()) {
				    	JSONObject beatjson =  new JSONObject(beat_boundary);
				    	 if (beatjson.has("beat_id")) {
				    		 beat_id = beatjson.getString("beat_id").toString();
				    	 } if (beatjson.has("f_division_id")) {
				    		 f_division_id=beatjson.getString("f_division_id").toString();
				    	 } if (beatjson.has("f_range_id")) {
				    		 f_range_id=beatjson.getString("f_range_id").toString();
				    	 } if (beatjson.has("f_section_id")) {
				    		 f_section_id=beatjson.getString("f_section_id").toString();
				    	 }	 
				     }	
			         // List<Object> aLlCordinateDetails = mergeBeatRepository.getALlCordinateDetails(Double.parseDouble(reportReq.getLongitude()), Double.parseDouble(reportReq.getLatitude()));
					if (!beat_id.isEmpty()) {
						try {
							    beatid=Integer.parseInt(beat_id);
								reportDO.setDivision(divisionRepository.findById(Integer.parseInt(f_division_id)).get());
								reportDO.setRange(rangeRepository.findById(Integer.parseInt(f_range_id)).get());
								reportDO.setSection(sectionRepository.findById(Integer.parseInt(f_section_id)).get());
								reportDO.setBeat(beatRepository.findById(Integer.parseInt(beat_id)).get());
						    } catch (Exception e) {
							throw new IncorrectPositionException("You are not in a correct position");
						}
					}else {
						throw new IncorrectPositionException("You are not in a correct position");
					}
				} catch (Exception e) {
					throw new IncorrectPositionException("Error in Fetching Data");
				}
		     } else {
				if (!reportReq.getBeat().isEmpty())
					reportDO.setBeat(beatRepository.findById(Integer.parseInt(reportReq.getBeat())).get());
				if (!reportReq.getSection().isEmpty())
					reportDO.setSection(sectionRepository.findById(Integer.parseInt(reportReq.getSection())).get());
				if (!reportReq.getRange().isEmpty())
					reportDO.setRange(rangeRepository.findById(Integer.parseInt(reportReq.getRange())).get());
				if (!reportReq.getDivision().isEmpty())
					reportDO.setDivision(divisionRepository.findById(Integer.parseInt(reportReq.getDivision())).get());
				beatid=Integer.parseInt(reportReq.getBeat());
			}
		if (!reportReq.getAccuracy().isEmpty())
			reportDO.setAccuracy(Double.parseDouble(reportReq.getAccuracy()));
		if (!reportReq.getAltitude().isEmpty())
			reportDO.setAltitude(Double.parseDouble(reportReq.getAltitude()));
		if (!reportReq.getCalf().isEmpty())
			reportDO.setCalf(Integer.parseInt(reportReq.getCalf()));
		if (!reportReq.getFemale().isEmpty())
			reportDO.setFemale(Integer.parseInt(reportReq.getFemale()));
		if (!reportReq.getHeard().isEmpty())
			reportDO.setHeard(Integer.parseInt(reportReq.getHeard()));
		reportDO.setIsActive(true);
		if (!reportReq.getLatitude().isEmpty()) {
			reportDO.setLatitude(Double.parseDouble(reportReq.getLatitude()));
		}else {
			reportDO.setLatitude(Double.parseDouble("0"));
		}
			
		if (!reportReq.getLongitude().isEmpty()) {
			reportDO.setLongitude(Double.parseDouble(reportReq.getLongitude()));
		}else {
			reportDO.setLongitude(Double.parseDouble("0"));
		}
			
		if (!reportReq.getLocation().isEmpty())
			reportDO.setLocation(reportReq.getLocation());
		if (!reportReq.getMukhna().isEmpty())
			reportDO.setMukhna(Integer.parseInt(reportReq.getMukhna()));
		if (!reportReq.getReport_through().isEmpty())
			reportDO.setReportThrough(reportReq.getReport_through());
		if (!reportReq.getReport_type().isEmpty())
			reportDO.setReportType(reportReq.getReport_type());
		if (!reportReq.getRemarks().isEmpty())
			reportDO.setRemarks(reportReq.getRemarks());
		if (!reportReq.getIndirectreportType().isEmpty())
			reportDO.setIndirectReportType(reportReq.getIndirectreportType());
		reportDO.setSyncDate(new Date());
		reportDO.setCreatedOn(new Date());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			Date date1 = dateFormat.parse(reportReq.getSightingTimefrom());
			Date date2 = dateFormat.parse(reportReq.getSightingTimeTo());
			Date date3 = dateFormat.parse(reportReq.getSightingDate());
			reportDO.setSightingTimefrom(date1);
			reportDO.setSightingTimeTo(date2);
			reportDO.setSightingDate(date3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!reportReq.getTotal().isEmpty())
			reportDO.setTotal(Integer.parseInt(reportReq.getTotal()));
		if (!reportReq.getTusker().isEmpty())
			reportDO.setTusker(Integer.parseInt(reportReq.getTusker()));
		if (!reportReq.getUser().isEmpty())
			reportDO.setUpdatedBy(Integer.parseInt(reportReq.getUser()));
		if (!reportReq.getUser().isEmpty())
			reportDO.setSurveyorUserId(Integer.parseInt(reportReq.getUser()));
		if (!reportReq.getUser().isEmpty())
			reportDO.setCreatedBy(Integer.parseInt(reportReq.getUser()));
		if (isDuplicate(reportReq.getReport_type(),reportReq.getSightingTimefrom(),beatid,reportReq.getTotal(),reportReq.getHeard(),reportReq.getTusker(),reportReq.getMukhna(),reportReq.getFemale(),reportReq.getCalf())) {
			reportDO.setIsDuplicate("d");
		}
		if (!file.isEmpty())
			reportDO.setImgAcqlocation(file);
		ReportDO report=reportRepository.save(reportDO);
		try {
		if(report!=null) {
			if(( report.getLatitude()!=0)&&(report.getLongitude()!=0)) {
					//TriggerSMS
				try {
					    fetchExternalAPi(JSONresult,report);
					}catch(Exception ex) {
						
					}
				 ///Vulnerability
				
				  List<Object> getdivList = reportRepositoryCustom.getVulenarableDivRailIntersect(Double.toString(report.getLatitude()),Double. toString(report.getLongitude()));
					if (!getdivList.isEmpty()) {
						
						List<String> listDiv=new ArrayList<String>();
				        Iterator itr = getdivList.iterator();
						while (itr.hasNext()) {
							Object[] obj = (Object[]) itr.next();
							listDiv.add(obj[0].toString());
						}
						for(String x :listDiv) {
							if(x.equals(report.getDivision().getDivisionId().toString())) {
								VulnerabilityDO vdo=new VulnerabilityDO();
								vdo.setReport(reportRepository.findById(report.getReportId()).get());
								vdo.setReportDate(report.getSightingDate());
							    vdo.setReportType(report.getReportType());
							    vdo.setCreatedOn(new Date());
							    vdo.setUpdatedOn(new Date());
							    vdo.setIsActive(true);
								 if(report.getBeat().getBeatId()!=null) 
									vdo.setBeat(beatRepository.findById(report.getBeat().getBeatId()).get());
									//vdo.setBeatCode(report.getBeat().getBeatId());
							    if(report.getSection().getSecId()!=null)
							    	vdo.setSection(sectionRepository.findById(report.getSection().getSecId()).get());
									//vdo.setSecCode(report.getSection().getSecId());
								if(report.getRange().getRangeId()!=null) 
									vdo.setRange(rangeRepository.findById(report.getRange().getRangeId()).get());
									//vdo.setRngCode(report.getRange().getRangeId());
							    if(report.getDivision().getDivisionId()!=null)
							    {
							    	vdo.setDivision(divisionRepository.findById(report.getDivision().getDivisionId()).get());
							    	//vdo.setDivnCode(report.getDivision().getDivisionId());
									 DivisionMasterDO findCircleByDivisionId = divisionRepository.findCircleByDivisionId(report.getDivision().getDivisionId());
									 vdo.setCircle(circleMasterRepository.findById(findCircleByDivisionId.getCircle().getCircleId()).get());
									// vdo.setCircleCode(findCircleByDivisionId.getCircle().getCircleId());
							    }
							    vulnerabilityRepository.save(vdo);  
							}
						}
				      }
				  } 
		}
	}catch(Exception ex){}
	return report;
	}
	 
		
	private boolean isDuplicate(String report_type, String dateString, Integer beatid, String total,String Heard,String Tusker,String Mukhna,String Female,String Calf) {
		String dt="";
		try {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	    Date d1 = df.parse(dateString);
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		dt = simpleDateFormat.format(d1);
	} catch (ParseException e) {
		e.printStackTrace();
	}
		List<Object[]> getduplicatereport = reportRepositoryCustom.getduplicatereport(report_type, dt,beatid,total,Heard,Tusker,Mukhna,Female,Calf);
		if(!getduplicatereport.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public List<LatLongResponse> geAllLatlong(String division) {
		List<LatLongResponse> list = new ArrayList<LatLongResponse>();
		if (!division.isEmpty() && division.equals("All")) {
			List<Object[]> findAllLatLong = reportRepository.findAllLatLong();

			for (Object[] objects : findAllLatLong) {
				for (int i = 0; i < objects.length; i++) {
					LatLongResponse latLongResponse = new LatLongResponse();
					if (objects[i] != null)
						latLongResponse.setLatitude(objects[i].toString());
					if (objects[i] != null)
						latLongResponse.setLongitude(objects[++i].toString());
					list.add(latLongResponse);
				}
			}
		} else if (!division.isEmpty() && !division.equals("All")) {
			List<Object[]> findAllLatLongByDivId = reportRepository.findAllLatLongByDivId(Integer.parseInt(division));
			for (Object[] objects : findAllLatLongByDivId) {
				for (int i = 0; i < objects.length; i++) {
					LatLongResponse latLongResponse = new LatLongResponse();
					if (objects[i] != null)
						latLongResponse.setLatitude(objects[i].toString());
					if (objects[i] != null)
						latLongResponse.setLongitude(objects[++i].toString());
					list.add(latLongResponse);
				}
			}
		}
		return list;
	}

	@Override
	public List<ReportResponse> viewReport(String reportType,String division, String startDate, String endDate,String userId) 
	{
		List<ReportResponse> list = new ArrayList<ReportResponse>();
		List<ReportDO> reportList = null;

		if((!reportType.isEmpty()) && (!division.isEmpty()) && (!startDate.isEmpty()) && (!endDate.isEmpty())&& (!userId.isEmpty())) 
		{
			DateFormat formatter;
			Date stDate = null;
			Date enDate = null;
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				stDate = formatter.parse(startDate);
				enDate = formatter.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findByUserIdAndStDateAndEndDate(stDate, enDate, Integer.parseInt(userId));
			} else if (division.equals("All") && !reportType.equals("All")) {
				reportList = reportRepository.findByUserIdAndStDateAndEndDateAndReportType(reportType, stDate, enDate,
						Integer.parseInt(userId));
			} else if (!division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findByUserIdAndStDateAndEndDateAndDivision(Integer.parseInt(division),
						stDate, enDate, Integer.parseInt(userId));
			} else {
				reportList = reportRepository.findByDivisionAndUserIdAndStDateAndEndDateAndReportType(reportType,
						Integer.parseInt(division), stDate, enDate, Integer.parseInt(userId));
			}
		} 
		  
		 else if ((!reportType.isEmpty()) && (!division.isEmpty()) && (!userId.isEmpty()) && (startDate.isEmpty())&& (endDate.isEmpty())) 
		 {
			Date enDate = null;
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, -2);
			enDate = c.getTime();
			if (division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findByUserIdAndEndDate(enDate, Integer.parseInt(userId));
			} else if (division.equals("All") && !reportType.equals("All")) {
				reportList = reportRepository.findByUserIdAndReportType(reportType, enDate, Integer.parseInt(userId));
			} else if (!division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findByUserIdAndDivison(Integer.parseInt(division), enDate,
						Integer.parseInt(userId));
			} else {
				reportList = reportRepository.findByDivisionAndUserIdAndReportType(reportType,
						Integer.parseInt(division), enDate, Integer.parseInt(userId));
			}

		} 
		 else if ((!reportType.isEmpty()) && (!division.isEmpty()) && (!startDate.isEmpty()) && (!endDate.isEmpty())&& (userId.isEmpty())) 
		 {
			DateFormat formatter;
			Date stDate = null;
			Date enDate = null;
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				stDate = formatter.parse(startDate);
				enDate = formatter.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findByStDateAndEndDate(stDate, enDate);
			} else if (division.equals("All") && !reportType.equals("All")) {
				reportList = reportRepository.findByStDateAndEndDateAndReportType(reportType, stDate, enDate);
			} else if (!division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findByStDateAndEndDateAndDivision(Integer.parseInt(division), stDate,
						enDate);
			} else {
				reportList = reportRepository.findByDivisionAndStDateAndEndDateAndReportType(reportType,
						Integer.parseInt(division), stDate, enDate);
			}

		} 
		 else if ((!reportType.isEmpty()) && (!division.isEmpty()) && (startDate.isEmpty()) && (endDate.isEmpty())&& (userId.isEmpty())) 
		 {
			Date enDate = null;
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, -2);
			enDate = c.getTime();
			if (division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findAllByDate(enDate);
			} else if (division.equals("All") && !reportType.equals("All")) {
				reportList = reportRepository.findByAllAndReportType(reportType, enDate);
			} else if (!division.equals("All") && reportType.equals("All")) {
				reportList = reportRepository.findAllByDivision(Integer.parseInt(division), enDate);
			} else {
				reportList = reportRepository.findByDivisionAndReportType(reportType, Integer.parseInt(division),
						enDate);
			}

		} else {
			throw new ReportException();
		}
		for (ReportDO reportDO : reportList) {
			ReportResponse reportREsponse = new ReportResponse();
			if (!reportDO.getReportType().equals("nil")) {
				reportREsponse.setId(reportDO.getReportId().toString());
				reportREsponse.setAccuracy("" + reportDO.getAccuracy());
				reportREsponse.setAltitude("" + reportDO.getAltitude());
				reportREsponse.setBeatName(reportDO.getBeat().getBeatName());
				reportREsponse.setCalf("" + reportDO.getCalf());
				reportREsponse.setCreatedBy("" + reportDO.getSurveyorUserId());
				reportREsponse.setCreatedOn("" + reportDO.getCreatedOn());
				reportREsponse.setDivisionName(reportDO.getDivision().getDivisionName());
				reportREsponse.setFemale("" + reportDO.getFemale());
				reportREsponse.setHeard("" + reportDO.getHeard());
				reportREsponse.setIsActive("" + reportDO.getIsActive());
				reportREsponse.setLatitude("" + reportDO.getLatitude());
				reportREsponse.setLocation(reportDO.getLocation());
				reportREsponse.setLongitude("" + reportDO.getLongitude());
				reportREsponse.setMukhna("" + reportDO.getMukhna());
				reportREsponse.setRangeName(reportDO.getRange().getRangeName());
				reportREsponse.setReportThrough(reportDO.getReportThrough());
				reportREsponse.setReportType(reportDO.getReportType());
				reportREsponse.setSecName(reportDO.getSection().getSecName());
				reportREsponse.setSightingDate("" + reportDO.getSightingDate());
				reportREsponse.setSightingTimefrom("" + reportDO.getSightingTimefrom());
				reportREsponse.setSightingTimeTo("" + reportDO.getSightingTimeTo());
				reportREsponse.setSurveyorUserId("" + reportDO.getSurveyorUserId());
				reportREsponse.setTusker("" + reportDO.getTusker());
				reportREsponse.setTotal("" + reportDO.getTotal());
				reportREsponse.setDuplicateReport("" + reportDO.getIsDuplicate());
				reportREsponse.setUpdatedBy("" + reportDO.getUpdatedBy());
				reportREsponse.setUpdatedOn("" + reportDO.getUpdatedOn());
				reportREsponse.setImgAcqlocation(reportDO.getImgAcqlocation());
				
			} else {
				reportREsponse.setId(reportDO.getReportId().toString());
				reportREsponse.setDivisionName(reportDO.getDivision().getDivisionName());
				reportREsponse.setLocation(reportDO.getLocation());
				reportREsponse.setRangeName(reportDO.getRange().getRangeName());
				reportREsponse.setSecName(reportDO.getSection().getSecName());
				reportREsponse.setBeatName(reportDO.getBeat().getBeatName());
				reportREsponse.setSightingTimefrom("" + reportDO.getSightingTimefrom());
				reportREsponse.setSightingTimeTo("" + reportDO.getSightingTimeTo());
				reportREsponse.setRemarks(reportDO.getRemarks());
				reportREsponse.setReportType(reportDO.getReportType());
				reportREsponse.setDuplicateReport("" + reportDO.getIsDuplicate());
				reportREsponse.setReportThrough(reportDO.getReportThrough());
				reportREsponse.setSurveyorUserId("" + reportDO.getSurveyorUserId());
			}

			list.add(reportREsponse);
		}
		return list;
	}

	@Override
	public ReportResponse viewReportById(String id) {
		Optional<ReportDO> findById = reportRepository.findById(Integer.parseInt(id));

		ReportResponse reportREsponse = null;
		if (findById.isPresent()) {
			ReportDO reportDO = findById.get();
			reportREsponse = new ReportResponse();
			reportREsponse.setId(reportDO.getReportId().toString());
			reportREsponse.setAccuracy("" + reportDO.getAccuracy());
			reportREsponse.setAltitude("" + reportDO.getAltitude());
			reportREsponse.setBeatName(reportDO.getBeat().getBeatName());
			reportREsponse.setCalf("" + reportDO.getCalf());
			reportREsponse.setCreatedBy("" + reportDO.getSurveyorUserId());
			reportREsponse.setCreatedOn("" + reportDO.getCreatedOn());
			reportREsponse.setDivisionName(reportDO.getDivision().getDivisionName());
			reportREsponse.setFemale("" + reportDO.getFemale());
			reportREsponse.setHeard("" + reportDO.getHeard());
			reportREsponse.setIsActive("" + reportDO.getIsActive());
			reportREsponse.setLatitude("" + reportDO.getLatitude());
			reportREsponse.setLocation(reportDO.getLocation());
			reportREsponse.setLongitude("" + reportDO.getLongitude());
			reportREsponse.setMukhna("" + reportDO.getMukhna());
			reportREsponse.setRangeName(reportDO.getRange().getRangeName());
			reportREsponse.setReportThrough(reportDO.getReportThrough());
			reportREsponse.setReportType(reportDO.getReportType());
			reportREsponse.setSecName(reportDO.getSection().getSecName());
			reportREsponse.setSightingDate("" + reportDO.getSightingDate());
			reportREsponse.setSightingTimefrom("" + reportDO.getSightingTimefrom());
			reportREsponse.setSightingTimeTo("" + reportDO.getSightingTimeTo());
			reportREsponse.setSurveyorUserId("" + reportDO.getSurveyorUserId());
			reportREsponse.setTusker("" + reportDO.getTusker());
			reportREsponse.setTotal("" + reportDO.getTotal());
			reportREsponse.setDuplicateReport("" + reportDO.getIsDuplicate());
			reportREsponse.setUpdatedBy("" +(authRepository.findById((Integer)reportDO.getUpdatedBy()).get().getUsername()));
			reportREsponse.setUpdatedOn("" + reportDO.getUpdatedOn());
			reportREsponse.setImgAcqlocation(reportDO.getImgAcqlocation());
		}

		return reportREsponse;
	}

	@Override
	public int getAllReportCount(String division) {
		int count = 0;
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllCount();
			else
				count = reportRepository.getCountByDivision(Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllReportCountDirect(String division) {
		int count = 0;
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCountDirect();
			else
				count = reportRepository.getAllReportCountDirectByDiv(Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllReportCountInDirect(String division) {
		int count = 0;
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCountInDirect();
			else
				count = reportRepository.getAllReportCountInDirectByDiv(Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllNillReportCount(String division) {
		int count = 0;
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCounNill();
			else
				count = reportRepository.getAllReportCountInNillByDiv(Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllReportCountIn24Hrs(String division) {
		int count = 0;
		Date enDate = null;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -2);
		enDate = c.getTime();
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllCountIn24Hrs(enDate);
			else
				count = reportRepository.getCountByDivisionIn24Hrs(enDate, Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllReportCountDirectIn24Hrs(String division) {
		int count = 0;
		Date enDate = null;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -2);
		enDate = c.getTime();
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCountDirectIn24Hrs(enDate);
			else
				count = reportRepository.getAllReportCountDirectByDivIn24Hrs(enDate, Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllReportCountInDirectIn24Hrs(String division) {
		int count = 0;
		Date enDate = null;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -2);
		enDate = c.getTime();
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCountInDirectIn24Hrs(enDate);
			else
				count = reportRepository.getAllReportCountInDirectByDivIn24Hrs(enDate, Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllNillReportCountIn24Hrs(String division) {
		int count = 0;
		Date enDate = null;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -2);
		enDate = c.getTime();
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCounNillIn24Hrs(enDate);
			else
				count = reportRepository.getAllReportCountInNillByDivIn24Hrs(enDate, Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllElephantDeathCountIn24Hrs(String division) {
		int count = 0;
		Date enDate = null;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -2);
		enDate = c.getTime();
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCountElephantDeathIn24Hrs(enDate);
			else
				count = reportRepository.getAllReportCountElephantDeathByDivIn24Hrs(enDate, Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public int getAllFireAlertCountIn24Hrs(String division) {
		int count = 0;
		Date enDate = null;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -2);
		enDate = c.getTime();
		if ((!division.isEmpty())) {
			if (division.equals("All"))
				count = reportRepository.getAllReportCountIncidentFireAlertIn24Hrs(enDate);
			else
				count = reportRepository.getAllReportCountIncidentFireAlertByDivIn24Hrs(enDate,
						Integer.parseInt(division));
		}
		return count;
	}

	@Override
	public List<LatLongResponse> geAllLatlongg(String division, String range, String section, String beat, String query,
			String startDate, String endDate) {
		List<LatLongResponse> latLongRes = new ArrayList<LatLongResponse>();
		List<Object> latLongByDivision = null;
		if (!division.isEmpty() && !query.isEmpty() && !startDate.isEmpty() && !startDate.isEmpty() && range.isEmpty()
				&& section.isEmpty() && beat.isEmpty()) {
			if (division.equals("all")) {
				latLongByDivision = reportRepositoryCustom.getLatLong(query, startDate, endDate);
			} else {
				latLongByDivision = reportRepositoryCustom.getLatLongByDivision(division, query, startDate, endDate);
			}

		} else if (!division.isEmpty() && !query.isEmpty() && !startDate.isEmpty() && !startDate.isEmpty()
				&& !range.isEmpty() && section.isEmpty() && beat.isEmpty()) {
			if (range.equals("all")) {
				latLongByDivision = reportRepositoryCustom.getLatLongByDivision(division, query, startDate, endDate);
			} else {
				latLongByDivision = reportRepositoryCustom.getLatLongByDivisionAndRange(division, range, query,
						startDate, endDate);
			}

		} else if (!division.isEmpty() && !query.isEmpty() && !startDate.isEmpty() && !startDate.isEmpty()
				&& !range.isEmpty() && !section.isEmpty() && beat.isEmpty()) {
			if (section.equals("all")) {
				latLongByDivision = reportRepositoryCustom.getLatLongByDivisionAndRange(division, range, query,
						startDate, endDate);
			} else {
				latLongByDivision = reportRepositoryCustom.getLatLongByDivisionAndRangeAndSec(division, range, section,
						query, startDate, endDate);
			}

		} else if (!division.isEmpty() && !query.isEmpty() && !startDate.isEmpty() && !startDate.isEmpty()
				&& !range.isEmpty() && !section.isEmpty() && !beat.isEmpty()) {
			if (beat.equals("all")) {
				latLongByDivision = reportRepositoryCustom.getLatLongByDivisionAndRangeAndSec(division, range, section,
						query, startDate, endDate);
			} else {
				latLongByDivision = reportRepositoryCustom.getLatLongByDivisionAndRangeAndSectAndBeat(division, range,
						section, beat, query, startDate, endDate);
			}
		}
		if (latLongByDivision != null) {
			Iterator<Object> iterator = latLongByDivision.iterator();
			while (iterator.hasNext()) {
				Object[] next = (Object[]) iterator.next();
				LatLongResponse latLong = new LatLongResponse();
				latLong.setLatitude(String.valueOf(next[1]));
				latLong.setLongitude(String.valueOf(next[0]));
				latLongRes.add(latLong);
			}
		}
		return latLongRes;
	}

	@Override
	public void triggerSMS(String longitude, String latitude) {
		try {
			Random rand = new Random();
		List<Object> triggerSms = smstriggerRepositoryCustom.triggerSms(longitude, latitude);
		Iterator<Object> iterator = triggerSms.iterator();
		while (iterator.hasNext()) {
			Object[] next = (Object[]) iterator.next();
			if (String.valueOf(next[1]).equals("true")) {
				List<String> findMobileByDivision = authRepository.findMobileByDivisionId(Integer.parseInt(String.valueOf(next[0])));
				if(!findMobileByDivision.isEmpty())
				{
					for (String mobile : findMobileByDivision) 
					{
							try {
								
								String Otp =String.format("%04d", rand.nextInt(10000));
								String Username = "suru.mistry007@gmail.com";
								String apiKey = "&apikey="
										+ URLEncoder.encode("UOMDAJ+z8HI-kFedKgMqcKVC8xpOolCny9DxlRtbiT", "UTF-8");
								String message = "&message=" + URLEncoder.encode("Your FNGO App Login OTP is : " + Otp + "");
								String sender = "&sender=" + URLEncoder.encode("SPARCS", "UTF-8");
								String numbers = "&numbers=" + URLEncoder.encode(mobile, "UTF-8");
								// Send data
								String data = "http://sms.webadd.in/api2/send/?username=" + Username + apiKey + numbers + message
										+ sender;
								URL url = new URL(data);
								URLConnection conn = url.openConnection();
								conn.setDoOutput(true);
								// Get the response
								BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
								String line;
								String sResult = "";
								while ((line = rd.readLine()) != null) {
									// Process line...
									sResult = sResult + line + " ";
								}
								rd.close();
								// stringBuffer.toString();
							} catch (Exception e) {
								System.out.println("Error SMS " + e);
								// return "Error "+e;
							}
			          }
		         }
			}
		}
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			// return "Error "+e;
		}
	}

	@Override
	public List<LatLongResponse> getAllLatLong() {
		int count = 0;
		Date enDate = null;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		enDate = c.getTime();
	List<Object[]> findAllLatLongByDate = reportRepository.findAllLatLongByDate(enDate);
	List<LatLongResponse> list = new ArrayList<LatLongResponse>();
		for (Object[] objects : findAllLatLongByDate) {
			for (int i = 0; i < objects.length; i++) {
				LatLongResponse latLongResponse = new LatLongResponse();
				if (objects[i] != null)
					latLongResponse.setLatitude(objects[i].toString());
				if (objects[i] != null)
					latLongResponse.setLongitude(objects[++i].toString());
				list.add(latLongResponse);
			}
		}
		return list;
	}

	@Override
	public AllReportCountDto getAllReportCountIn24Hrsss(String circle, String division, String range,String startDate, String endDate) 
	{
		List<Object> allReportCountIn24Hrsss = reportRepositoryCustom.getAllReportCountIn24Hrsss(circle,division,range,startDate,endDate);
		AllReportCountDto allReportCountDto=new AllReportCountDto();
		Iterator<Object> iterator = allReportCountIn24Hrsss.iterator();
		while (iterator.hasNext()) {
			Object[] next = (Object[]) iterator.next();
			if(next[0]!= null) 
			{
			    allReportCountDto.setAllReportCount((BigInteger) next[0]);
			}else 
			{
				allReportCountDto.setAllReportCount(0);
			}
			if(next[1]!= null) 
			{
			    allReportCountDto.setAllReportCountDirect((BigInteger) next[1]);
			}else
			{
				allReportCountDto.setAllReportCountDirect(0);
			}
			if(next[2]!= null) 
			{
				allReportCountDto.setAllReportCountInDirect((BigInteger) next[2]);
			}else 
			{
				allReportCountDto.setAllReportCountInDirect(0);
			}
			if(next[3]!= null) {
				allReportCountDto.setNillReportCount((BigInteger) next[3]);
			}else 
			{
				allReportCountDto.setNillReportCount(0);
			}
			if(next[4]!= null)
			{
				allReportCountDto.setAllElephantDeathReportCount((BigInteger) next[4]);
			}else 
			{
				allReportCountDto.setAllElephantDeathReportCount(0);
			}
			if(next[5]!= null) {
				allReportCountDto.setAllFireAlertReportCount((BigInteger) next[5]);
			}
			else 
			{
				allReportCountDto.setAllFireAlertReportCount(0);
			}
			if(next[6]!= null) 
			{
				allReportCountDto.setAllVulnerabilityCount((BigInteger) next[6]);
			}else 
			{
				allReportCountDto.setAllVulnerabilityCount(0);
			}
			
		}
		return allReportCountDto;
	}
///update on 25-02-2021 for start date and end date
	@Override
	public List<ReportResponse> viewReportLast24hrs(String circle, String division, String range,String startDate, String endDate) {
//		Date enDate = null;
//		Date date = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.add(Calendar.DATE, -1);
//		enDate = c.getTime();
		List<Object[]> viewReportLast24hrs = reportRepositoryCustom.viewReportLast24hrs(circle, division, range,startDate,endDate);
		List<ReportResponse> list = new ArrayList<ReportResponse>();
		for (Object[] objects : viewReportLast24hrs) {
				int i=0;
				ReportResponse reportResponse = new ReportResponse();
				if (objects[i] != null)
					reportResponse.setId(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setAccuracy(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setAltitude(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setCalf(objects[i].toString());
				++i;//CreatedBy
				//if (objects[++i] != null)reportResponse.setCreatedBy(authRepository.findById((Integer)objects[i]).get().getUsername());
				if (objects[++i] != null)
					reportResponse.setCreatedOn(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setFemale(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setHeard(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setImgAcqlocation(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setIndirectReportType(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setIsActive(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setDuplicateReport(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setLatitude(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setLocation(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setLongitude(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setMukhna(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setRemarks(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setReportThrough(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setReportType(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setSightingDate(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setSightingTimeTo(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setSightingTimefrom(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setCreatedBy(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setTotal(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setTusker(objects[i].toString());
				if (objects[++i] != null)//UpdateBy
				reportResponse.setUpdatedBy(objects[i].toString());
				//if (objects[++i] != null)reportResponse.setUpdatedBy(authRepository.findById((Integer)objects[i]).get().getUsername());
				if (objects[++i] != null)//UpdateOn
				reportResponse.setUpdatedOn(objects[i].toString());
				//if (objects[++i] != null)reportResponse.setUpdatedOn(authRepository.findById((Integer)objects[i]).get().getUsername());
				++i;//BeatId
				//if (objects[++i] != null)reportResponse.setBeatName(beatRepository.findById((Integer)objects[i]).get().getBeatName());				
				//divisionId
				//if (objects[++i] != null)reportResponse.setDivisionName(divisionRepository.findById((Integer)objects[i]).get().getDivisionName());
				if (objects[++i] != null)reportResponse.setDivisionId(objects[i].toString());
				++i;//rangeId
				//if (objects[++i] != null)reportResponse.setRangeName(rangeRepository.findById((Integer)objects[i]).get().getRangeName());			
				++i;//sectionId
				//if (objects[++i] != null)reportResponse.setSecName(sectionRepository.findById((Integer)objects[i]).get().getSecName());	
				if (objects[++i] != null)
					reportResponse.setCreatedBy(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setBeatName(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setDivisionName(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setRangeName(objects[i].toString());
				if (objects[++i] != null)
					reportResponse.setSecName(objects[i].toString());
				
				list.add(reportResponse);
			}
		return list;
	}

	@Override
	public List<ReportResponse> viewReport(String reportType, String circle, String division, String range,String section, String beat, String startDate, String endDate) 
	{
		List<ReportResponse> list = new ArrayList<ReportResponse>();
		List<Object[]> viewElephantReport = reportRepositoryCustom.viewElephantReport(reportType, circle, division,range, section, beat, startDate, endDate);
		for (Object[] objects : viewElephantReport) {
			int i = 0;
			ReportResponse reportResponse = new ReportResponse();
			if (objects[i] != null)
				reportResponse.setId(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setAccuracy(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setAltitude(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setCalf(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setCreatedBy(authRepository.findById((Integer) objects[i]).get().getUsername());
			if (objects[++i] != null)
				reportResponse.setCreatedOn(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setFemale(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setHeard(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setImgAcqlocation(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setIndirectReportType(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setIsActive(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setDuplicateReport(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setLatitude(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setLocation(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setLongitude(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setMukhna(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setRemarks(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setReportThrough(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setReportType(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setSightingDate(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setSightingTimeTo(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setSightingTimefrom(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setCreatedBy(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setTotal(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setTusker(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setUpdatedBy(authRepository.findById((Integer) objects[i]).get().getUsername());
			if (objects[++i] != null)
				reportResponse.setUpdatedOn(objects[i].toString());
			if (objects[++i] != null)
				reportResponse.setBeatName(beatRepository.findById((Integer) objects[i]).get().getBeatName());
			if (objects[++i] != null) {
				reportResponse.setDivisionName(divisionRepository.findById((Integer) objects[i]).get().getDivisionName());
				reportResponse.setDivisionId(objects[i].toString());
			}				
			if (objects[++i] != null)
				reportResponse.setRangeName(rangeRepository.findById((Integer) objects[i]).get().getRangeName());
			if (objects[++i] != null)
				reportResponse.setSecName(sectionRepository.findById((Integer) objects[i]).get().getSecName());
			
			list.add(reportResponse);
		}
		return list;
	}

	@Override
	public List<ReportResponse> viewReports(Integer surveyorUserId, String sightingDateFrom, String sightingDateTo) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date from=null;
		Date to=null;
		try {
			 from = dateFormat.parse(sightingDateFrom);
			 to = dateFormat.parse(sightingDateTo);
		     } 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		List<ReportResponse> list = new ArrayList<ReportResponse>();
		
	    List<ReportDO> findAllBySurveyorUserId = null;
		if (surveyorUserId != null && sightingDateFrom.isEmpty() && sightingDateTo.isEmpty())
	    {
			findAllBySurveyorUserId= reportRepository.findAllBySurveyorUserId(surveyorUserId);
	    }else
	    {
	    	findAllBySurveyorUserId=reportRepository.findAllBysurveyorUserIdAndSightingDateBetween(surveyorUserId, from, to);
	    }
		
		for(ReportDO reportDO:findAllBySurveyorUserId)
		{
			ReportResponse response = new ReportResponse();
			response.setId(reportDO.getReportId().toString());
			response.setAccuracy(reportDO.getAccuracy()+"");
			response.setAltitude(reportDO.getAltitude()+"");
			if(reportDO.getBeat()!=null)
			response.setBeatName(reportDO.getBeat().getBeatName());
			if(reportDO.getCalf()!=null)
			response.setCalf(reportDO.getCalf().toString());
			if(reportDO.getCreatedBy()!=null)
			response.setCreatedBy(reportDO.getCreatedBy().toString());
			if(reportDO.getHeard()!=null)
			response.setHeard(reportDO.getHeard().toString());
			if(reportDO.getImgAcqlocation()!=null)
			response.setImgAcqlocation(reportDO.getImgAcqlocation());
			if(reportDO.getIndirectReportType()!=null)
			response.setIndirectReportType(reportDO.getIndirectReportType());
			if(reportDO.getIsActive()!=null)
			response.setIsActive(reportDO.getIsActive().toString());
			response.setLatitude(reportDO.getLatitude()+"");
			if(reportDO.getLocation()!=null)
			response.setLocation(reportDO.getLocation());
			response.setLongitude(reportDO.getLongitude()+"");
			if(reportDO.getMukhna()!=null)
			response.setMukhna(reportDO.getMukhna().toString());
			if(reportDO.getRange()!=null)
			response.setRangeName(reportDO.getRange().getRangeName());
			if(reportDO.getDivision()!=null) 
			{
				response.setDivisionId(reportDO.getDivision().getDivisionId().toString());
				response.setDivisionName(reportDO.getDivision().getDivisionName());
			}		
			if(reportDO.getRemarks()!=null)
			response.setRemarks(reportDO.getRemarks());
			if(reportDO.getReportThrough()!=null)
			response.setReportThrough(reportDO.getReportThrough());
			if(reportDO.getReportType()!=null)
			response.setReportType(reportDO.getReportType());
			if(reportDO.getSection()!=null)
			response.setSecName(reportDO.getSection().getSecName());
			if(reportDO.getSightingDate()!=null)
			response.setSightingDate(reportDO.getSightingDate().toString());
			if(reportDO.getSightingTimefrom()!=null)
			response.setSightingTimefrom(reportDO.getSightingTimefrom().toString());
			if(reportDO.getSightingTimeTo()!=null)
			response.setSightingTimeTo(reportDO.getSightingTimeTo().toString());
			if(reportDO.getSurveyorUserId()!=null)
			response.setSurveyorUserId(reportDO.getSurveyorUserId().toString());
			if(reportDO.getTotal()!=null)
			response.setTotal(reportDO.getTotal().toString());
			if(reportDO.getIsDuplicate()!=null)
			response.setDuplicateReport(reportDO.getIsDuplicate().toString());
			response.setTusker(reportDO.getTusker()+"");
			response.setUpdatedBy(authRepository.findById((Integer)reportDO.getUpdatedBy()).get().getUsername());
			if(reportDO.getUpdatedOn()!=null) 
			response.setUpdatedOn(reportDO.getUpdatedOn().toString());
			if(reportDO.getCreatedOn()!=null) 
			response.setCreatedOn(reportDO.getCreatedOn().toString());
			response.setFemale(reportDO.getFemale()+"");
			list.add(response);
		}
		
		return list.stream().sorted(Comparator.comparing(ReportResponse::getSightingDate).reversed()).collect(Collectors.toList());
	}
	
	@Override
	public List<IncidentReportCountResponse> ViewAllVulnerabilityReport(String circleCode,String divnCode, String rngCode, String startDate,String endDate) 
	{
		List<IncidentReportCountResponse> list=new ArrayList<IncidentReportCountResponse>();
	    if(circleCode!=null && divnCode!=null && rngCode!=null && startDate!=null && endDate!=null)
	    {
	    	//VulnerabilityDTO vulnerabilityDTO=new VulnerabilityDTO();
	    	List<Object> vulnerableElephant = reportRepositoryCustom.getVulnerableElephant(circleCode, divnCode, rngCode, startDate, endDate);
	    	IncidentReportCountResponse incidentReportResponse = new IncidentReportCountResponse();
	    	vulnerableElephant.stream().collect(Collectors.toList());
//	    	Iterator<Object> iterator = vulnerableElephant.iterator();
//			while (iterator.hasNext()) 
//			{
//				Object objects = (Object) iterator.next();
//				IncidentReportCountResponse incidentReportResponse = new IncidentReportCountResponse();
//				incidentReportResponse.setDivisionName(divisionRepository.findDivisionNameByDivisionId(Integer.parseInt(divnCode)).getDivisionName());
//				//incidentReportResponse.setRangeName(rangeRepository.findRangeNameByDivisionId(Integer.parseInt(divnCode)).getRangeName());
//				incidentReportResponse.setCountValue(objects.toString());
//				list.add(incidentReportResponse);   
//			}
	    	
	  }
		return null;
   }
	@Override
	public List<CircleCountResponse> getVulnerabilityCountReports(String circle, String division, String range,String startDate, String endDate) 
	{
		List<CircleCountResponse> response = new ArrayList<CircleCountResponse>();
		Map<Integer, CircleCountResponse> map = new HashMap<Integer, CircleCountResponse>();
		if(circle!=null && division!=null && range!=null && startDate!=null && endDate!=null)
		    {
			 	List<Object> vulnerableElephant = reportRepositoryCustom.getVulnerableElephant(circle, division, range, startDate, endDate);
			 	if (!vulnerableElephant.isEmpty()) {
					try {
						Iterator itr = vulnerableElephant.iterator();
						while (itr.hasNext()) 
						{
							Object[] obj = (Object[]) itr.next();
					    	if (map.containsKey(obj[0])) {
								CircleCountResponse circleCountResponse = map.get(obj[0]);
								List<DivisionCountResponse> listdiv=circleCountResponse.getDiv();
								Integer count=circleCountResponse.getCount();
								circleCountResponse.setCount(count+1);
							   //Division  
								for(int i=0;i<listdiv.size();i++) {
									DivisionCountResponse div=listdiv.get(i);
									List<RangeCountResponse> listrng=div.getRange();
									Integer countDiv=div.getCount();
									
								 if(div.getDivisionId().equals(obj[2].toString())) {
									 div.setCount(countDiv+1);
									 if(div.getCount()==1) {
										 div.setCount(countDiv+1);
										}
									//Range
									 for(int j=0;j<listrng.size();j++) {
											
											if(listrng.stream().anyMatch(ti -> ti.getRangeId().equals(obj[4].toString()))) {
												RangeCountResponse rng=listrng.stream().filter(x -> x.getRangeId().equals(obj[4].toString())).findFirst().orElse(null);
												Integer countRng=rng.getCount();
												 rng.setCount(countRng+1);	
												 break;
											}else {
												RangeCountResponse rngn=new RangeCountResponse();
												rngn.setRangeId(obj[4].toString());
												rngn.setRangeName(obj[5].toString());
												rngn.setCount(1);
												listrng.add(rngn);													
										        break;
											}											
										}
									 break;
								 }else {
										List<RangeCountResponse> list=new ArrayList<RangeCountResponse>();	
									    RangeCountResponse rngn=new RangeCountResponse();
										rngn.setRangeId(obj[4].toString());
										rngn.setRangeName(obj[5].toString());
										rngn.setCount(1);
										list.add(rngn);
									  
									 
									    DivisionCountResponse divn=new DivisionCountResponse();
									    divn.setDivisionId(obj[2].toString());
										divn.setDivisionName(obj[3].toString());
										divn.setRange(list);
										divn.setCount(1);
										listdiv.add(divn);
										
										
										circleCountResponse.setDiv(listdiv);
										break;
									}
								}								
							}
						else
							{
                                
								CircleCountResponse circleCountResponse =new CircleCountResponse();
								circleCountResponse.setCircleId(Integer.parseInt(obj[0].toString()));
								circleCountResponse.setCircleName(obj[1].toString());
								circleCountResponse.setCount(1);
								
								List<RangeCountResponse> listrng=new ArrayList<RangeCountResponse>();	
								RangeCountResponse rng=new RangeCountResponse();
								rng.setRangeId(obj[4].toString());
								rng.setRangeName(obj[5].toString());
								rng.setCount(1);
								listrng.add(rng);
								
								List<DivisionCountResponse> listdiv =new ArrayList<DivisionCountResponse>();	
								DivisionCountResponse div=new DivisionCountResponse();
								div.setDivisionId(obj[2].toString());
								div.setDivisionName(obj[3].toString());
								div.setRange(listrng);
								div.setCount(1);
								listdiv.add(div);
								circleCountResponse.setDiv(listdiv);
								map.put((Integer) obj[0], circleCountResponse);
							}
						}
						for (CircleCountResponse globalresp : map.values()) {
							response.add(globalresp);
						}			
						
					} catch (Exception e) {
						throw new IncorrectPositionException("You are not in a correct position");
					}
				}else {
					 response = new ArrayList<CircleCountResponse>();
					//throw new IncorrectPositionException("You are not in a correct position");
				}
		    }
		 List<CircleCountResponse> collect = response.stream().collect(Collectors.toList());
					
					return collect;
		
	}

	public void fetchExternalAPi(String JSONresult,ReportDO report) {
		try {
			//String longitude, String latitude,Integer reportId,String reportType
			    String post="",beat="",village="",location_village="",railway_station="",NH="",SH="",canal="";
			    String beat_id="",division="",location_village_name="",intersecting_divisions="";
			    JSONObject json =  new JSONObject(JSONresult);
			    if (json.has("post")) {
			        post = json.getString("post");
			    }
			    JSONObject postjson =  new JSONObject(post);
			    if (postjson.has("beat_boundary")) {
			    	beat = postjson.getString("beat_boundary");		
			    } 
			    if (postjson.has("revenue_village_boundary")) {
			    	  village = postjson.getString("revenue_village_boundary");	
			    } 
			    if (postjson.has("location_revenue_village_boundary")) {
			    	 location_village = postjson.getString("location_revenue_village_boundary");
			    }
			    if (postjson.has("intersecting_divisions")) {
			    	intersecting_divisions = postjson.getString("intersecting_divisions");
			    }
			    if (postjson.has("railway_station")) {
			        railway_station = postjson.getString("railway_station");
			    }
			    if (postjson.has("NH")) {
			    	 NH = postjson.getString("NH");
			    }
			    if (postjson.has("SH")) {
			    	 SH = postjson.getString("SH");
			    }if (postjson.has("canal")) {
			    	canal = postjson.getString("canal");
			    }

			    //location Info
			    if(!location_village.isEmpty()) {
			    	JSONArray locationjson =  new JSONArray(location_village);
			    	 String[] arr = new String[locationjson.length()];	    	  
			    	 for(int i = 0; i < locationjson.length(); i++)
					    {
					        arr[i] = locationjson.getString(i);
					        if(!arr[i].isEmpty()) {
					        	JSONObject vjson =  new JSONObject(arr[i]);
					        	if (vjson.has("revenue_village_name")) {
					        		location_village_name = vjson.getString("revenue_village_name").toString();
					        	}
					        }
					    }   
			     }
				    
			    //Beat
			    if(!beat.isEmpty()) {
			    	JSONObject beatjson =  new JSONObject(beat);
			    	 if (beatjson.has("beat_id")) {
			    		 beat_id = beatjson.getString("beat_id").toString();
			    	 } if (beatjson.has("f_division_name")) {
			    		 division=beatjson.getString("f_division_name").toString();
			    	 }
			    }	
	///////////////----------------------Citizen User----------------------------------//////////////////////////
					    String controlrooms = beatChangeRepository.findControlRoomMobileByBeatId(Integer.parseInt(beat_id));
					    JSONArray villagejson =  new JSONArray(village);
					    String[] arr = new String[villagejson.length()];  
					    List<String> citizenmobilelist=new ArrayList<String>();
					    for(int i = 0; i < villagejson.length(); i++)
					    {
					    	arr[i] = villagejson.getString(i);
					    	JSONObject vjson =  new JSONObject(arr[i]);
					    	String village_id = vjson.getString("village_id").toString(); 
					    	List<String> mobilelist =  citizenUserRepo.findMobileByVillage(Integer.parseInt(village_id));  
					    	citizenmobilelist.addAll(mobilelist);
					    }
					    if(!citizenmobilelist.isEmpty())
					    {
					    	if(report.getReportType().equals("direct")) {
								//reportService.triggerSMS(reportReq.getLongitude(), reportReq.getLatitude()); //should be uncomment after proper template
								if(report.getIsDuplicate()==null) {
									sendToCitizen(citizenmobilelist,division,location_village_name,controlrooms,report.getReportId(),"Direct");
									//fetchExternalAPi(String.valueOf(report.getLongitude()),String.valueOf(report.getLatitude()),report.getReportId(),"direct");
								}
							}	
					    }
					/////////////////-----------------------------------Citizen User-------------------------///////////////////////////////////////////////////
			    /////////////////----------------------Authority User------------------------------------////////////////////////// 
			    //String division=beatChangeRepository.findDivisionNameByBeatId(Integer.parseInt(beat_id));
			    if(!intersecting_divisions.isEmpty()) {
			    	
			    	String division1="",division2="";
					String dfomobile1="",dfomobile2="";
			    	   JSONArray interjson =  new JSONArray(intersecting_divisions);
					    String[] arr2 = new String[interjson.length()];
					    for(int i = 0; i < interjson.length(); i++)
					    {
					    	arr2[i] = interjson.getString(i);
					    	JSONObject djson =  new JSONObject(arr2[i]);
					    	/////DivisionName
					    	String div_name = djson.getString("f_division_name").toString();
					    	if(i==0) {
					    		division1=div_name;
					    	}else {
					    		division2=div_name;
					    	}
					    	/////Mobile
					    	String divid= djson.getString("f_division_id").toString();
					    	List<String> dfomobile = authRepository.findDFOMobilesByDivisionID(Integer.parseInt(divid));
					    	if(!dfomobile.isEmpty())
							{
								for(int j = 0; j < dfomobile.size(); j++)
							    {							
									if(i==0) {
										if(j==0){
											dfomobile1=dfomobile.get(j);
										}
							    	}else {
							    		dfomobile2=dfomobile.get(j);
							    	}
							    }
							}
					    }
					    smsSendToForestAuthority(dfomobile1,dfomobile2,division1,division2);	
			     }
				///////////////----------------------Authority User------------------------------------//////////////////////////
				
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
		}
	}
     private void sendToCitizen(List<String> mobilesList,String Division,String Village,String controlmobile,Integer reportId,String reportType) {

		String VillageName="";
		String DivisionName="";
		String ControlMobileNumber="";
		if(!Village.isEmpty()) {
			VillageName=Village;
		}
		if(!Division.isEmpty()) {
			DivisionName=Division;
		}
		if(!controlmobile.isEmpty()) {
			ControlMobileNumber=controlmobile;
		}
		String message="ଏକ ଜରୁରୀ ସୂଚନା: -\r\n" +DivisionName+" ବନଖଣ୍ଡ ତରଫରୁ ଅବଗତ କରା ଯାଉଅଛି ଯେ ବଣୁଆ ହାତୀ ପଲ ଆଜି  "+VillageName+" (ନିକଟ ଗାଁ / ଜଙ୍ଗଲ) ରେ ଅଛନ୍ତି | ଘରୁ ବାହାରକୁ ବାହାରିଲେ ସତର୍କତା ଅବଲମ୍ବନ କରନ୍ତୁ | ଆପଣଙ୍କ ଗାଁ ପାଖରେ ହାତୀର ସୂଚନା ପାଇଲେ "+ControlMobileNumber+" ନମ୍ବର ରେ ତୁରନ୍ତ ଜଣାନ୍ତୁ |\r\n" + 
				"ଧନ୍ୟବାଦ,\r\n" + 
				"ଜଙ୍ଗଲ ଏବଂ ବନ୍ୟପ୍ରାଣୀ ବିଭାଗ, ଓଡିଶା ସରକାର |";
		String templateid="1407163886875136969";
	    String mobileNumbers=StringUtils.join(mobilesList, ',');
		SMSServices service=new SMSServices();
		String sendOtpSMS = service.sendUnicodeSMS(message, mobileNumbers, templateid);
		if(sendOtpSMS.contains("402")) {
			int count=mobilesList.size();
			SmSCounter sms=new SmSCounter();
			sms.setReportId(reportId);
			sms.setReportType(reportType);
			sms.setSmsCount(count);
			sms.setCraetedOn(new Date());
			sms.setUpdatedOn(new Date());
			sms.setIsActive(true);
			sms.setDeletedStatus(false);
			sms.setMobileNumbers(mobileNumbers);
			smsCounterRepository.save(sms);
		}
	}
	private void smsSendToForestAuthority(String dfomobile1,String dfomobile2,String division1,String division2) {
		if(!dfomobile1.isEmpty()) {
			String mobileNumber=dfomobile1;
			String message="Welcome to iWLMS,\r\n" + 
					"Alert! Elephant has been spotted near interdivision boundary between "+division1+" division and "+division2+" division.\r\n" + 
					"Thank You,\r\n" + 
					"PCCF WL, Govt. Of Odisha.";
			String templateid="1407163886867967936";
			SMSServices service=new SMSServices();
			String sendOtpSMS = service.sendSingleSMS(message, mobileNumber, templateid);
			
			System.out.println(sendOtpSMS);
		}
		if(!dfomobile2.isEmpty()) {
			String mobileNumber=dfomobile2;
			String message="Welcome to iWLMS,\r\n" + 
					"Alert! Elephant has been spotted near interdivision boundary between "+division1+" division and "+division2+" division.\r\n" + 
					"Thank You,\r\n" + 
					"PCCF WL, Govt. Of Odisha.";
			String templateid="1407163886867967936";
			SMSServices service=new SMSServices();
			String sendOtpSMS = service.sendSingleSMS(message, mobileNumber, templateid);
			
			System.out.println(sendOtpSMS);
		}
	}


	@Override
	public ResponseEntity<?> CorrectReportPositionByLatLong(int DivisionId) {
		try {
		    List<ReportDO> t_shighting_report=reportRepository.getAllDataByDivisionId(DivisionId);
		    for(ReportDO reportDO:t_shighting_report)
			{
		     if(reportDO.getLongitude()!=0 && reportDO.getLatitude()!=0) 
				{
					String JSONresult="",postdata="",beat_boundary="",beat_id="",f_division_id="",f_range_id="",f_section_id="";
					MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
					body.add("buffer", 5000);
					body.add("cordinates[0]", String.valueOf(reportDO.getLongitude()));
					body.add("cordinates[1]", String.valueOf(reportDO.getLatitude()));
		
					HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body);
		
					String uri = "https://odisha4kgeo.in/index.php/mapview/iwlms";
					RestTemplate restTemplate = new RestTemplate();
					JSONresult= restTemplate.postForEntity(uri, requestEntity,String.class).getBody().toString();
					
					JSONObject json =  new JSONObject(JSONresult);
					if (json.has("post")) {
						postdata = json.getString("post");
					}
					JSONObject postjson =  new JSONObject(postdata);
					if (postjson.has("beat_boundary")) {
						beat_boundary = postjson.getString("beat_boundary");		
					} 
					if(!beat_boundary.isEmpty()) {
					JSONObject beatjson =  new JSONObject(beat_boundary);
					 if (beatjson.has("beat_id")) {
						 beat_id = beatjson.getString("beat_id").toString();
					   } if (beatjson.has("f_division_id")) {
							 f_division_id=beatjson.getString("f_division_id").toString();
						 } if (beatjson.has("f_range_id")) {
							 f_range_id=beatjson.getString("f_range_id").toString();
						 } if (beatjson.has("f_section_id")) {
							 f_section_id=beatjson.getString("f_section_id").toString();
						 }	
						reportDO.setDivision(divisionRepository.findById(Integer.parseInt(f_division_id)).get());
						reportDO.setRange(rangeRepository.findById(Integer.parseInt(f_range_id)).get());
						reportDO.setSection(sectionRepository.findById(Integer.parseInt(f_section_id)).get());
						reportDO.setBeat(beatRepository.findById(Integer.parseInt(beat_id)).get());
						reportRepository.save(reportDO);
					}
				  
			    }     
		    }
			return ResponseEntity.ok(new MessageResponse("Updated Succefully!"));   
		}catch(Exception ex) {
			return ResponseEntity.ok(new MessageResponse("failed"));
		}
	}
}


