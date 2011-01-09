/*
 * Copyright (C) 2007-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.persistence.Helper.DataType;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This class contains operations to create/update/retrieve project instances from the Informix database. It also
 * provides methods to retrieve database look up table values. It implements the ProjectPersistence interface to
 * provide a plug-in persistence for Informix database. It is used by the ProjectManagerImpl class. The constructor
 * takes a namespace parameter to initialize database connection.
 * </p>
 *
 * <p>
 * Note that in this class, delete operation is not supported. To delete a project, its status is set to 'Deleted'.
 * Create and update operations here work on the project and its related items as well. It means creating/updating a
 * project would involve creating/updating its properties.
 * </p>
 *
 * <p>
 * This abstract class does not manage the connection itself. It contains three abstract methods which should be
 * implemented by concrete subclass to manage the connection:
 * <ul>
 * <li><code>openConnection()</code> is used to acquire and open the connection.</li>
 * <li><code>closeConnection()</code> is used to dispose the connection if the queries are executed successfully.</li>
 * <li><code>closeConnectionOnError()</code> is used to handle the error if the SQL operation fails.</li>
 * </ul>
 * The transaction handling logic should be implemented in subclasses while the <code>Statement</code>s and
 * <code>ResultSet</code>s are handled in this abstract class.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is thread safe because it is immutable.
 * </p>
 *
 * <p>
 * Version 1.1.3 (End Of Project Analysis Release Assembly v1.0)
 * <ul>
 * <li>Updated {@link #getAllProjectTypes(Connection)} method and relevant constant strings to populate project type
 * entities with new <code>generic</code> property.</li>
 * <li>Updated {@link #getAllProjectCategories(Connection)} method and relevant constant strings to populate project
 * type entities with new <code>generic</code> property.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2
 * <ul>
 * <li>Added review_system_version column to tables project_type_lu and project_info_type_lu. Update queries to reflect
 * this change.</li>
 * </ul>
 * </p>
 *
 * @author tuenm, urtks, bendlund, fuyun, moonli, pvmagacho
 * @version 1.2
 */
public abstract class AbstractInformixProjectPersistence implements ProjectPersistence {
    /**
     * <p>
     * Represents the default value for Project Id sequence name. It is used to create id generator for project. This
     * value will be overridden by 'ProjectIdSequenceName' configuration parameter if it exist.
     * </p>
     */
    public static final String PROJECT_ID_SEQUENCE_NAME = "project_id_seq";

    /**
     * <p>
     * Represents the default value for project audit id sequence name. It is used to create id generator for project
     * audit. This value will be overridden by 'ProjectAuditIdSequenceName' configuration parameter if it exist.
     * </p>
     */
    public static final String PROJECT_AUDIT_ID_SEQUENCE_NAME = "project_audit_id_seq";

    /**
     * Represents the name of connection name parameter in configuration.
     */
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";

    /**
     * Represents the name of connection factory namespace parameter in configuration.
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "ConnectionFactoryNS";

    /**
     * Represents the name of project id sequence name parameter in configuration.
     */
    private static final String PROJECT_ID_SEQUENCE_NAME_PARAMETER = "ProjectIdSequenceName";

    /**
     * Represents the name of project audit id sequence name parameter in configuration.
     */
    private static final String PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER = "ProjectAuditIdSequenceName";

