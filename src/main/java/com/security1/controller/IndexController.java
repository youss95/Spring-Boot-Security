package com.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security1.config.auth.PrincipalDetails;
import com.security1.model.User;
import com.security1.repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//시큐리티 세션에는 Authentication 객체가 있다. Userdetails: 일반로그인 하면 이게 authentication안에 들감, oauth2user:소셜로그인 타입을 사용가능
	//그럼 로그인 할때 구분은 어떻게 함?
	//X라는 클래스 만들자 UserDetails와 Oauth2User를 상속받는 놈 근데 UserDetails는 PrincipalDetails가 implements 한 놈이여서 PrincipalDetails로 
	
	//2가지 방법으로 getuser 
	@ResponseBody
	@GetMapping("/test/login")
	public String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
		System.out.println("/test/login =============");
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();//다운캐스팅
		System.out.println("authentication: "+principalDetails.getUser());
		System.out.println("userDetails: "+userDetails.getUser());
		return "세션 정보 확인하기";
	}
	
	@ResponseBody
	@GetMapping("/test/oauth/login")
	public String oauthTest(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/login =============");
		OAuth2User principalDetails = (OAuth2User)authentication.getPrincipal();//다운캐스팅
		System.out.println("authentication: "+principalDetails.getAttributes());
		System.out.println("oauth2user: "+oauth.getAttributes());
		return "oauth 세션 정보 확인하기";
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody  String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm")
	public  String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public  String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "redirect:/loginForm";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")   //메소드가 실행되기 직전에실행 ,여러개 설정하고 싶을때
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "DATA";
	}
	
	@GetMapping("/joinProc")
	public  String joinProc() {
		return "/";
	}
}


