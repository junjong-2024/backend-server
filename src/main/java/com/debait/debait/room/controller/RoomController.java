package com.debait.debait.room.controller;

import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.entity.Room;
import com.debait.debait.room.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Value("${jwt.secret}")
    private String jwtSecret;
@PostMapping()
public ResponseEntity<?> create(@AuthenticationPrincipal TokenUserInfo userInfo, @RequestBody RoomInfoRequestDTO dto, HttpServletRequest request) {

    if (userInfo == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    // Room 생성 시 사용자 아이디를 포함하여 DTO 객체 생성
    dto.setUser_id(userInfo.getUserId());

    RoomInfoResponseDTO create = roomService.create(dto, userInfo);
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