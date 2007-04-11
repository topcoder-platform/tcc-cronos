/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.ejb;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RatePersistenceException;

import javax.ejb.EJBLocalObject;


/**
 * <p>This is a local interface for the contract for CRUD operations on rate persistence data. This interface
 * will be used in implementing a stateless session ejb with container manager transactions.</p>
 * <p>Thread-Safety</p>
 * <p>Since this is a stateless session bean the container will ensure that only a single thread has access to
 * this bean at any given time. Thus any issues with thread-safety are not really relevant.</p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 3.2
  */
public interface LocalRate extends EJBLocalObject {
    /**
     * <p>Adds a collection of rates into persistence. In addition, this method may optionally add audit
     * records for each persistence action taken.</p>
     *
     * @param rates The array of Rates to add - this cannot be null, or contains null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     * @throws IllegalArgumentException if the array of rates is null, or empty or contains null values.
     */
    public void addRates(Rate[] rates, boolean audit) throws RatePersistenceException;

    /**
     * <p>Removes a collection of rates from persistence, identified by the passed rate Ids. In addition, this
     * method may optionally add audit records for each persistence action taken.</p>
     *
     * @param rates The array of Rates to removed - this cannot be null, or contains null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems deleting the rates.
     * @throws IllegalArgumentException if the array of rates is null, or empty or contains null values.
     */
    public void deleteRates(Rate[] rates, boolean audit) throws RatePersistenceException;

    /**
     * <p>Given the id of the rate type, as well as the id of the company that uses the rate, this returns the
     * rate instance matching the given parameters. If no match is found, null is returned, and no exception is
     * thrown..</p>
     *
     * @param rateId The ID of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id pair, or null if no match is found.
     * @throws RatePersistenceException if there are problems in the persistence
     */
    public Rate retrieveRate(long rateId, long companyId) throws RatePersistenceException;

    /**
     * <p>Given the description of the rate type, as well as the id of the company that uses the rate, this
     * returns the rate instance matching the given parameters. If no match is found, null is returned, and no
     * exception is thrown.</p>
     *
     * @param description The description of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id/description pair, or null if no match is found.
     *
     * @throws IllegalArgumentException If the description is null.
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(String description, long companyId) throws RatePersistenceException;

    /**
     *  <p>This is a delegation method contract which retrieves all rates used by a company from persistence.
     * They are returned as an array of (non-null) Rate instances, this array can be empty but will not be null</p>
     *
     * @param companyId The Id of the company using the rates.
     *
     * @return An array of Rates used by the given company.
     *
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate[] retrieveRates(long companyId) throws RatePersistenceException;

    /**
     * <p>Updates a collection of rates within the persistence. This overrides the information currently
     * related to the rate's ID, with the new information given. In addition, this method may optionally add audit
     * records for each persistence action taken.</p>
     *
     * @param rates The array of Rates to update - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     * @throws IllegalArgumentException if the array of rates is null, or contains null values or it's empty
     */
    public void updateRates(Rate[] rates, boolean audit) throws RatePersistenceException;
}
