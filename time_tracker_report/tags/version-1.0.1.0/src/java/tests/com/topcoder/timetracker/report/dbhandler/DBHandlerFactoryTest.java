/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.dbhandler;

import com.topcoder.timetracker.report.BaseTimeTrackerReportTest;
import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;


/**
 * This class contains the unit tests for {@link DBHandlerFactory}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DBHandlerFactoryTest extends BaseTimeTrackerReportTest {
    /**
     * This is the {@link DBHandlerFactory} instance tested in the test cases. It is instantiated in {@link #setUp()}.
     */
    private DBHandlerFactory dbHandlerFactory;

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correctness.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtor() throws Exception {
        new DBHandlerFactory();
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} is completely empty
     */
    public void testCtorFailEmptyCM() {
        try {
            clearConfig();
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers does not exist
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailNoDBHandlerClasses() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_noDBHandlersProperty.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers is empty
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyDBHandlerClasses() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_emptyDBHandlers.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers contains empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyDBHandlerName() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_emptyDBHandlerName.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers references a DBHandler config whose
     * classname is an empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyClassname() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_emptyClassName.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers references a DBHandler config whose
     * classname is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailNoClassname() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_noClassName.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers references a DBHandler config with classname
     * of unknown class
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailUnknownClass() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_unknownClass.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers references a DBHandler config that is not
     * found
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailUnknownDBHandler() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_unknownDBHandlerName.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers references a DBHandler config whose class
     * has no no-arg constructor
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailNoNoArgConstructor() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_classNoNoArgConstructor.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers references a DBHandler config whose class
     * throws an exception upon constructor invocation
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailExceptionInConstructor() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_exceptionInConstructorClass.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#DBHandlerFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for dbHandlers references a DBHandler config whose class
     * does not implement {@link DBHandler}
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailClassNotImplemetingDBHandler() throws Exception {
        try {
            loadDbHandlerConfiguration("DBHandlers_classNotImplementingDBHandler.xml");
            new DBHandlerFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#getDBHandler(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: dbHandlerName is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetDBHandlerFailNullDbHandlerName() throws Exception {
        try {
            dbHandlerFactory.getDBHandler(null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#getDBHandler(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: dbHandlerName is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetDBHandlerFailEmptyDbHandlerName() throws Exception {
        try {
            dbHandlerFactory.getDBHandler("    ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#getDBHandler(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: dbHandlerName not known by DBHandlerFactory
     */
    public void testGetDBHandlerFailUnknownDbHandlerName() {
        try {
            dbHandlerFactory.getDBHandler("UNKNOWN");
            fail("should throw");
        } catch (DBHandlerNotFoundException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link DBHandlerFactory#getDBHandler(String)} for correct behavior.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetDBHandler() throws Exception {
        final DBHandler dbHandler = dbHandlerFactory.getDBHandler("InformixDBHandler");
        assertEquals(InformixDBHandler.class, dbHandler.getClass());
    }

    /**
     * This method clears the {@link #DBHANDLERS_CONFIGURATION_NAMESPACE} and loads the file with the given name into
     * {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadDbHandlerConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(DBHANDLERS_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        dbHandlerFactory = new DBHandlerFactory();
    }
}