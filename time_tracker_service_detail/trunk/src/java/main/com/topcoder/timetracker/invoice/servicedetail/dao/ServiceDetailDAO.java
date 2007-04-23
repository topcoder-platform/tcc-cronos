/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.dao;

import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

/**
 * <p>
 * This interface defines all operation which may be done on service_detail table. Operations like adding,
 * removing, updating and retrieving entry or group of entries.
 * </p>
 * <p>
 * Classes which will implement this interface may not be thread safe.
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public interface ServiceDetailDAO {

    /**
     * <p>
     * This method is used to add InvoiceServiceDetail instance to data store. It also provide ability to audit
     * operation(if audit parameter is true). Method should ignore values of rate and amount stored in given
     * detail, it should get rate from project worker and amount calculate as multiply of rate and hours form
     * timeEntry attribute of detail.
     * </p>
     *
     * @param detail
     *            the detail given
     * @param audit
     *            whether the audit should be done or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     */
    public void addServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to remove InvoiceServiceDetail instance from data store by its id. It also provide
     * ability to audit operation(if audit parameter is true).
     * </p>
     *
     * @param id
     *            the given id of the <code>InvoceServiceDetail</code> that wants to be deleted
     * @param audit
     *            whether the audit should be done or not
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     */
    public void deleteServiceDetail(long id, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to delete all InvoiceServiceDetail instances from data store. It also provide ability to
     * audit operation(if audit parameter is true).
     * </p>
     *
     * @param audit
     *            whether the audit should be done or not
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     */
    public void deleteAllServiceDetails(boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to update InvoiceServiceDetail instance in data store. It also provide ability to audit
     * operation(if audit parameter is true). Method should ignore value amount stored in given detail, it should
     * calculate it as multiply of rate and hours form timeEntry attribute of detail.
     * </p>
     *
     * @param detail
     *            the given <code>InvoceServiceDetail</code>
     * @param audit
     *            whether the audit should be done or not
     *
     * @throws IllegalArgumentException
     *             if detail parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection)
     * @throws InvalidDataException
     *             should be thrown if detail data will be incorrect, e.g. impossible add them to database table
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     */
    public void updateServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instance from data store by its id.
     * </p>
     *
     * @param id
     *            the given invoice service detail id
     *
     * @return the <code>InvoceServiceDetail</code> with has id as its id
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection or with
     *             TimeEntry component)
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     */
    public InvoiceServiceDetail retrieveServiceDetail(long id) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instances from data store by invoice id.
     * </p>
     *
     * @param invoiceId
     *            the given invoice id
     *
     * @return array of <code>InvoceServiceDetail</code>s that have invoiceId as the invoice
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException (may be problems with connection or with
     *             TimeEntry component)
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long invoiceId) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve all InvoiceServiceDetail instances.
     * </p>
     *
     * @return array of all <code>InvoceServiceDetail</code>
     *
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection or with
     *             TimeEntry component)
     */
    public InvoiceServiceDetail[] retrieveAllServiceDetails() throws DataAccessException;

    /**
     * <p>
     * This method is used to add array of InvoiceServiceDetail instances to data store. It also provide ability to
     * audit operation(if audit parameter is true). Method should ignore values of rate and amount stored in given
     * details, it should get rate from project worker and amount calculate as multiply of rate and hours form
     * timeEntry attribute of detail.
     * </p>
     *
     * @param details
     *            array of <code>InvoceServiceDetail</code>s
     * @param audit
     *            whether the audit should be done or not
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
    public void addServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to delete array of InvoiceServiceDetail instances from data store. It also provide
     * ability to audit operation(if audit parameter is true).
     * </p>
     *
     * @param ids
     *            array of id of <code>InvoceServiceDetail</code>s
     * @param audit
     *            whether the audit should be done or not
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
    public void deleteServiceDetails(long[] ids, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to update array of InvoiceServiceDetail instances in data store. It also provide ability
     * to audit operation(if audit parameter is true). Method should ignore value amount stored in given details,
     * it should calculate it as multiply of rate and hours form timeEntry attribute of details.
     * </p>
     *
     * @param details
     *            array of <code>InvoceServiceDetail</code>s
     * @param audit
     *            whether the audit should be done or not
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
    public void updateServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instances from data store by its ids given in array.
     * </p>
     *
     * @param ids
     *            the given array of service detail ids
     *
     * @return an array of <code>InvoceServiceDetail</code> with the given ids
     *
     * @throws IllegalArgumentException
     *             if details parameter is null
     * @throws DataAccessException
     *             should be thrown if it will be caught SQLException(may be problems with connection or with
     *             TimeEntry component)
     * @throws EntityNotFoundException
     *             should be thrown if some detail  not found in database.
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long[] ids) throws DataAccessException;
}
