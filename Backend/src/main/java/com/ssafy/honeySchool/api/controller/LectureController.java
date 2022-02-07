package com.ssafy.honeySchool.api.controller;

import com.ssafy.honeySchool.api.request.ConnectReq;
import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.response.ConnectRes;
import com.ssafy.honeySchool.api.response.LectureRes;
import com.ssafy.honeySchool.api.response.UserInfoRes;
import com.ssafy.honeySchool.api.service.LectureService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "강의 API", tags = {"Lecture"})
@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {
    @Autowired
    LectureService lectureService;

    @PostMapping("/")
    public ResponseEntity<LectureRes> createLecture(@RequestBody LectureReq lectureReq, @RequestHeader(value = "Authorization") String header){
        LectureRes lr=lectureService.createLecture(lectureReq,header);
        return ResponseEntity.status(200).body(lr);
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getLectureList(@RequestHeader(value = "Authorization") String header){
        return null;
    }

    @GetMapping("/{session_id}")
    public ResponseEntity<List<String>> getLectureList(@PathVariable String session_id, @RequestHeader(value = "Authorization") String header){
        return null;
    }

    @DeleteMapping("/{session_id}")
    public ResponseEntity<String> deleteLecture(@PathVariable String session_id, @RequestBody LectureReq lectureReq,@RequestHeader(value = "Authorization") String header){
        return null;
    }

    @PostMapping("/{session_id}/conn")
    public ResponseEntity<ConnectRes> createConnection(@PathVariable String session_id,@RequestBody ConnectReq connectReq, @RequestHeader(value = "Authorization") String header){
        return null;
    }

    @GetMapping("/{session_id}/conn/{connection_id}")
    public ResponseEntity<ConnectRes> getConnection(){
        return null;
    }

    @GetMapping("/{session_id}/conn/")
    public ResponseEntity<ConnectRes> getConnectionList(){
        return null;
    }

    @DeleteMapping("/{session_id}/conn/{connection_id}")
    public ResponseEntity<ConnectRes> deleteConnection(){
        return null;
    }
}
