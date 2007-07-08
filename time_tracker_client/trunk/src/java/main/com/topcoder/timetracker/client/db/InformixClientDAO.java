/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.db;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.client.BatchOperationException;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientAuditException;
import com.topcoder.timetracker.client.ClientDAO;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.Depth;
import com.topcoder.timetracker.client.Helper;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;


/**
 * <p>
 * This class is the Informix database implementation of the ClientDAO. It provides general retrieve/update/remove/add
 * functionality to access the database. And it provides SearchClient method to search client with filter and depth.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class InformixClientDAO implements ClientDAO {

    /**
     * <p>
     * Represents the connection factory key.
     * </p>
     */
    private static final String CONNECTION_FACTORY_KEY = "ConnectionFactoryNamespace";

    /**
     * <p>
     * Represents the connection name key.
     * </p>
     */
    private static final String CONNECTION_NAME_KEY = "ConnectionName";

    /**
     * <p>
     * Represents the search bundle name key.
     * </p>
     */
    private static final String SEARCH_BUNDLE_NAME_KEY = "SearchBundleName";

    /**
     * <p>
     * Represents the search bundle manager key.
     * </p>
     */
    private static final String SEARCH_BUNDLE_MANAGER_KEY = "SearchBundleNamespace";

    /**
     * <p>
     * Represent the audit manager key.
     * </p>
     */
    private static final String AUDIT_MANAGER_KEY = "AuditManager";

    /**
     * <p>
     * Represents the SQL command to insert data into client.
     * </p>
     */
    private static final String SQL_INSERT_CLIENT = "insert into client (client_id, name, company_id,"
        + " creation_date, creation_user, modification_date, modification_user, payment_term_id, status,"
        + " salesTax, start_date, end_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL command to select all data from the client table.
     * </p>
     */
    private static final String SQL_SELECT_ALL_CLIENT = "select client_id, name, company_id, creation_date,"
        + " creation_user, modification_date, modification_user, payment_term_id, status, salesTax,"
        + "start_date, end_date from client";

    /**
     * <p>
     * Represents the SQL update command to update the client table.
     * </p>
     */
    private static final String SQL_UPDATE_CLIENT = "update client set name = ?, company_id = ?,"
        + " creation_date = ?, creation_user = ?, modification_date = ?, modification_user = ?,"
        + " payment_term_id = ?, status = ?, salesTax = ?, start_date = ?, end_date = ?" + " where client_id = ?";

    /**
     * <p>
     * Represents the SQL delete command to delete record from client table.
     * </p>
     */
    private static final String SQL_DELETE_CLIENT = "delete from client where client_id = ?";

    /**
     * <p>
     * Represents the SQL command to select projects with specified client.
     * </p>
     */
    private static final String SQL_SELECT_CLIENT_PROJECT = "select project_id from client_project"
        + " where client_id = ?";

    private static final String SQL_SELECT_CLIENT_PROJECTS_ID_NAME =
        "SELECT DISTINCT project.project_id AS project_id," +
        "       name" +
        "  FROM project," +
        "       client_project" +
        " WHERE project.project_id = client_project.project_id" +
        "   AND client_project.client_id = ?";

    /**
     * <p>
     * Represents the SQL command to insert data to client_project table.
     * </p>
     */
    private static final String SQL_INSERT_CLIENT_PROJECT = "insert into client_project (client_id, "
        + " project_id, creation_date, creation_user, modification_date, modification_user) values "
        + "(?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL command to select the record with specified client_id and project_id used for audit.
     * </p>
     */
    private static final String SQL_SELECT_CLIENT_PROJECT_ENTRY = "select client_id, project_id,"
        + " creation_user, creation_date, modification_user, modification_date from client_project"
        + " where client_id = ? AND project_id = ?";

    /**
     * <p>
     * Represents the SQL command to delete the record with specified client_id and project_id.
     * </p>
     */
    private static final String SQL_DELETE_CLIENT_PROJECT_ENTRY = "delete from client_project"
        + " where client_id = ? AND project_id = ?";

    /**
     * <p>
     * Represents the key for object factory namespace.
     * </p>
     */
    private static final String OBJECT_FACTORY_KEY = "ObjectFactoryNamespace";

    /**
     * <p>
     * Represents the connection factory used to generate the Connections. This variable is set in constructor,&nbsp;
     * is immutable and never be null. It is referenced by createConnection method.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the connection name used by the connectionFactory to generate the Connections. This variable is set
     * in constructor is immutable and possible null, possible empty(trim'd). It is referenced by createConnection
     * method.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the SearchBundle will be used to perform search with filter. This variable is set in constructor, is
     * immutable and never be null. It is referenced by searchClient method.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * Represents the AuditManage will be used. This variable is set in the construtor; is immutable and never be null.
     * It will be referenced by all the methods which can be audited.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * Map from the alias name to the real Client table column name.
     * </p>
     */
    private final Map clientColumnNames;

    /**
     * <p>
     * Map from the alias name to the real client_project table column name.
     * </p>
     */
    private final Map clientProjectColumnNames;

    /**
     * <p>
     * Constructs InformixClientDAO with default namespace.
     * </p>
     *
     * @throws ConfigurationException if it is thrown while calling the constructor
     */
    public InformixClientDAO() throws ConfigurationException {
        this(InformixClientDAO.class.getName());
    }

    /**
     * <p>
     * Constructs InformixClientDAO with the given arguments.
     * </p>
     *
     * @param namespace non null, non empty(trim'd) namespace
     *
     * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
     * @throws ConfigurationException if any exception prevents creating the connection factory successfully
     */
    public InformixClientDAO(String namespace) throws ConfigurationException {
        Helper.checkString(namespace, "namespace");

        // get the connection factory namespace.
        String connectionFactoryNamespace = Helper.getConfigString(namespace, CONNECTION_FACTORY_KEY, true);

        try {
            this.connectionFactory = new DBConnectionFactoryImpl(connectionFactoryNamespace);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException ce) {
            throw new ConfigurationException("Error creating the db connection factory.", ce);
        } catch (UnknownConnectionException uce) {
            throw new ConfigurationException("Un-known connection.", uce);
        }

        // get the connection name key.
        this.connectionName = Helper.getConfigString(namespace, CONNECTION_NAME_KEY, false);

        // get the search bundle name.
        String searchBundleName = Helper.getConfigString(namespace, SEARCH_BUNDLE_NAME_KEY, true);

        // get the search bundle manager.
        String searchBundleNamespace = Helper.getConfigString(namespace, SEARCH_BUNDLE_MANAGER_KEY, true);

        try {
            SearchBundleManager manager = new SearchBundleManager(searchBundleNamespace);

            this.searchBundle = manager.getSearchBundle(searchBundleName);
        } catch (SearchBuilderConfigurationException cce) {
            throw new ConfigurationException("Error when creating the such bundle.", cce);
        }

        // get the property for the alias
        // parse the property and fill the alias map
        Property property = getConfigPropertyObject(searchBundleNamespace,
                "searchBundles" + "." + searchBundleName + ".alias");

        Enumeration iterator = property.propertyNames();

        // list to save the alias name.
        List aliasNameList = new ArrayList();

        // list to save the column name.
        List columnNameList = new ArrayList();

        while (iterator.hasMoreElements()) {
            String aliasName = (String) iterator.nextElement();

            // add the alias name.
            aliasNameList.add(aliasName);

            // add the real column name.
            columnNameList.add(Helper.getConfigString(searchBundleNamespace,
                    "searchBundles" + "." + searchBundleName + ".alias" + "." + aliasName, true));
        }

        // initialize the client column names and client project column names hash table
        this.clientColumnNames = new HashMap();
        this.clientProjectColumnNames = new HashMap();

        // fill the two maps
        addKeyValuePair(aliasNameList, columnNameList);

        // get the object factory to init the audit manager
        String objectFactoryNamespace = Helper.getConfigString(namespace, OBJECT_FACTORY_KEY, true);

        // use object factory to create the instance.
        ObjectFactory of = Helper.getObjectFactory(objectFactoryNamespace);

        // create ClientDAO
        try {
            // ClientDAO is a required
            this.auditManager = (AuditManager) Helper.createObjectViaObjectFactory(of, AUDIT_MANAGER_KEY);
        } catch (ClassCastException cce) {
            throw new ConfigurationException("Error when creating dao.", cce);
        }
    }

    /**
     * <p>
     * Add the given Client into the database.
     * </p>
     *
     * @param client non null client to be added
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void addClient(Client client, boolean audit)
        throws ClientPersistenceException, ClientAuditException {
        // check argument.
        Helper.checkNull(client, "client");

        addClients(new Client[]{client}, audit);
    }

    /**
     * <p>
     * Add the given Clients into the database.
     * </p>
     *
     * @param clients non null, possible empty array containing non null contact to be added
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the array is null or containing null client
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void addClients(Client[] clients, boolean audit)
        throws ClientPersistenceException, ClientAuditException {
        Helper.checkArray(clients, "clients");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = createConnection();

            stmt = conn.prepareStatement(SQL_INSERT_CLIENT);

            Date currentDate = new Date();

            for (int i = 0; i < clients.length; i++) {
                // set the creation date and modification date.
                clients[i].setCreationDate(currentDate);
                clients[i].setModificationDate(currentDate);

                stmt.setLong(1, clients[i].getId());
                stmt.setString(2, clients[i].getName());
                stmt.setLong(3, clients[i].getCompanyId());
                stmt.setDate(4, new java.sql.Date(clients[i].getCreationDate().getTime()));
                stmt.setString(5, clients[i].getCreationUser());
                stmt.setDate(6, new java.sql.Date(clients[i].getModificationDate().getTime()));
                stmt.setString(7, clients[i].getModificationUser());
                stmt.setLong(8, clients[i].getPaymentTerm().getId());
                stmt.setInt(9, clients[i].isActive() ? 1 : 0);
                stmt.setDouble(10, clients[i].getSalesTax());
                stmt.setDate(11, new java.sql.Date(clients[i].getStartDate().getTime()));
                stmt.setDate(12, new java.sql.Date(clients[i].getEndDate().getTime()));

                stmt.addBatch();

                // do audit
                if (audit) {
                    auditClient(null, clients[i]);
                }
            }

            stmt.executeBatch();
        } catch (BatchUpdateException bue) {
            buildBatchOperationException(bue, clients.length);
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error updating the database.", sqle);
        } finally {
            releaseStatement(stmt, null);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieve Client by ID.
     * </p>
     *
     * @param id the id of the client
     *
     * @return the client with given id, null if not found
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public Client retrieveClient(long id) throws ClientPersistenceException {
        Helper.checkPositive(id, "id");

        Client[] clients = retrieveClients(new long[]{id});

        return (clients.length != 0) ? clients[0] : null;
    }

    /**
     * <p>
     * Retrieve the Clients with given IDs.
     * </p>
     *
     * @param ids non null, possible empty array containing id of client to be removed
     *
     * @return non null, possible empty array containing possible null clients
     *
     * @throws IllegalArgumentException if the array is null or any id is not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public Client[] retrieveClients(long[] ids) throws ClientPersistenceException {
        Helper.checkPositiveArray(ids, "ids");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            // create db connection
            conn = createConnection();

            // prepared the statement
            // the command will be of the format Select * from * where client_ids in (?, ?, ...?)
            stmt = conn.prepareStatement(buildBatchSelect(ids));

            // set the id
            for (int i = 0; i < ids.length; i++) {
                stmt.setLong(i + 1, ids[i]);
            }

            result = stmt.executeQuery();

            List list = new ArrayList();

            while (result.next()) {
                // get the result.
                list.add(getClientFromResultSet(result));
            }

            // convert to array and return.
            return (Client[]) list.toArray(new Client[list.size()]);
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error retrieve the information.", sqle);
        } finally {
            releaseStatement(stmt, result);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Remove Client with given ID.
     * </p>
     *
     * @param id the id of the client
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the id is not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void removeClient(long id, boolean audit) throws ClientPersistenceException, ClientAuditException {
        Helper.checkPositive(id, "id");

        removeClients(new long[]{id}, audit);
    }

    /**
     * <p>
     * Remove the Clients with given ids.
     * </p>
     *
     * @param ids non null, possible empty array containing id of client to be removed
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the array is null or any id is not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void removeClients(long[] ids, boolean audit)
        throws ClientPersistenceException, ClientAuditException {
        Helper.checkPositiveArray(ids, "ids");

        Connection conn = null;
        PreparedStatement stmt = null;

        Client[] oldClients = null;

        if (audit) {
            // if audit is on, get the old records first.
            oldClients = retrieveClients(ids);
        }

        try {
            // get the DB connection.
            conn = createConnection();

            // prepare the statement.
            stmt = conn.prepareStatement(SQL_DELETE_CLIENT);

            for (int i = 0; i < ids.length; i++) {
                stmt.setLong(1, ids[i]);
                stmt.addBatch();

                if (audit) {
                    auditClient(oldClients[i], null);
                }
            }

            stmt.executeBatch();
        } catch (BatchUpdateException bue) {
            buildBatchOperationException(bue, ids.length);
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error removing the client.", sqle);
        } finally {
            releaseStatement(stmt, null);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Update the Client.
     * </p>
     *
     * @param client non null client to be updated
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void updateClient(Client client, boolean audit)
        throws ClientPersistenceException, ClientAuditException {
        Helper.checkNull(client, "client");

        updateClients(new Client[]{client}, audit);
    }

    /**
     * <p>
     * Update the given Clients.
     * </p>
     *
     * @param clients non null, possible empty array containing non null contact
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the array is null or containing null client
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void updateClients(Client[] clients, boolean audit)
        throws ClientPersistenceException, ClientAuditException {
        Helper.checkArray(clients, "clients");

        Client[] olds = new Client[clients.length];

        for (int i = 0; i < clients.length; i++) {
            olds[i] = retrieveClient(clients[i].getId());
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // get the connection
            conn = createConnection();

            // prepare the statement.
            stmt = conn.prepareStatement(SQL_UPDATE_CLIENT);

            for (int i = 0; i < clients.length; i++) {
                if (olds[i] == null) {
                    // it's not a update. only insert it into the database.
                    addClient(clients[i], audit);
                    if (audit) {
                        auditClient(null, clients[i]);
                    }
                } else {
                    stmt.setString(1, clients[i].getName());
                    stmt.setLong(2, clients[i].getCompanyId());
                    stmt.setDate(3, new java.sql.Date(clients[i].getCreationDate().getTime()));
                    stmt.setString(4, clients[i].getCreationUser());
                    stmt.setDate(5, new java.sql.Date(clients[i].getModificationDate().getTime()));
                    stmt.setString(6, clients[i].getModificationUser());
                    stmt.setLong(7, clients[i].getPaymentTerm().getId());
                    stmt.setInt(8, clients[i].isActive() ? 1 : 0);
                    stmt.setDouble(9, clients[i].getSalesTax());
                    stmt.setDate(10, new java.sql.Date(clients[i].getStartDate().getTime()));
                    stmt.setDate(11, new java.sql.Date(clients[i].getEndDate().getTime()));
                    stmt.setLong(12, clients[i].getId());

                    if (audit) {
                        auditClient(olds[i], clients[i]);
                    }

                    stmt.addBatch();
                }
            }

            stmt.executeBatch();
        } catch (BatchUpdateException bue) {
            buildBatchOperationException(bue, clients.length);
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error updating the data.", sqle);
        } finally {
            releaseStatement(stmt, null);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieve all the Clients.
     * </p>
     *
     * @return non null, possible empty array containing all the clients
     *
     * @throws ClientPersistenceException if any exception occurs
     */
    public Client[] getAllClients() throws ClientPersistenceException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            // create the DB connection.
            conn = createConnection();

            // prepare the statement.
            stmt = conn.prepareStatement(SQL_SELECT_ALL_CLIENT);

            result = stmt.executeQuery();

            List list = new ArrayList();

            while (result.next()) {
                list.add(getClientFromResultSet(result));
            }

            return (Client[]) list.toArray(new Client[list.size()]);
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error when retrieve date from client.", sqle);
        } finally {
            releaseStatement(stmt, result);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Search for the clients with given filter and depth.
     * </p>
     *
     * @param filter non null filter
     * @param depth non null depth
     *
     * @return the non null result set of this search
     *
     * @throws ClientPersistenceException if any exception occurs
     * @throws IllegalArgumentException if the filter/depth is null
     */
    public CustomResultSet searchClient(Filter filter, Depth depth)
        throws ClientPersistenceException {
        Helper.checkNull(filter, "filter");
        Helper.checkNull(depth, "depth");

        try {
            // in order to search in the database, we have to translate the alias to the real name first
            return (CustomResultSet) searchBundle.search(filter, translateToColumnName(depth.getFields()));
        } catch (SearchBuilderException sbe) {
            throw new ClientPersistenceException("Filter is invalid.", sbe);
        }
    }

    /**
     * <p>
     * Add project to client.
     * </p>
     *
     * @param client the client
     * @param project the non null project to be added
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the client or project is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void addProjectToClient(Client client, Project project, boolean audit)
        throws ClientPersistenceException, ClientAuditException {
        Helper.checkNull(client, "client");
        Helper.checkNull(project, "project");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // create the connection
            conn = createConnection();

            // prepare the statement.
            stmt = conn.prepareStatement(SQL_INSERT_CLIENT_PROJECT);

            Date currentDate = new Date();

            stmt.setLong(1, client.getId());
            stmt.setLong(2, project.getId());
            stmt.setDate(3, new java.sql.Date(currentDate.getTime()));
            stmt.setString(4, client.getCreationUser());
            stmt.setDate(5, new java.sql.Date(currentDate.getTime()));
            stmt.setString(6, client.getModificationUser());

            stmt.executeUpdate();

            if (audit) {
                // create the audit details
                // the notification table has 13 columns.
                AuditDetail[] details = new AuditDetail[6];

                details[0] = buildAuditDetail("client_id", null, (new Long(client.getId())).toString());
                details[1] = buildAuditDetail("project_id", null, (new Long(project.getId())).toString());
                details[2] = buildAuditDetail("creation_user", null, client.getCreationUser());
                details[3] = buildAuditDetail("creation_date", null, currentDate.toString());
                details[4] = buildAuditDetail("modification_user", null, client.getModificationUser());
                details[5] = buildAuditDetail("modification_date", null, currentDate.toString());

                AuditHeader header = buildAuditHeader(client.getId(), "client_project", client.getCompanyId(),
                        AuditType.INSERT, client.getModificationUser());

                header.setDetails(details);

                try {
                    auditManager.createAuditRecord(header);
                } catch (AuditManagerException ame) {
                    throw new ClientAuditException("Error auditing.", ame);
                }
            }
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error inserting data into client_project table.", sqle);
        } finally {
            releaseStatement(stmt, null);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Remove project from client.
     * </p>
     *
     * @param client the client
     * @param projectId the id of the project
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the id is not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void removeProjectFromClient(Client client, long projectId, boolean audit)
        throws ClientPersistenceException, ClientAuditException {
        Helper.checkNull(client, "client");
        Helper.checkPositive(projectId, "projectId");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            conn = createConnection();

            if (audit) {
                stmt = conn.prepareStatement(SQL_SELECT_CLIENT_PROJECT_ENTRY);

                stmt.setLong(1, client.getId());
                stmt.setLong(2, projectId);

                result = stmt.executeQuery();

                while (result.next()) {
                    // create the audit details
                    // the notification table has 13 columns.
                    AuditDetail[] details = new AuditDetail[6];

                    details[0] = buildAuditDetail("client_id", (new Long(client.getId())).toString(), null);
                    details[1] = buildAuditDetail("project_id", (new Long(projectId)).toString(), null);
                    details[2] = buildAuditDetail("creation_user", result.getString("creation_user").toString(), null);
                    details[3] = buildAuditDetail("creation_date", result.getDate("creation_date").toString(), null);
                    details[4] = buildAuditDetail("modification_user",
                            result.getString("modification_user").toString(), null);
                    details[5] = buildAuditDetail("modification_date", result.getDate("modification_date").toString(),
                            null);

                    AuditHeader header = buildAuditHeader(client.getId(), "client_project", client.getCompanyId(),
                            AuditType.DELETE, client.getModificationUser());

                    header.setDetails(details);

                    try {
                        auditManager.createAuditRecord(header);
                    } catch (AuditManagerException ame) {
                        throw new ClientAuditException("Error auditing.", ame);
                    }
                }

                releaseStatement(stmt, result);
            }

            stmt = conn.prepareStatement(SQL_DELETE_CLIENT_PROJECT_ENTRY);

            stmt.setLong(1, client.getId());
            stmt.setLong(2, projectId);

            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Some error occur when deleting the entry.", sqle);
        } finally {
            releaseStatement(stmt, result);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Get all projects of the client.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return non null, possible empty array containing all the project ids of given client
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public long[] getAllProjectIDsOfClient(long clientId)
        throws ClientPersistenceException {
        Helper.checkPositive(clientId, "clientId");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            // get the connection
            conn = createConnection();

            // prepare the statement
            stmt = conn.prepareStatement(SQL_SELECT_CLIENT_PROJECT);
            stmt.setLong(1, clientId);

            result = stmt.executeQuery();

            ArrayList list = new ArrayList();

            while (result.next()) {
                list.add(new Long(result.getLong("project_id")));
            }

            long[] projects = new long[list.size()];

            for (int i = 0; i < projects.length; i++) {
                projects[i] = ((Long) list.get(i)).longValue();
            }

            return projects;
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error get project from client_project.", sqle);
        } finally {
            releaseStatement(stmt, result);
            releaseConnection(conn);
        }
    }

    /**
     * Gets all projects for the client specified by ID. Only the IDs/Names of the resulting
     * projects will be retrieved.
     *
     * @return the non null, possibly empty array containing all projects of the client.
     * @param clientId
     *            the ID of the client.
     * @throws IllegalArgumentException
     *             if client ID not positive.
     * @throws ClientPersistenceException
     *             if any exception occurs.
     */
    public Project[] getProjectIDsNamesForClient(long clientId) throws ClientPersistenceException {
        Helper.checkPositive(clientId, "clientId");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            // get the connection
            conn = createConnection();

            // prepare the statement
            stmt = conn.prepareStatement(SQL_SELECT_CLIENT_PROJECTS_ID_NAME);
            stmt.setLong(1, clientId);

            result = stmt.executeQuery();

            ArrayList list = new ArrayList();

            while (result.next()) {
                Project project = new Project();

                project.setId(result.getLong("project_id"));
                project.setName(result.getString("name"));
                project.setChanged(false);
                list.add(project);
            }

            return (Project[]) list.toArray(new Project[list.size()]);
        } catch (SQLException sqle) {
            throw new ClientPersistenceException("Error get project from client_project.", sqle);
        } finally {
            releaseStatement(stmt, result);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Create a Connection.
     * </p>
     *
     * @return non null created Connection
     *
     * @throws ClientPersistenceException if any exception prevents creating the Connection successfully
     */
    private Connection createConnection() throws ClientPersistenceException {
        try {
            if (connectionName != null) {
                return connectionFactory.createConnection(connectionName);
            }

            return connectionFactory.createConnection();
        } catch (DBConnectionException dbce) {
            throw new ClientPersistenceException("Error getting the connection.", dbce);
        }
    }

    /**
     * Close the connection.
     *
     * @param conn the connection to be closed
     */
    private void releaseConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * <p>
     * Release the statement and result set.
     * </p>
     *
     * @param stmt the statement to release
     * @param result the result to release
     */
    private void releaseStatement(Statement stmt, ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            // ignore it
        }
    }

    /**
     * Add the alias name list and column name list to the corresponding maps. Make sure the passed in list has equal
     * length.
     *
     * @param aliasNameList alias name list
     * @param columnNameList real database column names list
     *
     * @throws ConfigurationException if nay error occurred
     */
    private void addKeyValuePair(List aliasNameList, List columnNameList)
        throws ConfigurationException {
        List clientColumnNameList = ClientColumnName.getEnumList(ClientColumnName.class);
        List clientProjectColumnNameList = ClientProjectColumnName.getEnumList(ClientProjectColumnName.class);

        // add to the clientColumnNames, make sure all the column name is
        // contained.
        for (int i = 0; i < clientColumnNameList.size(); i++) {
            int index = aliasNameList.indexOf(clientColumnNameList.get(i).toString());

            if (index == -1) {
                throw new ConfigurationException("The config file does not contain " + clientColumnNameList.get(i)
                    + ".");
            }

            this.clientColumnNames.put(aliasNameList.get(index), columnNameList.get(index));
        }

        // add to the clientprojectColumnName, make sure all the column name is
        // contained
        for (int i = 0; i < clientProjectColumnNameList.size(); i++) {
            int index = aliasNameList.indexOf(clientProjectColumnNameList.get(i).toString());

            if (index == -1) {
                throw new ConfigurationException("The config file does not contain "
                    + clientProjectColumnNameList.get(i) + ".");
            }

            this.clientProjectColumnNames.put(aliasNameList.get(index), columnNameList.get(index));
        }
    }

    /**
     * <p>
     * Build the batch select command.
     * </p>
     * @param ids the id list to build
     * @return return the built command
     */
    private String buildBatchSelect(long[] ids) {
        StringBuffer sb = new StringBuffer(SQL_SELECT_ALL_CLIENT);

        sb.append(" where client_id in (");

        for (int i = 0; i < ids.length; i++) {
            sb.append("?");

            if (i != (ids.length - 1)) {
                sb.append(",");
            }
        }

        sb.append(")");

        return sb.toString();
    }

    /**
     * <p>
     * Read the client information the result set.
     * </p>
     * @param result the result set to read
     * @return the client
     * @throws SQLException if any error occurred
     */
    private Client getClientFromResultSet(ResultSet result)
        throws SQLException {
        Client client = new Client();

        client.setId(result.getLong("client_id"));
        client.setName(result.getString("name"));
        client.setCompanyId(result.getLong("company_id"));
        client.setCreationDate(result.getDate("creation_date"));
        client.setCreationUser(result.getString("creation_user"));
        client.setModificationDate(result.getDate("modification_date"));
        client.setModificationUser(result.getString("modification_user"));

        PaymentTerm paymentTerm = new PaymentTerm();
        long paymentTermId = result.getLong("payment_term_id");
        paymentTerm.setId(paymentTermId);
        client.setPaymentTerm(paymentTerm);
        client.setActive((result.getInt("status") != 0) ? true : false);
        client.setSalesTax(result.getDouble("salesTax"));
        client.setStartDate(result.getDate("start_date"));
        client.setEndDate(result.getDate("end_date"));

        return client;
    }

    /**
     * Build BatchOperationException from the BatchUpdateException.
     *
     * @param bue the BatchUpdateException
     * @param length the length of the batch
     *
     * @throws BatchOperationException convert BatchUpdateException to BatchOperationException and throw it.
     */
    private void buildBatchOperationException(BatchUpdateException bue, int length)
        throws BatchOperationException {
        // in informix, the batch will stop when error happened,
        // so updateResult will contain only the update that ends with a success.
        int[] updateResult = bue.getUpdateCounts();
        boolean[] success = new boolean[length];

        // set the success field
        Arrays.fill(success, 0, updateResult.length, true);
        Arrays.fill(success, updateResult.length, success.length, false);

        throw new BatchOperationException("Update batch error.", bue, success);
    }

    /**
     * <p>
     * Audit the client, the client is changed form the oldClient to the newClient, one of them may be null. If
     * oldClient is null, means that is a create action, if newClient is null, means that it's a delete action,
     * otherwise, it's a update action.
     * </p>
     *
     * @param oldClient the old Client
     * @param newClient the new Client
     *
     * @throws ClientAuditException if error when audit
     */
    private void auditClient(Client oldClient, Client newClient)
        throws ClientAuditException {
        // create the audit details
        // the notification table has 13 columns.
        AuditDetail[] details = new AuditDetail[12];

        details[0] = buildAuditDetail("client_id",
                (oldClient == null) ? null : (new Long(oldClient.getId())).toString(),
                (newClient == null) ? null : (new Long(newClient.getId())).toString());
        details[1] = buildAuditDetail("company_id",
                (oldClient == null) ? null : (new Long(oldClient.getCompanyId())).toString(),
                (newClient == null) ? null : (new Long(newClient.getCompanyId())).toString());
        details[2] = buildAuditDetail("fromAddress", (oldClient == null) ? null : oldClient.getName(),
                (newClient == null) ? null : newClient.getName());
        details[3] = buildAuditDetail("subject",
                (oldClient == null) ? null : (new Long(oldClient.getPaymentTerm().getId())).toString(),
                (newClient == null) ? null : (new Long(newClient.getPaymentTerm().getId())).toString());
        details[4] = buildAuditDetail("message",
                (oldClient == null) ? null : (new Double(oldClient.getSalesTax())).toString(),
                (newClient == null) ? null : (new Double(newClient.getSalesTax())).toString());
        details[5] = buildAuditDetail("last_time_sent",
                (oldClient == null) ? null : oldClient.getStartDate().toString(),
                (newClient == null) ? null : newClient.getStartDate().toString());
        details[6] = buildAuditDetail("next_time_send", (oldClient == null) ? null : oldClient.getEndDate().toString(),
                (newClient == null) ? null : newClient.getEndDate().toString());
        details[7] = buildAuditDetail("status", (oldClient == null) ? null : (oldClient.isActive() ? "1" : "0"),
                (newClient == null) ? null : (newClient.isActive() ? "1" : "0"));
        details[8] = buildAuditDetail("creation_user", (oldClient == null) ? null : oldClient.getCreationUser(),
                (newClient == null) ? null : newClient.getCreationUser());
        details[9] = buildAuditDetail("creation_date",
                (oldClient == null) ? null : oldClient.getCreationDate().toString(),
                (newClient == null) ? null : newClient.getCreationDate().toString());
        details[10] = buildAuditDetail("modification_user",
                (oldClient == null) ? null : oldClient.getModificationUser(),
                (newClient == null) ? null : newClient.getModificationUser());
        details[11] = buildAuditDetail("modification_date",
                (oldClient == null) ? null : oldClient.getModificationDate().toString(),
                (newClient == null) ? null : newClient.getModificationDate().toString());

        long entityId = -1;
        long companyId = -1;
        int actionType = -1;
        String creationUser = null;

        if ((oldClient != null) && (newClient != null)) {
            actionType = AuditType.UPDATE;
            creationUser = newClient.getModificationUser();
            entityId = newClient.getId();
            companyId = newClient.getCompanyId();
        } else if (oldClient == null) {
            actionType = AuditType.INSERT;
            creationUser = newClient.getCreationUser();
            entityId = newClient.getId();
            companyId = newClient.getCompanyId();
        } else if (newClient == null) {
            actionType = AuditType.DELETE;
            creationUser = oldClient.getModificationUser();
            entityId = oldClient.getId();
            companyId = oldClient.getCompanyId();
        }

        AuditHeader header = buildAuditHeader(entityId, "notification", companyId, actionType, creationUser);

        header.setDetails(details);

        try {
            auditManager.createAuditRecord(header);
        } catch (AuditManagerException ame) {
            throw new ClientAuditException("Error auditing.", ame);
        }
    }

    /**
     * Build the audit detail from the column name, old value and new value.
     *
     * @param columnName the column name for the audit detail
     * @param oldValue the old value of the column
     * @param newValue the new value for the column
     *
     * @return the AuditDetail
     */
    private AuditDetail buildAuditDetail(String columnName, String oldValue, String newValue) {
        AuditDetail detail = new AuditDetail();

        detail.setColumnName(columnName);
        detail.setOldValue(oldValue);
        detail.setNewValue(newValue);

        return detail;
    }

    /**
     * Build the auditHeder with the provided information.
     *
     * @param entityId the entity id for the header
     * @param tableName the table name for the header
     * @param companyId the company id for the header
     * @param actionType the action type for the header
     * @param creationUser the creation user of the header
     *
     * @return return the audit header
     */
    private AuditHeader buildAuditHeader(long entityId, String tableName, long companyId, int actionType,
        String creationUser) {
        AuditHeader header = new AuditHeader();

        header.setEntityId(entityId);
        header.setTableName(tableName);
        header.setCompanyId(companyId);
        header.setActionType(actionType);
        header.setApplicationArea(ApplicationArea.TT_CLIENT);
        header.setCreationUser(creationUser);

        return header;
    }

    /**
     * Translate the list from alias name to column name list.
     *
     * @param fieldName the alias name list
     *
     * @return the column name list
     *
     * @throws ClientPersistenceException if any error occurred
     */
    private List translateToColumnName(List fieldName)
        throws ClientPersistenceException {
        List columnName = new ArrayList();

        for (int i = 0; i < fieldName.size(); i++) {
            Object match = clientColumnNames.get(fieldName.get(i));

            if (match == null) {
                match = clientProjectColumnNames.get(fieldName.get(i));
            }

            if (match == null) {
                throw new ClientPersistenceException("Wrong filter names " + fieldName.get(i) + " .");
            }
            columnName.add(match.toString());
        }

        return columnName;
    }

    /**
     * Get the property specified from the specified namespace as Property object.
     *
     * @param namespace namespace to retrieve from.
     * @param key properties to retrieve
     *
     * @return The value of the given property
     *
     * @throws ConfigurationException if can not get the config value
     */
    private Property getConfigPropertyObject(String namespace, String key)
        throws ConfigurationException {
        Property property = null;

        try {
            property = ConfigManager.getInstance().getPropertyObject(namespace, key);
        } catch (UnknownNamespaceException une) {
            throw new ConfigurationException("The namespace '" + namespace + "' is unknown.", une);
        }

        if (property == null) {
            throw new ConfigurationException("The '" + key + "' property does not exist in '" + namespace
                + "' namespace.");
        }

        return property;
    }
}
