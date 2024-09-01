package com.topicals.topicalsapi.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER_STRING = "Authorization";
    public static final String REFRESH_TOKEN_HEADER_STRING = "Refresh-Token";

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(
            @RequestBody @Valid SignupRequest signupRequest
    ) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Void> authenticate(
            @RequestBody @Valid AuthenticationRequest authRequest,
            HttpServletResponse response
    ) {
        AuthTokenResponse authTokenResponse = authService.authenticate(authRequest);
        response.setHeader(AUTHORIZATION_HEADER_STRING,
                AUTHORIZATION_HEADER_PREFIX + authTokenResponse.getAccessToken());
        response.setHeader(REFRESH_TOKEN_HEADER_STRING,
                AUTHORIZATION_HEADER_PREFIX + authTokenResponse.getRefreshToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        AuthTokenResponse authTokenResponse = authService.refreshToken(request);

        if(authTokenResponse != null) {
            response.setHeader(AUTHORIZATION_HEADER_STRING,
                    AUTHORIZATION_HEADER_PREFIX + authTokenResponse.getAccessToken());
            response.setHeader(REFRESH_TOKEN_HEADER_STRING,
                    AUTHORIZATION_HEADER_PREFIX + authTokenResponse.getRefreshToken());

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
