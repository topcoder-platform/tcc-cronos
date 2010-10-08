/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>BaseCalculator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseCalculatorUnitTests extends TestCase {
    /**
     * Mock up base calculator.
     */
    private class TestAction extends BaseCalculator {
        /**
         * <p>
         * Serial Version UID of the class.
         * </p>
         */
        private static final long serialVersionUID = 1L;
    }

    /**
     * <p>
     * The default config file path of <code>DBConnectionFactory</code> for test.
     * </p>
     */
    private static final String DB_CONFIG_FILE = "test_files/ConnectionFactory.xml";

    /**
     * <p>
     * The default config file path of <code>SearchBundleManager</code> for test.
     * </p>
     */
    private static final String SB_CONFIG_FILE = "test_files/SearchBundleManager.xml";

    /**
     * <p>
     * The default config file path of <code>PhaseManager</code> for test.
     * </p>
     */
    private static final String PH_CONFIG_FILE = "test_files/PhaseManager.xml";

    /**
     * <p>
     * The default config file path of <code>ReviewManager</code> for test.
     * </p>
     */
    private static final String RM_CONFIG_FILE = "test_files/ReviewManager.xml";

    /**
     * <p>
     * The default config file path of <code>ProjectManager</code> for test.
     * </p>
     */
    private static final String PM_CONFIG_FILE = "test_files/ProjectManager.xml";

    /**
     * <p>
     * The default config file path of <code>InformixPersistence</code> for test.
     * </p>
     */
    private static final String IN_CONFIG_FILE = "test_files/InformixPersistence.xml";

    /**
     * Configuration object.
     */
    private ConfigurationObject cfgObject;

    /**
     * <code>BaseCalculator</code> class instance for unit test.
     */
    private BaseCalculator instance;

    /**
     * <p>
     * Set up.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void setUp() throws Exception {
        instance = new TestAction();
        ConfigManager cm = ConfigManager.getInstance();

        // load configuration files
        cm.add(new File(DB_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(SB_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(PH_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(RM_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(PM_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(IN_CONFIG_FILE).getAbsolutePath());

        cfgObject = TestsHelper.loadConfig("config.xml").getChild(TestsHelper.NAMESPACE);
    }

    /**
     * Clean up test.
     *
     * @throws Exception for JUnit
     */
    @SuppressWarnings("unchecked")
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
        cm = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseCalculator()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor_BaseCalculator() {
        assertNotNull("Instance should be correctly created.", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testBaseCalculator_configure() throws Exception {
        instance.configure(cfgObject);
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with config is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testBaseCalculator_configure_Null() {
        try {
            instance.configure(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("config"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with loggerName is empty.<br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testBaseCalculator_configure_LoggerName_Emtpy() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild("loggerNameError");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertTrue(e.getMessage().contains("loggerName"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with minimumCoefficient is negative.<br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testBaseCalculator_configure_minimumCoefficient_Negative() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml")
                .getChild("minimumCoefficientError");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertTrue(e.getMessage().contains("minimumCoefficient"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with invalid number.<br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testBaseCalculator_configure_minimumCoefficient_InvalidNumber() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild("invalidNumber");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getComments()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testBaseCalculator_getComments() throws Exception {
        Review review = new Review();

        // Set comment
        Comment comment = new Comment();
        comment.setAuthor(1L);
        Comment[] comments = new Comment[] {comment, comment};

        review.addComments(comments);

        // Set item
        Item item = new Item();
        item.addComments(comments);
        Item[] items = new Item[] {item, item};

        review.addItems(items);

        instance.configure(cfgObject);

        List<Comment> list = instance.getComments(review);

        assertNotNull("Method should be correctly executed.", list);
        assertEquals("Method should be correctly executed.", 2, list.size());

        for (Comment c : list) {
            assertEquals("Method should be correctly executed.", comment, c);
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLog()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testBaseCalculator_getLog() throws Exception {
        instance.configure(cfgObject);
        assertNotNull("Method should be correctly executed.", instance.getLog());

    }

    /**
     * <p>
     * Accuracy test for the method <code>getMinimumCoefficient()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testBaseCalculator_getMinimumCoefficient() throws Exception {
        instance.configure(cfgObject);
        assertNotNull("Method should be correctly executed.", instance.getMinimumCoefficient());
    }
}
