/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is thrown when a problem occurs when performing operations with the Company DAO in non-atomic Batch
 * Mode. All layers throw this exception: CompanyManager and implementations, EJBs, and the CompanyDAO and
 * implementations.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe as it is immutable.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class BatchCompanyDAOException extends BaseException {
    /**
     * <p>
     * The set of companies where a problem occurred during processing. The number of elements in this array and the
     * causes array should be equal, and the element in each array corresponds to the element in the other array with
     * the same index. (ie, problemCompanies[1] corresponds to causes[1]).
     * </p>
     *
     * <p>
     * This variable is immutable. Initialized In: Constructor and accessed In: getProblemCompanies.
     * </p>
     */
    private final Company[] problemCompanies;

    /**
     * <p>
     * The set of causes for the problems that occurred during processing. The number of elements in this array and the
     * problemCompanies array should be equal, and the element in each array corresponds to the element in the other
     * array with the same index. (ie, problemCompanies[1] corresponds to causes[1]).
     * </p>
     *
     * <p>
     * This variable is immutable. Initialized In: Constructor and accessed In: getCauses.
     * </p>
     */
    private final Throwable[] causes;

    /**
     * <p>
     * Constructor accepting a message and companies which means there is no error cause for each company of the input
     * array.
     * </p>
     *
     * @param message The message of the exception.
     * @param companies A set of companies that the DAO was working on when the problem occurred.
     */
    public BatchCompanyDAOException(String message, Company[] companies) {
        this(message, new Throwable[companies.length], companies);
    }

    /**
     * <p>
     * Constructor accepting a message, causes and companies.
     * </p>
     *
     * @param message The message of the exception.
     * @param causes A set of causes for the problems that occurred during processing.
     * @param companies A set of companies that the DAO was working on when the problem occurred.
     */
    public BatchCompanyDAOException(String message, Throwable[] causes, Company[] companies) {
        super(message);
        this.problemCompanies = companies;
        this.causes = causes;
    }

    /**
     * <p>
     * Retrieves the set of companies where a problem occurred during processing. Note, the return value is shallow
     * copied.
     * </p>
     *
     * @return the set of companies where a problem occurred during processing.
     */
    public Company[] getProblemCompanies() {
        return (Company[]) problemCompanies.clone();
    }

    /**
     * <p>
     * Retrieves the causes of the problems that occurred during processing.
     * </p>
     *
     * @return The causes of the problems that occurred during processing.
     */
    public Throwable[] getCauses() {
        return causes;
    }
}
