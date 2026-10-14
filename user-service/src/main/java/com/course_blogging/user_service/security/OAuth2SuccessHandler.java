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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2SuccessHandler.class);
    private static final String FRONTEND_OAUTH_SUCCESS_URL = "http://localhost:5173/oauth-success?token=";

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

        log.info("OAuth2 success handler called for provider: {}", authentication.getAuthorities());
        OAuth2User oauth2User =(OAuth2User) authentication.getPrincipal();

        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String providerId = oauth2User.getAttribute("sub");

        log.info("Google authentication succeeded for email: {}, provider ID: {}", email, providerId);

        UserEntity user = userRepository.findByemail(email).orElse(null);
        if (user == null) {
            user = new UserEntity();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(null);
            user.setAuthType(AuthType.GOOGLE);
            user.setProviderId(providerId);
            userRepository.save(user);
            log.info("Created Google user with ID: {}", user.getUserId());
        } else {
            // existing normal account
            if (user.getAuthType() == AuthType.NORMAL) {
                log.info("Google login rejected because {} belongs to a normal account", email);
                response.sendRedirect("http://localhost:5173/login?error=normal-account");
                return;
            }
            log.info("Found existing Google user with ID: {}", user.getUserId());
        }
        // google generate the login and get jwt token
        String token = jwtService.GenerateToken(user);
        log.info("Application JWT generated for user ID: {}", user.getUserId());
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String redirectUrl = FRONTEND_OAUTH_SUCCESS_URL + encodedToken;
        log.info("Redirecting Google user to frontend OAuth success page");
        response.sendRedirect(redirectUrl);
    }
}
