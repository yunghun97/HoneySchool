package com.ssafy.honeySchool.api.response;

import com.ssafy.honeySchool.api.dto.DefaultRecordingProperties;
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
    private DefaultRecordingProperties defaultRecordingProperties;
    private String customSessionId;

}
