/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.clientcockpit.CockpitConfigurationException;
import com.topcoder.management.phase.clientcockpit.CockpitPhaseManagementException;
import com.topcoder.management.phase.clientcockpit.CockpitPhaseManager;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.util.cache.SimpleCache;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure test for CockpitPhaseManager.
 * @author hfx
 * @version 1.0
 */
public class CockpitPhaseManagerTests extends TestCase {
    /**
     * The key to get contest manager from JNDI.
     */
    static final String CONTEST_MANAGER_KEY = "Contest_Manager_Key";

    /**
     * Namespace used to create CockpitPhaseManager.
     */
    private static final String NAMESPACE = CockpitPhaseManager.class.getName();

    /**
     * CockpitPhaseManager instance to tests.
     */
    private CockpitPhaseManager target;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadConfig("object-factory.xml");
        TestHelper.loadConfig("jndi-utils.xml");
        TestHelper.loadConfig("memory-usage.xml");
        TestHelper.loadConfig("config.xml");
        target = new CockpitPhaseManager();
        ContestManager contestManager = new MockContestManager();
        target.setContestManager(contestManager );
        // add status
        ContestManager cm = target.getContestManager();
        cm.addContestStatus(createContestStatus(1, "Draft", "Draft status"));
        cm.addContestStatus(createContestStatus(2, "Scheduled",
                "Scheduled status"));
        cm.addContestStatus(createContestStatus(3, "Active", "Active status"));
        cm.addContestStatus(createContestStatus(4, "Action Required",
                "Action Required status"));
        cm.addContestStatus(createContestStatus(5,
                "Insufficient Submissions - ReRun Possible",
                "Insufficient Submissions - ReRun Possible Required status"));
        cm.addContestStatus(createContestStatus(6, "Completed",
                "Completed status"));
        cm.addContestStatus(createContestStatus(7, "In Danger",
                "In Danger status"));
        cm.addContestStatus(createContestStatus(8, "Abandoned",
                "Abandoned status"));
        cm.addContestStatus(createContestStatus(9, "Extended",
                "Extended status"));
        cm.addContestStatus(createContestStatus(10, "Insufficient Submissions",
                "Insufficient Submissions status"));
        cm.addContestStatus(createContestStatus(11, "Repost", "Repost status"));
        cm.addContestStatus(createContestStatus(12, "Cancelled",
                "Cancelled status"));
        cm.addContestStatus(createContestStatus(13, "No Winner Chosen",
                "No Winner Chosen status"));
    }

    /**
     * <p>
     * Creates a ContestStatus instance with given status id, name and
     * description.
     * </p>
     * 
     * @param statusId
     *            the contest status id.
     * @param name
     *            the contest status name.
     * @param description
     *            the contest status description.
     * @return ContestStatus instance.
     */
    private ContestStatus createContestStatus(long statusId, String name,
            String description) {
        ContestStatus status = new ContestStatus();
        status.setContestStatusId(statusId);
        status.setName(name);
        status.setDescription(description);
        status.setStatuses(new ArrayList<ContestStatus>());

        return status;
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception
     *             to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        target = null;
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException expected if namespace is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure1() throws Exception {
        try {
            new CockpitPhaseManager(null);
            fail("IllegalArgumentException expected if namespace is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException expected if namespace is empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure2() throws Exception {
        try {
            new CockpitPhaseManager("   ");
            fail("IllegalArgumentException expected if namespace is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if namespace is empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure3() throws Exception {
        try {
            new CockpitPhaseManager("invalid");
            fail("CockpitConfigurationException expected if namespace is empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if objectFactoryNamespace_miss.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure4() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("objectFactoryNamespace_miss.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if objectFactoryNamespace_miss.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if objectFactoryNamespace_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure5() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("objectFactoryNamespace_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if objectFactoryNamespace_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if objectFactoryNamespace_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure6() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("objectFactoryNamespace_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if objectFactoryNamespace_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if jndiUtilNamespace_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure7() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("jndiUtilNamespace_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if jndiUtilNamespace_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if contestManagerName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure9() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("contestManagerName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if contestManagerName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if contestManagerKey_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure10() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("contestManagerKey_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if contestManagerKey_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if
     * contestManagerKey_required_miss.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure11() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("contestManagerKey_required_miss.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if contestManagerKey_required_miss.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if logName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure12() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("logName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if logName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if cacheContestStatusesKey_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_cacheContestStatusesKey_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("cacheContestStatusesKey_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if cacheContestStatusesKey_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if handlers_miss.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_handlers_miss() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("handlers_miss.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if handlers_miss.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if handlers_property_key_invalid.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_handlers_property_key_invalid1()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("handlers_property_key_invalid.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if handlers_property_key_invalid.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if handlers_property_key_invalid.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_handlers_property_key_invalid2()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("handlers_property_key_invalid.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if handlers_property_key_invalid.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if handlers_property_key_invalid.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_handlers_property_key_invalid3()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("handlers_property_key_invalid.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if handlers_property_key_invalid.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if handlers_property_value_miss.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_handlers_property_value_miss()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("handlers_property_value_miss.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if handlers_property_value_miss.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if handlers_property_value_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_handlers_property_value_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("handlers_property_value_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if handlers_property_value_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if
     * handlers_property_value_invalid.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_handlers_property_value_invalid()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("handlers_property_value_invalid.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if handlers_property_value_invalid.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if draftPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_draftPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("draftPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if draftPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if scheduledPhaseTypeNam_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_scheduledPhaseTypeNam_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("scheduledPhaseTypeNam_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if scheduledPhaseTypeNam_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if activePhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_activePhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("activePhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if activePhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if inDangerPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_inDangerPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("inDangerPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if inDangerPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if
     * insufficientSubmissionsReRunPossiblePhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_insufficientSubmissionsRe_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper
                    .loadConfig("insufficientSubmissionsReRunPossiblePhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if insufficientSubmissionsReRunPossiblePhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if extendedPhaseTypeNam_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_extendedPhaseTypeNam_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("extendedPhaseTypeNam_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if extendedPhaseTypeNam_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if repostPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_repostPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("repostPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if repostPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if
     * insufficientSubmissionsPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_insufficientSubmissionsPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper
                    .loadConfig("insufficientSubmissionsPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if insufficientSubmissionsPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if
     * noWinnerChosenPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_noWinnerChosenPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("noWinnerChosenPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if noWinnerChosenPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if completedPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_completedPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("completedPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if completedPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if abandonedPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_abandonedPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("abandonedPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if abandonedPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if cancelledPhaseTypeName_empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_cancelledPhaseTypeName_empty()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("cancelledPhaseTypeName_empty.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if cancelledPhaseTypeName_empty.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException expected if contestManagerName_CCE.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_contestManagerName_CCE()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("contestManagerName_CCE.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitPhaseManagementException expected if contestManagerName_CCE.");
        } catch (CockpitPhaseManagementException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if contestManagerKey_CCE.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_contestManagerKey_CCE()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("contestManagerKey_CCE.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if contestManagerKey_CCE.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if cacheContestStatusesKey_CCE.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_cacheContestStatusesKey_CCE()
            throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("cacheContestStatusesKey_CCE.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if cacheContestStatusesKey_CCE.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>CockpitPhaseManager(String)</code>.
     * </p>
     * <p>
     * CockpitConfigurationException expected if hander_CCE.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_String_failure_hander_CCE() throws Exception {
        try {
            ConfigManager.getInstance().removeNamespace(NAMESPACE);
            TestHelper.loadConfig("hander_CCE.xml");
            new CockpitPhaseManager(NAMESPACE);
            fail("CockpitConfigurationException expected if hander_CCE.");
        } catch (CockpitConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>CockpitPhaseManager(ContestManager, String, Cache)</code>.
     * </p>
     * <p>
     * IllegalArgumentException expected if ContestManager is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_ContestManager_String_failure1() throws Exception {
        try {
            new CockpitPhaseManager(null, "xxx", new SimpleCache());
            fail("IllegalArgumentException expected if ContestManager is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>getPhases(long contestId)</code>.
     * </p>
     * <p>
     * IllegalStateException if no contest manager has been set up before.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_long_failure1() throws Exception {
        target = new CockpitPhaseManager();
        try {
            target.getPhases(11);
            fail("IllegalStateException if no contest manager has been set up before.");
        } catch (IllegalStateException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>getPhases(long contestId)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException if if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_long_failure2() throws Exception {
        target.setContestManager(new FailureContestManager());
        try {
            target.getPhases(11);
            fail("CockpitPhaseManagementException if if any error occurs.");
        } catch (CockpitPhaseManagementException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>getPhases(long contestId)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException if if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_long_failure3() throws Exception {
        ContestManager contestManager = target.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, createDate(4, 1), repost);
        contest.setStatus(null);
        contest = contestManager.createContest(contest);

        try {
            target.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * This method create date.
     * 
     * @param d
     *            the day
     * @param m
     *            the month
     * @return date created
     */
    private Date createDate(int m, int d) {
        return new GregorianCalendar(2008, 3, 1).getTime();
    }

    /**
     * <p>
     * Test method <code>getPhases(long contestId)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException if if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_long_failure4() throws Exception {
        ContestManager contestManager = target.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, createDate(4, 1), repost);
        contest = contestManager.createContest(contest);

        // remove the "Action Required" status
        contestManager.removeContestStatus(4);

        try {
            target.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Test method <code>getPhases(long contestId)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException if if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_long_failure5() throws Exception {
        ContestManager contestManager = target.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, null, repost);
        contest = contestManager.createContest(contest);

        try {
            target.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Test method <code>getPhases(long[] contestIds)</code>.
     * </p>
     * <p>
     * IllegalArgumentException expected if contestIds is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_longArray_failure1() throws Exception {
        try {
            target.getPhases(null);
            fail("IllegalArgumentException expected if contestIds is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>getPhases(long[] contestIds)</code>.
     * </p>
     * <p>
     * IllegalStateException if no contest manager has been set.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_longArray_failure2() throws Exception {
        try {
            target = new CockpitPhaseManager();
            target.getPhases(new long[] { 111 });
            fail("IllegalStateException if no contest manager has been set up before.");
        } catch (IllegalStateException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>getPhases(long[] contestIds)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getPhases_longArray_failure3() throws Exception {
        target.setContestManager(new FailureContestManager());
        try {
            target.getPhases(new long[] { 111 });
            fail("CockpitPhaseManagementException if if any error occurs.");
        } catch (CockpitPhaseManagementException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>getAllPhaseTypes()</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getAllPhaseTypes_failure1() throws Exception {
        target.setContestManager(new FailureContestManager());
        try {
            target.getAllPhaseTypes();
            fail("CockpitPhaseManagementException if if any error occurs.");
        } catch (CockpitPhaseManagementException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>canStart(Phase phase)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_canStart_failure1() throws Exception {
        try {
            target.canStart(null);
            fail("IllegalArgumentException if phase is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>canStart(Phase phase)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase does not have a phase type.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_canStart_failure2() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);

        try {
            target.canStart(phase);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Test method <code>canStart(Phase phase)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_canStart_failure3() throws Exception {
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        target.registerHandler(new FailurePhaseHandler(), type,
                PhaseOperationEnum.START);

        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        try {

            target.canStart(phase);
            fail("CockpitPhaseManagementException if if any error occurs.");
        } catch (CockpitPhaseManagementException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>start(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_start_failure1() throws Exception {
        try {
            target.start(null, "xxxx");
            fail("IllegalArgumentException if phase is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>start(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase is invalid.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_start_failure2() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        try {

            target.start(phase, "xxxx");
            fail("IllegalArgumentException if phase is invalid.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>start(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if operator is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_start_failure3() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        phase.setPhaseType(type);
        try {
            target.start(phase, null);
            fail("IllegalArgumentException if operator is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>start(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if operator is empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_start_failure4() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        phase.setPhaseType(type);
        try {

            target.start(phase, "   ");
            fail("IllegalArgumentException if operator is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>canEnd(Phase phase)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_canEnd_failure1() throws Exception {
        try {
            target.canEnd(null);
            fail("IllegalArgumentException if phase is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>canEnd(Phase phase)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase does not have a phase type.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_canEnd_failure2() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);

        try {
            target.canEnd(phase);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Test method <code>canEnd(Phase phase)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_canEnd_failure3() throws Exception {
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        target.registerHandler(new FailurePhaseHandler(), type,
                PhaseOperationEnum.END);

        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        try {

            target.canEnd(phase);
            fail("CockpitPhaseManagementException if if any error occurs.");
        } catch (CockpitPhaseManagementException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>end(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_end_failure1() throws Exception {
        try {
            target.end(null, "xxxx");
            fail("IllegalArgumentException if phase is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>end(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if phase is invalid.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_end_failure2() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        try {

            target.end(phase, "xxxx");
            fail("IllegalArgumentException if phase is invalid.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>end(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if operator is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_end_failure3() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        phase.setPhaseType(type);
        try {

            target.end(phase, null);
            fail("IllegalArgumentException if operator is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>end(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if operator is empty.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_end_failure4() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        phase.setPhaseType(type);
        try {

            target.end(phase, "   ");
            fail("IllegalArgumentException if operator is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>end(Phase phase, String operator)</code>.
     * </p>
     * <p>
     * IllegalStateException if contest manager not set.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_end_failure5() throws Exception {
        target = new CockpitPhaseManager();

        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        phase.setPhaseType(type);
        try {

            target.end(phase, "xxxx");
            fail("IllegalStateException if contest manager not set.");
        } catch (IllegalStateException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if any argument is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_registerHandler_failure1() throws Exception {
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        try {
            target.registerHandler(null, type, PhaseOperationEnum.START);
            fail("IllegalArgumentException if any argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if any argument is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_registerHandler_failure2() throws Exception {
        // create a phase type
        PhaseType type = null;
        PhaseHandler handler = new MockPhaseHandler();
        try {
            target.registerHandler(handler, type, PhaseOperationEnum.START);
            fail("IllegalArgumentException if any argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>registerHandler(PhaseHandler, PhaseType, PhaseOperationEnum)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if any argument is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_registerHandler_failure3() throws Exception {
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        PhaseHandler handler = new MockPhaseHandler();
        try {
            target.registerHandler(handler, type, null);
            fail("IllegalArgumentException if any argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>unregisterHandler(PhaseType type, PhaseOperationEnum operation)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if any argument is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_unregisterHandler_failure1() throws Exception {
        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");
        try {
            target.unregisterHandler(type, null);
            fail("IllegalArgumentException if any argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>unregisterHandler(PhaseType type, PhaseOperationEnum operation)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if any argument is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_unregisterHandler_failure2() throws Exception {
        // create a phase type
        PhaseType type = null;
        try {
            target.unregisterHandler(type, PhaseOperationEnum.START);
            fail("IllegalArgumentException if any argument is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>getHandlerRegistrationInfo(PhaseHandler handler)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if handler is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_getHandlerRegistrationInfo_failure1() throws Exception {
        try {
            target.getHandlerRegistrationInfo(null);
            fail("IllegalArgumentException if handler is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>setContestManager(ContestManager contestManager)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if contestManager is null.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setContestManager_failure1() throws Exception {
        try {
            target.setContestManager((ContestManager) null);
            fail("IllegalArgumentException if contestManager is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>setContestManager(String contestManagerName)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if <code>contestManagerName</code> is
     * <code>null</code> or an empty string.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setContestManager_String_failure1() throws Exception {
        try {
            target.setContestManager((String) null);
            fail("IllegalArgumentException if contestManagerName is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>setContestManager(String contestManagerName)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if <code>contestManagerName</code> is
     * <code>null</code> or an empty string.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setContestManager_String_failure2() throws Exception {
        try {
            target.setContestManager("  ");
            fail("IllegalArgumentException if contestManagerName is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method <code>setContestManager(String contestManagerName)</code>.
     * </p>
     * <p>
     * CockpitPhaseManagementException if any error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setContestManager_String_failure3() throws Exception {
        try {
            target.setContestManager("invalid");
            fail("CockpitPhaseManagementException if any error occurs.");
        } catch (CockpitPhaseManagementException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>setHandlers(Map<HandlerRegistryInfo, PhaseHandler> handlers)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if <code>handlers</code> is <code>null</code>,
     * or if <code>handlers</code> contains <code>null</code> keys or
     * values.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setHandlers_failure1() throws Exception {
        try {
            target.setHandlers(null);
            fail("IllegalArgumentException if handlers is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>setHandlers(Map<HandlerRegistryInfo, PhaseHandler> handlers)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if <code>handlers</code> is <code>null</code>,
     * or if <code>handlers</code> contains <code>null</code> keys or
     * values.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setHandlers_failure2() throws Exception {
        Map<HandlerRegistryInfo, PhaseHandler> newHandlers = new HashMap<HandlerRegistryInfo, PhaseHandler>();
        newHandlers.put(null, new MockPhaseHandler());
        try {
            target.setHandlers(newHandlers);
            fail("IllegalArgumentException if handlers contains null key.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test method
     * <code>setHandlers(Map<HandlerRegistryInfo, PhaseHandler> handlers)</code>.
     * </p>
     * <p>
     * IllegalArgumentException if <code>handlers</code> is <code>null</code>,
     * or if <code>handlers</code> contains <code>null</code> keys or
     * values.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_setHandlers_failure3() throws Exception {
        // create map
        Map<HandlerRegistryInfo, PhaseHandler> newHandlers = new HashMap<HandlerRegistryInfo, PhaseHandler>();
        PhaseType phaseType = new PhaseType(1, "Extended");
        HandlerRegistryInfo info = new HandlerRegistryInfo(phaseType,
                PhaseOperationEnum.START);
        newHandlers.put(info, null);
        try {
            target.setHandlers(newHandlers);
            fail("IllegalArgumentException if handlers contains null value.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
}
