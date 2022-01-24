package com.sparc.pccf.wildlife.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.dto.PublicUserDto;
import com.sparc.pccf.wildlife.entity.ElephantReportDO;
import com.sparc.pccf.wildlife.entity.PublicUserDO;
import com.sparc.pccf.wildlife.request.PublicUserLoginRequest;
import com.sparc.pccf.wildlife.response.ElephantReportResponse;

public interface IGajasathiUserService {

	String generateOtp(String mobile);

	String createUser(PublicUserLoginRequest usrReq);

	ResponseEntity<?> otpBasedLogin(String mobile);

	ElephantReportResponse report(String textMessage,String userID, String latitude, String longitude, MultipartFile image, MultipartFile audioMessage, MultipartFile videoMessage,String type,String status,String reportingDate);

	PublicUserDto getUserById(String userId);

	List<ElephantReportResponse> getAllReportByUserID(String userId);

    List<ElephantReportResponse> viewAllReportData(String division, String range, String section,String beat);
    
    String broadCastMsg(String divisionId,String lat,String lon);
    
    ElephantReportDO broadCastedStatus(String reportId,String desc,String status, String userid);


}
