package com.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	//구글로 부터 받은 데이터에대한 후처리 함수
@Override
public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	System.out.println("userRequest: "+userRequest.getClientRegistration());
	System.out.println("userRequest: "+userRequest.getAccessToken());
	//구글 로그인 버튼 클릭 - 로그인 창 - 진행 로그인 완료 - code를 리턴(oAuth-clinet라는 라이브러리가 받아줌)
	//-> accesstoken요청 여기까지가 userrequest정보
	//이 정보로 회원 프로필 받아야함 그떄 쓰는 함수가 loaduser
	System.out.println("attributes: "+super.loadUser(userRequest).getAttributes());
	OAuth2User oauth2User = super.loadUser(userRequest);
	//super.loadUser가 하는 역할: 
	return super.loadUser(userRequest);
}

}
