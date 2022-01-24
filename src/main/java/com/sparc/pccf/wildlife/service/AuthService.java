package com.sparc.pccf.wildlife.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.sparc.pccf.wildlife.dto.CitizenUserInfoDto;
import com.sparc.pccf.wildlife.dto.ProfileDto;
import com.sparc.pccf.wildlife.dto.UserInfoDto;
import com.sparc.pccf.wildlife.entity.CitizenUser;
import com.sparc.pccf.wildlife.entity.User;
import com.sparc.pccf.wildlife.request.ChangeUsernameRequest;
import com.sparc.pccf.wildlife.request.CitizenFilterRequest;
import com.sparc.pccf.wildlife.request.CitizenSignUpRequest;
import com.sparc.pccf.wildlife.request.FilterRequest;
import com.sparc.pccf.wildlife.request.LoginRequest;
import com.sparc.pccf.wildlife.request.SignupRequest;
import com.sparc.pccf.wildlife.response.JuridicitionResponse;

public interface AuthService {
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	Boolean existsByPhone(String phone);

	User save(User user);

	User createUser(SignupRequest signUpRequest);

	User updateUser(SignupRequest signUpRequest);

	List<UserInfoDto> getAllUser();

	ProfileDto getUserById(String loginId);

	String deactivateUser(User userById, String role, boolean isActive);

	/*
	 * ProfileDto getUserProfileById(String loginId);
	 * 
	 * String updateProfile(SignupRequest signUpRequest);
	 */

	List<JuridicitionResponse> getJuridicitionByRoleId(String roleId);
	
	String getJuridctionNameByRole(String role,String id);
	
	Integer getJuridctionIdByRole(String role,String id);

	Integer getRoleIdByUserName(String username);
	
	String changePassWord(String username, String oldpassword,String newPassword);
	
	//User resetPassword(String username,String newPasswod, String oldPassword);

	String generateotp(String mobileNumber);

	ResponseEntity<?> signIn(@Valid LoginRequest loginRequest, HttpServletRequest request);

	ResponseEntity<?> otpBasedLogin(String mobileNumber, String otp);
	
	public Optional<User> findUserByEmail(String email);
	
    public Optional<User> findUserByResetToken(String resetToken);

	ResponseEntity<?> otpVerified(String login_id, String password);

	String resetPasswordWithMobile(String mobile, String newPasswod);

	List<UserInfoDto> getAllUserByCreatedBy(Integer id);
	
	String changeUsername(ChangeUsernameRequest URequest);
	
	String  resetUsernameWithMobile(String username, String newUsername);

	List<UserInfoDto> getAllUserByFilter(FilterRequest cPRequest);

	List<CitizenUserInfoDto> getAllCitizenUserByCreatedBy(Integer id);

	Boolean existsCitizenUserByPhone(String phone);

	List<CitizenUserInfoDto> getAllCitizenUser();

	CitizenUser createCitizenUser(CitizenSignUpRequest citizenSignUpRequest);

	List<CitizenUserInfoDto> getAllCitizenUserByFilter(CitizenFilterRequest cPRequest);

    
    
}
