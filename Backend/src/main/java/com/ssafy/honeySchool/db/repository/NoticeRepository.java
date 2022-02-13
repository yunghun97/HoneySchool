package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.honeySchool.db.entity.Notice;

import org.springframework.data.domain.Sort;

public interface NoticeRepository extends JpaRepository<Notice, Integer>{
	
	List<Notice> findAll(Sort sort);
	
	Notice findById(int noticeId);
	
}
