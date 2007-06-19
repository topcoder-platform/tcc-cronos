/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.invoice.ConfigUtil;
import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceStatusDAO;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.ArgumentCheckUtil;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval Time Tracker Invoice
 * Status data from a persistent store. It's assumed that the invoice statuses don't change during the application
 * lifetime so all statuses are loaded once time, so this improves the efficiency. The only thing that changes is
 * the status of Invoice as in the RS is specified.
 * </p>
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InformixInvoiceStatusDAO implements InvoiceStatusDAO {

    /** Represents SQL query for InvoiceStatus selection. */
    private static final String SELECT_FROM_INVOICE_STATUS = "SELECT * FROM invoice_status";

    /** Represents the property name of object factory namespace in the configuration. */
    private static final String OBJECT_FACTORY_NAMESPACE = "objectFactoryNamespace";

    /** Represents the property name of database connection factory key in the object factory. */
    private static final String DB_CONNECTION_FACTORY_KEY = "dbConnectionFactoryKey";

    /**
     * <p>
     * These are the map of invoice statuses loaded from db. Key is the id and value id the actual invoice status.
     * </p>
     * <p>
     * Initial Value:from getAllInvoiceStatus
     * </p>
     * <p>
     * Accessed In: getAllInvoiceStatus
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: getInvoiceStatus and getInvoiceStatus
     * </p>
     * <p>
     * Valid Values: not null, but can be void, if it's void can't contain null values.
     * </p>
     */
    private HashMap invoiceIdMap;

    /**
     * <p>
     * These are the map of invoice statuses loaded from db. Key is the description and value id the actual invoice
     * status.
     * </p>
     * <p>
     * Initial Value:from getAllInvoiceStatus
     * </p>
     * <p>
     * Accessed In: getAllInvoiceStatus
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: getInvoiceStatus and getInvoiceStatus
     * </p>
     * <p>
     * Valid Values: not null, but can be void, if it's void can't contain null values.
     * </p>
     */
    private HashMap invoiceDescriptionMap;

    /**
     * <p>
     * This is the connectionFactory that is used to acquire a connection to the persistent store when it is
     * needed. With DBConnectionFactory is possible to configure the JBoss Transaction DataSource
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not accessed.
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: getAllInvoiceStatus
     * </p>
     * <p>
     * Valid Values: Not null implementation.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Default constructor. Load the configuration values from default namespace.
     * </p>
     *
     * @throws InvoiceConfigurationException
     *             if any configured value is invalid or any required value is missing, it is also used to wrap the
     *             exceptions from ConfigManager
     */
    public InformixInvoiceStatusDAO() throws InvoiceConfigurationException {
        this(InformixInvoiceStatusDAO.class.getName());
    }

    /**
     * <p>
     * Constructor to load configuration values from the given namespace.
     * </p>
     *
     * @param namespace
     *            the namespace to load configuration values
     *
     * @throws IllegalArgumentException
     *             if argument is null or empty string
     * @throws InvoiceConfigurationException
     *             if any configured value is invalid or any required value is missing, it is also used to wrap the
     *             exceptions from ConfigManager
     */
    public InformixInvoiceStatusDAO(String namespace) throws InvoiceConfigurationException {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("namespace", namespace);

        // get the property value
        String objectFactoryNamespace = ConfigUtil.getRequiredProperty(namespace, OBJECT_FACTORY_NAMESPACE);

        // get object factory
        ObjectFactory of = ConfigUtil.createObjectFactory(objectFactoryNamespace);

        // get connection factory
        connectionFactory =
            (DBConnectionFactory) ConfigUtil.createObject(of, namespace, DB_CONNECTION_FACTORY_KEY,
                DBConnectionFactory.class);
    }

    /**
     * <p>
     * Returns all invoice statuses that are currently in the persistent store and initialize all invoice statuses
     * in lazy mode.
     * </p>
     *
     * @return the invoices statuses contained in db, a void arrays if there aren't invoice statuses.
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public synchronized InvoiceStatus[] getAllInvoiceStatus() throws InvoiceDataAccessException {

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            // gets the connection
            connection = DBUtil.getConnection(connectionFactory);
            try {
                statement = connection.createStatement();

                // do the query
                rs = DBUtil.executeQuery(statement, SELECT_FROM_INVOICE_STATUS);

                List temp = new ArrayList();
                invoiceIdMap = new HashMap();
                invoiceDescriptionMap = new HashMap();

                while (rs.next()) {
                    long id = rs.getLong("invoice_status_id");
                    String description = rs.getString("description");
                    // Skip illegal IDs
                    if (id <= 0) {
                        continue;
                    }
                    InvoiceStatus invoiceStatus =
                        new InvoiceStatus(id, description, rs.getString("creation_user"), rs
                            .getString("modification_user"), rs.getDate("creation_date"), rs
                            .getDate("modification_date"));
                    temp.add(invoiceStatus);
                    invoiceIdMap.put(new Long(id), invoiceStatus);
                    invoiceDescriptionMap.put(description, invoiceStatus);
                }

                return (InvoiceStatus[]) temp.toArray(new InvoiceStatus[temp.size()]);
            } catch (SQLException e) {
                throw new InvoiceDataAccessException("Database-related error happens", e);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Returns the invoice status from id.
     * </p>
     *
     * @param id
     *            the id of invoice statuses from what is retrieved
     *
     * @return the invoice status that has the id used
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if an invoice status doesn't exist with the specified id
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public InvoiceStatus getInvoiceStatus(long id) throws InvoiceDataAccessException {
        // make sure that the map is already filled
        if (invoiceIdMap == null) {
            getAllInvoiceStatus();
        }
        InvoiceStatus invoiceStatus = (InvoiceStatus) invoiceIdMap.get(new Long(id));
        if (invoiceStatus == null) {
            throw new InvoiceUnrecognizedEntityException(id, "Can't find invoice status with id " + id);
        }

        return invoiceStatus;
    }

    /**
     * <p>
     * Returns the invoice status from description. (It's assumed that it's unique for each invoice)
     * </p>
     *
     * @param description
     *            the description of invoice status from what is retrieved
     *
     * @return the invoice status that has the description used
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     * @throws IllegalArgumentException
     *             if description is null or empty.
     */
    public InvoiceStatus getInvoiceStatus(String description) throws InvoiceDataAccessException {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("description", description);

        // make sure that the map is already filled
        if (invoiceIdMap == null) {
            getAllInvoiceStatus();
        }

        InvoiceStatus invoiceStatus = (InvoiceStatus) invoiceDescriptionMap.get(description);
        if (invoiceStatus == null) {
            throw new InvoiceDataAccessException("Can't find invoice status with description " + description);
        }

        return invoiceStatus;
    }
}
