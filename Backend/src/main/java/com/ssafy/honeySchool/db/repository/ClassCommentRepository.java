package com.ssafy.honeySchool.db.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassCommentRepository extends JpaRepository<ClassComment, Integer>{
	
	// classBoard 게시글로 댓글 리스트 찾기
	List<ClassComment> findClassCommentByClassBoard(ClassBoard classBoard);
	
	// 댓글 삭제
	void deleteById(int id);
	
	// 댓글 수정
	Optional<ClassComment> findById(int id);
}
