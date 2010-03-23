/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Represents the processing result. The absence of warnings signifies a
 * flawless execution. Otherwise, any warning will detail when something
 * non-fatal occurred. Returned in the validateUser and provisionProject methods
 * of the service.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = { "warnings", "resultCode", "message"})
public class Result implements Serializable {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -4763445124339491980L;

    /**
     * <p>
     * Represents the list of warnings.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     */
    private List<Warning> warnings;

    private int resultCode;

    private String message;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public Result() {
    }

    
    /**
     * Default constructor.
     */
    public Result(String message, int resultCode) {
        this.message = message;
        this.resultCode = resultCode;
    }

    /**
     * <p>
     * Gets the list of warnings.
     * </p>
     *
     * @return the list of warnings.
     */
    public List<Warning> getWarnings() {
        return warnings;
    }

    /**
     * <p>
     * Sets the list of warnings.
     * </p>
     *
     * @param warnings
     *            the list of warnings.
     */
    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    public void setResultCode(int code)
    {
        this.resultCode = code;
    }

    public int getResultCode()
    {
        return this.resultCode;
    }

    public void setMessage(String msg)
    {
        this.message = msg;
    }

    public String getMessage()
    {
        return this.message;
    }
}

