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
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        List<UserRegisterResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable("user_id") String user_id) {
        UserRegisterResponseDTO userResponseDTO = userService.getUser(user_id);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO dto) {
        UserRegisterResponseDTO register = userService.register(dto);
        return ResponseEntity.ok().body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO dto) {
        System.out.println(dto);
        return ResponseEntity.ok().body(new UserLoginRequestDTO());
    }

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
