package com.sparc.pccf.wildlife.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparc.pccf.wildlife.custom.repository.ElephantDeathReportRepositoryCustom;
import com.sparc.pccf.wildlife.custom.repository.IncidentReportCustomRepository;
import com.sparc.pccf.wildlife.entity.BeatMasterDO;
import com.sparc.pccf.wildlife.entity.DamageDeatilsDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.ElephantDeathDO;
import com.sparc.pccf.wildlife.entity.ElephantDeathDetailsDO;
import com.sparc.pccf.wildlife.entity.FireDataDO;
import com.sparc.pccf.wildlife.exception.IncorrectPositionException;
import com.sparc.pccf.wildlife.repository.AuthRepository;
import com.sparc.pccf.wildlife.repository.BeatMasterRepository;
import com.sparc.pccf.wildlife.repository.CircleMasterRepository;
import com.sparc.pccf.wildlife.repository.DamageDetailsRepository;
import com.sparc.pccf.wildlife.repository.DivMasterRepository;
import com.sparc.pccf.wildlife.repository.ElephantDeathDetailsRepository;
import com.sparc.pccf.wildlife.repository.ElephantDeathReportRepository;
import com.sparc.pccf.wildlife.repository.IncidentReportRepository;
import com.sparc.pccf.wildlife.repository.MergeBeatRepository;
import com.sparc.pccf.wildlife.repository.RangeMasterRepository;
import com.sparc.pccf.wildlife.repository.SectionMasterRepository;
import com.sparc.pccf.wildlife.request.DamageRequest;
import com.sparc.pccf.wildlife.request.IncidentReportRequest;
import com.sparc.pccf.wildlife.response.ElephantDeathRespnsezz;
import com.sparc.pccf.wildlife.response.IncidentCountResponse;
import com.sparc.pccf.wildlife.response.IncidentReportResponse;
import com.sparc.pccf.wildlife.response.IncidentResponseAll;
import com.sparc.pccf.wildlife.service.IncidentReportService;

@Service
public class IncidentReportServiceImpl implements IncidentReportService {

	@Autowired
	private IncidentReportRepository incidentRepository;
	
	@Autowired
	private ElephantDeathReportRepository elephantDeathRepository;
	
	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private BeatMasterRepository beatRepository;

	@Autowired
	private SectionMasterRepository sectionRepository;

	@Autowired
	private RangeMasterRepository rangeRepository;

	@Autowired
	private DivMasterRepository divisionRepository;

	@Autowired
	private MergeBeatRepository mergeBeatRepository;

	@Autowired
	private CircleMasterRepository circleRepository;
	
	@Autowired
	private IncidentReportCustomRepository incidentReportCustomRepository;
	
	@Autowired
	private DamageDetailsRepository damageDetailsRepository;
	
	@Autowired
	private ElephantDeathDetailsRepository elephantDeathDetailsRepository;
	
	@Autowired
	private ElephantDeathReportRepositoryCustom elephantDeathReportRepositoryCustom;

	@Override
	public List<IncidentCountResponse> getTotalIncidentCount(String circlreId, String divisionId,
			String rangeId, String sectionId, String role) {
		List<IncidentCountResponse> ir = new ArrayList<IncidentCountResponse>();
		List<Object[]> totalcount = null;
		if (circlreId != null && !circlreId.isEmpty()) {
			totalcount = incidentRepository.getTotalcountByCirleIdGrpByDivisionId(Integer.parseInt(circlreId));
		} else if (divisionId != null && !divisionId.isEmpty()) {
			totalcount = incidentRepository.getTotalcountByDivisionIdGrpByCircleID(Integer.parseInt(divisionId));
		} else if (rangeId != null && !rangeId.isEmpty()) {
			totalcount = incidentRepository.getTotalcountByRangeIdGrpBySectionID(Integer.parseInt(rangeId));
		} else if (sectionId != null && !sectionId.isEmpty()) {
			totalcount = incidentRepository.getTotalcountBySectionIdGrpByBeatID(Integer.parseInt(sectionId));
		} else {
			totalcount = incidentRepository.getTotalcountGrpByCircleId();
		}
		Map<String, List<IncidentCountResponse>> map = new HashMap<String, List<IncidentCountResponse>>();
		for (Object[] objects : totalcount) {
			
			IncidentCountResponse ireport = new IncidentCountResponse();
			ireport.setName(objects[0].toString());
			ireport.setCount(objects[1].toString());
			ireport.setId(objects[2].toString());
			ir.add(ireport);
		}
		return ir;
	}

