/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

/**
 * <p>This interface defines the common contract that EntryDelegate, EntrySessionBean, and EntryLocalObject
 * all implement.These common methods are the main public functionality of the component.</p> <p> The methods
 * defined by this interface are basic CRUD methods for CutoffTimes, and a method to determine whether a
 * BaseEntry can be submitted.</p>
 * <p>Implementations of this interface are required to be thread safe.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
  */
public interface EntryManager {
    /**
     * <p>This is a simple check if the given entry can be submitted. In order to determine if an entry can be
     * submitted it must be submitted before the cut off day of the week and time.&nbsp; Once this time is reached on
     * the day of the week assigned as the cut off day, entries can no longer be entered for the previous week, ending
     * in Sunday.&nbsp; Each company should be able to configure their own cut off day and time.</p>
     *
     * @param entry non -null entry to check
     *
     * @return true if entry can be submitted and false otherwise
     *
     * @throws IllegalArgumentException if entry is null
     * @throws EntryManagerException if there were any issues in the persistence layer
     */
    public boolean canSubmitEntry(BaseEntry entry) throws EntryManagerException;

    /**
     * <p>This is a contract method for creation of a new cut off time record (in the persistence store).</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null
     * @throws EntryManagerException if there were any issues in the persistence layer
     */
    public void createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException;

    /**
     * <p>This is a method for deletion of a an existing cut off time record (in the persistence store).</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null
     * @throws EntryManagerException if there were any issues in the persistence layer
     */
    public void deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException;

    /**
     * <p>This is a&nbsp; method for reading of a an existing cut off time record (in the persistence store) by
     * company id.</p>
     *
     * @param companyId company id
     *
     * @return CutoffTimeBean data bean
     *
     * @throws EntryManagerException if there were any issues in the persistence layer
     * @throws IllegalArgumentException if companyId is &lt;=0
     */
    public CutoffTimeBean fetchCutoffTimeByCompanyID(long companyId)
        throws EntryManagerException;

    /**
     * <p>This is a method for reading of a an existing cut off time record (in the persistence store) by
     * actual record PK.</p>
     *
     * @param cutoffTimeId cutoff time id
     *
     * @return CutoffTimeBean data bean
     *
     * @throws EntryManagerException if there were any issues in the persistence layer
     * @throws IllegalArgumentException if cutoffTimeId is &lt;=0
     */
    public CutoffTimeBean fetchCutoffTimeById(long cutoffTimeId)
        throws EntryManagerException;

    /**
     * <p>This is a&nbsp; method for reading of a an existing cut off time record (in the persistence store) by
     * company id.</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null
     * @throws EntryManagerException if there were any issues in the persistence layer
     */
    public void updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException;
}
