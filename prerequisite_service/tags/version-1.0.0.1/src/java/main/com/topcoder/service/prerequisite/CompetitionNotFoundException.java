/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.xml.ws.WebFault;

import com.topcoder.service.prerequisite.ejb.CompetitionNotFoundFault;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when a competition is not found. It is related
 * with "competition_not_found_faultMsg" fault in WSDL, but it contains only annotations about
 * "competition_not_found_fault" because the messages are automatically constructed.
 * </p>
 * <p>
 * The constructors with <code>{@link CompetitionNotFoundFault}</code> and the getter are necessary for the correct
 * webservices serialization/deserialization. The other constructors are added for the future implementations of the
 * service interface.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "competition_not_found_fault",
    faultBean = "com.topcoder.service.prerequisite.ejb.CompetitionNotFoundFault")
public class CompetitionNotFoundException extends PrerequisiteServiceException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8353694402003575464L;

    /**
     * <p>
     * Represents the fault info, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private final CompetitionNotFoundFault faultInfo;

    /**
     * <p>
     * Represents the competition id not found.
     * </p>
     */
    private final long competitionIdNotFound;

    /**
     * <p>
     * Creates a <code>CompetitionNotFoundException</code> instance with error message and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the competition id not found.
     */
    public CompetitionNotFoundException(String message, CompetitionNotFoundFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.competitionIdNotFound = -1;
    }

    /**
     * <p>
     * Creates a <code>CompetitionNotFoundException</code> instance with error message, cause exception and fault
     * info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the competition id that is not found
     * @param cause
     *            the cause exception
     */
    public CompetitionNotFoundException(String message, CompetitionNotFoundFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.competitionIdNotFound = -1;
    }

    /**
     * <p>
     * Creates a <code>CompetitionNotFoundException</code> instance with error message and the competition id not
     * found.
     * </p>
     *
     * @param message
     *            the error message
     * @param competitionIdNotFound
     *            the competition id not found
     */
    public CompetitionNotFoundException(String message, long competitionIdNotFound) {
        super(message);
        this.faultInfo = null;
        this.competitionIdNotFound = competitionIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>CompetitionNotFoundException</code> instance with error message, inner cause and the
     * competition id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param competitionIdNotFound
     *            the competition id not found
     */
    public CompetitionNotFoundException(String message, Throwable cause, long competitionIdNotFound) {
        super(message, cause);
        this.faultInfo = null;
        this.competitionIdNotFound = competitionIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>CompetitionNotFoundException</code> instance with error message, exception data and the
     * competition id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param data
     *            the exception data
     * @param competitionIdNotFound
     *            the competition id not found
     */
    public CompetitionNotFoundException(String message, ExceptionData data, long competitionIdNotFound) {
        super(message, data);
        this.faultInfo = null;
        this.competitionIdNotFound = competitionIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>CompetitionNotFoundException</code> instance with error message, exception data, inner cause
     * and the competition id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     * @param competitionIdNotFound
     *            the competition id not found.
     */
    public CompetitionNotFoundException(String message, Throwable cause, ExceptionData data,
        long competitionIdNotFound) {
        super(message, cause, data);
        this.faultInfo = null;
        this.competitionIdNotFound = competitionIdNotFound;
    }

    /**
     * <p>
     * Gets the competition id not found.
     * </p>
     *
     * @return the competition id not found.
     */
    public long getCompetitionIdNotFound() {
        return competitionIdNotFound;
    }

    /**
     * <p>
     * Gets the fault bean.
     * </p>
     * <p>
     * <b>Notes</b>, this method is necessary for the serialization/deserialization.
     * </p>
     *
     * @return the fault bean, possibly null.
     */
    public CompetitionNotFoundFault getFaultInfo() {
        return faultInfo;
    }
}
