package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.config.JwtService;
import com.example.sayakat_travel.dto.request.SignInDto;
import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.dto.response.AuthenticationResponse;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.Roles;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.UserRepository;
import com.example.sayakat_travel.service.UserService;
import com.example.sayakat_travel.token.Token;
import com.example.sayakat_travel.token.TokenRepository;
import com.example.sayakat_travel.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileUploadServiceImpl fileUploadService;
    private final JavaMailSender mailSender;


    @Override
    public void signUpUser(SignUpDto signUpDto,String siteUrl) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate birthday = LocalDate.parse(signUpDto.getBirthday(),formatter);
        String photo_url = null;
        try {
            photo_url = fileUploadService.uploadFile(signUpDto.getPhoto());
        } catch (IOException e) {
            throw new ApiRequestException("Can not upload a image");
        }
        String randomCode = RandomStringUtils.random(64);
        User user =  User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .photo(photo_url)
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .middleName(signUpDto.getMiddleName())
                .role(Roles.USER)
                .phoneNumber(signUpDto.getPhoneNumber())
                .userInfo(signUpDto.getUserInfo())
                .birthday(birthday)
                .verificationCode(randomCode)
                .createdDate(LocalDate.now())
                .enabled(false)
                .build();
        repository.save(user);
        sendVerificationEmail(user,siteUrl);
    }

    @Override
    public User getUserById(Long id) {
        User user = repository.findById(id).orElse(null);
        if(user == null){
            throw new ApiRequestException("Not Found User:" + id);
        }
        return user;
    }

    public AuthenticationResponse signInUser(SignInDto signInDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(),
                        signInDto.getPassword()
                )
        );
        var user = repository.findByEmail(signInDto.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


    private void sendVerificationEmail(User user, String siteURL) {
        String toAddress = user.getEmail();
        String fromAddress = "travelsayakat@gmail.com";
        String senderName = "Sayakat Travel";
        String subject = "Пожалуйста подтвердите свою почту";
        String content = "Увожаемый(ая) [[name]],<br>"
                + "Пожалуйста нажмите на кнупку ниже, чтобы подтвердить Вашу почту:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ПОДТВЕРДИТь</a></h3>"
                + "Спасибо большое,<br>"
                + "Sayakat Travel.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            String fullName = user.getLastName() + " " + user.getFirstName();
            content = content.replace("[[name]]", fullName);
            String verifyURL = siteURL + "/api/user/verify?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ApiRequestException("Can Not Send Email Verification Code to email: "+ user.getEmail());

        }
    }

    public boolean verify(String verificationCode) {
        User user = repository.findByVerificationCode(verificationCode).orElse(null);
        if(user == null || user.isEnabled()) {
            return false;
        }else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repository.save(user);
            return true;
        }
    }
}
