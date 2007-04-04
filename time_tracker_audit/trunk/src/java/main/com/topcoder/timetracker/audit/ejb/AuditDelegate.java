/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.ejb;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.TimeTrackerAuditHelper;

import com.topcoder.util.config.ConfigManagerException;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.NamingException;


/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application. It is responsible for
 * looking up the local interface of the <code>AuditSessionBean</code>, and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class AuditDelegate implements AuditManager {
    /**
     * <p>
     * Represents the property name to retrieve the context_name value.
     * </p>
     */
    private static final String CONTEXT_NAME_PROPERTY = "context_name";

    /**
     * <p>
     * Represents the property name to retrieve the jndi_name value.
     * </p>
     */
    private static final String JNDI_NAME_PROPERTY = "jndi_name";

    /**
     * <p>
     * Represents the local ejb instance used for all calls. Created in the consructor, will not be null, and will not
     * change.
     * </p>
     */
    private final AuditLocalObject auditLocalObject;

    /**
     * <p>
     * Instantiates new <code>AuditDelegate</code> instance from the given namespace. It will obtain a reference to the
     * EJB <code>AuditLocalObject</code>.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException If namespace is null or empty.
     * @throws AuditConfigurationException If the attempt to instantiate the auditLocalObject fails.
     */
    public AuditDelegate(String namespace) throws AuditConfigurationException {
        TimeTrackerAuditHelper.validateString(namespace, "namespace");

        try {
            String contextName = TimeTrackerAuditHelper.getStringPropertyValue(namespace, CONTEXT_NAME_PROPERTY, false);
            String jndiName = TimeTrackerAuditHelper.getStringPropertyValue(namespace, JNDI_NAME_PROPERTY, true);

            Context context = (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            AuditLocalHome home = (AuditLocalHome) context.lookup(jndiName);
            auditLocalObject = home.create();
        } catch (NamingException e) {
            throw new AuditConfigurationException("Fails to create the ejb.", e);
        } catch (CreateException e) {
            throw new AuditConfigurationException("Fails to create the ejb.", e);
        } catch (ConfigManagerException e) {
            throw new AuditConfigurationException("Fails to get the context from JNDI Utility.", e);
        }
    }

    /**
     * <p>
     * Adds an audit record header, and all of its details, to persistence. This method is delegated to the persistence
     * layer.
     * </p>
     *
     * @param record The audit record header containing information to be added to the database - cannot be null.
     *
     * @throws IllegalArgumentException if record is null, or if not null field is not set for given audit header, or
     *         the contained 'details'.
     * @throws AuditManagerException If there are problems in adding the information.
     */
    public void createAuditRecord(AuditHeader record) throws AuditManagerException {
        auditLocalObject.createAuditRecord(record);
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
        return auditLocalObject.searchAudit(filter);
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
        return auditLocalObject.rollbackAuditRecord(auditHeaderId);
    }
}
