/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.db.Util;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

/**
 * <p>
 * This is a default implementation of the <code>TimeEntryManager</code> interface.
 * </p>
 *
 * <p>
 * It utilizes instances of the <code>TimeEntryDAO</code> in order to fulfill the necessary CRUDE
 * and search operations defined for the Time Tracker TimeEntries.
 * </p>
 *
 * <p>
 * It also uses <code>TimeEntryRejectReasonDAO</code> and <code>RejectReasonManager</code> to
 * associate any RejectReasons with the TimeEntries.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is dependent on the DAO implementations. Since the DAOs
 * are required to be thread-safe, this class is thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TimeEntryManagerImpl implements TimeEntryManager {
    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and
     * modify the persistent store when dealing with the Time Tracker Entries.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It cannot be null.
     * </p>
     */
    private final TimeEntryDAO timeEntryDao;

    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and
     * modify the persistent store when dealing with the relationship between
     * the RejectReason and TimeEntries.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It cannot be null.
     * </p>
     */
    private final TimeEntryRejectReasonDAO timeEntryRejectReasonDao;

    /**
     * <p>
     * This is the Manager instance used to retrieve reject reasons from the persistent store.
     * </p>
     *
     * <p>
     * It is use to check that the company ids of any related reject reasons match with the company
     * id of the time entry.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It cannot be null.
     * </p>
     */
    private final RejectReasonManager rejectReasonManager;

    /**
     * <p>
     * Constructor that accepts the necessary parameters to initialize the <code>TimeEntryManagerImpl</code>.
     * </p>
     *
     * @param timeEntryDao The dao used to manipulate time entries.
     * @param timeEntryRejectReasonDao The dao used to manipulate relationship between time entry and reject reason.
     * @param rejectReasonManager The manager used to retrieve reject reason data.
     *
     * @throws IllegalArgumentException if any parameter is null.
     */
    public TimeEntryManagerImpl(TimeEntryDAO timeEntryDao, TimeEntryRejectReasonDAO timeEntryRejectReasonDao,
        RejectReasonManager rejectReasonManager) {
        Util.checkNull(timeEntryDao, "timeEntryDao");
        Util.checkNull(timeEntryRejectReasonDao, "timeEntryRejectReasonDao");
        Util.checkNull(rejectReasonManager, "rejectReasonManager");

        this.timeEntryDao = timeEntryDao;
        this.timeEntryRejectReasonDao = timeEntryRejectReasonDao;
        this.rejectReasonManager = rejectReasonManager;
    }

    /**
     * <p>
     * Defines a time entry to be recognized within the persistent store managed by this manager.
     * </p>
     *
     * <p>
     * A unique time entry id will automatically be generated and assigned to the time entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * It will set the TimeEntry's creation and modification date to the current date.
     * These creation/modification details will also reflect in the persistent store.
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null or has id != -1.
     * @throws InvalidCompanyException if the TaskType company id does not match with this one.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeEntry(TimeEntry entry, boolean audit) throws InvalidCompanyException,
        DataAccessException {
        try {
            createTimeEntries(new TimeEntry[] {entry}, audit);
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to create the time entry.");
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TimeEntry parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * It will set the Entry's modification details to the current date.
     * These modification details will also reflect in the persistent store.
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null or has an id &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws InvalidCompanyException if the TaskType company id does not match with this one.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeEntry(TimeEntry entry, boolean audit) throws UnrecognizedEntityException,
        InvalidCompanyException, DataAccessException {
        try {
            updateTimeEntries(new TimeEntry[] {entry}, audit);
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to update the time entry.");
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the time entry
     * with the specified id.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entryId The entryId for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entryId &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeEntry(long entryId, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        try {
            deleteTimeEntries(new long[] {entryId}, audit);
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to delete the time entry.");
        }
    }

    /**
     * <p>
     * Retrieves a TimeEntry object that reflects the data in the persistent store on the Time Tracker
     * Time Entry with the specified entryId.
     * </p>
     *
     * @param entrytId The id of the entry to retrieve.
     * @return The entry with specified id.
     *
     * @throws IllegalArgumentException if entryId &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry getTimeEntry(long entrytId) throws UnrecognizedEntityException, DataAccessException {
        try {
            return timeEntryDao.getTimeEntries(new long[] {entrytId})[0];
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to get the time entry.");

            // never reach here
            return null;
        }
    }

    /**
     * <p>
     * Searches the persistent store for any time entries that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>TimeEntryFilterFactory</code> returned by <code>getTimeEntryFilterFactory</code> of this
     * instance, or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created using
     * <code>TimeEntryFilterFactory</code>.
     * </p>
     *
     * @param filter The filter used to search for TimeEntry.
     * @return The time entries satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] searchTimeEntries(Filter filter) throws DataAccessException {
        return timeEntryDao.searchTimeEntries(filter);
    }

    /**
     * <p>
     * This is a batch version of the <code>createTimeEntry</code> method.
     * </p>
     *
     * @param timeEntries An array of time entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntries is null, empty or contains null values or
     * same reference or has ids != -1.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeEntries(TimeEntry[] timeEntries, boolean audit) throws BatchOperationException,
        DataAccessException {
        Util.updateTimeTrackerBeanDates(timeEntries, "timeEntries", true);

        Throwable[] causes = new Throwable[timeEntries.length];
        TimeEntry[] nextStepTimeEntries = getNextStepTimeEntries(timeEntries, causes);

        try {
            timeEntryDao.createTimeEntries(nextStepTimeEntries, audit);
        } catch (BatchOperationException e) {
            Util.updateCauses(causes, e);
        }

        Util.finishBatchOperation(causes);
    }

    /**
     * <p>
     * This method will get the time entries to be processed by the DAO.
     * </p>
     *
     * <p>
     * In this method, the company ids of time entry and task type are compared, if they are not the same,
     * the time entries will not be process and a <code>InvalidCompanyException</code> will be set as an inner
     * cause.
     * </p>
     *
     * @param timeEntries the time entries to process
     * @param causes the inner causes of the
     * @return the time entries to be process by the DAO
     *
     * @throws BatchOperationException if all the time entries have different company ids with their task types
     * @throws DataAccessException if any time entry has null task type association
     */
    private TimeEntry[] getNextStepTimeEntries(TimeEntry[] timeEntries, Throwable[] causes)
        throws BatchOperationException, DataAccessException {
        List newTimeEntries = new ArrayList();

        for (int i = 0; i < timeEntries.length; i++) {
            TaskType taskType = timeEntries[i].getTaskType();
            if (taskType == null) {
                throw new DataAccessException("Some time entries have null task type.");
            }

            if (taskType.getCompanyId() != timeEntries[i].getCompanyId()) {
                causes[i] = new InvalidCompanyException(timeEntries[i].getCompanyId(), taskType.getCompanyId());
            } else {
                newTimeEntries.add(timeEntries[i]);
            }
        }

        if (newTimeEntries.size() == 0) {
            throw new BatchOperationException(
                "All the time entries have the different company ids with their task types.", causes);
        }

        return (TimeEntry[]) newTimeEntries.toArray(new TimeEntry[newTimeEntries.size()]);
    }

    /**
     * <p>
     * This is a batch version of the <code>updateTimeEntry</code> method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeEntries An array of time entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntries is null, empty or contains null values or
     * same reference or has ids &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeEntries(TimeEntry[] timeEntries, boolean audit) throws BatchOperationException,
        DataAccessException {
        Util.updateTimeTrackerBeanDates(timeEntries, "timeEntries", false);

        Throwable[] causes = new Throwable[timeEntries.length];
        TimeEntry[] nextStepTimeEntries = getNextStepTimeEntries(timeEntries, causes);

        try {
            timeEntryDao.updateTimeEntries(nextStepTimeEntries, audit);
        } catch (BatchOperationException e) {
            Util.updateCauses(causes, e);
        }

        Util.finishBatchOperation(causes);
    }

    /**
     * <p>
     * This is a batch version of the <code>deleteTimeEntry</code> method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeEntryIds An array of timeEntryIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntryIds is null, empty or contains values &lt; 0 or equal
     * time entry ids.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeEntries(long[] timeEntryIds, boolean audit) throws BatchOperationException,
        DataAccessException {
        timeEntryDao.deleteTimeEntries(timeEntryIds, audit);
    }

    /**
     * <p>
     * This is a batch version of the <code>getTimeEntry</code> method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeEntryIds An array of timeEntryIds for which the operation should be performed.
     * @return The TimeEntries corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeEntryIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getTimeEntries(long[] timeEntryIds) throws BatchOperationException,
        DataAccessException {
        return timeEntryDao.getTimeEntries(timeEntryIds);
    }

    /**
     * <p>
     * This adds a <code>RejectReason</code> with the specified id to the specified TimeEntry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The TimeEntry for which the operation should be performed.
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws UnrecognizedEntityException if a rejectReason or TimeEntry was not found with specified id.
     * @throws InvalidCompanyException if the TaskType company id does not match with RejectReason company id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void addRejectReasonToEntry(TimeEntry entry, long rejectReasonId, boolean audit)
        throws UnrecognizedEntityException, InvalidCompanyException, DataAccessException {
        Util.checkNull(entry, "entry");

        entry = getTimeEntry(entry.getId());
        RejectReason rejectReason = checkRejectReason(rejectReasonId);
        if (rejectReason.getCompanyId() != entry.getTaskType().getCompanyId()) {
            throw new InvalidCompanyException(entry.getTaskType().getCompanyId(), rejectReason.getCompanyId());
        }

        timeEntryRejectReasonDao.addRejectReasonToEntry(rejectReasonId, entry, audit);
    }

    /**
     * <p>
     * This removes a <code>RejectReason</code> with the specified id from the specified <code>TimeEntry</code>.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The user for which the operation should be performed.
     * @param rejectReasonId The rejectReason to remove.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws UnrecognizedEntityException if a rejectReason or TimeEntry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeRejectReasonFromEntry(TimeEntry entry, long rejectReasonId, boolean audit)
        throws UnrecognizedEntityException, DataAccessException {
        Util.checkNull(entry, "entry");

        entry = getTimeEntry(entry.getId());
        checkRejectReason(rejectReasonId);

        timeEntryRejectReasonDao.removeRejectReasonFromEntry(rejectReasonId, entry, audit);
    }

    /**
     * <p>
     * This method retrieves the <code>RejectReason</code> instance according to the given <code>rejectReasonId</code>.
     * </p>
     *
     * @param rejectReasonId the reject reason id
     * @return the <code>RejectReason</code> instance according to the given <code>rejectReasonId</code>.
     *
     * @throws UnrecognizedEntityException if the reject reason can not be found
     * @throws DataAccessException if exception occurs when get the reject reason via the manager
     */
    private RejectReason checkRejectReason(long rejectReasonId) throws UnrecognizedEntityException,
        DataAccessException {
        Util.checkIdValue(rejectReasonId, "reject reason");

        try {
            RejectReason reason = rejectReasonManager.retrieveRejectReason(rejectReasonId);
            if (reason == null) {
                throw new UnrecognizedEntityException(rejectReasonId, "The reject reason entity with id ["
                    + rejectReasonId + "] cannot be found.");
            }

            return reason;
        } catch (RejectReasonDAOException e) {
            throw new DataAccessException("Unable to get the reject reason from the manager.", e);
        }
    }

    /**
     * <p>
     * This removes all RejectReasons from the specified TimeEntry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The TimeEntry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntry is null or has id &lt; 0
     * @throws UnrecognizedEntityException if the given TimeEntry was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeAllRejectReasonsFromEntry(TimeEntry entry, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        Util.checkNull(entry, "entry");

        entry = getTimeEntry(entry.getId());

        timeEntryRejectReasonDao.removeAllRejectReasonsFromEntry(entry, audit);
    }

    /**
     * <p>
     * This retrieves all RejectReasons for the specified <code>TimeEntry</code>.
     * </p>
     *
     * @param entry The TimeEntry to retrieve RejectReasons from.
     * @return An array of RejectReason ids for the given TimeEntry.
     *
     * @throws IllegalArgumentException if timeEntryId is &lt; 0
     * @throws UnrecognizedEntityException if a TimeEntry was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] getAllRejectReasonsForEntry(TimeEntry entry) throws UnrecognizedEntityException,
        DataAccessException {
        Util.checkNull(entry, "entry");

        long entryId = entry.getId();
        getTimeEntry(entryId);

        return timeEntryRejectReasonDao.getAllRejectReasonsForEntry(entryId);
    }

    /**
     * <p>
     * Retrieves all the TimeEntries that are currently in the persistent
     * store.
     * </p>
     *
     * @return An array of time entries retrieved from the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getAllTimeEntries() throws DataAccessException {
        return timeEntryDao.getAllTimeEntries();
    }

    /**
     * <p>
     * Retrieves the <code>TimeEntryFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for TimeEntries.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned
     * by the given factory should be used with this instance's <code>searchTimeEntries</code> method.
     * </p>
     *
     * @return the <code>TimeEntryFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for time entries.
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory() {
        return timeEntryDao.getTimeEntryFilterFactory();
    }
}
