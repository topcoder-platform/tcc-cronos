/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

/**
 * <p>
 * This exception is thrown if the involved company was not found in the data store. This is thrown during
 * update/delete methods, and their batch correspondents. All layers throw this exception: CompanyManager and
 * implementations, EJBs, and the CompanyDAO and implementations.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe as it is immutable.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class CompanyNotFoundException extends CompanyDAOException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 5372741298428699968L;

	/**
     * <p>
     * Constructor accepting a message and problem company.
     * </p>
     *
     * @param message The message of the exception.
     * @param problemCompany The company that the DAO was working on when the exception occurred.
     */
    public CompanyNotFoundException(String message, Company problemCompany) {
        super(message, problemCompany);
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
    public CompanyNotFoundException(String message, Throwable cause, Company problemCompany) {
        super(message, cause, problemCompany);
    }
}
