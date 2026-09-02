package com.course_blogging.user_service.service;

import com.course_blogging.user_service.DTO.AutheticationResponce;
import com.course_blogging.user_service.DTO.LoginRequest;
import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.repository.UserRepository;
import com.course_blogging.user_service.security.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;

    public UserEntity CreateUser(UserEntity user) {
        UserEntity userEntity=new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setBio(user.getBio());
        log.info("password "+userEntity.getPassword());
        return userRepository.save(userEntity);
    }
    public List<UserEntity> GetAllUser() {
        return userRepository.findAll();
    }
    public UserEntity GetUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("UserNotFound"));
    }

    public UserEntity UpdateUser(UserEntity user, Long userId) {
        UserEntity userEntity = GetUserById(userId);
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setBio(user.getBio());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setCreateAt(user.getCreateAt());
        return userRepository.save(userEntity);
    }

    public void DeleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public AutheticationResponce Login(LoginRequest loginRequest) throws Throwable {
        UserEntity userEntity = userRepository.findByemail(loginRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())){
            throw new InputMismatchException("Email and password is invalid");
        }
        return new AutheticationResponce(jwtService.GenerateToken(userEntity),"bearer", userEntity.getUserId());
    }
}
