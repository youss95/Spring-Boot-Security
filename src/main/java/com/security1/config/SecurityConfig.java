package com.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security1.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터(securityconfig)가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true ,prePostEnabled = true) //secured 어노테시연 활성화 , pre: preAuthorized 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalOauth2UserService oauth2UserService;
	
	@Bean //ioc 등록
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/user/**").authenticated() //인증필요해
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll() //위에 주소 외에는 권한 허용
		.and().formLogin().loginPage("/loginForm")
		.loginProcessingUrl("/login") //login 주소가 호출되면 시큐리티가 낚아채서 대신 진행 : 컨트롤러 필요없다.
		.defaultSuccessUrl("/")
		.and()
		.oauth2Login()
		.loginPage("/loginForm")
		.userInfoEndpoint().userService(oauth2UserService)
		; //구글 로그인이 완료되면 코드가아니라 (엑세스토큰 + 사용자프로필정보) 한번에 받아준다.
		
		  //카카오 로그인이 완료되면 1. 코드받기(인증) 2. 엑세스토큰(권한)
		  //3. 사용자프로필 정보를 가져오기 4. 정보를 토대로 회원가입 자동진행   
		  //4-1. 정보가 모자를 경우 : 
	}
	
}
