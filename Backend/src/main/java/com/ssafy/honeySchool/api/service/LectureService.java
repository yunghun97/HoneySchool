package com.ssafy.honeySchool.api.service;

import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.response.LectureRes;
import com.ssafy.honeySchool.db.entity.Lecture;

public interface LectureService {
    LectureRes createLecture(LectureReq lectureReq, String header);

}
