package com.debait.debait.room.controller;

import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.entity.Room;
import com.debait.debait.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody RoomInfoRequestDTO dto){
        RoomInfoResponseDTO create = roomService.create(dto);
        return ResponseEntity.ok().body(create);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getRoom(String user_id){
        return ResponseEntity.ok().body("토론 목록 가져오기");
    }

    @GetMapping("/info")
    public ResponseEntity<?> getRoomInfo(String room_id){
        return ResponseEntity.ok().body("토론 방 상세 정보");
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getRoomSummary(String room_id){
        return ResponseEntity.ok().body("토론 방 정보 간단히 보기");
    }
}
