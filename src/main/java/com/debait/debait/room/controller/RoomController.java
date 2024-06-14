package com.debait.debait.room.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.debait.debait.auth.TokenUserInfo;
import com.debait.debait.room.dto.request.RoomInfoRequestDTO;
import com.debait.debait.room.dto.request.RoomUpdateRequestDTO;
import com.debait.debait.room.dto.response.RoomInfoResponseDTO;
import com.debait.debait.room.dto.response.RoomUpdateResponseDTO;
import com.debait.debait.room.entity.Room;
import com.debait.debait.room.entity.RoomSocket;
import com.debait.debait.room.service.RoomService;
import com.debait.debait.room.service.SocketClient;
import com.debait.debait.rule.entity.Rule;
import com.debait.debait.rule.repository.RuleRepository;
import com.debait.debait.rule.service.RuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @Autowired
    private RuleRepository ruleRepository;  // RuleRepository 주입

    @Autowired
    private SocketClient socketClient;

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

        // DTO에서 rule_id를 기반으로 Rule 객체 가져오기
        Optional<Rule> ruleOptional = ruleRepository.findByRule_id(dto.getRule_id());
        if (!ruleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rule not found");
        }

        Rule rule = ruleOptional.get();
        // Rule 객체에서 spec 값 추출 및 {와 } 제거
        String ruleSpec = rule.getSpec();
        if (ruleSpec.startsWith("{") && ruleSpec.endsWith("}")) {
            ruleSpec = ruleSpec.substring(1, ruleSpec.length() - 1);
        }

        // Room 생성 시 사용자 아이디를 포함하여 DTO 객체 생성
        dto.setUser_id(userInfo.getUserId());

        RoomInfoResponseDTO create = roomService.create(dto, userInfo);

        // spec 값을 JSON 문자열에 포함
        String jsonString = "{ \"name\" : \"" + dto.getName() + "\", \"description\" : \"" + dto.getDescription() + "\", "+ ruleSpec + " }";
        // System.out.println(jsonString);

        ObjectMapper mapper = new ObjectMapper();
        RoomSocket.RoomInfo roomInfo = null;
        try {
            // JSON 문자열을 RoomInfo 객체로 변환
            roomInfo = mapper.readValue(jsonString, RoomSocket.RoomInfo.class);

            // 소켓 클라이언트를 사용하여 roomInfo 전송
            socketClient.sendRoomInfo(roomInfo);

//            System.out.println("Room Name: " + roomInfo.getName());
//            System.out.println("Description: " + roomInfo.getDescription());
//            System.out.println("Team Size: " + roomInfo.getTeamSize());
//            System.out.println("Order Size: " + roomInfo.getOrderSize());
//            System.out.println("Rules:");
//            for (RoomSocket.Rules rule1 : roomInfo.getRules()) {
//                System.out.println("- Debater: " + rule1.getDebater() + ", Msg: " + rule1.getMsg() + ", Time: " + rule1.getTime());
//            }
        } catch (JsonProcessingException e) {
            // JSON 파싱 중 오류 발생 시 처리
            e.printStackTrace();
        }

        //System.out.println("-------------");
        //System.out.println(roomInfo);

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