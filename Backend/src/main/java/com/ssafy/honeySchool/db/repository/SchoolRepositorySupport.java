package com.ssafy.honeySchool.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.honeySchool.db.entity.QSchool;
import com.ssafy.honeySchool.db.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SchoolRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QSchool qSchool= QSchool.school1;

    public Optional<Integer> findIdBySchool(String school) {
        int id = jpaQueryFactory.select(qSchool.id).from(qSchool)
                .where(qSchool.school.eq(school)).fetchOne();
        if(id == 0) return Optional.empty();
        return Optional.ofNullable(id);
    }

    public Optional<String> findSchoolByID(int schoolNumber) {
        String school = jpaQueryFactory.select(qSchool.school).from(qSchool)
                .where(qSchool.id.eq(schoolNumber)).fetchOne();
        if(school.equals(null)) return Optional.empty();
        return Optional.ofNullable(school);
    }

}
