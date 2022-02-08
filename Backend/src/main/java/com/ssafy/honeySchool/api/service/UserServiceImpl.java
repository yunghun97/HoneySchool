package com.ssafy.honeySchool.api.service;


import com.ssafy.honeySchool.api.request.UserModifyPutReq;
import com.ssafy.honeySchool.api.request.UserRegisterPostReq;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.UserRepository;
import com.ssafy.honeySchool.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user= User.builder().userId(userRegisterInfo.getUser_id())
                .email(userRegisterInfo.getEmail())
                .classes(userRegisterInfo.getClasses())
                .name(userRegisterInfo.getName())
                .grade(userRegisterInfo.getGrade())
                .password(passwordEncoder.encode(userRegisterInfo.getPassword()))
                .number(userRegisterInfo.getNumber())
                .school(userRegisterInfo.getSchool())
                .position(userRegisterInfo.getPosition())
                .birth(userRegisterInfo.getBirth())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        // 디비에 유저 정보 조회 (userId 를 통한 조회).
        User user = userRepositorySupport.findUserByUserId(userId).get();
        return user;
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
        user.setPosition(userModifyPutReq.getPosition());
        user.setNumber(userModifyPutReq.getNumber());
        user.setEmail(userModifyPutReq.getEmail());
        user.setPassword(passwordEncoder.encode(userModifyPutReq.getPassword()));
        user.setGrade(userModifyPutReq.getGrade());
        user.setClasses(userModifyPutReq.getClasses());
        return userRepository.save(user);
    }
}
