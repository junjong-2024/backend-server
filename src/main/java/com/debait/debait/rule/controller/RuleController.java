package com.debait.debait.rule.controller;

import com.debait.debait.rule.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;

    @GetMapping()
    public ResponseEntity<?> getRule(String user_id) {
        return ResponseEntity.ok().body("규칙 목록 가져오기");
    }

    @GetMapping("/info")
    public ResponseEntity<?> getRuleInfo(String rule_id) {
        return ResponseEntity.ok().body("규칙 상세 보기");
    }
}
