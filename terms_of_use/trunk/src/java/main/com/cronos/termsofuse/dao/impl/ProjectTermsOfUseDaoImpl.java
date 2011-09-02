/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cronos.termsofuse.dao.EntityNotFoundException;
import com.cronos.termsofuse.dao.ProjectTermsOfUseDao;
import com.cronos.termsofuse.dao.TermsOfUseDaoConfigurationException;
import com.cronos.termsofuse.dao.TermsOfUsePersistenceException;
import com.cronos.termsofuse.model.TermsOfUse;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the default implementation of ProjectTermsOfUseDao. It utilizes the DB Connection Factory to get
 * access to the database. The configuration is done by the Configuration API.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;CMConfig&gt;
 *     &lt;Config name="projectTermsOfUseDao"&gt;
 *      &lt;Property name="dbConnectionFactoryConfig"&gt;
 *        &lt;Property name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *          &lt;Property name="connections"&gt;
 *                 &lt;Property name="default"&gt;
 *                     &lt;Value&gt;InformixJDBCConnection&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *              &lt;Property name="InformixJDBCConnection"&gt;
 *                  &lt;Property name="producer"&gt;
 *                      &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name="parameters"&gt;
 *                      &lt;Property name="jdbc_driver"&gt;
 *                      &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="jdbc_url"&gt;
 *                              &lt;Value&gt;
 *                                  jdbc:informix-sqli://localhost:1526/common_oltp:informixserver=ol_topcoder
 *                              &lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="user"&gt;
 *                          &lt;Value&gt;informix&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="password"&gt;
 *                          &lt;Value&gt;123456&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                  &lt;/Property&gt;
 *              &lt;/Property&gt;
 *          &lt;/Property&gt;
 *        &lt;/Property&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="loggerName"&gt;
 *          &lt;Value&gt;loggerName&lt;/Value&gt;
 *      &lt;/Property&gt;
 *     &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Create the configuration object
 * ConfigurationObject configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_PROJECT_TERMS);
 * // Instantiate the dao implementation from configuration defined above
 * ProjectTermsOfUseDao projectTermsOfUseDao = new ProjectTermsOfUseDaoImpl(configurationObject);
 *
 * // Create user terms of use to project link
 * projectTermsOfUseDao.createProjectRoleTermsOfUse(2, 2, 3, 2, 0);
 *
 * // Remove user terms of use to project link
 * projectTermsOfUseDao.removeProjectRoleTermsOfUse(2, 2, 3, 0);
 *
 * // Get terms of use with non-member-agreeable terms
 * // Will return two lists:
 * // 1st with ids: 1,2,3
 * // 2nd with ids: 1
 * Map&lt;Integer, List&lt;TermsOfUse&gt;&gt; termsGroupMap =
 *     projectTermsOfUseDao.getTermsOfUse(1, new int[] {1, 2}, true);
 *
 * // Get terms of use without non-member-agreeable terms
 * // Will return one list:
 * // 1st with ids: 1
 * termsGroupMap = projectTermsOfUseDao.getTermsOfUse(1, new int[] {1, 2}, false);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The class is immutable and thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public class ProjectTermsOfUseDaoImpl extends BaseTermsOfUseDao implements ProjectTermsOfUseDao {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ProjectTermsOfUseDaoImpl.class.getName();

    /**
     * <p>
     * Represents the SQL string to insert a project role terms of use association.
     * </p>
     */
    private static final String INSERT_PROJECT_TERMS = "INSERT INTO project_role_terms_of_use_xref (project_id,"
        + " resource_role_id, terms_of_use_id, sort_order, group_ind) VALUES (?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL string to delete a project role terms of use association.
     * </p>
     */
    private static final String DELETE_PROJECT_TERMS = "DELETE FROM project_role_terms_of_use_xref"
        + " WHERE project_id = ? and resource_role_id = ? and terms_of_use_id = ? and group_ind = ?";

    /**
     * <p>
     * Represents the SQL string to query the terms of use.
     * </p>
     */
    private static final String QUERY_TERMS =
        "SELECT DISTINCT terms_of_use.terms_of_use_id, sort_order, group_ind, terms_of_use_type_id,"
        + " title, electronically_signable, url, member_agreeable FROM project_role_terms_of_use_xref"
        + " INNER JOIN terms_of_use ON project_role_terms_of_use_xref.terms_of_use_id = terms_of_use.terms_of_use_id"
        + " WHERE project_id = ? AND resource_role_id IN (%1$s) order by group_ind, sort_order";

    /**
     * <p>
     * Represents the SQL string to delete all project role terms of use association for a given project.
     * </p>
     */
    private static final String DELETE_ALL_PROJECT_TERMS = "DELETE FROM project_role_terms_of_use_xref"
        + " WHERE project_id = ?";

    /**
     * This is the constructor with the ConfigurationObject parameter, which simply delegates the instance
     * initialization to the base class.
     *
     * @param configurationObject
     *            the configuration object containing the configuration.
     *
     * @throws IllegalArgumentException
     *             if the configurationObject is null.
     * @throws TermsOfUseDaoConfigurationException
     *             if any exception occurs while initializing the instance.
     */
    public ProjectTermsOfUseDaoImpl(ConfigurationObject configurationObject) {
        super(configurationObject);
    }

    /**
     * This method will create a project role terms of use association.
     *
     * @param groupIndex
     *            the group index to associate.
     * @param resourceRoleId
     *            the role id to associate.
     * @param sortOrder
     *            the association sort order.
     * @param termsOfUseId
     *            the terms of use id to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void createProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int sortOrder,
        int groupIndex) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".createProjectRoleTermsOfUse(int projectId, int resourceRoleId,"
            + " long termsOfUseId, int sortOrder, int groupIndex)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId", "resourceRoleId", "termsOfUseId", "sortOrder", "groupIndex"},
            new Object[] {projectId, resourceRoleId, termsOfUseId, sortOrder, groupIndex});

        try {
            Helper.executeUpdate(getDBConnectionFactory(), INSERT_PROJECT_TERMS,
                new Object[] {projectId, resourceRoleId, termsOfUseId, sortOrder, groupIndex});

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * This method will remove a project role terms of use association.
     *
     * @param groupIndex
     *            the group index to associate.
     * @param resourceRoleId
     *            the role id to associate.
     * @param termsOfUseId
     *            the terms of use id to associate group index to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void removeProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int groupIndex)
        throws EntityNotFoundException, TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".removeProjectRoleTermsOfUse(int projectId, int resourceRoleId,"
            + " long termsOfUseId, int groupIndex)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId", "resourceRoleId", "termsOfUseId", "groupIndex"},
            new Object[] {projectId, resourceRoleId, termsOfUseId, groupIndex});

        try {
            Helper.executeUpdate(getDBConnectionFactory(), DELETE_PROJECT_TERMS,
                new Object[] {projectId, resourceRoleId, termsOfUseId, groupIndex},
                new StringBuilder().append(projectId).append(", ").append(resourceRoleId).append(", ")
                    .append(termsOfUseId).append(", ").append(groupIndex).toString());

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * This method retrieves terms of use for specific pair of user and resource roles and groups it by terms of use
     * groups. It optionally can ignore non member agreeable terms of use.
     *
     * @param includeNonMemberAgreeable
     *            the flag specifying whether to include non member agreeable terms of use.
     * @param resourceRoleIds
     *            the role ids to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @return Map of lists of terms of use entities. The key of the map is the group index, the value is the list of
     *         terms for this group.
     *
     * @throws IllegalArgumentException
     *             if resourceRoleIds is null or empty array.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public Map<Integer, List<TermsOfUse>> getTermsOfUse(int projectId, int[] resourceRoleIds,
        boolean includeNonMemberAgreeable) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUse(int projectId, int[] resourceRoleIds,"
            + " boolean includeNonMemberAgreeable)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId", "resourceRoleIds", "includeNonMemberAgreeable"},
            new Object[] {projectId, toList(resourceRoleIds), includeNonMemberAgreeable});

        try {
            Helper.checkNull(resourceRoleIds, "resourceRoleIds");
            if (resourceRoleIds.length == 0) {
                throw new IllegalArgumentException("'resourceRoleIds' should not be empty.");
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < resourceRoleIds.length; i++) {
                if (i != 0) {
                    // Append a comma
                    sb.append(", ");
                }
                sb.append("?");
            }

            String query = String.format(QUERY_TERMS, sb.toString());

            Connection conn = Helper.createConnection(getDBConnectionFactory());
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, projectId);
                for (int i = 0; i < resourceRoleIds.length; i++) {
                    ps.setInt(2 + i, resourceRoleIds[i]);
                }

                ResultSet rs = ps.executeQuery();

                Map<Integer, List<TermsOfUse>> result = getTermsOfUse(rs, includeNonMemberAgreeable);

                // Log method exit
                Helper.logExit(log, signature, new Object[] {result});
                return result;
            } catch (SQLException e) {
                throw new TermsOfUsePersistenceException("A database access error has occurred.", e);
            } finally {
                // Close the prepared statement
                // (The result set will be closed automatically)
                Helper.closeStatement(ps);
                // Close the connection
                Helper.closeConnection(conn);
            }
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * This method will remove all project role terms of use association for a given project.
     *
     * @param projectId
     *            the project id to remove.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void removeAllProjectRoleTermsOfUse(int projectId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".removeAllProjectRoleTermsOfUse(int projectId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId"},
            new Object[] {projectId});

        try {
            Helper.executeUpdate(getDBConnectionFactory(), DELETE_ALL_PROJECT_TERMS,
                new Object[] {projectId});

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves terms of use for specific pair of user and resource roles and groups it by terms of use groups. It
     * optionally can ignore non member agreeable terms of use.
     *
     * @param rs
     *            the result set.
     * @param includeNonMemberAgreeable
     *            the flag specifying whether to include non member agreeable terms of use.
     *
     * @return Map of lists of terms of use entities. The key of the map is the group index, the value is the list of
     *         terms for this group.
     *
     * @throws SQLException
     *             if any persistence error occurs.
     */
    private static Map<Integer, List<TermsOfUse>> getTermsOfUse(ResultSet rs, boolean includeNonMemberAgreeable)
        throws SQLException {
        Map<Integer, List<TermsOfUse>> result = new HashMap<Integer, List<TermsOfUse>>();

        // The result set is sorted by the group_ind
        // This variable holds the previous group. This is used to check whether new list of ids should be
        // created.
        Integer previousGroup = null;

        // The single list of terms ids, which contains ids from the same group.
        List<TermsOfUse> groupList = new ArrayList<TermsOfUse>();
        Integer group = 0;

        // Flag, specifying whether the groupList should not be added to result list
        // This is done when one of user terms of the list is not memberAgreeable
        // and includeNonMemberAgreeable flag == false
        boolean ignoreGroupList = false;

        // Iterate over all results
        while (rs.next()) {
            // Get the group
            group = rs.getInt("group_ind");

            // if moved to next group, add current list to result and clear.
            if (!group.equals(previousGroup)) {
                if (!groupList.isEmpty() && !ignoreGroupList) {
                    result.put(previousGroup, new ArrayList<TermsOfUse>(groupList));
                }
                groupList.clear();

                previousGroup = group;
                ignoreGroupList = false;
            }
            if (ignoreGroupList) {
                // Skip the group
                continue;
            }

            TermsOfUse terms = Helper.getTermsOfUse(rs, null, null);
            groupList.add(terms);

            // Check if the group is need to be skipped
            // This check is necessary to be done if input includeNonMemberAgreeable == false
            // Note that ignoreGroupList == false
            if (!includeNonMemberAgreeable) {
                ignoreGroupList = !terms.isMemberAgreeable();
            }
        }
        // If some data present, add to result
        if (!groupList.isEmpty() && !ignoreGroupList) {
            result.put(group, groupList);
        }

        return result;
    }

    /**
     * Converts the array to a list.
     *
     * @param array
     *            the array.
     *
     * @return the list or <code>null</code> if array is <code>null</code>.
     */
    private static List<Integer> toList(int[] array) {
        if (array == null) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

        return list;
    }
}
