package com.umc.Carrot.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class PostSaveReqDto {
    private long user_id;
    private long category_id;
    private String title;
    private String contents;
    private String price;
    private String image_url;

}
