/**
 *
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.review.selection.impl.workloadfactorcalculator.DefaultWorkloadFactorCalculator;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.config.ConfigManager;

/**
 * This class contains Accuracy tests for DefaultWorkloadFactorCalculator.
 * @author sokol
 * @version 1.0
 */
public class DefaultWorkloadFactorCalculatorAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents DefaultWorkloadFactorCalculator instance for testing.
     * </p>
     */
    private DefaultWorkloadFactorCalculator calculator;

    /**
     * Creates suite that aggregates all Accuracy test cases for DefaultWorkloadFactorCalculator.
     * @return Test suite that aggregates all Accuracy test cases for DefaultWorkloadFactorCalculator
     */
    public static Test suite() {
        return new TestSuite(DefaultWorkloadFactorCalculatorAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        calculator = new DefaultWorkloadFactorCalculator();
        // load config
        ConfigManager.getInstance().add("accuracy/work_load_config.xml");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        // remove the namespace
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator <?> iter = cm.getAllNamespaces(); iter.hasNext();) {
            String nameSpace = (String) iter.next();
            if (nameSpace.equals("com.topcoder.util.log")) {
                continue;
            }
            cm.removeNamespace(nameSpace);
        }
        calculator = null;
    }

    /**
     * <p>
     * Tests DefaultWorkloadFactorCalculator constructor.
     * </p>
     * <p>
     * DefaultWorkloadFactorCalculator instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("DefaultWorkloadFactorCalculator instance should be created successfully.", calculator);
    }

    /**
     * <p>
     * Tests {@link DefaultWorkloadFactorCalculator#configure(com.topcoder.configuration.ConfigurationObject)} with
     * valid arguments passed.
     * </p>
     * <p>
     * DefaultWorkloadFactorCalculator instance should be configured successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager(ACCURACY_DIR + "conf.properties");
        config = manager.getConfiguration("work_load_config").getChild("valid_config1");
        double expectedValue = 0.5;
        calculator.configure(config);
        assertEquals("workloadFactorBase should be set successfully.", expectedValue, getFieldValue(calculator,
                "workloadFactorBase"));
        assertEquals("reviewNumberMultiplier should be set successfully.", expectedValue, getFieldValue(calculator,
                "reviewNumberMultiplier"));
    }

    /**
     * <p>
     * Tests {@link DefaultWorkloadFactorCalculator#calculateWorkloadFactor(int)} with default fields values.
     * </p>
     * <p>
     * Result should be <code>0.05</code> with 19 concurrentReviewNumber. No exception is expected.
     * </p>
     */
    public void testCalculateWorkloadFactor1() {
        int concurrentReviewNumber = 19;
        double result = calculator.calculateWorkloadFactor(concurrentReviewNumber);
        assertEquals("Result should be the same.", 0.05, result, DELTA);
    }

    /**
     * <p>
     * Tests {@link DefaultWorkloadFactorCalculator#calculateWorkloadFactor(int)} with default fields values.
     * </p>
     * <p>
     * Result should be <code>0</code> with 21 concurrentReviewNumber. No exception is expected.
     * </p>
     */
    public void testCalculateWorkloadFactor2() {
        int concurrentReviewNumber = 21;
        double result = calculator.calculateWorkloadFactor(concurrentReviewNumber);
        assertEquals("Result should be the same.", 0.0, result, DELTA);
    }
}
