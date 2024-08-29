package com.topicals.topicalsapi.actors.appuser;

import com.topicals.topicalsapi.auth.ChangePasswordRequest;

import javax.security.auth.login.CredentialException;
import java.security.Principal;
import java.util.UUID;

public interface IUserService {

    Appuser getUserById(UUID userId);
    void changePassword(ChangePasswordRequest request, Principal connectedUser) throws CredentialException;
}