package com.umc.Carrot;

import com.umc.Carrot.exception.DuplicateNicknameException;
import com.umc.Carrot.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    //서비스 -> 리포지토리
    private final PostRepository postRepository;

    //회원 가입할 때 회원 정보 저장
    public void save(PostSaveReqDto saveReqDto) throws DuplicateNicknameException {
        Post post = new Post(saveReqDto);
        postRepository.save(post);
    }

    //입력받은 id로 회원 정보 찾기
    public Post findByWord(String word) {
        return postRepository.findByWord(word).get();
    }


    //입력된 정보에 대해 회원 정보 수정
    public void updateTitle(Long postId, PostTitleReqDto updateReqDto) {
        postRepository.updateTitle(postId, updateReqDto);
    }

    //입력된 정보에 대해 회원 정보 수정
    public void update(Long postId, PostUpdateReqDto updateReqDto) {
        postRepository.update(postId, updateReqDto);
    }

    //입력된 id의 회원 정보 삭제
    public void delete(Long userId) {
        postRepository.delete(userId);
    }
}
