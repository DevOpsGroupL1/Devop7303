package com.boltonuni.devop7303.security;

import com.boltonuni.devop7303.entity.Role;
import com.boltonuni.devop7303.entity.UserRole;
import com.boltonuni.devop7303.service.RoleService;
import com.boltonuni.devop7303.service.UserRoleService;
import com.boltonuni.devop7303.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, LockedException {

		com.boltonuni.devop7303.entity.User user = userService.findByEmail(email);
		System.out.println("Auth user before: "+user.toString());
		if (user == null) {
//			System.out.println("@@@@@@@@@@@@@@");
			throw new UsernameNotFoundException("Invalid credentials");
		}
		if(!user.isActive())
			throw new DisabledException("This account is not verified.");
		UserRole userRole = userRoleService.findByUserId(user.getId());
		Role role = roleService.findRoleById(userRole.getRoleId());
		List<GrantedAuthority> roleAuth = new ArrayList<>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.getRoleName());
		roleAuth.add(auth);
		return new User(user.getEmail(), user.getPassword(), roleAuth);
	}
	

}