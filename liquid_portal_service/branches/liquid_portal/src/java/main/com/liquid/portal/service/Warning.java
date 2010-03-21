/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "warning", propOrder = { "message", "errorCode"})
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
    
    private int errorCode;

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

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
