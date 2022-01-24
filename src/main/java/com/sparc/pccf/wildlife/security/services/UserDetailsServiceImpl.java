package com.sparc.pccf.wildlife.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparc.pccf.wildlife.entity.User;
import com.sparc.pccf.wildlife.repository.AuthRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AuthRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username).get();
		if(user != null && user.isActive()) {
			return UserDetailsImpl.build(user);
		}
		else {
			 throw new UsernameNotFoundException("username not found");
		}		
	}
}
