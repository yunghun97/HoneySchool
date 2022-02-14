package com.ssafy.honeySchool.db.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="notice")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Notice {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String content;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private int viewcount;
	


	@Builder
	public Notice(String title, String content, User user, int viewcount) {		
		this.title = title;
		this.content = content;
		this.user = user;
		this.viewcount = viewcount;
	}
	
	// 수정
	public void update(String title, String content) {		
		this.title = title;
		this.content = content;
	}
}