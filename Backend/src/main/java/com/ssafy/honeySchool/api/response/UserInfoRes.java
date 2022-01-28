package com.ssafy.honeySchool.api.response;

import com.ssafy.honeySchool.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/info/{id}) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@Builder
@ApiModel("UserInfoResponse")
public class UserInfoRes {
    @ApiModelProperty(name="User ID")
    private String userId;
    @ApiModelProperty(name="Name")
    private String name;
    @ApiModelProperty(name="User Position")
    private String position;
    @ApiModelProperty(name="School Name")
    private String school;
    @ApiModelProperty(name="Student Grade")
    private int grade;
    @ApiModelProperty(name="Student Class")
    private int classes;
    @ApiModelProperty(name="Student number")
    private int number;
    @ApiModelProperty(name="Email")
    private String email;

    public static UserInfoRes of(User user) {
        UserInfoRes userInfoRes= UserInfoRes.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .position(user.getPosition())
                .school(user.getSchool())
                .grade(user.getGrade())
                .number(user.getNumber())
                .email(user.getEmail())
                .classes(user.getClasses())
                .build();
        return userInfoRes;
    }
}
