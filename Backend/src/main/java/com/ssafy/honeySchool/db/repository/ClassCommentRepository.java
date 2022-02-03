package com.ssafy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.ClassBoard;
import com.ssafy.db.entity.ClassComment;

@Repository
public interface ClassCommentRepository extends JpaRepository<ClassComment, Integer>{
	
	// classBoard 게시글로 댓글 리스트 찾기
	List<ClassComment> findClassCommentByClassBoard(ClassBoard classBoard);
}
