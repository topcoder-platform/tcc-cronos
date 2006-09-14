package com.cronos.onlinereview.phases.accuracytests;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.scoreaggregator.ReviewScoreAggregator;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.deliverable.UploadManager;
import com.cronos.onlinereview.phases.ManagerHelper;
import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.external.ProjectRetrieval;
import com.cronos.onlinereview.external.UserRetrieval;

import java.sql.Connection;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Accuracy test for ManagerHelper class.
 *
 * @author tuenm
 * @version 1.0
 */
public class ManagerHelperAccuracyTest extends BaseAccuracyTest{
    /**
     * The configuration file for ManagerHelper
     */
    public static final String MANAGER_HELPER_CONFIG_FILE = "accuracy/ManagerHelperConfig.xml";


    /**
     * <p/>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        releaseSingletonInstance(IDGeneratorFactory.class, "generators");

        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(MANAGER_HELPER_CONFIG_FILE);

        // load the configuration file of the managers
        configManager.add("accuracy/ProjectManagement.xml");
        configManager.add("accuracy/PhaseManagement.xml");
        configManager.add("accuracy/ScorecardManagement.xml");
        configManager.add("accuracy/ResourceUploadSearchBundleManager.xml");
        configManager.add("accuracy/AutoScreeningManagement.xml");
        configManager.add("accuracy/UserProjectDataStore.xml");
        configManager.add("accuracy/ReviewScorecardAggregator.xml");
        configManager.add("accuracy/Review_Management.xml");
    }

    /**
     * <p/>
     * Cleans up the test environment.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests ManagerHelper constructor. No exception is expected.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor() throws Exception {
        ManagerHelper helper = new ManagerHelper();
    }

    /**
     * Tests get PhaseManager from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetPhaseManager() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        PhaseManager manager = helper.getPhaseManager();
        assertNotNull("Cannot get PhaseManager using ManagerHelper.", manager);
    }

    /**
     * Tests get ProjectManager from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectManager() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        ProjectManager manager = helper.getProjectManager();
        assertNotNull("Cannot get ProjectManager using ManagerHelper.", manager);
    }

    /**
     * Tests get ScorecardManager from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScorecardManager() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        ScorecardManager manager = helper.getScorecardManager();
        assertNotNull("Cannot get ScorecardManager using ManagerHelper.", manager);
    }

    /**
     * Tests get ReviewManager from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetReviewManager() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        ReviewManager manager = helper.getReviewManager();
        assertNotNull("Cannot get ReviewManager using ManagerHelper.", manager);
    }

    /**
     * Tests get ScreeningManager from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetScreeningManager() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        ScreeningManager manager = helper.getScreeningManager();
        assertNotNull("Cannot get ScreeningManager using ManagerHelper.", manager);
    }

    /**
     * Tests get ResourceManager from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetResourceManager() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        ResourceManager manager = helper.getResourceManager();
        assertNotNull("Cannot get ResourceManager using ManagerHelper.", manager);
    }

    /**
     * Tests get UploadManager from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUploadManager() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        UploadManager manager = helper.getUploadManager();
        assertNotNull("Cannot get UploadManager using ManagerHelper.", manager);
    }

    /**
     * Tests get ProjectRetrieval from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetProjectRetrieval() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        ProjectRetrieval projectRetrieval = helper.getProjectRetrieval();
        assertNotNull("Cannot get ProjectRetrieval using ManagerHelper.", projectRetrieval);
    }

    /**
     * Tests get UserRetrieval from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testGetUserRetrieval() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        UserRetrieval userRetrieval = helper.getUserRetrieval();
        assertNotNull("Cannot get UserRetrieval using ManagerHelper.", userRetrieval);
    }

    /**
     * Tests get ReviewScoreAggregator from ManagerHelper. The returned value is expected to be non null.
     *
     * @throws Exception to JUnit.
     */
    public void testReviewScoreAggregator() throws Exception {
        ManagerHelper helper = new ManagerHelper();
        ReviewScoreAggregator agg = helper.getScorecardAggregator();
        assertNotNull("Cannot get ReviewScoreAggregator using ManagerHelper.", agg);
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
