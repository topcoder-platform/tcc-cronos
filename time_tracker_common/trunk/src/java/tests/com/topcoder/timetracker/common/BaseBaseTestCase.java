/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAO;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;

/**
 * <p>This is the base test case which abstracts the common behavior.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class BaseBaseTestCase extends TestCase {

    /**
     * <p>
     * The milliseconds of one day(24 hours).
     * </p>
     */
    public static final int ONEDAY = 1000 * 60 * 60 * 24;

    /**
     * <p>
     * The default namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DBCONNECTION_FACTORY_NAMESPACE
        = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents the location of the configuration file for Time Tracker Common component.
     * </p>
     */
    public static final String TIME_TRACKER_CONFIG_LOCATION = "UnitTests" + File.separator + "TimeTrackerCommon.xml";

    /**
     * <p>
     * Represents the location of the configuration file for DB Connection Factory component.
     * </p>
     */
    public static final String DB_CONFIG_LOCATION = "DBConnectionFactory.xml";

    /**
     * <p>
     * Represents the location of configuration file for Object Factory component.
     * </p>
     */
    public static final String OF_CONFIG_LOCATION = "UnitTests" + File.separator + "ObjectFactory.xml";

    /**
     * <p>
     * Represents the location of the error configuration file for <code>DatabasePaymentTermDAO</code>.
     * </p>
     */
    public static final String ERROR_PAYMENTDAO_CONFIG_LOCATION =
        "UnitTests" + File.separator + "PaymentTermDAO_Error.xml";

    /**
     * <p>
     * Represents the location of the error configuration file for <code>SimpleCommonManager</code>.
     * </p>
     */
    public static final String ERROR_COMMONMANAGER_CONFIG_LOCATION =
        "UnitTests" + File.separator + "CommonManager_Error.xml";

    /**
     * <p>
     * Represents the location of the error configuration file for DB Connection Factory component.
     * </p>
     */
    public static final String ERROR_DB_CONFIG_LOCATION =
        "UnitTests" + File.separator + "DBConnectionFactory_Error.xml";

    /**
     * <p>
     * Represents the location of error configuration file for Object Factory component.
     * </p>
     */
    public static final String ERROR_OF_CONFIG_LOCATION = "UnitTests" + File.separator + "ObjectFactory_Error.xml";

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    public static final String ERROR_MESSAGE = "error message";

    /**
     * <p>
     * An exception instance used as the cause exception.
     * </p>
     */
    public static final Exception CAUSE = new Exception();

    /**
     * <p>
     * The <code>DatabasePaymentTermDAO</code> used in test.
     * </p>
     */
    private DatabasePaymentTermDAO dao = null;

    /**
     * <p>
     * The <code>SimpleCommonManager</code> used in test.
     * </p>
     */
    private SimpleCommonManager manager = null;

    /**
     * <p>
     * The <code>TimeTrackerBean</code> used in test.
     * </p>
     */
    private TimeTrackerBean bean = null;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.initialConfigManager();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * End tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void endTest() throws Exception {
        this.bean = null;
        this.dao = null;
        this.manager = null;
        this.removeConfigManagerNS();
    }
    /**
     * <p>
     * Assert the error message is properly propagated.
     * </p>
     *
     * @param be The <code>BaseException</code> to assert
     */
    protected void assertErrorMessagePropagated(BaseException be) {
        assertTrue("Error message is properly propagated to super class.", be.getMessage().indexOf(ERROR_MESSAGE) >= 0);
    }

    /**
     * <p>
     * Assert the error cause is properly propagated.
     * </p>
     *
     * @param be The <code>BaseException</code> to assert
     */
    protected void assertErrorCausePropagated(BaseException be) {
        assertEquals("The inner exception should match.", CAUSE, be.getCause());
    }

    /**
     * <p>
     * Gets an instance of <code>TimeTrackerBean</code>.
     * </p>
     *
     * @return An instance of <code>DummyTimeTrackerBean</code>.
     */
    protected TimeTrackerBean getTimeTrackerBean() {
        if (this.bean == null) {
            this.bean = new DummyTimeTrackerBean();
        }
        return this.bean;
    }
    /**
     * <p>
     * Initial the <code>ConfigManager</code> with the configuration files in the test_files.
     * </p>
     *
     * @return initialized ConfigManager instance
     *
     * @throws ConfigManagerException to JUnit
     */
    protected ConfigManager initialConfigManager()
        throws ConfigManagerException {
        removeConfigManagerNS();

        ConfigManager cm = ConfigManager.getInstance();

        cm.add(TIME_TRACKER_CONFIG_LOCATION);
        cm.add(DB_CONFIG_LOCATION);
        cm.add(OF_CONFIG_LOCATION);
        cm.add(ERROR_PAYMENTDAO_CONFIG_LOCATION);
        cm.add(ERROR_DB_CONFIG_LOCATION);
        cm.add(ERROR_OF_CONFIG_LOCATION);
        cm.add(ERROR_COMMONMANAGER_CONFIG_LOCATION);

        return cm;
    }

    /**
     * <p>Remove all namespaces from config manager.</p>
     *
     * @throws ConfigManagerException to JUnit
     */
    protected void removeConfigManagerNS()
        throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Given the date, returns a date with one ms forward.
     * </p>
     *
     * @param date Date
     *
     * @return A date with one ms forward.
     */
    protected Date getDateWithOneMsForward(Date date) {
        return new Date(date.getTime() + 1);
    }

    /**
     * <p>
     * Given the date, returns a date with one ms backward.
     * </p>
     *
     * @param date Date
     *
     * @return A date with one ms backward.
     */
    protected Date getDateWithOneMsBackward(Date date) {
        return new Date(date.getTime() - 1);
    }

    /**
     * <p>
     * Given the date, returns a date with one day forward.
     * </p>
     *
     * @param date Date
     *
     * @return A date with one day forward.
     */
    protected Date getDateWithOneDayForward(Date date) {
        return new Date(date.getTime() + ONEDAY);
    }

    /**
     * <p>
     * Given the date, returns a date with one day backward.
     * </p>
     *
     * @param date Date
     *
     * @return A date with one day backward.
     */
    protected Date getDateWithOneDayBackward(Date date) {
        return new Date(date.getTime() - ONEDAY);
    }
    /**
     * <p>
     * Get the instance of <code>DatabasePaymentTermDAO</code> used in the test case.
     * The namespace used to instantiate is "DatabasePaymentTermDAO", see TimeTrackerCommon.xml for details.
     * </p>
     *
     * @return An instance of <code>DatabasePaymentTermDAO</code>.
     *
     * @throws Exception to JUnit
     */
    protected DatabasePaymentTermDAO getDao() throws Exception {
        if (this.dao == null) {
            this.dao = new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
        }
        return this.dao;
    }
    /**
     * <p>
     * Get the instance of <code>SimpleCommonManager</code> used in the test case.
     * The namespace used to instantiate is "com.topcoder.timetracker.common.SimpleCommonManager",
     * see TimeTrackerCommon.xml for details
     * </p>
     *
     * @return An instance of <code>SimpleCommonManager</code>.
     *
     * @throws Exception to JUnit
     */
    protected SimpleCommonManager getManager() throws Exception {
        if (this.manager == null) {
            this.manager = new SimpleCommonManager();
        }
        return this.manager;
    }

    /**
     * <p>
     * Get an instance of <code>PaymentTerm</code> without id.
     * All other fields are set with valid values.
     * </p>
     *
     * @return An instance of <code>PaymentTerm</code>.
     */
    protected PaymentTerm getPaymentTermWithOutId() {
        PaymentTerm paymentTerm = new PaymentTerm();
        Date current = this.getDateWithOneMsBackward(new Date());
        paymentTerm.setCreationDate(current);
        paymentTerm.setCreationUser("creation_user");
        paymentTerm.setModificationDate(current);
        paymentTerm.setModificationUser("modification_user");
        paymentTerm.setDescription("description");
        paymentTerm.setActive(true);
        paymentTerm.setTerm(2);
        return paymentTerm;
    }
    /**
     * <p>
     * Get an instance of <code>PaymentTerm</code> with given id.
     * All other fields are set with valid values.
     * </p>
     *
     * @param id The id of <code>PaymentTerm</code>.
     *
     * @return An instance of <code>PaymentTerm</code>.
     */
    protected PaymentTerm getPaymentTermWithId(long id) {
        PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
        paymentTerm.setId(id);
        paymentTerm.setModificationDate(new Date());
        return paymentTerm;
    }

    /**
     * <p>
     * Get value of given <code>Field</code> of given <code>Object</code>.
     * </p>
     *
     * @param object instance to get field from
     * @param fieldName name of field
     *
     * @return value of field
     *
     * @throws Exception to JUnit
     */
    protected Object getField(Object object, String fieldName) throws Exception {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }

    /**
     * <p>
     * Gets a <code>String</code> with length 65.
     * </p>
     *
     * @return A <code>String</code> with length 65.
     */
    protected String getStringWithLength65() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            sb.append("abcdefghijlmn");
        }
        return sb.toString();
    }
}
