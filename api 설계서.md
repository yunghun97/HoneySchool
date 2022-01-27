# API 명세서
- ### 기본 포트 : http://localhost:9999/api/v1
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
   "class": "INT",
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
   "class": "INT",
   "number": "INT",
   "email": "String",
   "year": "INT"
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
   "password": "String",
   "position": "String",
   "school":"String",
   "grade": "INT",
   "class": "INT",
   "number": "INT",
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
## 회원 ID 가져오기  
- URL
```
GET /users/userId
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
### 반 게시판 목록
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
    "content":"String",
    "writer":"String",
    "school":"String",
    "grade": "INT",
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
    "title":"String",
    "category":"String",
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
    "class":"INT",
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
    "class":"INT",
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
    "class":"INT",
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
            "content":"String",                        
            "writer":"String",
            "date":"DATETIME"            
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
    "school": "String",
    "grade":"INT",
    "class":"INT",
    "content": "String",
    "writer": "String"
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
    "writer": "String"
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
GET /board/notice/id
```
- Request
```
X
```

- Response
``` json
SUCCESS{
    "code":200, "message": "Success",
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
    "class":"INT",
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
PUT /board/notice
```
- Request
```json
{
    "id":"INT",
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
DELETE /board/notice/<int:id>
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

---
