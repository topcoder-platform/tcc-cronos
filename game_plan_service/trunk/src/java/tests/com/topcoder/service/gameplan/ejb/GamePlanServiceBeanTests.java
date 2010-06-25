/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan.ejb;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.GamePlanServiceConfigurationException;
import com.topcoder.service.gameplan.TestHelper;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Unit test for <code>GamePlanServiceBean</code> class.</p>
 *
 * @author FireIce
 * @version 1.0
 */
public class GamePlanServiceBeanTests {

    /**
     * <p>Represents the <code>GamePlanServiceBean</code> instance for testing.</p>
     */
    private GamePlanServiceBean gamePlanServiceBean;

    /**
     * <p>Set up the testing environment.</p>
     */
    @Before
    public void setUp() {
        gamePlanServiceBean = new GamePlanServiceBean();
    }

    /**
     * <p>Tears down the testing environment.</p>
     */
    @After
    public void tearDown() {
        gamePlanServiceBean = null;
    }

    /**
     * <p>Tests the <code>GamePlanServiceBean</code> constructor.</p>
     *
     * <p>Expected: the instance should be created all the time.</p>
     */
    @Test
    public void testGamePlanServiceBean() {
        assertNotNull("instance should be created", gamePlanServiceBean);
    }

    /**
     * <p>Tests the <code>initialize()</code> method.</p>
     *
     * <p>Expected: if the logName is null, the log field should be null.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testInitialize_logName_null() throws Exception {
        gamePlanServiceBean.initialize();

        // verify that the log field is not set.
        assertNull("The log field should be null.", TestHelper.getField(gamePlanServiceBean, "log"));
    }

    /**
     * <p>Tests the <code>initialize()</code> method.</p>
     *
     * <p>Condition: the logName is empty.</p>
     *
     * <p>Expected: throw GamePlanServiceConfigurationException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = GamePlanServiceConfigurationException.class)
    public void testInitialize_logName_empty() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "");

        gamePlanServiceBean.initialize();
    }

    /**
     * <p>Tests the <code>initialize()</code> method.</p>
     *
     * <p>Condition: the logName is trimmed empty.</p>
     *
     * <p>Expected: throw GamePlanServiceConfigurationException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = GamePlanServiceConfigurationException.class)
    public void testInitialize_logName_trimmedEmpty() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", " \t");

        gamePlanServiceBean.initialize();
    }

    /**
     * <p>Tests the <code>initialize()</code> method.</p>
     *
     * <p>Condition: the logName is not null/empty.</p>
     *
     * <p>Expected: The log field should be set.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testInitialize_logName_valid() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "valid");

        gamePlanServiceBean.initialize();

        // verify that the log field is set.
        assertNotNull("The log field should not be null.", TestHelper.getField(gamePlanServiceBean, "log"));
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: the tcSubject is null.</p>
     *
     * <p>Expected: throw IllegalArgumentException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveGamePlanData_null() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        gamePlanServiceBean.retrieveGamePlanData(null);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: the softwareEntityManager is null.</p>
     *
     * <p>Expected: throw IllegalStateException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testRetrieveGamePlanData_softwareEntityManager_null() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        gamePlanServiceBean.retrieveGamePlanData(new TCSubject(1l));
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: the studioEntityManager is null.</p>
     *
     * <p>Expected: throw IllegalStateException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testRetrieveGamePlanData_studioEntityManager_null() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());

        TCSubject tcSubject = new TCSubject(1l);
        tcSubject.addPrincipal(new RolePrincipal("Cockpit Administrator", 1l));

        gamePlanServiceBean.retrieveGamePlanData(tcSubject);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: no data associated with the given user id.</p>
     *
     * <p>Expected: an empty list should return</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy1() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(100l);

        List<TCDirectProjectGamePlanData> result = gamePlanServiceBean.retrieveGamePlanData(tcSubject);

        assertNotNull("The list should never null.", result);
        assertTrue("The list should be empty.", result.isEmpty());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id.</p>
     *
     * <p>Expected: Retrieves all the valid project data for the given user id.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy2() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(1l);

        List<TCDirectProjectGamePlanData> result = gamePlanServiceBean.retrieveGamePlanData(tcSubject);
        assertNotNull("The list should never null.", result);
        assertEquals("The list should contain one element.", 1, result.size());

        List<SoftwareProjectData> softwareProjectDataList = result.get(0).getSoftwareProjects();
        assertNotNull("The list should never null.", softwareProjectDataList);
        assertEquals("The list should contain one element.", 1, softwareProjectDataList.size());
        // verify the data
        SoftwareProjectData softwareProjectData = softwareProjectDataList.get(0);
        assertNotNull("The element should not be null", softwareProjectData);
        assertEquals("incorrect data", 1, softwareProjectData.getTcDirectProjectId());
        assertEquals("incorrect data", 1, softwareProjectData.getProjectId());
        assertEquals("incorrect data", "Project Name 1", softwareProjectData.getProjectName());
        assertNotNull("incorrect data", softwareProjectData.getStartDate());
        assertNotNull("incorrect data", softwareProjectData.getEndDate());
        assertEquals("incorrect data", new Long(1), softwareProjectData.getCreateUserId());
        assertNotNull("incorrect data", softwareProjectData.getDependencyProjectIds());
        assertTrue("incorrect data", softwareProjectData.getDependencyProjectIds().length == 1);
        assertFalse("incorrect data", softwareProjectData.isRepost());
        assertTrue("incorrect data", softwareProjectData.isStarted());
        assertTrue("incorrect data", softwareProjectData.isFinished());
        assertEquals("incorrect data", "Active", softwareProjectData.getProjectStatus());
        assertNull("incorrect data", softwareProjectData.getCurrentPhase());
        assertEquals("incorrect data", "Design", softwareProjectData.getProjectType());


        List<StudioProjectData> studioProjectDataList = result.get(0).getStudioProjects();
        assertNotNull("The list should never null.", studioProjectDataList);
        assertTrue("The list should be empty.", studioProjectDataList.isEmpty());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id.</p>
     *
     * <p>Expected: Retrieves all the valid project data for the given user id.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy3() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(2l);
        tcSubject.addPrincipal(new RolePrincipal("Copilot", 1l));

        List<TCDirectProjectGamePlanData> result = gamePlanServiceBean.retrieveGamePlanData(tcSubject);
        assertNotNull("The list should never null.", result);
        assertEquals("The list should contain one element.", 1, result.size());

        List<SoftwareProjectData> softwareProjectDataList = result.get(0).getSoftwareProjects();
        assertNotNull("The list should never null.", softwareProjectDataList);
        assertTrue("The list should be empty.", softwareProjectDataList.isEmpty());

        List<StudioProjectData> studioProjectDataList = result.get(0).getStudioProjects();
        assertNotNull("The list should never null.", studioProjectDataList);
        assertEquals("The list should contain one element.", 1, studioProjectDataList.size());
        // verify the data
        StudioProjectData studioProjectData = studioProjectDataList.get(0);
        assertNotNull("The element should not be null", studioProjectData);
        assertEquals("incorrect data", 1, studioProjectData.getTcDirectProjectId());
        assertEquals("incorrect data", new Long(1), studioProjectData.getProjectId());
        assertEquals("incorrect data", 1, studioProjectData.getContestId());
        assertEquals("incorrect data", "Project Name 1", studioProjectData.getProjectName());
        assertEquals("incorrect data", "Contest Name 1", studioProjectData.getContestName());
        assertNotNull("incorrect data", studioProjectData.getStartDate());
        assertNotNull("incorrect data", studioProjectData.getEndDate());
        assertEquals("incorrect data", new Long(2), studioProjectData.getCreateUserId());
        assertTrue("incorrect data", studioProjectData.isStarted());
        assertTrue("incorrect data", studioProjectData.isFinished());
        assertEquals("incorrect data", "Active - Public", studioProjectData.getContestStatus());
        assertNull("incorrect data", studioProjectData.getContestType());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     *
     * <p>Condition: Admin mode is used.</p>
     *
     * <p>Expected: Retrieves all the valid project data.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData_accuracy4() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(2l);
        tcSubject.addPrincipal(new RolePrincipal("Cockpit Administrator", 1l));

        List<TCDirectProjectGamePlanData> result = gamePlanServiceBean.retrieveGamePlanData(tcSubject);
        assertNotNull("The list should never null.", result);
        assertEquals("The list should contain one element.", 2, result.size());

        // verify the data
        for (TCDirectProjectGamePlanData tcDirectProjectGamePlanData : result) {
            assertNotNull("The element should not be null", tcDirectProjectGamePlanData);
            assertTrue("incorrect data", tcDirectProjectGamePlanData.getTcDirectProjectId() == 1
                    || tcDirectProjectGamePlanData.getTcDirectProjectId() == 2);

            assertFalse("the list should not be empty", tcDirectProjectGamePlanData.getSoftwareProjects().isEmpty());
            assertFalse("the list should not be empty", tcDirectProjectGamePlanData.getStudioProjects().isEmpty());

            // detail field retrieval are verified in
            // testRetrieveGamePlanData_accuracy2 and testRetrieveGamePlanData_accuracy3.
        }
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: the tcSubject is null.</p>
     *
     * <p>Expected: throw IllegalArgumentException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveGamePlanData2_null() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        gamePlanServiceBean.retrieveGamePlanData(null, 1L);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: the softwareEntityManager is null.</p>
     *
     * <p>Expected: throw IllegalStateException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testRetrieveGamePlanData2_softwareEntityManager_null() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        gamePlanServiceBean.retrieveGamePlanData(new TCSubject(1l));
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: the studioEntityManager is null.</p>
     *
     * <p>Expected: throw IllegalStateException.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testRetrieveGamePlanData2_studioEntityManager_null() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());

        TCSubject tcSubject = new TCSubject(1l);
        tcSubject.addPrincipal(new RolePrincipal("Cockpit Administrator", 1l));

        gamePlanServiceBean.retrieveGamePlanData(tcSubject);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: no data associated with the given user id.</p>
     *
     * <p>Expected: an empty list should return</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy1() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(100l);

        TCDirectProjectGamePlanData result = gamePlanServiceBean.retrieveGamePlanData(tcSubject, -1);

        assertNull("The return value should null.", result);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id and TC Direct project ID.</p>
     *
     * <p>Expected: Retrieves The specific TC Direct Project game plan data for the given user.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy2() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(1l);

        TCDirectProjectGamePlanData result = gamePlanServiceBean.retrieveGamePlanData(tcSubject, 1L);
        assertNotNull("The list should never null.", result);
        List<SoftwareProjectData> softwareProjectDataList = result.getSoftwareProjects();
        assertNotNull("The list should never null.", softwareProjectDataList);
        assertEquals("The list should contain one element.", 1, softwareProjectDataList.size());
        // verify the data
        SoftwareProjectData softwareProjectData = softwareProjectDataList.get(0);
        assertNotNull("The element should not be null", softwareProjectData);
        assertEquals("incorrect data", 1, softwareProjectData.getTcDirectProjectId());
        assertEquals("incorrect data", 1, softwareProjectData.getProjectId());
        assertEquals("incorrect data", "Project Name 1", softwareProjectData.getProjectName());
        assertNotNull("incorrect data", softwareProjectData.getStartDate());
        assertNotNull("incorrect data", softwareProjectData.getEndDate());
        assertEquals("incorrect data", new Long(1), softwareProjectData.getCreateUserId());
        assertNotNull("incorrect data", softwareProjectData.getDependencyProjectIds());
        assertTrue("incorrect data", softwareProjectData.getDependencyProjectIds().length == 1);
        assertFalse("incorrect data", softwareProjectData.isRepost());
        assertTrue("incorrect data", softwareProjectData.isStarted());
        assertTrue("incorrect data", softwareProjectData.isFinished());
        assertEquals("incorrect data", "Active", softwareProjectData.getProjectStatus());
        assertNull("incorrect data", softwareProjectData.getCurrentPhase());
        assertEquals("incorrect data", "Design", softwareProjectData.getProjectType());


        List<StudioProjectData> studioProjectDataList = result.getStudioProjects();
        assertNotNull("The list should never null.", studioProjectDataList);
        assertTrue("The list should be empty.", studioProjectDataList.isEmpty());
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: Try to retrieve game plan data for the given user id.</p>
     *
     * <p>Expected: Retrieves all the valid project data for the given user id.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy3() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(1l);
        tcSubject.addPrincipal(new RolePrincipal("Copilot", 1l));

        TCDirectProjectGamePlanData result = gamePlanServiceBean.retrieveGamePlanData(tcSubject, 3L);
        assertNull("The return value is null.", result);
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject, long)</code> method.</p>
     *
     * <p>Condition: Admin mode is used.</p>
     *
     * <p>Expected: Retrieves all the valid project data.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Test
    public void testRetrieveGamePlanData2_accuracy4() throws Exception {
        TestHelper.setField(gamePlanServiceBean, "logName", "UnitTest");
        // mimic the EJB initialization.
        gamePlanServiceBean.initialize();

        TestHelper.setField(gamePlanServiceBean, "softwareEntityManager", TestHelper.getSoftwareEntityManager());
        TestHelper.setField(gamePlanServiceBean, "studioEntityManager", TestHelper.getStudioEntityManager());

        TCSubject tcSubject = new TCSubject(2l);
        tcSubject.addPrincipal(new RolePrincipal("Cockpit Administrator", 1l));

        TCDirectProjectGamePlanData result = gamePlanServiceBean.retrieveGamePlanData(tcSubject, 1L);
        assertNotNull("The list should never null.", result);

        assertNotNull("The element should not be null", result);
        assertEquals("incorrect data", 1, result.getTcDirectProjectId());

        assertFalse("the list should not be empty", result.getSoftwareProjects().isEmpty());
        assertFalse("the list should not be empty", result.getStudioProjects().isEmpty());
    }
}
