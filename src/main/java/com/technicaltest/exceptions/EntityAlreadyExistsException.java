package com.technicaltest.exceptions;

/**
 * EntityAlreadyExistsException is a custom exception class that extends RuntimeException.
 * It is thrown when an attempt is made to create an entity that already exists in the database.
 *
 * @author Wilmaryucuma7
 * @version 1.0
 *
 */
public class EntityAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new EntityAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}