package com.ssafy.honeySchool.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="class_board")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ClassBoard {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String category;
	private String title;
	private String content;
	// writer에서 user FK로 바꿨다
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String school;
	private int grade;
	private int classes;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	// file_link는 지웠다
	private int viewcount;
	


	@Builder
	public ClassBoard(String category, String title, String content, User user, String school, int grade,
			int classes, int viewcount) {		
		this.category = category;
		this.title = title;
		this.content = content;
		this.user = user;
		this.school = school;
		this.grade = grade;
		this.classes = classes;
		this.viewcount = viewcount;
	}
	
	// 전체게시판 수정
	// 수정불가할 것 같은 항목들을 지웠음
	// 1. date: 최초 작성일 같아서
	// 2. writer
	// 3. id
	// 4. school, grade, classes: 반 게시판이니까 안바꿔도 돼서
	public void update(String category, String title, String content) {		
		this.category = category;
		this.title = title;
		this.content = content;
	}
	
}
