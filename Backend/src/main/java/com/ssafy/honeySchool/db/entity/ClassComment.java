package com.ssafy.honeySchool.db.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="class_comment")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ClassComment {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	private String writer;
	private String file_link;
	private Date date;
	
	// 다대일 관계 만들기
	// ???
	// private int board_id;
	@ManyToOne
	@JoinColumn(name = "board_id")
	private ClassBoard classBoard;

	
	@Builder
	public ClassComment(String content, String writer, String file_link, ClassBoard classBoard) {
//		super();
		this.content = content;
		this.writer = writer;
		this.file_link = file_link;
		this.classBoard = classBoard;
	}
	
	// 댓글 수정
	public void update(String content, String writer, String file_link) {		
		this.content = content;
		this.writer = writer;
		this.file_link = file_link;
	}
}
