package com.ssafy.honeySchool.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	int id;
	int board_id;
	String content;
	String writer;
	String date;
}
