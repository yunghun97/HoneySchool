package com.ssafy.honeySchool.api.response;

import com.ssafy.honeySchool.api.request.DefaultRecordingPropertiesReq;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureRes {
    private String id;
    private String object;
    private long createdAt;
    private String mediaMode;
    private String recordingMode;
    private DefaultRecordingPropertiesReq defaultRecordingPropertiesReq;
    private String customSessionId;
    private Connections connections;

}
