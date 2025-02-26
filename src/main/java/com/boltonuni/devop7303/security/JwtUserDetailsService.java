package com.boltonuni.devop7303.security;

import com.boltonuni.devop7303.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, LockedException {

		com.boltonuni.devop7303.entity.User user = userService.findByEmail(email);
		
		if (user == null) {
//			System.out.println("@@@@@@@@@@@@@@");
			throw new UsernameNotFoundException("Invalid credentials");
		}
		if(!user.isActive())
			throw new DisabledException("This account is not verified.");
		return new User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}
	

}