package com.debait.debait.rule.controller;

import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.service.RoomService;
import com.debait.debait.rule.dto.request.RuleInfoRequestDTO;
import com.debait.debait.rule.dto.response.RuleInfoResponseDTO;
import com.debait.debait.rule.service.RuleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/rule")
@Slf4j
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 규칙 생성하기
    @PostMapping()
    public ResponseEntity<?> create(@AuthenticationPrincipal TokenUserInfo userInfo, @RequestBody RuleInfoRequestDTO dto, HttpServletRequest request) {

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // Rule 생성 시 사용자 아이디를 포함하여 DTO 객체 생성
        dto.setUser_id(userInfo.getUserId());

        RuleInfoResponseDTO createRule = ruleService.create(dto, userInfo);
        return ResponseEntity.ok().body(createRule);
        //return ResponseEntity.ok().body("규칙 생성하기");
    }

    // 규칙 목록 가져오기
    @GetMapping()
    public ResponseEntity<?> getRule(String user_id) {
        return ResponseEntity.ok().body("규칙 목록 가져오기");
    }

    // 규칙 상세 정보보기
    @GetMapping("/info")
    public ResponseEntity<?> getRuleInfo(String rule_id) {
        return ResponseEntity.ok().body("규칙 상세 보기");
    }

}
