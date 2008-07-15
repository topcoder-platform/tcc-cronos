/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.util.Date;

import com.topcoder.object.auditor.AuditDAO;
import com.topcoder.object.auditor.AuditDAOException;
import com.topcoder.object.auditor.AuditEntry;

/**
 * <p>
 * This is a <code>MockAuditDAO</code> class implements <code>AuditDAO</code> interface defining the
 * methods to store and retrieve audit.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuditDAO implements AuditDAO {

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public MockAuditDAO() {
        // empty
    }

    /**
     * <p>
     * Checks whether the given Object is not null.
     * </p>
     *
     * @param obj
     *            the argument to check
     * @param name
     *            the name of the argument
     * @throws IllegalArgumentException
     *             if the given Object(obj) is null
     */
    private static void checkNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("[ " + name + " ] should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the given String is not null and not empty(trimmed).
     * </p>
     *
     * @param str
     *            the String to check
     * @param name
     *            the name of first argument
     * @throws IllegalArgumentException
     *             if the given string is null or empty string (trimmed)
     */
    private static void checkString(String str, String name) {
        checkNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("[" + name + "] should not be empty (trimmed).");
        }
    }

    /**
     * <p>
     * Stores the given audit entry, in this class .
     * </p>
     *
     * <p>
     * In this class, just return the passed in entry.
     * </p>
     *
     * @param entry
     *            the audit entry to store
     * @return the created audit entry
     * @throws IllegalArgumentException
     *             if the argument is null
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public AuditEntry audit(AuditEntry entry) throws AuditDAOException {
        checkNull(entry, "entry");
        return entry;
    }

    /**
     * <p>
     * Gets creation and modification audit entries of the given data, return empty array if none is found.
     * </p>
     *
     * <p>
     * In this class, just return empty array.
     * </p>
     *
     * @param newData
     *            the new data
     * @return creation and modification audit entries of the given data, return empty array if none is found
     * @throws IllegalArgumentException
     *             if the argument is null
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public AuditEntry[] findByNewData(Object newData) throws AuditDAOException {
        checkNull(newData, "newData");
        return new AuditEntry[0];
    }

    /**
     * <p>
     * Gets deletion and modification audit entries of the given data, return empty array if none is found.
     * </p>
     *
     * <p>
     * In this class, just return empty array.
     * </p>
     *
     * @param oldData
     *            the old data
     * @return deletion and modification audit entries of the given data, return empty array if none is found
     * @throws IllegalArgumentException
     *             if the argument is null
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public AuditEntry[] findByOldData(Object oldData) throws AuditDAOException {
        checkNull(oldData, "oldData");
        return new AuditEntry[0];
    }

    /**
     * <p>
     * Gets audit entries of the given user, return empty array if none is found.
     * </p>
     *
     * <p>
     * In this class, just return empty array.
     * </p>
     *
     * @param user
     *            the user name
     * @return audit entries of the given user, return empty array if none is found
     * @throws IllegalArgumentException
     *             if the argument is null or empty
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public AuditEntry[] findByUser(String user) throws AuditDAOException {
        checkString(user, "user");
        return new AuditEntry[0];
    }

    /**
     * <p>
     * Gets audit entries within the given time range (inclusive), return empty array if none is found.
     * </p>
     *
     * <p>
     * In this class, just return empty array.
     * </p>
     *
     * @param start
     *            the start time
     * @param end
     *            the end time
     * @return audit entries within the given time range (inclusive), return empty array if none is found
     * @throws IllegalArgumentException
     *             if any argument is null, or end date is earlier than start date
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public AuditEntry[] findByDateRange(Date start, Date end) throws AuditDAOException {
        checkNull(start, "start");
        checkNull(end, "end");
        if (start.compareTo(end) == 1) {
            throw new IllegalArgumentException("end date is earlier than start date");
        }

        return new AuditEntry[0];
    }

    /**
     * <p>
     * Deletes creation and modification audit entries of the given data.
     * </p>
     *
     * <p>
     * In this class, just return <i>0</i>.
     * </p>
     *
     * @param newData
     *            the data to delete entries for
     * @return the number of entries deleted
     * @throws IllegalArgumentException
     *             if the argument is null
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public int deleteByNewData(Object newData) throws AuditDAOException {
        checkNull(newData, "newData");
        return 0;
    }

    /**
     * <p>
     * Deletes deletion and modification audit entries of the given data.
     * </p>
     *
     * <p>
     * In this class, just return <i>0</i>.
     * </p>
     *
     * @param oldData
     *            the data to delete audit entries for
     * @return the number of entries deleted
     * @throws IllegalArgumentException
     *             if the argument is null
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public int deleteByOldData(Object oldData) throws AuditDAOException {
        checkNull(oldData, "oldData");
        return 0;
    }

    /**
     * <p>
     * Deletes audit entries of the given user.
     * </p>
     *
     * <p>
     * In this class, just return <i>0</i>.
     * </p>
     *
     * @param user
     *            the user to delete audit entries for
     * @return the number of entries deleted
     * @throws IllegalArgumentException
     *             if the argument is null or empty
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public int deleteByUser(String user) throws AuditDAOException {
        checkString(user, "user");
        return 0;
    }

    /**
     * <p>
     * Deletes audit entries within the given time range (inclusive).
     * </p>
     *
     * <p>
     * In this class, just return <i>0</i>.
     * </p>
     *
     * @param start
     *            the start time
     * @param end
     *            the end time
     * @return the number of entries deleted
     * @throws IllegalArgumentException
     *             if any argument is null, or end date is earlier than start date
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     */
    public int deleteByDateRange(Date start, Date end) throws AuditDAOException {
        checkNull(start, "start");
        checkNull(end, "end");
        if (start.compareTo(end) == 1) {
            throw new IllegalArgumentException("end date is earlier than start date");
        }
        return 0;
    }
}
