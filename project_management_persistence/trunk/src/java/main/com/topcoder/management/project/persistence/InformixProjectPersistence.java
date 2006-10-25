/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.topcoder.db.connectionfactory.DBConnectionException;
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
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * This class contains operations to create/update/retrieve project instances
 * from the Informix database. It also provides methods to retrieve database
 * look up table values. It implements the ProjectPersistence interface to
 * provide a plug-in persistence for Informix database. It is used by the
 * ProjectManagerImpl class. The constructor takes a namespace parameter to
 * initialize database connection.
 * </p>
 * <p>
 * Note that in this class, delete operation is not supported. To delete a
 * project, its status is set to 'Deleted'. Create and update operations here
 * work on the project and its related items as well. It means creating/updating
 * a project would involve creating/updating its properties.
 * </p>
 * <p>
 * Thread Safety: The implementation is not thread safe in that two threads
 * running the same method will use the same statement and could overwrite each
 * other's work.
 * </p>
 * @author tuenm, urtks
 * @version 1.0
 */
public class InformixProjectPersistence implements ProjectPersistence {
    /**
     * <p>
     * Represents the default value for Project Id sequence name. It is used to
     * create id generator for project. This value will be overridden by
     * 'ProjectIdSequenceName' configuration parameter if it exist.
     * </p>
     */
    public static final String PROJECT_ID_SEQUENCE_NAME = "project_id_seq";

    /**
     * <p>
     * Represents the default value for project audit id sequence name. It is
     * used to create id generator for project audit. This value will be
     * overridden by 'ProjectAuditIdSequenceName' configuration parameter if it
     * exist.
     * </p>
     */
    public static final String PROJECT_AUDIT_ID_SEQUENCE_NAME = "project_audit_id_seq";

    /**
     * Represents the name of connection name parameter in configuration.
     */
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";

