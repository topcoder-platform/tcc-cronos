/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This interface defines the required methods for any persistence layer to be plugged into the
 * <code>AuditManager</code>. The three methods have the same definitions as the Create/Retrieve/Delete methods within
 * the manager.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>All implementations of this interface should handle their own thread safety, and must also be
 * able to be constructed through using the Object Factory - the intention is that they never need to be constructed
 * directly.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface AuditPersistence {
    /**
     * <p>
     * Persists information about a new audit record, including all of its contained 'details'. In addition, the
     * implementation should log any records or details that cannot be added, before throwing an exception.
     * </p>
     *
     * @param record The record header and details data that is to be added to persistence. Cannot be null.
     *
     * @throws IllegalArgumentException if record is null, or if not null field is not set for given audit header, or
     *         the contained 'details'.
     * @throws AuditPersistenceException If there are problems in adding the information.
     */
    void createAuditRecord(AuditHeader record) throws AuditPersistenceException;

    /**
     * <p>
     * Searches through persistent information for audit records which fill certain criteria. They are collected then
     * returned as a (possibly empty) array of AuditHeaders. In addition, if there are any problems searching, the
     * implementation should log the search criteria before throwing an exception.
     * </p>
     *
     * @param filter The criteria that all values in the return must pass - or null if all audit records are to be
     *        obtained.
     *
     * @return An array (non-null, possibly empty) of Audit records which match the criteria.
     *
     * @throws AuditPersistenceException If there are any problems searching the audits.
     */
    AuditHeader[] searchAudit(Filter filter) throws AuditPersistenceException;

    /**
     * <p>
     * Removes an audit header, and all of its details, from persistence. In addition, the implementation should log
     * whenever the audit / details cannot be removed, before throwing an exception.
     * </p>
     *
     * @param auditHeaderId The unique ID of the audit record to be removed
     *
     * @return boolean - true if anything was removed from the database, otherwise false.
     *
     * @throws AuditPersistenceException If there are problems in removing the data.
     */
    boolean rollbackAuditRecord(long auditHeaderId) throws AuditPersistenceException;
}
