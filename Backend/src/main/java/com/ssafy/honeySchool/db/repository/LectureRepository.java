package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.honeySchool.db.entity.LectureUserHistory;
import com.ssafy.honeySchool.db.entity.User;

@Repository
public interface LectureRepository extends JpaRepository<LectureUserHistory, Integer> {
	@Query(value = "SELECT user_id, link, time, CASE WHEN is_join = true then '참석' else '퇴장' END as 수강기록 FROM user_lecture_history ulh where user  = :user")
	List<LectureUserHistory> findAllByUser(@Param(value = "user") User user);
	LectureUserHistory save(LectureUserHistory lectureUserHistory);
}
