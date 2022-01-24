package com.sparc.pccf.wildlife.controller;

import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparc.pccf.wildlife.Utility.CustomUtility;
import com.sparc.pccf.wildlife.Utility.SiteURL;
import com.sparc.pccf.wildlife.entity.User;
import com.sparc.pccf.wildlife.repository.AuthRepository;
import com.sparc.pccf.wildlife.response.MessageResponse;
import com.sparc.pccf.wildlife.service.AuthService;
import com.sparc.pccf.wildlife.service.EmailService;

/**
 * @author Prasanjit
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/password")
public class PasswordController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/resetpassword")
	public ResponseEntity<?> processForgotPassword(HttpServletRequest request, @RequestParam String userEmail) throws AddressException, MessagingException
	{
		//Boolean existsByEmail = authRepo.existsByEmail(userEmail);
		Optional<User> findUserByEmail = authService.findUserByEmail(userEmail);
		if (findUserByEmail.isPresent())
		{
			User user = findUserByEmail.get();
			user.setResetToken(UUID.randomUUID().toString());
			// Save token to database
			authService.save(user);	
		String _emailSubject = "Password Reset Request";
		
		SiteURL siteURL=new SiteURL();
		String _emailBody = siteURL.getSiteURL(request) + "/resetpassword?token=" + user.getResetToken();
		
		//String _emailBody = ("http://localhost:8080/resetpassword?token="+ user.getResetToken());
		
		CustomUtility customUtility=new CustomUtility();
		customUtility.sendEmail(userEmail, _emailSubject, _emailBody);
		}else
			throw new UsernameNotFoundException("not found");
		
		    return ResponseEntity.ok(new MessageResponse("A password reset link has been sent to your email")); 
		
	}
	
	@PutMapping("/resetpassword")
	public ResponseEntity<?> setNewPassword(@RequestParam String password,@RequestParam String resetToken) 
	{
		//Optional<User> user = authService.findUserByEmail(userEmail);
		Optional<User> user = authService.findUserByResetToken(resetToken);
		if (user.isPresent()) 
		{
			User resetUser = user.get(); 
			// Set new password    
            resetUser.setPassword(bCryptPasswordEncoder.encode(password));
			authService.save(resetUser);
			
	        // After Set the reset token to null so it can't be used again
	        resetUser.setResetToken(null);
		}
		return ResponseEntity.ok(new MessageResponse("Your password has sucessfully reset"));
   }

}
