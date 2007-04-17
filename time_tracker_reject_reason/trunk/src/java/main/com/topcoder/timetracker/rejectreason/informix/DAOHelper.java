/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * <p>
 * Helper class for Filter DAO classes. It contains methods to create AuditHeader and AuditDetail, it also contains a
 * utility to release database resources.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
final class DAOHelper {
    /**
     * The private constructor to avoid instantiation.
     */
    private DAOHelper() {
        // Do nothing
    }

    /**
     * Creates a new AuditHeader with the given data.
     *
     * @param actionType the action type of the audit header.
     * @param tableName the table name of the audit header.
     * @param entityId the entity id of the audit header.
     * @param companyId the company id of the audit header.
     * @param details the audit details of the audit header.
     * @param area the application area of the audit header.
     * @param now the creation date of the audit header.
     * @param username the name of the creation user of the audit header.
     *
     * @return the created AuditHeader object with the given data.
     */
    static AuditHeader createAdutiHeader(int actionType, String tableName, long entityId, long companyId,
        AuditDetail[] details, ApplicationArea area, Date now, String username) {
        AuditHeader header = new AuditHeader();
        header.setActionType(actionType);
        header.setTableName(tableName);
        header.setEntityId(entityId);
        header.setCompanyId(companyId);
        header.setDetails(details);
        header.setApplicationArea(area);
        header.setCreationDate(new Timestamp(now.getTime()));
        header.setCreationUser(username);

        return header;
    }

    /**
     * Creates a new AuditDetail with the given data.
     *
     * @param columnName the column name of audit detail.
     * @param oldValue old value of the column.
     * @param newValue new value of the column.
     *
     * @return the created AuditDetail object with the given data.
     */
    static AuditDetail createAuditDetail(String columnName, String oldValue, String newValue) {
        AuditDetail detail = new AuditDetail();
        detail.setColumnName(columnName);
        detail.setOldValue(oldValue);
        detail.setNewValue(newValue);

        return detail;
    }

    /**
     * Releases database resources.
     *
     * @param rs the result set to release.
     * @param statement the prepared statement to release.
     * @param connection the database connection to release.
     */
    static void releaseResources(ResultSet rs, PreparedStatement statement, Connection connection) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // Ignore
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // Ignore
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}
