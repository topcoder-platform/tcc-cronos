/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl.contestdataretriever;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.contest.coo.ContestData;
import com.topcoder.management.contest.coo.ContestDataRetriever;
import com.topcoder.management.contest.coo.ContestDataRetrieverException;
import com.topcoder.management.contest.coo.Helper;
import com.topcoder.management.contest.coo.impl.BaseDBConnector;
import com.topcoder.management.contest.coo.impl.ConfigurationException;

/**
 * <p>
 * This class represents <code>DBContestDataRetriever</code>. It implements
 * <code>ContestDataRetriever</code> interface. It retrievers the contest data
 * from the database. SQL queries are used.
 * </p>
 * <p>
 * This class extends from <code>BaseDBConnector</code> to provide database
 * related functionalities.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //create DBContestDataRetriever from configuration
 * DBContestDataRetriever retriever = new DBContestDataRetriever(configuration);
 *
 * //retrieve contest data with project id 14 from database.
 * ContestData data = retriever.retrieveContestData(14);
 * </pre>
 *
 * </p>
 * <p>
 * <strong> Thread safety:</strong> This class is thread safe since it is
 * immutable. The logging used is thread safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.0
 */
public class DBContestDataRetriever extends BaseDBConnector implements
        ContestDataRetriever {

    /**
     * SQL query sentence to retrieve component information.
     */
    private static final String COMPONENT_QUERY = "select component_name, version_id,category, "
            + "end_date from project where project_id = ?";
    /**
     * SQL query sentence to retrieve SVN information.
     */
    private static final String SVN_QUERY = "select value from project_info "
            + "where project_info_type_id = 8 and project_id = ?";
    /**
     * SQL query sentence to retrieve project phase information.
     */
    private static final String PROJECT_PHASE_QUERY = "select phase_desc from project where project_id = ?";
    /**
     * SQL query sentence to retrieve winner information.
     */
    private static final String WINNER_QUERY = "select user_id from project_result "
            + "where project_id = ? and submit_ind = 1";
    /**
     * SQL query sentence to retrieve second place information.
     */
    private static final String SECOND_PLACE_QUERY = "select user_id from project_result "
            + "where project_id = ? and submit_ind = 2";
    /**
     * SQL query sentence to retrieve component information.
     */
    private static final String REVIEWS_QUERY = "select reviewer_id from submission_review where project_id = ?";

    /**
     * SQL query sentence to retrieve design project id when this is a
     * development project.
     */
    private static final String DESIGN_PROJECT_ID_QUERY = "select project_id from project "
            + "where component_name = ? AND version_id = ?";

    /**
     * Constructor.
     *
     * @param configuration The configuration object. must not be null.
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     */
    public DBContestDataRetriever(ConfigurationObject configuration)
        throws ConfigurationException {
        // delegate check to super class.
        super(configuration);
    }

    /**
     * Retrieve contest data of the given project id.
     *
     * @param projectId The project id. must be positive.
     * @return The contest data of the given project.
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ContestDataRetrieverException if there is any error in performing
     *             this method
     *
     */
    public ContestData retrieveContestData(long projectId)
        throws ContestDataRetrieverException {
        // log signature
        final String signature = "DBContestDataRetriever#retrieveContestData";
        Helper.logEnter(getLogger(), signature);
        Helper.checkId(getLogger(), "projectId", projectId);
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // get connection from factory
            connection = this.getDbConnectionFactory().createConnection(getConnectionName());
            ContestData contestData = new ContestData();

            String componentName;
            String versionId;
            rs = executeQuery(connection, stmt, projectId, COMPONENT_QUERY);
            if (rs.next()) {
                componentName = rs.getString(1);
                versionId = rs.getString(2);
                contestData.setComponentName(componentName + " " + versionId);
                contestData.setCategory(rs.getString(3));
                contestData.setContestEndDate(rs.getDate(4));
            } else {
                throw new ContestDataRetrieverException("project with id["
                        + projectId + "] doesn't exist in DB.");
            }
            // retrieve svnPath
            String svnPath = retrieveOneColumnFromQuery(connection, stmt, rs, projectId,
                SVN_QUERY, false, "svnPath");
            contestData.setSvnPath(svnPath);
            // get the phase of the project
            String phase = retrieveOneColumnFromQuery(connection, stmt, rs, projectId,
                PROJECT_PHASE_QUERY, false, "phase");
            // log contest data retrieved from DB.
            Helper.logInfo(getLogger(), MessageFormat.format("retrieve ContestData from DB: componentName[{0}],"
                    + " contestEndDate[{1}], category[{2}],svnPath[{3}] ,"
                    + " phase[{4}].", contestData.getComponentName(), contestData.getContestEndDate(),
                    contestData.getCategory(), contestData.getSvnPath(), phase));
            String winner = retrieveOneColumnFromQuery(connection, stmt, rs, projectId, WINNER_QUERY, true, null);
            String secondPlace = retrieveOneColumnFromQuery(connection, stmt, rs, projectId,
                SECOND_PLACE_QUERY, true, null);
            // get reviewers as follow
            List<String> reviewers = new ArrayList<String>();
            rs = executeQuery(connection, stmt, projectId, REVIEWS_QUERY);
            while (rs.next()) {
                reviewers.add(rs.getString(1));
            }
            setContestDataField(phase, contestData, winner, secondPlace, reviewers);
            if (phase.equals("development")) {
                // set design field for the contest data if this is in
                // development phase.
                setDesignFieldForDevelopment(connection, stmt, rs, componentName, versionId, projectId, contestData);
            }
            return contestData;
        } catch (DBConnectionException e) {
            throw Helper.logError(getLogger(), new ContestDataRetrieverException("fail to connect database.", e));
        } catch (SQLException e) {
            throw Helper.logError(getLogger(), new ContestDataRetrieverException("fail to execute SQL sentence.", e));
        } finally {
            Helper.releaseDBresource(getLogger(), connection, stmt, rs);
            Helper.logExit(getLogger(), signature);
        }
    }
    /**
     * <p>
     * set winner, second place, reviews for contest data.
     * </p>
     *
     * @param phase the phase.
     * @param contestData the contest data.
     * @param winner the winner
     * @param secondPlace the second place.
     * @param reviewers the reviewer
     */

    private void setContestDataField(String phase, ContestData contestData,
            String winner, String secondPlace, List<String> reviewers) {
        if (phase.equals("design")) {
            contestData.setDesignWinner(winner);
            contestData.setDesignSecondPlace(secondPlace);
            contestData.setDesignReviewers(reviewers);
            // log contest data retrieved from DB.
            Helper.logInfo(getLogger(), MessageFormat.format("retrieve ContestData from DB:"
                    + " componentName[{0}], designWinner[{1}], designSecondPlace[{2}],"
                    + " phase[{3}] " + getReviewers(reviewers), contestData.getComponentName(), winner, secondPlace));
        } else if (phase.equals("development")) {
            contestData.setDevelopmentWinner(winner);
            contestData.setDevelopmentSecondPlace(secondPlace);
            contestData.setDevelopmentReviewers(reviewers);
            // log contest data retrieved from DB.
            Helper.logInfo(getLogger(), MessageFormat.format("retrieve ContestData from DB:"
                    + " componentName[{0}], developmentWinner[{1}], developmentSecondPlace[{2}],"
                    + " phase[{3}] " + getReviewers(reviewers), contestData.getComponentName(), winner, secondPlace));
        }
    }
    /**
     * <p>
     * retrieve the result only one row returned with string column (like
     * winner, second place, SVN path...) from query.
     * </p>
     *
     * @param connection the database connection.
     * @param stmt the prepared statement.
     * @param rs the result set.
     * @param projectId the project id.
     * @param query the SQL query sentence.
     * @param nullable whether can be null,
     * @param name for exception message, if can not be null,
     * @return the one string column (like winner, second place, SVN path...),if
     *         not found (winner,second place), return null, if (SVN path,phase)
     *         throw exception.
     * @throws SQLException if fail to retrieve data,delegate dealing to caller
     *             method.
     * @throws ContestDataRetrieverException if the value is required
     */
    private String retrieveOneColumnFromQuery(Connection connection,
            PreparedStatement stmt, ResultSet rs, long projectId, String query,
            boolean nullable, String name)
        throws SQLException, ContestDataRetrieverException {
        rs = executeQuery(connection, stmt, projectId, query);
        String rt = null;
        if (rs.next()) {
            rt = rs.getString(1);
        }
        if (!nullable && rt == null) {
            throw new ContestDataRetrieverException("project [" + name
                    + " doesn't exist in DB.");
        }
        return rt;
    }

    /**
     * <p>
     * set design field for the contest data if this is in development phase.
     * </p>
     *
     * @param conn the database connection
     * @param stmt prepared statement.
     * @param rs the result set.
     * @param componentName the component name of development.
     * @param versionId the version id of development
     * @param projectId the development project id.
     * @param contestData the contest data to set.
     * @throws SQLException if fail to retrieve data,delegate dealing to caller
     *             method.
     * @throws ContestDataRetrieverException if fail to retrieve data.
     */
    private void setDesignFieldForDevelopment(Connection conn,
            PreparedStatement stmt, ResultSet rs, String componentName,
            String versionId, long projectId, ContestData contestData)
        throws SQLException, ContestDataRetrieverException {
        long designProjectId = 0;
        // get designWinner as follow
        stmt = conn.prepareStatement(DESIGN_PROJECT_ID_QUERY);
        stmt.setString(1, componentName);
        stmt.setString(2, versionId);
        rs = stmt.executeQuery();
        // This would return 2 project_ids, let designProjectId be the
        // project_id that is different from projectId argument
        while (rs.next()) {
            if (rs.getLong(1) != projectId) {
                designProjectId = rs.getLong(1);
            }
        }
        String designWinner = retrieveOneColumnFromQuery(conn, stmt, rs, designProjectId, WINNER_QUERY, true, null);
        String designSecond = retrieveOneColumnFromQuery(conn, stmt, rs, designProjectId,
            SECOND_PLACE_QUERY, true, null);

        // get reviewers as follow
        List<String> reviewer = new ArrayList<String>();
        rs = executeQuery(conn, stmt, designProjectId, REVIEWS_QUERY);
        while (rs.next()) {
            reviewer.add(rs.getString(1));
        }
        contestData.setDesignWinner(designWinner);
        contestData.setDesignSecondPlace(designSecond);
        contestData.setDesignReviewers(reviewer);
        // log design information retrieved from DB.
        Helper.logInfo(getLogger(), MessageFormat.format("retrieve design information for development phase:"
                + " componentName[{0} {1}], designWinner[{2}], designSecondPlace[{3}]"
                + getReviewers(reviewer), componentName, versionId, designWinner, designSecond));
    }

    /**
     * construct the log message for reviewers.
     *
     * @param reviewers the component reviewers.
     * @return the message for reviewer used for logging.
     */
    private String getReviewers(List<String> reviewers) {
        StringBuilder rt = new StringBuilder();
        rt.append("reviewers:");
        for (String reviewer : reviewers) {
            rt.append(" [").append(reviewer).append("]");
        }
        return rt.toString();
    }

    /**
     * execute SQL query to get <code>ResultSet</code>.
     *
     * @param connection the database connection
     * @param stmt database statement.
     * @param projectId the project id.
     * @param query the SQL query sentence.
     * @return the result set.
     * @throws SQLException if fail to execute query,caller method will catch
     *             this.
     */
    private ResultSet executeQuery(Connection connection,
            PreparedStatement stmt, long projectId, String query)
        throws SQLException {
        stmt = connection.prepareStatement(query);
        stmt.setLong(1, projectId);
        return stmt.executeQuery();
    }
}
