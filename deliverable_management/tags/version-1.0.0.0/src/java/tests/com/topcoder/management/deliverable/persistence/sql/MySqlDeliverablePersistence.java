/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;

/**
 * A database implementation for DeliverablePersistence.
 *
 * @author Chenhong
 * @version 1.0
 */
public class MySqlDeliverablePersistence implements DeliverablePersistence {

    /**
     * Selects the deliverable data for the given id and submission id.
     */
    private static final String SELECT_DELIVERABLE_SUBMISSION = "SELECT deliverable_id, "
            + "submission_id, phase_type_id, upload.resource_id, "
            + "deliverable_lu.name, deliverable_lu.description, per_submission, required, deliverable_lu.create_user, "
            + "deliverable_lu.create_date, deliverable_lu.modify_user, deliverable_lu.modify_date, "
            + "project.project_id FROM deliverable_lu, project, submission, submission_status_lu, upload "
            + "WHERE submission.submission_status_id = submission_status_lu.submission_status_id AND "
            + "submission_status_lu.name = 'Active' AND submission.upload_id = " + "upload.upload_id "
            + "AND deliverable_lu.per_submission = 1 "
            + "AND upload.project_id = project.project_id AND deliverable_lu.deliverable_id = ? AND "
            + "submission.submission_id = ?";

    /**
     * Selects the delivery using IN clues. The result doe not contains submission id.
     */
    private static final String SELECT_DELIV = "SELECT deliverable_id, phase_type_id, resource_role_id, name, "
            + "description, per_submission, required, deliverable_lu.create_user, deliverable_lu.create_date, "
            + "deliverable_lu.modify_user, deliverable_lu.modify_date, project.project_id, resource_id "
            + "FROM deliverable_lu, project, upload WHERE project.project_id = upload.upload_id "
            + "AND deliverable_id  IN ";

    /**
     * Selects the deliverable - submission ids pairs.
     */
    private static final String SELECT_DELIV_SUBMISSION = "SELECT deliverable_id, submission_id "
            + "FROM deliverable_lu, submission " + "INNER JOIN submission_status_lu ON "
            + "submission.submission_status_id = submission_status_lu.submission_status_id "
            + "WHERE submission_status_lu.name = 'Active' AND deliverable_lu.deliverable_id IN ";

    /**
     * Selects the full deliverable for given ids and submissions.
     */
    private static final String SELECT_DELIVERABLE_FOR_PAIRS = "SELECT deliverable_id, submission_id, "
            + "phase_type_id, upload.resource_id, "
            + "deliverable_lu.name, deliverable_lu.description, per_submission, required, deliverable_lu.create_user, "
            + "deliverable_lu.create_date, deliverable_lu.modify_user, deliverable_lu.modify_date, "
            + "project.project_id FROM deliverable_lu, project, submission, submission_status_lu, upload "
            + "WHERE submission.submission_status_id = submission_status_lu.submission_status_id AND "
            + "submission_status_lu.name = 'Active' AND submission.upload_id = upload.upload_id "
            + "AND deliverable_lu.per_submission = 1 AND upload.project_id = project.project_id AND ";

    /**
     * The name of the connection producer to use when a connection to the database is retrieved
     * from the DBConnectionFactory.
     *
     */
    private final String connectionName;

    /**
     * The connection factory to use when a connection to the database is needed.
     *
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * SqlDeliverablePersistence constructor: Creates a new SqlDeliverablePersistence. The connectionName field is
     * set to null.
     *
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public MySqlDeliverablePersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * SqlDeliverablePersistence constructor: Creates a new SqlDeliverablePersistence. All fields are set to the
     * given values.
     *
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     * @param connectionName The name of the connection to use. Can be null.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public MySqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName) {
        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Loads the deliverables associated with the given deliverable id. There may be more than
     * one deliverable returned if the deliverable is a "per submission" deliverable. Hence the need for an array
     * return type. If there is no matching deliverable in the persistence, an empty array should be returned.
     * </p>
     *
     * @return The matching deliverable (possibly expanded by matching with each active submission, if it is a "per
     *         submission" deliverable), or an empty array.
     * @param deliverableId The id of the deliverable
     * @throws IllegalArgumentException If deliverableId is <= 0
     * @throws DeliverablePersistenceException If there is an error reading the persistence
     */
    public Deliverable[] loadDeliverables(long deliverableId) throws DeliverablePersistenceException {
        return loadDeliverables(new long[] {deliverableId});
    }

