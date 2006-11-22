/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.errorhandling.BaseRuntimeException;


/**
 * This exception will be thrown by GameOperationLogicUtility#initialize block if failed to load the configured values
 * or they are invalid.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class GameOperationConfigException extends BaseRuntimeException {
    /**
     * Create the exception with msg.
     *
     * @param msg the error message
     */
    public GameOperationConfigException(String msg) {
        super(msg);
    }

    /**
     * Create the exception with error message and cause exception.
     *
     * @param msg the error message
     * @param cause the cause exception
     */
    public GameOperationConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
