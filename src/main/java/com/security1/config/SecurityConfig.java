package com.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터(securityconfig)가 스프링 필터체인에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
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
		.defaultSuccessUrl("/");
	}
	
}
