package com.ssafy.honeySchool.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="class_board")
@NoArgsConstructor
@DynamicInsert
public class ClassBoard {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String category;
	private String title;
	private String content;
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
	
	
}
