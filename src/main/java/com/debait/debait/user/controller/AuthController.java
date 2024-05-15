package com.debait.debait.user.controller;

import com.debait.debait.auth.JwtAuthFilter;
import com.debait.debait.auth.TokenProvider;
import com.debait.debait.user.dto.request.UserLoginRequestDTO;
import com.debait.debait.user.dto.request.UserTokenRequestDTO;
import com.debait.debait.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<UserTokenRequestDTO> authorize(@Valid @RequestBody UserLoginRequestDTO loginDto) {
        // UserService의 login 메소드를 호출하여 JWT를 생성
        String jwt = userService.login(loginDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token을 넣어줌
        httpHeaders.add(JwtAuthFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // tokenDto를 이용해 response body에도 넣어서 리턴
        return new ResponseEntity<>(new UserTokenRequestDTO(jwt), httpHeaders, HttpStatus.OK);
    }
}
