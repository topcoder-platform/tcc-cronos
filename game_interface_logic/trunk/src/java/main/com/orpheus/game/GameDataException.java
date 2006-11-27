/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This is essentially an exception indicating a failure to modify game data.
 * It will be invoked for the following issues:
 * <li>When we try to persist (Read/Write) data from and to the persistence supporting EJB.</li>
 * <li>When we try to lookup/create an EJB (local or remote) and we fail.</li>
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class GameDataException extends BaseException {
    /**
     * <p>Default constructor.</p>
     *
     */
    public GameDataException() {
        super();
    }

    /**
     * <p>Constructs an object of this exception with the given error message.</p>
     *
     * @param msg exception message
     */
    public GameDataException(String msg) {
        super(msg);
    }

    /**
     * <p>Constructs an object of this exception with the given error message and cause.</p>
     *
     * @param msg exception message
     * @param cause chained exception cause.
     */
    public GameDataException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
