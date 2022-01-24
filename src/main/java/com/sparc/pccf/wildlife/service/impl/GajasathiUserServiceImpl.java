package com.sparc.pccf.wildlife.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.custom.repository.ElephantDeathReportRepositoryCustom;
import com.sparc.pccf.wildlife.custom.repository.PublicUserCustomRepository;
import com.sparc.pccf.wildlife.dto.MobileNumberResponseDTO;
import com.sparc.pccf.wildlife.dto.PublicUserDto;
import com.sparc.pccf.wildlife.entity.BeatMasterDO;
import com.sparc.pccf.wildlife.entity.DivisionMasterDO;
import com.sparc.pccf.wildlife.entity.ElephantReportDO;
import com.sparc.pccf.wildlife.entity.PublicUserDO;
import com.sparc.pccf.wildlife.entity.RangeMasterDO;
import com.sparc.pccf.wildlife.entity.SectionMasterDO;
import com.sparc.pccf.wildlife.exception.IncorrectPositionException;
import com.sparc.pccf.wildlife.repository.AuthRepository;
import com.sparc.pccf.wildlife.repository.BeatMasterRepository;
import com.sparc.pccf.wildlife.repository.DivMasterRepository;
import com.sparc.pccf.wildlife.repository.ElephantReportRepositroy;
import com.sparc.pccf.wildlife.repository.IGajasathiUserRepository;
import com.sparc.pccf.wildlife.repository.MergeBeatRepository;
import com.sparc.pccf.wildlife.repository.PublicUserRepository;
import com.sparc.pccf.wildlife.repository.RangeMasterRepository;
import com.sparc.pccf.wildlife.repository.SectionMasterRepository;
import com.sparc.pccf.wildlife.request.PublicUserLoginRequest;
import com.sparc.pccf.wildlife.response.ElephantReportResponse;
import com.sparc.pccf.wildlife.response.MessageResponse;
import com.sparc.pccf.wildlife.service.FileStorageService;
import com.sparc.pccf.wildlife.service.IGajasathiUserService;

@Service
public class GajasathiUserServiceImpl implements IGajasathiUserService {

	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private IGajasathiUserRepository iGajasathiUserRepository;

	@Autowired
	private MergeBeatRepository mergeBeatRepository;

	@Autowired
	private BeatMasterRepository beatRepository;

	@Autowired
	private RangeMasterRepository rangeRepository;
	
	@Autowired
	private SectionMasterRepository sectionRepository;

	@Autowired
	private DivMasterRepository divisionRepository;
	
	@Autowired
	private ElephantReportRepositroy elephantReportRepositroy;
	
	@Autowired
	private PublicUserRepository PublicUserRepo;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@Autowired
	DivMasterRepository divMasterRepository;
	
	@Autowired
	RangeMasterRepository rangeMasterRepo;
	
	@Autowired
	SectionMasterRepository sectionMasterRepo;
	
	@Autowired
	BeatMasterRepository beatMasterRepo;
	
	@Autowired
	PublicUserCustomRepository publicUserCustomRepo;
	
	@Value("${file.upload.directory}")
	private String fileUploadDirectory;
	
	@Autowired
	ElephantDeathReportRepositoryCustom elephantDeathReportRepoCustom;

