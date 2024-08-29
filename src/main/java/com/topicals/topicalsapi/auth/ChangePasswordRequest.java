package com.topicals.topicalsapi.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotNull(message = "existingPassword cannot be null")
    @NotBlank(message = "existingPassword must not be empty")
    private String existingPassword;

    @NotNull(message = "newPassword cannot be null")
    @NotBlank(message = "newPassword must not be empty")
    private String newPassword;

}
