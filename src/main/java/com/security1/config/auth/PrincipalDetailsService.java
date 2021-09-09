package com.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security1.model.User;
import com.security1.repository.UserRepository;


//언제 발동? 시큐리티 설정에서  loginprocess 걸어놔서 /login요청이 오면 자동으로 UserDetailService 타입으로 ioc되있는 loadbyusername실행
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // sesison(Authentication(UserDetail)) 요런식으로 알아서 해줌
	User user = userRepository.findByUsername(username);
	if(user != null) {
		return new PrincipalDetails(user); // Authentication(UserDetail) 처럼 Authentication안에 쏙 들어감
	}
		return null;
	}

}
