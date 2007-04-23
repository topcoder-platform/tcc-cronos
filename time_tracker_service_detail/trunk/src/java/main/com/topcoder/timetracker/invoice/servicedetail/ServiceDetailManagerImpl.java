/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetailHome;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This is default realization of ServiceDetailManager interface. This class realized J2EE patterns Business
 * Delegate and Service Locator. Each method of this class look up for LocalServiceDetail by using JNDIUtils and
 * run its corresponding method.
 * </p>
 * <p>
 * Class is not thread safe, because it is immutable.
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class ServiceDetailManagerImpl implements ServiceDetailManager {

    /**
     * This attribute represents default namespace. This is immutable, static, sets by default.
     */
    private static final String DEFAULT_NAMESPACE = "com.topcoder.timetracker.invoice.servicedetail.impl";

    /**
     * Represents connection string which is used in getService() method to look up session bean. This is
     * immutable, sets by constructor, null impossible or empty string is impossible.
     */
    private final String connectionString;

    /**
     * <p>
     * This constructor is used to set connectionString attribute.
     * </p>
     *
     * @throws ConfigurationException
     *             if configuration process failed
     */
    public ServiceDetailManagerImpl() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * This constructor is used to set connectionString attribute.
     * </p>
     *
     * @param namespace
     *            the given namespace
     *
     * @throws IllegalArgumentException
     *             if namespace is null or empty String
     * @throws ConfigurationException
     *             if configuration process failed
     */
    public ServiceDetailManagerImpl(String namespace) throws ConfigurationException {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("namespace", namespace);

        try {
            connectionString = ConfigManager.getInstance().getString(namespace, "connection_string");
            if (ArgumentCheckUtil.isNullOrEmptyString(connectionString)) {
                throw new ConfigurationException("The connection_string should not be null or an empty string");
            }
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Can't found namespace " + namespace, e);
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
     * @param detail
     *            the given <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws InvalidDataException
     *             if detail data is incorrect, e.g. impossible to insert in data store]
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public void addServiceDetail(InvoiceServiceDetail detail, boolean audit) throws TransactionCreationException,
        DataAccessException {
        ArgumentCheckUtil.checkNotNull("detail", detail);

        getService().addServiceDetail(detail, audit);
    }

    /**
     * <p>
     * This method is used to remove InvoiceServiceDetail instance from data store by its id. It also provide
     * ability to audit operation (if audit parameter is true).
     * </p>
     *
     * @param id
     *            the given id of <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public void deleteServiceDetail(long id, boolean audit) throws DataAccessException,
        TransactionCreationException {
        getService().deleteServiceDetail(id, audit);
    }

    /**
     * <p>
     * This method is used to delete all InvoiceServiceDetail instances from data store. It also provide ability to
     * audit operation (if audit parameter is true).
     * </p>
     *
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public void deleteAllServiceDetails(boolean audit) throws DataAccessException, TransactionCreationException {
        getService().deleteAllServiceDetails(audit);
    }

    /**
     * <p>
     * This method is used to update InvoiceServiceDetail instance in data store. It also provide ability to audit
     * operation (if audit parameter is true). Method should ignore value amount stored in given detail, it should
     * calculate it as multiply of rate and hours form timeEntry attribute of detail.
     * </p>
     *
     * @param detail
     *            the given <code>InvoiceServiceDetail</code>
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws InvalidDataException
     *             if detail data is incorrect, e.g. impossible to insert in data store
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public void updateServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException,
        TransactionCreationException {
        ArgumentCheckUtil.checkNotNull("detail", detail);

        getService().updateServiceDetail(detail, audit);
    }

    /**
     * <p>
     * This method is used to delete all InvoiceServiceDetail instances from data store. It also provide ability to
     * audit operation (if audit parameter is true).
     * </p>
     *
     * @param id
     *            the given id of <code>InvoiceServiceDetail</code>
     *
     * @return the <code>InvoiceServiceDetail</code> with the given id
     *
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public InvoiceServiceDetail retrieveServiceDetail(long id) throws DataAccessException,
        TransactionCreationException {
        return getService().retrieveServiceDetail(id);
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
     *             if appears problem with data store
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long invoiceId) throws DataAccessException,
        TransactionCreationException {
        return getService().retrieveServiceDetails(invoiceId);
    }

    /**
     * <p>
     * This method is used to retrieve all InvoiceServiceDetail instances.
     * </p>
     *
     * @return all <code>InvoiceServiceDetail</code>s
     *
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public InvoiceServiceDetail[] retrieveAllServiceDetails() throws DataAccessException,
        TransactionCreationException {
        return getService().retrieveAllServiceDetails();
    }

    /**
     * <p>
     * This method is used to add array of InvoiceServiceDetail instances to data store. It also provide ability to
     * audit operation (if audit parameter is true). Method should ignore values of rate and amount stored in given
     * details, it should get rate from project worker and amount calculate as multiply of rate and hours form
     * timeEntry attribute of detail.
     * </p>
     *
     * @param details
     *            the given array of <code>InvoiceServiceDetail</code>s
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws IllegalArgumentException
     *             if details parameter is null or its contains null elements
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws InvalidDataException
     *             if detail data is incorrect, e.g. impossible to insert in data store
     * @throws TransactionCreationException
     *             if impossible to create transaction
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void addServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException,
        TransactionCreationException {
        ArgumentCheckUtil.checkNotNull("details", details);
        ArgumentCheckUtil.checkNotContainsNull("details", details);

        getService().addServiceDetails(details, audit);
    }

    /**
     * <p>
     * This method is used to delete array of InvoiceServiceDetail instances from data store. It also provide
     * ability to audit operation (if audit parameter is true).
     * </p>
     *
     * @param ids
     *            the given array of id of <code>InvoceServiceDetail</code>s
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws IllegalArgumentException
     *             if details parameter is null
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws TransactionCreationException
     *             if impossible to create transaction
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void deleteServiceDetails(long[] ids, boolean audit) throws DataAccessException,
        TransactionCreationException {
        ArgumentCheckUtil.checkNotNull("ids", ids);

        getService().deleteServiceDetails(ids, audit);
    }

    /**
     * <p>
     * This method is used to update array of InvoiceServiceDetail instances in data store. It also provide ability
     * to audit operation (if audit parameter is true). Method should ignore value amount stored in given details,
     * it should calculate it as multiply of rate and hours form timeEntry attribute of details.
     * </p>
     *
     * @param details
     *            the given array of <code>InvoiceServiceDetail</code>s
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws IllegalArgumentException
     *             if details parameter is null or its contains null elements
     * @throws DataAccessException
     *             if appears problem with data store
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws InvalidDataException
     *             if detail data is incorrect, e.g. impossible to insert in data store
     * @throws TransactionCreationException
     *             if impossible to create transaction
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void updateServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException,
        TransactionCreationException {
        ArgumentCheckUtil.checkNotNull("details", details);
        ArgumentCheckUtil.checkNotContainsNull("details", details);

        getService().updateServiceDetails(details, audit);
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
     *             if appears problem with data store
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long[] ids) throws DataAccessException,
        TransactionCreationException {
        ArgumentCheckUtil.checkNotNull("ids", ids);

        return getService().retrieveServiceDetails(ids);
    }

    /**
     * <p>
     * This method is used to get local interface of session bean which is responsible for implementing
     * transaction.
     * </p>
     *
     * @return LocalServiceDetail instance, null impossible
     *
     * @throws TransactionCreationException
     *             if impossible to create transaction
     */
    protected LocalServiceDetail getService() throws TransactionCreationException {
        try {
            return (LocalServiceDetail) ((LocalServiceDetailHome) JNDIUtils.getObject(connectionString)).create();
        } catch (NamingException e) {
            throw new TransactionCreationException("Can't retrieve the LocalServiceDetailHome from the JNDI", e);
        } catch (CreateException e) {
            throw new TransactionCreationException("Can't create the LocalServiceDetail", e);
        } catch (EJBException e) {
            throw new TransactionCreationException("Can't create the LocalServiceDetail", e);
        } catch (ClassCastException e) {
            throw new TransactionCreationException("The object is not the correct type", e);
        }
    }
}
