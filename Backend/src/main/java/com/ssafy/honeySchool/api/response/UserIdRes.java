package com.ssafy.honeySchool.api.response;

import com.ssafy.honeySchool.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 ID 정보 조회 API ([GET] /api/v1/users/userId) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserIdRes{
	@ApiModelProperty(name="User ID")
	String userId;
	
	public static UserIdRes of(User user) {
		UserIdRes res = new UserIdRes();
		res.setUserId(user.getUserId());
		return res;
	}
}
