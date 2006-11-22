/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.errorhandling.BaseRuntimeException;


/**
 * <p>
 * GameDataConfigurationException is thrown from GameDataHelper's static initializer if the any property is not
 * configured properly.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
public class GameDataConfigurationException extends BaseRuntimeException {

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message
     *            the error message.
     */
    public GameDataConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     *
     * @param message
     *            the error message.
     * @param cause
     *            the inner cause.
     */
    public GameDataConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
