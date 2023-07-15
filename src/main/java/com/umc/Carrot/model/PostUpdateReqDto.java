package com.umc.Carrot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateReqDto {
    private String contents;
    private String price;
    private String image_url;
}
