/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import com.orpheus.user.persistence.ejb.PendingConfirmationBean;
import com.orpheus.user.persistence.ejb.UserProfileBean;

/**
 * <p>
 * A static factory class that is used by the {@link PendingConfirmationBean}
 * and {@link UserProfileBean} session beans to create and obtain references to
 * the {@link PendingConfirmationDAO} and {@link UserProfileDAO} instances,
 * respectively. The DAO classes are instantiated lazily.
 * </p>
 * <p>
 * <b>Configuration:</b><br>
 * The creation of the DAO classes is performed using the Object Factory
 * component and is configurable. The configuration parameters are listed in the
 * table below. They should all be specified in the
 * "com.orpheus.user.persistence.DAOFactory" namespace.
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Property</b></td>
 * <td><b>Description</b></td>
 * <td><b>Required</b></td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>specNamespace</td>
 * <td>The ObjectFactory configuration namespace specifying the DAO object
 * creation. This namespace is passed to the
 * <code>ConfigManagerSpecificationFactory</code> class</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>pendingConfirmationDAO</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>PendingConfirmationDAO</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>userProfileDAO</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>PendingConfirmationDAO</code></td>
 * <td>Yes</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * <b>Thread-safety:</b><br> This class is immutable and has synchronized factory
 * methods. It is thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileDAO
 * @see PendingConfirmationDAO
 */
public class DAOFactory {

    /**
     * <p>
     * The namespace from which to read the DAOFactory configuration
     * information.
     * </p>
     */
    private static final String NAMESPACE = DAOFactory.class.getName();

    /**
     * <p>
     * The PendingConfirmationDAO instance which is used by the
     * PendingConfirmationBean.
     * </p>
     * <p>
     * This field is instantiated lazily in the getPendingConfirmationDAO()
     * method. After it is instantiated, it cannot be changed.
     * </p>
     */
    private static PendingConfirmationDAO pendingConfirmationDAO;

    /**
     * <p>
     * The UserProfileDAO instance which is used by the UserProfileBean.
     * </p>
     * <p>
     * This field is instantiated lazily in the getUserProfileDAO() method.
     * After it is instantiated, it cannot be changed.
     * </p>
     */
    private static UserProfileDAO userProfileDAO;

    /**
     * <p>
     * This constructor is private to prevent this class from being
     * instantiated.
     * </p>
     */
    private DAOFactory() {
        // Empty constructor.
    }

    /**
     * <p>
     * Gets the {@link PendingConfirmationDAO} instance which is used to persist
     * confirmation messages. This <code>PendingConfirmationDAO</code>
     * instance is created only when this method is called for the first time.
     * Every subsequent call to this method will simply return the already
     * created instance. If creating the instance fails, an
     * <code>ObjectInstantiationException</code> is thrown.
     * </p>
     * <p>
     * This method is <i>synchronized</i> to avoid the thread-safety issues
     * related to lazy instantiation
     * </p>
     *
     * @return the <code>PendingConfirmationDAO</code> instance
     * @throws ObjectInstantiationException if creating the
     *         <code>PendingConfirmationDAO</code> instance fails (usually due
     *         to a configuration error)
     */
    public static synchronized PendingConfirmationDAO getPendingConfirmationDAO() throws ObjectInstantiationException {
        if (pendingConfirmationDAO == null) {
            pendingConfirmationDAO = (PendingConfirmationDAO) createDAO("pendingConfirmationDAO",
                                                                        PendingConfirmationDAO.class);
        }
        return pendingConfirmationDAO;
    }

    /**
     * <p>
     * Gets the {@link UserProfileDAO} instance which is used to persist user
     * profiles. This <code>UserProfileDAO</code> instance is created only
     * when this method is called for the first time. Every subsequent call to
     * this method will simply return the already created instance. If creating
     * the instance fails, an <code>ObjectInstantiationException</code> is
     * thrown.
     * </p>
     * <p>
     * This method is <i>synchronized</i> to avoid the thread-safety issues
     * related to lazy instantiation
     * </p>
     *
     * @return the <code>UserProfileDAO</code> instance
     * @throws ObjectInstantiationException if creating the
     *         <code>UserProfileDAO</code> instance fails (usually due to a
     *         configuration error)
     */
    public static synchronized UserProfileDAO getUserProfileDAO() throws ObjectInstantiationException {
        if (userProfileDAO == null) {
            userProfileDAO = (UserProfileDAO) createDAO("userProfileDAO", UserProfileDAO.class);
        }
        return userProfileDAO;
    }

    /**
     * <p>
     * Creates the DAO instance of the specified type corresponding to the given
     * key in the configuration. The DAO creation is done using the
     * ObjectFactory.
     * </p>
     *
     * @param daoKeyConfigProperty the configuration property specifying the
     *        Object Factory key of the DAO to create
     * @param daoType the type of the DAO class to create
     * @return the DAO class
     * @throws ObjectInstantiationException if creating the DAO instance fails
     */
    private static Object createDAO(String daoKeyConfigProperty, Class daoType) throws ObjectInstantiationException {
        String daoKey = UserLogicPersistenceHelper.getConfigProperty(NAMESPACE, daoKeyConfigProperty, true);
        return UserLogicPersistenceHelper.createObject(NAMESPACE, daoKey, daoType);
    }

}
