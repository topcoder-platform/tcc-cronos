/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao;

import com.topcoder.web.common.model.AuditRecord;

/**
 * <p>
 * This interface defines methods for persisting audit records and checking whether some operation was previously
 * performed by specific user.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> Implementations of this interface are required to be thread safe when entities passed
 * to them are used by the caller in thread safe manner.
 * </p>
 * @author saarixx, kalc
 * @version 1.0
 */
public interface AuditDAO {
    /**
     * <p>
     * Saves the given audit record.
     * </p>
     * @param record
     *            the audit record to be saved
     * @throws IllegalArgumentException
     *             if record is null
     */
    public void audit(AuditRecord record);

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
    public boolean hasOperation(String handle, String operationType);
}
