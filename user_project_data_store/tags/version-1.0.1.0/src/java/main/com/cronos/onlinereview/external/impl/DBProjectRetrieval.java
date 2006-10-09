/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import com.cronos.onlinereview.external.ConfigException;
import com.cronos.onlinereview.external.ExternalObject;
import com.cronos.onlinereview.external.ExternalProject;
import com.cronos.onlinereview.external.ProjectRetrieval;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserProjectDataStoreHelper;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>This is the Database implementation of the ProjectRetrieval interface.</p>
 * <p>All the methods (except retrieveProject(long)) call super.getConnection to get a connection from
 * the DBConnectionFactory, and then call to super.retrieveObjects, which calls this.createObject.
 * Afterwards, the prepared statement, result set and connections are all closed using
 * super.close().</p>
 * <p>All SQLExceptions in all methods should be wrapped in RetrievalException.</p>
 * <p>This class is immutable and therefore thread-safe.</p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public class DBProjectRetrieval extends BaseDBRetrieval implements ProjectRetrieval {

    /**
     * <p>The name of the forum type property in the config file.</p>
     */
    private static final String FORUM_TYPE_STRING = "forumType";

    /**
     * <p>The default value of the forum type, it is 2.</p>
     */
    private static final int FORUM_TYPE_DEFAULT_VALUE = 2;

    /**
     * <p>The string denotes the database column name of the component version id.</p>
     */
    private static final String COMPONENT_VERSION_ID_STRING = "comp_vers_id";

    /**
     * <p>The string denotes the database column name of the version.</p>
     */
    private static final String VERSION_STRING = "version";

    /**
     * <p>The string denotes the database column name of the version text.</p>
     */
    private static final String VERSION_TEXT_STRING = "version_text";

    /**
     * <p>The string denotes the database column name of the component id.</p>
     */
    private static final String COMPONENT_ID_STRING = "component_id";

    /**
     * <p>The string denotes the database column name of the comments.</p>
     */
    private static final String COMMENTS_STRING = "comments";

    /**
     * <p>The string denotes the database column name of the forum id.</p>
     */
    private static final String FORUM_ID_STRING = "forum_id";

    /**
     * <p>The string denotes the database column name of the component name.</p>
     */
    private static final String COMPONENT_NAME_STRING = "component_name";

    /**
     * <p>The string denotes the database column name of the description.</p>
     */
    private static final String DESCRIPTION_STRING = "description";

    /**
     * <p>The string denotes the database column name of the root category id.</p>
     */
    private static final String ROOT_CATEGORY_ID_STRING = "category_id";

    /**
     * <p>The forum type code to use in the retrieve methods.</p>
     * <p>Sets by the constructor and never modified. The value will never be negative.</p>
     */
    private final int forumType;

    /**
     * <p>Constructs this object with the given parameters, by calling the super constructor.</p>
     *
     * @param connFactory the connection factory to use with this object.
     * @param connName the connection name to use when creating connections.
     * @param forumType the forum type code to use in the retrieve queries.
     *
     * @throws IllegalArgumentException if connFactory or connName is null or if forumType is negative
     * or connName is empty after trimmed.
     * @throws ConfigException if the connection name doesn't correspond to a connection the factory
     * knows about.
     */
    public DBProjectRetrieval(DBConnectionFactory connFactory, String connName, int forumType)
        throws ConfigException {

        super(connFactory, connName);

        UserProjectDataStoreHelper.validateNegative(forumType, "forumType");
        this.forumType = forumType;
    }

    /**
     * <p>Constructs this object with the given namespace by calling the super constructor.</p>
     * <p>Retrieves the forum type property named "forumType" from the namesapce to be used in each of
     * the retrieve methods.</p>
     * <p>If not present as a property, the forum type code defaults to 2.</p>
     *
     * @param namespace the name of the ConfigManager namespace; see BaseDBRetrieval(String) for details.
     *
     * @throws IllegalArgumentException if the parameter is null or empty after trim.
     * @throws ConfigException if the namespace could not be found, or if the connection factory could
     * not be instantiated with the given namespace, or if the connection name is unknown to the
     * connection factory, or if the "forumType" property was not positive.
     */
    public DBProjectRetrieval(String namespace)
        throws ConfigException {

        super(namespace);

        try {
            // Gets the string value of the forumType from the config file.
            String forumTypeStr = (String) ConfigManager.getInstance()
                .getProperty(namespace, FORUM_TYPE_STRING);

            if (forumTypeStr == null) {
                // Sets the forumType value to 2 as default.
                this.forumType = FORUM_TYPE_DEFAULT_VALUE;
            } else {
                int forumTypeInt = Integer.parseInt(forumTypeStr);

                // If the forumType property is not positive.
                if (forumTypeInt <= 0) {
                    throw new ConfigException("The forumType property should be positive.");
                }
                this.forumType = forumTypeInt;
            }
        } catch (NumberFormatException e) {
            throw new ConfigException("The forumType property cannot be parsed into an integer.", e);
        } catch (UnknownNamespaceException e) {
            throw new ConfigException("The namespace of the ConfigManager could not be found.", e);
        }
    }

    /**
     * <p>Retrieves the external project with the given id.</p>
     * <p>Simply calls retrieveProjects(long[]) and returns the first entry in the array. If the array
     * is empty, return null.</p>
     *
     * @return the external project which has the given id, or null if not found.
     * @param id the id of the project we are interested in.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject retrieveProject(long id)
        throws RetrievalException {

        // Gets projects by calling retrieveProjects(long[]).
        ExternalProject[] projects = retrieveProjects(new long[] {id});

        // If the array is empty, return null; else, return this first one.
        return (ExternalProject) UserProjectDataStoreHelper.retFirstObject(projects);
    }

    /**
     * <p>Retrieves the external projects with the given name and version.</p>
     * <p>Simply calls retrieveProjects(String[], String[]) and returns the entire array. Note that since
     * names and version texts are not unique (probably due to being in different catalogs) there may be
     * more than one project that corresponds to this name and version.</p>
     *
     * @return external projects which have the given name and version text.
     * @param name the name of the project we are interested in.
     * @param version the version of the project we are interested in.
     *
     * @trows IllegalArgumentException if either argument is null or empty after trim.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject[] retrieveProject(String name, String version)
        throws RetrievalException {

        // Delegates to retrieveProjects(String[], String[]).
        return retrieveProjects(new String[] {name}, new String[] {version});
    }

    /**
     * <p>Retrieves the external projects with the given ids. Note that retrieveProjects(ids)[i] will
     * not necessarily correspond to ids[i].</p>
     * <p>If an entry in ids was not found, no entry in the return array will be present. If there are
     * any duplicates in the input array, the output will NOT contain a duplicate ExternalProject.</p>
     *
     * @return an array of external projects who have the given ids. If none of the given ids were
     * found, an empty array will be returned. The index of the entries in the array will not necessarily
     * directly correspond to the entries in the ids array.
     * @param ids the ids of the projects we are interested in.
     *
     * @throws IllegalArgumentException if ids is null or any entry is not positive.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject[] retrieveProjects(long[] ids)
        throws RetrievalException {

        UserProjectDataStoreHelper.validateArray(ids, "ids");

        if (ids.length == 0) {
            return new ExternalProject[0];
        }

        // Constructs the queryAndClause string.
        String queryAndClause = "and cv.comp_vers_id in ";

        // Delegates to retrieveProjects(String, Object, int).
        return retrieveProjects(queryAndClause, ids, ids.length);
    }

    /**
     * <p>Retrieves the external projects with the given names and versions. Both the name
     * AND version has to match for the record to be retrieved. Note that retrieveProjects(names,
     * versions)[i] will not necessarily correspond to names[i] and versions[i].</p>
     * <p>If an entry in names/versions was not found, no entry in the return array will be present.
     * If there are any duplicates in the input array, the output will NOT contain a duplicate
     * ExternalProject.</p>
     *
     * @return an array of external projects who have the given names and versions. If none were
     * found, an empty array will be returned. The index of the entries in the array will not necessarily
     * directly correspond to the entries in the input array.
     * @param names the names of the projects we are interested in; each entry corresponds to the
     * same indexed entry in the versions array.
     * @param versions the versions of the projects we are interested in; each entry corresponds to
     * the same indexed entry in the names array.
     *
     * @throws IllegalArgumentException if either array is null or any entry in either array is null
     * or empty after trim, or the sizes of these two array are not the same.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject[] retrieveProjects(String[] names, String[] versions)
        throws RetrievalException {

        UserProjectDataStoreHelper.validateArray(names, "names");
        UserProjectDataStoreHelper.validateArray(versions, "versions");
        if (names.length != versions.length) {
            throw new IllegalArgumentException("The sizes of these two array are not the same.");
        }

        if (names.length == 0 && versions.length == 0) {
            return new ExternalProject[0];
        }

        // Constructs the queryAndClause string.
        String queryAndClause = "and cc.component_name || cv.version_text in ";

        String[] namesPlusVersions = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            namesPlusVersions[i] = names[i] + versions[i];
        }

        // Delegates to retrieveProjects(String, Object, int).
        return retrieveProjects(queryAndClause, namesPlusVersions, names.length);
    }

    /**
     * <p>Creates an ExternalProjectImpl from the columns in the given result set.</p>
     * <p>This method is called by super.retrieveObjects which is why it must return an ExternalObject
     * instead of an ExternalProjectImpl.</p>
     *
     * @return an ExternalProjectImpl with the columns of the given result set.
     * @param rs a result set row which contains the columns needed to instantiate an ExternalProjectImpl
     * object.
     *
     * @throws RetrievalException if rs didn't contain the required columns, or if any of them could
     * not be retrieved.
     */
    protected ExternalObject createObject(ResultSet rs)
        throws RetrievalException {

        ExternalProjectImpl retProjectImpl = null;

        try {
            // Creates the ExternalProjectImpl instance.
            long id = rs.getLong(COMPONENT_VERSION_ID_STRING);
            long versionId = rs.getLong(VERSION_STRING);
            String version = rs.getString(VERSION_TEXT_STRING);

            retProjectImpl = new ExternalProjectImpl(id, versionId, version);

            // Sets other fields of the ExternalProjectImpl instance.
            retProjectImpl.setCatalogId(rs.getLong(ROOT_CATEGORY_ID_STRING));
            retProjectImpl.setComments(rs.getString(COMMENTS_STRING));
            retProjectImpl.setComponentId(rs.getLong(COMPONENT_ID_STRING));
            retProjectImpl.setDescription(rs.getString(DESCRIPTION_STRING));
            retProjectImpl.setName(rs.getString(COMPONENT_NAME_STRING));

            Object forumIdObject = rs.getObject(FORUM_ID_STRING);
            if (forumIdObject != null) {
                // The project do have a forum id.
                retProjectImpl.setForumId(Long.parseLong(forumIdObject.toString()));
            }

        } catch (SQLException e) {
            throw new RetrievalException("Some of the project values cannot be retrieved.", e);
        } catch (NumberFormatException e) {
            throw new RetrievalException("The value of ForumId is not set as long value.", e);
        }

        return retProjectImpl;
    }

    /**
     * <p>Retrieves the external projects with the given parameters. The parameters can be long[]
     * or String[].</p>
     * <p>This method is called by retrieveProjects(long[]) and retrieveProjects(String[], String[]),
     * it does the common operations for these two methods.</p>
     *
     * @return an array of external projects who have the given names and versions. If none were
     * found, an empty array will be returned. The index of the entries in the array will not necessarily
     * directly correspond to the entries in the input array.
     * @param queryAndClause the query of the prepareStatement, given be the caller.
     * @param queryParameter the parameter of the query, it can be long[] or String[], due to the
     * different caller.
     * @param paramLength the length of the queryParameter array.
     *
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    private ExternalProject[] retrieveProjects(String queryAndClause, Object queryParameter, int paramLength)
        throws RetrievalException {

        // Opens the connection.
        Connection conn = super.getConnection();

        // Constructs the query string.
        String query = "SELECT cv.comp_vers_id, "
            + "cv.component_id, "
            + "version, "
            + "version_text, "
            + "comments, "
            + "component_name, "
            + "description, "
            + "cc.root_category_id category_id, "
            + "forum_id "
            + "FROM comp_versions cv, comp_catalog cc, OUTER comp_forum_xref f "
            + "WHERE cv.component_id = cc.component_id "
            + "and cv.comp_vers_id = f.comp_vers_id "
            + "and f.forum_type = ? ";
        query += queryAndClause + UserProjectDataStoreHelper.generateQuestionMarks(paramLength);

        // Prepares the statement.
        PreparedStatement ps = UserProjectDataStoreHelper.createStatement(conn, query,
                "projectQuery");

        // Sets the parameters
        try {
            ps.setLong(1, this.forumType);

            if (queryParameter instanceof long[]) {
                for (int i = 0; i < paramLength; i++) {
                    ps.setLong(i + 2, ((long[]) queryParameter)[i]);
                }
            } else if (queryParameter instanceof String[]) {
                for (int i = 0; i < paramLength; i++) {
                    ps.setString(i + 2, ((String[]) queryParameter)[i]);
                }
            }
        } catch (SQLException e) {
            throw new RetrievalException("Database access error occurs while setting the parameters.", e);
        }

        // Retrieves the objects.
        Map objects = super.retrieveObjects(ps);

        // Closes the Prepared Statement and connection
        super.close(ps, conn);

        // Converts the map to an ExternalProject array.
        ExternalProject[] projects = (ExternalProject[]) new LinkedList(objects.values())
            .toArray(new ExternalProject[0]);

        return projects;
    }
}
