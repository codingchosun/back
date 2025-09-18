package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class UserPairDto {
    private User fromUser;
    private User toUser;

    public UserPairDto(User fromUser,User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public static List<UserPairDto> makeUserPairList(List<User> userList) {
        List<UserPairDto> rtnUserPairList = new ArrayList<>();
        for (User fromUser : userList) {
            for (User toUser : userList) {
                if( !(fromUser.getUserId().equals(toUser.getUserId())) ) {
                    rtnUserPairList.add(new UserPairDto(fromUser,toUser));
                }
            }
        }

        log.info("userPairList={}", rtnUserPairList);

        return rtnUserPairList;
    }

    @Override
    public String toString() {
        return "UserPairDto{" +
                "fromUser=" + fromUser.getUserId() +
                ", toUser=" + toUser.getUserId() +
                '}';
    }
}
