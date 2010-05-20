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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @version 1.1
 */
public class DBContestDataRetriever extends BaseDBConnector implements
        ContestDataRetriever {

    /**
     * SQL query sentence to retrieve component information.
     */
    private static final String PROJECT_INFO_QUERY = "select c.category_name, pcl.name as phase, "
        + "(select max(actual_end_time) from project_phase where project_id = ?) as end_time, "
        + "pi6.value as component_name, pi7.value as verion, pi8.value as svn, "
        + "u1.handle as first_winner, "
        + "(select handle from project_info, user where project_id = ? "
        + "and project_info_type_id = 24 and user_id=value) as second_winner "
        + "from categories c, comp_catalog cc, project_info pi2, project_category_lu pcl, project p, "
        + "project_info pi6, project_info pi7, project_info pi8, project_info pi23, user u1 "
        + "where c.category_id=cc.root_category_id and cc.component_id=pi2.value and pi2.project_id=? "
        + "and pcl.project_category_id = p.project_category_id and pi2.project_info_type_id=2"
        + " and p.project_id = pi2.project_id "
        + "and pi6.project_id = p.project_id and pi6.project_info_type_id = 6 "
        + "and pi7.project_id = p.project_id and pi7.project_info_type_id = 7 "
        + "and pi8.project_id = p.project_id and pi8.project_info_type_id = 8 "
        + "and pi23.project_id = p.project_id and pi23.project_info_type_id = 23 and u1.user_id = pi23.value ";

    /**
     * SQL query sentence to retrieve component information.
     */
    private static final String REVIEWS_QUERY = "select ri.value from resource_info ri, resource r "
        + "where r.project_id=? and r.resource_role_id in (4, 5, 6, 7) and ri.resource_id=r.resource_id "
        + "and ri.resource_info_type_id=2";

    /**
     * SQL query sentence to retrieve design project id when this is a
     * development project.
     */
    private static final String FIND_CORRESPONDING_DESIGN_CONTEST = "select q1.project_id from project p, "
        + "project_info q1 "
        + "join project_info p1 on p1.value = q1.value and p1.project_info_type_id = 1 and p1.project_id = ? "
        + "join project_info q2 on q2.project_id = q1.project_id and q2.project_info_type_id = 2 "
        + "join project_info p2 on p2.value = q2.value and p2.project_info_type_id = 2 and p2.project_id = ? "
        + "join project_info q3 on q3.project_id = q1.project_id and q3.project_info_type_id = 3 "
        + "join project_info p3 on p3.value = q3.value and p3.project_info_type_id = 3 and p3.project_id = ? "
        + "where q1.project_info_type_id = 1 and p.project_id = q1.project_id and p.project_category_id = 1 "
        + " and p.project_status_id in (1,2,7) and q1.project_id <> ?";

    /** Type id of component name. */
    private static final int COMPONENT_NAME_TYPE_ID = 6;
    /** Type id of version. */
    private static final int VERSION_TYPE_ID = 7;
    /** Type id of svn path. */
    private static final int SVN_PATH_TYPE_ID = 8;
    /** Type id of first place user. */
    private static final int FIRST_PLACE_USER_TYPE_ID = 23;
    /** Type id of second place user. */
    private static final int SECOND_PLACE_USER_TYPE_ID = 24;

    /** Design category id. */
    private static final String DESIGN_CATEGORY_ID = "Design";
    /** Development category id. */
    private static final String DEVELOPMENT_CATEGORY_ID = "Development";

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

            // get project information
            ProjectInfo projectInfo = getProjectInfo(connection, projectId);
            if (projectInfo.componentName == null || projectInfo.versionId == null) {
                throw new ContestDataRetrieverException("project with id["
                    + projectId + "] doesn't exist in DB.");
            }
            contestData.setComponentName(projectInfo.componentName + " " + projectInfo.versionId);
            contestData.setSvnPath(projectInfo.svnPath);
            String winner = projectInfo.winner;
            String secondPlace = projectInfo.secondPlace;
            contestData.setCategory(projectInfo.category);
            String phase = projectInfo.phase;
            contestData.setContestEndDate(projectInfo.endTime);
            // log contest data retrieved from DB.
            Helper.logInfo(getLogger(), MessageFormat.format("retrieve ContestData from DB: componentName[{0}],"
                + " contestEndDate[{1}], category[{2}],svnPath[{3}] ,"
                + " phase[{4}].", contestData.getComponentName(), contestData.getContestEndDate(),
                contestData.getCategory(), contestData.getSvnPath(), phase));

            setContestDataField(
                projectId, phase, contestData, winner, secondPlace, getReviewersList(connection, projectId));
            if (phase.equals(DEVELOPMENT_CATEGORY_ID)) {
                // set design field for the contest data if this is in development phase.
                setDesignFieldForDevelopment(connection, projectId, contestData);
            }
            return contestData;
        } catch (DBConnectionException e) {
            throw Helper.logError(getLogger(), new ContestDataRetrieverException("fail to connect database.", e));
        } catch (SQLException e) {
            throw Helper.logError(getLogger(), new ContestDataRetrieverException("fail to execute SQL sentence.", e));
        } finally {
            Helper.releaseDBResource(connection, stmt, rs);
            Helper.logExit(getLogger(), signature);
        }
    }

    /**
     * <p>
     * set winner, second place, reviews for contest data.
     * </p>
     *
     * @param projectId the projectId.
     * @param phase the phase.
     * @param contestData the contest data.
     * @param winner the winner
     * @param secondPlace the second place.
     * @param reviewers the reviewer
     */
    private void setContestDataField(long projectId, String phase, ContestData contestData,
            String winner, String secondPlace, List<String> reviewers) {
        if (phase.equals(DESIGN_CATEGORY_ID)) {
                contestData.setDesignProjectId(Long.toString(projectId));
            contestData.setDesignWinner(winner);
            contestData.setDesignSecondPlace(secondPlace);
            contestData.setDesignReviewers(reviewers);
            // log contest data retrieved from DB.
            Helper.logInfo(getLogger(), MessageFormat.format("retrieve ContestData from DB:"
                + " componentName[{0}], designWinner[{1}], designSecondPlace[{2}],"
                + " phase[{3}] " + getReviewers(reviewers), contestData.getComponentName(), winner, secondPlace));
        } else if (phase.equals(DEVELOPMENT_CATEGORY_ID)) {
                contestData.setDevelopmentProjectId(Long.toString(projectId));
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
     * Gets project info from database.
     *
     * @param connection the database connection.
     * @param projectId the project id.
     * @throws SQLException if fail to retrieve data,delegate dealing to caller method.
     * @return project info.
     */
    private ProjectInfo getProjectInfo(Connection connection, long projectId)
        throws SQLException {
        ResultSet rs = null;
        try {
            ProjectInfo projectInfo = new ProjectInfo();
            rs = executeQuery(connection, PROJECT_INFO_QUERY, projectId, projectId, projectId);
            if (rs.next()) {
                projectInfo.category = rs.getString(1);
                projectInfo.phase = rs.getString(2);
                projectInfo.endTime = rs.getDate(3);
                projectInfo.componentName = rs.getString(4);
                projectInfo.versionId = rs.getString(5);
                projectInfo.svnPath = rs.getString(6);
                projectInfo.winner = rs.getString(7);
                projectInfo.secondPlace = rs.getString(8);
            }
            return projectInfo;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * Gets reviewers for project from database.
     *
     * @param conn the database connection.
     * @param projectId the project id.
     * @return list of reviewers.
     * @throws SQLException if fail to retrieve data,delegate dealing to caller method.
     */
    private List<String> getReviewersList(Connection conn, long projectId) throws SQLException {
        ResultSet rs = null;
        try {
            Set<String> reviewers = new HashSet<String>();
            rs = executeQuery(conn, REVIEWS_QUERY, projectId);
            while (rs.next()) {
                reviewers.add(rs.getString(1));
            }
            return new ArrayList<String>(reviewers);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * <p>
     * set design field for the contest data if this is in development phase.
     * </p>
     *
     * @param conn the database connection.
     * @param projectId the development project id.
     * @param contestData the contest data to set.
     * @throws SQLException if fail to retrieve data,delegate dealing to caller
     *             method.
     * @throws ContestDataRetrieverException if fail to retrieve data.
     */
    private void setDesignFieldForDevelopment(Connection conn,
            long projectId, ContestData contestData)
        throws SQLException, ContestDataRetrieverException {
        ResultSet rs = null;
        try {
            // get designWinner as follow
            rs = executeQuery(conn, FIND_CORRESPONDING_DESIGN_CONTEST, projectId, projectId, projectId, projectId);
            if (rs.next()) {
                long designProjectId = rs.getLong(1);
                ProjectInfo projectInfo = getProjectInfo(conn, designProjectId);
                String designWinner = projectInfo.winner;
                String designSecond = projectInfo.secondPlace;

                contestData.setDesignProjectId(Long.toString(designProjectId));
                contestData.setDesignWinner(designWinner);
                contestData.setDesignSecondPlace(designSecond);
                List<String> reviewer = getReviewersList(conn, designProjectId);
                contestData.setDesignReviewers(reviewer);

                // log design information retrieved from DB.
                Helper.logInfo(getLogger(), MessageFormat.format("retrieve design information for development phase:"
                    + " designWinner[{0}], designSecondPlace[{1}]"
                    + getReviewers(reviewer), designWinner, designSecond));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /** Class to contain information about project. */
    private class ProjectInfo {
        /** Component name. */
        private String componentName = null;
        /** Component version. */
        private String versionId = null;
        /** Winner user. */
        private String winner = null;
        /** Second palace user */
        private String secondPlace = null;
        /** SVN path of component */
        private String svnPath = null;
        /** Component category. */
        private String category = null;
        /** Component phase. */
        private String phase = null;
        /** Component end time. */
        private Date endTime = null;
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
     * @param query the SQL query sentence.
     * @param params query parameters.
     * @return the result set.
     * @throws SQLException if fail to execute query,caller method will catch
     *             this.
     */
    private ResultSet executeQuery(Connection connection, String query, Object ... params)
        throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
                stmt.setObject(i+1, params[i]);
        }
        return stmt.executeQuery();
    }
}

