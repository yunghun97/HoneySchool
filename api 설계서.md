# API 명세서
- ### 기본 포트 : http://localhost:9999/api/v1
[회원](#회원)  
[게시판](#게시판)  
[화상회의](#화상회의)
## 회원  

### 회원가입
- URL
```
POST /users/register
```
- Request
```json
{  
   "name":"String", 
   "user_id": "String",
   "password": "String",
   "position": "String",
   "school":"String",
   "grade": "INT",
   "classes": "INT",
   "number": "INT",
   "email": "String",
   "birth": "Date"
}
```
- Response
```json
SUCCESS{ "code": 200, "message": "Success"}
FAIL {
    "code": 500, "message": "Server Error",
    "code": 401, "message": "인증 실패"
    "code": 404, "message": "사용자 없음"
    "code": 500, "message": "서버 오류"
}
```
  
### 로그인
- URL
```
POST /auth/login
```
- Request
```json
{
    "user_id":"String",
    "password":"String"
}  
```
- Response
```json
SUCCESS{ 
    "accessToken" : "String",
    "code": 200, 
    "message": "Success"
}
FAIL {
    "code": 500, "message": "Server Error",
    "code": 401, "message": "유효하지 않은 password"
}
```  

### 내 프로필
-  URL
```
GET /users/<string:user_id>
```
- Request
X
- Response
``` json
SUCCESS{
   "name":"String",    
   "password": "String",
   "position": "String",
   "school":"String",
   "grade": "INT",
   "classes": "INT",
   "number": "INT",
   "email": "String",
   "birth": "DATE"
}
FAIL {
    "code": 500, "message": "Server Error",
    "code": 401, "message": "본인의 ID가 아닙니다.",
}
```
### 회원 정보 수정
- URL
```
PUT /users/<string:user_id>
```
- Request
```json
{
   "name":"String",    
   "position": "String",
   "school":"String",
   "grade": "INT",
   "classes": "INT",
   "number": "INT",   
   "password": "String",
   "email": "String",
   "birth": "DATE"
}
```
- Response
```json
SUCCESS{ "code": 200, "message": "Success"}
FAIL {
    "code": 500, "message": "Server Error",
    "code": 401, "message": "본인의 ID가 아닙니다."
}
```
### 회원 정보 삭제
- URL
```
DELETE /users/<string:user_id>
```
- Request
```json
{
    "user_id":"String"
}
```
- Response
```json
SUCCESS{ "code": 200, "message": "Success"}
FAIL {
    "code": 500, "message": "Server Error",
    "code": 401, "message": "본인의 ID가 아닙니다."
}
```  
### 회원 ID 가져오기  
- URL
```
GET /users/userInfo
```
- Request
```Header
"Authentication" : "accessToken"
```
- Response
```json
SUCCESS{ "userId" : "String"}
FAIL {
    "code": 403, "error" : "Forbidden", "message": "Access Denied."
}
``` 
## 게시판
---
### 반
### 반 게시판 전체 목록
- URL
```
GET /board/class/<string:school>&<int:grade>&<int:class>
```
- Request
```
X
```
- Response
``` json
SUCCESS{
    "code": 200, "message": "Success",
    "data":[
        {
            "id":"INT",
            "category":"String",
            "title":"String",
            "writer":"String",
            "date":"DATETIME",
            "viewCount":"INT"
        }
    ]
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "해당하는 학교 또는 반이 없습니다."
}
```
### 반 게시판 카테고리글
- URL
```
GET /board/class/<string:school><string:category>&<int:grade>&<int:class>
```
- Request
```
```

- Response
``` json
SUCCESS{
    "code":200, "message": "Success",
    "id": "INT",
    "category":"String",
    "title":"String",
    "writer":"String",
    "date":"DATETIME",
    "viewCount":"INT"
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "해당하는 글이 없습니다."
}
```
### 반 게시판 글 상세
- URL
```
GET /board/class/detail/<string:school>&<int:grade>&<int:class>&<int:id>
```
- Request
```
```

- Response
``` json
SUCCESS{
    "code":200, "message": "Success",
    "id":"INT",
    "category":"String",
    "title":"String",
    "content":"String",
    "writer":"String",
    "school":"String",
    "grade":"INT",
    "classes":"INT",
    "date":"DATETIME",
    "file_link":"String",
    "viewCount":"INT"
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "해당하는 글이 없습니다."
}
```
### 반 게시판 글 작성
- URL
```
POST /board/class
```
- Request
```json
{
    "category":"String",
    "school":"String",
    "title":"String",
    "date":"DATE",
    "grade":"INT",
    "classes":"INT",
    "writer":"String",
    "content":"String",
    "file_link":"String",
}
```
- Response
``` json
SUCCESS{
    "code":200,"message":"Success",
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "글 작성 오류"
}
```
### 반 게시판 글 수정
- URL
```
PUT /board/class
```
- Request
```json
{
    "id":"INT",
    "category":"String",
    "school":"String",
    "title":"String",
    "date":"DATE",
    "grade":"INT",
    "classes":"INT",
    "writer":"String",
    "content":"String",
    "file_link":"String",
}
```
- Response
``` json
SUCCESS{
    "code":200,"message":"Success",
    "school":"String",
    "grade":"INT",
    "classes":"INT",
    "id":"INT"    
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "글 수정 오류"
}
```
### 반 게시판 글 삭제
- URL
```
DELETE /board/class/<string:school>&<int:grade>&<int:class>&<int:id>
```
- Request
```json
X
```
- Response
``` json
SUCCESS{
    "code":200,"message":"Success",
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "글 삭제 오류"
}
```
## 반 댓글
### 반 게시판 댓글 가져오기
- URL
```
GET /board/class/comment/<string:school>&<int:grade>&<int:class>
```
- Request
```
X
```
- Response
``` json
SUCCESS{
    "code": 200, "message": "Success",
    "data":[
        {
            "id":"INT",
            "board_id":"INT",
            "content": "String",
            "writer": "String",
            "file_link": "String"         
        }
    ]
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "해당하는 댓글이 없습니다."
}
```
### 반 게시판 댓글 작성
- URL
```
POST /board/class/comment
```
- Request
```json
{
    "board_id":"INT",
    "content": "String",
    "writer": "String",
    "file_link": "String"
}
```
- Response
``` json
SUCCESS{
    "code": 200, "message": "Success",
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "댓글 작성 실패."
}
```
### 반 게시판 댓글 수정
- URL
```
PUT /board/class/comment
```
- Request
```json
{
    "id":"INT",
    "board_id":"INT",    
    "content": "String",
    "writer": "String",
    "file_link": "String",
}
```
- Response
``` json
SUCCESS{
    "code": 200, "message": "Success",
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "댓글 수정 실패"
}
```
### 반 게시판 댓글 삭제
- URL
```
DELETE /board/class/comment/<INT:id>
```
- Request
```
X
```
- Response
``` json
SUCCESS{
    "code": 200, "message": "Success",
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "댓글 삭제 실패"
}
```  

---

### 공지
### 공지사항 게시판 목록
- URL
```
GET /board/notice
```
- Request
```
X
```
- Response
``` json
SUCCESS{
    "code": 200, "message": "Success",
    "data":[
        {
            "id":"INT",
            "category":"String",
            "title":"String",
            "writer":"String",
            "date":"DATETIME",
            "file_link":"String",
            "viewCount":"INT"
        }
    ]
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "링크 에러."
}
```

### 공지사항 게시판 글 상세
- URL
```
GET /board/notice/id?<int:id>
```
- Request
```
X
```

- Response
``` json
SUCCESS{
    "code":200, "message": "Success",
    "id":"INT",
    "category":"String",
    "title":"String",
    "content":"String",
    "writer":"String",
    "date":"DATETIME",
    "file_link":"String",
    "viewCount":"INT"
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "해당하는 글이 없습니다."
}
```
### 공지사항 게시판 글 작성
- URL
```
POST /board/notice
```
- Request
```json
{
    "category":"String",    
    "title":"String",
    "date":"DATE",
    "grade":"INT",
    "classes":"INT",
    "writer":"String",
    "content":"String",
    "file_link":"String",
}
```
- Response
``` json
SUCCESS{
    "code":200,"message":"Success",
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "글 작성 오류"
}
```
### 공지사항 게시판 글 수정
- URL
```
PUT /board/notice?<int:id>
```
- Request
```json
{
    "category":"String",
    "title":"String",
    "date":"DATE",
    "writer":"String",
    "content":"String",
    "file_link":"String",    
}
```
- Response
``` json
SUCCESS{
    "code":200,"message":"Success",
    "id":"INT"    
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "글 수정 오류"
}
```
### 공지사항 게시판 글 삭제
- URL
```
DELETE /board/notice?<int:id>
```
- Request  
X
- Response
``` json
SUCCESS{
    "code":200,"message":"Success",
}
FAIL{
    "code": 500, "message": "Server Error",
    "code": 401, "message": "글 삭제 오류"
}
```  

## 화상회의  
## 공통 HEADERS
{
    Authorization: Basic ```EncodeBase64(OPENVIDUAPP:ssafy)```
}

## Room
- Response
### 세션 생성
- URL
```
POST /lecture
```
- Request  
```json
{
    "customSessionId":  "mySessionId"
}
```
- Response  
```Session Object```
```json
{
    "id": "ses_YnDaGYNcd7",
    "object": "session",
    "createdAt": 1538481996019,
    "mediaMode": "ROUTED",
    "recordingMode": "MANUAL",
    "defaultRecordingProperties": {
        "name": "MyRecording",
        "hasAudio": true,
        "hasVideo": true,
        "outputMode": "COMPOSED",
        "recordingLayout": "BEST_FIT",
        "resolution": "1280x720",
        "frameRate": 25,
        "shmSize": 536870912,
        "mediaNode": "media_i-po39jr3e10rkjsdfj"
    },
    "customSessionId": "TestSession",
    "connections": {
        "numberOfElements": 0,
        "content": []
    },
    "recording": false,
    "forcedVideoCodec": "VP8",
    "allowTranscoding": false,
    "mediaNodeId": "media_i-po39jr3e10rkjsdfj"
}
```
### 세션 삭제
- URL
```
DELETE /lecture?string:sessionId>
```
- Request  
X
- Response  
```HTTP responses```
```
204
404
```
### 현재 생성된 모든 세션 정보 보기
- URL
```
GET /lecture
```
- Request
X
- Response
```json
{
    "numberOfElements": 0, // Total number of Sessions
    "content": []       // 	Array of Session objects
}

```

### 세션 입장
- URL
```
POST /lecture/connect
```
- Request
```json
{
    "customSessionId": "mysessionId" 
}
```
- Response  
```Connetion Object```
```json
{
    "id": "con_Xnxg19tonh",
    "object": "connection",
    "type": "WEBRTC",
    "status": "active",
    "sessionId": "ses_YnDaGYNcd7",
    "createdAt": 1538481999022,
    "activeAt": 1538481999843,
    "location": "Madrid, Spain",
    "ip": "37.122.145.190",
    "platform": "Chrome 85.0.4183.102 on Linux 64-bit",
    "token": "wss://localhost:4443?sessionId=TestSession&token=tok_AVe8o7iltWqtijyl&role=PUBLISHER&version=2.16.0&coturnIp=localhost&turnUsername=M2ALIY&turnCredential=7kfjy2",
    "serverData": "My Server Data",
    "clientData": "My Client Data",
    "record": true,
    "role": "PUBLISHER",
    "kurentoOptions": {
        "videoMaxRecvBandwidth": 1000,
        "videoMinRecvBandwidth": 300,
        "videoMaxSendBandwidth": 1000,
        "videoMinSendBandwidth": 300,
        "allowedFilters": [
            "GStreamerFilter",
            "ZBarFilter"
        ]
    },
    "rtspUri": null,
    "adaptativeBitrate": null,
    "onlyPlayWithSubscribers": null,
    "networkCache": null,
    "publishers": [
        {
            "createdAt": 1538481999710,
            "streamId": "str_CAM_NhxL_con_Xnxg19tonh",
            "mediaOptions": {
                "hasAudio": true,
                "audioActive": true,
                "hasVideo": true,
                "videoActive": true,
                "typeOfVideo": "CAMERA",
                "frameRate": 30,
                "videoDimensions": "{\"width\":640,\"height\":480}",
                "filter": {}
            }
        }
    ],
    "subscribers": [
        {
            "streamId": "str_MIC_JSXs_con_OV0CsFsykJ",
            "createdAt": 1538482000856
        }
    ]
}

```  
  
### 세션 퇴장
- URL
```
DELETE /lecture?<string:sessionId>
```
- Request  
X
- Response  
```HTTP responses```  
```
204
400
404
```
  
### 방 입장 퇴장 기록 가져오기
- URL
```
GET /leture/history?<string:userId>
```
- Request  
X
- Response
```json
{
    "id": 1,    // HistoryId
    "join": "true",
    "link": "sessionId",
    "time": "2022-02-15",
    "user":{
        "id": 2,  // user 테이블의 Id
        "name": "권영현",
        "position": "S",
        "grade": 1,
        "classes": 2,
        "number": 5,
        "userId": "ssafy",
        "email": "ssafy@naver.com",
        "password": "JWT로 암호화된비번",
        "birth": 2000
    }
}
```

### 방 입장 퇴장 기록 남기기
- URL
```
POST /lecture/history?<string:userId>
```
- Request  
```json
{
    "link": "mysessionId",
    "join": "true"    // true면 입장 false 면 퇴장
}
```
- Response  
```HTTP responses```  
```
200
400
```

