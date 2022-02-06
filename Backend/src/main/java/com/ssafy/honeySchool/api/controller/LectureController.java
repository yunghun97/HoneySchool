package com.ssafy.honeySchool.api.controller;

import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.response.LectureRes;
import com.ssafy.honeySchool.api.response.UserInfoRes;
import com.ssafy.honeySchool.api.service.LectureService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
