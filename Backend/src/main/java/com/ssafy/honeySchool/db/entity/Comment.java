package com.ssafy.honeySchool.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="comment")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Comment {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	private String content;
	
//	@Enumerated(value = EnumType.STRING)
//    @Column(name = "is_deleted")
//    private DeleteYn isDeleted;
	
	@ManyToOne
	@JoinColumn(name = "board_id")
	private ClassBoard classBoard;
	
	@Column(name = "parent_id")
	private int parentId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	
	
	@Builder
	public Comment(String content, ClassBoard classBoard, int parentId, User user) {
		super();
		this.content = content;
		this.classBoard = classBoard;
		this.parentId = parentId;
		this.user = user;
	}
	@Builder
	public Comment(String content, ClassBoard classBoard, User user) {
		super();
		this.content = content;
		this.classBoard = classBoard;
		this.user = user;
	}

	
	// 댓글 수정
	public void update(String content) {
//		super();
		this.content = content;
	}
	
	

	
//	@Builder
//	public ClassComment(String content, String writer, String file_link, ClassBoard classBoard) {
////		super();
//		this.content = content;
//		this.writer = writer;
//		this.file_link = file_link;
//		this.classBoard = classBoard;
//	}
//	
//	// 댓글 수정
//	public void update(String content, String writer, String file_link) {		
//		this.content = content;
//		this.writer = writer;
//		this.file_link = file_link;
//	}
}