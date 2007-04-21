/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This is a class that acts as a factory for the <code>InvoiceDAO</code> and <code>InvoiceStatusDAO</code>,
 * and may be used to easily access the dao s class for various purposes. It uses lazy instantiation to create the
 * dao implementations. Instantiation is done through the Object Factory. This class is used by the SessionBean to
 * delegate the work to.
 * </p>
 * <p>
 * Thread Safety: The methods should be synchronized to ensure only a single instance of the manager is
 * instantiated. The DAOs themselves are thread-safe since it is required by the InvoiceDAO interface and
 * InvoiceStatusDAO interface.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceDAOFactory {

    /**
     * <p>
     * This is the InvoiceDAO that will be used. It is null until it has been accessed. It will be initialized by a
     * call to ObjectFactory.createObject.
     * </p>
     * <p>
     * Initial Value: From getInvoiceDAO
     * </p>
     * <p>
     * Accessed In: getInvoiceDAO
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: Not utilized
     * </p>
     * <p>
     * Valid Values: Possibly null InvoiceDAO instances.
     * </p>
     */
    private static InvoiceDAO invoiceDAO;

    /**
     * <p>
     * This is the InvoiceStatusDAO that will be used. It is null until it has been accessed. It will be
     * initialized by a call to ObjectFactory.createObject.
     * </p>
     * <p>
     * Initial Value: From getInvoiceStatusDAO
     * </p>
     * <p>
     * Accessed In: getInvoiceStatusDAO
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: Not utilized
     * </p>
     * <p>
     * Valid Values: Possibly null InvoiceStatusDAO instances.
     * </p>
     */
    private static InvoiceStatusDAO invoiceStatusDAO;

    /**
     * <p>
     * Private Constructor.
     * </p>
     */
    private InvoiceDAOFactory() {
        // nothing to do
    }

    /**
     * <p>
     * Retrieves an instance of InvoiceDAO. If it has not yet been initialized, then an instance will be created.
     * </p>
     * <p>
     * Thread Safety: Synchronization should be used to ensure only a single instance is created.
     * </p>
     *
     * @return the <code>InvoiceDAO</code>
     *
     * @throws InvoiceConfigurationException
     *             if any configured value is invalid or any required value is missing, it is also used to wrap the
     *             exceptions from ConfigManager
     */
    public static synchronized InvoiceDAO getInvoiceDAO() throws InvoiceConfigurationException {
        if (invoiceDAO == null) {
            invoiceDAO = (InvoiceDAO) getObject("invoiceDAOKey", InvoiceDAO.class);
        }
        return invoiceDAO;
    }

    /**
     * Gets object from object factory.
     *
     * @param propertyKey
     *            property key of the object
     * @param intendedClass
     *            the intended class
     *
     * @return the object
     *
     * @throws InvoiceConfigurationException
     *             if there is a configuration error
     */
    private static Object getObject(String propertyKey, Class intendedClass) throws InvoiceConfigurationException {
        String namespace = InvoiceDAOFactory.class.getName();
        ObjectFactory objectFactory =
            ConfigUtil.createObjectFactory(ConfigUtil.getRequiredProperty(namespace, "objectFactoryNamespace"));
        return ConfigUtil.createObject(objectFactory, namespace, propertyKey, intendedClass);
    }

    /**
     * <p>
     * Retrieves an instance of InvoiceStatusDAO. If it has not yet been initialized, then an instance will be
     * created.
     * </p>
     * <p>
     * Thread Safety: Synchronization should be used to ensure only a single instance is created.
     * </p>
     *
     * @return the <code>InvoiceStatusDAO</code>
     *
     * @throws InvoiceConfigurationException
     *             if any configured value is invalid or any required value is missing, it is also used to wrap the
     *             exceptions from ConfigManager
     */
    public static synchronized InvoiceStatusDAO getInvoiceStatusDAO() throws InvoiceConfigurationException {
        if (invoiceStatusDAO == null) {
            invoiceStatusDAO = (InvoiceStatusDAO) getObject("invoiceStatusDAOKey", InvoiceStatusDAO.class);
        }
        return invoiceStatusDAO;
    }

    /**
     * Clears the cached DAOs.
     */
    public static synchronized void clear() {
        invoiceDAO = null;
        invoiceStatusDAO = null;
    }
}
