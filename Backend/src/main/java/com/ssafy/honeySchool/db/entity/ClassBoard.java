package com.ssafy.honeySchool.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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
	// DB를 먼저 수정했다. 이제 여기 user FK로 바꿔야함
	private String writer;
	private String school;
	private int grade;
	private int classes;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String file_link;
	private int viewcount;
	


	@Builder
	public ClassBoard(String category, String title, String content, String writer, String school, int grade,
			int classes, String file_link, int viewcount) {		
		this.category = category;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.school = school;
		this.grade = grade;
		this.classes = classes;
		this.file_link = file_link;
		this.viewcount = viewcount;
	}
	
	// 전체게시판 수정
	// 수정불가할 것 같은 항목들을 지웠음
	// 1. date: 최초 작성일 같아서
	// 2. writer
	// 3. id
	// 4. school, grade, classes: 반 게시판이니까 안바꿔도 돼서
	public void update(String category, String title, String content, String file_link) {		
		this.category = category;
		this.title = title;
		this.content = content;
		this.file_link = file_link;
	}
	
}
