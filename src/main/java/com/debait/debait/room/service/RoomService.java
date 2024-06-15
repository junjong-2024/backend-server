package com.debait.debait.room.service;

import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.request.RoomUpdateRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.dto.response.RoomUpdateResponseDTO;
import com.debait.debait.room.entity.Room;
import com.debait.debait.room.repository.RoomRepository;
import com.debait.debait.rule.entity.Rule;
import com.debait.debait.rule.repository.RuleRepository;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RuleRepository ruleRepository;

    @Transactional
    public RoomInfoResponseDTO create(RoomInfoRequestDTO dto, TokenUserInfo userInfo) {

        User user = userRepository.findById(userInfo.getUserId()).orElseThrow(() ->
                new RuntimeException("일치하는 회원을 찾을 수 없습니다.")
        );

        Rule rule = ruleRepository.findById(dto.getRule_id())
                .orElseThrow(() -> new RuntimeException("지정된 규칙을 찾을 수 없습니다."));

        Room save = roomRepository.save(dto.toEntity(user, rule));
        RoomInfoResponseDTO responseDTO = new RoomInfoResponseDTO(save);

        return responseDTO;
    }

    public List<RoomInfoResponseDTO> getRoomInfoList(String userId) {
//        List<Room> rooms = roomRepository.findByUser_Id(userId);
//        return rooms.stream().map(RoomInfoResponseDTO::new).collect(Collectors.toList());
        List<Room> rooms = roomRepository.findByUser_IdOrderByCreated_atDesc(userId);
        return rooms.stream().map(RoomInfoResponseDTO::new).collect(Collectors.toList());
    }

    public RoomInfoResponseDTO getRoom(String room_id) {
        Optional<Room> optionalRoom = roomRepository.findById(room_id);
        Room room = optionalRoom.orElseThrow(()-> new RuntimeException("Room not found"));
        //return modelMapper.map(room, RoomInfoResponseDTO.class);
        return RoomInfoResponseDTO.fromRoom(room);
    }

    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }

    @Transactional
    public RoomUpdateResponseDTO updateRoom(String room_id, RoomUpdateRequestDTO dto) {
        Room room = roomRepository.findById(room_id).orElseThrow(()-> new RuntimeException("일치하는 토론방이 없습니다."));

        if (dto.getScript() != null){
            room.setScript(dto.getScript());
        }

        if (dto.getVideo_src() != null) {
            room.setVideo_src(dto.getVideo_src());
        }

        if (dto.getThumbnail_src() != null) {
            room.setThumbnail_src(dto.getThumbnail_src());
        }

        Room savedRoom = roomRepository.save(room);

        return new RoomUpdateResponseDTO(savedRoom);
    }

}