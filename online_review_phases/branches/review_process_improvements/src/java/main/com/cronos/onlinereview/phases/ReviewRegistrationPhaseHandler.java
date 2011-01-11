/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.cronos.onlinereview.phases.logging.LogMessage;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This handler is responsible for checking whether the Review Registration Phase can be performed and
 * performing the phase. It extends <code>AbstractPhaseHandler</code> to leverage the various services
 * provided by the base class. Logging is done with the Logging Wrapper.
 * </p>
 * <p>
 * Usage: please see "test_files/config/Phase_Handler_1_5.xml". The namespace is
 * "com.cronos.onlinereview.phases.ReviewRegistrationPhaseHandler".
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe because it's immutable.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.5
 * @since 1.5
 */
public class ReviewRegistrationPhaseHandler extends AbstractPhaseHandler {

    /**
     * <p>
     * Represents the default namespace of this class.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It is final and won't change
     * once it is initialized as part of variable declaration to:
     * "com.cronos.onlinereview.phases.ReviewRegistrationPhaseHandler".
     * </p>
     * <p>
     * Usage: It is used in {@link #ReviewRegistrationPhaseHandler()} (for initialization).
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.ReviewRegistrationPhaseHandler";

    /**
     * <p>
     * The logger used for logging.
     * </p>
     * <p>
     * LegalValue: It cannot be null. Initialization and Mutability: It is final and won't change once it is
     * initialized as part of variable declaration to:
     * LogFactory.getLog(ReviewRegistrationPhaseHandler.class.getName()).
     * </p>
     * <p>
     * Usage: It is used throughout the class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogFactory.getLog(ReviewRegistrationPhaseHandler.class.getName());

    /**
     * <p>
     * The Constant PHASE_TYPE.
     * </p>
     */
    private static final String PHASE_TYPE = "Review Registration";

    /**
     * <p>
     * The number of primary reviewers needed.
     * </p>
     * <p>
     * LegalValue: It must be non-negative. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards. The default value is 1.
     * </p>
     * <p>
     * Usage: It is used in {@link #ReviewRegistrationPhaseHandler()} (for initialization),
     * {@link #isReviewBoardFilled()}.
     * </p>
     */
    private final int primaryReviewerCount;

    /**
     * <p>
     * The number of secondary reviewers needed. </p
     * <p>>
     * LegalValue: It must be non-negative. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards. The default value is 2.
     * </p>
     * <p>
     * Usage: It is used in {@link #ReviewRegistrationPhaseHandler()} (for initialization),
     * {@link #isReviewBoardFilled()}.
     * </p>
     */
    private final int secondaryReviewerCount;

    /**
     * <p>
     * The primary reviewer resource id in the 'resource' table for checking whether the review board is
     * filled.
     * </p>
     * <p>
     * LegalValue: It can be any value. Initialization and Mutability: It's initialized within constructor,
     * won't change afterwards. The default value is 16.
     * </p>
     * <p>
     * Usage: It is used in {@link #ReviewRegistrationPhaseHandler()} (for initialization),
     * {@link #isReviewBoardFilled()}.
     * </p>
     */
    private final long primaryReviewerResourceId;

    /**
     * <p>
     * The second reviewer resource id in the 'resource' table for checking whether the review board is
     * filled.
     * </p>
     * <p>
     * LegalValue: It can be any value. Initialization and Mutability: It's initialized within constructor,
     * won't change afterwards. The default value is 17.
     * </p>
     * <p>
     * Usage: It is used in {@link #ReviewRegistrationPhaseHandler()} (for initialization),
     * {@link #isReviewBoardFilled()}.
     * </p>
     */
    private final long secondReviewerResourceId;

