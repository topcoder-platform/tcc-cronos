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
 * This is a class that acts as a factory for the <code>UserManager</code>, and may be used
 * to easily access the manager class for various purposes.
 * </p>
 *
 * <p>
 * It uses lazy instantiation to create the manager implementations.
 * Instantiation is done through the Object Factory.
 * </p>
 *
 * <p>
 * Thread Safety: The methods should be synchronized to ensure only a single instance of
 * the manager is instantiated. The managers themselves are thread-safe since it is
 * required by the <code>UserManager</code> interface.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
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
     * It is null until it has been accessed.
     * It will be initialized through the Object Factory.
     * </p>
     */
    private static UserManager userManager;

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
     * @throws ConfigurationException if fails to create the <code>UserManager</code> instance
     * from object factory
     */
    public static synchronized UserManager getUserManager() throws ConfigurationException {
        if (userManager == null) {
            try {
                ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(NAMESPACE));
                Object value = of.createObject(UserManager.class.getName());

                // verify the class type of the created object
                if (!(value instanceof UserManager)) {
                    throw new ConfigurationException("The created object is not an instance of UserManager.");
                }

                userManager = (UserManager) value;
            } catch (SpecificationConfigurationException e) {
                throw new ConfigurationException("SpecificationConfigurationException occurs "
                    + "while creating ObjectFactory instance using namespace " + NAMESPACE, e);
            } catch (IllegalReferenceException e) {
                throw new ConfigurationException("IllegalReferenceException occurs "
                    + "while creating ObjectFactory instance using namespace " + NAMESPACE, e);
            } catch (InvalidClassSpecificationException e) {
                throw new ConfigurationException("InvalidClassSpecificationException occurs while creating object.", e);
            }
        }

        return userManager;
    }
}
