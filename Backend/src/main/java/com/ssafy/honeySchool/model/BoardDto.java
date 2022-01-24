package com.ssafy.honeySchool.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
	int id;
	String category;
	String title;
	String content;
	String writer;
	String school;
	String grade;
	String classes;
	String date;
	String file_link;
	int viewcount;
	
	
}
