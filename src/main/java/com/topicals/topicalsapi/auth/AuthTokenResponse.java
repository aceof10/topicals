package com.topicals.topicalsapi.auth;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class AuthTokenResponse {
    private final String accessToken;
    private final String refreshToken;

    public AuthTokenResponse(String authorizationToken, String refreshToken) {
        this.accessToken = authorizationToken;
        this.refreshToken = refreshToken;
    }

}
