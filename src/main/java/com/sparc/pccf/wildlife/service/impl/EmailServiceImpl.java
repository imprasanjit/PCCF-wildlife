package com.sparc.pccf.wildlife.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sparc.pccf.wildlife.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	/*
	 * @Autowired private JavaMailSender mailSender;
	 */

	@Override
	@Async
	public void sendEmail(SimpleMailMessage email) {
		//mailSender.send(email);
	}
}
