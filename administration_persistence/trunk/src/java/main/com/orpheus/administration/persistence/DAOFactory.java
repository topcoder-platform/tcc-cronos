/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * <p>Static factory for supplying the DAO instances to the EJBs. It uses synchronized lazy instantiation to get the
 * initial instance of each DAO. Supports the creation of the {@link AdminDataDAO AdminDataDAO} and {@link
 * MessageDAO MessageDAO}.
 *
 * <p>This class has three required configuration parameters.
 *
 * <ul>
 *   <li><strong>specNamespace</strong>: the object factory specification namespace</li>
 *   <li><strong>adminDataDAO</strong>: the object factory key to use to instantiate the <code>AdminDataDAO</code></li>
 *   <li><strong>messageDAO</strong>: the object factory key to use to instantiate the <code>MessageDAO</code></li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This object is effectively immutable and thread-safe, by virtue of being
 * sycnhrhonized.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class DAOFactory {
    /**
     * <p>Represents the namespace used to retrieve the DAO instances in the accessor methods.</p>
     */
    public static final String NAMESPACE = "com.orpheus.administration.persistence.DAOFactory";

    /**
     * <p>Represents the <code>AdminDataDAO</code> instance. Created when the instance is first requested, and will
     * not change or be <code>null</code> after that.</p>
     */
    private static AdminDataDAO adminDataDAO;

    /**
     * <p>Represents the <code>MessageDAO</code> instance. Created when the instance is first requested, and will
     * not change or be <code>null</code> after that.</p>
     */
    private static MessageDAO messageDAO;

    /**
     * Private constructor to prevent instantiation.
     */
    private DAOFactory() {
    }

    /**
     * <p>Obtains the <code>AdminDataDAO</code> instance. If it does not exist yet, it will be created using the
     * object factory. Synchronized to avoid threading issues with lazy instantiation. See the class documentation
     * for a description of the configuration parameters.</p>
     *
     * @return the <code>AdminDataDAO</code> instance
     * @throws InstantiationException if the namespace is missing or does not contain a required property or the
     *   object cannot be instantiated
     */
    public static synchronized AdminDataDAO getAdminDataDAO() throws InstantiationException {
        if (adminDataDAO == null) {
            adminDataDAO = (AdminDataDAO) ObjectFactoryHelpers.instantiateObject(NAMESPACE, "specNamespace",
                                                                                 "adminDataDAO", AdminDataDAO.class);
        }

        return adminDataDAO;
    }

    /**
     * <p>Obtains the <code>MessageDAO</code> instance. If it does not exist yet, it will be created using the
     * object factory. Synchronized to avoid threading issues with lazy instantiation.</p>
     *
     * @return the <code>MessageDAO</code> instance
     * @throws InstantiationException if the namespace is missing or does not contain a required property or the
     *   object cannot be instantiated
     */
    public static synchronized MessageDAO getMessageDAO() throws InstantiationException {
        if (messageDAO == null) {
            messageDAO = (MessageDAO) ObjectFactoryHelpers.instantiateObject(NAMESPACE, "specNamespace",
                                                                             "messageDAO", MessageDAO.class);
        }

        return messageDAO;
    }
}
