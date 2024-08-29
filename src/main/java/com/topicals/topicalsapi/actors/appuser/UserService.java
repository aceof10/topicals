package com.topicals.topicalsapi.actors.appuser;

import com.topicals.topicalsapi.auth.ChangePasswordRequest;
import com.topicals.topicalsapi.exceptions.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, IUserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Appuser getUserById(UUID userId) {
        return userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));
    }

    Appuser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email).orElse(null);
    }


    public void changePassword(ChangePasswordRequest request, Principal connectedUser) throws CredentialException {

        var appUser = (Appuser) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getExistingPassword(), appUser.getPassword())) {
            throw new CredentialException();
        }

        appUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Appuser appUser = userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User %s not found", email)));

        return new User(appUser.getEmail(), appUser.getPassword(), new ArrayList<>());
    }
}