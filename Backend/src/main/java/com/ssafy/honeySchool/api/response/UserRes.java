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
public class UserRes {
	@ApiModelProperty(name="User ID")
	String userId;
	@ApiModelProperty(name="학교 이름")
	String school;
	@ApiModelProperty(name="Name")
	private String name;
	@ApiModelProperty(name="Student Grade")
	private int grade;
	@ApiModelProperty(name="Student Class")
	private int class_number;
	@ApiModelProperty(name="Student number")
	private int student_number;
	@ApiModelProperty(name="선생/학생")
	private String position;
	@ApiModelProperty(name="학교 번호")
	private int school_number;

	public static UserRes of(User user,int school_number) {
		UserRes res = new UserRes();
		res.setUserId(user.getUserId());
		res.setStudent_number(user.getNumber());
		res.setClass_number(user.getClasses());
		res.setName(user.getName());
		res.setGrade(user.getGrade());
		res.setPosition(user.getPosition());
		res.setSchool(user.getSchool());
		res.setSchool_number(school_number);
		return res;
	}
}
