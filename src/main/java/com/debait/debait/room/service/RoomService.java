package com.debait.debait.room.service;

import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.entity.Room;
import com.debait.debait.room.repository.RoomRepository;
import com.debait.debait.rule.entity.Rule;
import com.debait.debait.rule.repository.RuleRepository;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RuleRepository ruleRepository;
    public RoomInfoResponseDTO create(RoomInfoRequestDTO dto, TokenUserInfo userInfo) {

        User user = userRepository.findById(userInfo.getUserId()).orElseThrow(() ->
                new RuntimeException("일치하는 회원을 찾을 수 없습니다.")
        );

        Rule aaabbb = ruleRepository.findById("aaabbb").orElseThrow(() -> new RuntimeException("조건에 맞는 규칙이 없습니다."));


        Room save = roomRepository.save(dto.toEntity(user, aaabbb));
        return new RoomInfoResponseDTO(save);
    }

//    public List<RoomInfoResponseDTO> getRoomInfoList(String user_id) {
//
//    }
}
