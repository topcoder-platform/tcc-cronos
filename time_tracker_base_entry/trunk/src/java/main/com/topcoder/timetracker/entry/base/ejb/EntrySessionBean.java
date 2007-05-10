/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.ejb;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.CutoffTimeDao;
import com.topcoder.timetracker.entry.base.DuplicateEntryException;
import com.topcoder.timetracker.entry.base.EntryManager;
import com.topcoder.timetracker.entry.base.EntryManagerException;
import com.topcoder.timetracker.entry.base.EntryNotFoundException;
import com.topcoder.timetracker.entry.base.PersistenceException;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.util.Calendar;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <p>This is a Stateless SessionBean that is used to provided business services to manage BaseEntry and
 * CutoffTimeBean objects within the Time Tracker Application. It implements the EntryManager interface and delegates
 * to an instance of CutoffTimeDao. Transactions for this bean are handled by the EJB Container. It is expected that
 * the transaction level declared in the deployment descriptor for this class will be REQUIRED.</p>
 *  <p>In order to ensure that failed transactions are properly rolled back by the EJB container, all business
 * methods will call setRollbackOnly() if an exception is thrown.</p>
 *  <p>All method calls on methods in EntryManager interface except for canSubmitEntry() are delegated to an
 * instance of CutoffTimeDao through the getDao() method.</p>
 *  <p>Thread Safety: The EJB Container will handle thread safety for this class, ensuring that it is used by no
 * more than one thread simultaneously.</p>
 *  <p>Sample config for this class:</p>
 *  <pre>
 * &lt;Config name=&quot;com.topcoder.timetracker.entry.base.ejb.EntrySessionBean&quot;&gt;
 *    &lt;Property name=&quot;of_namespace&quot;&gt;
 *     &lt;Value&gt;ObjectFactory&lt;/Value&gt;
 *   &lt;/Property&gt;
 *
 *    &lt;Property name=&quot;dao_key&quot;&gt;
 *     &lt;Value&gt;dao&lt;/Value&gt;
 *    &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  </pre>
 *  <p>All properties are required.</p>
 * @author bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class EntrySessionBean implements EntryManager, SessionBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = 3322064103650450148L;

	/** Property name of dao key in Config Manager. The dao key is used to create the CutoffTimeDao via
     *  ObjectFactory. */
    private static final String PROP_DAO_KEY = "dao_key";

    /**
     * Property name of object factory namespace in Config Manager. The namespace is used to create the Object
     * Factory.
     */
    private static final String PROP_OBJFACTORY_NS = "of_namespace";

    /** Default namespace where the config for this class located. */
    private static final String DEFAULT_NAMESPACE = EntrySessionBean.class.getName();

    /**
     * <p>This is the instance of CutoffTimeDao that this session bean delegates all the work to, initialized
     * in ejbCreate().</p>
     */
    private CutoffTimeDao dao;

    /**
     * <p>This is the instance of SessionContext that was provided by the EJB container. It is stored and made
     * available to subclasses. initialized in setSessionContext().</p>
     */
    private SessionContext sessionContext;

