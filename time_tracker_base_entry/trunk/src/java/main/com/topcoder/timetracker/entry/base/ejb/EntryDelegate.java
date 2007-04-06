/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.ejb;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.EntryManager;
import com.topcoder.timetracker.entry.base.EntryManagerException;
import com.topcoder.timetracker.entry.base.ParameterCheck;

import com.topcoder.util.config.ConfigManagerException;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.NamingException;


/**
 * <p>This is a Business Delegate/Service Locator that may be used within a J2EE application. It is responsible for
 * looking up the local interface of the EntrySessionBean, and delegating any calls to the bean.</p>
 * <p>Thread Safety: - This class is thread safe, since all state is modified at construction.</p>
 *
 * <p> Sample config for this class:</p>
 *
 * <pre>
 * &lt;Config name=&quot;com.topcoder.timetracker.entry.base&quot;&gt;
 *   &lt;Property name=&quot;context_name&quot;&gt;
 *    &lt;!-- Optional, if not set, default context will be used--&gt;
 *    &lt;Value&gt;default&lt;/Value&gt;
 *   &lt;/Property&gt;
 *
 *   &lt;!--Required --&gt;
 *   &lt;Property name=&quot;jndi_home&quot;&gt;
 *    &lt;Value&gt;tt_entry&lt;/Value&gt;
 *   &lt;/Property&gt;
 * &lt;/Config&gt;
 * </pre>
 *
 * @author bendlund, TCSDEVELOPER
 * @version 3.2
  */
public class EntryDelegate implements EntryManager {
    /** Property name in config manger. The value is used to retrieve EJBLocalHome object for EntrySessionBean. */
    private static final String PROP_JNDI_HOME = "jndi_home";

    /**
     * Property name of context name in config manager. The context name is used to retrieve the home object of
     * the respective session bean.
     */
    private static final String PROP_CONTEXT_NAME = "context_name";

    /**
     * <p>This is the local interface for the EntrySessionBean. All business calls are delegated here.
     * Initialized in ctor, not null.</p>
     */
    private final EntryLocalObject entryLocalObject;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace and creates the
     * EntryLocalObject according to it.
     * </p>
     *
     * @param namespace ConfigManager namespace from which to read configuration
     * @throws IllegalArgumentException if namespace is empty or null.
     * @throws ConfigurationException if the attempt to instantiate the entryLocalObject fails
     */
    public EntryDelegate(String namespace) throws ConfigurationException {
        ParameterCheck.checkNullEmpty("namespace", namespace);

        String contextName = ConfigHelper.getStringProperty(namespace, PROP_CONTEXT_NAME, false);
        String jndiHome = ConfigHelper.getStringProperty(namespace, PROP_JNDI_HOME, true);

        try {
            //if context name is not specified, uses default context
            Context context = (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);
            EntryLocalHome localHome = (EntryLocalHome) context.lookup(jndiHome);
            this.entryLocalObject = localHome.create();
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("JNDIUtil is not configured properly", e);
        } catch (NamingException e) {
            throw new ConfigurationException("failed to create EntryLocalObject via JNDIUtil", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("failed to obtain EntryLocalHome", e);
        } catch (CreateException e) {
            throw new ConfigurationException("failed to create EntryLocalObject", e);
        }
    }

    /**
     * <p>This is a simple check if the given entry can be submitted. In order to determine if an entry can be
     * submitted it must be submitted before the cut off day of the week and time. Once this time reaches on the day
     * of the week assigned as the cut off day, entries can no longer be entered for the previous week, ending in
     * Saturday. Each company should be able to configure their own cut off day and time.</p>
     *  <p>If there's no cutoff time for the given entry's company, EntryManagerException will be thrown.</p>
     *
     * @param entry non -null entry to check
     *
     * @return true if entry can be submitted and false otherwise
     *
     * @throws IllegalArgumentException if entry is null or the entry has no date
     * @throws EntryManagerException if there is any problem EntryLocalObject, or not cutoff time for the given entry's
     *         company
     */
    public boolean canSubmitEntry(BaseEntry entry) throws EntryManagerException {
        return entryLocalObject.canSubmitEntry(entry);
    }

    /**
     * <p>Creates a new cutoff time. cutoffTime, companyId, creationUser should be set as requirement for
     * creation, and the company must be existent.</p>
     *  <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null
     * @throws EntryManagerException if there is any problem EntryLocalObject
     */
    public void createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException {
        entryLocalObject.createCutoffTime(cutoffTimeBean, audit);
    }

    /**
     * <p>This is a method for deletion of a an existing cut off time record (in the persistence store).</p>
     * <p>Note that for deletion, modification user should be set.</p>
     * <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean the java bean encapsulating the record data to delete
     * @param audit boolean indicating whether to audit this operation
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null or its id is not set(&lt;=0) or no
     *  modification user is set
     * @throws EntryManagerException if there is any problem EntryLocalObject
     */
    public void deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException {
        entryLocalObject.deleteCutoffTime(cutoffTimeBean, audit);
    }

    /**
     * <p>This is a method for reading of a an existing cut off time record (in the persistence store) by
     * company id.</p>
     *
     * @param companyId company id
     *
     * @return CutoffTimeBean with given company id, null if the record not found
     *
     * @throws EntryManagerException if there is any problem EntryLocalObject
     * @throws IllegalArgumentException if companyId is &lt;=0
     */
    public CutoffTimeBean fetchCutoffTimeByCompanyID(long companyId)
        throws EntryManagerException {
        return entryLocalObject.fetchCutoffTimeByCompanyID(companyId);
    }

    /**
     * <p>This is a method for reading of a an existing cut off time record (in the persistence store) by
     * actual record PK.</p>
     *
     * @param cutoffTimeId cutoff time id
     *
     * @return CutoffTimeBean with given id, null if the record not found
     *
     * @throws EntryManagerException if there is any problem EntryLocalObject
     * @throws IllegalArgumentException if cutoffTimeId is &lt;=0
     */
    public CutoffTimeBean fetchCutoffTimeById(long cutoffTimeId)
        throws EntryManagerException {
        return entryLocalObject.fetchCutoffTimeById(cutoffTimeId);
    }

    /**
     * <p>This is a method for updating of a an existing cut off time record (in the persistence store). This
     * method will not update the companyId, creationUser, creationDate, and cutoffTime, modificationUser should
     * not be null. And note that if {@link CutoffTimeBean#isChanged()} is false, the updating will not take effect.</p>
     * <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null
     * @throws EntryManagerException if there is any problem EntryLocalObject
     */
    public void updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException {
        entryLocalObject.updateCutoffTime(cutoffTimeBean, audit);
    }
}
