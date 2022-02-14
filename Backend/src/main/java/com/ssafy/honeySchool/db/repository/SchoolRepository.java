package com.ssafy.honeySchool.db.repository;

import com.ssafy.honeySchool.db.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Integer> {
    Optional<School> findBySchool(String school);
}
