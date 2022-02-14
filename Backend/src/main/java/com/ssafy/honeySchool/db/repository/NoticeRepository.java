package com.ssafy.honeySchool.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.honeySchool.db.entity.Notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer>{
	
	Page<Notice> findAll(Pageable pageable);
//	List<Notice> findAll(Sort sort);
	
	Notice findById(int noticeId);
	
	// 조회수 증가
	@Modifying
	@Query("update Notice p set p.viewcount = p.viewcount + 1 where p.id = :id") 
	int updateView(@Param("id") int id);
	
}
