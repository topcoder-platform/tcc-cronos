/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.ejb;

import javax.ejb.EJBLocalObject;

import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.InvalidDataException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

/**
 * <p>
 * This represent local interface for ServiceDetailBean. This interface will be looked up by ServiceDetailManager
 * implementation. This extends from EJBLocalObject interface.
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public interface LocalServiceDetail extends EJBLocalObject {

    /**
     * <p>
     * This method is used to add InvoiceServiceDetail instance to data store. It also provide ability to audit
     * operation(if audit parameter is true). This method run under container transaction, which is configured by
     * Required level.
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
     *             if detail data is incorrect, e.g. impossible to insert in data store]]
     */
    public void addServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to remove InvoiceServiceDetail instance from data store by its id. It also provide
     * ability to audit operation(if audit parameter is true). This method run under container transaction, which
     * is configured by Required level.
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
     */
    public void deleteServiceDetail(long id, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to delete all InvoiceServiceDetail instances from data store. It also provide ability to
     * audit operation(if audit parameter is true). This method run under container transaction, which is
     * configured by Required level.
     * </p>
     *
     * @param audit
     *            whether the operation should be audited or not
     *
     * @throws DataAccessException
     *             if appears problem with data store
     */
    public void deleteAllServiceDetails(boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to update InvoiceServiceDetail instance in data store. It also provide ability to audit
     * operation(if audit parameter is true). This method run under container transaction, which is configured by
     * Required level.
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
     *             if detail data is incorrect, e.g. impossible to insert in data store]
     */
    public void updateServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instance from data store by its id. This method run
     * under container transaction, which is configured by Required level.
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
     */
    public InvoiceServiceDetail retrieveServiceDetail(long id) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instances from data store by invoice id. This method
     * run under container transaction, which is configured by Required level.
     * </p>
     *
     * @param invoiceId
     *            the given id of <code>Invoice</code>
     *
     * @return the <code>InvoiceServiceDetail</code>s with the given invoiceId
     *
     * @throws DataAccessException
     *             if appears problem with data store
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long invoiceId) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve all InvoiceServiceDetail instances. This method run under container
     * transaction, which is configured by Required level.
     * </p>
     *
     * @return all <code>InvoiceServiceDetail</code>s
     *
     * @throws DataAccessException
     *             if appears problem with data store
     */
    public InvoiceServiceDetail[] retrieveAllServiceDetails() throws DataAccessException;

    /**
     * <p>
     * This method is used to add array of InvoiceServiceDetail instances to data store. It also provide ability to
     * audit operation(if audit parameter is true). This method run under container transaction, which is
     * configured by Required level.
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
     *             if detail data is incorrect, e.g. impossible to insert in data store]
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void addServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve all InvoiceServiceDetail instances. This method run under container
     * transaction, which is configured by Required level.
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
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void deleteServiceDetails(long[] ids, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to update array of InvoiceServiceDetail instances in data store. It also provide ability
     * to audit operation(if audit parameter is true). This method run under container transaction, which is
     * configured by Required level.
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
     *             if detail data is incorrect, e.g. impossible to insert in data store]
     * @throws BatchExecutionException
     *             if execution of batch fails
     */
    public void updateServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve all InvoiceServiceDetail instances. This method run under container
     * transaction, which is configured by Required level.
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
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long[] ids) throws DataAccessException;
}
