package com.ssafy.honeySchool.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserModifyPutRequest")
public class UserModifyPutReq {
    @ApiModelProperty(name="유저 Password", example="ssafy_password")
    String password;
    @ApiModelProperty(name="학교 이름", example = "싸피초등학교")
    String school;
    @ApiModelProperty(name="학년", example = "1")
    int grade;
    @ApiModelProperty(name="반", example = "2")
    int classes;
    @ApiModelProperty(name="번호", example = "2")
    int number;
    @ApiModelProperty(name="유저 email", example = "ssafy@naver.com")
    String email;
    @ApiModelProperty(name="교사인지 학생인지", example = "교사")
    String position;
    @ApiModelProperty(name="이름", example = "김싸피")
    String name;
    @ApiModelProperty(name="유저 ID", example = "ssafy")
    String userId;

}