    /**
     * Creates the Deliverable instance using the data from reslt set.
     *
     * @param rs the source result set.
     * @param isPerSubmission indicates if submission id is present.
     * @return the Deliverable.
     * @throws SQLException if any error occurs.
     */
    private Deliverable populateDeliverable(ResultSet rs, boolean isPerSubmission) throws SQLException {
        long project = rs.getLong("project_id");
        long phase = rs.getLong("phase_type_id");
        long resource = rs.getLong("resource_id");
        Long submission = null;
        if (isPerSubmission) {
            submission = new Long(rs.getLong("submission_id"));
        }
        boolean isRequired = rs.getBoolean("required");
        Deliverable deliverable = new Deliverable(project, phase, resource, submission, isRequired);
        deliverable.setId(rs.getLong("deliverable_id"));
        deliverable.setName(rs.getString("name"));
        deliverable.setDescription(rs.getString("description"));
        deliverable.setCreationTimestamp(rs.getTimestamp("create_date"));
        deliverable.setCreationUser(rs.getString("create_user"));
        deliverable.setModificationTimestamp(rs.getTimestamp("modify_date"));
        deliverable.setModificationUser(rs.getString("modify_user"));

        return deliverable;
    }

    /**
     * <p>
     * Creates the database connection using the DbConnectionFactory. If the <code>connectionName</code> is
     * <code>null</code>, default connection will be created.
     * </p>
     *
     * @return the database connection.
     * @throws DeliverablePersistenceException if error occurs while creating database connection.
     */
    private Connection createConnection() throws DeliverablePersistenceException {
        try {
            Connection conn = null;
            if (connectionName == null) {
                conn = connectionFactory.createConnection();
            } else {

                conn = connectionFactory.createConnection(connectionName);
            }

            return conn;
        } catch (DBConnectionException ex) {
            throw new DeliverablePersistenceException("Error occurs while creating connection: " + ex);
        }
    }

