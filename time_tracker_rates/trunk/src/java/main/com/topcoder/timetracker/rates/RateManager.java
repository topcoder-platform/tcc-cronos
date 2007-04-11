/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.topcoder.timetracker.rates.ejb.LocalHomeRate;
import com.topcoder.timetracker.rates.ejb.LocalRate;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * <p>This class acts as a business delegate. A RateManager is the entrypoint into the storage and retrieval of
 * rates within the system - it provides the basic CRUD methods, for both single and multiple Rates. Each method
 * within is delegated to a pluggable transaction controlled stateless session EJB which then delegates the requests
 * to persistence layer, the type of which is determined via configuration.</p>
 *  <p>Thread Safety: all of the methods are simply delegate calls to ejb, and ejb is ensured by container to be
 * thread-safe.</p>
 *
 * @author AleaActaEst, sql_lall, TCSDEVELOPER
 * @version 3.2
 */
public class RateManager {
    /**
     * The default namespace to use for this component. In this case, the value is the package name:
     * "com.topcoder.timetracker.rates" This is public, static and immutable, and used within the no-argument
     * constructor.
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.timetracker.rates";

    /** Property name for ejb reference name in config manager. The property is used to obtain the EJB from jndi. */
    private static final String PROP_REF_NAME = "rate_local_ejb_reference_name";

    /**
     * <p>This is the delegate to the persistence layer for this manager. It must be non-null and is set
     * through either of the constructors. It is used all the CRUD methods in this manager. This is initialized in one
     * of the constructors and is based on a pre-configured JDNI property which we will read in this delegate from
     * configuration. This cannot be null and one initialized it is immutable.</p>
     */
    private final LocalRate rateLocalEjb;

    /**
     * Default, no-arg constructor that initializes the object using the default namespace.
     *
     * @throws RateConfigurationException
     *             If there are problems constructing the object using the default parameter.
     */
    public RateManager() throws RateConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Constructs a new Rate Manager instance taking a namespace location telling where configuration parameters can be
     * found. The LocalRate will be created based on the jndi property configured in ConfigManager.
     * </p>
     * @param namespace
     *            (non-null, non-empty) Namespace location of the configuration parameters.
     * @throws RateConfigurationException
     *             If there are problems loading or using the configuration details.
     * @throws IllegalArgumentException
     *             If the namespace provided is null or empty.
     */
    public RateManager(String namespace) throws RateConfigurationException {
        ParameterCheck.checkNullEmpty("namespace", namespace);

        String jndiLookupName = ConfigHelper.getStringProperty(namespace, PROP_REF_NAME, true);
        Context context;

        try {
            context = getInitialContext();
        } catch (NamingException e) {
            throw new RateConfigurationException("failed to create InitialContext", e);
        }

        LocalHomeRate rateHome;

        try {
            rateHome = (LocalHomeRate) context.lookup(jndiLookupName);
        } catch (NamingException e) {
            throw new RateConfigurationException("failed to obtain LocalHomeRate with name:" + jndiLookupName, e);
        }

        try {
            this.rateLocalEjb = rateHome.create();
        } catch (CreateException e) {
            throw new RateConfigurationException("failed to create LocalRate", e);
        }
    }

    /**
     * <p>Adds a single rate into the manager.</p>
     *
     * @param rate The single rate to be added.
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RateManagerException If there are problems in adding the rate
     * @throws IllegalArgumentException if the rate given is null
     */
    public void addRate(Rate rate, boolean audit) throws RateManagerException {
        ParameterCheck.checkNull("rate", rate);

        this.rateLocalEjb.addRates(new Rate[] {rate }, audit);
    }

    /**
     * Adds a collection of rates into the manager. Note that this will add as many rates as possible into
     * persistence, RateManagerException will be thrown if none of the rate can be added.
     *
     * @param rates The array of rates to add. This may not be null, or contain any null instances.
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RateManagerException If there are problems in adding the rates - wrapping any persistence exception
     *         thrown.
     * @throws IllegalArgumentException if the array of rates is null, empty,  or contains null values.
     */
    public void addRates(Rate[] rates, boolean audit) throws RateManagerException {
        ParameterCheck.checkArray("rates", rates);
        this.rateLocalEjb.addRates(rates, audit);
    }

    /**
     * Removes a single rate from the manager.
     *
     * @param rate The single rate to be deleted
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RateManagerException If there are problems in removing the rate
     * @throws IllegalArgumentException if the rate given is null
     */
    public void deleteRate(Rate rate, boolean audit) throws RateManagerException {
        ParameterCheck.checkNull("rate", rate);
        this.rateLocalEjb.deleteRates(new Rate[] {rate }, audit);
    }

    /**
     * Removes a collection of rates from the manager. Note that this will delete as many rates as possible
     * from persistence, RateManagerException will be thrown if none of the rate can be deleted.
     *
     * @param rates The array of rates to delete. This may not be null, or contain any null instances.
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RateManagerException If there are problems in deleting the rates - wrapping any persistence exception
     *         thrown.
     * @throws IllegalArgumentException if the array of rates is null, empty, or contains null values.
     */
    public void deleteRates(Rate[] rates, boolean audit)
        throws RateManagerException {
        ParameterCheck.checkArray("rates", rates);
        this.rateLocalEjb.deleteRates(rates, audit);
    }

    /**
     * Retrieves a single rate from the manager.
     *
     * @param rateId The ID of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id pair, or null if no match is found.
     *
     * @throws RateManagerException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(long rateId, long companyId)
        throws RateManagerException {
        return this.rateLocalEjb.retrieveRate(rateId, companyId);
    }

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
     * @throws RateManagerException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(String description, long companyId)
        throws RateManagerException {
        ParameterCheck.checkNull("description", description);

        return this.rateLocalEjb.retrieveRate(description, companyId);
    }

    /**
     * Retrieves all rates used by a company from persistence. They are returned as an array of (non-null) Rate
     * instances, this array can be empty but will not be null.
     *
     * @param companyId The Id of the company using the rates.
     *
     * @return An array of Rates used by the given company.
     *
     * @throws RateManagerException If there are problems retrieving the rates from persistence
     */
    public Rate[] retrieveRates(long companyId) throws RateManagerException {
        return this.rateLocalEjb.retrieveRates(companyId);
    }

    /**
     * Utility method that updates a single rate in the manager.
     *
     * @param rate The single rate to be updated
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RateManagerException If there are problems in updating the rate
     * @throws IllegalArgumentException if the rate given is null
     */
    public void updateRate(Rate rate, boolean audit) throws RateManagerException {
        ParameterCheck.checkNull("rate", rate);
        this.rateLocalEjb.updateRates(new Rate[] {rate }, audit);
    }

    /**
     * Updates a collection of rates within the manager. Note that this will update as many rates as possible
     * into persistence, RateManagerException will be thrown if none of the rate can be updated.
     *
     * @param rates The array of rates to update. This may not be null, or contain any null instances.
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RateManagerException If there are problems in updating the rates - wrapping any persistence exception
     *         thrown.
     * @throws IllegalArgumentException if the array of rates is null, or contains null values.
     */
    public void updateRates(Rate[] rates, boolean audit)
        throws RateManagerException {
        ParameterCheck.checkArray("rates", rates);
        this.rateLocalEjb.updateRates(rates, audit);
    }

    /**
     * Helper method to obtain the Context. Sub class can override this method to add more properties.
     *
     * @return Context
     *
     * @throws NamingException if failed to get the context
     */
    protected Context getInitialContext() throws NamingException {
        return new InitialContext();
    }
}
