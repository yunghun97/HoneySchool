package com.ssafy.honeySchool.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	@ApiModelProperty(name="유저 Password", example="ssafy_password")
	String password;
	@ApiModelProperty(name="학교 이름", example = "싸피초등학교")
	String school;
	@ApiModelProperty(name="학년", example = "1")
	int grade;
	@ApiModelProperty(name="반", example = "2")
	int class_number;
	@ApiModelProperty(name="번호", example = "2")
	int student_number;
	@ApiModelProperty(name="유저 ID", example = "ssafy")
	String user_id;
	@ApiModelProperty(name="유저 email", example = "ssafy@naver.com")
	String email;
	@ApiModelProperty(name="교사인지 학생인지", example = "교사")
	String position;
	@ApiModelProperty(name="이름", example = "김싸피")
	String name;

	LocalDate birth;
}
