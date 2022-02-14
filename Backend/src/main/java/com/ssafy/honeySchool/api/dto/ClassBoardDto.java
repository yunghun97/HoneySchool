package com.ssafy.honeySchool.api.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.Timetable;
import com.ssafy.honeySchool.db.entity.User;

public class ClassBoardDto {
	
	public int id;
	public String category;
	public String title;
	public String content;
	public User user;
	public String school;
	public int grade;
	public int classes;
	public String date;
	public int viewcount;
	
	
	// 생성자	
	public ClassBoardDto(int id, String category, String title, String content, User user, String school, int grade,
			int classes, String date, int viewcount) {
		super();
		this.id = id;
		this.category = category;
		this.title = title;
		this.content = content;
		this.user = user;
		this.school = school;
		this.grade = grade;
		this.classes = classes;
		this.date = date;
		this.viewcount = viewcount;
	}


	public static ClassBoardDto from(ClassBoard classBoard) {
		Date date = classBoard.getDate();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = transFormat.format(date);
		
        return new ClassBoardDto(classBoard.getId(), classBoard.getCategory(), classBoard.getTitle(), classBoard.getContent(), 
        		classBoard.getUser(), classBoard.getSchool(), classBoard.getGrade(), classBoard.getClasses(), dateStr, classBoard.getViewcount());
    }
	
}