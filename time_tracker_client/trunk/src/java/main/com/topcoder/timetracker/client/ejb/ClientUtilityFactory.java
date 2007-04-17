/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.ejb;

import com.topcoder.timetracker.client.ClientUtility;
import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.Helper;
import com.topcoder.util.objectfactory.ObjectFactory;


/**
 * <p>
 * This is a class that acts as a factory for the ClientUtility, and may be used to easily access the utility class for
 * various purposes.  It uses lazy instantiation to create the ClientUtility.  Instantiation is done through the
 * Object Factory.
 * </p>
 *
 * <p>
 * As of version 3.2, this is used by the SessionBeans to delegate the work to.
 * </p>
 *
 * <p>
 * Thread Safety: - The method is thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityFactory {
    /**
     * <p>
     * This is the namespace to use when creating a ConfigManagerSpecificationFactory. It is also the namespace that
     * should be used when writing a configuration file for the ClientUtilityFactory.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.client";

    /**
     * <p>
     * This is the ClientUtility that will be used.  It is null until it has been accessed.  It will be initialized by
     * a call to ObjectFactory.createObject.
     * </p>
     *
     * <p>
     * Initial Value: From getClientUtility
     * </p>
     *
     * <p>
     * Accessed In: getClientUtility
     * </p>
     *
     * <p>
     * Modified In: Not Modified
     * </p>
     *
     * <p>
     * Utilized In: Not utilized
     * </p>
     *
     * <p>
     * Valid Values: Possibly null ClientUtility instances.
     * </p>
     */
    private static ClientUtility clientUtility;

    /**
     * <p>
     * Private instance used for synchronization in case that clientUtility may be null.
     * </p>
     */
    private static final Object SYN_OBJECT = new Object();

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private ClientUtilityFactory() {
        // your code here
    }

    /**
     * <p>
     * Retrieves an instance of ClientUtility.  If it has not yet been initialized, then an instance will be created.
     * </p>
     *
     * @return An instance of ClientUtility to use.
     *
     * @throws ConfigurationException if any error occurred
     */
    public static ClientUtility getClientUtility() throws ConfigurationException {
        synchronized (SYN_OBJECT) {
            if (clientUtility != null) {
                return clientUtility;
            }

            // create the object factory
            ObjectFactory of = Helper.getObjectFactory(NAMESPACE);

            try {
                clientUtility = (ClientUtility) Helper.createObjectViaObjectFactory(of, ClientUtility.class.getName());

                return clientUtility;
            } catch (ClassCastException cce) {
                throw new ConfigurationException("Wrong type is created.", cce);
            }
        }
    }
}
