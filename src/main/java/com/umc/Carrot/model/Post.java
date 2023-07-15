package com.umc.Carrot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor //파라미터 없는 기본 생성자 생성
public class Post {
    private long post_id;
    private String image_url;
    private String title;
    private String contents;
    private String price;
    private long reportedCount;
    private  long user_id;
    private long category_id;
    private Timestamp createdAt;
    private String status;
    private Timestamp updatedAt;

    //PostSaveReqDto를 Post로 변환
    public Post(PostSaveReqDto SaveReqDto){
        user_id = SaveReqDto.getUser_id();
        category_id=SaveReqDto.getCategory_id();
        title = SaveReqDto.getTitle();
        contents = SaveReqDto.getContents();
        price = SaveReqDto.getPrice();
        image_url = SaveReqDto.getImage_url();

    }
}
