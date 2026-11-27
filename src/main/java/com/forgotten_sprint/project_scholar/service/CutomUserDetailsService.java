package com.forgotten_sprint.project_scholar.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.forgotten_sprint.project_scholar.repository.UserRepository;

@Service
public class CutomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;
	
	public CutomUserDetailsService (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> 
					new UsernameNotFoundException(
								"User not found: " + username
							)
						);
	}
}
