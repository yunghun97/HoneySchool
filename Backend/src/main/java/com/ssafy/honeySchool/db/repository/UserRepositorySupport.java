package com.ssafy.honeySchool.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.honeySchool.api.dto.UserNameDto;
import com.ssafy.honeySchool.api.response.UserListRes;
import com.ssafy.honeySchool.db.entity.QUser;
import com.ssafy.honeySchool.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    // UserId로 User 정보 return
    public Optional<User> findUserByUserId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public Optional<List<UserNameDto>> findUserList(String school, int gradeNumber, int classNumber){
        List<Tuple> result= jpaQueryFactory.select(qUser.name,qUser.number).from(qUser)
                .where(qUser.school.eq(school),qUser.grade.eq(gradeNumber),qUser.classes.eq(classNumber))
                .fetch();
        if(result == null) return Optional.empty();

        List<UserNameDto> userList=new ArrayList<>();

        for (Tuple tuple : result){
            UserNameDto user=new UserNameDto();
            user.setName(tuple.get(qUser.name));
            user.setStudentNumber(tuple.get(qUser.number));
            userList.add(user);
        }
        return Optional.ofNullable(userList);
    }
}
