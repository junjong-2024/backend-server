package com.debait.debait.rule.service;

import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.rule.dto.request.RuleInfoRequestDTO;
import com.debait.debait.rule.dto.response.RuleInfoResponseDTO;
import com.debait.debait.rule.entity.Rule;
import com.debait.debait.rule.repository.RuleRepository;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleService {

    private final UserRepository userRepository;
    private final RuleRepository ruleRepository;

    private final ModelMapper modelMapper;

    public RuleInfoResponseDTO create(RuleInfoRequestDTO dto, TokenUserInfo userInfo) {

        User user = userRepository.findById(userInfo.getUserId()).orElseThrow(() ->
                new RuntimeException("일치하는 회원을 찾을 수 없습니다.")
        );

        Rule save = ruleRepository.save(dto.toEntity(user));
        return new RuleInfoResponseDTO(save);
    }

    // 모든 규칙 가져오기
    public List<RuleInfoResponseDTO> getRuleInfoList(String userId) {
        List<Rule> rules = ruleRepository.findByUser_id(userId);
        if (rules.isEmpty()) {
            throw new IllegalArgumentException("No rules found for user ID: " + userId);
        }
        return rules.stream().map(RuleInfoResponseDTO::new).collect(Collectors.toList());
    }
}
