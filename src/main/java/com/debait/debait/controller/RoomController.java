package com.debait.debait.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    @PostMapping()
    public ResponseEntity<?> create(String name){
        return ResponseEntity.ok().body("토론방 생성");
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
        return ResponseEntity.ok().body("토론 방 상세 간단히 보기");
    }
}
