package com.ssafy.honeySchool.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.honeySchool.db.entity.Timetable;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Integer>{

//	List<Timetable> findAllByStartTimeBetween(Date start, Date end);
	// 오늘 시간표 찾기 : startTime만 따졌다
	List<Timetable> findAllBySchoolAndGradeAndClassesAndStartTimeBetweenOrderByStartTimeAsc(String school, int grade, int classes, Date start, Date end);
	
}