	@Override
	public String generateOtp(String mobile) {
		PublicUserDO findByMobile = iGajasathiUserRepository.findByMobile(Long.parseLong(mobile));
		if (findByMobile == null) {
			//String OTP = Integer.toString(new Random().nextInt(100000));
			String OTP= new DecimalFormat("000000").format(new Random().nextInt(999999));
			try {
				String Username = "suru.mistry007@gmail.com";
				String apiKey = "&apikey=" + URLEncoder.encode("UOMDAJ+z8HI-kFedKgMqcKVC8xpOolCny9DxlRtbiT", "UTF-8");
				String message = "&message=" + URLEncoder.encode("Your Login Verification Code is "+ OTP +"");
				String sender = "&sender=" + URLEncoder.encode("SPARCS", "UTF-8");
				String numbers = "&numbers=" + URLEncoder.encode(mobile.toString(), "UTF-8");
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
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return OTP;
		} else {
			return "number is already registered !!!";
		}
	}
	@Override
	public String createUser(PublicUserLoginRequest usrReq) {
		if (usrReq != null) {
			PublicUserDO publicUserDo = new PublicUserDO();
			publicUserDo.setUsername(usrReq.getUsername());
			publicUserDo.setMobile(Long.parseLong(usrReq.getMobile()));
			publicUserDo.setAge(Integer.parseInt(usrReq.getAge()));
			publicUserDo.setCraetedOn(new Date());

			if (!usrReq.getLongitude().isEmpty()  && !usrReq.getLatitude().isEmpty() ) 
			{

			if (usrReq.getLongitude() != "" && usrReq.getLatitude() != "") {

			if (usrReq.getLongitude() != "" && usrReq.getLatitude() != ""&& usrReq.getDivision()=="" 
					&& usrReq.getRange()=="" && usrReq.getSection()=="" && usrReq.getBeat()=="") {

				publicUserDo.setLongitude(Double.parseDouble(usrReq.getLongitude()));
				publicUserDo.setLatitude(Double.parseDouble(usrReq.getLatitude()));
				List<Object> aLlCordinateDetails = mergeBeatRepository.getALlCordinateDetails(Double.parseDouble(usrReq.getLongitude()), 
						                                                                      Double.parseDouble(usrReq.getLatitude()));
				if (!aLlCordinateDetails.isEmpty()) {
					try {
						Iterator itr = aLlCordinateDetails.iterator();
						//System.out.println("hi");
						while (itr.hasNext()) {
							Object[] obj = (Object[]) itr.next();	
							publicUserDo.setDivision(divisionRepository.findById(Integer.parseInt(String.valueOf(obj[0]))).get());
							publicUserDo.setRange(rangeRepository.findById(Integer.parseInt(String.valueOf(obj[1]))).get());
							publicUserDo.setSection(sectionRepository.findById(Integer.parseInt(String.valueOf(obj[2]))).get());
							publicUserDo.setBeat(beatRepository.findById(Integer.parseInt(String.valueOf(obj[3]))).get());
							
						}
					} catch (Exception e) {
						throw new IncorrectPositionException("You are not in a correct position");
					}
				}

			} else {
				if(usrReq.getDivision()!="")
					publicUserDo.setDivision(divisionRepository.findById(Integer.parseInt(usrReq.getDivision())).get());
				if(usrReq.getRange()!="")
					publicUserDo.setRange(rangeRepository.findById(Integer.parseInt(usrReq.getRange())).get());
				if(usrReq.getSection()!="")
					publicUserDo.setSection(sectionRepository.findById(Integer.parseInt(usrReq.getSection())).get());
				if(usrReq.getBeat()!="") 
				{
					publicUserDo.setBeat(beatRepository.findById(Integer.parseInt(usrReq.getBeat())).get());
					List<Object> aLlCordinateDetails1 = mergeBeatRepository.getLatLongByBeatId(usrReq.getBeat());
					if (!aLlCordinateDetails1.isEmpty()) {
						try {
							Iterator itr = aLlCordinateDetails1.iterator();
							while (itr.hasNext()) {
								Object[] obj = (Object[]) itr.next();
								publicUserDo.setLongitude(Double.parseDouble(String.valueOf(obj[0])));
								publicUserDo.setLatitude(Double.parseDouble(String.valueOf(obj[1])));
							}
						} catch (Exception e) {
							throw new IncorrectPositionException("You are not in a correct position");
						}
					}
				    
				}
				
				if(usrReq.getBeat()!="")
					publicUserDo.setBeat(beatRepository.findById(Integer.parseInt(usrReq.getBeat())).get());
				if(usrReq.getLongitude()!="")
					publicUserDo.setLongitude(Double.parseDouble(usrReq.getLongitude()));
				if(usrReq.getLatitude()!="")
					publicUserDo.setLatitude(Double.parseDouble(usrReq.getLatitude()));
			}
			PublicUserDO save = iGajasathiUserRepository.save(publicUserDo);
			if (save != null) {
				return "success";
			} else {
				return "failed";
			}

		} else {
			return "failed";
		}
	}
	
		}
		return fileUploadDirectory;
		
	}

	@Override
	public ResponseEntity<?> otpBasedLogin( String mobile) {
		PublicUserDO findByMobile = iGajasathiUserRepository.findByMobile(Long.parseLong(mobile));
		if(findByMobile!=null) {
			return ResponseEntity.ok(findByMobile);
		}else {
			return ResponseEntity.ok(new MessageResponse("user is not available"));	
		}		
	}

	@Override
	public ElephantReportResponse report(String textMessage,String userID, String latitude, String longitude, MultipartFile image, MultipartFile audioMessage,
			MultipartFile videoMessage,String type,String status,String reportingDate) {
		ElephantReportDO elephantReportDO = new ElephantReportDO();
		
		if (image!=null&&!image.isEmpty()) {
			String uploadPhoto = fileStorageService.uploadPhoto(image,type,userID);
			elephantReportDO.setImgPath(uploadPhoto);
		}
		if (audioMessage != null&&!audioMessage.isEmpty()) {
			String uploadPhoto = fileStorageService.uploadPhoto(audioMessage,type,userID);
			elephantReportDO.setAudioMessagePath(uploadPhoto);
		}
		if (videoMessage != null&&!videoMessage.isEmpty()) {
			String uploadPhoto = fileStorageService.uploadPhoto(videoMessage,type,userID);
			elephantReportDO.setVideoMessagePath(uploadPhoto);
		}
		if (textMessage != "") {
			elephantReportDO.setDescription(textMessage);
		}
		if (userID != "") {
			elephantReportDO.setUserId(Long.parseLong(userID));
		}
		if(status!="")
		{
			elephantReportDO.setStatus(status);
		}
		elephantReportDO.setSyncDate(new Date());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			Date date1 = dateFormat.parse(reportingDate);
			elephantReportDO.setReportingDate(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (latitude != "" && latitude != "") {
			elephantReportDO.setLatitude(Double.parseDouble(latitude));
			elephantReportDO.setLongitude(Double.parseDouble(longitude));
			List<Object> aLlCordinateDetails = mergeBeatRepository.getALlCordinateDetails(Double.parseDouble(longitude),
					Double.parseDouble(latitude));
			if (!aLlCordinateDetails.isEmpty()) {
				try {
					Iterator itr = aLlCordinateDetails.iterator();
					while (itr.hasNext()) {
						Object[] obj = (Object[]) itr.next();
						elephantReportDO.setDivision(divisionRepository.findById(Integer.parseInt(String.valueOf(obj[0]))).get());
						elephantReportDO.setRange(rangeRepository.findById(Integer.parseInt(String.valueOf(obj[1]))).get());
						elephantReportDO.setSection(sectionRepository.findById(Integer.parseInt(String.valueOf(obj[2]))).get());
						elephantReportDO.setBeat(beatRepository.findById(Integer.parseInt(String.valueOf(obj[3]))).get());
					}
				} catch (Exception e) {
					throw new IncorrectPositionException("You are not in a correct position");
				}
			}
		}
		ElephantReportDO elephant=elephantReportRepositroy.save(elephantReportDO);
		ElephantReportResponse elephantReportResponse = new ElephantReportResponse();
		if(elephant!=null) {
					elephantReportResponse.setReportId(elephant.getReportId().toString());
				
				
				if(elephant.getActionDate() != null)
					elephantReportResponse.setActionDate(elephant.getActionDate().toString());
				if(elephant.getAudioMessagePath() != null) {
					elephantReportResponse.setAudioMessagePath(elephant.getAudioMessagePath().toString());
				}else {
					elephantReportResponse.setAudioMessagePath("");
				}
			
				
				if(elephant.getDescription() != null)
					elephantReportResponse.setDescription(elephant.getDescription().toString());
				if(elephant.getImgPath() != null)
				{
					elephantReportResponse.setImgPath(elephant.getImgPath().toString());
				}else {
					elephantReportResponse.setImgPath("");
				}
			
				if(elephant.getLatitude() != null)
					elephantReportResponse.setLatitude(elephant.getLatitude().toString());
				if(elephant.getLongitude() != null)
					elephantReportResponse.setLongitude(elephant.getLongitude() .toString());
				if(elephant.getReportingDate() != null) {
						elephantReportResponse.setReportingDate(elephant.getReportingDate().toString());
					}
				if(elephant.getStatus() != null)
					elephantReportResponse.setStatus(elephant.getStatus().toString());
				if(elephant.getUserId() != null)
					elephantReportResponse.setUserId(elephant.getUserId().toString());
				if(elephant.getVideoMessagePath() != null) {
					elephantReportResponse.setVideoMessagePath(elephant.getVideoMessagePath().toString());
					}else {
						elephantReportResponse.setVideoMessagePath("");	
					}
				if(elephant.getBeat() != null)
				{
					elephantReportResponse.setBeat(elephant.getBeat().getBeatName());
				    elephantReportResponse.setBeatId(elephant.getBeat().getBeatId().toString());
				}else {
					elephantReportResponse.setBeat("");
				    elephantReportResponse.setBeatId("");
				}
				
				if(elephant.getDivision() != null) 
				{
				    elephantReportResponse.setDivision(elephant.getDivision().getDivisionName());
				    elephantReportResponse.setDivisionId(elephant.getDivision().getDivisionId().toString());
				}else {
					elephantReportResponse.setDivision("");
				    elephantReportResponse.setDivisionId("");
				}
			
				if(elephant.getRange() != null)
				{
					elephantReportResponse.setRange(elephant.getRange().getRangeName());
				    elephantReportResponse.setRangeId(elephant.getRange().getRangeId().toString());
				}else {
					elephantReportResponse.setRange("");
				    elephantReportResponse.setRangeId("");
				}
				if(elephant.getSection()!= null)
				{
					elephantReportResponse.setSection(elephant.getSection().getSecName());
				    elephantReportResponse.setSectionId(elephant.getSection().getSecId().toString());
				}else {
					elephantReportResponse.setSection("");
				    elephantReportResponse.setSectionId("");
				}
		}	
		return  elephantReportResponse;
	}

	@Override
	public PublicUserDto getUserById(String userId) {
		PublicUserDO findByMobile = iGajasathiUserRepository.findByMobile(Long.parseLong(userId));
		PublicUserDto publicUserDto =new PublicUserDto();

		DivisionMasterDO findDivisionNameByDivisionId = divMasterRepository.findDivisionNameByDivisionId(findByMobile.getDivision().getDivisionId());
		publicUserDto.setDivision(findDivisionNameByDivisionId.getDivisionName());
		
		RangeMasterDO findRangeNameByRangeId = rangeMasterRepo.findRangeNameByRangeId(findByMobile.getRange().getRangeId());
		publicUserDto.setRange(findRangeNameByRangeId.getRangeName());
		
		SectionMasterDO findSecNamebySecId = sectionMasterRepo.findSecNameBySecId(findByMobile.getSection().getSecId());
		publicUserDto.setSection(findSecNamebySecId.getSecName());
		
		BeatMasterDO findBeatNamebyBeatId = beatMasterRepo.findBeatNameByBeatId(findByMobile.getBeat().getBeatId());
		publicUserDto.setBeat(findBeatNamebyBeatId.getBeatName());
		

		publicUserDto.setAge(findByMobile.getAge());
		publicUserDto.setLatitude(findByMobile.getLatitude());
		publicUserDto.setLongitude(findByMobile.getLongitude());
		publicUserDto.setMobile(findByMobile.getMobile());
		publicUserDto.setOtp(findByMobile.getOtp());
		publicUserDto.setUsername(findByMobile.getUsername());
		publicUserDto.setImgPath(findByMobile.getImgPath());
		
		return publicUserDto;
		
	}

	@Override
	public List<ElephantReportResponse> getAllReportByUserID(String userId) {
		 List<ElephantReportDO> findAllByUserId = elephantReportRepositroy.findAllByUserId(Long.parseLong(userId));
		List<ElephantReportResponse> list = new ArrayList<ElephantReportResponse>();
		for (ElephantReportDO objects : findAllByUserId) 
		{
			int i = 0;
			ElephantReportResponse elephantReportResponse = new ElephantReportResponse();		
				elephantReportResponse.setReportId(objects.getReportId().toString());
			
			++i;//action by
			if(objects.getActionDate() != null)
				elephantReportResponse.setActionDate(objects.getActionDate().toString());
			if(objects.getAudioMessagePath() != null) {
				elephantReportResponse.setAudioMessagePath(objects.getAudioMessagePath().toString());
			}else {
				elephantReportResponse.setAudioMessagePath("");
			}
			++i;//broad desc
			++i;//created on 
			if(objects.getDescription() != null)
				elephantReportResponse.setDescription(objects.getDescription().toString());
			if(objects.getImgPath() != null)
			{
				elephantReportResponse.setImgPath(objects.getImgPath().toString());
			}else {
				elephantReportResponse.setImgPath("");
			}
			++i;//is active 
			if(objects.getLatitude() != null)
				elephantReportResponse.setLatitude(objects.getLatitude().toString());
			if(objects.getLongitude() != null)
				elephantReportResponse.setLongitude(objects.getLongitude() .toString());
			if(objects.getReportingDate() != null) {
					elephantReportResponse.setReportingDate(objects.getReportingDate().toString());
				}
			if(objects.getStatus() != null)
				elephantReportResponse.setStatus(objects.getStatus().toString());
			++i;//update on
			if(objects.getUserId() != null)
				elephantReportResponse.setUserId(objects.getUserId().toString());
			if(objects.getVideoMessagePath() != null) {
				elephantReportResponse.setVideoMessagePath(objects.getVideoMessagePath().toString());
				}else {
					elephantReportResponse.setVideoMessagePath("");	
				}
			if(objects.getBeat() != null)
			{
				elephantReportResponse.setBeat(objects.getBeat().getBeatName());
			    elephantReportResponse.setBeatId(objects.getBeat().getBeatId().toString());
			}else {
				elephantReportResponse.setBeat("");
			    elephantReportResponse.setBeatId("");
			}
			
			if(objects.getDivision() != null) 
			{
			    elephantReportResponse.setDivision(objects.getDivision().getDivisionName());
			    elephantReportResponse.setDivisionId(objects.getDivision().getDivisionId().toString());
			}else {
				elephantReportResponse.setDivision("");
			    elephantReportResponse.setDivisionId("");
			}
		
			if(objects.getRange() != null)
			{
				elephantReportResponse.setRange(objects.getRange().getRangeName());
			    elephantReportResponse.setRangeId(objects.getRange().getRangeId().toString());
			}else {
				elephantReportResponse.setRange("");
			    elephantReportResponse.setRangeId("");
			}
			if(objects.getSection()!= null)
			{
				elephantReportResponse.setSection(objects.getSection().getSecName());
			    elephantReportResponse.setSectionId(objects.getSection().getSecId().toString());
			}else {
				elephantReportResponse.setSection("");
			    elephantReportResponse.setSectionId("");
			}
			
			list.add(elephantReportResponse);
		}
		list.sort(Comparator.comparing(ElephantReportResponse::getReportingDate).reversed());

		return list;
	}

	@Override
	public List<ElephantReportResponse> viewAllReportData(String division, String range, String section,String beat) 
	{
		List<ElephantReportResponse> list = new ArrayList<ElephantReportResponse>();
		List<Object[]> viewAllReportData = elephantDeathReportRepoCustom.viewAllReportData(division, range, section, beat);
		for (Object[] objects : viewAllReportData) 
		{
			int i = 0;
			ElephantReportResponse elephantReportResponse = new ElephantReportResponse();
			if(objects[i] != null) {
				elephantReportResponse.setReportId(objects[i].toString());
			}
			++i;//action by
			if(objects[++i] != null)
				elephantReportResponse.setActionDate(objects[i].toString());
			if(objects[++i] != null) {
				elephantReportResponse.setAudioMessagePath(objects[i].toString());
			}else {
				elephantReportResponse.setAudioMessagePath("");
			}
			++i;//broad desc
			++i;//created on 
			if(objects[++i] != null)
				elephantReportResponse.setDescription(objects[i].toString());
			if(objects[++i] != null)
			{
				elephantReportResponse.setImgPath(objects[i].toString());
			}else {
				elephantReportResponse.setImgPath("");
			}
			++i;//is active 
			if(objects[++i] != null)
				elephantReportResponse.setLatitude(objects[i].toString());
			if(objects[++i] != null)
				elephantReportResponse.setLongitude(objects[i].toString());
			if(objects[++i] != null) {
					elephantReportResponse.setReportingDate(objects[i].toString());
				}
			if(objects[++i] != null)
				elephantReportResponse.setStatus(objects[i].toString());
			++i;//update on
			if(objects[++i] != null)
				elephantReportResponse.setUserId(objects[i].toString());
			if(objects[++i] != null) {
				elephantReportResponse.setVideoMessagePath(objects[i].toString());
				}else {
					elephantReportResponse.setVideoMessagePath("");	
				}
			if(objects[++i] != null)
			{
				elephantReportResponse.setBeat(beatRepository.findById(Integer.parseInt(String.valueOf(objects[i]))).get().getBeatName());
			    elephantReportResponse.setBeatId(objects[i].toString());
			}else {
				elephantReportResponse.setBeat("");
			    elephantReportResponse.setBeatId("");
			}
			
			if(objects[++i] != null) 
			{
			    elephantReportResponse.setDivision(divisionRepository.findById(Integer.parseInt(String.valueOf(objects[i]))).get().getDivisionName());
			    elephantReportResponse.setDivisionId(objects[i].toString());
			}else {
				elephantReportResponse.setDivision("");
			    elephantReportResponse.setDivisionId("");
			}
		
			if(objects[++i] != null)
			{
				elephantReportResponse.setRange(rangeMasterRepo.findById(Integer.parseInt(String.valueOf(objects[i]))).get().getRangeName());
			    elephantReportResponse.setRangeId(objects[i].toString());
			}else {
				elephantReportResponse.setRange("");
			    elephantReportResponse.setRangeId("");
			}
			if(objects[++i] != null)
			{
				elephantReportResponse.setSection(sectionRepository.findById(Integer.parseInt(String.valueOf(objects[i]))).get().getSecName());
			    elephantReportResponse.setSectionId(objects[i].toString());
			}else {
				elephantReportResponse.setSection("");
			    elephantReportResponse.setSectionId("");
			}
			
			
			
			//elephantReportResponse.setReportingDate(objects[i].toString());
			
			
			
			list.add(elephantReportResponse);
		}
		return list;
	}
	public void authorityReporting(double lat,double lon,String divisionId)
	{
		List<Object> allBroadcastDiv = publicUserCustomRepo.getAllAuthorityNum(lat,lon);
		Iterator<Object> iterator = allBroadcastDiv.iterator();
	  	 while (iterator.hasNext()) {
			Object[] next = (Object[]) iterator.next();
				List<String> findMobileByDivision = authRepository.findMobileByDivisionId(Integer.parseInt(String.valueOf(next[0])));
				if(!findMobileByDivision.isEmpty())
				{
					for (String mobile : findMobileByDivision) 
					{
						try 
						{
						String Otp = "1234";
						//String Username = "suru.mistry007@gmail.com"; //should be uncomment after proper template
						String Username = "123@gmail.com";//should be Comment after proper template
						String apiKey = "&apikey="+ URLEncoder.encode("UOMDAJ+z8HI-kFedKgMqcKVC8xpOolCny9DxlRtbiT", "UTF-8");
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
	@Override
	public String broadCastMsg(String divisionId,String lat,String lon) 
	{
		String msg="";
		String sResult = "";
		List<PublicUserDO> findByDivision = PublicUserRepo.findByDivision(divisionId);
		authorityReporting(Double.parseDouble(lat), Double.parseDouble(lon),divisionId);
		List<MobileNumberResponseDTO> allBroadcastNum = publicUserCustomRepo.getAllBroadcastNum(Double.parseDouble(lat), Double.parseDouble(lon),divisionId);
		for (MobileNumberResponseDTO mobileNumberResponseDTO : allBroadcastNum) 
		{
			
			try {
				String Otp = "1234";
				//String Username = "suru.mistry007@gmail.com";		//should be uncomment after proper template
				String Username = "123@gmail.com";  //should be Comment after proper template
				String apiKey = "&apikey="+ URLEncoder.encode("UOMDAJ+z8HI-kFedKgMqcKVC8xpOolCny9DxlRtbiT", "UTF-8");
				String message = "&message=" + URLEncoder.encode("Your Login Verification Code is " + Otp + "");
				String sender = "&sender=" + URLEncoder.encode("SPARCS", "UTF-8");
				String numbers = "&numbers=" + URLEncoder.encode(mobileNumberResponseDTO.getMobile(), "UTF-8");
				// Send data
				String data = "http://sms.webadd.in/api2/send/?username=" + Username + apiKey + numbers + message+ sender;
				URL url = new URL(data);
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				
				while ((line = rd.readLine()) != null) {
					// Process line...
					sResult = sResult + line + " ";
				}
				rd.close();// stringBuffer.toString();
			} catch (Exception e) {
				System.out.println("Error SMS " + e);
				// return "Error "+e;
			}
		}
		if(sResult.contains("success"))
		{
			msg="success";
	      }else {
	    	  msg="failed"; 
	      }
		return msg;
	}

	@Override
	public ElephantReportDO broadCastedStatus(String reportId,String desc, String status,String userid) 
	{
		ElephantReportDO elephantReport = elephantReportRepositroy.findByReportId(Integer.parseInt(reportId)).get();
		//ElephantReportDO ElephantReportDO = new ElephantReportDO();
		if(elephantReport != null)
		{
			elephantReport.setStatus(status);
			if(desc!="")
			elephantReport.setBroadcastDescription(desc);
			elephantReport.setActionBy(Integer.parseInt(userid));
			elephantReport.setActionDate(new Date());
		}
		return elephantReportRepositroy.save(elephantReport);
	}

	
}