	@Override
	public FireDataDO addIncidentReport(IncidentReportRequest incidentReportRequest,String fileName) {
		FireDataDO fireData = new FireDataDO();
		if (!incidentReportRequest.getAccuracy().isEmpty())
			fireData.setAccuracy(Double.parseDouble(incidentReportRequest.getAccuracy()));
		if (!incidentReportRequest.getAltitude().isEmpty())
			fireData.setAltitude(Double.parseDouble(incidentReportRequest.getAltitude()));

		List<Object> aLlCordinateDetails = mergeBeatRepository.getALlCordinateDetails(
				Double.parseDouble(incidentReportRequest.getLongitude()),
				Double.parseDouble(incidentReportRequest.getLatitude()));
		try {
			if (!aLlCordinateDetails.isEmpty()) {
				Iterator itr = aLlCordinateDetails.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					fireData.setDivision(divisionRepository.findById(Integer.parseInt(String.valueOf(obj[0]))).get());
					fireData.setRange(rangeRepository.findById(Integer.parseInt(String.valueOf(obj[1]))).get());
					fireData.setSection(sectionRepository.findById(Integer.parseInt(String.valueOf(obj[2]))).get());
					fireData.setBeat(beatRepository.findById(Integer.parseInt(String.valueOf(obj[3]))).get());
					fireData.setCircle(divisionRepository.findById(Integer.parseInt(String.valueOf(obj[0]))).get().getCircle());
				}
			} else {
				throw new IncorrectPositionException("incorrect postion");
			}
		}catch (Exception e) {
			throw new IncorrectPositionException("incorrect postion");
		}
		
