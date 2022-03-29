package com.ssafy.honeySchool.common.auth;

import com.ssafy.honeySchool.api.service.UserService;
import com.ssafy.honeySchool.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
public class HoneySchoolUserDetailService implements UserDetailsService{
	@Autowired
	UserService userService;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    		User user = userService.getUserByUserId(username);
    		if(user != null) {
    			HoneySchoolUserDetails userDetails = new HoneySchoolUserDetails(user);
    			return userDetails;
    		}
    		return null;
    }
}
