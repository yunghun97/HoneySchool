package com.ssafy.honeySchool.api.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ssafy.honeySchool.db.entity.Notice;
import com.ssafy.honeySchool.db.entity.User;

public class NoticeDto {

	public int id;
	public String title;
	public String content;
	public User user;
	public String date;
	public int viewcount;
	
	public NoticeDto(int id, String title, String content, User user, String date, int viewcount) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.user = user;
		this.date = date;
		this.viewcount = viewcount;
	}
	
	public static NoticeDto from(Notice notice) {
		Date date = notice.getDate();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = transFormat.format(date);
		return new NoticeDto(notice.getId(), notice.getTitle(), notice.getContent(), notice.getUser(), dateStr, notice.getViewcount());
	}
	
}
