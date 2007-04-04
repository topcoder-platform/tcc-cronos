/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This interface defines the common contract that <code>AuditDelegate</code>, <code>AuditSessionBean</code>,
 * and <code>AuditLocalObject</code> all implement. These common methods are the main public functionality of the
 * component which can allow a user to audit database operations, rollback audits, and to find existing audits.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Implementations of this interface are required to be thread safe.
 * </p>

 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface AuditManager {
    /**
     * <p>
     * Adds an audit record header, and all of its details, to persistence. This method is delegated to the
     * persistence layer.
     * </p>
     *
     * @param record The audit record header containing information to be added to the database - cannot be null.
     *
     * @throws IllegalArgumentException if record is null, or if not null field is not set for given audit header, or
     *         the contained 'details'.
     * @throws AuditManagerException If there are problems in adding the information.
     */
    void createAuditRecord(AuditHeader record) throws AuditManagerException;

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
     *        matches are found, but will never be null.
     *
     * @throws AuditManagerException If there are any problems searching the audits.
     */
    AuditHeader[] searchAudit(Filter filter) throws AuditManagerException;

    /**
     * <p>
     * Removes an audit header, and all of its details, from persistence. This method is delegated to the
     * persistence layer.
     * </p>
     *
     * @param auditHeaderId The ID of the audit header to remove.
     *
     * @return boolean - true if anything was removed from the database, otherwise false.
     *
     * @throws AuditManagerException If there are problems in removing the data.
     */
    boolean rollbackAuditRecord(long auditHeaderId) throws AuditManagerException;
}
