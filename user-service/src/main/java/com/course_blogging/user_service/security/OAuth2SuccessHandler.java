package com.course_blogging.user_service.security;


import com.course_blogging.user_service.entity.AuthType;
import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    public OAuth2SuccessHandler(
            UserRepository userRepository,
            JWTService jwtService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauth2User =(OAuth2User) authentication.getPrincipal();

        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String providerId = oauth2User.getAttribute("sub");

        UserEntity user = userRepository.findByemail(email).orElse(null);
        if (user == null) {
            user = new UserEntity();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(null);
            user.setAuthType(AuthType.GOOGLE);
            user.setProviderId(providerId);
            userRepository.save(user);
        } else {
            // existing normal account
            if (user.getAuthType() == AuthType.NORMAL) {
                response.sendRedirect("http://localhost:5173/login?error=normal-account");
                return;
            }

        }
        // Google only establishes identity; application requests continue with this JWT.
        String token = jwtService.GenerateToken(user);
        response.sendRedirect("http://localhost:5173/oauth-success?token=" + token);
    }
    }
