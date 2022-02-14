package com.ssafy.honeySchool.db.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="user_lecture_history")
@NoArgsConstructor
@DynamicInsert
public class LectureUserHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	String link;
	Date time;
	@Column(name="is_join")
	boolean join;
	public LectureUserHistory(int id, User user, Date time, boolean join) {
		super();
		this.id = id;
		this.user = user;
		this.time = time;
		this.join = join;
	}
	
}
