package com.ssafy.honeySchool.api.controller;

import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.service.LectureService;
import com.ssafy.honeySchool.db.entity.LectureUserHistory;

import antlr.collections.List;
import io.swagger.annotations.Api;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Map;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "강의 API", tags = {"Lecture"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {
    @Autowired
    LectureService lectureService;

    private String defaultHeader = "OPENVIDUAPP:ssafy";

    private Map<String, String> map;
    
    // 세션 만들기
    @PostMapping()
    public Mono<String> createLecture(@RequestBody LectureReq lectureReq){
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.createLecture(lectureReq,header);

    }
    // 모든 세션 정보 검색
    @GetMapping()
    public Mono<String> searchAllLecture() {
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.searchAllLecture(header);
    }
    // 세션 나가기
    @DeleteMapping()
    public Mono<String> deleteLecture(@RequestParam(value = "sessionId") String sessionId){
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.deleteLecture(sessionId, header);
    }
    // 세션 정보 검색
    @GetMapping("/session")
    public Mono<String> searchLecture(@RequestParam(value = "sessionId") String sessionId){
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.searchLecture(sessionId, header);
    }
    // 연결하기
    @PostMapping("/connect")
    public Mono<String> connectLecture(@RequestBody LectureReq lectureReq) throws ParseException {
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.connectLecture(lectureReq.getCustomSessionId(), header);
    }
    // 연결 끊기
    @DeleteMapping("/connect")
    public HttpStatus disconnectLecture(@RequestParam(value= "sessionId") String sessionId, @RequestParam(value="connectionId") String connectionId) {
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.disconnectLecture(sessionId,connectionId,header);
    }
    @GetMapping("/history")
    public ResponseEntity<List> selectAllHistory(@RequestParam(value="userId") String userId){
    	return new ResponseEntity(lectureService.selectAllLectureHistory(userId),HttpStatus.OK);
    }
    @PostMapping("/history/{userId}")
    public LectureUserHistory insertHistory(@RequestBody LectureUserHistory body, @PathVariable("userId") String userId) {
    	return lectureService.insertLectureHistory(body, userId);
    }
}
