/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.io.Serializable;

/**
 * <p>
 * Represents a non-fatal warning regarding something that went wrong during
 * processing. It contains the message.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class Warning implements Serializable {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -8171617124012723029L;

    /**
     * <p>
     * Represents the warning message.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     */
    private String message;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public Warning() {
    }

    /**
     * <p> Gets the message. </p>
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>
     * Sets the message.
     * </p>
     *
     * @param message
     *            the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
