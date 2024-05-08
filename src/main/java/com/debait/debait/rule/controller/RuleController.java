package com.debait.debait.rule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rule")
public class RuleController {

    @GetMapping()
    public ResponseEntity<?> getRule(String user_id) {
        return ResponseEntity.ok().body("규칙 목록 가져오기");
    }

    @GetMapping("/info")
    public ResponseEntity<?> getRuleInfo(String rule_id) {
        return ResponseEntity.ok().body("규칙 상세 보기");
    }
}
