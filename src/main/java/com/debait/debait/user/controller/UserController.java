
package com.debait.debait.user.controller;

import com.debait.debait.user.dto.request.UserLoginRequestDTO;
import com.debait.debait.user.dto.request.UserRegisterRequestDTO;
import com.debait.debait.user.dto.request.UserUpdateRequestDTO;
import com.debait.debait.user.dto.response.UserRegisterResponseDTO;
import com.debait.debait.user.dto.response.UserUpdateResponseDTO;
import com.debait.debait.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user") // Controller에 들어온 요청을 처리하는 기준점 (/user 경로로 들어온 요청을 처리)
@Slf4j // 자동 로깅 코드를 생성해주는 기능 제공
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping() // HTTP GET 요청을 처리하는 핸들러 메소드 지정
    public ResponseEntity<?> getAllUsers() {
        List<UserRegisterResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users); // 조회된 리스트를 ResponseEntity의 body에 담아서 응답
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable("user_id") String user_id) {
        UserRegisterResponseDTO userResponseDTO = userService.getUser(user_id);
        return ResponseEntity.ok().body(userResponseDTO); // 조회된 사용자 정보를 ResponseEntity의 body에 담아서 응답
    }

    @PostMapping("/register") // HTTP POST 요청을 처리하는 핸들러 메소드 지정
    public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO dto) {
        UserRegisterResponseDTO register = userService.register(dto);
        return ResponseEntity.ok().body(register);
    }

    // 사용자 로그인을 처리하는 POST 요청을 처리하는 메소드
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO dto) {
        System.out.println(dto); // 사용자 로그인 요청을 받아 처리하고 응답
        return ResponseEntity.ok().body(new UserLoginRequestDTO());
    }

    // 사용자 정보를 업데이트하는 PUT 요청을 처리하는 핸들러 메소드
    @PutMapping("/update/{user_id}")
    public ResponseEntity<?> update(@PathVariable("user_id") String user_id, @RequestBody UserUpdateRequestDTO dto) {
//        userService.updateUser(user_id, dto);
//        UserRegisterResponseDTO updatedUserResponseDTO = userService.getUser(user_id);
//        return ResponseEntity.ok().body(updatedUserResponseDTO);
        try {
            log.warn("request : {}", dto);
            UserUpdateResponseDTO responseDTO = userService.updateUser(user_id, dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            log.warn("Error : {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

//    @PutMapping("/payment")
//    public ResponseEntity<?> payment(String id){
//        return ResponseEntity.ok().body("결제 정보 수정");
//    }
//
//    @PutMapping("/verify")
//    public ResponseEntity<?> verify(String id){
//        return ResponseEntity.ok().body("이메일 인증");
//    }

    // 특정 사용자를 삭제하는 DELETE 요청을 처리하는 핸들러 메소드
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> delete(@PathVariable("user_id") String user_id){

        try {
            userService.deleteUser(user_id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.warn("Error : {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
