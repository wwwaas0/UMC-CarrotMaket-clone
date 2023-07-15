package com.umc.Carrot;

import com.umc.Carrot.exception.DuplicateNicknameException;
import com.umc.Carrot.model.User;
import com.umc.Carrot.model.UserSaveReqDto;
import com.umc.Carrot.model.UserUpdateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    //서비스 -> 리포지토리
    private final UserRepository userRepository;

    //회원 가입할 때 회원 정보 저장
    public void save(UserSaveReqDto saveReqDto) throws DuplicateNicknameException {
        //UserSaveReqDto에서 User로 변환
        if(userRepository.isNicknameDuplicate (saveReqDto.getNickname())){
            throw new DuplicateNicknameException(saveReqDto.getNickname()+"은 중복된 닉네임입니다.");
        }
        User user = new User();
        user.setEmail(saveReqDto.getEmail());
        user.setPassword(saveReqDto.getPassword());
        user.setPhoneNum(saveReqDto.getPhoneNum());
        user.setProfileImage_url(saveReqDto.getProfileImage_url());
        user.setNickname(saveReqDto.getNickname());
        userRepository.save(user);
    }

    //입력받은 id로 회원 정보 찾기
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    //모든 회원의 정보 찾기
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //입력된 정보에 대해 회원 정보 수정
    public void update(Long userId, UserUpdateReqDto updateReqDto) {
        userRepository.update(userId, updateReqDto);
    }

    //입력된 id의 회원 정보 삭제
    public void delete(Long userId) {
        userRepository.delete(userId);
    }

}
