/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

/**
 * <p>
 * This interface defines all operation which may be done on service_detail table. Operations like adding,
 * removing, updating and retrieving entry or group of entries. This interface will be used by user directly and it
 * represents all functionality on service details.
 * </p>
 * <p>
 * Classes which will implement this interface should not be thread safe.
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public interface ServiceDetailManager {

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
    public void addServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException,
        TransactionCreationException;

    /**
     * <p>
     * This method is used to remove InvoiceServiceDetail instance from data store by its id. It also provide
     * ability to audit operation(if audit parameter is true).
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
        TransactionCreationException;

    /**
     * <p>
     * This method is used to delete all InvoiceServiceDetail instances from data store. It also provide ability to
     * audit operation(if audit parameter is true).
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
    public void deleteAllServiceDetails(boolean audit) throws DataAccessException, TransactionCreationException;

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
        TransactionCreationException;

    /**
     * <p>
     * This method is used to delete all InvoiceServiceDetail instances from data store. It also provide ability to
     * audit operation(if audit parameter is true).
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
        TransactionCreationException;

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
        TransactionCreationException;

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
        TransactionCreationException;

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
        TransactionCreationException;

    /**
     * <p>
     * This method is used to delete array of InvoiceServiceDetail instances from data store. It also provide
     * ability to audit operation(if audit parameter is true).
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
        TransactionCreationException;

    /**
     * <p>
     * This method is used to update array of InvoiceServiceDetail instances in data store. It also provide ability
     * to audit operation(if audit parameter is true). Method should ignore value amount stored in given details,
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
        TransactionCreationException;

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
        TransactionCreationException;

}
