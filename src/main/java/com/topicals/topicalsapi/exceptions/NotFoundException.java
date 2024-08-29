package com.topicals.topicalsapi.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

public class NotFoundException extends EntityNotFoundException {

    public NotFoundException(
            @NotNull String entityType,
            @NotNull Object entityId
    ) {
        super("Entity not found: " + entityType + " with id " + entityId);
    }
}
