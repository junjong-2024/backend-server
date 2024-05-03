package com.debait.debait.controller;

import com.debait.debait.dto.request.UserRegisterRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public ResponseEntity<?> getUser(String user_id){
        return ResponseEntity.ok().body("사용자 정보");
    }

    @PostMapping()
    public ResponseEntity<?> register(UserRegisterRequestDTO dto){
        return ResponseEntity.ok().body("회원가입 요청 들어옴");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(String id){
        return ResponseEntity.ok().body("정보 수정 요청");
    }

    @PutMapping("/payment")
    public ResponseEntity<?> payment(String id){
        return ResponseEntity.ok().body("결제 정보 수정");
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verify(String id){
        return ResponseEntity.ok().body("이메일 인증");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(String id){
        return ResponseEntity.ok().body("회원 삭제");
    }

}
