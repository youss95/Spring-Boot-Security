package com.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.security1.model.User;

import lombok.Data;

//시큐리티가 /login 낚아채서 로그인 진행하는데 진행 완료되면 세션에 넣어주는데 (security contextHolder)라는 키값에 세션 정보를 저장
//Authentication객체여야 세션에 들어갈 수 있다.
//Authentication안에는 유저정보가 있어야 된다.
//유저 객체의 타입은 UserDetails 여야 한다.
//=> 시큐리티 세션에 들어갈 객체 : Authentication => UserDetails
//principal imple 하면 Authentcation안에 넣을수 있다.
@Data
public class PrincipalDetails implements UserDetails,OAuth2User{
	
	private User user;
	
	public PrincipalDetails(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}
	
	//해당 유저의 권한을 리턴하는 곳
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
	collect.add(new GrantedAuthority() {	
		@Override
		public String getAuthority() {
			// TODO Auto-generated method stub
			return user.getRole();
		}
	});
	return collect;
}

@Override
	public String getPassword() {
	
		return user.getPassword();
	}
@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

@Override
public boolean isAccountNonExpired() {
	
	return true;
}

@Override
public boolean isAccountNonLocked() {
	
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	
	return true;
}

@Override
public boolean isEnabled() {
	
	
	//우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로함 (private timestamp loginDate)
	//user.getLoginDate() 가져와서 현재시간 - 로그인시간 => 1년 초과 하면 return false;
	return true;
}

@Override
public Map<String, Object> getAttributes() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getName() {
	// TODO Auto-generated method stub
	return null;
}

}
