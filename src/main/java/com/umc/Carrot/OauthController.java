package com.umc.Carrot;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {
    private final OauthService oauthService;

    //[GET] /oauth
    @ResponseBody
    @GetMapping
    public void kakaoCallback(@RequestParam String code) throws IOException {
        System.out.println("kakao code: "+code);
        oauthService.getKakaoAccessToken(code);
    }

}
