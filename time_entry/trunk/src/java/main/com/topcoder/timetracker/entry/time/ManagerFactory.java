/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is a class that acts as a factory for the different managers, and may be used
 * to easily access manager classes for various purposes.
 * </p>
 *
 * <p>
 * It uses lazy instantiation to create the manager implementations.
 * Instantiation is done through the Object Factory.
 * </p>
 *
 * <p>
 * Thread Safety: The methods should be synchronized to ensure only a single instance of each
 * manager is instantiated. The managers themselves are thread-safe since it is
 * required by the manager interfaces.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class ManagerFactory {
    /**
     * <p>
     * This is the namespace to use when creating a <code>ConfigManagerSpecificationFactory</code>.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.entry.time";

    /**
     * <p>
     * This is the <code>TimeEntryManager</code> singleton instance that will be used.
     * </p>
     *
     * <p>
     * It is null until it has been accessed.
     * </p>
     *
     * <p>
     * It will be initialized via object factory.
     * </p>
     */
    private static TimeEntryManager timeEntryManager;

    /**
     * <p>
     * This is the <code>TimeStatusManager</code> singleton instance that will be used.
     * </p>
     *
     * <p>
     * It is null until it has been accessed.
     * </p>
     *
     * <p>
     * It will be initialized via object factory.
     * </p>
     */
    private static TimeStatusManager timeStatusManager;

    /**
     * <p>
     * This is the <code>TaskTypeManager</code> singleton instance that will be used.
     * </p>
     *
     * <p>
     * It is null until it has been accessed.
     * </p>
     *
     * <p>
     * It will be initialized via object factory.
     * </p>
     */
    private static TaskTypeManager taskTypeManager;

    /**
     * <p>
     * This is the lock object when lazy instantiating <code>taskTypeManager</code> instance.
     * </p>
     *
     * <p>
     * It is set when declaring and never changed afterwards.
     * </p>
     */
    private static final Object TASKTYPELOCKOBJECT = new Object();

    /**
     * <p>
     * This is the lock object when lazy instantiating <code>timeStatusManager</code> instance.
     * </p>
     *
     * <p>
     * It is set when declaring and never changed afterwards.
     * </p>
     */
    private static final Object TIMESTATUSLOCKOBJECT = new Object();

    /**
     * <p>
     * This is the lock object when lazy instantiating <code>timeEntryManager</code> instance.
     * </p>
     *
     * <p>
     * It is set when declaring and never changed afterwards.
     * </p>
     */
    private static final Object TIMEENTRYLOCKOBJECT = new Object();

    /**
     * <p>
     * Private Constructor.
     * </p>
     */
    private ManagerFactory() {
        // empty
    }

    /**
     * <p>
     * Retrieves the singleton instance of <code>TimeEntryManager</code>.
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
     * @return The single <code>TimeEntryManager</code> instance to use.
     *
     * @throws ConfigurationException if fails to create the <code>TimeEntryManager</code> instance
     * from object factory
     */
    public static TimeEntryManager getTimeEntryManager() throws ConfigurationException {
        synchronized (TIMEENTRYLOCKOBJECT) {
            if (timeEntryManager == null) {
                timeEntryManager = (TimeEntryManager) createObject(TimeEntryManager.class.getName(),
                    TimeEntryManager.class);
            }
        }

        return timeEntryManager;
    }

    /**
     * <p>
     * Retrieves the singleton instance of <code>TimeStatusManager</code>.
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
     * @return The single <code>TimeStatusManager</code> instance to use.
     *
     * @throws ConfigurationException if fails to create the <code>TimeStatusManager</code> instance
     * from object factory
     */
    public static TimeStatusManager getTimeStatusManager() throws ConfigurationException {
        synchronized (TIMESTATUSLOCKOBJECT) {
            if (timeStatusManager == null) {
                timeStatusManager = (TimeStatusManager) createObject(TimeStatusManager.class.getName(),
                    TimeStatusManager.class);
            }
        }

        return timeStatusManager;
    }

    /**
     * <p>
     * Retrieves the singleton instance of <code>TaskTypeManager</code>.
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
     * @return The single <code>TaskTypeManager</code> instance to use.
     *
     * @throws ConfigurationException if fails to create the <code>TaskTypeManager</code> instance
     * from object factory
     */
    public static TaskTypeManager getTaskTypeManager() throws ConfigurationException {
        synchronized (TASKTYPELOCKOBJECT) {
            if (taskTypeManager == null) {
                taskTypeManager = (TaskTypeManager) createObject(TaskTypeManager.class.getName(),
                    TaskTypeManager.class);
            }
        }

        return taskTypeManager;
    }

    /**
     * <p>
     * Creates an object from the given namespace and the class type.
     * </p>
     *
     * <p>
     * The type of the object to create is given as well, it is for validation purpose.
     * </p>
     *
     * @param key the key to create the object
     * @param type the class type of the object to create, it is for validation purpose
     * @return the object created from the given object factory and the key
     *
     * @throws ConfigurationException if fail to create the object or the
     * created object is an instance of the given type
     */
    private static Object createObject(String key, Class type) throws ConfigurationException {
        try {
            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(NAMESPACE));
            Object value = of.createObject(key);

            // verify the class type of the created object
            if (!type.isInstance(value)) {
                throw new ConfigurationException("The configed object for key [" + key + "] is not an instance of "
                    + type.getName());
            }

            return value;
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException(
                "InvalidClassSpecificationException occurs while creating object with key [" + key
                    + "] using object factory, caused by ", e);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("SpecificationConfigurationException occurs "
                + "while creating ObjectFactory instance using namespace " + NAMESPACE, e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("IllegalReferenceException occurs "
                + "while creating ObjectFactory instance using namespace " + NAMESPACE, e);
        }
    }
}
