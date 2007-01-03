/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.company;

import com.cronos.timetracker.common.Utils;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown when a problem occurs when performing operations with the Company DAO.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class CompanyDAOException extends BaseException {

    /**
     * <p>
     * The company that the DAO was working with when a problem occurred.
     * </p>
     * <p>
     * Initialized In: Constructor (may be null).
     * </p>
     * <p>
     * Accessed In: getProblemCompany
     * </p>
     *
     */
    private final Company problemCompany;

    /**
     * <p>
     * Constructor accepting a message, cause and problem company.
     * </p>
     *
     *
     * @param message The message of the exception.
     * @param cause The cause of the exception.
     * @param problemCompany The company that the DAO was working on when the exception occurred.
     * @throws IllegalArgumentException if the message is null or an empty String.
     */
    public CompanyDAOException(String message, Throwable cause, Company problemCompany) {
        super(message, cause);
        Utils.checkString(message, "message", false);
        this.problemCompany = problemCompany;
    }

    /**
     * <p>
     * Retrieves the company that the DAO was working with when a problem occurred.
     * </p>
     *
     *
     * @return the company that the DAO was working with when a problem occurred.
     */
    public Company getProblemCompany() {
        return problemCompany;
    }
}
