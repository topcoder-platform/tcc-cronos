/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.ejb;

import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.dao.ServiceDetailDAO;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * <p>
 * This class is used to provide transactions. It simply gets ServiceDetailDAO from manager and run its
 * corresponding method. If executing is failed it simply use method setRollbackOnly() to notify container that it
 * should be rollback. Class contains as attribute ServiceDetailDAO, which is initialized in method ejbCreate().
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class ServiceDetailBean implements SessionBean {

    /** Serial version UID. */
    private static final long serialVersionUID = 5329479449486963411L;

    /**
     * This attributes is used to save session context in current bean. As bean is stateless it will be set up only
     * once - during creation of component. This is mutable, sets by corresponding setter methods, default value -
     * null.
     */
    private SessionContext context = null;

    /**
     * Represents instance of ServiceDetailDAO. It is used in each method of bean to provide operation with
     * database. This is mutable, sets by ejbCreate() only once, null sets as default but after creating of bean
     * null is not allowed.
     */
    private ServiceDetailDAO dao = null;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public ServiceDetailBean() {
        // nothing to do
    }

    /**
     * <p>
     * Creates the bean and initialized the dao attribute.
     * </p>
     *
     * @throws EJBException
     *             to indicate a failure caused by a system-level error
     */
    public void ejbCreate() {
        try {
            InitialContext c = new InitialContext();
            String factoryNamespace = (String) c.lookup("java:comp/env/factory_namespace");

            String daoName = (String) c.lookup("java:comp/env/dao_name");

            SpecificationFactory specificationFactory = new ConfigManagerSpecificationFactory(factoryNamespace);

            ObjectFactory objectFactory = new ObjectFactory(specificationFactory);

            dao = (ServiceDetailDAO) objectFactory.createObject(daoName);

        } catch (NamingException e) {
            throw new EJBException("Error in getting the environment variable", e);
        } catch (SpecificationConfigurationException e) {
            throw new EJBException("Error in factory creation", e);
        } catch (IllegalReferenceException e) {
            throw new EJBException("Error in factory creation", e);
        } catch (InvalidClassSpecificationException e) {
            throw new EJBException("Error in DAO object creation", e);
        } catch (ClassCastException e) {
            throw new EJBException("Object referenced is not the right type", e);
        }
    }

    /**
     * <p>
     * This method is invoked by the container when it wants to remove the instance. For current realization is
     * empty.
     * </p>
     *
     * @throws EJBException
     *             to indicate a failure caused by a system-level error
     */
    public void ejbRemove() {
        // nothing to do
    }

    /**
     * <p>
     * This method is invoked by the container when it wants to activate the instance. For current realization is
     * empty.
     * </p>
     *
     * @throws EJBException
     *             to indicate a failure caused by a system-level error
     */
    public void ejbActivate() {
        // nothing to do
    }

    /**
     * <p>
     * This method is invoked by the container when it wants to passivate the instance. For current realization is
     * empty.
     * </p>
     *
     * @throws EJBException
     *             to indicate a failure caused by a system-level error
     */
    public void ejbPassivate() {
        // nothing to do
    }

    /**
     * <p>
     * This method is used by the container to pass a reference to the SessionContext to the bean instance. Simply
     * set context attribute
     * </p>
     *
     * @param context
     *            SessionContext instance, null impossible
     *
     * @throws EJBException
     *             to indicate a failure caused by a system-level error
     */
    public void setSessionContext(SessionContext context) {
        this.context = context;
    }

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
     *             if detail data is incorrect, e.g. impossible to insert in data store]
     */
    public void addServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException {
        try {
            dao.addServiceDetail(detail, audit);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public void deleteServiceDetail(long id, boolean audit) throws DataAccessException {
        try {
            dao.deleteServiceDetail(id, audit);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public void deleteAllServiceDetails(boolean audit) throws DataAccessException {
        try {
            dao.deleteAllServiceDetails(audit);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public void updateServiceDetail(InvoiceServiceDetail detail, boolean audit) throws DataAccessException {
        try {
            dao.updateServiceDetail(detail, audit);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public InvoiceServiceDetail retrieveServiceDetail(long id) throws DataAccessException {
        try {
            return dao.retrieveServiceDetail(id);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public InvoiceServiceDetail[] retrieveServiceDetails(long invoiceId) throws DataAccessException {
        try {
            return dao.retrieveServiceDetails(invoiceId);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public InvoiceServiceDetail[] retrieveAllServiceDetails() throws DataAccessException {
        try {
            return dao.retrieveAllServiceDetails();
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public void addServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException {
        try {
            dao.addServiceDetails(details, audit);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This method is used to delete array of InvoiceServiceDetail instances from data store. It also provide
     * ability to audit operation(if audit parameter is true). This method run under container transaction, which
     * is configured by Required level.
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
    public void deleteServiceDetails(long[] ids, boolean audit) throws DataAccessException {
        try {
            dao.deleteServiceDetails(ids, audit);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

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
    public void updateServiceDetails(InvoiceServiceDetail[] details, boolean audit) throws DataAccessException {
        try {
            dao.updateServiceDetails(details, audit);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This method is used to retrieve InvoiceServiceDetail instances from data store by its ids given in array.
     * This method run under container transaction, which is configured by Required level.
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
     *             should be thrown if some detail  not found in database.]
     */
    public InvoiceServiceDetail[] retrieveServiceDetails(long[] ids) throws DataAccessException {
        try {
            return dao.retrieveServiceDetails(ids);
        } catch (DataAccessException e) {
            context.setRollbackOnly();
            throw e;
        }
    }
}
