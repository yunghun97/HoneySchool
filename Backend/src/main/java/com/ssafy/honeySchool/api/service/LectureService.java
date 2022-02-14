package com.ssafy.honeySchool.api.service;

import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.response.LectureRes;
import com.ssafy.honeySchool.db.entity.Lecture;
import com.ssafy.honeySchool.db.entity.LectureUserHistory;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public interface LectureService {
    Mono<String> createLecture(LectureReq lectureReq, String header);
    Mono<String> searchAllLecture(String header);
    Mono<String> deleteLecture(String sessionId, String header);
    Mono<String> connectLecture(String sessionId, String header) throws ParseException;
    HttpStatus disconnectLecture(String sessionId, String connectionId, String header);
	Mono<String> searchLecture(String sessionId, String header);
	List<LectureUserHistory> selectAllLectureHistory(String userId);		
	LectureUserHistory insertLectureHistory(LectureUserHistory body, String userId);
}
