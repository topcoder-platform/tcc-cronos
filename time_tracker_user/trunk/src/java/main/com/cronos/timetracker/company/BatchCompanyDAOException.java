/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.company;

import com.cronos.timetracker.common.Utils;

/**
 * <p>
 * This exception is thrown when a problem occurs when performing operations with the Company DAO in non-atomic
 * Batch Mode.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class BatchCompanyDAOException extends CompanyDAOException {

    /**
     * <p>
     * The set of companies where a problem occurred during processing. The number of elements in this array and
     * the causes array should be equal, and the element in each array corresponds to the element in the other
     * array with the same index. (ie, problemCompanies[1] corresponds to causes[1])
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: getProblemCompanies
     * </p>
     *
     *
     */
    private final Company[] problemCompanies;

    /**
     * <p>
     * The set of causes for the problems that occurred during processing. The number of elements in this array and
     * the problemCompanies array should be equal, and the element in each array corresponds to the element in the
     * other array with the same index. (ie, problemCompanies[1] corresponds to causes[1])
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Accessed In: getCauses
     * </p>
     *
     */
    private final Throwable[] causes;

    /**
     * <p>
     * Constructor accepting a message, causes and companies
     * </p>
     * <p>
     * The first element of each array is provided as the argument to the respective super constructor.
     * </p>
     *
     *
     * @param message The message of the exception.
     * @param causes A set of causes for the problems that occurred during processing.
     * @param companies A set of companies that the DAO was working on when the problem occurred.
     * @throws IllegalArgumentException if the message is null or an empty String, or if any parameter is null, or
     *         if companies contains null elements, or the size of both arrays are not equal.
     */
    public BatchCompanyDAOException(String message, Throwable[] causes, Company[] companies) {
        super(message, Utils.getFirstThrowable(causes), getCompany(companies));
        if (causes.length != companies.length) {
            throw new IllegalArgumentException("Different arrays sizes.");
        }
        this.causes = causes;
        this.problemCompanies = companies;

    }

    /**
     * <p>
     * Returns the first element from the array, if any exists.
     * </p>
     *
     * @param companies the companies array.
     * @return the first company element, or <code>null</code> if array is empty.
     */
    private static Company getCompany(Company[] companies) {
        Utils.checkNull(companies, "companies");
        for (int i = 0; i < companies.length; i++) {
            if (companies[i] == null) {
                throw new IllegalArgumentException("The users array contains null element.");
            }
        }

        if (companies.length > 0) {
            return companies[0];
        }

        return null;
    }

    /**
     * <p>
     * Retrieves the set of companies where a problem occurred during processing.
     * </p>
     *
     *
     * @return the set of companies where a problem occurred during processing.
     */
    public Company[] getProblemCompanies() {
        return problemCompanies;
    }

    /**
     * <p>
     * Retrieves the causes of the problems that occurred during processing.The number of elements in this array
     * and the problemCompanies array should be equal, and the element in each array corresponds to the element in
     * the other array with the same index. (ie, problemCompanies[1] corresponds to causes[1] and vice versa)
     * </p>
     *
     *
     *
     * @return The causes of the problems that occurred during processing
     */
    public Throwable[] getCauses() {
        return causes;
    }
}
