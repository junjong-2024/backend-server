package com.debait.debait.user.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.debait.debait.auth.TokenProvider;
import com.debait.debait.user.dto.request.UserLoginRequestDTO;
import com.debait.debait.user.dto.request.UserRegisterRequestDTO;
import com.debait.debait.user.dto.request.UserUpdateRequestDTO;
import com.debait.debait.user.dto.response.UserLoginResponseDTO;
import com.debait.debait.user.dto.response.UserRegisterResponseDTO;
import com.debait.debait.user.dto.response.UserUpdateResponseDTO;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    public List<UserRegisterResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserRegisterResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UserRegisterResponseDTO getUser(String user_id) {
        Optional<User> optionalUser = userRepository.findById(user_id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserRegisterResponseDTO.class);
    }


    public UserRegisterResponseDTO register(UserRegisterRequestDTO dto) {
        // 아이디 중복 확인 로직 추가
        if (userRepository.existsByLoginId(dto.getLogin_id())) {
            throw new RuntimeException("아이디가 이미 존재합니다.");
        }

        User save = userRepository.save(dto.toEntity());
        return new UserRegisterResponseDTO(save);
    }

    @Transactional
    public UserUpdateResponseDTO updateUser(String user_id, UserUpdateRequestDTO dto) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("일치한 회원이 없습니다."));

        if (dto.getName() != null) {
            user.setName(dto.getName());
//            log.warn("name : ", dto.getName());
        }
        if (dto.getUser_email() != null) {
            user.setUser_email(dto.getUser_email());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }

        User savedUser = userRepository.save(user);
//        log.warn("savedUser : {}", savedUser);

        return new UserUpdateResponseDTO(savedUser);
    }

    // void updatePayment(String user_id, UserRegisterResponseDTO userDTO);

    // void verifyEmail(String user_id);

    public void deleteUser(String user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("일치한 회원이 없습니다."));
        userRepository.delete(user);
    }

    public UserLoginResponseDTO login (UserLoginRequestDTO dto) {
        // UserRepository를 사용하여 사용자 정보를 가져옴
//        User user = userRepository.findByLogin_id(dto.getLogin_id())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<User> optionalUser = userRepository.findByLogin_id(dto.getLogin_id());


        if (!optionalUser.isPresent()) {
            return new UserLoginResponseDTO("User not found");
        }
        User user = optionalUser.get();

        // 사용자가 존재하지 않는 경우
        //if (user == null) {
        //    return new UserLoginResponseDTO("User not found");
        //}

        // 사용자 정보와 비밀번호를 확인하여 토큰 생성
        // 실제 보안에 필요한 추가적인 검증이 필요합니다.
        if (!user.getPassword().equals(dto.getPassword())) {
            //throw new IllegalArgumentException("Invalid password");
            return new UserLoginResponseDTO("Invalid password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());


        // 토큰 생성
        String token = tokenProvider.createToken(user);

        // 로그인 응답 DTO 생성
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        responseDTO.setToken(token);

        return responseDTO;
    }
}
