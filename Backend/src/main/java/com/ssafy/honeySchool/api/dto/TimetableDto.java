package com.ssafy.honeySchool.api.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ssafy.honeySchool.db.entity.Timetable;

public class TimetableDto {
	
	public int id;
	public String subject;
	public String startTime;  // 시:분 - 00:00
	public String endTime;  // 시:분 - 00:00
	public String day;  // 요일

	
	// 생성자
	public TimetableDto(int id, String subject, String startTime, String endTime, String day) {
		super();
		this.id = id;
		this.subject = subject;
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
	}



	public static TimetableDto from(Timetable timetable) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date startDate = timetable.getStartTime();  
		String startStr = dateFormat.format(startDate);
		Date endDate = timetable.getEndTime();  
		String endStr = dateFormat.format(endDate);
		
        return new TimetableDto(timetable.getId(), timetable.getSubject(), startStr, endStr, timetable.getDay());
    }
}
