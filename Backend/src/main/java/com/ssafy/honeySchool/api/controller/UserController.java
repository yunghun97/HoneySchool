package com.ssafy.honeySchool.api.controller;

import com.ssafy.honeySchool.api.request.UserModifyPutReq;
import com.ssafy.honeySchool.api.request.UserRegisterPostReq;
import com.ssafy.honeySchool.api.response.UserRes;
import com.ssafy.honeySchool.api.response.UserInfoRes;
import com.ssafy.honeySchool.api.service.UserService;
import com.ssafy.honeySchool.common.auth.HoneySchoolUserDetails;
import com.ssafy.honeySchool.common.model.response.BaseResponseBody;
import com.ssafy.honeySchool.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "회원 가입", notes = "입력된 정보를 바탕으로 회원가입한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

        // TODO: 중복 체크

        User user = userService.createUser(registerInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/userInfo")
    @ApiOperation(value = "회원 Id 조회", notes = "회원 ID 응답.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        HoneySchoolUserDetails userDetails = (HoneySchoolUserDetails) authentication.getDetails();
        String userId = userDetails.getUsername();
        User user = userService.getUserByUserId(userId);

        return ResponseEntity.status(200).body(UserRes.of(user));
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "회원 정보", notes = "회원의 정보를 가져온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserInfoRes> getUserInfo(@PathVariable String id) {

        User user = userService.getUserByUserId(id);

        return ResponseEntity.status(200).body(UserInfoRes.of(user));
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "회원 삭제", notes = "Id를 받아와서 회원 삭제를 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteUser(@PathVariable String id) {

        userService.deleteByUserId(id);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "회원 수정", notes = "Id를 받아와서 회원 수정을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> modifyUser(@PathVariable String id,@RequestBody UserModifyPutReq userModifyPutReq) {


        User user=userService.updateUser(id, userModifyPutReq);
        if(user!=null)
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        else
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
    }
}
