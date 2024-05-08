package com.debait.debait.user.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.debait.debait.user.dto.request.UserRegisterRequestDTO;
import com.debait.debait.user.dto.request.UserUpdateRequestDTO;
import com.debait.debait.user.dto.response.UserRegisterResponseDTO;
import com.debait.debait.user.dto.response.UserUpdateResponseDTO;
import com.debait.debait.user.entity.User;
import com.debait.debait.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
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
        User save = userRepository.save(dto.toEntity());
        return new UserRegisterResponseDTO(save);
    }

    @Transactional
    public UserUpdateResponseDTO updateUser(String user_id, UserUpdateRequestDTO dto) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("일치한 회원이 없습니다."));

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

        User savedUser = userRepository.save(user);
        log.warn("savedUser : {}", savedUser);

        return new UserUpdateResponseDTO(savedUser);
    }

    // void updatePayment(String user_id, UserRegisterResponseDTO userDTO);

    // void verifyEmail(String user_id);

    public void deleteUser(String user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("일치한 회원이 없습니다."));
        userRepository.delete(user);
    }
}