    /**
     * <p>
     * Create an instance of the class with the default namespace.
     * </p>
     *
     * @throws ConfigurationException
     *             if any error occurs
     */
    public ReviewRegistrationPhaseHandler() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Create an instance of the class.
     * </p>
     *
     * @param namespace
     *            the configuration namespace.
     * @throws ConfigurationException
     *             if any error occurs
     * @throws IllegalArgumentException
     *             if namespace is null or empty.
     */
    public ReviewRegistrationPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);

        primaryReviewerCount = parseStringToInt(1, namespace, "primaryReviewerCount");

        secondaryReviewerCount = parseStringToInt(2, namespace, "secondaryReviewerCount");

        primaryReviewerResourceId = parseStringToT(16, namespace, "primaryReviewerResourceId", Long.class)
            .longValue();

        secondReviewerResourceId = parseStringToT(17, namespace, "secondReviewerResourceId", Long.class)
            .longValue();
    }

    /**
     * <p>
     * Parses the string to int value. the parameter value is option and non-negative integer string.
     * </p>
     *
     * @param defaultValue
     *            the default integer number
     * @param namespace
     *            the configuration namespace.
     * @param paramName
     *            the parameter name
     * @return the integer number
     * @throws ConfigurationException
     *             the configuration exception occurs
     */
    private int parseStringToInt(int defaultValue, String namespace, String paramName)
        throws ConfigurationException {

        int count = parseStringToT(defaultValue, namespace, paramName, Integer.class).intValue();
        if (count < 0) {
            String detail = "The value for " + paramName + " should be non-negative integer.";
            LOG.log(Level.ERROR, detail);
            throw new ConfigurationException(detail);
        }
        return count;

    }

    /**
     * <p>
     * Parses the string to any number value. the parameter value is option, if the value is not set. the
     * default value is return.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param defaultValue
     *            the default any number
     * @param namespace
     *            the configuration namespace.
     * @param paramName
     *            the parameter name
     * @param className
     *            the number type class name
     * @return the any type number
     * @throws ConfigurationException
     *             the configuration exception occurs
     */
    private <T extends Number> T parseStringToT(Number defaultValue, String namespace, String paramName,
        Class<T> className) throws ConfigurationException {

        String str = PhasesHelper.getPropertyValue(namespace, paramName, false);
        try {
            Constructor<T> cons = className.getConstructor(String.class);
            if (str == null) {
                return cons.newInstance(defaultValue.toString());
            }
            return cons.newInstance(str);
        } catch (Exception ex) {
            String detail = "The value for " + paramName + " should be " + className.toString().toLowerCase()
                + " number string.";
            LOG.log(Level.ERROR, new LogMessage(null, null, detail, ex));
            throw new ConfigurationException(detail, ex);
        }
    }

    /**
     * <p>
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed.
     * </p>
     * <p>
     * This method will be called by <code>canStart()</code> and <code>canEnd()</code> methods of
     * <code>PhaseManager</code> implementations in Phase Management component.
     * </p>
     *
     * @param phase
     *            The input phase to check
     * @return True if the input phase can be executed, false otherwise.
     * @throws IllegalArgumentException
     *             if phase is null;
     * @throws PhaseNotSupportedException
     *             if the input phase type is not "Review Registration"
     * @throws PhaseHandlingException
     *             if there is any error occurred
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);

            // check if the phase is to start:
            boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

            if (toStart) {
                // check if the phase can start in terms of dependency
                // check if all parent projects are completed
                return PhasesHelper.canPhaseStart(phase)
                    && PhasesHelper.areParentProjectsCompleted(phase, createConnection(), getManagerHelper(),
                        LOG);

            }

            // check if dependencies are met.
            // check if the phase end time is reached.
            // return true only if dependencies are met, phase end time is reached, and the review board is
            // fully
            // filled:
            return PhasesHelper.arePhaseDependenciesMet(phase, false)
                && PhasesHelper.reachedPhaseEndTime(phase) && isReviewBoardFilled(phase);
        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, null, phase.getProject().getId());
        }
    }

    /**
     * <p>
     * Provides additional logic to execute a phase. This method will be called by <code>start()</code> and
     * <code>end()</code> methods of <code>PhaseManager</code> implementations in Phase Management component.
     * </p>
     * <p>
     * This method can send email to a group of users associated with timeline notification for the project.
     * The email can be send on start phase or end phase base on configuration settings.
     * </p>
     *
     * @param phase
     *            The input phase to check.
     * @param operation
     *            The operator that execute the phase.
     * @throws IllegalArgumentException
     *             if phase is null; operation is null or empty.
     * @throws PhaseNotSupportedException
     *             if the input phase type doesn't equal to "Review Registration"
     * @throws PhaseHandlingException
     *             if there is any error occurred
     */
    public void perform(Phase phase, String operation) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operation, "operation");
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);
            PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

            sendEmail(phase, new HashMap<String, Object>());
        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, operation, phase.getProject().getId());
        }
    }

    /**
     * <p>
     * This method checks if the review board is fully filled.
     * </p>
     *
     * @param phase
     *            the input phase to check.
     * @return true if the review board is fully filled, false otherwise.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     */
    private boolean isReviewBoardFilled(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = createConnection();
            // this sql is used to check if the review board is filled
            ps = conn.prepareStatement("select count(*) from resource where project_id=? and resource_id=?");

            // set the project id:
            ps.setLong(1, phase.getProject().getId());

            // get the count of the primary reviewer's resource:
            int count = queryCount(ps, primaryReviewerResourceId);

            if (count != primaryReviewerCount) {
                return false;
            }

            // get the count of the secondary reviewer's resource:
            count = queryCount(ps, secondReviewerResourceId);

            if (count != secondaryReviewerCount) {
                return false;
            }

            return true;
        } catch (SQLException ex) {
            String detail = "When query the database, the exception occured.";
            LOG.log(Level.ERROR, new LogMessage(phase.getId(), null, detail, ex));
            throw new PhaseHandlingException(detail, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Query count.
     * </p>
     *
     * @param ps
     *            the PreparedStatement instance
     * @param id
     *            the id number
     * @return the count number
     * @throws SQLException
     *             the SQL exception occurs
     */
    private int queryCount(PreparedStatement ps, Long id) throws SQLException {
        ResultSet rs = null;

        try {
            // set the id:
            ps.setLong(2, id);

            // perform query:
            rs = ps.executeQuery();

            // get result:
            rs.next();

            // get the count of resource:
            return rs.getInt(1);

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

    }
}
