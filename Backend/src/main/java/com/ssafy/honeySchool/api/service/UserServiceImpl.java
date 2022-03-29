package com.ssafy.honeySchool.api.service;


import com.querydsl.core.Tuple;
import com.ssafy.honeySchool.api.dto.UserNameDto;
import com.ssafy.honeySchool.api.request.UserModifyPutReq;
import com.ssafy.honeySchool.api.request.UserRegisterPostReq;
import com.ssafy.honeySchool.db.entity.School;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.SchoolRepository;
import com.ssafy.honeySchool.db.repository.SchoolRepositorySupport;
import com.ssafy.honeySchool.db.repository.UserRepository;
import com.ssafy.honeySchool.db.repository.UserRepositorySupport;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    SchoolRepositorySupport schoolRepositorySupport;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SchoolRepository schoolRepository;

    @Value("${open.neis.openapi.url}")
    private String schoolUrl;

    @Value("${open.neis.key}")
    private String key;

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user= User.builder().userId(userRegisterInfo.getUser_id())
                .email(userRegisterInfo.getEmail())
                .classes(userRegisterInfo.getClass_number())
                .name(userRegisterInfo.getName())
                .grade(userRegisterInfo.getGrade())
                .password(passwordEncoder.encode(userRegisterInfo.getPassword()))
                .number(userRegisterInfo.getStudent_number())
                .school(userRegisterInfo.getSchool())
                .position(userRegisterInfo.getPosition())
                .birth(userRegisterInfo.getBirth())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        // 디비에 유저 정보 조회 (userId 를 통한 조회).
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        if(!user.isPresent()) return null;
        return user.get();
    }

    @Override
    public void deleteByUserId(String userId){
        userRepository.deleteByUserId(userId);
    }

    @Override
    public User updateUser(String id, UserModifyPutReq userModifyPutReq){
        Optional<User> pUser = userRepositorySupport.findUserByUserId(id);
        if(!pUser.isPresent()) return null;

        User user= pUser.get();
        user.setName(userModifyPutReq.getName());
        user.setSchool(userModifyPutReq.getSchool());
        user.setNumber(userModifyPutReq.getStudent_number());
        user.setGrade(userModifyPutReq.getGrade());
        user.setPassword(passwordEncoder.encode(userModifyPutReq.getPassword()));
        user.setClasses(userModifyPutReq.getClass_number());
        return userRepository.save(user);
    }

    @Override
    public List<UserNameDto> getUserList(int schoolNumber, int gradeNumber, int classNumber) {
        String school=schoolRepositorySupport.findSchoolByID(schoolNumber).get();

        return userRepositorySupport.findUserList(school,gradeNumber,classNumber).get();
    }

    @Override
    public int getSchoolNumberBySchool(String school) {
        int schoolNumber=schoolRepositorySupport.findIdBySchool(school).get();
        return schoolNumber;
    }


    @Override
    public String getSchoolNameByOpenApi() {
        for (int i = 1; i < 65; i++) {
            String tmp=WebClient.create(schoolUrl)
                    .method(HttpMethod.POST)
                    .uri("?KEY="+key+"&pIndex="+i+"&pSize="+"100"+"&Type="+"json"+"&SCHUL_KND_SC_NM="+"초등학교")
                    .retrieve()
                    .bodyToMono(String.class).block();

            try{
                JSONParser jsonParser=new JSONParser();

                JSONObject jsonObject=(JSONObject) jsonParser.parse(tmp);

                JSONArray schoolInfoArray = (JSONArray) jsonObject.get("schoolInfo");

                JSONArray rowArray=null;

                for (int j = 0; j < schoolInfoArray.size(); j++) {
                    JSONObject schoolInfoObject=(JSONObject) schoolInfoArray.get(j);
                    rowArray=(JSONArray) schoolInfoObject.get("row");
                }
                for (int j = 0; j < rowArray.size(); j++) {
                    JSONObject rowObject=(JSONObject) rowArray.get(j);

                    String name=rowObject.get("SCHUL_NM").toString();

                    schoolRepository.save(new School(name));
                }

            } catch (ParseException e) {
                e.printStackTrace();
                return "Fail";
            }
        }

        return "Success";
    }

}
