package com.topicals.topicalsapi.auth;

import com.topicals.topicalsapi.actors.appuser.Appuser;
import com.topicals.topicalsapi.actors.appuser.IUserRepository;
import com.topicals.topicalsapi.exceptions.AlreadyExistsException;
import com.topicals.topicalsapi.exceptions.NotFoundException;
import com.topicals.topicalsapi.jwt.JwtService;
import com.topicals.topicalsapi.role.AppUserRole;
import com.topicals.topicalsapi.token.Token;
import com.topicals.topicalsapi.token.TokenRepository;
import com.topicals.topicalsapi.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    
    public String signup(SignupRequest signupRequest) {
        try {
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                throw new AlreadyExistsException("Email", signupRequest.getEmail());
            }

            var user = Appuser.builder()
                    .firstName(signupRequest.getFirstName())
                    .lastName(signupRequest.getLastName())
                    .email(signupRequest.getEmail())
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .roles(new HashSet<>(Set.of(AppUserRole.USER)))
                    .createdAt(LocalDateTime.now())
                    .build();

            var userInDB = userRepository.save(user);

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("roles", userInDB.getRoles());

            var jwtToken = jwtService.generateToken(extraClaims, userInDB);

            saveUserToken(userInDB, jwtToken);
            return "Signup successful. Proceed to login.";

        } catch (AlreadyExistsException e) {
            return "Email already registered. Please use a different email or proceed to login.";
        } catch (Exception e) {
            return "An error occurred during signup. Please try again.";
        }
    }

     private Map<String, Object> mapRolesAsExtraClaims(Appuser user) {
        Map<String, Object> rolesAsExtraClaims = new HashMap<>();
        rolesAsExtraClaims.put("roles", user.getRoles());

        return rolesAsExtraClaims;
    }

    AuthTokenResponse authenticate(
            AuthenticationRequest authRequest
    ) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        var userInDB = userRepository.findUserByEmail(authRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User", authRequest.getEmail()));

        var accessToken = jwtService.generateToken(
                mapRolesAsExtraClaims(userInDB),
                userInDB
        );
        var refreshToken = jwtService.generateRefreshToken(userInDB);

        revokeAllUserTokens(userInDB);
        saveUserToken(userInDB, accessToken);

        return AuthTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    void saveUserToken(Appuser user, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        tokenRepository.save(token);
    }

    void revokeAllUserTokens(Appuser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    AuthTokenResponse refreshToken(
            HttpServletRequest request
    ) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var userInDB = this.userRepository.findUserByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("User", userEmail));

            if (jwtService.isTokenValid(refreshToken, userInDB)) {
                var accessToken = jwtService.generateToken(
                        mapRolesAsExtraClaims(userInDB),
                        userInDB
                );

                revokeAllUserTokens(userInDB);
                saveUserToken(userInDB, accessToken);

                return AuthTokenResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        return null;
    }
}
