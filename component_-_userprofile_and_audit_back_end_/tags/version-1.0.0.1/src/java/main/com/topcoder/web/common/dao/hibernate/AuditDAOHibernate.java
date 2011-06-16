/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;

/**
 * <p>
 * This class is an implementation of AuditDAO that manages AuditRecord entities in persistence using Hibernate.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable and thread safe when entities passed to it are used by the
 * caller in thread safe manner. It's assumed that transactions are managed externally.
 * </p>
 *  <p>
 * <strong>Sample Usage:</strong> <code>
 * <pre>
 * //instantiate the class
 * AuditDAO auditDAO = new AuditDAOHibernate();
 *
 * // Get if given has an operation
 *   boolean hasOperation = auditDAO.hasOperation("dok_tester", "login")
 *
 * // Save an instance of AuditRecord
 * AuditRecord record = new AuditRecord();
 * auditDAO.audit(record);
 * </pre>
 * </code>
 * </p>
 *
 * @author saarixx, kalc
 * @version 1.0
 */
public class AuditDAOHibernate extends Base implements AuditDAO {

    /**
     * <p>
     * Query used to check whether user has performed the operation of specified operationtype previously.
     * </p>
     */
    private static final String COUNT_QUERY = "select count(r) from AuditRecord r where r.handle = ? "
            + "and r.operationType = ?";

    /**
     * <p>
     * Creates an instance of AuditDAOHibernate.
     * </p>
     */
    public AuditDAOHibernate() {
        super();
    }

    /**
     * <p>
     * Creates an instance of AuditDAOHibernate using the given Hibernate session.
     * </p>
     * @param session
     *            the Hibernate session
     * @throws IllegalArgumentException
     *             if session is null
     */
    public AuditDAOHibernate(Session session) {
        super(Helper.checkParam(session));
    }

    /**
     * <p>
     * Saves the given audit record.
     * </p>
     * @param record
     *            the audit record to be saved
     * @throws IllegalArgumentException
     *             if record is null
     */
    public void audit(AuditRecord record) {
        ParameterCheckUtility.checkNotNull(record, "auditRecord");
        session.saveOrUpdate(record);
    }

    /**
     * <p>
     * Checks whether the user performed the specified operation previously.
     * </p>
     * @param handle
     *            the handle of the user
     * @param operationType
     *            the operation type
     * @return true if the user performed the specified operation previously, false otherwise
     * @throws IllegalArgumentException
     *             if handle or operationType is null/empty
     */
    public boolean hasOperation(String handle, String operationType) {
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(handle, "handle");
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(operationType, "operationType");
        Query query = session.createQuery(COUNT_QUERY);
        query.setString(0, handle);
        query.setString(1, operationType);
        Long value = (Long) query.uniqueResult();
        return (value > 0);
    }
}