    /**
     * Represents the name of connection factory namespace parameter in
     * configuration.
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "ConnectionFactoryNS";

    /**
     * Represents the name of project id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_ID_SEQUENCE_NAME_PARAMETER = "ProjectIdSequenceName";

    /**
     * Represents the name of project audit id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER = "ProjectAuditIdSequenceName";

    /**
     * Represents the sql statement to query all project types.
     */
    private static final String QUERY_ALL_PROJECT_TYPES_SQL = "SELECT "
        + "project_type_id, name, description FROM project_type_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query all project categories.
     */
    private static final String QUERY_ALL_PROJECT_CATEGORIES_SQL = "SELECT "
        + "category.project_category_id, category.name, category.description, "
        + "type.project_type_id, type.name, type.description "
        + "FROM project_category_lu AS category " + "JOIN project_type_lu AS type "
        + "ON category.project_type_id = type.project_type_id";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project categories.
     */
    private static final DataType[] QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query all project statuses.
     */
    private static final String QUERY_ALL_PROJECT_STATUSES_SQL = "SELECT "
        + "project_status_id, name, description FROM project_status_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project statuses.
     */
    private static final DataType[] QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query all project property types.
     */
    private static final String QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL = "SELECT "
        + "project_info_type_id, name, description FROM project_info_type_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project property types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query projects.
     */
    private static final String QUERY_PROJECTS_SQL = "SELECT "
        + "project.project_id, status.project_status_id, status.name, "
        + "category.project_category_id, category.name, type.project_type_id, type.name, "
        + "project.create_user, project.create_date, project.modify_user, project.modify_date "
        + "FROM project JOIN project_status_lu AS status "
        + "ON project.project_status_id=status.project_status_id "
        + "JOIN project_category_lu AS category "
        + "ON project.project_category_id=category.project_category_id "
        + "JOIN project_type_lu AS type " + "ON category.project_type_id=type.project_type_id "
        + "WHERE project.project_id IN ";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query projects.
     */
    private static final DataType[] QUERY_PROJECTS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE};

    /**
     * Represents the sql statement to query project properties.
     */
    private static final String QUERY_PROJECT_PROPERTIES_SQL = "SELECT "
        + "info.project_id, info_type.name, info.value " + "FROM project_info AS info "
        + "JOIN project_info_type_lu AS info_type "
        + "ON info.project_info_type_id=info_type.project_info_type_id "
        + "WHERE info.project_id IN ";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project properties.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTIES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query project property ids.
     */
    private static final String QUERY_PROJECT_PROPERTY_IDS_SQL = "SELECT "
        + "project_info_type_id FROM project_info WHERE project_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project property ids.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the sql statement to create project.
     */
    private static final String CREATE_PROJECT_SQL = "INSERT INTO project "
        + "(project_id, project_status_id, project_category_id, "
        + "create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to create project property.
     */
    private static final String CREATE_PROJECT_PROPERTY_SQL = "INSERT INTO project_info "
        + "(project_id, project_info_type_id, value, "
        + "create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to create project audit.
     */
    private static final String CREATE_PROJECT_AUDIT_SQL = "INSERT INTO project_audit "
        + "(project_audit_id, project_id, update_reason, "
        + "create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to update project.
     */
    private static final String UPDATE_PROJECT_SQL = "UPDATE project "
        + "SET project_status_id=?, project_category_id=?, modify_user=?, modify_date=CURRENT "
        + "WHERE project_id=?";

    /**
     * Represents the sql statement to update project property.
     */
    private static final String UPDATE_PROJECT_PROPERTY_SQL = "UPDATE project_info "
        + "SET value=?, modify_user=?, modify_date=CURRENT "
        + "WHERE project_id=? AND project_info_type_id=?";

    /**
     * Represents the sql statement to delete project properties.
     */
    private static final String DELETE_PROJECT_PROPERTIES_SQL = "DELETE FROM project_info "
        + "WHERE project_id=? AND project_info_type_id IN ";

    /**
     * <p>
     * The factory instance used to create connection to the database. It is
     * initialized in the constructor using DBConnectionFactory component and
     * never changed after that. It will be used in various persistence methods
     * of this project.
     * </p>
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * Represents the database connection name that will be used by
     * DBConnectionFactory. This variable is initialized in the constructor and
     * never changed after that. It will be used in create/update/retrieve
     * method to create connection. This variable can be null, which mean
     * connection name is not defined in the configuration namespace and default
     * connection will be created.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the IDGenerator for project table. This variable is
     * initialized in the constructor and never change after that. It will be
     * used in createProject() method to generate new Id for project..
     * </p>
     */
    private final IDGenerator projectIdGenerator;

    /**
     * <p>
     * Represents the IDGenerator for project audit table. This variable is
     * initialized in the constructor and never change after that. It will be
     * used in updateProject() method to store update reason.
     * </p>
     */
    private final IDGenerator projectAuditIdGenerator;

    /**
     * <p>
     * Create a new instance of InformixProjectPersistence.
     * </p>
     * <p>
     * The passing namespace parameter will be used to get the namespace to
     * initialize DBConnectionFactory component. ConfigurationException will be
     * thrown if the 'ConnectionFactoryNS' property is missing in the given
     * namespace.
     * </p>
     * @param namespace
     *            The namespace to load connection setting.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     * @throws ConfigurationException
     *             if error occurs while loading configuration settings, or
     *             required configuration parameter is missing.
     * @throws PersistenceException
     *             if cannot initialize the connection to the database.
     */
    public InformixProjectPersistence(String namespace) throws ConfigurationException,
        PersistenceException {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");

        // get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        // get db connection factory namespace
        String factoryNamespace = Helper.getConfigurationParameterValue(cm, namespace,
            CONNECTION_FACTORY_NAMESPACE_PARAMETER, true);

        // try to create a DBConnectionFactoryImpl instance from
        // factoryNamespace
        try {
            factory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (Exception e) {
            throw new ConfigurationException(
                "Unable to create a new instance of DBConnectionFactoryImpl class"
                    + " from namespace [" + factoryNamespace + "].", e);
        }

        // get the connection name
        connectionName = Helper.getConfigurationParameterValue(cm, namespace,
            CONNECTION_NAME_PARAMETER, false);

        // try to get project id sequence name
        String projectIdSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            PROJECT_ID_SEQUENCE_NAME_PARAMETER, false);
        // use default name if project id sequence name is not provided
        if (projectIdSequenceName == null) {
            projectIdSequenceName = PROJECT_ID_SEQUENCE_NAME;
        }

        // try to get project audit id sequence name
        String projectAuditIdSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER, false);
        // use default name if project audit id sequence name is not provided
        if (projectAuditIdSequenceName == null) {
            projectAuditIdSequenceName = PROJECT_AUDIT_ID_SEQUENCE_NAME;
        }

        // try to get the IDGenerators
        try {
            projectIdGenerator = IDGeneratorFactory.getIDGenerator(projectIdSequenceName);
        } catch (IDGenerationException e) {
            throw new PersistenceException("Unable to create IDGenerator for '"
                + projectIdSequenceName + "'.", e);
        }
        try {
            projectAuditIdGenerator = IDGeneratorFactory.getIDGenerator(projectAuditIdSequenceName);
        } catch (IDGenerationException e) {
            throw new PersistenceException("Unable to create IDGenerator for '"
                + projectAuditIdSequenceName + "'.", e);
        }
    }

    /**
     * Create a connection with transaction support.
     * @return the connection created
     * @throws PersistenceException
     *             if error happens when creating the connection
     */
    private Connection createTransactionalConnection() throws PersistenceException {
        try {
            Connection conn = connectionName == null ? factory.createConnection() : factory
                .createConnection(connectionName);

            conn.setAutoCommit(false);
            //conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return conn;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error occurs when getting "
                + (connectionName == null ? "the default connection." : ("the connection '"
                    + connectionName + "'.")), e);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs when setting "
                + (connectionName == null ? "the default connection" : ("the connection '"
                    + connectionName + "'")) + " to support transaction.", e);
        }
    }

    /**
     * <p>
     * Create the project in the database using the given project instance.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its
     * properties are stored in 'project_info' table. For the project and its
     * properties, the operator parameter is used as the creation/modification
     * user and the creation date and modification date will be the current date
     * time when the project is created.
     * </p>
     * @param project
     *            The project instance to be created in the database.
     * @param operator
     *            The creation user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public void createProject(Project project, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the project
        Long newId;
        // createDate will contain the create_date value retrieved from
        // database.
        Date createDate;

        try {
            // create the connection
            conn = createTransactionalConnection();

            // check whether the project id is already in the database
            if (project.getId() > 0) {
                if (Helper.checkEntityExists("project", "project_id", project.getId(), conn)) {
                    throw new PersistenceException("The project with the same id ["
                        + project.getId() + "] already exists.");
                }
            }

            try {
                // generate id for the project
                newId = new Long(projectIdGenerator.getNextID());
            } catch (IDGenerationException e) {
                throw new PersistenceException("Unable to generate id for the project.", e);
            }

            // create the project
            createProject(newId, project, operator, conn);

            // get the creation date.
            createDate = (Date) Helper.doSingleValueQuery(conn, "SELECT create_date "
                + "FROM project WHERE project_id=?", new Object[] {newId}, Helper.DATE_TYPE);

            Helper.commitTransaction(conn);
        } catch (PersistenceException e) {
            Helper.rollBackTransaction(conn);
            throw e;
        } finally {
            Helper.closeConnection(conn);
        }

        // set the newId when no exception occurred
        project.setId(newId.longValue());

        // set the creation/modification user and date when no exception
        // occurred
        project.setCreationUser(operator);
        project.setCreationTimestamp(createDate);
        project.setModificationUser(operator);
        project.setModificationTimestamp(createDate);
    }

    /**
     * Create the project in the database using the given project instance.
     * @param projectId
     *            The new generated project id
     * @param project
     *            The project instance to be created in the database.
     * @param operator
     *            The creation user of this project.
     * @param conn
     *            The database connection
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private void createProject(Long projectId, Project project, String operator, Connection conn)
        throws PersistenceException {

        // insert the project into database
        Object[] queryArgs = new Object[] {projectId, new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator, operator};
        Helper.doDMLQuery(conn, CREATE_PROJECT_SQL, queryArgs);

        // get the property id - property value map from the project.
        Map idValueMap = makePropertyIdPropertyValueMap(project.getAllProperties(), conn);

        // create the project properties
        createProjectProperties(projectId, idValueMap, operator, conn);
    }

    /**
     * Create the project properties in the database.
     * @param projectId
     *            The new generated project id
     * @param idValueMap
     *            The property id - property value map
     * @param operator
     *            The creation user of this project
     * @param conn
     *            The database connection
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private void createProjectProperties(Long projectId, Map idValueMap, String operator,
        Connection conn) throws PersistenceException {

        PreparedStatement preparedStatement = null;
        try {
            // prepare the statement.
            preparedStatement = conn.prepareStatement(CREATE_PROJECT_PROPERTY_SQL);

            // enumerator each property in the idValueMap
            for (Iterator it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                // insert the project property into database
                Object[] queryArgs = new Object[] {projectId, entry.getKey(), entry.getValue(),
                    operator, operator};
                Helper.doDMLQuery(preparedStatement, queryArgs);
            }

        } catch (SQLException e) {
            throw new PersistenceException("Unable to create parepared statement ["
                + CREATE_PROJECT_PROPERTY_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }

    /**
     * <p>
     * Update the given project instance into the database.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its
     * properties are stored in 'project_info' table. All related items in these
     * tables will be updated. If items are removed from the project, they will
     * be deleted from the persistence. Likewise, if new items are added, they
     * will be created in the persistence. For the project and its properties,
     * the operator parameter is used as the modification user and the
     * modification date will be the current date time when the project is
     * updated. An update reason is provided with this method. Update reason
     * will be stored in the persistence for future references.
     * </p>
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator
     *            The modification user of this project.
     * @throws IllegalArgumentException
     *             if any input is null. Or operator is empty string. Or project
     *             id is zero or negative.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public void updateProject(Project project, String reason, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertLongPositive(project.getId(), "project id");
        Helper.assertObjectNotNull(reason, "reason");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        // modifyDate will contain the modify_date retrieved from database.
        Date modifyDate;

        Connection conn = null;

        try {
            // create the connection
            conn = createTransactionalConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", project.getId(), conn)) {
                throw new PersistenceException("The project id [" + project.getId()
                    + "] does not exist in the database.");
            }

            // update the project
            updateProject(project, reason, operator, conn);

            // get the modification date.
            modifyDate = (Date) Helper.doSingleValueQuery(conn, "SELECT modify_date "
                + "FROM project WHERE project_id=?", new Object[] {new Long(project.getId())},
                Helper.DATE_TYPE);

            Helper.commitTransaction(conn);
        } catch (PersistenceException e) {
            Helper.rollBackTransaction(conn);
            throw e;
        } finally {
            Helper.closeConnection(conn);
        }

        // set the modification user and date when no exception
        // occurred.
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
    }

    /**
     * Update the given project instance into the database.
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator
     *            The modification user of this project.
     * @param conn
     *            the database connection
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private void updateProject(Project project, String reason, String operator, Connection conn)
        throws PersistenceException {
        Long projectId = new Long(project.getId());

        // update the project type and project category
        Object[] queryArgs = new Object[] {new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator, projectId};
        Helper.doDMLQuery(conn, UPDATE_PROJECT_SQL, queryArgs);

        // get the property id - property value map from the new project object.
        Map idValueMap = makePropertyIdPropertyValueMap(project.getAllProperties(), conn);

        // update the project properties
        updateProjectProperties(projectId, idValueMap, operator, conn);

        // create project audit record into project_audit table
        createProjectAudit(projectId, reason, operator, conn);
    }

    /**
     * Update the project properties into the database.
     * @param projectId
     *            the id of the project
     * @param idValueMap
     *            the property id - property value map
     * @param operator
     *            the modification user of this project
     * @param conn
     *            the database connection
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private void updateProjectProperties(Long projectId, Map idValueMap, String operator,
        Connection conn) throws PersistenceException {
        // get old property ids from database
        Set propertyIdSet = getProjectPropertyIds(projectId, conn);

        // create a property id-property value map that contains the properties
        // to insert
        Map createIdValueMap = new HashMap();

        PreparedStatement preparedStatement = null;
        try {
            // prepare the statement.
            preparedStatement = conn.prepareStatement(UPDATE_PROJECT_PROPERTY_SQL);

            // enumerator each property id in the project object
            for (Iterator it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                Long propertyId = (Long) entry.getKey();

                // check if the property in the project object already exists in
                // the
                // database
                if (propertyIdSet.contains(propertyId)) {
                    propertyIdSet.remove(propertyId);

                    // update the project property
                    Object[] queryArgs = new Object[] {entry.getValue(), operator, projectId,
                        propertyId};
                    Helper.doDMLQuery(preparedStatement, queryArgs);
                } else {
                    // add the entry to the createIdValueMap
                    createIdValueMap.put(propertyId, entry.getValue());
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Unable to create parepared statement ["
                + UPDATE_PROJECT_PROPERTY_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }

        // create the new properties
        createProjectProperties(projectId, createIdValueMap, operator, conn);

        // delete the remaining property ids that are not in the project object
        // any longer
        deleteProjectProperties(projectId, propertyIdSet, conn);
    }

    /**
     * Delete the project properties from the database.
     * @param projectId
     *            the id of the project
     * @param propertyIdSet
     *            the Set that contains the property ids to delete
     * @param conn
     *            the database connection
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private void deleteProjectProperties(Long projectId, Set propertyIdSet, Connection conn)
        throws PersistenceException {

        // check if the property id set is empty
        // do nothing if property id set is empty
        if (!propertyIdSet.isEmpty()) {

            // build the id list string
            StringBuffer idListBuffer = new StringBuffer();
            idListBuffer.append('(');
            int idx = 0;
            for (Iterator it = propertyIdSet.iterator(); it.hasNext();) {
                if (idx++ != 0) {
                    idListBuffer.append(',');
                }
                idListBuffer.append(it.next());
            }
            idListBuffer.append(')');

            // delete the properties whose id is in the set
            Helper.doDMLQuery(conn, DELETE_PROJECT_PROPERTIES_SQL + idListBuffer.toString(),
                new Object[] {projectId});
        }
    }

    /**
     * Create a project audit record into the database.
     * @param projectId
     *            The id of the project
     * @param reason
     *            The update reason
     * @param operator
     *            the modification user of this project
     * @param conn
     *            the database connection
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private void createProjectAudit(Long projectId, String reason, String operator, Connection conn)
        throws PersistenceException {

        Long auditId;
        try {
            // generate a new audit id
            auditId = new Long(projectAuditIdGenerator.getNextID());
        } catch (IDGenerationException e) {
            throw new PersistenceException("Unable to generate id for project.", e);
        }

        // insert the update reason information to project_audit table
        Object[] queryArgs = new Object[] {auditId, projectId, reason, operator, operator};
        Helper.doDMLQuery(conn, CREATE_PROJECT_AUDIT_SQL, queryArgs);
    }

    /**
     * Gets all the property ids associated to this project.
     * @param projectId
     *            The id of this project
     * @param conn
     *            The database connection
     * @return A set that contains the property ids
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private Set getProjectPropertyIds(Long projectId, Connection conn) throws PersistenceException {
        Set idSet = new HashSet();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTY_IDS_SQL,
            new Object[] {projectId}, QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the set
            idSet.add(row[0]);
        }
        return idSet;
    }

    /**
     * Make a property name - property id map from ProjectPropertyType[].
     * @param propertyTypes
     *            the ProjectPropertyType array
     * @return a property name - property id map
     * @throws PersistenceException
     *             if duplicate property type names are found
     */
    private Map makePropertyNamePropertyIdMap(ProjectPropertyType[] propertyTypes)
        throws PersistenceException {
        Map map = new HashMap();

        // enumerate each property type
        for (int i = 0; i < propertyTypes.length; ++i) {

            // check if the property name already exists
            if (map.containsKey(propertyTypes[i].getName())) {
                throw new PersistenceException("Duplicate project property type names ["
                    + propertyTypes[i].getName() + "] found in project_info_type_lu table.");
            }

            // put the name-id pair to the map
            map.put(propertyTypes[i].getName(), new Long(propertyTypes[i].getId()));
        }
        return map;
    }

    /**
     * Make a property id-property value(String) map from property name-property
     * value map.
     * @param nameValueMap
     *            the property name-property value map
     * @param conn
     *            the database connection
     * @return a property id-property value map
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private Map makePropertyIdPropertyValueMap(Map nameValueMap, Connection conn)
        throws PersistenceException {
        Map idValueMap = new HashMap();

        // get the property name-property id map
        Map nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));

        // enumerate each property
        for (Iterator it = nameValueMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();

            // find the property id from property name
            Object propertyId = nameIdMap.get(entry.getKey());
            // check if the property id can be found
            if (propertyId == null) {
                throw new PersistenceException("Unable to find ProjectPropertyType name ["
                    + entry.getKey() + "] in project_info_type_lu table.");
            }

            // put the property id-property value(String) pair into the map
            idValueMap.put(propertyId, entry.getValue().toString());
        }
        return idValueMap;
    }

    /**
     * <p>
     * Retrieves the project instance from the persistence given its id. The
     * project instance is retrieved with its related items, such as properties.
     * </p>
     * @return The project instance, or null if not found.
     * @param id
     *            The id of the project to be retrieved.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException {
        Helper.assertLongPositive(id, "id");

        Project[] projects = getProjects(new long[] {id});
        return projects.length == 0 ? null : projects[0];
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their
     * ids. The project instances are retrieved with their properties.
     * </p>
     * @param ids
     *            The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws IllegalArgumentException
     *             if ids is null or empty, or ids contains an id that is less
     *             than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        Helper.assertObjectNotNull(ids, "ids");

        // check if ids is empty
        if (ids.length == 0) {
            throw new IllegalArgumentException("Array 'ids' should not be empty.");
        }

        // check if ids contains an id that is <= 0
        for (int i = 0; i < ids.length; ++i) {
            if (ids[i] <= 0) {
                throw new IllegalArgumentException("Array 'ids' contains an id that is <= 0.");
            }
        }

        Connection conn = null;

        try {
            // create the connection
            conn = createTransactionalConnection();

            // get the project objects
            Project[] projects = getProjects(ids, conn);

            return projects;
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their
     * ids. The project instances are retrieved with their properties.
     * </p>
     * @param ids
     *            The ids of the projects to be retrieved.
     * @param conn
     *            the database connection
     * @return An array of project instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
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
            ProjectStatus status = new ProjectStatus(((Long) row[1]).longValue(), (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[5]).longValue(), (String) row[6]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory(((Long) row[3]).longValue(),
                (String) row[4], type);

            // create a new instance of ProjectType class
            projects[i] = new Project(((Long) row[0]).longValue(), category, status);

            // assign the audit information
            projects[i].setCreationUser((String) row[7]);
            projects[i].setCreationTimestamp((Date) row[8]);
            projects[i].setModificationUser((String) row[9]);
            projects[i].setModificationTimestamp((Date) row[10]);
        }

        // get the Id-Project map
        Map projectMap = makeIdProjectMap(projects);

        // find project properties in the table.
        rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTIES_SQL + idList, new Object[] {},
            QUERY_PROJECT_PROPERTIES_COLUMN_TYPES);

        // enumerate each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // get the corresponding Project object
            Project project = (Project) projectMap.get(row[0]);

            // set the property to project
            project.setProperty((String) row[1], row[2]);
        }
        return projects;
    }

    /**
     * Make an Id-Project map from Project[].
     * @param projects
     *            the Id-Project array
     * @return an Id-Project map
     */
    private Map makeIdProjectMap(Project[] projects) {
        Map map = new HashMap();

        for (int i = 0; i < projects.length; ++i) {
            map.put(new Long(projects[i].getId()), projects[i]);
        }
        return map;
    }

    /**
     * Gets an array of all project types in the persistence. The project types
     * are stored in 'project_type_lu' table.
     * @return An array of all project types in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection
            conn = createTransactionalConnection();

            // get all the project types
            ProjectType[] projectTypes = getAllProjectTypes(conn);

            return projectTypes;
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * Gets an array of all project types in the persistence. The project types
     * are stored in 'project_type_lu' table.
     * @param conn
     *            the database connection
     * @return An array of all project types in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
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
            projectTypes[i] = new ProjectType(((Long) row[0]).longValue(), (String) row[1],
                (String) row[2]);
        }

        return projectTypes;
    }

    /**
     * Gets an array of all project categories in the persistence. The project
     * categories are stored in 'project_category_lu' table.
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection
            conn = createTransactionalConnection();

            // get all categories
            ProjectCategory[] projectCategories = getAllProjectCategories(conn);

            return projectCategories;
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * Gets an array of all project categories in the persistence. The project
     * categories are stored in 'project_category_lu' table.
     * @param conn
     *            the database connection
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
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
            ProjectType type = new ProjectType(((Long) row[3]).longValue(), (String) row[4],
                (String) row[5]);

            // create a new instance of ProjectCategory class
            projectCategories[i] = new ProjectCategory(((Long) row[0]).longValue(),
                (String) row[1], (String) row[2], type);
        }

        return projectCategories;
    }

    /**
     * <p>
     * Gets an array of all project statuses in the persistence. The project
     * statuses are stored in 'project_status_lu' table.
     * </p>
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection
            conn = createTransactionalConnection();

            // get all the project statuses
            ProjectStatus[] projectStatuses = getAllProjectStatuses(conn);

            return projectStatuses;
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * Gets an array of all project statuses in the persistence. The project
     * statuses are stored in 'project_status_lu' table.
     * @param conn
     *            the database connection
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
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
            projectStatuses[i] = new ProjectStatus(((Long) row[0]).longValue(), (String) row[1],
                (String) row[2]);
        }

        return projectStatuses;
    }

    /**
     * <p>
     * Gets an array of all project property type in the persistence. The
     * project property types are stored in 'project_info_type_lu' table.
     * </p>
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection
            conn = createTransactionalConnection();

            ProjectPropertyType[] propertyTypes = getAllProjectPropertyTypes(conn);

            return propertyTypes;
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * Gets an array of all project property type in the persistence. The
     * project property types are stored in 'project_info_type_lu' table.
     * @param conn
     *            the database connection
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private ProjectPropertyType[] getAllProjectPropertyTypes(Connection conn)
        throws PersistenceException {
        // find all project property types in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL,
            new Object[] {}, QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES);

        // create the ProjectPropertyType array.
        ProjectPropertyType[] propertyTypes = new ProjectPropertyType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectPropertyType class
            propertyTypes[i] = new ProjectPropertyType(((Long) row[0]).longValue(),
                (String) row[1], (String) row[2]);
        }

        return propertyTypes;
    }
}
