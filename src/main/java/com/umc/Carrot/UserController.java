package com.umc.Carrot;

import com.umc.Carrot.exception.DuplicateNicknameException;
import com.umc.Carrot.model.User;
import com.umc.Carrot.model.UserSaveReqDto;
import com.umc.Carrot.model.UserUpdateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user") // 해당 컨트롤러가 /user경로에 대한 req를 처리
@RequiredArgsConstructor //final필드, @NonNull 어노테이션이 붙은 필드에 대한 생성자 인자 자동 생성
//UserController 객체 생성할 때 UserService을 생성자 인자로
public class UserController {
    //컨트롤러 -> 서비스
    private final UserService userService;

    //회원가입
    @PostMapping
    public String save(@RequestBody UserSaveReqDto saveReqDto) throws DuplicateNicknameException {
        userService.save(saveReqDto);
        return saveReqDto.getNickname()+"님의 회원가입이 완료되었습니다!";
    }

    //특정 회원 조회
    @GetMapping("/{userId}")
    //@PathVariable: userId값이 HTTP 요청 URL의 경로에서 추출되어 사용된다는 것을 나타냄
    public User get(@PathVariable Long userId) {
        return userService.findById(userId);
    }

//    모든 회원 조회
    @GetMapping
    public List<User> getList() {
        return userService.findAll();
    }

    //회원 정보 수정
    @PutMapping("/{userId}")
    public User update(@PathVariable Long userId,
                       @RequestBody UserUpdateReqDto updateReqDto) {
        userService.update(userId, updateReqDto); //회원 정보 수정
        return userService.findById(userId); //수정된 회원 정보 반환
    }

    //회원 삭제
    @DeleteMapping("/{userId}")
    public String delete(@PathVariable Long userId) {
        String userNickname = userService.findById(userId).getNickname();
        userService.delete(userId);
        return userNickname+"님의 계정 삭제가 완료되었습니다.";
    }

    //회원가입시, 같은 닉네임을 가진 회원이 있을 경우
    //DuplicateNicknameException 예외 처리
    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<String> handleDuplicateNicknameException(DuplicateNicknameException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
