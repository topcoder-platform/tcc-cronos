/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception will be thrown if manager related configuration data is missing or is incorrect,
 * or if the actual mechanism for configuration reading fails.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class GameDataManagerConfigurationException extends BaseException {
    /**
     * <p>Default constructor.</p>
     *
     */
    public GameDataManagerConfigurationException() {
        super();
    }

    /**
     * <p>Constructs an object of this exception with the given error message.</p>
     *
     * @param msg exception message
     */
    public GameDataManagerConfigurationException(String msg) {
        super(msg);
    }

    /**
     * <p>Constructs an object of this exception with the given error message and cause.</p>
     *
     * @param msg exception message
     * @param cause chained exception cause.
     */
    public GameDataManagerConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
