/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditPersistence;
import com.topcoder.timetracker.audit.AuditPersistenceException;
import com.topcoder.timetracker.audit.TimeTrackerAuditHelper;

import com.topcoder.util.objectfactory.ObjectFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <p>
 * This is a Stateless <code>SessionBean</code> that is used to provided business services to manage auditing within
 * the Time Tracker Application. It implements the <code>AuditManager</code> interface and delegates to an instance of
 * <code>AuditPersistence</code> as the DAO. Transactions for this bean are handled by the EJB Container. It is
 * expected that the transaction level declared in the deployment descriptor for this class will be REQUIRED.
 * </p>
 *
 * <p>
 * The DAO will be instantiated in ejbCreate() via config manager from the default namespace, which is the fully
 * qualified name of this class. And it will be available during use of the bean.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The AuditManager interface implementations are required to be thread-safe, and so this
 * stateless session bean is thread-safe also.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class AuditSessionBean implements SessionBean, AuditManager {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -596653189656212419L;

	/**
     * <p>
     * Represents the namespace used to retrieve the dao instance.
     * </p>
     */
    private static final String NAMESPACE = AuditSessionBean.class.getName();

    /**
     * <p>
     * Represents the property name to retrieve the of_namespace value.
     * </p>
     */
    private static final String OF_NAMESPACE_PROPERTY = "of_namespace";

    /**
     * <p>
     * Represents the property name to retrieve the dao_key value.
     * </p>
     */
    private static final String DAO_KEY_PROPERTY = "dao_key";

    /**
     * <p>
     * Represents the instance of DAO that this session bean delegates all the work to.
     * </p>
     *
     * <p>
     * It will be instantiated in ejbCreate() and be available during use of the bean.
     * </p>
     */
    private AuditPersistence dao;

    /**
     * <p>
     * Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs. Set in the
     * setSessionContext() method by the container.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public AuditSessionBean() {
    }

    /**
     * <p>
     * Adds an audit record header, and all of its details, to persistence. This method is delegated to the persistence
     * layer.
     * </p>
     *
     * @param record The audit record header containing information to be added to the database - cannot be null
     *
     * @throws AuditManagerException If there are problems in adding the information.
     */
    public void createAuditRecord(AuditHeader record) throws AuditManagerException {
        try {
            dao.createAuditRecord(record);
        } catch (AuditPersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new AuditManagerException("Errors occur in the persistence layer.", e);
        }
    }

    /**
     * <p>
     * Searches through the audits, and returns an array of <code>AuditHeader</code> objects which pass the given
     * filters. This method is delegated to the persistence layer.
     * </p>
     *
     * @param filter A filter describing the search constraints against which the audits are to be tested. Can be null
     *        if no filter is to be used.
     *
     * @return An array of <code>AuditHeader</code> objects that match the given filter. This array may be empty if no
     *         matches are found, but will never be null.
     *
     * @throws AuditManagerException If there are any problems searching the audits.
     */
    public AuditHeader[] searchAudit(Filter filter) throws AuditManagerException {
        try {
            return dao.searchAudit(filter);
        } catch (AuditPersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new AuditManagerException("Errors occur in the persistence layer.", e);
        }
    }

    /**
     * <p>
     * Removes an audit header, and all of its details, from persistence. This method is delegated to the persistence
     * layer.
     * </p>
     *
     * @param auditHeaderId The ID of the audit header to remove.
     *
     * @return boolean - true if anything was removed from the database, otherwise false.
     *
     * @throws AuditManagerException If there are problems in removing the data.
     */
    public boolean rollbackAuditRecord(long auditHeaderId) throws AuditManagerException {
        try {
            return dao.rollbackAuditRecord(auditHeaderId);
        } catch (AuditPersistenceException e) {
            sessionContext.setRollbackOnly();
            throw new AuditManagerException("Errors occur in the persistence layer.", e);
        }
    }

    /**
     * <p>
     * The dao field is instantiated in this method. Read the configuration file from the default namespace, which is
     * the fully qualified name of this class. The configuration file should have two properties, of_namespace and
     * dao_key. Use dao_key to get an <code>AuditPersistence</code> from Object Factory.
     * </p>
     *
     * @throws CreateException if it fails to create the dao instance.
     */
    public void ejbCreate() throws CreateException {
        try {
            // obtain the namespace used for ConfigManagerSpecificationFactory to use with ObjectFactory
            String ofNamespace = TimeTrackerAuditHelper.getStringPropertyValue(NAMESPACE, OF_NAMESPACE_PROPERTY, true);

            // obtain the key for audit DAO
            String daoKey = TimeTrackerAuditHelper.getStringPropertyValue(NAMESPACE, DAO_KEY_PROPERTY, true);

            // create the audit DAO object
            ObjectFactory objectFactory = TimeTrackerAuditHelper.createObjectFactory(ofNamespace);
            dao = (AuditPersistence) TimeTrackerAuditHelper.createObject(objectFactory, daoKey, AuditPersistence.class);
        } catch (AuditConfigurationException e) {
            throw new CreateException("Fails to create the AuditPersistence instance. Exception: "
                + TimeTrackerAuditHelper.getExceptionStaceTrace(e));
        }
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>
     * Removes the bean. This is an empty implementation.
     * </p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>
     * Sets the session context.
     * </p>
     *
     * @param context session context.
     */
    public void setSessionContext(SessionContext context) {
        this.sessionContext = context;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context if necessary.
     * </p>
     *
     * @return session context.
     */
    protected SessionContext getSessionContext() {
        return this.sessionContext;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the dao field.
     * </p>
     *
     * @return value of dao field, not null.
     */
    protected AuditPersistence getDao() {
        return this.dao;
    }
}
