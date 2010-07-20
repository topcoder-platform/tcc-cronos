/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.deliverables;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The ApprovalDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class. The SQL query
 * used checks to see whether the approval review for the submission is complete. If so, it marks the deliverable as
 * completed, using the date of the approval/rejection comment.
 *
 * <p>This class is immutable.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ApprovalDeliverableChecker extends SingleQuerySqlDeliverableChecker {

    /**
     * Creates a new ApprovalDeliverableChecker.
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public ApprovalDeliverableChecker(DBConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * <p>
     * Gets the SQL query string to select the last modification date for the review scorecard for the
     * resource(reviewer)/submission. Returned query will have 2 placeholders for the resource_id and submission_id
     * values.
     * </p>
     *
     * @return The SQL query string to execute.
     */
    @Override
    protected String getSqlQuery() {
        return "SELECT rv.modify_date " +
                "FROM review rv " +
                "WHERE rv.committed = 1 " +
                "AND rv.resource_id = ? " +
                "AND rv.submission_id = ? " +
                "AND ((SELECT COUNT(*) " +
                "      FROM project_phase pp " +
                "      WHERE pp.phase_type_id = 11 " +
                "      AND pp.project_phase_id <= ?) " +
                "     = " +
                "     (SELECT COUNT(*) " +
                "      FROM review rv2 " +
                "      WHERE rv2.committed = 1 " +
                "      AND rv2.resource_id = rv.resource_id " +
                "      AND rv2.submission_id = rv.submission_id))";
    }

    /**
     * <p>
     * Given a PreparedStatement representation of the SQL query returned by the getSqlQuery method, this method
     * extracts resource_id and submission_id values from the deliverable and sets them as
     * parameters of the PreparedStatement.
     * </p>
     *
     * @param deliverable The deliverable from which to get any needed parameters to set on the PreparedStatement.
     * @param statement   The PreparedStatement representation of the SQL query returned by getSqlQuery.
     * @throws java.sql.SQLException if any error occurs while setting the values to statement.
     * @throws com.topcoder.management.deliverable.persistence.DeliverableCheckingException
     *                               if the deliverable is not per submission.
     */
    protected void fillInQueryParameters(Deliverable deliverable, PreparedStatement statement)
            throws SQLException, DeliverableCheckingException {
        statement.setLong(1, deliverable.getResource());
        statement.setLong(2, deliverable.getSubmission().longValue());
        statement.setLong(3, deliverable.getPhase());
    }
}
