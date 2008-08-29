/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.clients.model.Company;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is used to indicate any error that occurs in CompanyManager's business methods, for example, an error
 * thrown by underlying CompanyDAO implementation will be caught and wrapped in this exception and re-thrown.
 * It extends from ManagerException.
 *
 * It has a field that can be used to carry a Company instance when the exception is constructed, user can retrieve the
 * company by related getter, it helps user diagnose the exception.
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class CompanyManagerException extends ManagerException {
    /**
     * Represents a Company instance. Typically, when an operation fails, the project entity it operates on will be
     * stored in this field to let the user diagnose the error.
     *
     * It's set in the constructor, can be null and has a getter.
     */
    private final Company company;

    /**
     * Constructs an instance of this exception with specified Company object.
     *
     * @param company
     *            the Company instance associated with this error, can be null
     */
    public CompanyManagerException(Company company) {
        this.company = company;
    }

    /**
     * Constructs an instance of this exception with given error message and Company object.
     *
     * @param message
     *            the error message
     * @param company
     *            the Company instance associated with this error, can be null
     */
    public CompanyManagerException(String message, Company company) {
        super(message);
        this.company = company;
    }

    /**
     * Constructs an instance of this exception with given error message, inner cause and Company object.
     *
     * @param message
     *            the error message
     * @param cause
     *            the inner error
     * @param company
     *            the Company instance associated with this error, can be null
     */
    public CompanyManagerException(String message, Throwable cause, Company company) {
        super(message, cause);
        this.company = company;
    }

    /**
     * Constructs an instance of this exception with given error message, inner cause, exception data and Company
     * object.
     *
     *
     * @param message
     *            the error message
     * @param cause
     *            the inner error
     * @param exceptionData
     *            an object carrying the details of this exception
     * @param company
     *            the company instance associated with this error, can be null
     */
    public CompanyManagerException(String message, Throwable cause, ExceptionData exceptionData, Company company) {
        super(message, cause, exceptionData);
        this.company = company;
    }

    /**
     * Gets the Company instance related to this exception.
     *
     * @return Company instance, can be null
     */
    public Company getCompany() {
        return company;
    }
}
