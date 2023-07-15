package com.umc.Carrot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveReqDto {
    private String email;
    private String password;
    private String phoneNum;
    private String profileImage_url;
    private String nickname;
}
