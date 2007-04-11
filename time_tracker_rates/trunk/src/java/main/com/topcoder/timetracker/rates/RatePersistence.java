/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

/**
 * Interface defining the necessary methods for any pluggable persistence layer for use with the Rates component.
 * Provided are four basic CRUD operations for batches of rates (add/update/delete/retrieveRates) as well as two
 * additional methods that allow single rates to be obtained, identified by company and ID or description. It is
 * up to each implementation to define how much information it loads as well as when it throws exceptions.Each
 * implementing class should be able to be constructed by using the Object Factory component. In addition, it
 * is assumed that each implementing class will handle its own thread safety.
 *
 * @author AleaActaEst, sql_lall, TCSDEVELOPER
 * @version 3.2
  */
public interface RatePersistence {
    /**
     * Adds a collection of rates into persistence. In addition, this method may optionally add audit records
     * for each persistence action taken.
     *
     * @param rates The array of Rates to add - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     * @throws IllegalArgumentException if the array of rates is null, empty, or contains null values.
     */
    public void addRates(Rate[] rates, boolean audit) throws RatePersistenceException;

    /**
     * Removes a collection of rates from persistence, identified by the passed rate Ids. In addition, this
     * method may optionally add audit records for each persistence action taken.
     *
     * @param rates The array of Rates to removed - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     * @throws IllegalArgumentException if the array of rates is null, empty, or contains null values.
     */
    public void deleteRates(Rate[] rates, boolean audit)
        throws RatePersistenceException;

    /**
     * Given the id of the rate type, as well as the id of the company that uses the rate, this returns the
     * rate instance matching the given parameters. If no match is found, null is returned, and no exception is
     * thrown.
     *
     * @param rateId The ID of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id pair, or null if no match is found.
     *
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(long rateId, long companyId)
        throws RatePersistenceException;

    /**
     * Given the description of the rate type, as well as the id of the company that uses the rate, this
     * returns the rate instance matching the given parameters. If no match is found, null is returned, and no
     * exception is thrown.
     *
     * @param description The description of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id/description pair, or null if no match is found.
     *
     * @throws IllegalArgumentException If the description is null.
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(String description, long companyId)
        throws RatePersistenceException;

    /**
     * Retrieves all rates used by a company from persistence. They are returned as an array of (non-null) Rate
     * instances, this array can be empty but will not be null.
     *
     * @param companyId The Id of the company using the rates.
     *
     * @return An array of Rates used by the given company, this array can be empty but will not be null
     *
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate[] retrieveRates(long companyId) throws RatePersistenceException;

    /**
     * Updates a collection of rates within the persistence. This overrides the information currently related
     * to the rate's ID, with the new information given. In addition, this method may optionally add audit records for
     * each persistence action taken.
     *
     * @param rates The array of Rates to update - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     * @throws IllegalArgumentException if the array of rates is null, empty, or contains null values.
     */
    public void updateRates(Rate[] rates, boolean audit)
        throws RatePersistenceException;
}
