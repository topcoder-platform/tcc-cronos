/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.phase.PhaseOperationEnum;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * Unit test case of {@link CockpitPhaseManager} for failure.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CockpitPhaseManagerFailureTest extends TestCase {
    /**
     * <p>
     * Represents configuration namespace used for testing.
     * </p>
     */
    private static final String JNDI_NAMESPACE = "CockpitPhaseManagerJNDI";

    /**
     * <p>
     * Represents CockpitPhaseManager instance to test against.
     * </p>
     */
    private CockpitPhaseManager manager;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(CockpitPhaseManagerFailureTest.class);

        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "cockpit-jndi.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "object-factory.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "jndi-utils.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "memory-usage.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "cockpit-invalid.xml");

        manager = new CockpitPhaseManager(JNDI_NAMESPACE);
        TestHelper.initContestStatuses(manager.getContestManager());
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        manager = null;
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when
     * <code>namespace</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureNullNamespace() throws Exception {
        try {
            new CockpitPhaseManager(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when
     * <code>namespace</code> is empty.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureEmptyNamespace() throws Exception {
        try {
            new CockpitPhaseManager("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when
     * <code>namespace</code> is unknown.
     * </p>
     * <p>
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureUnknownNamespace() throws Exception {
        try {
            new CockpitPhaseManager("unknown");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when underlying
     * <code>ContestManager</code> instance is obtained via JNDI.
     * </p>
     * <p>
     * Property &quot;jndiUtilNamespace&quot; is not specified in config and creating <code>JNDIUtil</code>
     * using its default namespace <code>JNDIUtils.NAMESPACE</code> is failed.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureJNDIUtil1() throws Exception {
        ConfigManager.getInstance().removeNamespace(JNDIUtils.NAMESPACE);

        try {
            new CockpitPhaseManager(JNDI_NAMESPACE);
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when underlying
     * <code>ContestManager</code> instance is obtained via JNDI.
     * </p>
     * <p>
     * Property &quot;jndiUtilNamespace&quot; is specified in config and creating <code>JNDIUtil</code>
     * using configured namespace is failed. <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureJNDIUtil2() throws Exception {
        try {
            new CockpitPhaseManager("jndi_ns_failure");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when unable to create
     * <code>ContestManager</code> instance.
     * </p>
     * <p>
     * The value of property &quot;contestManagerName&quot; is not bound in JNDI context.
     * <code>CockpitPhaseManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureContestManagerJNDI1()
        throws Exception {
        try {
            new CockpitPhaseManager("jndi_naming_failure");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when unable to create
     * <code>ContestManager</code> instance.
     * </p>
     * <p>
     * The value of property &quot;contestManagerName&quot; in JNDI context is bound to non ContestManager
     * instance. <code>CockpitPhaseManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureContestManagerJNDI2()
        throws Exception {
        try {
            new CockpitPhaseManager("jndi_invalid_object_failure");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when underlying
     * <code>ContestManager</code> instance is created by <code>ObjectFactory</code>.
     * </p>
     * <p>
     * When &quot;contestManagerName&quot; property is not specified, the property
     * &quot;contestManagerKey&quot; is becoming required but it is missing from configuration.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_OF_FailureMissingContestManagerKey()
        throws Exception {
        try {
            manager = new CockpitPhaseManager("missing_contest_manager_key");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when underlying
     * <code>ContestManager</code> instance is created by <code>ObjectFactory</code>.
     * </p>
     * <p>
     * When &quot;contestManagerName&quot; property is not specified, the property
     * &quot;contestManagerKey&quot; is becoming required but it is empty in configuration.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_OF_FailureEmptyContestManagerKey()
        throws Exception {
        try {
            manager = new CockpitPhaseManager("empty_contest_manager_key");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when it is failed to
     * create <code>ObjectFactory</code> instance.
     * </p>
     * <p>
     * The &quot;objectFactoryNamespace&quot; property refer to unknown namespace.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateObjectFactoryUnknownNamespace()
        throws Exception {
        ConfigManager.getInstance().removeNamespace("ObjectFactory");

        try {
            new CockpitPhaseManager(JNDI_NAMESPACE);
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when it is failed to
     * create <code>ObjectFactory</code> instance.
     * </p>
     * <p>
     * The configured object in <code>ObjectFactory</code> configuration namespace is invalid.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateObjectFactoryIllegalReference()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_object_factory");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when underlying
     * <code>ContestManager</code> instance is created by <code>ObjectFactory</code>.
     * </p>
     * <p>
     * The configured object in <code>ObjectFactory</code> configuration namespace is not an instance of
     * <code>ContestManager</code>. <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_OF_FailureCreateObjectNotTypeInstance1()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_object_type1");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when underlying
     * <code>PhaseHandler</code> instance is created by <code>ObjectFactory</code>.
     * </p>
     * <p>
     * The configured object in <code>ObjectFactory</code> configuration namespace is not an instance of
     * <code>PhaseHandler</code>. <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_OF_FailureCreateObjectNotTypeInstance2()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_object_type2");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when an invalid class
     * specification is configured in <code>ObjectFactory</code> configuration namespace.
     * </p>
     * <p>
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureInvalidClass() throws Exception {
        try {
            new CockpitPhaseManager("invalid_class_of");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when &quot;handlers&quot;
     * is not specified in configuration namespace.
     * </p>
     * <p>
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureMissingHandlers() throws Exception {
        try {
            new CockpitPhaseManager("missing_handlers");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when fails to create
     * <code>HandlerRegistryInfo</code> instance.
     * </p>
     * <p>
     * The sub-property of &quot;handlers&quot; property has invalid name format. The format should be
     * 'phaseTypeId,phaseTypeName,phaseOperationEnumName'. <code>CockpitConfigurationException</code> is
     * expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateHandlerRegistryInfo1()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_handlers1");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when fails to create
     * <code>HandlerRegistryInfo</code> instance.
     * </p>
     * <p>
     * The phaseTypeId part in the sub-property name of &quot;handlers&quot; property is not parseable to long
     * value. <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateHandlerRegistryInfo2()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_handlers2");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when fails to create
     * <code>HandlerRegistryInfo</code> instance.
     * </p>
     * <p>
     * The phaseOperationEnumName part in the sub-property name of &quot;handlers&quot; property is not any of
     * 'start, end or cancel' (case-insensitive). <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateHandlerRegistryInfo3()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_handlers3");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when fails to create
     * <code>HandlerRegistryInfo</code> instance.
     * </p>
     * <p>
     * The phaseType id part in the sub-property name of &quot;handlers&quot; property is negative value.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateHandlerRegistryInfo4()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_handlers4");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when fails to create
     * <code>HandlerRegistryInfo</code> instance.
     * </p>
     * <p>
     * The phaseType name part in the sub-property name of &quot;handlers&quot; property is empty.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateHandlerRegistryInfo5()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_handlers5");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when fails to create
     * <code>HandlerRegistryInfo</code> instance.
     * </p>
     * <p>
     * The phaseOperationEnumName part in the sub-property name of &quot;handlers&quot; property is empty.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreateHandlerRegistryInfo6()
        throws Exception {
        try {
            new CockpitPhaseManager("invalid_handlers6");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when fails to create
     * <code>PhaseHandler</code> instance.
     * </p>
     * <p>
     * The sub-property value of &quot;handlers&quot; property is empty.
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureCreatePhaseHandler() throws Exception {
        try {
            new CockpitPhaseManager("invalid_handlers7");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when
     * &quot;objectFactoryNamespace&quot; is missing.
     * </p>
     * <p>
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureMissingObjectFactoryNamespace()
        throws Exception {
        try {
            new CockpitPhaseManager("missing_of_namespace");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>CockpitPhaseManager(String namespace)</code> method for failure when
     * &quot;objectFactoryNamespace&quot; is empty.
     * </p>
     * <p>
     * <code>CockpitConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor2_FailureEmptyObjectFactoryNamespace()
        throws Exception {
        try {
            new CockpitPhaseManager("empty_of_namespace");
            fail("CockpitConfigurationException is expected");
        } catch (CockpitConfigurationException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests
     * <code>CockpitPhaseManager(ContestManager contestManager, String logName, Cache cachedContestStatuses)</code>
     * constructor for failure when <code>contestManager</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor3_FailureNullContestManager() throws Exception {
        try {
            new CockpitPhaseManager(null, "myLog", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests
     * <code>CockpitPhaseManager(ContestManager contestManager, String logName, Cache cachedContestStatuses)</code>
     * constructor for failure when <code>logName</code> is empty.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCtor3_FailureEmptyLogName() throws Exception {
        try {
            new CockpitPhaseManager(new MockContestManager(), " ", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for failure when no contest manager has been set.
     * </p>
     * <p>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_NoContestManager() throws Exception {
        manager = new CockpitPhaseManager();

        try {
            manager.getPhases(1);
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for failure when fails on retrieving contest from
     * contest manager.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_FailureRetrieveContest()
        throws Exception {
        ContestManager contestManager = new MockContestManager() {
                public Contest getContest(long contestId)
                    throws ContestManagementException {
                    throw new ContestManagementException("This is thrown for test only");
                }
            };

        manager.setContestManager(contestManager);

        try {
            manager.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for failure when fails on retrieving all contest
     * statuses from contest manager.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_FailureRetrieveAllContestStatuses()
        throws Exception {
        ContestManager contestManager = new MockContestManager() {
                public List<ContestStatus> getAllContestStatuses()
                    throws ContestManagementException {
                    throw new ContestManagementException("This is thrown for test only");
                }
            };

        manager.setContestManager(contestManager);
        TestHelper.initContestStatuses(contestManager);

        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), repost);
        contest = contestManager.createContest(contest);

        try {
            manager.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for failure when contest with contestId has no
     * contest status.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_FailureNoContestStatus()
        throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), repost);
        contest.setStatus(null);
        contest = contestManager.createContest(contest);

        try {
            manager.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for failure when no appropriate contest status is
     * found in contest manager for the mapped phase.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_FailureNoMatchingContestStatus()
        throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), repost);
        contest = contestManager.createContest(contest);

        // remove the "Action Required" status
        contestManager.removeContestStatus(4);

        try {
            manager.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long contestId)</code> method for failure when no start date is set in the
     * contest found in contest manager.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases_FailureNoStartDate() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, null, repost);
        contest = contestManager.createContest(contest);

        try {
            manager.getPhases(1);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for failure when <code>contestIds</code> is
     * <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_NullIds() throws Exception {
        try {
            manager.getPhases(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for failure when no contest manager has been
     * set.
     * </p>
     * <p>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_NoContestManager() throws Exception {
        manager = new CockpitPhaseManager();

        try {
            manager.getPhases(new long[] {1});
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for failure when fails on retrieving contest
     * from contest manager.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_FailureRetrieveContest()
        throws Exception {
        ContestManager contestManager = new MockContestManager() {
                public Contest getContest(long contestId)
                    throws ContestManagementException {
                    throw new ContestManagementException("This is thrown for test only");
                }
            };

        manager.setContestManager(contestManager);

        try {
            manager.getPhases(new long[] {1, 2});
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for failure when fails on retrieving all
     * contest statuses from contest manager.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_FailureRetrieveAllContestStatuses()
        throws Exception {
        ContestManager contestManager = new MockContestManager() {
                public List<ContestStatus> getAllContestStatuses()
                    throws ContestManagementException {
                    throw new ContestManagementException("This is thrown for test only");
                }
            };

        manager.setContestManager(contestManager);
        TestHelper.initContestStatuses(contestManager);

        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), repost);
        contest = contestManager.createContest(contest);

        try {
            manager.getPhases(new long[] {1});
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for failure when contest with contestId has no
     * contest status.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_FailureNoContestStatus()
        throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), repost);
        contest.setStatus(null);
        contest = contestManager.createContest(contest);

        try {
            manager.getPhases(new long[] {1});
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for failure when no appropriate contest status
     * is found in contest manager for the mapped phase.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_FailureNoMatchingContestStatus()
        throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), repost);
        contest = contestManager.createContest(contest);

        // remove the "Action Required" status
        contestManager.removeContestStatus(4);

        try {
            manager.getPhases(new long[] {1});
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getPhases(long[] contestIds)</code> method for failure when no start date is set in the
     * contest found in contest manager.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetPhases2_FailureNoStartDate() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, null, repost);
        contest = contestManager.createContest(contest);

        try {
            manager.getPhases(new long[] {1});
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getAllPhaseTypes()</code> method for failure when no contest manager has been set.
     * </p>
     * <p>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetAllPhaseTypes_NoContestManager()
        throws Exception {
        manager = new CockpitPhaseManager();

        try {
            manager.getAllPhaseTypes();
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getAllPhaseTypes()</code> method for failure when fails on retrieving all contest
     * statuses from contest manager.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testGetAllPhaseTypes_FailureRetrieveAllContestStatuses()
        throws Exception {
        ContestManager contestManager = new MockContestManager() {
                public List<ContestStatus> getAllContestStatuses()
                    throws ContestManagementException {
                    throw new ContestManagementException("This is thrown for test only");
                }
            };

        manager.setContestManager(contestManager);
        TestHelper.initContestStatuses(contestManager);

        ContestStatus repost = contestManager.getContestStatus(11);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), repost);
        contest = contestManager.createContest(contest);

        try {
            manager.getAllPhaseTypes();
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>canStart(Phase phase)</code> method for failure when a phase handler fails on performing
     * the operation on given phase.
     * </p>
     * <p>
     * <code>CockpitPhaseManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanStart_FailurePhaseHandling() throws Exception {
        // clear the current handlers.
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // create a phase type
        PhaseType type = new PhaseType(1, "Draft");

        PhaseHandler phaseHandler = new MockPhaseHandler() {
                public boolean canPerform(Phase phase)
                    throws PhaseHandlingException {
                    throw new PhaseHandlingException("This is thrown for test only");
                }
            };

        manager.registerHandler(phaseHandler, type, PhaseOperationEnum.START);

        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        try {
            manager.canStart(phase);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>canStart(Phase phase)</code> method for failure when <code>phase</code> is
     * <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanStart_FailureNullPhase() throws Exception {
        try {
            manager.canStart(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>canStart(Phase phase)</code> method for failure when <code>phase</code> does not have
     * associated phaseType.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanStart_FailureNullPhaseType() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);

        try {
            manager.canStart(phase);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when <code>phase</code>
     * is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_FailureNullPhase() throws Exception {
        try {
            manager.start(null, "TCS");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when <code>operator</code>
     * is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_FailureNullOperator() throws Exception {
        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        try {
            manager.start(phase, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when <code>operator</code>
     * is empty.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_FailureEmptyOperator() throws Exception {
        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        try {
            manager.start(phase, " ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when no contest manager has
     * been set.
     * </p>
     * <p>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_NoContestManager() throws Exception {
        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        manager = new CockpitPhaseManager();

        try {
            manager.start(phase, "TCS");
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when <code>phase</code>
     * has no phase type.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_NoPhaseType() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);

        try {
            manager.start(phase, "TCS");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when contest manager fails
     * on updating contest status.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_FailureUpdateContestStatus()
        throws Exception {
        ContestManager contestManager = new MockContestManager() {
                @Override
                public void updateContestStatus(long contestId, long newStatusId)
                    throws ContestManagementException {
                    throw new ContestManagementException("This is thrown for test only");
                }
            };

        TestHelper.initContestStatuses(contestManager);
        manager.setContestManager(contestManager);

        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Draft");

        try {
            manager.start(phase, "TCS");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when phase handler fails on
     * performing operation.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_FailurePhaseHandling() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Draft");

        PhaseHandler handler = new MockPhaseHandler() {
                @Override
                public void perform(Phase phase, String operator)
                    throws PhaseHandlingException {
                    throw new PhaseHandlingException("This is thrown for test only");
                }
            };

        manager.registerHandler(handler, phase.getPhaseType(), PhaseOperationEnum.START);

        try {
            manager.start(phase, "TCS");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>start(Phase phase, String operator)</code> method for failure when no contest status is
     * found for the given phase type.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testStart_FailureNoContestStatus() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), draft);
        contest = contestManager.createContest(contest);

        PhaseType type = new PhaseType(1, "Draft");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        project.setId(contest.getContestId());

        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        contestManager.removeContestStatus(1);

        try {
            manager.start(phase, "TCS");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>canEnd(Phase phase)</code> method for failure when a phase handler fails on performing
     * the operation on given phase.
     * </p>
     * <p>
     * <code>CockpitPhaseManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanEnd_FailurePhaseHandling() throws Exception {
        // clear the current handlers.
        manager.setHandlers(new HashMap<HandlerRegistryInfo, PhaseHandler>());

        // create a phase type
        PhaseType type = new PhaseType(3, "Scheduled");

        PhaseHandler phaseHandler = new MockPhaseHandler() {
                public boolean canPerform(Phase phase)
                    throws PhaseHandlingException {
                    throw new PhaseHandlingException("This is thrown for test only");
                }
            };

        manager.registerHandler(phaseHandler, type, PhaseOperationEnum.END);

        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        try {
            manager.canEnd(phase);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>canEnd(Phase phase)</code> method for failure when <code>phase</code> is
     * <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanEnd_FailureNullPhase() throws Exception {
        try {
            manager.canEnd(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>canEnd(Phase phase)</code> method for failure when <code>phase</code> does not have
     * associated phaseType.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCanEnd_FailureNullPhaseType() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);

        try {
            manager.canEnd(phase);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when <code>phase</code> is
     * <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_FailureNullPhase() throws Exception {
        try {
            manager.end(null, "TCS");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when <code>operator</code>
     * is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_FailureNullOperator() throws Exception {
        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        try {
            manager.end(phase, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when <code>operator</code>
     * is empty.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_FailureEmptyOperator() throws Exception {
        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        try {
            manager.end(phase, " ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when no contest manager has
     * been set.
     * </p>
     * <p>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_NoContestManager() throws Exception {
        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);
        manager = new CockpitPhaseManager();

        try {
            manager.end(phase, "TCS");
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when <code>phase</code> has
     * no phase type.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_NoPhaseType() throws Exception {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, 10);

        try {
            manager.end(phase, "TCS");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when contest manager fails on
     * updating contest status.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_FailureUpdateContestStatus() throws Exception {
        ContestManager contestManager = new MockContestManager() {
                @Override
                public void updateContestStatus(long contestId, long newStatusId)
                    throws ContestManagementException {
                    throw new ContestManagementException("This is thrown for test only");
                }
            };

        TestHelper.initContestStatuses(contestManager);
        manager.setContestManager(contestManager);

        ContestStatus scheduled = contestManager.getContestStatus(2);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), scheduled);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Scheduled");

        try {
            manager.end(phase, "TCS");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when phase handler fails on
     * performing operation.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_FailurePhaseHandling() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus scheduled = contestManager.getContestStatus(2);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), scheduled);
        contest = contestManager.createContest(contest);

        Project project = manager.getPhases(1);
        Phase phase = TestHelper.findPhaseByTypeName(project.getAllPhases(), "Scheduled");

        PhaseHandler handler = new MockPhaseHandler() {
                @Override
                public void perform(Phase phase, String operator)
                    throws PhaseHandlingException {
                    throw new PhaseHandlingException("This is thrown for test only");
                }
            };

        manager.registerHandler(handler, phase.getPhaseType(), PhaseOperationEnum.END);

        try {
            manager.end(phase, "TCS");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>end(Phase phase, String operator)</code> method for failure when no contest status is
     * found for the given phase type.
     * </p>
     * <p>
     * CockpitPhaseManagementException is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testEnd_FailureNoContestStatus() throws Exception {
        ContestManager contestManager = manager.getContestManager();
        ContestStatus scheduled = contestManager.getContestStatus(2);
        Contest contest = TestHelper.createContest(1, TestHelper.createDate(2008, 3, 20), scheduled);
        contest = contestManager.createContest(contest);

        PhaseType type = new PhaseType(2, "Scheduled");
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        project.setId(contest.getContestId());

        Phase phase = new Phase(project, 10);
        phase.setPhaseType(type);

        contestManager.removeContestStatus(2);

        try {
            manager.end(phase, "TCS");
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum op)</code>
     * method for failure when <code>handler</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testRegisterHandler_FailureNullHandler() {
        PhaseType type = new PhaseType(1, "Repost");
        HandlerRegistryInfo info = new HandlerRegistryInfo(type, PhaseOperationEnum.START);

        try {
            manager.registerHandler(null, info.getType(), info.getOperation());
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum op)</code>
     * method for failure when <code>type</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testRegisterHandler_FailureNullType() {
        try {
            manager.registerHandler(new MockPhaseHandler(), null, PhaseOperationEnum.START);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum op)</code>
     * method for failure when <code>op</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testRegisterHandler_FailureNullOperation() {
        try {
            manager.registerHandler(new MockPhaseHandler(), new PhaseType(1, "Active"), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>unregisterHandler(PhaseType type, PhaseOperationEnum op)</code> method for failure when
     * <code>type</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testUnregisterHandler_FailureNullType() {
        try {
            manager.unregisterHandler(null, PhaseOperationEnum.END);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>unregisterHandler(PhaseType type, PhaseOperationEnum op)</code> method for failure when
     * <code>op</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testUnregisterHandler_FailureNullOperation() {
        try {
            manager.unregisterHandler(new PhaseType(1, "Repost"), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>getHandlerRegistrationInfo(PhaseHandler handler)</code> method for failure when
     * <code>handler</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testGetHandlerRegistrationInfo_FailureNullHandler() {
        try {
            manager.getHandlerRegistrationInfo(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setContestManager(ContestManager contestManager)</code> method for failure when
     * <code>contestManager</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetContestManager1_FailureNullContestManager() {
        try {
            manager.setContestManager((ContestManager) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setContestManager(String contestManagerName)</code> method for failure when
     * <code>contestManagerName</code> is <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testSetContestManager2_FailureNullName()
        throws Exception {
        try {
            manager.setContestManager((String) null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setContestManager(String contestManagerName)</code> method for failure when
     * <code>contestManagerName</code> is empty.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testSetContestManager2_FailureEmptyName()
        throws Exception {
        try {
            manager.setContestManager(" ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setContestManager(String contestManagerName)</code> method for failure when obtaining
     * <code>ContestManager</code> from JNDI using <code>contestManagerName</code>.
     * </p>
     * <p>
     * Creating <code>JNDIUtil</code> using its default namespace <code>JNDIUtils.NAMESPACE</code> is
     * failed. <code>CockpitPhaseManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testSetContestManager2_FailureJNDIUtil1()
        throws Exception {
        ConfigManager.getInstance().removeNamespace(JNDIUtils.NAMESPACE);

        try {
            manager.setContestManager(CockpitPhaseManagerTest.CONTEST_MANAGER_JNDI_NAME);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setContestManager(String contestManagerName)</code> method for failure when obtaining
     * <code>ContestManager</code> from JNDI using <code>contestManagerName</code>.
     * </p>
     * <p>
     * The value of <code>contestManagerName</code> is not bound in JNDI context.
     * <code>CockpitPhaseManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testSetContestManager2_FailureContestManagerJNDI1()
        throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(JNDIUtils.NAMESPACE);
        cm.setProperty(JNDIUtils.NAMESPACE, "context.default.factory", InvalidObjectContextFactory.class.getName());

        try {
            manager.setContestManager(CockpitPhaseManagerTest.CONTEST_MANAGER_JNDI_NAME);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setContestManager(String contestManagerName)</code> method for failure when obtaining
     * <code>ContestManager</code> from JNDI using <code>contestManagerName</code>.
     * </p>
     * <p>
     * The value of <code>contestManagerName</code> in JNDI context is bound to non ContestManager instance.
     * <code>CockpitPhaseManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testSetContestManager2_FailureContestManagerJNDI2()
        throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(JNDIUtils.NAMESPACE);
        cm.setProperty(JNDIUtils.NAMESPACE, "context.default.factory", InvalidObjectContextFactory.class.getName());

        try {
            manager.setContestManager(CockpitPhaseManagerTest.CONTEST_MANAGER_JNDI_NAME);
            fail("CockpitPhaseManagementException is expected");
        } catch (CockpitPhaseManagementException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setHandlers(Map handlers)</code> method for failure when <code>handlers</code> is
     * <code>null</code>.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetHandlers_FailureNullHandlers() {
        try {
            manager.setHandlers(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setHandlers(Map handlers)</code> method for failure when <code>handlers</code> contains
     * <code>null</code> key.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetHandlers_FailureHandlersContainsNullKey() {
        Map<HandlerRegistryInfo, PhaseHandler> newHandlers = new HashMap<HandlerRegistryInfo, PhaseHandler>();
        newHandlers.put(null, new MockPhaseHandler());

        try {
            manager.setHandlers(newHandlers);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests <code>setHandlers(Map handlers)</code> method for failure when <code>handlers</code> contains
     * <code>null</code> value.
     * </p>
     * <p>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetHandlers_FailureHandlersContainsNullValue() {
        Map<HandlerRegistryInfo, PhaseHandler> newHandlers = new HashMap<HandlerRegistryInfo, PhaseHandler>();
        PhaseType phaseType = new PhaseType(1, "Extended");
        HandlerRegistryInfo info = new HandlerRegistryInfo(phaseType, PhaseOperationEnum.START);
        newHandlers.put(info, null);

        try {
            manager.setHandlers(newHandlers);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }
}
