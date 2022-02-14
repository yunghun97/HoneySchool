package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.honeySchool.db.entity.Notice;

import org.springframework.data.domain.Sort;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer>{
	
	List<Notice> findAll(Sort sort);
	
	Notice findById(int noticeId);
	
	// 조회수 증가
	@Modifying
	@Query("update Notice p set p.viewcount = p.viewcount + 1 where p.id = :id") 
	int updateView(@Param("id") int id);
	
}