    /**
     * <p>
     * Loads the deliverable associated with the given submission. The deliverable must be a "per
     * submission" deliverable and the given submission must be "Active". If this is not the case, null is
     * returned.
     * </p>
     *
     * @return The deliverable, or null if there is no deliverable for the given id, or submission is not an
     *         'Active' submission
     * @param deliverableId The id of the deliverable
     * @param submissionId The id of the submission the deliverable should be associated with
     * @throws IllegalArgumentException If deliverableId is <= 0
     * @throws DeliverablePersistenceException If there is an error reading the persistence data
     */
    public Deliverable loadDeliverable(long deliverableId, long submissionId)
        throws DeliverablePersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = createConnection();
        try {
            pstmt = conn.prepareStatement(SELECT_DELIVERABLE_SUBMISSION);
            pstmt.setLong(1, deliverableId);
            pstmt.setLong(2, submissionId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return populateDeliverable(rs, true);
            }

        } catch (SQLException ex) {
            throw new DeliverablePersistenceException("Error occurs while retrieving the deliverable.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }

        return null;
    }

    /**
     * <p>
     * Loads all Deliverables with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param deliverableIds The ids of deliverables to load
     * @return The loaded deliverables
     * @throws IllegalArgumentException if any id is <= 0
     * @throws DeliverablePersistenceException if there is an error reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds) throws DeliverablePersistenceException {

        // If the parameter array is empty, simply return a 0-size array.
        if (deliverableIds.length == 0) {
            return new Deliverable[0];
        }

        Connection conn = createConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = DBUtil.createInQueryStatement(SELECT_DELIV, deliverableIds, conn);

            rs = pstmt.executeQuery();
            List result = new ArrayList();
            List perSubmission = new ArrayList();
            while (rs.next()) {
                if (rs.getBoolean("per_submission")) {
                    perSubmission.add(new Long(rs.getLong("deliverable_id")));
                } else {
                    result.add(populateDeliverable(rs, false));
                }
            }

            if (perSubmission.size() > 0) {
                result.addAll(Arrays.asList(getDeliverablesPerSubmission(conn, perSubmission)));
            }

            return (Deliverable[]) result.toArray(new Deliverable[result.size()]);
        } catch (SQLException ex) {
            throw new DeliverablePersistenceException("Error occurs during database operation.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }

    }

    /**
     * Returns requested deliverables that are per submission with appropriate submissions.
     *
     * @param conn the connection to use.
     * @param perSubmission the list of deliverables ids.
     * @return the array of deliverables.
     * @throws SQLException if any database error occurs.
     * @throws DeliverablePersistenceException if any error occurs.
     */
    private Deliverable[] getDeliverablesPerSubmission(Connection conn, List perSubmission) throws SQLException,
        DeliverablePersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_DELIV_SUBMISSION
                    + DBUtil.createQuestionMarks(perSubmission.size()));
            for (int i = 0; i < perSubmission.size(); i++) {
                pstmt.setLong(i + 1, ((Long) perSubmission.get(i)).longValue());
            }

            rs = pstmt.executeQuery();

            List submissions = new ArrayList();
            perSubmission.clear();
            while (rs.next()) {
                perSubmission.add(new Long(rs.getLong("deliverable_id")));
                submissions.add(new Long(rs.getLong("submission_id")));
            }

            return loadDeliverables(DBUtil.listToArray(perSubmission), DBUtil.listToArray(submissions));
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    /**
     * <p>
     * Loads the deliverables associated with the given submissions. The deliverables must be
     * "per submission" deliverables and the given submissions must be "Active". Pairs of ids not meeting this
     * requirement will not be returned.
     * </p>
     *
     * @param deliverableIds The ids of deliverables to load
     * @param submissionIds The ids ofthe submission for each deliverable
     * @return The loaded deliverables
     * @throws IllegalArgumentException If the two arguments do not have the same number of elements
     * @throws IllegalArgumentException if any id (in either array) is <= 0
     * @throws DeliverablePersistenceException if there is an error reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] submissionIds)
        throws DeliverablePersistenceException {

        // if the parameter array is empty, simply return a 0-size array.
        if (deliverableIds.length == 0) {
            return new Deliverable[0];
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = createConnection();

        try {
            pstmt = conn.prepareStatement(SELECT_DELIVERABLE_FOR_PAIRS + createWhereClause(deliverableIds.length));

            for (int i = 0; i < deliverableIds.length; i++) {
                pstmt.setLong(i * 2 + 1, deliverableIds[i]);
                pstmt.setLong(i * 2 + 2, submissionIds[i]);
            }
            rs = pstmt.executeQuery();
            List result = new ArrayList();

            while (rs.next()) {
                result.add(populateDeliverable(rs, true));
            }

            return (Deliverable[]) result.toArray(new Deliverable[result.size()]);
        } catch (SQLException ex) {
            throw new DeliverablePersistenceException("Error occurs while retrieving the deliverable.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    /**
     * Creates the WHERE caluse for the query that selects deliverable and submission.
     *
     * @param count the number of pairs in the query.
     *
     * @return the party of the WHERE caluse
     */
    private static String createWhereClause(int count) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" (");
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                buffer.append(" OR ");
            }
            buffer.append(" (deliverable_lu.deliverable_id = ? AND submission.submission_id = ?) ");
        }
        buffer.append(" )");
        return buffer.toString();
    }
}
