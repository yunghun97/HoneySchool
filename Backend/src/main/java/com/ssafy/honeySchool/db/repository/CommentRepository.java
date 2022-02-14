package com.ssafy.honeySchool.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	// 댓글을 삭제할 때 parent_id로 대댓글 지우기
	void deleteAllByParentId(int parentId);
	void deleteById(int id);
	
	// 대댓글 밑에 댓글 못쓰게
	Optional<Comment> findById(int id);
	
	// 댓글 불러오기
	Optional<List<Comment>> findCommentByClassBoardOrderByParentIdAsc(ClassBoard classBoard);
	
	// 숙제 게시판 - 내가 쓴 댓글만 보인다.
	Optional<List<Comment>> findCommentByClassBoardAndUserAndParentId(ClassBoard classBoard, User user, int parentId);
	
	// 숙제 게시판 - 내가 쓴 댓글에서 대댓글보기 클릭하면 대댓글이 나온다.
	Optional<List<Comment>> findCommentByParentId(int parentId);
	
	
	
//	// classBoard 게시글로 댓글 리스트 찾기
//	List<ClassComment> findClassCommentByClassBoard(ClassBoard classBoard);
//	
//	// 댓글 삭제
//	void deleteById(int id);
//	
//	// 댓글 수정
//	Optional<ClassComment> findById(int id);
}
