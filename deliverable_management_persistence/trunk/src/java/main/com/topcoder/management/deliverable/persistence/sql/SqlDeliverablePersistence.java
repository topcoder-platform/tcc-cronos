/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.management.deliverable.persistence.PersistenceException;
import com.topcoder.management.deliverable.persistence.sql.Helper.DataType;
import com.topcoder.management.deliverable.persistence.sql.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import java.sql.Connection;

/**
 * <p>
 * The SqlDeliverablePersistence class implements the DeliverablePersistence
 * interface, in order to persist to the database structure given in the
 * deliverable_management.sql script.
 * </p>
 *
 * <p>
 * This class does not cache a Connection to the database. Instead a new
 * Connection is used on every method call. PreparedStatements should be used to
 * execute the SQL statements.
 * </p>
 *
 * <p>
 * Sample configuration:
 * <pre>
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *       &lt;Property name="connections"&gt;
 *           &lt;Property name="default"&gt;
 *               &lt;Value&gt;informix_connection&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="informix_connection"&gt;
 *               &lt;Property name="producer"&gt;
 *                   &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *               &lt;/Property&gt;
 *               &lt;Property name="parameters"&gt;
 *                   &lt;Property name="jdbc_driver"&gt;
 *                       &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="jdbc_url"&gt;
 *                       &lt;Value&gt;jdbc:informix-sqli://localhost:9088/dm:informixserver=informix_db&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="user"&gt;
 *                       &lt;Value&gt;informix&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="password"&gt;
 *                       &lt;Value&gt;test&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *               &lt;/Property&gt;
 *           &lt;/Property&gt;
 *       &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * <p>
 * Sample usage:
 *
 * <pre>
 *
 * // first a DBConnectionFactory instance is created.
 * DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
 * DBConnectionFactoryImpl.class.getName());
 *
 * // create the instance of SqlDeliverablePersistence class, using the
 * // default connection name
 * DeliverablePersistence persistence1 = new SqlDeliverablePersistence(connectionFactory);
 *
 * // or create the instance of SqlDeliverablePersistence class, using the
 * // given connection name
 * DeliverablePersistence persistence2 = new SqlDeliverablePersistence(connectionFactory,
 * "informix_connection");
 *
 * // load deliverable from the persistence
 * Deliverable deliverable = persistence.loadDeliverable(2, 1, 1, 1);
 * // the above loading can be batched.
 * Deliverable[] deliverables1 = persistence.loadDeliverables(
 * new long[]{2, 1}, new long[]{1, 2}, new long[]{1, 1}, new long[]{1, 1});
 * </pre>
 *
 * <p>Changes in 1.1: queries were updated to ensure that submission types are
 * specified for submissions when loading deliverables.
 * </p>
 *
 * <p><strong>Thread Safety:</strong> This class is immutable and thread-safe in the sense that
 * multiple threads can not corrupt its internal data structures. However, the
 * results if used from multiple threads can be unpredictable as the database is
 * changed from different threads. This can equally well occur when the
 * component is used on multiple machines or multiple instances are used, so
 * this is not a thread-safety concern.
 * </p>
 *
 * @author aubergineanode, saarixx, urtks, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class SqlDeliverablePersistence implements DeliverablePersistence {

    /**
     * <p>
     * Logger instance using the class name as category.
     * </p>
     */
    private static final Log LOGGER = LogFactory.getLog(SqlDeliverablePersistence.class.getName());

    /**
     * <p>
     * Represents the sql statement to load deliverables with submission.
     * </p>
     *
     * <p>
     * Changes in 1.1: added join with submission_type_lu - to ensure that submission has assigned submission type.
     * </p>
     */
    private static final String LOAD_DELIVERABLES_WITH_SUBMISSION_SQL = "SELECT "
            + "upload.project_id, project_phase.project_phase_id, resource.resource_id, "
            + "submission.submission_id, deliverable_lu.required, "
            + "deliverable_lu.deliverable_id, deliverable_lu.create_user, deliverable_lu.create_date, "
            + "deliverable_lu.modify_user, deliverable_lu.modify_date, "
            + "deliverable_lu.name, deliverable_lu.description "
            + "FROM deliverable_lu "
            + "INNER JOIN resource ON resource.resource_role_id = deliverable_lu.resource_role_id "
            + "INNER JOIN project_phase ON project_phase.project_id = resource.project_id AND "
            + "project_phase.phase_type_id = deliverable_lu.phase_type_id "
            + "INNER JOIN upload ON upload.project_id = resource.project_id "
            + "INNER JOIN submission ON submission.upload_id = upload.upload_id "
            + "INNER JOIN submission_status_lu ON submission.submission_status_id = "
            + "submission_status_lu.submission_status_id "
            + "INNER JOIN submission_type_lu ON submission.submission_type_id = submission_type_lu.submission_type_id "
            + "WHERE deliverable_lu.per_submission = 1 AND submission_status_lu.name = 'Active' AND ";

    /**
     * <p>
     * Represents the column types for the result set which is returned by
     * executing the sql statement to load deliverables with submission.
     * </p>
     */
    private static final DataType[] LOAD_DELIVERABLES_WITH_SUBMISSION_COLUMN_TYPES = new DataType[]{
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
        Helper.BOOLEAN_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * <p>
     * Represents the sql statement to load deliverables without submission.
     * </p>
     */
    private static final String LOAD_DELIVERABLES_WITHOUT_SUBMISSION_SQL = "SELECT "
            + "resource.project_id, project_phase.project_phase_id, "
            + "resource.resource_id, deliverable_lu.required, "
            + "deliverable_lu.deliverable_id, deliverable_lu.create_user, deliverable_lu.create_date, "
            + "deliverable_lu.modify_user, deliverable_lu.modify_date, "
            + "deliverable_lu.name, deliverable_lu.description "
            + "FROM deliverable_lu "
            + "INNER JOIN resource ON resource.resource_role_id = deliverable_lu.resource_role_id "
            + "INNER JOIN project_phase ON project_phase.project_id = resource.project_id AND "
            + "project_phase.phase_type_id = deliverable_lu.phase_type_id "
            + "WHERE deliverable_lu.per_submission = 0 AND ";


    /**
     * <p>
     * Represents the column types for the result set which is returned by
     * executing the sql statement to load deliverables without submission.
     * </p>
     */
    private static final DataType[] LOAD_DELIVERABLES_WITHOUT_SUBMISSION_COLUMN_TYPES = new DataType[]{
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.BOOLEAN_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * <p>
     * The name of the connection producer to use when a
     * connection to the database is retrieved from the DBConnectionFactory.
     * This field is immutable and can be null or non-null. When non-null, no
     * restrictions are applied to the field. When this field is null, the
     * createConnection() method is used to get a connection. When it is
     * non-null, the createConnection(String) method is used to get a
     * connection. This field is not exposed by this class, and is used whenever
     * a connection to the database is needed (i.e. in every method).
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * The connection factory to use when a connection to the
     * database is needed. This field is immutable and must be non-null. This
     * field is not exposed by this class and is used whenever a connection to
     * the database is needed (i.e. in every method).
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>Creates a new SqlDeliverablePersistence. Default connection name of the
     * connectionFactory will be used.</p>
     *
     * @param connectionFactory The connection factory to use for getting connections to the
     *                          database.
     *
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public SqlDeliverablePersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * <p>Creates a new SqlDeliverablePersistence, with the given connectionFactory
     * and connectionName.</p>
     *
     * @param connectionFactory The connection factory to use for getting connections to the
     *                          database.
     * @param connectionName    The name of the connection to use. Can be null, which means
     *                          default connection name will be used.
     *
     * @throws IllegalArgumentException If connectionFactory is null, or connectionName is empty (trimmed).
     */
    public SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName) {
        Helper.assertObjectNotNull(connectionFactory, "connectionFactory");
        Helper.assertStringNotEmpty(connectionName, "connectionName");

        LOGGER.log(Level.INFO,
                "Instantiate SqlDeliverablePersistence with connectionFactory and connectionName:" + connectionName);

        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>Loads the deliverables associated with the given deliverable id and resource id.
     * There may be more than one deliverable returned. If there is no matching
     * deliverable in the persistence, an empty array should be returned.</p>
     *
     * @param deliverableId The id of the deliverable
     * @param resourceId    The resource id of deliverable
     * @param phaseId       The phase id of deliverable
     *
     * @return The matching deliverables (possibly expanded by matching with
     *         each active submission, if it is a "per submission" deliverable),
     *         or an empty array.
     *
     * @throws IllegalArgumentException If deliverableId is <= 0 or resourceId <=0
     * @throws DeliverablePersistenceException
     *                                  If there is an error when reading the persistence
     */
    public Deliverable[] loadDeliverables(long deliverableId, long resourceId, long phaseId)
        throws DeliverablePersistenceException {
        Helper.assertIdNotUnset(deliverableId, "deliverableId");
        Helper.assertIdNotUnset(resourceId, "resourceId");
        Helper.assertIdNotUnset(phaseId, "phaseId");

        LOGGER.log(Level.INFO, new LogMessage("Deliverable", new Long(deliverableId), null,
                "load Deliverables with resourceId:" + resourceId + " phaseId:" + phaseId
                + ", delegate to loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds)."));
        return loadDeliverables(new long[]{deliverableId}, new long[]{resourceId}, new long[]{phaseId});
    }

    /**
     * <p>Loads the deliverable associated with the given submission and resource. The
     * deliverable must be a "per submission" deliverable and the given
     * submission must be "Active". If this is not the case, null is returned.</p>
     *
     * @param deliverableId The id of the deliverable
     * @param resourceId    The resource id of deliverable
     * @param phaseId       The phase id of deliverable
     * @param submissionId  The id of the submission the deliverable should be associated
     *                      with
     *
     * @return The deliverable, or null if there is no "per submission"
     *         deliverable for the given id, or submission is not an 'Active'
     *         submission
     *
     * @throws IllegalArgumentException If deliverableId, resourceId or submissionId is <= 0
     * @throws DeliverablePersistenceException
     *                                  If there is an error when reading the persistence data
     */
    public Deliverable loadDeliverable(long deliverableId, long resourceId, long phaseId, long submissionId)
        throws DeliverablePersistenceException {
        Helper.assertIdNotUnset(deliverableId, "deliverableId");
        Helper.assertIdNotUnset(resourceId, "resourceId");
        Helper.assertIdNotUnset(phaseId, "phaseId");
        Helper.assertIdNotUnset(submissionId, "submissionId");

        LOGGER.log(Level.INFO, new LogMessage("Deliverable", new Long(deliverableId), null,
                "load Deliverable with resourceId:" + resourceId
                        + " phaseId:" + phaseId + " and submissionId:" + submissionId
                        + ", delegate to loadDeliverables(long[] deliverableIds,"
                        + " long[] resourceIds, long[] phaseIds, long[] submissionIds)."));

        Deliverable[] deliverables = loadDeliverables(new long[]{deliverableId},
                new long[]{resourceId}, new long[]{phaseId}, new long[]{submissionId});
        return deliverables.length == 0 ? null : deliverables[0];
    }

    /**
     * <p>Loads all Deliverables with the given ids and resource ids from persistence.
     * May return a 0-length array.</p>
     *
     * @param deliverableIds The ids of deliverables to load
     * @param resourceIds    The resource ids of deliverables to load
     * @param phaseIds       The phase ids of deliverables to load
     *
     * @return The loaded deliverables
     *
     * @throws IllegalArgumentException if deliverableIds or resourceIds is null or any id is <= 0
     * @throws IllegalArgumentException If the two arguments do not have the same number of elements
     * @throws DeliverablePersistenceException
     *                                  if there is an error when reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds)
        throws DeliverablePersistenceException {
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(deliverableIds, "deliverableIds");
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(resourceIds, "resourceIds");
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(phaseIds, "phaseIds");

        if (deliverableIds.length != resourceIds.length
                || deliverableIds.length != phaseIds.length) {
            throw new IllegalArgumentException("deliverableIds, resourceIds and phaseIds should have"
                    + " the same number of elements.");
        }

        // simply return an empty Deliverable array if deliverableIds is empty
        if (deliverableIds.length == 0) {
            return new Deliverable[0];
        }

        LOGGER.log(Level.INFO, new LogMessage("Deliverable", null, null,
                "load Deliverables with deliverableIds:" + Helper.getIdString(deliverableIds)
                        + ", resourceId:" + Helper.getIdString(resourceIds) + " and phaseId:"
                        + Helper.getIdString(phaseIds)));

        // build the match condition string.
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (int i = 0; i < deliverableIds.length; ++i) {
            if (i != 0) {
                stringBuffer.append(" OR ");
            }
            stringBuffer.append('(');
            stringBuffer.append("deliverable_lu.deliverable_id=");
            stringBuffer.append(deliverableIds[i]);
            stringBuffer.append(" AND ");
            stringBuffer.append("resource.resource_id=");
            stringBuffer.append(resourceIds[i]);
            stringBuffer.append(" AND ");
            stringBuffer.append("project_phase.project_phase_id=");
            stringBuffer.append(phaseIds[i]);
            stringBuffer.append(")");
        }
        stringBuffer.append(')');
        String matchCondition = stringBuffer.toString();

        Connection conn = null;
        try {
            try {
                // since two queries are needed, auto-commit mode is
                // disabled to ensure that the database snapshot does not change
                // during the two queries.
                conn = Helper.createConnection(connectionFactory, connectionName, true, true);

                // get the non-"per submission" deliverables
                Deliverable[] deliverablesWithoutSubmission = loadDeliverablesWithoutSubmission(
                        conn, matchCondition);
                // get the "per submission" deliverables
                Deliverable[] deliverablesWithSubmission = loadDeliverablesWithSubmission(conn,
                        matchCondition);

                // merge the two arrays into one array
                Deliverable[] deliverables = new Deliverable[deliverablesWithoutSubmission.length
                        + deliverablesWithSubmission.length];
                System.arraycopy(deliverablesWithoutSubmission, 0, deliverables, 0,
                        deliverablesWithoutSubmission.length);
                System.arraycopy(deliverablesWithSubmission, 0, deliverables,
                        deliverablesWithoutSubmission.length, deliverablesWithSubmission.length);

                return deliverables;

                // since the connection is read-only, no commit or rollback
                // operations are needed.
            } finally {
                Helper.closeConnection(conn);
            }
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage("Deliverable", null, null,
                    "Fail to load Deliverables with deliverableIds:" + Helper.getIdString(deliverableIds)
                            + ", resourceId:" + Helper.getIdString(resourceIds)
                            + " and phaseId:" + Helper.getIdString(phaseIds), e));
            // wrap PersistenceException
            throw new DeliverablePersistenceException(
                    "Unable to load deliverables without submission from the database.", e);
        }
    }

    /**
     * <p>Loads the deliverables associated with the given submissions and resource. The
     * deliverables must be "per submission" deliverables and the given
     * submissions must be "Active". Pairs of ids not meeting this requirement
     * will not be returned.</p>
     *
     * @param deliverableIds The ids of deliverables to load
     * @param resourceIds    The resource ids of deliverables to load
     * @param phaseIds       The phase ids of deliverables to load
     * @param submissionIds  The ids of the submission for each deliverable
     *
     * @return The loaded deliverables
     *
     * @throws IllegalArgumentException if any array is null or any id (in either array) is <= 0
     * @throws IllegalArgumentException If the three arguments do not have the same number of elements
     * @throws DeliverablePersistenceException
     *                                  if there is an error when reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds,
                                          long[] submissionIds)
        throws DeliverablePersistenceException {
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(deliverableIds, "deliverableIds");
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(resourceIds, "resourceIds");
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(phaseIds, "phaseIds");
        Helper.assertLongArrayNotNulLAndOnlyHasPositive(submissionIds, "submissionIds");
        if (deliverableIds.length != submissionIds.length
                || deliverableIds.length != phaseIds.length
                || deliverableIds.length != resourceIds.length) {
            throw new IllegalArgumentException(
                    "deliverableIds, resourceIds, phaseIds and submissionIds should have the same number of elements");
        }

        // simply return an empty Deliverable array if deliverableIds is empty
        if (deliverableIds.length == 0) {
            return new Deliverable[0];
        }

        LOGGER.log(Level.INFO, new LogMessage("Deliverable", null, null,
                "load Deliverables with deliverableIds:" + Helper.getIdString(deliverableIds)
                        + ", resourceId:" + Helper.getIdString(resourceIds) + ",phaseId:"
                        + Helper.getIdString(phaseIds)
                        + " and submissionIds:" + Helper.getIdString(submissionIds)));

        // build the match condition string.
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (int i = 0; i < deliverableIds.length; ++i) {
            if (i != 0) {
                stringBuffer.append(" OR ");
            }
            stringBuffer.append('(');
            stringBuffer.append("deliverable_lu.deliverable_id=");
            stringBuffer.append(deliverableIds[i]);
            stringBuffer.append(" AND ");
            stringBuffer.append("submission.submission_id=");
            stringBuffer.append(submissionIds[i]);
            stringBuffer.append(" AND ");
            stringBuffer.append("resource.resource_id=");
            stringBuffer.append(resourceIds[i]);
            stringBuffer.append(" AND ");
            stringBuffer.append("project_phase.project_phase_id=");
            stringBuffer.append(phaseIds[i]);
            stringBuffer.append(")");
        }
        stringBuffer.append(')');
        String matchCondition = stringBuffer.toString();

        Connection conn = null;
        try {
            try {
                // since only one query is needed, auto-commit mode is enabled.
                conn = Helper.createConnection(connectionFactory, connectionName, true, true);

                return loadDeliverablesWithSubmission(conn, matchCondition);
            } finally {
                Helper.closeConnection(conn);
            }
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage("Deliverable", null, null,
                    "Failed to load Deliverables with deliverableIds:" + Helper.getIdString(deliverableIds)
                            + ", resourceId:" + Helper.getIdString(resourceIds) + ",phaseId:"
                            + Helper.getIdString(phaseIds)
                            + " and submissionIds:" + Helper.getIdString(submissionIds), e));
            // wrap PersistenceException
            throw new DeliverablePersistenceException(
                    "Unable to load deliverables with submission from the database.", e);
        }
    }

    /**
     * <p>Loads the deliverables which are NOT associated with submissions. The
     * deliverables must NOT be "per submission" deliverables. An additional
     * match condition that much be meet is added to the tail of the sql string.</p>
     *
     * @param conn           the connection to the persistence
     * @param matchCondition the addition condition
     *
     * @return the loaded deliverables
     *
     * @throws PersistenceException if there is an error when reading the persistence data
     */
    private Deliverable[] loadDeliverablesWithoutSubmission(Connection conn, String matchCondition)
        throws PersistenceException {

        // load deliverables without submission
        Object[][] rows = Helper.doQuery(conn, LOAD_DELIVERABLES_WITHOUT_SUBMISSION_SQL
                + matchCondition, new DataType[0], new Object[0],
                LOAD_DELIVERABLES_WITHOUT_SUBMISSION_COLUMN_TYPES);

        // create a new Deliverable array
        Deliverable[] deliverables = new Deliverable[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // read from the first item
            int startIndex = 0;

            // get the parameters of the Deliverable's constructor
            long projectId = ((Long) row[startIndex++]).longValue();
            long phaseId = ((Long) row[startIndex++]).longValue();
            long resourceId = ((Long) row[startIndex++]).longValue();
            boolean required = ((Boolean) row[startIndex++]).booleanValue();

            // create a new Deliverable object
            Deliverable deliverable = new Deliverable(projectId, phaseId, resourceId, null,
                    required);

            // then fill the NamedDeliverableStructure fields from startIndex
            Helper.loadNamedEntityFieldsSequentially(deliverable, row, startIndex);

            // assign it to the array
            deliverables[i] = deliverable;
        }
        return deliverables;
    }

    /**
     * <p>Loads the deliverables associated with the given submissions. The
     * deliverables must be "per submission" deliverables and the given
     * submissions must be "Active". An additional match condition that much be
     * meet is added to the tail of the sql string.</p>
     *
     * @param conn           the connection to the persistence
     * @param matchCondition the addition condition
     *
     * @return the loaded deliverables
     *
     * @throws PersistenceException if there is an error when reading the persistence data
     */
    private Deliverable[] loadDeliverablesWithSubmission(Connection conn, String matchCondition)
        throws PersistenceException {

        // load deliverables
        Object[][] rows = Helper.doQuery(conn, LOAD_DELIVERABLES_WITH_SUBMISSION_SQL
                + matchCondition, new DataType[0], new Object[0],
                LOAD_DELIVERABLES_WITH_SUBMISSION_COLUMN_TYPES);

        // create a new Deliverable array
        Deliverable[] deliverables = new Deliverable[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // read from the first item
            int startIndex = 0;

            // get the parameters of the Deliverable's constructor
            long projectId = ((Long) row[startIndex++]).longValue();
            long phaseId = ((Long) row[startIndex++]).longValue();
            long resourceId = ((Long) row[startIndex++]).longValue();
            Long submissionId = (Long) row[startIndex++];
            boolean required = ((Boolean) row[startIndex++]).booleanValue();

            // create a new Deliverable object
            Deliverable deliverable = new Deliverable(projectId, phaseId, resourceId,
                    submissionId, required);

            // then fill the NamedDeliverableStructure fields from startIndex
            Helper.loadNamedEntityFieldsSequentially(deliverable, row, startIndex);

            // assign it to the array
            deliverables[i] = deliverable;
        }
        return deliverables;
    }
}
