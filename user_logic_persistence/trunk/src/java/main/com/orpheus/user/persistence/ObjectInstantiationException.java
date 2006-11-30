/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import com.orpheus.user.persistence.impl.SQLServerPendingConfirmationDAO;
import com.orpheus.user.persistence.impl.SQLServerUserProfileDAO;
import com.orpheus.user.persistence.impl.UserProfileTranslator;

/**
 * <p>
 * Thrown when the construction of a class fails due to a configuration error.
 * This exception is thrown by the constructors of the following classes:
 * </p>
 * <ul>
 * <li>{@link OrpheusPendingConfirmationStorage}</li>
 * <li>{@link LocalOrpheusPendingConfirmationStorage}</li>
 * <li>{@link RemoteOrpheusPendingConfirmationStorage}</li>
 * <li>{@link SQLServerPendingConfirmationDAO}</li>
 * <li>{@link SQLServerUserProfileDAO}</li>
 * <li>{@link UserProfileTranslator}</li>
 * </ul>
 * <p>
 * This exception is also thrown by the
 * {@link DAOFactory#getPendingConfirmationDAO()} and
 * {@link DAOFactory#getUserProfileDAO()} methods when the DAO classes which
 * those they create cannot be instantiated.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public class ObjectInstantiationException extends UserPersistenceException {

    /**
     * <p>
     * Creates a new <code>ObjectInstantiationException</code> with the
     * specified detail message.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     */
    public ObjectInstantiationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new <code>ObjectInstantiationException</code> with the
     * specified detail message and cause.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     * @param cause the cause of this exception
     */
    public ObjectInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

}
