package com.debait.debait.room.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.request.RoomUpdateRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.dto.response.RoomUpdateResponseDTO;
import com.debait.debait.room.entity.Room;
import com.debait.debait.room.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    if (dto.getName() == null || dto.getRule_id() == null){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rule_name and rule_id must be provided");
    }
    // Room 생성 시 사용자 아이디를 포함하여 DTO 객체 생성
    dto.setUser_id(userInfo.getUserId());

    RoomInfoResponseDTO create = roomService.create(dto, userInfo);
    return ResponseEntity.ok().body(create);
    }

    // 특정 사용자가 생성한 토론 목록을 가져오기
    @GetMapping("/list")
    public ResponseEntity<?> getRoom(@AuthenticationPrincipal TokenUserInfo userInfo) {
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        List<RoomInfoResponseDTO> rooms = roomService.getRoomInfoList(userInfo.getUserId());
        return ResponseEntity.ok().body(rooms);
    }

    // 특정 방의 상세 정보를 가져오기
    @GetMapping("/{room_id}")
    public ResponseEntity<?> getRoomInfo(@PathVariable("room_id") String room_id){
    RoomInfoResponseDTO roomInfoResponseDTO = roomService.getRoom(room_id);
    return ResponseEntity.ok().body(roomInfoResponseDTO);
        // return ResponseEntity.ok().body("토론 방 상세 정보");
    }

    @PutMapping("/update/{room_id}")
    public ResponseEntity<?> update(@PathVariable("room_id") String room_id, @RequestBody RoomUpdateRequestDTO dto) {
        try {
            log.warn("request : {}", dto);
            RoomUpdateResponseDTO responseDTO = roomService.updateRoom(room_id, dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e){
            log.warn("Error : {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            log.warn(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    // 특정 방의 정보를 간단히 보기
    @GetMapping("/summary")
    public ResponseEntity<?> getRoomSummary(String room_id){
        return ResponseEntity.ok().body("토론 방 정보 간단히 보기");
    }

    @DeleteMapping("/del/{room_id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("room_id") String room_id) {
        roomService.deleteRoom(room_id);
        return ResponseEntity.ok().body("Room deleted successfully");
    }

}