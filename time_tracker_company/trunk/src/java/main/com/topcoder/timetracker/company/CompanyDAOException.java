/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is thrown when a problem occurs when performing operations with the Company DAO. All layers throw
 * this exception: CompanyManager and implementations, EJBs, and the CompanyDAO and implementations.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe as it is immutable.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class CompanyDAOException extends BaseException {
    /**
     * <p>
     * The company that the DAO was working with when a problem occurred.
     * </p>
     *
     * <p>
     * It is immutable. Initialized In: Constructor (may be null) and accessed In: getProblemCompany.
     * </p>
     */
    private final Company problemCompany;

    /**
     * <p>
     * Constructor accepting a message and problem company.
     * </p>
     *
     * @param message The message of the exception.
     * @param problemCompany The company that the DAO was working on when the exception occurred.
     */
    public CompanyDAOException(String message, Company problemCompany) {
        super(message);
        this.problemCompany = problemCompany;
    }

    /**
     * <p>
     * Constructor accepting a message, cause and problem company.
     * </p>
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param problemCompany The company that the DAO was working on when the exception occurred.
     */
    public CompanyDAOException(String message, Throwable cause, Company problemCompany) {
        super(message, cause);
        this.problemCompany = problemCompany;
    }

    /**
     * <p>
     * Retrieves the company that the DAO was working with when a problem occurred.
     * </p>
     *
     * @return the company that the DAO was working with when a problem occurred.
     */
    public Company getProblemCompany() {
        return this.problemCompany;
    }
}
