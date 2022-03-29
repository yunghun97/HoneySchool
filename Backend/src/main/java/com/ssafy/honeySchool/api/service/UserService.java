package com.ssafy.honeySchool.api.service;


import com.ssafy.honeySchool.api.dto.UserNameDto;
import com.ssafy.honeySchool.api.request.UserModifyPutReq;
import com.ssafy.honeySchool.api.request.UserRegisterPostReq;
import com.ssafy.honeySchool.db.entity.School;
import com.ssafy.honeySchool.db.entity.User;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
    User createUser(UserRegisterPostReq userRegisterInfo);
    User getUserByUserId(String userId);
    void deleteByUserId(String userId);
    User updateUser(String id,UserModifyPutReq userModifyPutReq);
    List<UserNameDto> getUserList(int schoolNumber, int gradeNumber, int classNumber);
    int getSchoolNumberBySchool(String school);
    String getSchoolNameByOpenApi();
}
