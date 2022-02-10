package com.ssafy.honeySchool.db.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="timetable")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Timetable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String subject;
	
	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
//	private LocalDateTime startTime;
	
	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
//	private LocalDateTime endTime;
	
	private String day;  // 요일
	private String school;
	private int grade;
	private int classes;
	
	@Builder
	public Timetable(String subject, Date startTime, Date endTime, String day, String school, int grade, int classes) {
//		super();
		this.subject = subject;
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
		this.school = school;
		this.grade = grade;
		this.classes = classes;
	}
	
}
