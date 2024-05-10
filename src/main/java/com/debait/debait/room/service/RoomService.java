package com.debait.debait.room.service;

import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.entity.Room;
import com.debait.debait.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomInfoResponseDTO create(RoomInfoRequestDTO dto) {
        Room save = roomRepository.save(dto.toEntity());
        return new RoomInfoResponseDTO(save);
    }

//    public List<RoomInfoResponseDTO> getRoomInfoList(String user_id) {
//
//    }
}
