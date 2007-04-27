/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusManager;
import com.topcoder.timetracker.entry.fixedbilling.ManagerFactory;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage FixedBillingStatuses within the
 * Time Tracker Application. It contains the same methods as <code>FixedBillingStatusManager</code>, and delegates to
 * an instance of <code>FixedBillingStatusManager</code>.
 * </p>
 *
 * <p>
 * The instance of FixedBillingStatusManager to use is retrieved from the ManagerFactory class.
 * </p>
 *
 * <p>
 * Thread Safety: The FixedBillingStatusManager interface implementations are required to be thread-safe, and so this
 * stateless session bean is thread-safe also.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatusSessionBean implements FixedBillingStatusManager, SessionBean {
    /**
     * <p>
     * This is the instance of SessionContext that was provided by the EJB container.  It is stored and made available
     * to subclasses.  It is also used when performing a rollback.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public FixedBillingStatusSessionBean() {
    }

    /**
     * <p>
     * Defines a FixedBillingStatus to be recognized within the persistent store managed by this utility. A unique
     * status id will automatically be generated and assigned to the status.  There is also the option to perform an
     * audit.  If an audit is specified, then the audit must be rolled back in case the operation fails.
     * </p>
     *
     * <p>
     * The implementation will set the status' creation and modification date to the current date.   These
     * creation/modification details will also reflect in the persistent store. The creation and modification user is
     * the responsibility of the calling application.
     * </p>
     *
     * @param status The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void createFixedBillingStatus(FixedBillingStatus status)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBililngStatusManager().createFixedBillingStatus(status);
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
     * Modifies the persistent store so that it now reflects the data in the provided FixedBillingStatus parameter.
     * There is also the option to perform an audit.  If an audit is specified, then the audit must be rolled back in
     * case the operation fails.
     * </p>
     *
     * <p>
     * The implementation will set the status' modification details to the current date.   These modification details
     * will also reflect in the persistent store. The modification user is the responsibility of the calling
     * application.
     * </p>
     *
     * @param status The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateFixedBillingStatus(FixedBillingStatus status)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBililngStatusManager().updateFixedBillingStatus(status);
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
     * Modifies the persistent store so that it  no longer contains data on the FixedBillingstatus with the specified
     * id.
     * </p>
     *
     * @param statusId The id of the status for which the operation should be performed.
     *
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws IllegalArgumentException if statusId &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteFixedBillingStatus(long statusId)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBililngStatusManager().deleteFixedBillingStatus(statusId);
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
     * Retrieves a FixedBillingStatus object that reflects the data in the persistent store with the specified
     * statusId.
     * </p>
     *
     * @param statusId The id of the status to retrieve.
     *
     * @return The status with specified id.
     *
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus getFixedBillingStatus(long statusId)
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBililngStatusManager().getFixedBillingStatus(statusId);
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
     * Searches the persistent store for any FixedBillingStatuses that satisfy the criteria that was specified in the
     * provided search filter.  The provided filter should be created using either the filters that are created using
     * the FixedBillingStatusFilterFactory returned by getFixedBillingStatusEntryFilterFactory of this instance, or a
     * composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component) that
     * combines the filters created using FixedBillingStatusFilterFactory.
     * </p>
     *
     * @param criteria The filter used to search for statuses.
     *
     * @return The statuses satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus[] searchFixedBillingStatuses(Filter criteria)
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBililngStatusManager().searchFixedBillingStatuses(criteria);
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
     * This is a batch version of the createFixedBillingStatus method.
     * </p>
     *
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param statuses An array of statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public void createFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBililngStatusManager().createFixedBillingStatuses(statuses);
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
     * This is a batch version of the updateFixedBillingStatus method.
     * </p>
     *
     * <p>
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * @param statuses An array of  statuses for which the operation should be performed.
     *
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBililngStatusManager().updateFixedBillingStatuses(statuses);
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
     * This is a batch version of the deleteFixedBillingStatus method.
     * </p>
     *
     * @param statusIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteFixedBillingStatuses(long[] statusIds)
        throws DataAccessException {
        try {
            ManagerFactory.getFixedBililngStatusManager().deleteFixedBillingStatuses(statusIds);
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
     * This is a batch version of the getFixedBillingStatus  method.
     * </p>
     *
     * @param statusIds An array of statusIds for which time status should be retrieved.
     *
     * @return The statuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus[] getFixedBillingStatuses(long[] statusIds)
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBililngStatusManager().getFixedBillingStatuses(statusIds);
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
     * Retrieves the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching
     * for FixedBillingStatuses.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingStatuses method.
     * </p>
     *
     * @throws DataAccessException if a problem occur.
     *
     * @return the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching for
     *         FixedBillingStatuses.
     */
    public FixedBillingStatusFilterFactory getFixedBillingStatusFilterFactory()
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBililngStatusManager().getFixedBillingStatusFilterFactory();
        } catch (ConfigurationException ce) {
            sessionContext.setRollbackOnly();
            throw new DataAccessException("Unable to get the Manager instance.", ce);
        }
    }

    /**
     * <p>
     * Retrieves all the FixedBillingStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of statuses retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingStatus[] getAllFixedBillingStatuses()
        throws DataAccessException {
        try {
            return ManagerFactory.getFixedBililngStatusManager().getAllFixedBillingStatuses();
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
