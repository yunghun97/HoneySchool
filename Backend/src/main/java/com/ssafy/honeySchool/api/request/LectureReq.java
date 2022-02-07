package com.ssafy.honeySchool.api.request;

import com.ssafy.honeySchool.api.dto.DefaultRecordingProperties;
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
    private DefaultRecordingProperties defaultRecordingProperties;
}
