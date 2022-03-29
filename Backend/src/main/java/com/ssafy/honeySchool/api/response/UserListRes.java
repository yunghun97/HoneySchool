package com.ssafy.honeySchool.api.response;

import com.ssafy.honeySchool.api.dto.UserNameDto;
import com.ssafy.honeySchool.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserListRes {
    private List<UserNameDto> userList;

    public static UserListRes of(List<UserNameDto> userList){
        UserListRes userListRes=UserListRes.builder()
                .userList(userList).build();
        return userListRes;
    }
}
