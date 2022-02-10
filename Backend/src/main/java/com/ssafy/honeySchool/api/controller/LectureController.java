package com.ssafy.honeySchool.api.controller;

import com.ssafy.honeySchool.api.request.ConnectReq;
import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.response.ConnectRes;
import com.ssafy.honeySchool.api.response.LectureRes;
import com.ssafy.honeySchool.api.service.LectureService;
import io.swagger.annotations.Api;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;
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

    @PostMapping()
    public Mono<String> createLecture(@RequestBody LectureReq lectureReq){
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.createLecture(lectureReq,header);

    }
    @GetMapping()
    public Mono<String> searchAllLecture() {
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.searchAllLecture(header);
    }
    @DeleteMapping()
    public Mono<String> deleteLecture(@RequestParam(value = "sessionId") String sessionId){
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.deleteLecture(sessionId, header);
    }

    @PostMapping("/connect")
    public Mono<String> connectLecture(@RequestBody LectureReq lectureReq) throws ParseException {
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.connectLecture(lectureReq.getCustomSessionId(), header);
    }
    @DeleteMapping("/connect")
    public HttpStatus disconnectLecture(@RequestParam(value= "sessionId") String sessionId, @RequestParam(value="connectionId") String connectionId) {
        String header = "Basic "+ Base64.getEncoder().encodeToString(this.defaultHeader.getBytes());
        return lectureService.disconnectLecture(sessionId,connectionId,header);
    }
}
