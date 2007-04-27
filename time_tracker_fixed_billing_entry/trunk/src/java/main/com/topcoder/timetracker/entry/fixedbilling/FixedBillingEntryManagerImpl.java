/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

import java.sql.Timestamp;


/**
 * <p>
 * This is a default implementation of the <code>FixedBillingEntryManager</code> interface. It utilizes instances of
 * the <code>FixedBillingEntryDAO</code> in order to fulfill the necessary CRUDE and search operations defined for the
 * Time TrackerFixedBillingEntries. It also uses <code>FixedBillingEntryRejectReasonDAO</code> to associate any
 * RejectReasons with the FixedBillingEntries.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is dependent on the DAO implementations  Since the DAOs  are required to be
 * thread-safe, this class is thread safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class FixedBillingEntryManagerImpl implements FixedBillingEntryManager {
    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and  modify the persistent store when dealing with
     * the FixedBillingEntries.
     * </p>
     */
    private final FixedBillingEntryDAO fixedBillingEntryDAO;

    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and  modify the persistent store when dealing with
     * the relationship between the RejectReason and FixedBillingEntries.
     * </p>
     */
    private final FixedBillingEntryRejectReasonDAO fixedBillingEntryRejectReasonDAO;

    /**
     * <p>
     * This is the Manager instance used to retrieve reject reasons from the persistent store.  It may change based on
     * the final look of the Time Tracker Reject Reason component.  It is used to check that the company id of any
     * related reject reasons match with the company id of the FixedBillingEntry.
     * </p>
     */
    private final RejectReasonManager rejectReasonManager;

    /**
     * <p>
     * Constructor that accepts the necessary parameters to initialize the FixedBillingEntryManagerImpl.
     * </p>
     *
     * @param fixedBillingEntryDao The FixedBillingEntryDAO to use.
     * @param fixedBillingEntryRejectReasonDAO The FixedBillingEntryRejectReasonDAO to use.
     * @param rejectReasonManager The RejectReasonManager to use.
     *
     * @throws IllegalArgumentException if any argument is null.
     */
    public FixedBillingEntryManagerImpl(FixedBillingEntryDAO fixedBillingEntryDao,
        FixedBillingEntryRejectReasonDAO fixedBillingEntryRejectReasonDAO, RejectReasonManager rejectReasonManager) {
        Helper.checkNull("fixedBillingEntryDao", fixedBillingEntryDao);
        Helper.checkNull("fixedBillingEntryRejectReasonDAO", fixedBillingEntryRejectReasonDAO);
        Helper.checkNull("rejectReasonManager", rejectReasonManager);
        this.fixedBillingEntryDAO = fixedBillingEntryDao;
        this.fixedBillingEntryRejectReasonDAO = fixedBillingEntryRejectReasonDAO;
        this.rejectReasonManager = rejectReasonManager;
    }

    /**
     * <p>
     * Defines a fixed billing entry to be recognized within the persistent store managed by this utility. A unique
     * fixed billing entry id will automatically be generated and assigned to the entry. There is also the option to
     * perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Entry's creation and modification date to the current date.   These
     * creation/modification details will also reflect in the persistent store. The creation and modification user is
     * the responsibility of the calling application.
     * </p>
     *
     * <p>
     * The entity should be provided with no id set (id = -1).  Otherwise, the implementation should throw
     * IllegalArgumentException.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null, or has an invalid entry provided.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void createFixedBillingEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException {
        Helper.checkNull("entry", entry);
        Helper.checkRequiredColumn("entry.getFixedBillingStatus()", entry.getFixedBillingStatus());

        Timestamp current = new Timestamp(System.currentTimeMillis());
        entry.setModificationDate(current);
        entry.setCreationDate(current);
        entry.getFixedBillingStatus().setCreationDate(current);
        entry.getFixedBillingStatus().setModificationDate(current);
        fixedBillingEntryDAO.createFixedBillingEntries(new FixedBillingEntry[] {entry }, audit);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided FixedBillingEntry parameter.
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Entry's modification details to the current date.   These modification details
     * will also reflect in the persistent store. The modification user is the responsibility of the calling
     * application.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateFixedBillingEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException {
        Helper.checkNull("entry", entry);
        Helper.checkRequiredColumn("entry.getFixedBillingStatus()", entry.getFixedBillingStatus());

        Timestamp current = new Timestamp(System.currentTimeMillis());
        entry.setModificationDate(current);
        entry.getFixedBillingStatus().setModificationDate(current);
        fixedBillingEntryDAO.updateFixedBillingEntries(new FixedBillingEntry[] {entry }, audit);
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the fixed billing entry with the specified
     * id.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit. The creation and modification user is the responsibility of the
     * calling application.
     * </p>
     *
     * @param entryId The entryId for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entryId &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     */
    public void deleteFixedBillingEntry(long entryId, boolean audit)
        throws DataAccessException {
        //Should remove all reject reasons.
        removeAllRejectReasonsFromEntry(getFixedBillingEntry(entryId), audit);
        fixedBillingEntryDAO.deleteFixedBillingEntries(new long[] {entryId }, audit);
    }

    /**
     * <p>
     * Retrieves a FixedBillingEntry object that reflects the data in the persistent store with the specified entryId.
     * </p>
     *
     * @param entrytId The id of the entry to retrieve.
     *
     * @return The entry with specified id.
     *
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingEntry getFixedBillingEntry(long entrytId)
        throws DataAccessException {
        return fixedBillingEntryDAO.getFixedBillingEntries(new long[] {entrytId })[0];
    }

    /**
     * <p>
     * Searches the persistent store for any fixed billing entries  that satisfy the criteria that was specified in the
     * provided search filter.  The provided filter should be created using either the filters that are created using
     * the FixedBillingEntryFilterFactory returned by getFixedBillingEntryFilterFactory of this instance, or a
     * composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component) that
     * combines the filters created using FixedBillingEntryFilterFactory.
     * </p>
     *
     * @param filter The filter used to search for entries.
     *
     * @return The entries satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws InvalidFilterException if the filter cannot be recognized.
     */
    public FixedBillingEntry[] searchFixedBillingEntries(Filter filter)
        throws DataAccessException {
        return fixedBillingEntryDAO.searchFixedBillingEntries(filter);
    }

    /**
     * <p>
     * This is a batch version of the createFixedBillingEntry method.
     * </p>
     *
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param entries An array of entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public void createFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException {
        Helper.checkObjectArray("entries", entries);

        Timestamp current = new Timestamp(System.currentTimeMillis());

        for (int i = 0; i < entries.length; i++) {
            Helper.checkRequiredColumn("entries[i].getFixedBillingStatus()", entries[i].getFixedBillingStatus());
            entries[i].setModificationDate(current);
            entries[i].setCreationDate(current);
            entries[i].getFixedBillingStatus().setCreationDate(current);
            entries[i].getFixedBillingStatus().setModificationDate(current);
        }

        fixedBillingEntryDAO.createFixedBillingEntries(entries, audit);
    }

    /**
     * <p>
     * This is a batch version of the updateFixedBillingEntry method.
     * </p>
     *
     * <p>
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * @param entries An array of entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws UnrecognizedEntityException if an entry with the provided id was not found in the data store.
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public void updateFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException {
        Helper.checkObjectArray("entries", entries);

        Timestamp current = new Timestamp(System.currentTimeMillis());

        for (int i = 0; i < entries.length; i++) {
            Helper.checkRequiredColumn("entries[i].getFixedBillingStatus()", entries[i].getFixedBillingStatus());
            entries[i].setModificationDate(current);
            entries[i].getFixedBillingStatus().setModificationDate(current);
        }

        fixedBillingEntryDAO.updateFixedBillingEntries(entries, audit);
    }

    /**
     * <p>
     * This is a batch version of the deleteFixedBillingEntry method.
     * </p>
     *
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param entryIds An array of entryIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entryIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteFixedBillingEntries(long[] entryIds, boolean audit)
        throws DataAccessException {
        Helper.checkNull("entryIds", entryIds);

        for (int i = 0; i < entryIds.length; i++) {
            removeAllRejectReasonsFromEntry(getFixedBillingEntry(entryIds[i]), audit);
        }

        fixedBillingEntryDAO.deleteFixedBillingEntries(entryIds, audit);
    }

    /**
     * <p>
     * This is a batch version of the getFixedBillingEntry method.
     * </p>
     *
     * @param entryIds An array of entryIds for which entries should be retrieved.
     *
     * @return The entries corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if entryIds is null or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if an entry  with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public FixedBillingEntry[] getFixedBillingEntries(long[] entryIds)
        throws DataAccessException {
        return fixedBillingEntryDAO.getFixedBillingEntries(entryIds);
    }

    /**
     * <p>
     * This adds RejectReason with the specified id to the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any parameter is null.
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws InvalidCompanyException if the companyId of the entry and RejectReason do not match.
     */
    public void addRejectReasonToEntry(FixedBillingEntry entry, long rejectReasonId, boolean audit)
        throws DataAccessException {
        Helper.checkNull("entry", entry);
        Helper.checkLongValue("rejectReasonId", rejectReasonId);

        RejectReason reason = rejectReasonManager.retrieveRejctReason(rejectReasonId);

        if ((reason == null) || (entry.getCompanyId() != reason.getCompanyId())) {
            throw new InvalidCompanyException(entry.getId(), rejectReasonId);
        } else {
            entry.setModificationDate(new Timestamp(System.currentTimeMillis()));
            fixedBillingEntryRejectReasonDAO.addRejectReasonToEntry(rejectReasonId, entry, audit);
        }
    }

    /**
     * <p>
     * This removes a RejectReason with the specified id from the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param rejectReasonsId The rejectReason to remove.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws IllegalArgumentException if either argument is null.
     */
    public void removeRejectReasonFromEntry(FixedBillingEntry entry, long rejectReasonsId, boolean audit)
        throws DataAccessException {
        Helper.checkNull("entry", entry);
        entry.setModificationDate(new Timestamp(System.currentTimeMillis()));
        fixedBillingEntryRejectReasonDAO.removeRejectReasonFromEntry(rejectReasonsId, entry, audit);
    }

    /**
     * <p>
     * This removes all RejectReasons from the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the entry is null.
     * @throws UnrecognizedEntityException if the given entry was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeAllRejectReasonsFromEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException {
        Helper.checkNull("entry", entry);
        entry.setModificationDate(new Timestamp(System.currentTimeMillis()));
        fixedBillingEntryRejectReasonDAO.removeAllRejectReasonsFromEntry(entry, audit);
    }

    /**
     * <p>
     * This retrieves all RejectReasons for the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.  If an audit is specified, then the audit must be rolled back in
     * the case that the operation fails.
     * </p>
     *
     * @param entry The entry to retrieve RejectReasons from.
     *
     * @return An array of RejectReason ids for the given entry.
     *
     * @throws IllegalArgumentException if the entry is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws UnrecognizedEntityException if a entry was not found in the data store.
     */
    public long[] getAllRejectReasonsForEntry(FixedBillingEntry entry)
        throws DataAccessException {
        Helper.checkNull("entry", entry);

        return fixedBillingEntryRejectReasonDAO.getAllRejectReasonsForEntry(entry.getId());
    }

    /**
     * <p>
     * Retrieves all the FixedBillingEntries that are currently in the persistent store.
     * </p>
     *
     * @return An array of fixed billing entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingEntry[] getAllFixedBillingEntries()
        throws DataAccessException {
        return fixedBillingEntryDAO.getAllFixedBillingEntries();
    }

    /**
     * <p>
     * Retrieves the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     * FixedBillingEntries. This is used to conveniently specify the search criteria that should be used.  The filters
     * returned by the given factory should be used with this instance's searchFixedBillingEntries method.
     * </p>
     *
     * @return the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     *         fixed billing entries.
     */
    public FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory() {
        return fixedBillingEntryDAO.getFixedBillingEntryFilterFactory();
    }
}
