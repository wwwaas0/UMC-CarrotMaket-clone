package com.umc.Carrot;

import com.umc.Carrot.exception.DuplicateNicknameException;
import com.umc.Carrot.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts") // 해당 컨트롤러가 user경로에 대한 req를 처리
@RequiredArgsConstructor //final필드, @NonNull 어노테이션이 붙은 필드에 대한 생성자 인자 자동 생성
public class PostController {
    private final PostService postService;
    private final UserService userService;

    //게시물 생성
    @PostMapping
    public String save(@RequestBody PostSaveReqDto saveReqDto) throws DuplicateNicknameException {
        postService.save(saveReqDto);
        String userNickname = userService.findById(saveReqDto.getUser_id()).getNickname();
        return userNickname+"님의 게시물 ["+saveReqDto.getTitle()+"]가 성공적으로 게시되었습니다!";
    }

    //특정 단어가 들어간 게시물 조회
    @GetMapping("/{searchWord}")
    //@PathVariable: userId값이 HTTP 요청 URL의 경로에서 추출되어 사용된다는 것을 나타냄
    public Post get(@PathVariable String searchWord) {
        return postService.findByWord(searchWord);
    }

    //Query Parameter 이용
    //@GetMapping
    //public Post get(@RequestParam("searchWord") String searchWord) {
    //    return postService.findByWord(searchWord);
    //}


    //게시물 제목 수정
    @PatchMapping("/{postId}/title")
    public String updateTitle(@PathVariable Long postId, @RequestBody PostTitleReqDto titleReqDto)
    {
        postService.updateTitle(postId, titleReqDto);
        return "게시목의 제목이 ["+titleReqDto.getTitle()+"]으로 바뀌었습니다.";
    }
    //게시물 내용, 가격, 이미지 수정
    @PutMapping("/{postId}/contents")
    public String update(@PathVariable Long postId,
                         @RequestBody PostUpdateReqDto updateReqDto) {
        postService.update(postId, updateReqDto);
        return "수정된 게시물의 내용은 ["+updateReqDto.getContents()+"], 가격은 ["+updateReqDto.getPrice()+"], 이미지는 ["+updateReqDto.getImage_url()+"]입니다.";
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    public String delete(@PathVariable Long postId) {
        postService.delete(postId);
        return "post_id "+postId+"번 게시물이 삭제되었습니다.";
    }

}