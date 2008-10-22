/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.service.studio.ejb.ContestNotFoundFault;
import com.topcoder.util.errorhandling.ExceptionData;

import javax.xml.ws.WebFault;

/**
 * <p> This exception is thrown automatically by the web service interface when a contest is not found. It is related
 * with "contest_not_found_faultMsg" fault in WSDL, but it contains only annotations about "contest_not_found_fault"
 * because the messages are automatically constructed. It was generated by JBoss tools and I changed the name, the
 * inheritance and other changes.</p>
 *
 * <p> The constructors with ContestNotFoundFault and the getter are necessary for the correct webservices
 * serialization/deserialization. The other constructors are added for the future implementations of the service
 * interface.</p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "contest_not_found_fault", faultBean = "com.topcoder.service.studio.ejb.ContestNotFoundFault")
public class ContestNotFoundException extends StudioServiceException {
    /**
     * <p> Represents the faultInfo, Java type that goes as soapenv:Fault detail element.</p><p> defined in constructor
     * null, means that is not set</p>
     */
    private final ContestNotFoundFault faultInfo;

    /**
     * <p> Represents the contestNotFound. I updated the WSDL, now it's an long. It is retrieved from
     * ContestNotFoundFault.</p>
     */
    private final long contestIdNotFound;

    /**
     * <p> Construct the exception. Call the super constructor,set the fault info and set the contestIdNotFound from the
     * faultInfo.</p>
     *
     * @param message   the message
     * @param faultInfo the fault info which contains the contestIdNotFound
     */
    public ContestNotFoundException(String message, ContestNotFoundFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.contestIdNotFound = (faultInfo == null) ? -1 : faultInfo.getContestIdNotFound();
    }

    /**
     * <p> Construct the exception. Call the super constructor,set the fault info and set the contestIdNotFound from the
     * faultInfo.</p>
     *
     * @param message   the message
     * @param faultInfo the fault info which contains the contestIdNotFound
     * @param cause     the cause
     */
    public ContestNotFoundException(String message, ContestNotFoundFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.contestIdNotFound = (faultInfo == null) ? -1 : faultInfo.getContestIdNotFound();
    }

    /**
     * <p>Constructor with error message.</p>
     *
     * @param message           the error message
     * @param contestIdNotFound the contest's id not found
     */
    public ContestNotFoundException(String message, long contestIdNotFound) {
        super(message);
        this.contestIdNotFound = contestIdNotFound;
        this.faultInfo = null;
    }

    /**
     * <p>Constructor with error message and inner cause.</p>
     *
     * @param message           the error message
     * @param cause             the cause of this exception
     * @param contestIdNotFound the contest's id not found
     */
    public ContestNotFoundException(String message, Throwable cause, long contestIdNotFound) {
        super(message, cause);
        this.contestIdNotFound = contestIdNotFound;
        this.faultInfo = null;
    }

    /**
     * <p>Constructor with error message and exception data</p>
     *
     * @param message           the error message
     * @param contestIdNotFound the contest's id not found
     * @param data              the exception data
     */
    public ContestNotFoundException(String message, ExceptionData data, long contestIdNotFound) {
        super(message, data);
        this.contestIdNotFound = contestIdNotFound;
        this.faultInfo = null;
    }

    /**
     * <p>Constructor with error message and inner cause and exception data</p>
     *
     * @param message           the error message
     * @param cause             the cause of this exception
     * @param data              the exception data
     * @param contestIdNotFound the contest's id not found
     */
    public ContestNotFoundException(String message, Throwable cause, ExceptionData data, long contestIdNotFound) {
        super(message, cause, data);
        this.contestIdNotFound = contestIdNotFound;
        this.faultInfo = null;
    }

    /**
     * <p> Return the contestId NotFound</p>
     *
     * @return the contestId NotFound
     */
    public long getContestIdNotFound() {
        return contestIdNotFound;
    }

    /**
     * <p> Return the fault bean. This method is necessary for the serialization/deserialization. it returns null if the
     * constructors without fault bean are used.</p>
     *
     * @return returns fault bean
     */
    public ContestNotFoundFault getFaultInfo() {
        return faultInfo;
    }
}

