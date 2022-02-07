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
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="class_comment")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@ToString
public class ClassComment {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int board_id;
	private String content;
	private String writer;
	private String file_link;
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "class_board_id")
	private ClassBoard classBoard;

	
	@Builder
	public ClassComment(int board_id, String content, String writer, String file_link) {
//		super();
		this.board_id = board_id;
		this.content = content;
		this.writer = writer;
		this.file_link = file_link;		
	}
	
	
}
