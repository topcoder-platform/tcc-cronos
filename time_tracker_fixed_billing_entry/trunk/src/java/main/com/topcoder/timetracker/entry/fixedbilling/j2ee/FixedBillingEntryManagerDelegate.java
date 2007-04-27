/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.j2ee;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;
import com.topcoder.timetracker.entry.fixedbilling.Helper;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import javax.naming.Context;
import javax.naming.NamingException;


/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.  It is responsible for
 * looking up the local interface of the SessionBean for <code>FixedBillingEntryManager</code>, and delegating any
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
public class FixedBillingEntryManagerDelegate implements FixedBillingEntryManager {
    /**
     * <p>
     * This is the local interface for the FixedBillingEntryManager business services.  All business calls are
     * delegated here.
     * </p>
     */
    private final FixedBillingEntryManagerLocal fixedBillingEntryManager;

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
    public FixedBillingEntryManagerDelegate(String namespace)
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

            FixedBillingEntryManagerLocalHome homeObject =
                (FixedBillingEntryManagerLocalHome) JNDIUtils.getObject(context, localHomeName);
            this.fixedBillingEntryManager = homeObject.create();
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
        fixedBillingEntryManager.createFixedBillingEntry(entry, audit);
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
        fixedBillingEntryManager.updateFixedBillingEntry(entry, audit);
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
        fixedBillingEntryManager.deleteFixedBillingEntry(entryId, audit);
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
        return fixedBillingEntryManager.getFixedBillingEntry(entrytId);
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
        return fixedBillingEntryManager.searchFixedBillingEntries(filter);
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
        fixedBillingEntryManager.createFixedBillingEntries(entries, audit);
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
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     */
    public void updateFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException {
        fixedBillingEntryManager.updateFixedBillingEntries(entries, audit);
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
        fixedBillingEntryManager.deleteFixedBillingEntries(entryIds, audit);
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
        return fixedBillingEntryManager.getFixedBillingEntries(entryIds);
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
        fixedBillingEntryManager.addRejectReasonToEntry(entry, rejectReasonId, audit);
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
        fixedBillingEntryManager.removeRejectReasonFromEntry(entry, rejectReasonsId, audit);
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
        fixedBillingEntryManager.removeAllRejectReasonsFromEntry(entry, audit);
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
        return fixedBillingEntryManager.getAllRejectReasonsForEntry(entry);
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
        return fixedBillingEntryManager.getAllFixedBillingEntries();
    }

    /**
     * <p>
     * Retrieves the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     * FixedBillingEntries.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingEntries method.
     * </p>
     *
     * @throws DataAccessException if a problem occur.
     * @return the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     *         fixed billing entries.
     */
    public FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory()
        throws DataAccessException {
        return fixedBillingEntryManager.getFixedBillingEntryFilterFactory();
    }
}
