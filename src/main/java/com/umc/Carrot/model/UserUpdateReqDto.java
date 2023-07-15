package com.umc.Carrot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateReqDto {//api생성할 때 입력해야하는 값
    private String email;
    private String password;
    private String phoneNum;
    private String profileImage_url;
    private String nickname;
}
