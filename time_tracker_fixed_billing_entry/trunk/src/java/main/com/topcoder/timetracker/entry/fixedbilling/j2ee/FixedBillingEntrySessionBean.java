/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;
import com.topcoder.timetracker.entry.fixedbilling.ManagerFactory;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage FixedBillingEntries within the
 * Time Tracker Application. It contains the same methods as <code>FixedBillingEntryManager</code>, and delegates to
 * an instance of <code>FixedBillingEntryManager</code>.
 * </p>
 *
 * <p>
 * The instance of FixedBillingEntryManager to use is retrieved from the ManagerFactory class.
 * </p>
 *
 * <p>
 * Thread Safety: The FixedBillingEntryManager interface implementations are required to be thread-safe, and so this
 * stateless session bean is thread-safe also.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class FixedBillingEntrySessionBean implements FixedBillingEntryManager, SessionBean {
    /**
     * <p>
     * This is the instance of SessionContext that was provided by the EJB container.  It is stored and made available
     * to subclasses. It is also used when performing a rollback.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public FixedBillingEntrySessionBean() {
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
     * The entity should be provided with no id set (id = 0).  Otherwise, the implementation should throw
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
        try {
            ManagerFactory.getFixedBillingEntryManager().createFixedBillingEntry(entry, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
        try {
            ManagerFactory.getFixedBillingEntryManager().updateFixedBillingEntry(entry, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (UnrecognizedEntityException uee) {
            sessionContext.setRollbackOnly();
            throw uee;
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws IllegalArgumentException if entryId &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteFixedBillingEntry(long entryId, boolean audit)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBillingEntryManager().deleteFixedBillingEntry(entryId, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (UnrecognizedEntityException uee) {
            sessionContext.setRollbackOnly();
            throw uee;
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
        try {
            return ManagerFactory.getFixedBillingEntryManager().getFixedBillingEntries(new long[] {entrytId })[0];
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
     */
    public FixedBillingEntry[] searchFixedBillingEntries(Filter filter)
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBillingEntryManager().searchFixedBillingEntries(filter);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
        try {
            ManagerFactory.getFixedBillingEntryManager().createFixedBillingEntries(entries, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBillingEntryManager().updateFixedBillingEntries(entries, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
        try {
            ManagerFactory.getFixedBillingEntryManager().deleteFixedBillingEntries(entryIds, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingEntry[] getFixedBillingEntries(long[] entryIds)
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBillingEntryManager().getFixedBillingEntries(entryIds);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
     */
    public void addRejectReasonToEntry(FixedBillingEntry entry, long rejectReasonId, boolean audit)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBillingEntryManager().addRejectReasonToEntry(entry, rejectReasonId, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
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
        try {
            ManagerFactory.getFixedBillingEntryManager().removeRejectReasonFromEntry(entry, rejectReasonsId, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
        try {
            ManagerFactory.getFixedBillingEntryManager().removeAllRejectReasonsFromEntry(entry, audit);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
        try {
            return ManagerFactory.getFixedBillingEntryManager().getAllRejectReasonsForEntry(entry);
        } catch (IllegalArgumentException iae) {
            sessionContext.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
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
        try {
            return ManagerFactory.getFixedBillingEntryManager().getAllFixedBillingEntries();
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        } catch (DataAccessException dae) {
            sessionContext.setRollbackOnly();
            throw dae;
        }
    }

    /**
     * <p>
     * Retrieves the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     * FixedBillingEntries.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingEntries method.
     * </p>
     *
     * @throws DataAccessException if a problem occur.
     *
     * @return the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     *         fixed billing entries.
     */
    public FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory()
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBillingEntryManager().getFixedBillingEntryFilterFactory();
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        }
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbCreate() {
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>
     * Sets the SessionContext to use for this session.  This method is included to comply with the SessionBean
     * interface.
     * </p>
     *
     * @param ctx The SessionContext to use.
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }
}
