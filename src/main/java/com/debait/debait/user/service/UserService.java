package com.debait.debait.user.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.debait.debait.auth.TokenProvider;
import com.debait.debait.user.dto.request.UserLoginRequestDTO;
import com.debait.debait.user.dto.request.UserRegisterRequestDTO;
import com.debait.debait.user.dto.request.UserUpdateRequestDTO;
import com.debait.debait.user.dto.response.UserRegisterResponseDTO;
import com.debait.debait.user.dto.response.UserUpdateResponseDTO;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public List<UserRegisterResponseDTO> getAllUsers() {
        //  UserRepository에서 모든 사용자를 조회
        List<User> users = userRepository.findAll();
        //조회된 사용자를 UserRegisterResponseDTO 로 변환하여 리스트로 반환
        return users.stream()
                .map(user -> modelMapper.map(user, UserRegisterResponseDTO.class))
                .collect(Collectors.toList());
    }
    // 특정 사용자를 조회하는 메소드
    public UserRegisterResponseDTO getUser(String user_id) {
        Optional<User> optionalUser = userRepository.findById(user_id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        // 조회된 사용자를 UserRegisterResponseDTO로 변환하여 반환
        return modelMapper.map(user, UserRegisterResponseDTO.class);
    }

    // 새로운 사용자를 등록하는 메소드
    public UserRegisterResponseDTO register(UserRegisterRequestDTO dto) {
        User save = userRepository.save(dto.toEntity());
        return new UserRegisterResponseDTO(save);
    }

    // 사용자 정보를 업데이트하는 메소드
    @Transactional
    public UserUpdateResponseDTO updateUser(String user_id, UserUpdateRequestDTO dto) {
        // 주어진 ID에 해당하는 사용자를 UserRepository에서 조회
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("일치한 회원이 없습니다."));

        // 받은 DTO에 변경된 정보가 있으면 각 필드를 업데이트
        if (dto.getName() != null) {
            user.setName(dto.getName());
            log.warn("name : ", dto.getName());
        }
        if (dto.getUser_email() != null) {
            user.setUser_email(dto.getUser_email());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }

        // 업데이트된 사용자 정보를 UserRepository에 저장하고 반환
        User savedUser = userRepository.save(user);
        log.warn("savedUser : {}", savedUser);

        return new UserUpdateResponseDTO(savedUser);
    }

    // void updatePayment(String user_id, UserRegisterResponseDTO userDTO);

    // void verifyEmail(String user_id);

    // 사용자를 삭제하는 메소드
    public void deleteUser(String user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("일치한 회원이 없습니다."));
        userRepository.delete(user);
    }

    public String login(UserLoginRequestDTO userLoginRequestDTO) {

        Optional<User> userOptional = userRepository.findOneWithAuthoritiesByLogin_id(userLoginRequestDTO.getLogin_id());

        if (!userOptional.isPresent()) {
            String errorMessage = "User not found with login_id: " + userLoginRequestDTO.getLogin_id();
            System.err.println(errorMessage); // 서버에서 확인 가능하도록 출력
            throw new IllegalArgumentException(errorMessage);
        }


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getLogin_id(), userLoginRequestDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);
    }
}
