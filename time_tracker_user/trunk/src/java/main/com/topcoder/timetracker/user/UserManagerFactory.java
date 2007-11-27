/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is a class that acts as a factory for the <code>UserManager</code>, and may be used to easily access the
 * manager class for various purposes.
 * </p>
 * <p>
 * It uses lazy instantiation to create the manager implementations. Instantiation is done through the Object
 * Factory.
 * </p>
 * <p>
 * Thread Safety: The methods should be synchronized to ensure only a single instance of the manager is
 * instantiated. The managers themselves are thread-safe since it is required by the <code>UserManager</code>
 * interface.
 * </p>
 *
 * @author ShindouHikaru, biotrail, George1, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserManagerFactory {

    /**
     * <p>
     * This is the namespace to use when creating a <code>ConfigManagerSpecificationFactory</code>.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.user";

    /**
     * <p>
     * This is the <code>UserManager</code> that will be used.
     * </p>
     *
     * <p>
     * It is null until it has been accessed. It will be initialized through the Object Factory.
     * </p>
     */
    private static UserManager userManager;

    /**
     * <p>
     * This is the UserStatusManager that will be used. It is null until it has been accessed. It will be
     * initialized by a call to ObjectFactory.createObject.
     * </p>
     * <p>
     * Initial Value: From getUserStatusManager
     * </p>
     * <p>
     * Accessed In: getUserStatusManager
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: Not utilized
     * </p>
     * <p>
     * Valid Values: Possibly null UserStatusManager instances.
     * </p>
     */
    private static UserStatusManager userStatusManager;

    /**
     * <p>
     * This is the UserTypeManager that will be used. It is null until it has been accessed. It will be initialized
     * by a call to ObjectFactory.createObject.
     * </p>
     * <p>
     * Initial Value: From getUserTypeManager
     * </p>
     * <p>
     * Accessed In: getUserTypeManager
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: Not utilized
     * </p>
     * <p>
     * Valid Values: Possibly null UserTypeManager instances.
     * </p>
     */
    private static UserTypeManager userTypeManager;

    /**
     * <p>
     * Private Constructor.
     * </p>
     */
    private UserManagerFactory() {
        // empty
    }

    /**
     * <p>
     * Retrieves an instance of <code>UserManager</code>.
     * </p>
     *
     * <p>
     * If it has not yet been initialized, then an instance will be created from object factory.
     * </p>
     *
     * <p>
     * Note, synchronization is used to ensure only a single instance is created.
     * </p>
     *
     * @return The single <code>UserManager</code> instance to use.
     *
     * @throws ConfigurationException
     *             if fails to create the <code>UserManager</code> instance from object factory
     */
    public static synchronized UserManager getUserManager() throws ConfigurationException {
        if (userManager == null) {
            userManager = (UserManager) createObject(UserManager.class.getName(), UserManager.class);
        }

        return userManager;
    }

    /**
     * <p>
     * Creates object from object factory.
     * </p>
     *
     * @param key
     *            the key of the object in the specification factory of the object factory
     * @param intendedClass
     *            the intended class of the result
     * @return the created object
     * @throws ConfigurationException
     *             if there is any exception when creating the object
     */
    private static Object createObject(String key, Class intendedClass) throws ConfigurationException {
        try {
            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(NAMESPACE));
            Object value = of.createObject(key);

            // verify the class type of the created object
            if (!(intendedClass.isInstance(value))) {
                throw new ConfigurationException("The created object is not an instance of UserManager.");
            }

            return value;
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("SpecificationConfigurationException occurs "
                + "while creating ObjectFactory instance using namespace " + NAMESPACE, e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("IllegalReferenceException occurs "
                + "while creating ObjectFactory instance using namespace " + NAMESPACE, e);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException(
                "InvalidClassSpecificationException occurs while creating object.", e);
        }
    }

    /**
     * <p>
     * Retrieves an instance of <code>UserStatusManager</code>. If it has not yet been initialized, then an
     * instance will be created.
     * </p>
     * <p>
     * Thread Safety: Synchronization should be used to ensure only a single instance is created.
     * </p>
     *
     * @return The single <code>UserStatusManager</code> instance to use.
     * @throws ConfigurationException
     *             if there is any error in creating the user status manager
     *
     * @since 3.2.1
     */
    public static synchronized UserStatusManager getUserStatusManager() throws ConfigurationException {
        if (userStatusManager == null) {
            userStatusManager =
                (UserStatusManager) createObject(UserStatusManager.class.getName(), UserStatusManager.class);
        }

        return userStatusManager;
    }

    /**
     * <p>
     * Retrieves an instance of <code>UserTypeManager</code>. If it has not yet been initialized, then an
     * instance will be created.
     * </p>
     * <p>
     * Thread Safety: Synchronization should be used to ensure only a single instance is created.
     * </p>
     *
     * @return The single <code>UserTypeManager</code> instance to use.
     * @throws ConfigurationException
     *             if there is any error in creating the user type manager
     * @since 3.2.1
     */
    public static synchronized UserTypeManager getUserTypeManager() throws ConfigurationException {
        if (userTypeManager == null) {
            userTypeManager =
                (UserTypeManager) createObject(UserTypeManager.class.getName(), UserTypeManager.class);
        }

        return userTypeManager;
    }

}
