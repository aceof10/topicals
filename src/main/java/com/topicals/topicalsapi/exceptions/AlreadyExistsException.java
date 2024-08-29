package com.topicals.topicalsapi.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotNull;

public class AlreadyExistsException extends EntityExistsException {

    public AlreadyExistsException(
            @NotNull String course,
            @NotNull String email
    ) {
        super("Already exists: " + course + ": " + email);
    }
}
