package com.sparc.pccf.wildlife.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.dto.PublicUserDto;
import com.sparc.pccf.wildlife.entity.ElephantReportDO;
import com.sparc.pccf.wildlife.entity.PublicUserDO;
import com.sparc.pccf.wildlife.request.LoginRequest;
import com.sparc.pccf.wildlife.request.PublicUserLoginRequest;
import com.sparc.pccf.wildlife.response.ElephantReportResponse;
import com.sparc.pccf.wildlife.response.MessageResponse;
import com.sparc.pccf.wildlife.service.IGajasathiUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/gajasathiController")
public class GajasthiUserController {
	
	@Autowired
	private IGajasathiUserService iGajasathiUserLoginService;
	
	@PostMapping("/generateOtp")
	public ResponseEntity<?> generateOtp(@RequestParam String mobile) 
	{		 
		 String generateOtp = iGajasathiUserLoginService.generateOtp(mobile);
		  return ResponseEntity.ok(new MessageResponse(generateOtp));
	}
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody PublicUserLoginRequest usrReq) 
	{		 
		 String generateOtp = iGajasathiUserLoginService.createUser(usrReq);
		  return ResponseEntity.ok(new MessageResponse(generateOtp));
	}
	
	@PostMapping("/otpBasedLogin")
	public ResponseEntity<?> otpBasedLogin(@RequestParam String mobile)
	{
		return iGajasathiUserLoginService.otpBasedLogin(mobile); 
		
	}
	
	@PostMapping("/report")
	public ElephantReportResponse report(@RequestParam String textMessage,
									@RequestParam String userID,
									@RequestParam String latitude,
									@RequestParam String longitude,
									@RequestParam MultipartFile image,
									@RequestParam MultipartFile audioMessage,
									@RequestParam MultipartFile videoMessage,
									@RequestParam String type,
									@RequestParam String status,
									@RequestParam String reportingDate)
	{
		
		 return  iGajasathiUserLoginService.report(textMessage,userID,latitude,longitude,image,audioMessage,videoMessage,type, status,reportingDate); 
		
	}
	@GetMapping("/getUserById")
	public PublicUserDto getUserById(@RequestParam String userId) 
	{		 		 
		  return iGajasathiUserLoginService.getUserById(userId);
	}
	
	@GetMapping("/getAllReportByUserID")
	public List<ElephantReportResponse> getAllReportByUserID(@RequestParam String userId) 
	{		 		 
		  return iGajasathiUserLoginService.getAllReportByUserID(userId);
	}
	
	//getALlData by filtering
	@GetMapping("/viewAllGajasathiReportData")
	public List<ElephantReportResponse> viewAllReportData(/* @RequestParam(value = "circle") String circle, */ 
			                                       @RequestParam(value = "division") String division,
			                                       @RequestParam(value = "range") String range, 
			                                       @RequestParam(value = "section") String section,
			                                       @RequestParam(value = "beat") String beat)
	{
		List<ElephantReportResponse> viewAllReportData = iGajasathiUserLoginService.viewAllReportData(division, range, section, beat);
		return viewAllReportData;
		
	}
	
	@PostMapping("/broadCastMsg")
	public ResponseEntity<?> broadCastMsg(@RequestParam String divisionId,@RequestParam String lat, @RequestParam String lon)
	{
		 String msg = iGajasathiUserLoginService.broadCastMsg(divisionId, lat, lon);
		 return ResponseEntity.ok(new MessageResponse(msg));
		
	}
	
	@PostMapping("/broadCastedStatus")
	public ElephantReportDO broadCastedStatus(@RequestParam String reportId,@RequestParam String desc,@RequestParam String status,String userid)
			   										
	{
		ElephantReportDO broadCastedStatus = iGajasathiUserLoginService.broadCastedStatus(reportId,desc, status,userid);
		return broadCastedStatus;
		
	}
}
