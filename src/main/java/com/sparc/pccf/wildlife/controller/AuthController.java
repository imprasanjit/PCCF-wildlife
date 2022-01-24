package com.sparc.pccf.wildlife.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparc.pccf.wildlife.dto.CitizenUserInfoDto;
import com.sparc.pccf.wildlife.dto.ProfileDto;
import com.sparc.pccf.wildlife.dto.UserInfoDto;
import com.sparc.pccf.wildlife.entity.CitizenUser;
import com.sparc.pccf.wildlife.entity.User;
import com.sparc.pccf.wildlife.repository.AuthRepository;
import com.sparc.pccf.wildlife.repository.DivMasterRepository;
import com.sparc.pccf.wildlife.repository.LoginActivityRepository;
import com.sparc.pccf.wildlife.request.ChangePassWordRequest;
import com.sparc.pccf.wildlife.request.ChangeUsernameRequest;
import com.sparc.pccf.wildlife.request.CitizenFilterRequest;
import com.sparc.pccf.wildlife.request.CitizenSignUpRequest;
import com.sparc.pccf.wildlife.request.FilterRequest;
import com.sparc.pccf.wildlife.request.LoginRequest;
import com.sparc.pccf.wildlife.request.SignupRequest;
import com.sparc.pccf.wildlife.response.JuridicitionResponse;
import com.sparc.pccf.wildlife.response.MessageResponse;
import com.sparc.pccf.wildlife.security.jwt.JwtUtils;
import com.sparc.pccf.wildlife.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthService authService;
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	DivMasterRepository masterRepo;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) 
	{
		ResponseEntity<?> signIn = authService.signIn(loginRequest,request);
	       if(signIn!=null)
	    	{
	    	    return signIn;
	    	}else {
	    		 return ResponseEntity.badRequest().body(new MessageResponse("LoginFailed"));
	    	}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (authService.existsByUsername(signUpRequest.getUsername()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));

		authService.createUser(signUpRequest);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	@PostMapping("/citizensignup")
	public ResponseEntity<?> citizensignup(@RequestBody CitizenSignUpRequest citizenSignUpRequest) {

		CitizenUser createCitizenUser = authService.createCitizenUser(citizenSignUpRequest);
		if(createCitizenUser!=null) {
			return ResponseEntity.ok(new MessageResponse("success"));
		}else {
			return ResponseEntity.ok(new MessageResponse("failed"));
		}
	}
	@GetMapping("/getJuridicitionByRoleId")
	public List<JuridicitionResponse> getJuridicitionByRoleId(@RequestParam String roleId) 
	{
		List<JuridicitionResponse> juridicitionResponse =authService.getJuridicitionByRoleId(roleId);
		return juridicitionResponse;
	}
	@PutMapping("/updateuser")
	public ResponseEntity<?> updateUser(@RequestBody SignupRequest signUpRequest) 
	{
		authService.updateUser(signUpRequest);
		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
	}

	@GetMapping("/getuser")
	public List<UserInfoDto> getAllUser() {
		List<UserInfoDto> allUser= new ArrayList<UserInfoDto>();
		try {
			allUser= authService.getAllUser();
		
		}catch(Exception  ex) {
			ex.printStackTrace();
		}
		return allUser;
		
	}
	
	@GetMapping("/getAllUserByCreatedBy")
	public List<UserInfoDto> getAllUserByCreatedBy(@RequestParam Integer id) 
	{
		//List<User> findByCraetedBy = authRepository.findByCraetedBy(id);
		 List<UserInfoDto> allUserByCreatedBy = authService.getAllUserByCreatedBy(id);
		return allUserByCreatedBy;
	}
	@PostMapping("/getAllUserByFilter")
	public List<UserInfoDto> getAllUserByFilter(@RequestBody FilterRequest cPRequest) 
	{
		 List<UserInfoDto> allUserByCreatedBy = authService.getAllUserByFilter(cPRequest);
		return allUserByCreatedBy;
	}
	@PostMapping("/getAllCitizenUserByFilter")
	public List<CitizenUserInfoDto> getAllCitizenUserByFilter(@RequestBody CitizenFilterRequest cPRequest) 
	{
		 List<CitizenUserInfoDto> allUserByCreatedBy = authService.getAllCitizenUserByFilter(cPRequest);
		return allUserByCreatedBy;
	}
	@GetMapping("/getuserById")
	public ProfileDto getUserById(@RequestParam String loginId) {
		return authService.getUserById(loginId);
	}

	/*
	 * @GetMapping("/profile") public ProfileDto getUserProfileById(@RequestParam
	 * String loginId) { return authService.getUserProfileById(loginId); }
	 * 
	 * @PutMapping("/updateProfile") public ResponseEntity<?>
	 * updateProfile(@RequestBody SignupRequest signUpRequest) { String result =
	 * authService.updateProfile(signUpRequest); return ResponseEntity.ok(new
	 * MessageResponse(result)); }
	 */
	@PutMapping("/getuserByIdAndDeactivate")
	public String getuserByIdAndDeactivate(@RequestParam String loginId, @RequestParam String role,@RequestParam boolean isActive) 
	{
		User user = authService.findByUsername(loginId).get();
		return authService.deactivateUser(user, role, isActive);
	}
	
	@GetMapping("/checkUserNameAvail")
	public boolean checkUserAvail(@RequestParam String userName) 
	{
		if (authService.existsByUsername(userName))
			return true;
		else
			return false;
	}

	@GetMapping("/checkEmailAvail")
	public boolean checkEmailAvail(@RequestParam String email) {
 		if (authService.existsByEmail(email)) 
			return true;
		else
			return false;
	}
	@GetMapping("/checkPhoneAvail")
	public boolean checkPhoneAvail(@RequestParam String phone) {
 		if (authService.existsByPhone(phone)) 
			return true;
		else
			return false;
	}
	@GetMapping("/checkCitizenUserPhoneAvail")
	public boolean checkCitizenUserPhoneAvail(@RequestParam String phone) {
 		if (authService.existsCitizenUserByPhone(phone)) 
			return true;
		else
			return false;
	}
	
//	@PutMapping("/resetpassword")
//	public User resetpassword(@RequestParam String username,@RequestParam String newPasswod,String oldPassword)
//	{
//		User resetPassword = authService.resetPassword(username,newPasswod,oldPassword);
//		return resetPassword;	
//	}
	
	@PostMapping("/changePassWord")
	public ResponseEntity<?> changePassWord(@RequestBody ChangePassWordRequest cPRequest)
	{
		
		String msg=authService.changePassWord(cPRequest.getUsername(), cPRequest.getOldpassword(), cPRequest.getNewPassword());
		
			return ResponseEntity.ok(new MessageResponse(msg));
	}
	@PutMapping("/resetPasswordWithMobile")
	public ResponseEntity<?>  resetPasswordWithMobile(@RequestParam String mobile,@RequestParam String newPassword)
	{
		String msg= authService.resetPasswordWithMobile(mobile,newPassword);
		return ResponseEntity.ok(new MessageResponse(msg));
	}
	
	
	/* change user API */
	
	@PostMapping("/changeUsername")
	public ResponseEntity<?> changeUsername(@RequestBody ChangeUsernameRequest URequest)
	{	
		String changeUsername = authService.changeUsername(URequest);
		return ResponseEntity.ok(new MessageResponse(changeUsername));
	}
	
	@PutMapping("/resetUsernameWithMobile")
	public ResponseEntity<?> resetUsernameWithMobile(@RequestParam String username,@RequestParam String newUsername)
	{
		String resetUsernameWithMobile = authService.resetUsernameWithMobile(username, newUsername);
		return ResponseEntity.ok(new MessageResponse(resetUsernameWithMobile));
	}
	
	
	@PostMapping("/generateotp")
	public  ResponseEntity<?> generateotp(@RequestParam String mobileNumber)
	{
		 String generateotp = authService.generateotp(mobileNumber);
		return ResponseEntity.ok(new MessageResponse(generateotp));
	}
	@PostMapping("/otpVerified")
	public ResponseEntity<?> otpVerified(@Valid @RequestBody LoginRequest loginRequest)
	{
		return authService.otpVerified(loginRequest.getLogin_id(),loginRequest.getPassword()); 		
	}	
	
	
	@PostMapping("/otpBasedLogin")
	public ResponseEntity<?> otpBasedLogin(@Valid @RequestBody LoginRequest loginRequest)
	{
		return authService.otpBasedLogin(loginRequest.getLogin_id(),loginRequest.getPassword()); 		
	}	
	@GetMapping("/getAllCitizenUserByCreatedBy")
	public List<CitizenUserInfoDto> getAllCitizenUserByCreatedBy(@RequestParam Integer id) 
	{
		//List<User> findByCraetedBy = authRepository.findByCraetedBy(id);
		 List<CitizenUserInfoDto> allUserByCreatedBy = authService.getAllCitizenUserByCreatedBy(id);
		return allUserByCreatedBy;
	}
	@GetMapping("/getAllCitizenUser")
	public List<CitizenUserInfoDto> getAllCitizenUser() 
	{
		 List<CitizenUserInfoDto> allUserByCreatedBy = authService.getAllCitizenUser();
		return allUserByCreatedBy;
	}
  
}
