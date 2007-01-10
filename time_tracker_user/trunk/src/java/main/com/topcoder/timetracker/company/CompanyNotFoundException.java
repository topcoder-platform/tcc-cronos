/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.company;

/**
 * <p>
 * This exception is thrown if the involved company was not found in the data store. This is thrown during
 * update/delete methods, and their batch correspondents.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class CompanyNotFoundException extends CompanyDAOException {

    /**
     * <p>
     * Constructor accepting a message, cause and problem company.
     * </p>
     *
     *
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param problemCompany The company that the DAO was working on when the exception occurred.
     * @throws IllegalArgumentException if the message is null or an empty String.
     */
    public CompanyNotFoundException(String message, Throwable cause, Company problemCompany) {
        super(message, cause, problemCompany);
    }
}
