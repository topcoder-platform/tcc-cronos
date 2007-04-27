/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusManager;
import com.topcoder.timetracker.entry.fixedbilling.Helper;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import javax.naming.Context;
import javax.naming.NamingException;


/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.  It is responsible for
 * looking up the local interface of the SessionBean for <code>FixedBillingStatusManager</code>, and delegating any
 * calls to the bean.
 * </p>
 *
 * <p>
 * &lt;?xml version=&quot;1.0&quot;?&gt;<br>
 * &lt;CMConfig&gt;<br>
 * &lt;Config name=&quot;FixedBillingEntryManagerDelegateTest&quot;&gt;<br>
 * &lt;Property name=&quot;contextName&quot;&gt;<br>
 * &lt;Value&gt;mockEJBContext&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;localHomeName&quot;&gt;<br>
 * &lt;Value&gt;FixedBillingEntryManagerDelegateTest&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Config&gt;<br>
 * &lt;Config name=&quot;FixedBillingStatusManagerDelegateTest&quot;&gt;<br>
 * &lt;Property name=&quot;contextName&quot;&gt;<br>
 * &lt;Value&gt;mockEJBContext&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;Property name=&quot;localHomeName&quot;&gt;<br>
 * &lt;Value&gt;FixedBillingStatusManagerDelegateTest&lt;/Value&gt;<br>
 * &lt;/Property&gt;<br>
 * &lt;/Config&gt;<br>
 * &lt;/CMConfig&gt;<br>
 * </p>
 *
 * <p>
 * Thread Safety: - This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatusManagerDelegate implements FixedBillingStatusManager {
    /**
     * <p>
     * This is the local interface for the FixedBillingStatusManager business services.  All business calls are
     * delegated here.
     * </p>
     */
    private final FixedBillingStatusManagerLocal fixedBillingStatusManager;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace.
     * </p>
     *
     * @param namespace The namespace to use.
     *
     * @throws IllegalArgumentException if namespace is null or an empty String.
     * @throws ConfigurationException if a problem occurs while constructing the instance.
     */
    public FixedBillingStatusManagerDelegate(String namespace)
        throws ConfigurationException {
        Helper.checkNullOrEmpty("namespace", namespace);

        try {
            ConfigManager config = ConfigManager.getInstance();
            String ctxConfig = config.getString(namespace, "contextName");
            String localHomeName = config.getString(namespace, "localHomeName");

            if ((localHomeName == null) || (localHomeName.trim().length() == 0)) {
                throw new ConfigurationException("The localHomeName property should not be null or empty.");
            }

            Context context = null;

            if ((ctxConfig == null) || (ctxConfig.trim().length() == 0)) {
                context = JNDIUtils.getDefaultContext();
            } else {
                context = JNDIUtils.getContext(ctxConfig);
            }

            FixedBillingStatusManagerLocalHome homeObject =
                (FixedBillingStatusManagerLocalHome) JNDIUtils.getObject(context, localHomeName);
            this.fixedBillingStatusManager = homeObject.create();
        } catch (NamingException ne) {
            throw new ConfigurationException("Unable to create the instance.", ne);
        } catch (ConfigManagerException cme) {
            throw new ConfigurationException("Unable to create the instance.", cme);
        } catch (ClassCastException cce) {
            throw new ConfigurationException("Unable to create the instance.", cce);
        } catch (NullPointerException npe) {
            throw new ConfigurationException("Unable to create the instance.", npe);
        }
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
        fixedBillingStatusManager.createFixedBillingStatus(status);
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
        fixedBillingStatusManager.updateFixedBillingStatus(status);
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
        fixedBillingStatusManager.deleteFixedBillingStatus(statusId);
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
        return fixedBillingStatusManager.getFixedBillingStatus(statusId);
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
        return fixedBillingStatusManager.searchFixedBillingStatuses(criteria);
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
        fixedBillingStatusManager.createFixedBillingStatuses(statuses);
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
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public void updateFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException {
        fixedBillingStatusManager.updateFixedBillingStatuses(statuses);
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
        fixedBillingStatusManager.deleteFixedBillingStatuses(statusIds);
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
        return fixedBillingStatusManager.getFixedBillingStatuses(statusIds);
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
        return fixedBillingStatusManager.getFixedBillingStatusFilterFactory();
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
        return fixedBillingStatusManager.getAllFixedBillingStatuses();
    }
}
