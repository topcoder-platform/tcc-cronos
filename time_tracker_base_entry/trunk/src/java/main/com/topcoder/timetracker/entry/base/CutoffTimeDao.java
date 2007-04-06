/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

/**
 * <p>This is a general CRUD interface for cut off date/time persistence.</p>
 * <p>Thread Safety:Implementations should be thread-safe.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
  */
public interface CutoffTimeDao {
    /**
     * <p>This is a contract method for creation of a new cut off time record in the persistence store.</p>
     *
     * @param cutoffTimeBean the java bean encapsulating the record data to create
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null
     * @throws DuplicateEntryException if the id for that entry already exists
     * @throws PersistenceException if any other persistence error occurs
     */
    public void createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws PersistenceException;

    /**
     * <p>This is a contract method for deletion of a an existing cut off time record in the persistence store.</p>
     *
     * @param cutoffTimeBean the java bean encapsulating the record data to delete
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null or its id is not set(&lt;=0)
     * @throws EntryNotFoundException if the the entry does not exist in the database
     * @throws PersistenceException if any other persistence error occurs
     */
    public void deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws PersistenceException;

    /**
     * <p>This is a contract method for reading of a an existing cut off time record in the persistence store
     * by company id.</p>
     *
     * @param companyId company id
     *
     * @return Fetched record or null if not found
     *
     * @throws IllegalArgumentException if id is &lt;=0
     * @throws PersistenceException if any other persistence error occurs
     */
    public CutoffTimeBean fetchCutoffTimeByCompanyID(long companyId)
        throws PersistenceException;

    /**
     * <p>This is a contract method for reading of a an existing cut off time record in the persistence store
     * by actual record PK.</p>
     *
     * @param cutoffTimeId actual record id of the cut off time table
     *
     * @return Fetched record or null if not found
     *
     * @throws IllegalArgumentException if id is &lt;=0
     * @throws PersistenceException if any other persistence error occurs
     */
    public CutoffTimeBean fetchCutoffTimeById(long cutoffTimeId)
        throws PersistenceException;

    /**
     * <p>This is a contract method for updating of a an existing cut off time record in the persistence store.</p>
     *
     * @param cutoffTimeBean the java bean encapsulating the record data to update
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null or its id is not set(&lt;=0)
     * @throws EntryNotFoundException if the the entry does not exist in the database
     * @throws PersistenceException if any other persistence error occurs
     */
    public void updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws PersistenceException;
}
