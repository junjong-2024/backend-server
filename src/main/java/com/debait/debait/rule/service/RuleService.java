package com.debait.debait.rule.service;

import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.room.repository.RoomRepository;
import com.debait.debait.rule.dto.request.RuleInfoRequestDTO;
import com.debait.debait.rule.dto.response.RuleInfoResponseDTO;
import com.debait.debait.rule.entity.Rule;
import com.debait.debait.rule.repository.RuleRepository;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleService {

    private final UserRepository userRepository;
    private final RuleRepository ruleRepository;

    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;

    public RuleInfoResponseDTO create(RuleInfoRequestDTO dto, TokenUserInfo userInfo) {

        User user = userRepository.findById(userInfo.getUserId()).orElseThrow(() ->
                new RuntimeException("일치하는 회원을 찾을 수 없습니다.")
        );

        Rule rule = dto.toEntity(user);
        Rule savedRule = ruleRepository.save(rule);
        //Rule save = ruleRepository.save(dto.toEntity(user));
        //return new RuleInfoResponseDTO(save);

        return new RuleInfoResponseDTO(savedRule);
    }

    // 모든 규칙 가져오기
    public List<RuleInfoResponseDTO> getRuleInfoList(String userId) {
        List<Rule> rules = ruleRepository.findByUser_id(userId);
        if (rules.isEmpty()) {
            throw new IllegalArgumentException("No rules found for user ID: " + userId);
        }
        return rules.stream().map(RuleInfoResponseDTO::new).collect(Collectors.toList());
    }

    // 규칙 상세 보기
    public RuleInfoResponseDTO getRule(String rule_id) {
        Optional<Rule> optionalRule = ruleRepository.findById(rule_id);
        Rule rule = optionalRule.orElseThrow(() -> new RuntimeException("Rule not found"));
        return RuleInfoResponseDTO.fromRule(rule);
    }

    public boolean isRuleInUse(String rule_id) {
        return roomRepository.existsById(rule_id);
    }

    // 규칙 삭제하기
    public String deleteRule(String rule_id) {
        try {
            if (isRuleInUse(rule_id)) {
                return "The rule with ID " + rule_id + " is currently in use and cannot be deleted.";
            }
            Optional<Rule> optionalRule = ruleRepository.findById(rule_id);
            if (optionalRule.isPresent()) {
                ruleRepository.delete(optionalRule.get());
                log.info("Rule with ID {} deleted successfully", rule_id);
                return "Rule with ID " + rule_id + " deleted successfully";
            } else {
                return "Rule not found";
            }
        } catch (Exception e) {
            log.error("Error deleting rule with ID {}: {}", rule_id, e.getMessage());
            return "Failed to delete rule with ID " + rule_id + ". It might be in use.";
        }
    }
}


