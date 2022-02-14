package com.ssafy.honeySchool.api.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.Timetable;
import com.ssafy.honeySchool.db.entity.User;

public class CommentDto {
	
	public int id;
	public String createdAt;  // yyyy-MM-dd HH:mm:ss
	public String content;
	public int parent_id;
	public User user;
	
	
	// 댓글, 대댓글 정렬에 쓰려고
	public int getId() {
		return id;
	}
	public int getParent_id() {
		return parent_id;
	}


	// 생성자	
	public CommentDto(int id, String createdAt, String content, int parent_id, User user) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.content = content;
		this.parent_id = parent_id;
		this.user = user;
	}
	

	public static CommentDto from(Comment comment) {
		Date createdAt = comment.getCreatedAt();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createdAtStr = transFormat.format(createdAt);
		
        return new CommentDto(comment.getId(), createdAtStr, comment.getContent(), comment.getParentId(), comment.getUser());
    }


}
