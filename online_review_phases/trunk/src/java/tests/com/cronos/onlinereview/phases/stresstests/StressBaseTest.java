/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.stresstests;

import java.sql.Connection;
import java.util.Iterator;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/**
 * <p>
 * The base class for all the stress cases.
 * </p>
 *
 * @author assistant
 */
public class StressBaseTest extends TestCase {

    /**
     * The first level load.
     */
    public static final int FIRST_LEVEL = 100;

    /**
     * The second level load.
     */
    public static final int SECOND_LEVEL = 1000;

    /**
     * The third level load.
     */
    public static final int THIRD_LEVEL = 10000;

    /**
     * The first level load.
     */
    public static final int THREAD_NUMBER = 100;

    /**
     * The default id.
     */
    protected static final int DEFAULT_ID = 1;

    /**
     * The connection used to test.
     */
    protected Connection conn;

    /**
     * Represents the long value to record the running time.
     */
    private static long time = 0L;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        releaseSingletonInstance(IDGeneratorFactory.class, "generators");

        clearConfigurations();

        // load configurations
        ConfigManager cm = ConfigManager.getInstance();

        cm.add("stress/DB_Factory.xml");
        cm.add("stress/Document_Manager.xml");
        cm.add("stress/Manager_Helper.xml");
        cm.add("stress/Phase_Handler.xml");
        cm.add("stress/Phase_Management.xml");
        cm.add("stress/Project_Management.xml");
        cm.add("stress/Review_Management.xml");
        cm.add("stress/Review_Score_Aggregator.xml");
        cm.add("stress/Scorecard_Management.xml");
        cm.add("stress/Screening_Management.xml");
        cm.add("stress/Upload_Resource_Search.xml");
        cm.add("stress/User_Project_Data_Store.xml");
        cm.add("stress/Email_Engine.xml");

        // get the connection
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        conn = factory.createConnection();

    }

    /**
     * Clear the configurations.
     * @throws Exception if any error happens
     */
    private void clearConfigurations() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }
    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        clearConfigurations();
    }

    /**
     * Start record the time.
     *
     * @param method the method name
     * @param load the load to run
     */
    protected void startRecord(String method, int load) {
        time = System.currentTimeMillis();
    }

    /**
     * End record the time.
     *
     * @param method the method name
     * @param load the load to run
     */
    protected void endRecord(String method, int load) {
        time = System.currentTimeMillis() - time;
        System.out.println("Run " + method + " " + load + " times took " + time + "ms.");
    }

    /**
     * <p>A helper method to be used to <code>nullify</code> the singleton instance. The method uses a <code>Java
     * Reflection API</code> to access the field and initialize the field with <code>null</code> value. The operation
     * may fail if a <code>SecurityManager</code> prohibits such sort of accessing.</p>
     *
     * @param clazz a <code>Class</code> representing the class of the <code>Singleton</code> instance.
     * @param instanceName a <code>String</code> providing the name of the static field holding the reference to the
     * singleton instance.
     */
    public static final void releaseSingletonInstance(Class clazz, String instanceName) throws Exception {
        try {
            Field instanceField = clazz.getDeclaredField(instanceName);
            boolean accessibility = instanceField.isAccessible();
            instanceField.setAccessible(true);

            if (Modifier.isStatic(instanceField.getModifiers())) {
                instanceField.set(null, null);
            } else {
                System.out.println("An error occurred while trying to release the singleton instance - the "
                                   + " '" + instanceName + "' field is not static");
            }

            instanceField.setAccessible(accessibility);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to release the singleton instance : " + e);
        }
    }
}
