/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.time.TimeEntryManager;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.ArgumentCheckUtil;
import com.topcoder.timetracker.invoice.servicedetail.BatchExecutionException;
import com.topcoder.timetracker.invoice.servicedetail.ConfigurationException;
import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.EntityNotFoundException;
import com.topcoder.timetracker.invoice.servicedetail.InvalidDataException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.dao.ServiceDetailDAO;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorkerUtility;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserManager;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is implementation of ServiceDetailDAO interface. This class realizes all operation with database table and
 * also provides ability to audit operation.
 * </p>
 * <p>
 * Class is immutable, but it is not thread safe. Methods on this class do not use transaction and in this case
 * they may injure data in database. This class should run with some transaction manager, like EJB in this
 * component. Using this class directly may cause a lot of problems so do not do it.
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class ServiceDetailDAOImpl implements ServiceDetailDAO {

    /** Property key for user manager configuration. */
    private static final String USER_MANAGER_NAME_KEY = "user_manager_name";

    /** Property key for project worker utility configuration. */
    private static final String PROJECT_WORKER_UTILITY_NAME_KEY = "project_worker_utility_name";

    /** Property key for time entry manager configuration. */
    private static final String TIME_ENTRY_MANAGER_NAME_KEY = "time_entry_manager_name";

    /** Property key for audit manager name configuration. */
    private static final String AUDIT_MANAGER_NAME_KEY = "audit_manager_name";

    /** Property key for factory namespace configuration. */
    private static final String FACTORY_NAMESPACE_KEY = "factory_namespace";

    /** Property key for id generator configuration. */
    private static final String ID_GENERATOR_KEY = "id_generator";

    /** Property key for connection name configuration. */
    private static final String CONNECTION_NAME_KEY = "connection_name";

    /** Property key for connection namespace configuration. */
    private static final String CONNECTION_NAMESPACE_KEY = "connection_namespace";

    /** Property key for use batch configuration. */
    private static final String USE_BATCH_KEY = "use_batch";

    /** Insert query. */
    private static final String INSERT_QUERY =
        "INSERT INTO service_details (service_detail_id, time_entry_id, "
            + "invoice_id, rate, amount, creation_date, creation_user, modification_date, "
            + "modification_user) VALUES (?,?,?,?,?,?,?,?,?)";

    /** Update query. */
    private static final String UPDATE_QUERY =
        "UPDATE service_details SET time_entry_id=?, invoice_id=?, rate=?, "
            + "amount=?, modification_date=?, modification_user=? where service_detail_id=?";

    /**
     * <p>
     * This attribute represents default namespace. It is used by constructor without parameter. This is immutable,
     * static, sets by default.
     * </p>
     */
    private static final String DEFAULT_NAMESPACE = "com.topcoder.timetracker.invoice.servicedetail.dao.impl";

    /**
     * <p>
     * Represents default instance of DBConnectionFactory. It used by many methods of children classes to create
     * Connection instance. This is immutable, sets by constructor, null impossible.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents instance of IDGenerator. It is used by addServiceDetail() method to generate unique id for
     * service_detail_id of service_detal table. This is immutable, sets by constructor, null impossible.
     * </p>
     */
    private final IDGenerator generator;

    /**
     * <p>
     * This attributes is used to audit information about operations(add/update/delete). This is immutable, sets by
     * constructor, null impossible.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * This attributes is used to get TimeEntry instances in methods which retrieves data. This is immutable, sets
     * by constructor, null impossible.
     * </p>
     */
    private final TimeEntryManager timeEntryManager;

    /**
     * <p>
     * This attributes is used to define which rate when service detail is added to database. It is allows to get
     * username. This is immutable, sets by constructor, null impossible.
     * </p>
     */
    private final UserManager userManager;

    /**
     * <p>
     * This attributes is used to retrieve rate when service detail is added to database. This is immutable, sets
     * by constructor, null impossible.
     * </p>
     */
    private final ProjectWorkerUtility projectWorkerUtility;

    /**
     * <p>
     * This attributes is used to indicate mode of usage of batch operation. If it is true we allowed using batch
     * operation, if false we do not allowed doing it. This attribute was added because not all drivers support
     * batch operations. This is immutable, sets by constructor.
     * </p>
     */
    private final boolean useBatch;

    /**
     * <p>
     * This constructor is used to set class attributes. It simply run the other constructor of the class will
     * default namespace.
     * </p>
     *
     * @throws ConfigurationException
     *             if configuration process failed
     */
    public ServiceDetailDAOImpl() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * This constructor is used to set class attributes. It uses Config Manager to load needed properties.
     * </p>
     *
     * @param namespace
     *            the given namespace
     *
     * @throws ConfigurationException
     *             if configuration process failed
     * @throws IllegalArgumentException
     *             if namespace is null or empty String
     */
    public ServiceDetailDAOImpl(String namespace) throws ConfigurationException {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("namespace", namespace);
        try {
            // retrieves use_batch configuration (only "true" or "false" allowed
            String useBatchString = ConfigManager.getInstance().getString(namespace, USE_BATCH_KEY);
            if ("true".equals(useBatchString)) {
                useBatch = true;
            } else if ("false".equals(useBatchString)) {
                useBatch = false;
            } else {
                throw new ConfigurationException("Error value for use_batch configuration");
            }

            // get connection_namespace and use it to create connectionFactory
            String connectionNamespace = getConfigurationValue(namespace, CONNECTION_NAMESPACE_KEY);
            connectionFactory = new DBConnectionFactoryImpl(connectionNamespace);

            // get connection_name and set the default connection name
            String connectionName = getConfigurationValue(namespace, CONNECTION_NAME_KEY);
            ((DBConnectionFactoryImpl) connectionFactory).setDefault(connectionName);

            // get id_generator and use it to create generator
            String idGeneratorName = getConfigurationValue(namespace, ID_GENERATOR_KEY);
            generator = IDGeneratorFactory.getIDGenerator(idGeneratorName);

            // get factory_namespace and use it to create objectFactory
            String factoryNamespace = getConfigurationValue(namespace, FACTORY_NAMESPACE_KEY);
            SpecificationFactory specificationFactory = new ConfigManagerSpecificationFactory(factoryNamespace);
            ObjectFactory objectFactory = new ObjectFactory(specificationFactory);

            auditManager = (AuditManager) getObjectFromFactory(namespace, AUDIT_MANAGER_NAME_KEY, objectFactory);
            timeEntryManager =
                (TimeEntryManager) getObjectFromFactory(namespace, TIME_ENTRY_MANAGER_NAME_KEY, objectFactory);
            projectWorkerUtility =
                (ProjectWorkerUtility) getObjectFromFactory(namespace, PROJECT_WORKER_UTILITY_NAME_KEY,
                    objectFactory);
            userManager = (UserManager) getObjectFromFactory(namespace, USER_MANAGER_NAME_KEY, objectFactory);

        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("The namespace " + namespace + " is unknown", e);
        } catch (UnknownConnectionException e) {
            throw new ConfigurationException("Unknown connection name", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new ConfigurationException("Exception in database configuration", e);
        } catch (IDGenerationException e) {
            throw new ConfigurationException("Exception in generating the id", e);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("Exception in creating specification factory", e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("Exception in creating specification factory", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("Exception in creating the object from object factory", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("The type of the object is different", e);
        }

    }

    /**
     * Retrieves object from object factory. The key of the object in the object factory is
     * objectConfigurationName.
     *
     * @param namespace
     *            the given namespace
     * @param objectConfigurationName
     *            the configuration name where object key is located
     * @param objectFactory
     *            the given object factory
     *
     * @return the intended object
     *
     * @throws UnknownNamespaceException
     *             if the namespace is unknown
     * @throws InvalidClassSpecificationException
     *             if the objectKey is not in the object factory
     * @throws ConfigurationException
     *             if the objectKey is empty string or null
     */
    private Object getObjectFromFactory(String namespace, String objectConfigurationName,
        ObjectFactory objectFactory) throws UnknownNamespaceException, InvalidClassSpecificationException,
        ConfigurationException {
        String objectKey = getConfigurationValue(namespace, objectConfigurationName);
        return objectFactory.createObject(objectKey);
    }

    /**
     * Extract configuration value from the configuration manager.
     *
     * @param namespace
     *            the namespace
     * @param objectConfigurationName
     *            the name of the configuration property
     *
     * @return the configuration value
     *
     * @throws UnknownNamespaceException
     *             if the namespace is unknown
     * @throws ConfigurationException
     *             if the value is an empty string or null
     */
    private String getConfigurationValue(String namespace, String objectConfigurationName)
        throws UnknownNamespaceException, ConfigurationException {
        String objectKey = ConfigManager.getInstance().getString(namespace, objectConfigurationName);
        if (ArgumentCheckUtil.isNullOrEmptyString(objectKey)) {
            throw new ConfigurationException("The objectKey can't be null or an empty string");
        }
        return objectKey;
    }

    /**
     * <p>
     * This method is used to add InvoiceServiceDetail instance to data store. It also provide ability to audit
     * operation(if audit parameter is true). Method should ignore values of rate and amount stored in given
     * detail, it should get rate from project worker and amount calculate as multiply of rate and hours form
     * timeEntry attribute of detail.
     * </p>
     *
     * @param detail
     *            the given <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     */
    public void addServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException {
        ArgumentCheckUtil.checkNotNull("detail", detail);

        Connection connection = null;
        try {
            // create connection
            connection = connectionFactory.createConnection();

            addServiceDetail(connection, detail, audit);

        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } finally {
            DBUtil.closeConnection(connection);
        }

    }

    /**
     * <p>
     * This method is used to add InvoiceServiceDetail instance to data store. It also provide ability to audit
     * operation(if audit parameter is true). Method should ignore values of rate and amount stored in given
     * detail, it should get rate from project worker and amount calculate as multiply of rate and hours form
     * timeEntry attribute of detail.
     * </p>
     *
     * @param connection
     *            the givem connection
     * @param detail
     *            the given <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     */
    private void addServiceDetail(Connection connection, InvoiceServiceDetail detail, boolean audit)
        throws DataAccessException {

        PreparedStatement insertStatement = null;
        try {

            // generate unique id of detail
            detail.setId(generator.getNextID());

            checkDetail(detail);

            calculateAmount(detail, true);

            insertStatement = connection.prepareStatement(INSERT_QUERY);
            Date now = new Date(Calendar.getInstance().getTimeInMillis());
            detail.setCreationDate(now);
            detail.setModificationDate(now);
            insertStatement.setLong(1, detail.getId());
            insertStatement.setLong(2, detail.getTimeEntry().getId());
            insertStatement.setLong(3, detail.getInvoice().getId());
            insertStatement.setInt(4, detail.getRate());
            insertStatement.setBigDecimal(5, detail.getAmount());
            insertStatement.setDate(6, now);
            insertStatement.setString(7, detail.getCreationUser());
            insertStatement.setDate(8, now);
            insertStatement.setString(9, detail.getModificationUser());

            insertStatement.execute();

            if (audit) {
                AuditHeader header = createAuditHeader(null, detail, now);

                auditManager.createAuditRecord(header);
            }

            detail.setChanged(false);
        } catch (com.topcoder.timetracker.project.DataAccessException dae) {
            throw new DataAccessException("An exception happens when retrieving project workers", dae);
        } catch (com.topcoder.timetracker.user.DataAccessException dae) {
            throw new DataAccessException("An exception happens when retrieving users", dae);
        } catch (AuditManagerException ame) {
            throw new DataAccessException("An exception happens when doing audit", ame);
        } catch (IDGenerationException e) {
            throw new DataAccessException("An exception happens when generating id", e);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } finally {
            DBUtil.closeStatement(insertStatement);
        }
    }

    /**
     * Creates audit header.
     *
     * @param oldDetail
     *            as result set
     * @param newDetail
     *            the given InvoiceServiceDetail
     * @param now
     *            current time
     *
     * @return the audit header
     *
     * @throws SQLException
     *             when there is trouble accessing the result set
     */
    private AuditHeader createAuditHeader(ResultSet oldDetail, InvoiceServiceDetail newDetail, Date now)
        throws SQLException {
        AuditHeader header = new AuditHeader();
        header.setEntityId(newDetail.getId());
        header.setTableName("service_details");
        header.setCompanyId(newDetail.getTimeEntry().getCompanyId());
        if (oldDetail == null) {
            header.setActionType(AuditType.INSERT);
        } else {
            header.setActionType(AuditType.UPDATE);
        }
        header.setApplicationArea(ApplicationArea.TT_INVOICE);
//        header.setResourceId(newDetail.getId());
        header.setCreationUser(newDetail.getCreationUser());

        // id
        AuditDetail idDetail = new AuditDetail();
        idDetail.setColumnName("service_detail_id");
        idDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("service_detail_id"));
        idDetail.setNewValue(newDetail.getId() + "");

        // time entry id
        AuditDetail timeEntryIdDetail = new AuditDetail();
        timeEntryIdDetail.setColumnName("time_entry_id");
        timeEntryIdDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("time_entry_id"));
        timeEntryIdDetail.setNewValue(newDetail.getTimeEntry().getId() + "");

        // invoice id
        AuditDetail invoiceIdDetail = new AuditDetail();
        invoiceIdDetail.setColumnName("invoice_id");
        invoiceIdDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("invoice_id"));
        invoiceIdDetail.setNewValue(newDetail.getInvoice().getId() + "");

        // rate
        AuditDetail rateDetail = new AuditDetail();
        rateDetail.setColumnName("rate");
        rateDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("rate"));
        rateDetail.setNewValue(newDetail.getRate() + "");

        // amount
        AuditDetail amountDetail = new AuditDetail();
        amountDetail.setColumnName("amount");
        amountDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("amount"));
        amountDetail.setNewValue(newDetail.getAmount() + "");

        // creation date
        AuditDetail creationDateDetail = new AuditDetail();
        creationDateDetail.setColumnName("creation_date");
        creationDateDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("creation_date"));
        creationDateDetail.setNewValue(now + "");

        // creation user
        AuditDetail creationUserDetail = new AuditDetail();
        creationUserDetail.setColumnName("creation_user");
        creationUserDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("creation_user"));
        creationUserDetail.setNewValue(newDetail.getCreationUser());

        // modification date
        AuditDetail modificationDateDetail = new AuditDetail();
        modificationDateDetail.setColumnName("modification_date");
        modificationDateDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("modification_date"));
        modificationDateDetail.setNewValue(now + "");

        // modification user
        AuditDetail modificationUserDetail = new AuditDetail();
        modificationUserDetail.setColumnName("modification_user");
        modificationUserDetail.setOldValue(oldDetail == null ? null : oldDetail.getString("modification_user"));
        modificationUserDetail.setNewValue(newDetail.getModificationUser());

        header.setDetails(new AuditDetail[] {idDetail, timeEntryIdDetail, invoiceIdDetail, rateDetail,
            amountDetail, creationDateDetail, creationUserDetail, modificationDateDetail, modificationUserDetail});
        return header;
    }

    /**
     * Calculates the amount of a InvoiceServiceDetail.
     *
     * @param detail
     *            the given InvoiceServiceDetail
     * @param searchRate
     *            whethet the rate should be serached again or not
     * @throws InvalidDataException
     *             if there is no such user
     */
    private void calculateAmount(InvoiceServiceDetail detail, boolean searchRate)
        throws InvalidDataException, com.topcoder.timetracker.project.DataAccessException,
            com.topcoder.timetracker.user.DataAccessException {
        if (searchRate) {
            ProjectWorkerFilterFactory filterFactory = projectWorkerUtility.getProjectWorkerFilterFactory();
            Filter filter = filterFactory.createProjectIdFilter(detail.getInvoice().getProjectId());

            ProjectWorker[] workers = projectWorkerUtility.searchProjectWorkers(filter);

            String creationUsername = detail.getTimeEntry().getCreationUser();

            long[] userIds = new long[workers.length];
            boolean found = false;
            for (int i = 0; i < workers.length; i++) {
                userIds[i] = workers[i].getUserId();
                User user = userManager.getUser(userIds[i]);
                if (user.getUsername().equals(creationUsername)) {
                    detail.setRate((int) workers[i].getPayRate()); // TODO: SHOULD allow setting double values
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new InvalidDataException("No such user [" + creationUsername + "] in the database");
            }
        } else {
            if (detail.getRate() == -1) {
                throw new InvalidDataException("Rate should be positive");
            }
        }
        detail.setAmount(new BigDecimal(detail.getTimeEntry().getHours() * detail.getRate()));
    }

    /**
     * Checks validity of InvoiceServiceDetail.
     *
     * @param detail
     *            the given InvoiceServiceDetail
     *
     * @throws InvalidDataException
     *             if the InvoiceServiceDetail is not valid
     */
    private void checkDetail(InvoiceServiceDetail detail) throws InvalidDataException {
        if (detail.getInvoice() == null) {
            throw new InvalidDataException("The invoice can't be null");
        }
        if (detail.getTimeEntry() == null) {
            throw new InvalidDataException("The time entry can't be null");
        }
        if (detail.getCreationUser() == null) {
            throw new InvalidDataException("The creation user can't be null");
        }
        if (detail.getModificationUser() == null) {
            throw new InvalidDataException("The modification user can't be null");
        }
    }

    /**
     * <p>
     * This method is used to remove InvoiceServiceDetail instance from data store by its id. It also provide
     * ability to audit operation (if audit parameter is true).
     * </p>
     *
     * @param id
     *            the given id of <code>InvoceServiceDetail</code>
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     */
    public void deleteServiceDetail(long id, boolean audit) throws DataAccessException {
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            deleteServiceDetail(connection, id, audit);
        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Creates array of audit details from the record on the database.
     *
     * @param serviceDetailId
     *            the id of the service details
     * @param connection
     *            the given connection
     *
     * @return array of <code>InvoiceServiceDetail</code>
     *
     * @throws SQLException
     *             if there is error when communicating with the database
     */
    private AuditDetail[] buildAuditDetail(long serviceDetailId, Connection connection) throws SQLException {
        AuditDetail[] auditDetails;
        Statement statement = null;
        ResultSet set = null;
        try {
            statement = connection.createStatement();
            set =
                statement.executeQuery("SELECT * FROM service_details WHERE service_detail_id=" + serviceDetailId);
            auditDetails = new AuditDetail[set.getMetaData().getColumnCount()];
            if (set.next()) {
                for (int i = 0; i < set.getMetaData().getColumnCount(); i++) {
                    AuditDetail detail = new AuditDetail();
                    detail.setColumnName(set.getMetaData().getColumnName(i + 1));
                    detail.setNewValue(null);
                    detail.setOldValue(set.getString(i + 1));
                    auditDetails[i] = detail;
                }

            }
            return auditDetails;
        } finally {
            DBUtil.closeStatement(statement);
            DBUtil.closeResultSet(set);
        }
    }

    /**
     * <p>
     * This method is used to delete all InvoiceServiceDetail instances from data store. It also provide ability to
     * audit operation(if audit parameter is true).
     * </p>
     *
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     */
    public void deleteAllServiceDetails(boolean audit) throws DataAccessException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = connectionFactory.createConnection();
            // select all service ids from service_details table
            statement = connection.createStatement();
            set = statement.executeQuery("SELECT service_detail_id FROM service_details");

            while (set.next()) {
                deleteServiceDetail(set.getLong("service_detail_id"), audit);
            }

        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(set);
            DBUtil.closeStatement(statement);
        }
    }

    /**
     * <p>
     * This method is used to update InvoiceServiceDetail instance in data store. It also provide ability to audit
     * operation(if audit parameter is true). Method should ignore value amount stored in given detail, it should
     * calculate it as multiply of rate and hours form timeEntry attribute of detail.
     * </p>
     *
     * @param detail
     *            the given <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     * @throws EntityNotFoundException
     *             should be thrown if some detail not found in database.
     */
    public void updateServiceDetail(InvoiceServiceDetail detail, boolean audit)
        throws DataAccessException {
        ArgumentCheckUtil.checkNotNull("detail", detail);
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();

            updateServiceDetail(connection, detail, audit);
        } catch (AuditManagerException ame) {
            throw new DataAccessException("An exception happens when doing audit", ame);
        } catch (com.topcoder.timetracker.project.DataAccessException dae) {
            throw new DataAccessException("An exception happens when updating service details", dae);
        } catch (com.topcoder.timetracker.user.DataAccessException dae) {
            throw new DataAccessException("An exception happens when updating service details", dae);
        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * <p>
     * This method is used to update InvoiceServiceDetail instance in data store. It also provide ability to audit
     * operation(if audit parameter is true). Method should ignore value amount stored in given detail, it should
     * calculate it as multiply of rate and hours form timeEntry attribute of detail.
     * </p>
     *
     * @param connection
     *            the given connection
     * @param detail
     *            the given <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     * @throws EntityNotFoundException
     *             should be thrown if some detail not found in database.
     */
    private void updateServiceDetail(Connection connection, InvoiceServiceDetail detail, boolean audit)
        throws DataAccessException, AuditManagerException,
            com.topcoder.timetracker.project.DataAccessException,
            com.topcoder.timetracker.user.DataAccessException {

        Statement statement = null;
        ResultSet set = null;
        PreparedStatement updateStatement = null;
        try {

            checkDetail(detail);

            Date now = new Date(Calendar.getInstance().getTimeInMillis());
            detail.setModificationDate(now);

            // if detail is change, recalculate amount
            if (detail.isChanged()) {
                calculateAmount(detail, false);
            }

            if (audit) {
                statement = connection.createStatement();
                set =
                    statement.executeQuery("SELECT * FROM service_details WHERE service_detail_id="
                        + detail.getId());

                if (set.next()) {
                    auditManager.createAuditRecord(createAuditHeader(set, detail, now));
                }

            }

            updateStatement = connection.prepareStatement(UPDATE_QUERY);

            updateStatement.setLong(1, detail.getTimeEntry().getId());
            updateStatement.setLong(2, detail.getInvoice().getId());
            updateStatement.setInt(3, detail.getRate());
            updateStatement.setBigDecimal(4, detail.getAmount());
            updateStatement.setDate(5, now);
            updateStatement.setString(6, detail.getModificationUser());
            updateStatement.setLong(7, detail.getId());

            updateStatement.execute();

            if (updateStatement.getUpdateCount() == 0) {
                throw new InvalidDataException("No record is updated");
            }

            detail.setChanged(false);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } finally {
            DBUtil.closeResultSet(set);
            DBUtil.closeStatement(statement);
            DBUtil.closeStatement(updateStatement);
        }
    }

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instance from data store by its id.
     * </p>
     *
     * @param id
     *            the given id of <code>InvoiceServiceDetail</code>
     *
     * @return the <code>InvoiceServiceDetail</code> with the given id
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException (may be problems with connection or with
     *             TimeEntry component)
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     */
    public InvoiceServiceDetail retrieveServiceDetail(long id) throws DataAccessException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        try {
            connection = connectionFactory.createConnection();
            // select all service ids from service_detail table
            statement = connection.createStatement();
            set = statement.executeQuery("SELECT * FROM service_details WHERE service_detail_id=" + id);

            if (set.next()) {
                return transformResultSetToServiceDetail(set);

            } else {
                throw new EntityNotFoundException(id);
            }

        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } catch (UnrecognizedEntityException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } catch (com.topcoder.timetracker.entry.time.DataAccessException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(set);
            DBUtil.closeStatement(statement);
        }
    }

    /**
     * Transforms result set to InvoiceServiceDetail.
     *
     * @param set
     *            the given result set
     *
     * @return InvoiceServiceDetail
     *
     * @throws SQLException
     *             if there is error when accessing database
     * @throws com.topcoder.timetracker.entry.time.DataAccessException
     *             if there is error when getting time entry from time entry manager
     */
    private InvoiceServiceDetail transformResultSetToServiceDetail(ResultSet set) throws SQLException,
        com.topcoder.timetracker.entry.time.DataAccessException {
        Invoice invoice = new Invoice();
        invoice.setId(set.getLong("invoice_id"));

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setId(set.getLong("service_detail_id"));
        detail.setAmount(set.getBigDecimal("amount"));
        detail.setRate(set.getInt("rate"));
        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntryManager.getTimeEntry(set.getLong("time_entry_id")));
        detail.setCreationDate(set.getDate("creation_date"));
        detail.setCreationUser(set.getString("creation_user"));
        detail.setModificationDate(set.getDate("modification_date"));
        detail.setModificationUser(set.getString("modification_user"));
        detail.setChanged(false);

        return detail;
    }

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instances from data store by invoice id.
     * </p>
     *
     * @param invoiceId
     *            the given id of <code>Invoice</code>
     *
     * @return the <code>InvoiceServiceDetail</code>s with the given invoiceId
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection or with
     *             TimeEntry component)
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long invoiceId) throws DataAccessException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = connectionFactory.createConnection();
            // select all service ids from service_detail table
            statement = connection.createStatement();
            set = statement.executeQuery("SELECT * FROM service_details WHERE invoice_id=" + invoiceId);

            List list = new ArrayList();

            while (set.next()) {
                list.add(transformResultSetToServiceDetail(set));
            }

            return (InvoiceServiceDetail[]) list.toArray(new InvoiceServiceDetail[list.size()]);

        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } catch (UnrecognizedEntityException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } catch (com.topcoder.timetracker.entry.time.DataAccessException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(set);
            DBUtil.closeStatement(statement);
        }
    }

    /**
     * <p>
     * This method is used to retrieve all InvoiceServiceDetail instances.
     * </p>
     *
     * @return all <code>InvoiceServiceDetail</code>s
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection or with
     *             TimeEntry component)
     */
    public InvoiceServiceDetail[] retrieveAllServiceDetails() throws DataAccessException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = connectionFactory.createConnection();
            // select all service ids from service_detail table
            statement = connection.createStatement();
            set = statement.executeQuery("SELECT * FROM service_details");

            List list = new ArrayList();

            while (set.next()) {
                list.add(transformResultSetToServiceDetail(set));
            }

            return (InvoiceServiceDetail[]) list.toArray(new InvoiceServiceDetail[list.size()]);

        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } catch (UnrecognizedEntityException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } catch (com.topcoder.timetracker.entry.time.DataAccessException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(set);
            DBUtil.closeStatement(statement);
        }
    }

    /**
     * <p>
     * This method is used to add array of InvoiceServiceDetail instances to data store. It also provide ability to
     * audit operation(if audit parameter is true). Method should ignore values of rate and amount stored in given
     * details, it should get rate from project worker and amount calculate as multiply of rate and hours form
     * timeEntry attribute of detail.
     * </p>
     *
     * @param details
     *            the given array of <code>InvoiceServiceDetail</code>s
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws IllegalArgumentException
     *             if details parameter is null or its contains null elements
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void addServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException {
        ArgumentCheckUtil.checkNotNull("details", details);
        ArgumentCheckUtil.checkNotContainsNull("details", details);

        if (!useBatch) {
            Connection connection = null;
            try {
                connection = connectionFactory.createConnection();
                for (int i = 0; i < details.length; i++) {
                    addServiceDetail(connection, details[i], audit);
                }
            } catch (DBConnectionException e) {
                throw new DataAccessException("An exception happens when creating connection", e);
            } finally {
                DBUtil.closeConnection(connection);
            }
        } else {
            Connection connection = null;
            PreparedStatement insertStatement = null;
            try {
                // create connection
                connection = connectionFactory.createConnection();

                insertStatement = connection.prepareStatement(INSERT_QUERY);

                for (int i = 0; i < details.length; i++) {

                    // generate unique id of detail
                    details[i].setId(generator.getNextID());

                    checkDetail(details[i]);

                    // calculate amount
                    calculateAmount(details[i], true);

                    Date now = new Date(Calendar.getInstance().getTimeInMillis());
                    details[i].setCreationDate(now);
                    details[i].setModificationDate(now);
                    insertStatement.setLong(1, details[i].getId());
                    insertStatement.setLong(2, details[i].getTimeEntry().getId());
                    insertStatement.setLong(3, details[i].getInvoice().getId());
                    insertStatement.setInt(4, details[i].getRate());
                    insertStatement.setBigDecimal(5, details[i].getAmount());
                    insertStatement.setDate(6, now);
                    insertStatement.setString(7, details[i].getCreationUser());
                    insertStatement.setDate(8, now);
                    insertStatement.setString(9, details[i].getModificationUser());

                    insertStatement.addBatch();

                    if (audit) {
                        auditManager.createAuditRecord(createAuditHeader(null, details[i], now));
                    }
                }

                int[] is = insertStatement.executeBatch();

                checkBatchError(details, is);

                for (int i = 0; i < details.length; ++i) {
                    details[i].setChanged(false);
                }
            } catch (AuditManagerException ame) {
                throw new DataAccessException("An exception happens when doing audit", ame);
            } catch (com.topcoder.timetracker.project.DataAccessException dae) {
                throw new DataAccessException("An exception happens when adding service details", dae);
            } catch (com.topcoder.timetracker.user.DataAccessException dae) {
                throw new DataAccessException("An exception happens when adding service details", dae);
            } catch (DBConnectionException e) {
                throw new DataAccessException("An exception happens when creating connection", e);
            } catch (IDGenerationException e) {
                throw new DataAccessException("An exception happens when generating id", e);
            } catch (SQLException e) {
                throw new DataAccessException("An exception happens when accessing database", e);
            } finally {
                DBUtil.closeConnection(connection);
                DBUtil.closeStatement(insertStatement);
            }
        }
    }

    /**
     * Checks the result of batch operation.
     *
     * @param details
     *            array of service details
     * @param is
     *            array of result
     * @throws BatchExecutionException
     *             in there is some problem with the batch operation
     */
    private void checkBatchError(InvoiceServiceDetail[] details, int[] is) throws BatchExecutionException {
        // checks if there are errors in the batch operation
        List errors = new ArrayList();
        for (int i = 0; i < is.length; i++) {
            if (is[i] == 0) {
                errors.add(new Integer(i));
            }
        }

        if (errors.size() > 0) {
            long[] thrown = new long[errors.size()];
            for (int i = 0; i < errors.size(); i++) {
                thrown[i] = details[((Integer) errors.get(i)).intValue()].getId();
            }
            throw new BatchExecutionException(thrown);
        }
    }

    /**
     * <p>
     * This method is used to delete array of InvoiceServiceDetail instances from data store. It also provide
     * ability to audit operation(if audit parameter is true).
     * </p>
     *
     * @param ids
     *            he given array of id of <code>InvoceServiceDetail</code>s
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws IllegalArgumentException
     *             if details parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void deleteServiceDetails(long[] ids, boolean audit) throws DataAccessException {
        ArgumentCheckUtil.checkNotNull("ids", ids);

        if (!useBatch) {
            Connection connection = null;
            try {
                connection = connectionFactory.createConnection();
                for (int i = 0; i < ids.length; i++) {
                    deleteServiceDetail(connection, ids[i], audit);
                }
            } catch (DBConnectionException e) {
                throw new DataAccessException("An exception happens when creating connection", e);
            } finally {
                DBUtil.closeConnection(connection);
            }
        } else {

            Connection connection = null;
            PreparedStatement deleteStatement = null;
            try {
                connection = connectionFactory.createConnection();

                deleteStatement =
                    connection.prepareStatement("DELETE FROM service_details WHERE service_detail_id = ?");

                for (int i = 0; i < ids.length; i++) {

                    AuditDetail[] auditDetails = null;
                    if (audit) {
                        auditDetails = buildAuditDetail(ids[i], connection);
                    }

                    deleteStatement.setLong(1, ids[i]);
                    deleteStatement.addBatch();

                    if (audit) {
                        AuditHeader header = new AuditHeader();
                        header.setEntityId(ids[i]);
                        header.setTableName("service_details");
//                        header.setCompanyId(ids[i]);
                        header.setActionType(AuditType.DELETE);
                        header.setApplicationArea(ApplicationArea.TT_INVOICE);
//                        header.setResourceId(ids[i]);
                        if (auditDetails[6] != null) {
                            header.setCreationUser(auditDetails[6].getOldValue());
                        }

                        header.setDetails(auditDetails);
                        auditManager.createAuditRecord(header);
                    }
                }

                int[] is = deleteStatement.executeBatch();

                // checks if there are errors in the batch operation
                List errors = new ArrayList();
                for (int i = 0; i < is.length; i++) {
                    if (is[i] == 0) {
                        errors.add(new Integer(i));
                    }
                }

                if (errors.size() > 0) {
                    long[] thrown = new long[errors.size()];
                    for (int i = 0; i < errors.size(); i++) {
                        thrown[i] = ids[((Integer) errors.get(i)).intValue()];
                    }
                    throw new BatchExecutionException(thrown);
                }
            } catch (AuditManagerException ame) {
                throw new DataAccessException("An exception happens when doing audit", ame);
            } catch (DBConnectionException e) {
                throw new DataAccessException("An exception happens when creating connection", e);
            } catch (SQLException e) {
                throw new DataAccessException("An exception happens when accessing database", e);
            } finally {
                DBUtil.closeConnection(connection);
                DBUtil.closeStatement(deleteStatement);
            }
        }
    }

    /**
     * <p>
     * This method is used to remove InvoiceServiceDetail instance from data store by its id. It also provide
     * ability to audit operation (if audit parameter is true).
     * </p>
     *
     * @param connection
     *            the given connection
     * @param id
     *            the given id of <code>InvoceServiceDetail</code>
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     */
    private void deleteServiceDetail(Connection connection, long id, boolean audit) throws DataAccessException {
        Statement statement = null;
        try {
            AuditDetail[] auditDetails = null;
            if (audit) {
                auditDetails = buildAuditDetail(id, connection);
            }

            statement = connection.createStatement();
            statement.execute("DELETE FROM service_details WHERE service_detail_id =" + id);

            if (statement.getUpdateCount() == 0) {
                throw new EntityNotFoundException(id);
            }

            if (audit) {
                AuditHeader header = new AuditHeader();
                header.setEntityId(id);
                header.setTableName("service_details");
//                header.setCompanyId(id);
                header.setActionType(AuditType.DELETE);
                header.setApplicationArea(ApplicationArea.TT_INVOICE);
//                header.setResourceId(id);
                header.setCreationUser(auditDetails[6].getOldValue());

                header.setDetails(auditDetails);
                auditManager.createAuditRecord(header);
            }
        } catch (AuditManagerException ame) {
            throw new DataAccessException("An exception happens when doing audit", ame);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } finally {
            DBUtil.closeStatement(statement);
        }
    }

    /**
     * <p>
     * This method is used to update array of InvoiceServiceDetail instances in data store. It also provide ability
     * to audit operation(if audit parameter is true). Method should ignore value amount stored in given details,
     * it should calculate it as multiply of rate and hours form timeEntry attribute of details.
     * </p>
     *
     * @param details
     *            array of given <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation is audited or not
     *
     * @throws IllegalArgumentException
     *             if details parameter is null or its contains null elements
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void updateServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException {
        ArgumentCheckUtil.checkNotNull("details", details);
        ArgumentCheckUtil.checkNotContainsNull("details", details);

        if (!useBatch) {
            Connection connection = null;
            try {
                connection = connectionFactory.createConnection();
                for (int i = 0; i < details.length; i++) {
                    updateServiceDetail(connection, details[i], audit);
                }
            } catch (AuditManagerException ame) {
                throw new DataAccessException("An exception happens when doing audit", ame);
            } catch (com.topcoder.timetracker.project.DataAccessException dae) {
                throw new DataAccessException("An exception happens when updating service details", dae);
            } catch (com.topcoder.timetracker.user.DataAccessException dae) {
                throw new DataAccessException("An exception happens when updating service details", dae);
            } catch (DBConnectionException e) {
                throw new DataAccessException("An exception happens when creating connection", e);
            } finally {
                DBUtil.closeConnection(connection);
            }
        } else {
            Connection connection = null;
            PreparedStatement updateStatement = null;
            ResultSet set = null;
            Statement statement = null;
            try {
                connection = connectionFactory.createConnection();

                updateStatement = connection.prepareStatement(UPDATE_QUERY);

                for (int i = 0; i < details.length; i++) {
                    checkDetail(details[i]);

                    Date now = new Date(Calendar.getInstance().getTimeInMillis());
                    details[i].setModificationDate(now);

                    if (details[i].isChanged()) {
                        calculateAmount(details[i], false);
                    }

                    if (audit) {
                        statement = connection.createStatement();
                        set =
                            statement.executeQuery("SELECT * FROM service_details WHERE service_detail_id="
                                + details[i].getId());

                        if (set.next()) {
                            auditManager.createAuditRecord(createAuditHeader(set, details[i], now));
                        }

                    }

                    updateStatement.setLong(1, details[i].getTimeEntry().getId());
                    updateStatement.setLong(2, details[i].getInvoice().getId());
                    updateStatement.setInt(3, details[i].getRate());
                    updateStatement.setBigDecimal(4, details[i].getAmount());
                    updateStatement.setDate(5, now);
                    updateStatement.setString(6, details[i].getModificationUser());
                    updateStatement.setLong(7, details[i].getId());

                    updateStatement.addBatch();
                }
                int[] is = updateStatement.executeBatch();

                checkBatchError(details, is);

                for (int i = 0; i < details.length; ++i) {
                    details[i].setChanged(false);
                }
            } catch (AuditManagerException ame) {
                throw new DataAccessException("An exception happens when doing audit", ame);
            } catch (com.topcoder.timetracker.project.DataAccessException dae) {
                throw new DataAccessException("An exception happens when updating service details", dae);
            } catch (com.topcoder.timetracker.user.DataAccessException dae) {
                throw new DataAccessException("An exception happens when updating service details", dae);
            } catch (DBConnectionException e) {
                throw new DataAccessException("An exception happens when creating connection", e);
            } catch (SQLException e) {
                throw new DataAccessException("An exception happens when accessing database", e);
            } finally {
                DBUtil.closeConnection(connection);
                DBUtil.closeStatement(updateStatement);
                DBUtil.closeStatement(statement);
                DBUtil.closeResultSet(set);
            }
        }
    }

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instances from data store by its ids given in array.
     * </p>
     *
     * @param ids
     *            the given array of id of <code>InvoceServiceDetail</code>s
     *
     * @return the <code>InvoiceServiceDetail</code>s with the given ids
     *
     * @throws IllegalArgumentException
     *             if details parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException (may be problems with connection or with
     *             TimeEntry component)
     * @throws EntityNotFoundException
     *             should be thrown if some detail not found in database.
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long[] ids) throws DataAccessException {
        ArgumentCheckUtil.checkNotNull("details", ids);
        if (ids.length == 0) {
            return new InvoiceServiceDetail[0];
        }

        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            StringBuffer query = new StringBuffer("SELECT * FROM service_details WHERE ");
            for (int i = 0; i < ids.length; i++) {
                query.append("service_detail_id = " + ids[i]);

                if (i != ids.length - 1) {
                    query.append(" OR ");
                }
            }

            connection = connectionFactory.createConnection();
            // select all service ids from service_detail table
            statement = connection.createStatement();
            set = statement.executeQuery(query.toString());

            List list = new ArrayList();

            while (set.next()) {
                list.add(transformResultSetToServiceDetail(set));
            }

            if (list.size() != ids.length) {
                for (int i = 0; i < ids.length; i++) {
                    boolean found = false;
                    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                        InvoiceServiceDetail serviceDetail = (InvoiceServiceDetail) iterator.next();
                        if (serviceDetail.getId() == ids[i]) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        throw new EntityNotFoundException(ids[i]);
                    }
                }
            }

            return (InvoiceServiceDetail[]) list.toArray(new InvoiceServiceDetail[list.size()]);

        } catch (DBConnectionException e) {
            throw new DataAccessException("An exception happens when creating connection", e);
        } catch (SQLException e) {
            throw new DataAccessException("An exception happens when accessing database", e);
        } catch (UnrecognizedEntityException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } catch (com.topcoder.timetracker.entry.time.DataAccessException e) {
            throw new DataAccessException("An exception happens when getting time entry", e);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeStatement(statement);
            DBUtil.closeResultSet(set);
        }
    }
}