		if (!incidentReportRequest.getUser().isEmpty())
			fireData.setCreatedBy(Integer.parseInt(incidentReportRequest.getUser()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			Date date1 = dateFormat.parse(incidentReportRequest.getIncidentDate());
			fireData.setIncidentReport(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fireData.setCraetedOn(new Date());
		fireData.setSyncDate(new Date());
		fireData.setIncidentReportType(incidentReportRequest.getReportType());
		fireData.setIncidentRemark(incidentReportRequest.getIncidentRemark());
		fireData.setLatitude(Double.parseDouble(incidentReportRequest.getLatitude()));
		fireData.setLongitude(Double.parseDouble(incidentReportRequest.getLongitude()));
		List<DamageRequest> damage = incidentReportRequest.getDamage();
		List<DamageDeatilsDO> set = new ArrayList<DamageDeatilsDO>();

		if(incidentReportRequest.getReportType().equalsIgnoreCase("others")) {
			DamageDeatilsDO d = new DamageDeatilsDO();
			d.setName("Others");
			d.setValue(0);
			set.add(d);
			
		}else {
			for (DamageRequest damageRequest : damage) {
				DamageDeatilsDO d = new DamageDeatilsDO();
				d.setName(damageRequest.getName());
				d.setValue(Double.parseDouble(damageRequest.getValue()));
				set.add(d);
			}
		}
		
		fireData.setDamageDetails(set);
		if (!incidentReportRequest.getUser().isEmpty())
			fireData.setUpdatedBy(Integer.parseInt(incidentReportRequest.getUser()));
		if (!fileName.isEmpty())
			fireData.setIncidentPhoto(fileName);
		
		return incidentRepository.save(fireData);

	}

	@Override
	public List<IncidentReportResponse> viewAllIncidentReportCount(String circlreId, String divisionId, String rangeId,String startDate, String endDate) {
		List<IncidentReportResponse> res = new ArrayList<IncidentReportResponse>();
		Date sdate=null;
		Date edate=null;
		try {
			if(!startDate.isEmpty()&&!endDate.isEmpty()) {
				 sdate= new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
				 edate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
			}	
		} catch (ParseException e) {
			throw new RuntimeException();
		}  
		
		if (circlreId.isEmpty() && divisionId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCount = incidentRepository.getTotalIncidentCount(sdate,edate);
			Iterator<Object[]> iterator = totalIncidentCount.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[0]));
				incidentReportResponse.setCountValue(String.valueOf(objects[1]));
				res.add(incidentReportResponse);
			}
		}
		else if (!circlreId.isEmpty() && divisionId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = incidentRepository
					.getTotalIncidentCountByCircle(Integer.parseInt(circlreId),sdate,edate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[1]));
				incidentReportResponse.setCountValue(String.valueOf(objects[2]));
				res.add(incidentReportResponse);
			}
		}
		else if (!circlreId.isEmpty() && !divisionId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = incidentRepository
					.getTotalIncidentCountByCircleAndDivision(Integer.parseInt(circlreId),Integer.parseInt(divisionId),sdate,edate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[2]));
				incidentReportResponse.setCountValue(String.valueOf(objects[3]));
				res.add(incidentReportResponse);
			}
		}
		else if (!circlreId.isEmpty() && !divisionId.isEmpty() && !rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = incidentRepository
					.getTotalIncidentCountByCircleAndDivisionAndRange(Integer.parseInt(circlreId),Integer.parseInt(divisionId),Integer.parseInt(rangeId),sdate,edate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[3]));
				incidentReportResponse.setCountValue(String.valueOf(objects[4]));
				res.add(incidentReportResponse);
			}
		}
		
		 else if (!divisionId.isEmpty() && circlreId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = incidentRepository
					.getTotalIncidentCountByDivision(Integer.parseInt(divisionId),sdate,edate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[1]));
				incidentReportResponse.setCountValue(String.valueOf(objects[2]));
				res.add(incidentReportResponse);
			}
		}
		 else if (!divisionId.isEmpty() && !rangeId.isEmpty() && circlreId.isEmpty() ) {
				List<Object[]> totalIncidentCountByCircle = incidentRepository
						.getTotalIncidentCountByDivisionAndRange(Integer.parseInt(divisionId),Integer.parseInt(rangeId),sdate,edate);
				Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
					incidentReportResponse.setName(String.valueOf(objects[2]));
					incidentReportResponse.setCountValue(String.valueOf(objects[3]));
					res.add(incidentReportResponse);
				}
			}else if (!rangeId.isEmpty() && divisionId.isEmpty() && circlreId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = incidentRepository
					.getTotalIncidentCountByRange(Integer.parseInt(rangeId),sdate,edate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[1]));
				incidentReportResponse.setCountValue(String.valueOf(objects[2]));
				res.add(incidentReportResponse);
			}
		}
		return res;
	}

	@Override
	public ElephantDeathDO addElephantDeathReport(IncidentReportRequest incidentReportRequest,String fileName) {
		ElephantDeathDO elephantDeathIncident = new ElephantDeathDO();
		if (!incidentReportRequest.getAccuracy().isEmpty())
			elephantDeathIncident.setAccuracy(Double.parseDouble(incidentReportRequest.getAccuracy()));
		if (!incidentReportRequest.getAltitude().isEmpty())
			elephantDeathIncident.setAltitude(Double.parseDouble(incidentReportRequest.getAltitude()));
		
		if (!incidentReportRequest.getLocation().isEmpty())
			elephantDeathIncident.setLocation(incidentReportRequest.getLocation());
       
		if (!incidentReportRequest.getDeathReason().isEmpty())
			elephantDeathIncident.setDeathReason(incidentReportRequest.getDeathReason());
		
		List<Object> aLlCordinateDetails = mergeBeatRepository.getALlCordinateDetails(
				Double.parseDouble(incidentReportRequest.getLongitude()),
				Double.parseDouble(incidentReportRequest.getLatitude()));
		try {
			if (!aLlCordinateDetails.isEmpty()) {
				Iterator itr = aLlCordinateDetails.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					elephantDeathIncident.setDivision(divisionRepository.findById(Integer.parseInt(String.valueOf(obj[0]))).get());
					elephantDeathIncident.setRange(rangeRepository.findById(Integer.parseInt(String.valueOf(obj[1]))).get());
					elephantDeathIncident.setSection(sectionRepository.findById(Integer.parseInt(String.valueOf(obj[2]))).get());
					elephantDeathIncident.setBeat(beatRepository.findById(Integer.parseInt(String.valueOf(obj[3]))).get());
					elephantDeathIncident.setCircle(divisionRepository.findById(Integer.parseInt(String.valueOf(obj[0]))).get().getCircle());
					}
			} else {
				throw new IncorrectPositionException("incorrect postion");
			}
		}catch (Exception e) {
			throw new IncorrectPositionException("incorrect postion");
		}
		
		if (!incidentReportRequest.getUser().isEmpty())
			elephantDeathIncident.setCreatedBy(Integer.parseInt(incidentReportRequest.getUser()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			Date date1 = dateFormat.parse(incidentReportRequest.getIncidentDate());
			elephantDeathIncident.setIncidentReport(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!incidentReportRequest.getIncidentRemark().isEmpty())
			elephantDeathIncident.setIncidentRemark(incidentReportRequest.getIncidentRemark());
		
		elephantDeathIncident.setCraetedOn(new Date());
		elephantDeathIncident.setSyncDate(new Date());
		elephantDeathIncident.setLatitude(Double.parseDouble(incidentReportRequest.getLatitude()));
		elephantDeathIncident.setLongitude(Double.parseDouble(incidentReportRequest.getLongitude()));
		List<DamageRequest> damage = incidentReportRequest.getDamage();
		List<ElephantDeathDetailsDO> set = new ArrayList<ElephantDeathDetailsDO>();
		for (DamageRequest damageRequest : damage) {
			ElephantDeathDetailsDO d = new ElephantDeathDetailsDO();
			d.setName(damageRequest.getName());
			d.setValue(Double.parseDouble(damageRequest.getValue()));
			set.add(d);
		}
		elephantDeathIncident.setDeathDetails(set);
		if (!incidentReportRequest.getUser().isEmpty())
			elephantDeathIncident.setUpdatedBy(Integer.parseInt(incidentReportRequest.getUser()));
		if (!fileName.isEmpty())
			elephantDeathIncident.setIncidentPhoto(fileName);
		return elephantDeathRepository.save(elephantDeathIncident);
	}

	@Override
	public List<IncidentReportResponse> viewAllElephantDeathReport(String circlreId, String divisionId,
			String rangeId, String startDate, String endDate) {

		List<IncidentReportResponse> res = new ArrayList<IncidentReportResponse>();
		if (circlreId.isEmpty() && divisionId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCount = elephantDeathReportRepositoryCustom.getTotalIncidentCount(startDate,endDate);
			Iterator<Object[]> iterator = totalIncidentCount.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[0]));
				incidentReportResponse.setCountValue(String.valueOf(objects[1]));
				res.add(incidentReportResponse);
			}
		}
		else if (!circlreId.isEmpty() && divisionId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = elephantDeathReportRepositoryCustom
					.getTotalIncidentCountByCircle(Integer.parseInt(circlreId),startDate,endDate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[1]));
				incidentReportResponse.setCountValue(String.valueOf(objects[2]));
				res.add(incidentReportResponse);
			}
		}
		else if (!circlreId.isEmpty() && !divisionId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = elephantDeathReportRepositoryCustom
					.getTotalIncidentCountByCircleAndDivision(Integer.parseInt(circlreId),Integer.parseInt(divisionId),startDate,endDate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[2]));
				incidentReportResponse.setCountValue(String.valueOf(objects[3]));
				res.add(incidentReportResponse);
			}
		}
		else if (!circlreId.isEmpty() && !divisionId.isEmpty() && !rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = elephantDeathReportRepositoryCustom
					.getTotalIncidentCountByCircleAndDivisionAndRange(Integer.parseInt(circlreId),Integer.parseInt(divisionId),Integer.parseInt(rangeId),startDate,endDate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[3]));
				incidentReportResponse.setCountValue(String.valueOf(objects[4]));
				res.add(incidentReportResponse);
			}
		}
		
		 else if (!divisionId.isEmpty() && circlreId.isEmpty() && rangeId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = elephantDeathReportRepositoryCustom
					.getTotalIncidentCountByDivision(Integer.parseInt(divisionId),startDate,endDate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[1]));
				incidentReportResponse.setCountValue(String.valueOf(objects[2]));
				res.add(incidentReportResponse);
			}
		}
		 else if (!divisionId.isEmpty() && !rangeId.isEmpty() && circlreId.isEmpty() ) {
				List<Object[]> totalIncidentCountByCircle = elephantDeathReportRepositoryCustom
						.getTotalIncidentCountByDivisionAndRange(Integer.parseInt(divisionId),Integer.parseInt(rangeId),startDate,endDate);
				Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
					incidentReportResponse.setName(String.valueOf(objects[2]));
					incidentReportResponse.setCountValue(String.valueOf(objects[3]));
					res.add(incidentReportResponse);
				}
			}else if (!rangeId.isEmpty() && divisionId.isEmpty() && circlreId.isEmpty()) {
			List<Object[]> totalIncidentCountByCircle = elephantDeathReportRepositoryCustom
					.getTotalIncidentCountByRange(Integer.parseInt(rangeId),startDate,endDate);
			Iterator<Object[]> iterator = totalIncidentCountByCircle.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				IncidentReportResponse incidentReportResponse = new IncidentReportResponse();
				incidentReportResponse.setName(String.valueOf(objects[2]));
				incidentReportResponse.setCountValue(String.valueOf(objects[3]));
				res.add(incidentReportResponse);
			}
		}
		return res;
	}
	@Override
	public List<IncidentResponseAll> viewAllIncidentReport(String circlreId, 
														   String divisionId, 
														   String rangeId,
														   String sectionId, 
														   String beatId, 
														   String reportType, 
														   String startDate, 
														   String endDate) 
	{
		List<IncidentResponseAll> res = new ArrayList<IncidentResponseAll>();
		List<Object[]> allIncidentReport = incidentReportCustomRepository.getAllIncidentReport(circlreId, divisionId, rangeId,sectionId,beatId,reportType, startDate, endDate);
		for (Object[] objects : allIncidentReport) 
		{
			IncidentResponseAll incidentReportResponse=new IncidentResponseAll();
			int i=0;
			int reportId=0;
			if (objects[i] != null) {
				reportId = Integer.parseInt(objects[i].toString());
				incidentReportResponse.setReport_Id(objects[i].toString());
			}
			if (objects[++i] != null)
				incidentReportResponse.setAccuracy(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setAltitude(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setCraetedOn(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setCreatedBy(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setImgPath(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setIncidentRemarks(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setIncidentReport(objects[i].toString());
			if (objects[++i] != null)
			{
				String type=objects[i].toString();
				incidentReportResponse.setIncidentReportType(type);
				if(reportId!=0) {
				 List<DamageDeatilsDO> details = damageDetailsRepository.findByReportId(reportId);
					if(!details.isEmpty()) {
						for(int j=0;j<details.size();j++)
						{	
							if(type.equals("cattle/domestic animal kill")) {
								incidentReportResponse.setInjured(""+(int)details.get(j).getValue());
								incidentReportResponse.setKill(""+(int)details.get(j).getValue());
							}else {
								if(incidentReportResponse.getInjured()==null)
									incidentReportResponse.setInjured("0");
								if(incidentReportResponse.getKill()==null)
									incidentReportResponse.setKill("0");
							}if(type.equals("house damage")) {
								incidentReportResponse.setHouseDamage(""+(int)details.get(j).getValue());
							}else {
								if(incidentReportResponse.getHouseDamage()==null)
									incidentReportResponse.setHouseDamage("0");
							}if(type.equals("human injury/kill")) {
								incidentReportResponse.setHuman(""+(int)details.get(j).getValue());
							}else {
								if(incidentReportResponse.getHuman()==null)
									incidentReportResponse.setHuman("0");
							}if(type.equals("crop damage")) {
								incidentReportResponse.setCrop(""+(int)details.get(j).getValue());
							}else {
								if(incidentReportResponse.getCrop()==null)
									incidentReportResponse.setCrop("0");
							}
							if(type.equals("elephant on road")|| type.equals("elephant found in pit/dug well")||type.equals("elephant injured/sick")) {
								if(details.get(j).getName().equals("Makhna") || details.get(j).getName().equals("Male")) {
									incidentReportResponse.setMakhna(""+(int)details.get(j).getValue());
								}else {
									if(incidentReportResponse.getMakhna()==null)
										incidentReportResponse.setMakhna("0");
								}
								if(details.get(j).getName().equals("Female")) {
									incidentReportResponse.setFemale(""+(int)details.get(j).getValue());
						
								}else {
									if(incidentReportResponse.getFemale()==null)
										incidentReportResponse.setFemale("0");
								}						
								if(details.get(j).getName().equals("Calf")) {
									incidentReportResponse.setCalf(""+(int)details.get(j).getValue());
					
								}else {
									if(incidentReportResponse.getCalf()==null)
										incidentReportResponse.setCalf("0");
								}
							}
							
						}
					}
				}
			}
			if (objects[++i] != null)
				incidentReportResponse.setIsActive(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setLatitude(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setLongitude(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setUpdatedBy((authRepository.findById((Integer)objects[i]).get().getUsername()));
			if (objects[++i] != null)
				incidentReportResponse.setUpdatedOn(objects[i].toString());
			if (objects[++i] != null)
				incidentReportResponse.setBeat(beatRepository.findById(Integer.parseInt(objects[i].toString())).get().getBeatName());
			if (objects[++i] != null)
				incidentReportResponse.setCircle(circleRepository.findById(Integer.parseInt(objects[i].toString())).get().getCircleName());
			if (objects[++i] != null)
				incidentReportResponse.setDivision(divisionRepository.findById(Integer.parseInt(objects[i].toString())).get().getDivisionName());	
			if (objects[i] != null)
				incidentReportResponse.setDivisionId(divisionRepository.findById(Integer.parseInt(objects[i].toString())).get().getDivisionId().toString());
			if (objects[++i] != null)
				incidentReportResponse.setRange(rangeRepository.findById(Integer.parseInt(objects[i].toString())).get().getRangeName());
			if (objects[++i] != null)
				incidentReportResponse.setSection(sectionRepository.findById(Integer.parseInt(objects[i].toString())).get().getSecName());
			if (objects[++i] != null)
				incidentReportResponse.setSyncDate(objects[i].toString());
			res.add(incidentReportResponse);
			}
		return res;
				}

	@Override
	public List<ElephantDeathRespnsezz> viewAllElephantDeathReportzz(String circlreId, 
																	 String divisionId, 
																	 String rangeId,
																	 String sectionId,
																	 String beatId,
																	 String startDate,
																	 String endDate) 
	{
		List<ElephantDeathRespnsezz> res = new ArrayList<ElephantDeathRespnsezz>();
		List<Object[]> viewAllElephantDeathReportzz = incidentReportCustomRepository.viewAllElephantDeathReportzz(circlreId, divisionId, rangeId,sectionId,beatId, startDate, endDate);
		for (Object[] objects : viewAllElephantDeathReportzz) 
		{
			ElephantDeathRespnsezz elephantDeathRespnsezz=new ElephantDeathRespnsezz();
			int i=0;
			if (objects[i] != null) {
				elephantDeathRespnsezz.setReport_Id(objects[i].toString());
				List<ElephantDeathDetailsDO> details = elephantDeathDetailsRepository.findByReportId(Integer.parseInt(objects[i].toString()));
				if(!details.isEmpty()) {
					for(int j=0;j<details.size();j++)
					{						
						if(details.get(j).getName().equals("Makhna") || details.get(j).getName().equals("Male")) {
							elephantDeathRespnsezz.setMakhna(""+(int)details.get(j).getValue());
				
						}else {
							if(elephantDeathRespnsezz.getMakhna()==null)
							elephantDeathRespnsezz.setMakhna("0");
						}
						if(details.get(j).getName().equals("Female")) {
							elephantDeathRespnsezz.setFemale(""+(int)details.get(j).getValue());
				
						}else {
							if(elephantDeathRespnsezz.getFemale()==null)
							elephantDeathRespnsezz.setFemale("0");
						}						
						if(details.get(j).getName().equals("Calf")) {
							elephantDeathRespnsezz.setCalf(""+(int)details.get(j).getValue());
			
						}else {
							if(elephantDeathRespnsezz.getCalf()==null)
							elephantDeathRespnsezz.setCalf("0");
						}
					}
				}
			}
			if (objects[++i] != null)
				elephantDeathRespnsezz.setAccuracy(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setAltitude(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setCraetedOn(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setCreatedBy(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setIncidentPhoto(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setIncidentReport(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setIsActive(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setLatitude(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setLocation(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setLongitude(objects[i].toString());
			if (objects[++i] != null)
				//elephantDeathRespnsezz.setUpdatedBy(objects[i].toString());
			     elephantDeathRespnsezz.setUpdatedBy((authRepository.findById((Integer)objects[i]).get().getUsername()));
			if (objects[++i] != null)
				elephantDeathRespnsezz.setUpdatedOn(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setBeat(beatRepository.findById(Integer.parseInt(objects[i].toString())).get().getBeatName());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setCircle(circleRepository.findById(Integer.parseInt(objects[i].toString())).get().getCircleName());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setDivision(divisionRepository.findById(Integer.parseInt(objects[i].toString())).get().getDivisionName());	
			if (objects[i] != null)
				elephantDeathRespnsezz.setDivisionId(divisionRepository.findById(Integer.parseInt(objects[i].toString())).get().getDivisionId().toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setRange(rangeRepository.findById(Integer.parseInt(objects[i].toString())).get().getRangeName());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setSection(sectionRepository.findById(Integer.parseInt(objects[i].toString())).get().getSecName());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setDeathReason(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setSyncDate(objects[i].toString());
			if (objects[++i] != null)
				elephantDeathRespnsezz.setIncidentRemark(objects[i].toString());
			res.add(elephantDeathRespnsezz);
		}
		return res;
	}

	@Override
	public List<DamageDeatilsDO> getIncidentRepotzDetailsByReportId(String reportId) {
		
		// TODO Auto-generated method stub	
		 return  damageDetailsRepository.findByReportId(Integer.parseInt(reportId));
	}

	@Override
	public List<ElephantDeathDetailsDO> getElephantDEathDetailsByReportId(String reportId) {
		// TODO Auto-generated method stub
		return elephantDeathDetailsRepository.findByReportId(Integer.parseInt(reportId));
	}
}
