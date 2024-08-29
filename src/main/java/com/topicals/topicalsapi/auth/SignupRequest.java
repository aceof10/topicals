package com.topicals.topicalsapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SignupRequest {

    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName must not be empty")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    @NotBlank(message = "lastName must not be empty")
    private String lastName;

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email must not be empty")
    @Email(message = "Invalid email!")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password must not be empty")
    private String password;

}
