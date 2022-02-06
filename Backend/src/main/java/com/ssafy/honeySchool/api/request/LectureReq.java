package com.ssafy.honeySchool.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureReq {
    private String mediaMode;
    private String recordingMode;
    private String customSessionId;
    private String forceVideoCodec;
    private boolean allowTranscoding;
    private DefaultRecordingPropertiesReq defaultRecordingPropertiesReq;
}