    /**
     * Represents the sql statement to query all project types.
     *
     * @version 1.2 Added review_system_version column.
     */
    private static final String QUERY_ALL_PROJECT_TYPES_SQL = "SELECT "
        + "project_type_id, name, description, is_generic, review_system_version FROM project_type_lu";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE, Helper.LONG_TYPE};

    /**
     * Represents the sql statement to query all project categories.
     */
    private static final String QUERY_ALL_PROJECT_CATEGORIES_SQL = "SELECT "
        + "category.project_category_id, category.name, category.description, "
        + "type.project_type_id, type.name, type.description, type.is_generic, type.review_system_version "
        + "FROM project_category_lu AS category " + "JOIN project_type_lu AS type "
        + "ON category.project_type_id = type.project_type_id";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project categories.
     */
    private static final DataType[] QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.BOOLEAN_TYPE, Helper.LONG_TYPE};

    /**
     * Represents the sql statement to query all project statuses.
     */
    private static final String QUERY_ALL_PROJECT_STATUSES_SQL = "SELECT "
        + "project_status_id, name, description FROM project_status_lu";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project statuses.
     */
    private static final DataType[] QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query all project property types.
     */
    private static final String QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL = "SELECT "
        + "project_info_type_id, name, description FROM project_info_type_lu";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project property types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query projects.
     */
    private static final String QUERY_PROJECTS_SQL = "SELECT "
        + "project.project_id, status.project_status_id, status.name, "
        + "category.project_category_id, category.name, type.project_type_id, type.name, type.review_system_version, "
        + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description "
        + "FROM project JOIN project_status_lu AS status ON project.project_status_id=status.project_status_id "
        + "JOIN project_category_lu AS category ON project.project_category_id=category.project_category_id "
        + "JOIN project_type_lu AS type ON category.project_type_id=type.project_type_id "
        + "WHERE project.project_id IN ";

    /**
     * Represents the sql statement to query projects by creation date.
     */
    private static final String QUERY_PROJECTS_BY_CREATE_DATE_SQL = "SELECT "
        + "  project.project_id, project_status_lu.project_status_id, project_status_lu.name, "
        + "  project_category_lu.project_category_id, project_category_lu.name, "
        + "  project_type_lu.project_type_id, project_type_lu.name, project_type_lu.version "
        + "  project.create_user, project.create_date, project.modify_user, project.modify_date, "
        + "  project_category_lu.description, pi1.value as project_name, pi2.value as project_version "
        + "  FROM project, project_category_lu, project_status_lu, project_type_lu, project_info pi1, "
        + "  project_info pi2 WHERE project.project_category_id = project_category_lu.project_category_id "
        + "  AND project.project_status_id = project_status_lu.project_status_id "
        + "  AND project_category_lu.project_type_id = project_type_lu.project_type_id "
        + "  AND pi1.project_id = project.project_id AND pi1.project_info_type_id = 6 "
        + "  AND pi2.project_id = project.project_id AND pi2.project_info_type_id = 7 "
        + "  AND (project.project_status_id != 3 and project.project_status_id != 2)"
        + "  AND date(project.create_date) > date(current) - ";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * projects.
     */
    private static final DataType[] QUERY_PROJECTS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE};

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * projects.
     */
    private static final DataType[] QUERY_PROJECTS_BY_CREATE_DATE_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query project properties.
     */
    private static final String QUERY_PROJECT_PROPERTIES_SQL = "SELECT "
        + "info.project_id, info_type.name, info.value " + "FROM project_info AS info "
        + "JOIN project_info_type_lu AS info_type " + "ON info.project_info_type_id=info_type.project_info_type_id "
        + "WHERE info.project_id IN ";

    /**
     * Represents the sql statement to query project properties.
     */
    private static final String QUERY_ONE_PROJECT_PROPERTIES_SQL = "SELECT "
        + "info.project_id, info_type.name, info.value "
        + "FROM project_info AS info JOIN project_info_type_lu AS info_type "
        + "ON info.project_info_type_id=info_type.project_info_type_id WHERE info.project_id = ?";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * project properties.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTIES_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query project property ids.
     */
    private static final String QUERY_PROJECT_PROPERTY_IDS_SQL = "SELECT "
        + "project_info_type_id FROM project_info WHERE project_id=?";

    /**
     * Represents the sql statement to query project property ids and values.
     */
    private static final String QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL = "SELECT "
        + "project_info_type_id, value FROM project_info WHERE project_id=?";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * project property ids.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * project property ids and values.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to create project.
     */
    private static final String CREATE_PROJECT_SQL = "INSERT INTO project "
        + "(project_id, project_status_id, project_category_id, "
        + "create_user, create_date, modify_user, modify_date) " + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to create project property.
     */
    private static final String CREATE_PROJECT_PROPERTY_SQL = "INSERT INTO project_info "
        + "(project_id, project_info_type_id, value, " + "create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to create project audit.
     */
    private static final String CREATE_PROJECT_AUDIT_SQL = "INSERT INTO project_audit "
        + "(project_audit_id, project_id, update_reason, " + "create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to update project.
     */
    private static final String UPDATE_PROJECT_SQL = "UPDATE project "
        + "SET project_status_id=?, project_category_id=?, modify_user=?, modify_date=? " + "WHERE project_id=?";

    /**
     * Represents the sql statement to update project property.
     */
    private static final String UPDATE_PROJECT_PROPERTY_SQL = "UPDATE project_info "
        + "SET value=?, modify_user=?, modify_date=CURRENT " + "WHERE project_id=? AND project_info_type_id=?";

    /**
     * Represents the sql statement to delete project properties.
     */
    private static final String DELETE_PROJECT_PROPERTIES_SQL = "DELETE FROM project_info "
        + "WHERE project_id=? AND project_info_type_id IN ";

    /**
     * <p>
     * Represents the audit creation type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_CREATE_TYPE = 1;

    /**
     * <p>
     * Represents the audit deletion type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_DELETE_TYPE = 2;

    /**
     * <p>
     * Represents the audit update type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_UPDATE_TYPE = 3;

    /**
     * Represents the SQL statement to audit project info.
     *
     * @since 1.1.2
     */
    private static final String PROJECT_INFO_AUDIT_INSERT_SQL = "INSERT INTO project_info_audit "
        + "(project_id, project_info_type_id, value, audit_action_type_id, action_date, action_user_id) "
        + "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The factory instance used to create connection to the database. It is initialized in the constructor using
     * DBConnectionFactory component and never changed after that. It will be used in various persistence methods of
     * this project.
     * </p>
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * Represents the database connection name that will be used by DBConnectionFactory. This variable is initialized
     * in the constructor and never changed after that. It will be used in create/update/retrieve method to create
     * connection. This variable can be null, which mean connection name is not defined in the configuration namespace
     * and default connection will be created.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the IDGenerator for project table. This variable is initialized in the constructor and never change
     * after that. It will be used in createProject() method to generate new Id for project..
     * </p>
     */
    private final IDGenerator projectIdGenerator;

    /**
     * <p>
     * Represents the IDGenerator for project audit table. This variable is initialized in the constructor and never
     * change after that. It will be used in updateProject() method to store update reason.
     * </p>
     */
    private final IDGenerator projectAuditIdGenerator;

    /**
     * <p>
     * Creates a new instance of <code>AbstractProjectPersistence</code> from the settings in configuration.
     * </p>
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>ConnectionFactoryNS: The namespace that contains settings for DB Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by DBConnectionFactory to create connection.
     * If missing, default connection will be created. It is optional.</li>
     * <li>ProjectIdSequenceName: The sequence name used to create Id generator for project Id. If missing default
     * value (project_id_seq) is used. It is optional.</li>
     * <li>ProjectAuditIdSequenceName: The sequence name used to create Id generator for project audit Id. If missing,
     * default value (project_audit_id_seq) is used. It is optional.</li>
     * </ul>
     * </p>
     * <p>
     * Configuration sample:
     *
     * <pre>
     * &lt;Config name=&quot;AbstractInformixProjectPersistence&quot;&gt;
     * &lt;!-- The DBConnectionFactory class name used to create DB Connection Factory, required --&gt;
     *   &lt;Property name=&quot;ConnectionFactoryNS&quot;&gt;
     *    &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name=&quot;ConnectionName&quot;&gt;
     *    &lt;Value&gt;informix_connection&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name=&quot;ProjectIdSequenceName&quot;&gt;
     *    &lt;Value&gt;project_id_seq&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name=&quot;ProjectAuditIdSequenceName&quot;&gt;
     *    &lt;Value&gt;project_audit_id_seq&lt;/Value&gt;
     *   &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * </p>
     *
     * @param namespace The namespace to load configuration setting.
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws ConfigurationException if error occurs while loading configuration settings, or required configuration
     *             parameter is missing.
     * @throws PersistenceException if cannot initialize the connection to the database.
     */
    protected AbstractInformixProjectPersistence(String namespace) throws ConfigurationException,
        PersistenceException {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");

        // get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        // get db connection factory namespace
        String factoryNamespace = Helper.getConfigurationParameterValue(cm, namespace,
            CONNECTION_FACTORY_NAMESPACE_PARAMETER, true, getLogger());

        // try to create a DBConnectionFactoryImpl instance from
        // factoryNamespace
        try {
            factory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (Exception e) {
            throw new ConfigurationException("Unable to create a new instance of DBConnectionFactoryImpl class"
                + " from namespace [" + factoryNamespace + "].", e);
        }

        // get the connection name
        connectionName = Helper.getConfigurationParameterValue(cm, namespace, CONNECTION_NAME_PARAMETER, false,
            getLogger());

        // try to get project id sequence name
        String projectIdSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            PROJECT_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project id sequence name is not provided
        if (projectIdSequenceName == null) {
            projectIdSequenceName = PROJECT_ID_SEQUENCE_NAME;
        }

        // try to get project audit id sequence name
        String projectAuditIdSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project audit id sequence name is not provided
        if (projectAuditIdSequenceName == null) {
            projectAuditIdSequenceName = PROJECT_AUDIT_ID_SEQUENCE_NAME;
        }

        // try to get the IDGenerators
        try {
            projectIdGenerator = IDGeneratorFactory.getIDGenerator(projectIdSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR, "The projectIdSequence [" + projectIdSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + projectIdSequenceName + "'.", e);
        }
        try {
            projectAuditIdGenerator = IDGeneratorFactory.getIDGenerator(projectAuditIdSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR,
                "The projectAuditIdSequence [" + projectAuditIdSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + projectAuditIdSequenceName + "'.",
                e);
        }
    }

    /**
     * <p>
     * Creates the project in the database using the given project instance.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its properties are stored in 'project_info' table.
     * The project's associating scorecards are stored in 'project_scorecard' table. For the project, its properties
     * and associating scorecards, the operator parameter is used as the creation/modification user and the creation
     * date and modification date will be the current date time when the project is created.
     * </p>
     *
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void createProject(Project project, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the project
        Long newId;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "creating new project: " + project.getAllProperties()));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (project.getId() > 0) {
                if (Helper.checkEntityExists("project", "project_id", project.getId(), conn)) {
                    throw new PersistenceException("The project with the same id [" + project.getId()
                        + "] already exists.");
                }
            }

            try {
                // generate id for the project
                newId = new Long(projectIdGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new project"));
            } catch (IDGenerationException e) {
                throw new PersistenceException("Unable to generate id for the project.", e);
            }

            // create the project
            createProject(newId, project, operator, conn);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to create project " + project.getAllProperties(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the newId when no exception occurred
        project.setId(newId.longValue());

    }

    /**
     * <p>
     * Update the given project instance into the database.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its properties are stored in 'project_info' table.
     * The project's associating scorecards are stored in 'project_scorecard' table. All related items in these tables
     * will be updated. If items are removed from the project, they will be deleted from the persistence. Likewise, if
     * new items are added, they will be created in the persistence. For the project, its properties and associating
     * scorecards, the operator parameter is used as the modification user and the modification date will be the
     * current date time when the project is updated. An update reason is provided with this method. Update reason
     * will be stored in the persistence for future references.
     * </p>
     *
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for future references.
     * @param operator The modification user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is empty string. Or project id is zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertLongPositive(project.getId(), "project id");
        Helper.assertObjectNotNull(reason, "reason");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        // modifyDate will contain the modify_date retrieved from database.
        Date modifyDate;

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(new Long(project.getId()), operator, "updating project: " + project.getAllProperties()));
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", project.getId(), conn)) {
                throw new PersistenceException("The project id [" + project.getId()
                    + "] does not exist in the database.");
            }

            // update the project
            updateProject(project, reason, operator, conn);

            getLogger().log(
                Level.INFO,
                new LogMessage(new Long(project.getId()), operator, "execute sql:" + "SELECT modify_date "
                    + "FROM project WHERE project_id=?"));
            // get the modification date.
            modifyDate = (Date) Helper.doSingleValueQuery(conn, "SELECT modify_date "
                + "FROM project WHERE project_id=?", new Object[] {new Long(project.getId())}, Helper.DATE_TYPE);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to update project " + project.getAllProperties(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the modification user and date when no exception
        // occurred.
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
    }

    /**
     * Retrieves the project instance from the persistence given its id. The project instance is retrieved with its
     * related items, such as properties and scorecards.
     *
     * @return The project instance.
     * @param id The id of the project to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException {
        Helper.assertLongPositive(id, "id");

        Project[] projects = getProjects(new long[] {id});
        return projects.length == 0 ? null : projects[0];
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their ids. The project instances are
     * retrieved with their properties.
     * </p>
     *
     * @param ids ids The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws PersistenceException
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        Helper.assertObjectNotNull(ids, "ids");

        // check if ids is empty
        if (ids.length == 0) {
            throw new IllegalArgumentException("Array 'ids' should not be empty.");
        }

        String idstring = "";
        // check if ids contains an id that is <= 0
        for (int i = 0; i < ids.length; ++i) {
            idstring += ids[i] + ",";
            if (ids[i] <= 0) {
                throw new IllegalArgumentException("Array 'ids' contains an id that is <= 0.");
            }
        }

        Connection conn = null;

        getLogger().log(Level.INFO, "get projects with the ids: " + idstring.substring(0, idstring.length() - 1));
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Project[] projects = getProjects(ids, conn);
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to retrieving projects with ids: "
                    + idstring.substring(0, idstring.length() - 1), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose create date is within current - days.
     * </p>
     *
     * @param days last 'days'
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjectsByCreateDate(int days) throws PersistenceException {

        Connection conn = null;

        getLogger().log(Level.INFO, "get projects by create date: " + days);
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Project[] projects = getProjectsByCreateDate(days, conn);
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
            getLogger()
                .log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving by create date: " + days, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project types in the persistence. The project types are stored in 'project_type_lu' table.
     *
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllProjectTypes method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project types
            ProjectType[] projectTypes = getAllProjectTypes(conn);
            closeConnection(conn);
            return projectTypes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllProjectTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project categories in the persistence. The project categories are stored in
     * 'project_category_lu' table.
     *
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllProjectCategories method."));
        try {
            // create the connection
            conn = openConnection();

            // get all categories
            ProjectCategory[] projectCategories = getAllProjectCategories(conn);

            closeConnection(conn);
            return projectCategories;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllProjectCategories.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project statuses in the persistence. The project statuses are stored in
     * 'project_status_lu' table.
     *
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllProjectStatuses method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project statuses
            ProjectStatus[] projectStatuses = getAllProjectStatuses(conn);
            closeConnection(conn);
            return projectStatuses;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllProjectStatuses.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project property type in the persistence. The project property types are stored in
     * 'project_info_type_lu' table.
     *
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllProjectPropertyTypes method."));
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            ProjectPropertyType[] propertyTypes = getAllProjectPropertyTypes(conn);
            closeConnection(conn);
            return propertyTypes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllProjectPropertyTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Returns the database connection name that will be used by DB Connection Factory.
     *
     * @return a possibly <code>null</code> string representing the connection name used in DB Connection Factory.
     */
    protected String getConnectionName() {
        return connectionName;
    }

    /**
     * returns the <code>DBConnectionFactory</code> instance used to create connection to the database.
     *
     * @return the <code>DBConnectionFactory</code> instance used to create connection to the database.
     */
    protected DBConnectionFactory getConnectionFactory() {
        return factory;
    }

    /**
     * <p>
     * Return the getLogger().
     * </p>
     *
     * @return the <code>Log</code> instance used to take the log message
     */
    protected abstract Log getLogger();

    /**
     * <p>
     * Abstract method used to get an open connection from which to perform DB operations.
     * </p>
     * <p>
     * The implementations in subclasses will get a connection and properly prepare it for use, depending on the
     * transaction management strategy used in the subclass.
     * </p>
     *
     * @return an open connection used for DB operation.
     * @throws PersistenceException if there's a problem getting the Connection
     */
    protected abstract Connection openConnection() throws PersistenceException;

    /**
     * <p>
     * Abstract method used to commit any transaction (if necessary in the subclass) and close the given connection
     * after an operation completes successfully.
     * </p>
     * <p>
     * It is used by all public methods after the successful execution of DB operation.
     * </p>
     * <p>
     * The implementations in subclasses will close the given connection. Depending on the transaction management
     * strategy used in the subclass, a transaction on the connection may be committed.
     * </p>
     *
     * @param connection connection to close
     * @throws PersistenceException if any problem occurs trying to close the connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnection(Connection connection) throws PersistenceException;

    /**
     * <p>
     * Abstract method used to rollback any transaction (if necessary in the subclass) and close the given connection
     * when an error occurs.
     * </p>
     * <p>
     * It is used by all public methods just before any exception is thrown if fails to do DB operation..
     * </p>
     * <p>
     * The implementations in subclasses will close the given connection. Depending on the transaction management
     * strategy used in the subclass, a transaction on the connection may be rolled back.
     * </p>
     *
     * @param connection connection to close
     * @throws PersistenceException if any problem occurs closing the Connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnectionOnError(Connection connection) throws PersistenceException;

    /**
     * Creates the project in the database using the given project instance.
     *
     * @param projectId The new generated project id
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @param conn The database connection
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private void createProject(Long projectId, Project project, String operator, Connection conn)
        throws PersistenceException {

        getLogger().log(Level.INFO, "insert record into project with id:" + projectId);

        // insert the project into database
        Object[] queryArgs = new Object[] {projectId, new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator, operator};
        Helper.doDMLQuery(conn, CREATE_PROJECT_SQL, queryArgs);

        // get the creation date.
        Date createDate = (Date) Helper.doSingleValueQuery(conn,
            "SELECT create_date FROM project WHERE project_id=?", new Object[] {projectId}, Helper.DATE_TYPE);

        // set the creation/modification user and date when no exception
        // occurred
        project.setCreationUser(operator);
        project.setCreationTimestamp(createDate);
        project.setModificationUser(operator);
        project.setModificationTimestamp(createDate);

        // get the property id - property value map from the project.
        Map<Long, String> idValueMap = makePropertyIdPropertyValueMap(project.getAllProperties(), conn);

        // create the project properties
        createProjectProperties(projectId, project, idValueMap, operator, conn);
    }

    /**
     * Update the given project instance into the database.
     *
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for future references.
     * @param operator The modification user of this project.
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private void updateProject(Project project, String reason, String operator, Connection conn)
        throws PersistenceException {
        Long projectId = project.getId();

        getLogger()
            .log(Level.INFO, new LogMessage(projectId, operator, "update project with projectId:" + projectId));

        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());

        // update the project type and project category
        Object[] queryArgs = new Object[] {new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator, modifyDate, projectId};
        Helper.doDMLQuery(conn, UPDATE_PROJECT_SQL, queryArgs);

        // update the project object so this data's correct for audit purposes
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);

        // get the property id - property value map from the new project object.
        Map<Long, String> idValueMap = makePropertyIdPropertyValueMap(project.getAllProperties(), conn);

        // update the project properties
        updateProjectProperties(project, idValueMap, operator, conn);

        // create project audit record into project_audit table
        createProjectAudit(projectId, reason, operator, conn);
    }

    /**
     * Makes a property id-property value(String) map from property name-property value map.
     *
     * @param nameValueMap the property name-property value map
     * @param conn the database connection
     * @return a property id-property value map
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private Map<Long, String> makePropertyIdPropertyValueMap(Map<String, Object> nameValueMap, Connection conn)
        throws PersistenceException {
        Map<Long, String> idValueMap = new HashMap<Long, String>();

        // get the property name-property id map
        Map<String, Long> nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));

        // enumerate each property
        for (Iterator<Entry<String, Object>> it = nameValueMap.entrySet().iterator(); it.hasNext();) {
            Entry<String, Object> entry = it.next();

            // find the property id from property name
            Long propertyId = nameIdMap.get(entry.getKey());
            // check if the property id can be found
            if (propertyId == null) {
                throw new PersistenceException("Unable to find ProjectPropertyType name [" + entry.getKey()
                    + "] in project_info_type_lu table.");
            }

            // put the property id-property value(String) pair into the map
            idValueMap.put(propertyId, entry.getValue().toString());
        }

        return idValueMap;
    }

    /**
     * Gets an array of all project property type in the persistence. The project property types are stored in
     * 'project_info_type_lu' table.
     *
     * @param conn the database connection
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private ProjectPropertyType[] getAllProjectPropertyTypes(Connection conn) throws PersistenceException {
        // find all project property types in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL, new Object[] {},
            QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES);

        // create the ProjectPropertyType array.
        ProjectPropertyType[] propertyTypes = new ProjectPropertyType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectPropertyType class
            propertyTypes[i] = new ProjectPropertyType(((Long) row[0]).longValue(), (String) row[1], (String) row[2]);
        }

        return propertyTypes;
    }

    /**
     * Get projects from sql result.
     *
     * @param result result set from sql query
     * @return An array with project found in persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(CustomResultSet result) throws PersistenceException {
        Connection conn = null;
        try {
            conn = openConnection();
            PreparedStatement ps = conn.prepareStatement(QUERY_ONE_PROJECT_PROPERTIES_SQL);
            int size = result.getRecordCount();
            Project[] projects = new Project[size];
            for (int i = 0; i < size; i++) {
                result.absolute(i + 1);

                // create the ProjectStatus object
                ProjectStatus status = new ProjectStatus(result.getLong(2), result.getString(3));

                // create the ProjectType object
                ProjectType type = new ProjectType(result.getLong(6), result.getString(7), result.getInt(8));

                // create the ProjectCategory object
                ProjectCategory category = new ProjectCategory(result.getLong(4), result.getString(5), type);

                // create a new instance of ProjectType class
                projects[i] = new Project(result.getLong(1), category, status);

                // assign the audit information
                projects[i].setCreationUser(result.getString(9));
                projects[i].setCreationTimestamp(result.getDate(10));
                projects[i].setModificationUser(result.getString(11));
                projects[i].setModificationTimestamp(result.getDate(12));

                ps.setLong(1, projects[i].getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    projects[i].setProperty(rs.getString(2), rs.getString(3));
                }
            }
            return projects;
        } catch (InvalidCursorStateException icse) {
            throw new PersistenceException("cursor state is invalid.", icse);
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their ids. The project instances are
     * retrieved with their properties.
     * </p>
     *
     * @param ids The ids of the projects to be retrieved.
     * @param conn the database connection
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private Project[] getProjects(long ids[], Connection conn) throws PersistenceException {

        // build the id list string
        StringBuffer idListBuffer = new StringBuffer();
        idListBuffer.append('(');
        for (int i = 0; i < ids.length; ++i) {
            if (i != 0) {
                idListBuffer.append(',');
            }
            idListBuffer.append(ids[i]);
        }
        idListBuffer.append(')');
        // get the id list string
        String idList = idListBuffer.toString();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECTS_SQL + idList, new Object[] {},
            QUERY_PROJECTS_COLUMN_TYPES);

        // create the Project array.
        Project[] projects = new Project[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectStatus object
            ProjectStatus status = new ProjectStatus((Long) row[1], (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType((Long) row[5], (String) row[6], (Long) row[7]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory((Long) row[3], (String) row[4], type);
            category.setDescription((String) row[12]);
            // create a new instance of ProjectType class
            projects[i] = new Project((Long) row[0], category, status);

            // assign the audit information
            projects[i].setCreationUser((String) row[8]);
            projects[i].setCreationTimestamp((Date) row[9]);
            projects[i].setModificationUser((String) row[10]);
            projects[i].setModificationTimestamp((Date) row[11]);
        }

        // get the Id-Project map
        Map<Long, Project> projectMap = makeIdProjectMap(projects);

        // find project properties in the table.
        rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTIES_SQL + idList, new Object[] {},
            QUERY_PROJECT_PROPERTIES_COLUMN_TYPES);

        // enumerate each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // get the corresponding Project object
            Project project = projectMap.get(row[0]);

            // set the property to project
            project.setProperty((String) row[1], row[2]);
        }
        return projects;
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose create date is within current - days.
     * </p>
     *
     * @param days last 'days'
     * @param conn the database connection
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the database.
     *
     * @since 1.1 Added review_system_version column.
     */
    private Project[] getProjectsByCreateDate(int days, Connection conn) throws PersistenceException {

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECTS_BY_CREATE_DATE_SQL + days, new Object[] {},
            QUERY_PROJECTS_BY_CREATE_DATE_COLUMN_TYPES);

        // create the Project array.
        Project[] projects = new Project[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectStatus object
            ProjectStatus status = new ProjectStatus((Long) row[1], (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType((Long) row[5], (String) row[6], (Long) row[7]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory((Long) row[3], (String) row[4], type);
            category.setDescription((String) row[12]);
            // create a new instance of ProjectType class
            projects[i] = new Project((Long) row[0], category, status);

            // assign the audit information
            projects[i].setCreationUser((String) row[8]);
            projects[i].setCreationTimestamp((Date) row[9]);
            projects[i].setModificationUser((String) row[10]);
            projects[i].setModificationTimestamp((Date) row[11]);

            // here we only get project name and project version
            projects[i].setProperty("Project Name", (String) row[13]);
            projects[i].setProperty("Project Version", (String) row[14]);
        }

        return projects;
    }

    /**
     * Gets an array of all project types in the persistence. The project types are stored in 'project_type_lu' table.
     *
     * @param conn the database connection
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     *
     * @since 1.1 Added review_system_version column.
     */
    private ProjectType[] getAllProjectTypes(Connection conn) throws PersistenceException {
        // find all project types in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_TYPES_SQL, new Object[] {},
            QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES);

        // create the ProjectType array.
        ProjectType[] projectTypes = new ProjectType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectType class
            projectTypes[i] = new ProjectType((Long) row[0], (String) row[1], (String) row[2], (Boolean) row[3],
                (Long) row[4]);
        }

        return projectTypes;
    }

    /**
     * Create the project properties in the database.
     *
     * @param projectId the project's Id
     * @param project The new generated project
     * @param idValueMap The property id - property value map
     * @param operator The creation user of this project
     * @param conn The database connection
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private void createProjectProperties(Long projectId, Project project, Map<Long, String> idValueMap,
        String operator, Connection conn) throws PersistenceException {

        getLogger().log(Level.INFO,
            new LogMessage(projectId, operator, "insert record into project_info with project id" + projectId));
        PreparedStatement preparedStatement = null;
        try {
            // prepare the statement.
            preparedStatement = conn.prepareStatement(CREATE_PROJECT_PROPERTY_SQL);

            // enumerator each property in the idValueMap
            for (Iterator<Entry<Long, String>> it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry<Long, String> entry = it.next();

                // insert the project property into database
                Object[] queryArgs = new Object[] {projectId, entry.getKey(), entry.getValue(), operator, operator};
                Helper.doDMLQuery(preparedStatement, queryArgs);

                auditProjectInfo(conn, projectId, project, AUDIT_CREATE_TYPE, entry.getKey(), entry.getValue());
            }

        } catch (SQLException e) {
            throw new PersistenceException("Unable to create prepared statement [" + CREATE_PROJECT_PROPERTY_SQL
                + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }

    /**
     * Make an Id-Project map from Project[].
     *
     * @param projects the Id-Project array
     * @return an Id-Project map
     */
    private Map<Long, Project> makeIdProjectMap(Project[] projects) {
        Map<Long, Project> map = new HashMap<Long, Project>();

        for (int i = 0; i < projects.length; ++i) {
            map.put(projects[i].getId(), projects[i]);
        }

        return map;
    }

    /**
     * Update the project properties into the database.
     *
     * @param project the project object
     * @param idValueMap the property id - property value map
     * @param operator the modification user of this project
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private void updateProjectProperties(Project project, Map<Long, String> idValueMap, String operator,
        Connection conn) throws PersistenceException {

        Long projectId = project.getId();

        // get old property ids and values from database
        Map<Long, String> propertyMap = getProjectPropertyIdsAndValues(projectId, conn);

        // create a property id-property value map that contains the properties
        // to insert
        Map<Long, String> createIdValueMap = new HashMap<Long, String>();

        PreparedStatement preparedStatement = null;
        try {
            getLogger()
                .log(
                    Level.INFO,
                    new LogMessage(projectId, operator, "update project, update project_info with projectId:"
                        + projectId));

            // prepare the statement.
            preparedStatement = conn.prepareStatement(UPDATE_PROJECT_PROPERTY_SQL);

            // enumerator each property id in the project object
            for (Iterator<Entry<Long, String>> it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry<Long, String> entry = it.next();

                Long propertyId = entry.getKey();

                // check if the property in the project object already exists in
                // the database
                if (propertyMap.containsKey(propertyId)) {
                    // if the value hasn't been changed, we don't need to update anything
                    if (!propertyMap.get(propertyId).equals((String) entry.getValue())) {
                        // update the project property
                        Object[] queryArgs = new Object[] {entry.getValue(), operator, projectId, propertyId};
                        Helper.doDMLQuery(preparedStatement, queryArgs);

                        auditProjectInfo(conn, project, AUDIT_UPDATE_TYPE, propertyId, entry.getValue());
                    }
                    propertyMap.remove(propertyId);
                } else {
                    // add the entry to the createIdValueMap
                    createIdValueMap.put(propertyId, entry.getValue());
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Unable to create prepared statement [" + UPDATE_PROJECT_PROPERTY_SQL
                + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }

        // create the new properties
        createProjectProperties(project.getId(), project, createIdValueMap, operator, conn);

        // delete the remaining property ids that are not in the project object
        // any longer
        deleteProjectProperties(project, propertyMap.keySet(), conn);
    }

    /**
     * Gets all the property ids associated to this project.
     *
     * @param projectId The id of this project
     * @param conn The database connection
     * @return A set that contains the property ids
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private Set<Long> getProjectPropertyIds(Long projectId, Connection conn) throws PersistenceException {
        Set<Long> idSet = new HashSet<Long>();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTY_IDS_SQL, new Object[] {projectId},
            QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the set
            idSet.add((Long) row[0]);
        }
        return idSet;
    }

    /**
     * Gets all the property ids and values associated to this project.
     *
     * @param projectId The id of this project
     * @param conn The database connection
     * @return A map that contains the property values, keyed by id
     *
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private Map<Long, String> getProjectPropertyIdsAndValues(Long projectId, Connection conn)
        throws PersistenceException {

        Map<Long, String> idMap = new HashMap<Long, String>();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL, new Object[] {projectId},
            QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the map
            idMap.put((Long) row[0], (String) row[1]);
        }

        return idMap;
    }

    /**
     * Delete the project properties from the database.
     *
     * @param project the project object
     * @param propertyIdSet the Set that contains the property ids to delete
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private void deleteProjectProperties(Project project, Set<Long> propertyIdSet, Connection conn)
        throws PersistenceException {

        Long projectId = project.getId();

        // check if the property id set is empty
        // do nothing if property id set is empty
        if (!propertyIdSet.isEmpty()) {

            // build the id list string
            StringBuffer idListBuffer = new StringBuffer();
            idListBuffer.append('(');
            int idx = 0;
            for (Long id : propertyIdSet) {
                if (idx++ != 0) {
                    idListBuffer.append(',');
                }
                idListBuffer.append(id);
            }
            idListBuffer.append(')');

            getLogger().log(Level.INFO,
                new LogMessage(projectId, null, "delete records from project_info with projectId:" + projectId));

            // delete the properties whose id is in the set
            Helper
                .doDMLQuery(conn, DELETE_PROJECT_PROPERTIES_SQL + idListBuffer.toString(), new Object[] {projectId});

            for (Long id : propertyIdSet) {
                auditProjectInfo(conn, project, AUDIT_DELETE_TYPE, id, null);
            }
        }
    }

    /**
     * Create a project audit record into the database.
     *
     * @param projectId The id of the project
     * @param reason The update reason
     * @param operator the modification user of this project
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private void createProjectAudit(Long projectId, String reason, String operator, Connection conn)
        throws PersistenceException {

        Long auditId;
        try {
            // generate a new audit id
            auditId = new Long(projectAuditIdGenerator.getNextID());
            getLogger()
                .log(Level.INFO, new LogMessage(projectId, operator, "generate new id for the project audit."));
        } catch (IDGenerationException e) {
            throw new PersistenceException("Unable to generate id for project.", e);
        }
        getLogger().log(Level.INFO, "insert record into project_audit with projectId:" + projectId);
        // insert the update reason information to project_audit table
        Object[] queryArgs = new Object[] {auditId, projectId, reason, operator, operator};
        Helper.doDMLQuery(conn, CREATE_PROJECT_AUDIT_SQL, queryArgs);
    }

    /**
     * Make a property name - property id map from ProjectPropertyType[].
     *
     * @param propertyTypes the ProjectPropertyType array
     * @return a property name - property id map
     * @throws PersistenceException if duplicate property type names are found
     */
    private Map<String, Long> makePropertyNamePropertyIdMap(ProjectPropertyType[] propertyTypes)
        throws PersistenceException {
        Map<String, Long> map = new HashMap<String, Long>();

        // enumerate each property type
        for (int i = 0; i < propertyTypes.length; ++i) {

            // check if the property name already exists
            if (map.containsKey(propertyTypes[i].getName())) {
                throw new PersistenceException("Duplicate project property type names [" + propertyTypes[i].getName()
                    + "] found in project_info_type_lu table.");
            }

            // put the name-id pair to the map
            map.put(propertyTypes[i].getName(), propertyTypes[i].getId());
        }

        return map;
    }

    /**
     * Gets an array of all project categories in the persistence. The project categories are stored in
     * 'project_category_lu' table.
     *
     * @param conn the database connection
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private ProjectCategory[] getAllProjectCategories(Connection conn) throws PersistenceException {
        // find all project categories in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_CATEGORIES_SQL, new Object[] {},
            QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES);

        // create the ProjectCategory array.
        ProjectCategory[] projectCategories = new ProjectCategory[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectType object
            ProjectType type = new ProjectType((Long) row[3], (String) row[4], (String) row[5], (Boolean) row[6],
                (Long) row[7]);

            // create a new instance of ProjectCategory class
            projectCategories[i] = new ProjectCategory((Long) row[0], (String) row[1], (String) row[2], type);
        }

        return projectCategories;
    }

    /**
     * Gets an array of all project statuses in the persistence. The project statuses are stored in
     * 'project_status_lu' table.
     *
     * @param conn the database connection
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private ProjectStatus[] getAllProjectStatuses(Connection conn) throws PersistenceException {
        // find all project statuses in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_STATUSES_SQL, new Object[] {},
            QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES);

        // create the ProjectStatus array.
        ProjectStatus[] projectStatuses = new ProjectStatus[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectStatus class
            projectStatuses[i] = new ProjectStatus(((Long) row[0]).longValue(), (String) row[1], (String) row[2]);
        }

        return projectStatuses;
    }

    /**
     * This method will audit project information. This information is generated when most project properties are
     * inserted, deleted, or edited.
     *
     * @param connection the connection to database
     * @param projectId the id of the project being audited
     * @param project the project being audited
     * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or AUDIT_UPDATE_TYPE
     * @param projectInfoTypeId the project info type id
     * @param value the project info value that we're changing to
     *
     * @throws PersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.1.2
     */
    private void auditProjectInfo(Connection connection, Long projectId, Project project, int auditType,
        long projectInfoTypeId, String value) throws PersistenceException {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(PROJECT_INFO_AUDIT_INSERT_SQL);

            int index = 1;
            statement.setLong(index++, projectId);
            statement.setLong(index++, projectInfoTypeId);
            statement.setString(index++, value);
            statement.setInt(index++, auditType);
            statement.setTimestamp(index++, new Timestamp(project.getModificationTimestamp().getTime()));
            statement.setLong(index++, Long.parseLong(project.getModificationUser()));

            if (statement.executeUpdate() != 1) {
                throw new PersistenceException("Audit information was not successfully saved.");
            }
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            throw new PersistenceException("Unable to insert project_info_audit record.", e);
        } finally {
            Helper.closeStatement(statement);
        }
    }

    /**
     * This method will audit project information. This information is generated when most project properties are
     * inserted, deleted, or edited.
     *
     * @param connection the connection to database
     * @param project the project being audited
     * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or AUDIT_UPDATE_TYPE
     * @param projectInfoTypeId the project info type id
     * @param value the project info value that we're changing to
     *
     * @throws PersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.1.2
     */
    private void auditProjectInfo(Connection connection, Project project, int auditType, long projectInfoTypeId,
        String value) throws PersistenceException {
        auditProjectInfo(connection, project.getId(), project, auditType, projectInfoTypeId, value);
    }
}
