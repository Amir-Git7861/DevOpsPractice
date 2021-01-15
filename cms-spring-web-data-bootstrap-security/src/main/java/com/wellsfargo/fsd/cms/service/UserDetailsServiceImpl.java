package com.wellsfargo.fsd.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wellsfargo.fsd.cms.dao.CMSUserRepo;
import com.wellsfargo.fsd.cms.entity.CMSUser;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder pEnc;
	
	@Autowired
	private CMSUserRepo userRepo;
	
		@Override
		public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
			
			UserDetails userDetails;
			
			CMSUser user=userRepo.findByUserName(userName);
			
			if(user==null) {
				throw new UsernameNotFoundException("No Such user Found! ");
			}
			
			List<GrantedAuthority> authorities=new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(user.getRole()));
			
			userDetails=(UserDetails) new User(
					user.getUserName(),
					user.getEncodedPassword(),
					authorities
					
					);
			return userDetails;
			
		}
		public CMSUser save(CMSUser user) {
			user.setEncodedPassword(pEnc.encode(user.getPassword()));
			return userRepo.save(user);
		
	}
}
		

	