/**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public EntrySessionBean() {
        //does nothing
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
     * @throws EntryManagerException if any error occurs in persistence layer, or there's no cutoff time for the given
     *         entry's company
     */
    public boolean canSubmitEntry(BaseEntry entry) throws EntryManagerException {
        if (entry == null) {
            sessionContext.setRollbackOnly();
            throw new IllegalArgumentException("entry is null");
        }

        //entry should have date
        if (entry.getDate() == null) {
            sessionContext.setRollbackOnly();
            throw new IllegalArgumentException("entry.date is null");
        }

        //calculates cutoff time for the entry
        Calendar cutoffTime;

        try {
            cutoffTime = getCutoffTime(entry);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("failed to load cutoffTime", e);
        }

        //no cutoffTime for this entry's company, can not be submitted
        if (cutoffTime == null) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("cutoffTime for entry compId:" + entry.getCompanyId() + " is not found");
        }

        Calendar now = Calendar.getInstance();
        Calendar entryDate = Calendar.getInstance();
        entryDate.setTime(entry.getDate());

        //entry can be submitted only if: now>=entryDate && now<=cutoff
        return (!now.before(entryDate) && !now.after(cutoffTime));
    }

    /**
     * <p>Creates a new cutoff time. cutoffTime, companyId, creationUser should be set as requirement for
     * creation, and the company must be existent.</p>
     *  <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws EntryManagerException any persistence exception occurs
     * @throws IllegalArgumentException if cutoffTimeBean is null
     */
    public void createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException {
        try {
            getDao().createCutoffTime(cutoffTimeBean, audit);
        } catch (DuplicateEntryException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("duplicate entry", e);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("failed to create entry", e);
        } catch (RuntimeException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
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
     * @throws EntryManagerException any persistence exception occurs
     */
    public void deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException {
        try {
            getDao().deleteCutoffTime(cutoffTimeBean, audit);
        } catch (EntryNotFoundException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("entry not found", e);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("failed to delete entry", e);
        } catch (RuntimeException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>This method has simply been included to complete the SessionBean interface. It has an empty method
     * body.</p>
     */
    public void ejbActivate() {
        //does nothing
    }

    /**
     * <p>Initializes this Session Bean. This will read required properties from config manager, and use the
     * Object Factory to create the dao.</p>
     *
     * @throws CreateException if there's a problem creating this ejb
     */
    public void ejbCreate() throws CreateException {
        String key = null;

        try {
            String objFactoryNS = ConfigHelper.getStringProperty(DEFAULT_NAMESPACE, PROP_OBJFACTORY_NS, true);
            key = ConfigHelper.getStringProperty(DEFAULT_NAMESPACE, PROP_DAO_KEY, true);

            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(objFactoryNS));
            dao = (CutoffTimeDao) factory.createObject(key);
        } catch (SpecificationConfigurationException e) {
            throw new CreateException("failed to create ObjectFactory -" + e.getMessage());
        } catch (IllegalReferenceException e) {
            throw new CreateException("failed to create ObjectFactory -" + e.getMessage());
        } catch (ClassCastException e) {
            throw new CreateException("created object for key:" + key + " should be type of "
                    + CutoffTimeDao.class.getName() + " -" + e.getMessage());
        } catch (InvalidClassSpecificationException e) {
            throw new CreateException("failed to create Object for key:" + key + "-" + e.getMessage());
        } catch (ConfigurationException e) {
            throw new CreateException("invalid config -" + e.getMessage());
        }
    }

    /**
     * <p>This method has simply been included to complete the SessionBean interface. It has an empty method
     * body.</p>
     */
    public void ejbPassivate() {
        //does nothing
    }

    /**
     * <p>This method has simply been included to complete the SessionBean interface. It has an empty method
     * body.</p>
     */
    public void ejbRemove() {
        //does nothing
    }

    /**
     * <p>This is a method for reading of a an existing cut off time record (in the persistence store) by
     * company id.</p>
     *
     * @param companyId company id
     *
     * @return CutoffTimeBean with given company id, null if the record not found
     *
     * @throws EntryManagerException any persistence exception occurs
     * @throws IllegalArgumentException if companyId is &lt;=0
     */
    public CutoffTimeBean fetchCutoffTimeByCompanyID(long companyId)
        throws EntryManagerException {
        try {
            return getDao().fetchCutoffTimeByCompanyID(companyId);
        } catch (EntryNotFoundException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("entry not found", e);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("failed to fetch entry", e);
        } catch (RuntimeException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>This is a method for reading of a an existing cut off time record (in the persistence store) by
     * actual record PK.</p>
     *
     * @param cutoffTimeId cutoff time id
     *
     * @return CutoffTimeBean with given id, null if the record not found
     *
     * @throws EntryManagerException any persistence exception occurs
     * @throws IllegalArgumentException if cutoffTimeId is &lt;=0
     */
    public CutoffTimeBean fetchCutoffTimeById(long cutoffTimeId)
        throws EntryManagerException {
        try {
            return getDao().fetchCutoffTimeById(cutoffTimeId);
        } catch (EntryNotFoundException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("entry not found", e);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("failed to fetch entry", e);
        } catch (RuntimeException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>Sets the SessionContext to use for this session. This method is used by the EJB container to set the
     * session context before the bean is used.</p>
     *
     * @param context SessionContext for this bean; set by the EJB container
     */
    public void setSessionContext(SessionContext context) {
        this.sessionContext = context;
    }

    /**
     * <p>This is a method for updating of a an existing cut off time record (in the persistence store). This
     * method will not update the companyId, creationUser, creationDate, and cutoffTime, modificationUser should not
     * be null. And note that if {@link CutoffTimeBean#isChanged()} is false, the updating will not take effect.</p>
     *  <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws EntryManagerException any persistence exception occurs
     * @throws IllegalArgumentException if cutoffTimeBean is null
     */
    public void updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws EntryManagerException {
        try {
            getDao().updateCutoffTime(cutoffTimeBean, audit);
        } catch (EntryNotFoundException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("entry not found", e);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new EntryManagerException("failed to update entry", e);
        } catch (RuntimeException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>Protected accessor for dao. All access to the dao field should be through this method.</p>
     *
     * @return the instance of CutoffTimeDao that this session bean delegates all the work to, may be null if the
     *         Session Bean is not initialized
     */
    protected CutoffTimeDao getDao() {
        return dao;
    }

    /**
     * <p>Protected method that allows subclasses to access the session context if necessary.</p>
     *
     * @return the instance of SessionContext that was provided by the EJB container, may be null if the Session Bean
     *         is not initialized
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * Gets the cutoff time for the entry's company from persistence and calculates the actual cutoff time for
     * this entry. The actual cutoff time for the entry is calculated by inheriting the day of week and time(hour,
     * minute, second, millisecond) from the company's cutoff time, and rolling the week to the next week of the
     * entry's date. Note that a week starts on Sunday and ends on Saturday.
     *
     * @param entry The entry whose cutoff time will be calculated
     *
     * @return cutoff time for this entry, may be null if there's no cutoff time for the entry's company
     *
     * @throws PersistenceException if any error occurs in persistence
     */
    private Calendar getCutoffTime(BaseEntry entry) throws PersistenceException {
        CutoffTimeBean cutoffTimeBean = getDao().fetchCutoffTimeByCompanyID(entry.getCompanyId());

        if (cutoffTimeBean == null) {
            return null;
        }

        Calendar tmp = Calendar.getInstance();
        tmp.setTime(cutoffTimeBean.getCutoffTime());

        Calendar cutoffTime = Calendar.getInstance();
        cutoffTime.setTime(entry.getDate());

        //just keeps the week of current entry, and set the day of week and time from cutoffTimeBean
        cutoffTime.set(Calendar.DAY_OF_WEEK, tmp.get(Calendar.DAY_OF_WEEK));
        cutoffTime.set(Calendar.HOUR_OF_DAY, tmp.get(Calendar.HOUR_OF_DAY));
        cutoffTime.set(Calendar.MINUTE, tmp.get(Calendar.MINUTE));
        cutoffTime.set(Calendar.SECOND, tmp.get(Calendar.SECOND));
        cutoffTime.set(Calendar.MILLISECOND, tmp.get(Calendar.MILLISECOND));

        //the cutoff time for entry is in the next week
        //the week days is from Sunday to Saturday
        cutoffTime.add(Calendar.WEEK_OF_YEAR, 1);

        return cutoffTime;
    }
}
