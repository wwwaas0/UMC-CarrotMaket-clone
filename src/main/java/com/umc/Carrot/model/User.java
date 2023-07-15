package com.umc.Carrot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor //파라미터 없는 기본 생성자 생성
public class User {
    private long user_id;
    private String email;
    private String password;
    private String phoneNum;
    private String profileImage_url;
    private double mannerTemperature;
    private String nickname;
    private Timestamp createdAt;
    private String status;
    private Timestamp updatedAt;
}
