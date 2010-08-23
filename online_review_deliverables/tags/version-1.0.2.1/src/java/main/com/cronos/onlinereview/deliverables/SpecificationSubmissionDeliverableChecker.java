/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.deliverables;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.Deliverable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The SpecificationSubmissionDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class. The SQL
 * query used checks to see whether the specification is already submitted for phase. If so, it marks the deliverable as
 * completed, using the date of the specification submission.
 *
 * <p>This class is immutable.</p>
 *
 * @author isv
 * @version 1.0.2
 * @since 1.0.2
 */
public class SpecificationSubmissionDeliverableChecker extends SingleQuerySqlDeliverableChecker {

    /**
     * <p>Constructs new <code>SpecificationSubmissionDeliverableChecker</code> instance with specified connection
     * factory.</p>
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public SpecificationSubmissionDeliverableChecker(DBConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * <p>Gets the SQL query string to select the last modification date for the specification submission for the
     * project phase. Returned query will have 2 placeholders for the project_id and project_phase_id values.</p>
     *
     * @return The SQL query string to execute.
     */
    @Override
    protected String getSqlQuery() {
        return "SELECT s.modify_date " +
               "FROM submission s " +
               "INNER JOIN upload u ON s.upload_id = u.upload_id " +
               "WHERE s.submission_status_id <> 5 " +
               "AND s.submission_type_id = 2 " +
               "AND u.project_id = ? " +
               "AND u.resource_id = ? " +
               "AND ((SELECT COUNT(*) " +
               "      FROM project_phase pp " +
               "      WHERE pp.phase_type_id = 13 " +
               "      AND pp.project_id = ? " +
               "      AND pp.scheduled_start_time <= (SELECT pp2.scheduled_start_time FROM project_phase pp2 WHERE pp2.project_phase_id = ?)) " +
               "     = " +
               "     (SELECT COUNT(*) " +
               "      FROM submission s2 " +
               "      INNER JOIN upload u2 ON s2.upload_id = u2.upload_id " +
               "      WHERE s2.submission_status_id <> 5 " +
               "      AND s2.submission_type_id = 2 " +
               "      AND s2.create_date <= s.create_date " +
               "      AND u2.project_id = ?))";
    }

    /**
     * <p>Given a PreparedStatement representation of the SQL query returned by the getSqlQuery method, this method
     * extracts phase_id and project_id values from the deliverable and sets them as parameters of the
     * PreparedStatement.</p>
     *
     * @param deliverable The deliverable from which to get any needed parameters to set on the PreparedStatement.
     * @param statement The PreparedStatement representation of the SQL query returned by getSqlQuery.
     * @throws SQLException if any error occurs while setting the values to statement.
     */
    protected void fillInQueryParameters(Deliverable deliverable, PreparedStatement statement) throws SQLException {
        statement.setLong(1, deliverable.getProject());
        statement.setLong(2, deliverable.getResource());
        statement.setLong(3, deliverable.getProject());
        statement.setLong(4, deliverable.getPhase());
        statement.setLong(5, deliverable.getProject());
    }
}
