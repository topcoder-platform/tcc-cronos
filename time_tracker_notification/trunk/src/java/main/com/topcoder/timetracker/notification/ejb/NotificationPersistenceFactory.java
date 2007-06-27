/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.ejb;

import com.topcoder.timetracker.notification.Helper;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.NotificationPersistence;

import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This is a class that acts as a factory for the NotificationPersistence, and may be used to easily access the
 * NotificationPersistence class for various purposes.  It uses lazy instantiation to create the
 * NotificationPersistence.  Instantiation is done through the Object Factory.
 * </p>
 *
 * <p>
 * Thread Safety: - The methods is synchronized to ensure only a single instance of each NotificationPersistence is
 * instantiated. The NotificationPersistence implementations themselves are thread-safe since it is required by the
 * NotificationPersistence interfaces.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 1.0
 */
public class NotificationPersistenceFactory {

    /**
     * <p>
     * This is the namespace to use when creating a ConfigManagerSpecificationFactory. It is also the namespace that
     * should be used when writing a configuration file for the managers.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.notification";

    /**
     * <p>
     * This is the NotificationPersistence  that will be used.  It is null until it has been accessed.  It will be
     * initialized by a call to ObjectFactory.createObject.
     * </p>
     *
     * <p>
     * Initial Value: From getNotificationPersistence
     * </p>
     *
     * <p>
     * Accessed In: getNotificationPersistence
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
     * Valid Values: Possibly null NotificationPersistence instances.
     * </p>
     */
    private static NotificationPersistence NotificationPersistence;

    /** Represents variable used for synchronization. */
    private static Object SynObject = new Object();

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private NotificationPersistenceFactory() {
        // empty constructor
    }

    /**
     * <p>
     * Retrieves an instance of NotificationPersistence.  If it has not yet been initialized, then an instance will be
     * created.
     * </p>
     *
     * <p>
     * Thread Safety: Synchronization should be used to ensure only a single instance is created.
     * </p>
     *
     * @return An instance of NotificationPersistence to use.
     *
     * @throws NotificationConfigurationException if a problem occurs during instantiation.
     */
    public static NotificationPersistence getNotificationPersistence()
        throws NotificationConfigurationException {
        synchronized (SynObject) {
            if (NotificationPersistence != null) {
                return NotificationPersistence;
            }

            // create the object factory
            ObjectFactory of = Helper.getObjectFactory(NAMESPACE);

            try {
                NotificationPersistence = (NotificationPersistence) Helper.createObjectViaObjectFactory(of,
                        NotificationPersistence.class.getName());

                return NotificationPersistence;
            } catch (ClassCastException cce) {
                throw new NotificationConfigurationException("Wrong type is created.", cce);
            }
        }
    }
}
