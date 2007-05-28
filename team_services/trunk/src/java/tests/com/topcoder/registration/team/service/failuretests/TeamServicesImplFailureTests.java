/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.failuretests;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.offer.Offer;
import com.topcoder.management.team.offer.OfferManagerException;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.registration.team.service.TeamServiceConfigurationException;
import com.topcoder.registration.team.service.TeamServices;
import com.topcoder.registration.team.service.TeamServicesException;
import com.topcoder.registration.team.service.UnknownEntityException;
import com.topcoder.registration.team.service.impl.TeamServicesImpl;

import junit.framework.TestCase;
/**
 * Failure tests for TeamServicesImpl class.
 * @author slion
 * @version 1.0
 */
public class TeamServicesImplFailureTests extends TestCase {
    /**
     * Represents the valid config file path.
     */
    private static final String VALID_CONFIG = "test_files/failure/config.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/invalid1.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/invalid2.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/invalid3.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/invalid4.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/invalid5.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/invalid6.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/invalid7.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/invalid8.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG9 = "test_files/failure/invalid9.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG10 = "test_files/failure/invalid10.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG11 = "test_files/failure/invalid11.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG12 = "test_files/failure/invalid12.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG13 = "test_files/failure/invalid13.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG14 = "test_files/failure/invalid14.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG15 = "test_files/failure/invalid15.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG16 = "test_files/failure/invalid16.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG17 = "test_files/failure/invalid17.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG18 = "test_files/failure/invalid18.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG19 = "test_files/failure/invalid19.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG20 = "test_files/failure/invalid20.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG21 = "test_files/failure/invalid21.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG22 = "test_files/failure/invalid22.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG23 = "test_files/failure/invalid23.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG24 = "test_files/failure/invalid24.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG25 = "test_files/failure/invalid25.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG26 = "test_files/failure/invalid26.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG27 = "test_files/failure/invalid27.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG28 = "test_files/failure/invalid28.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG29 = "test_files/failure/invalid29.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG30 = "test_files/failure/invalid30.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG31 = "test_files/failure/invalid31.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG32 = "test_files/failure/invalid32.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG33 = "test_files/failure/invalid33.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG34 = "test_files/failure/invalid34.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG35 = "test_files/failure/invalid35.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG36 = "test_files/failure/invalid36.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG37 = "test_files/failure/invalid37.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG38 = "test_files/failure/invalid38.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG39 = "test_files/failure/invalid39.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG40 = "test_files/failure/invalid40.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG41 = "test_files/failure/invalid41.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG42 = "test_files/failure/invalid42.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG43 = "test_files/failure/invalid43.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG44 = "test_files/failure/invalid44.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG45 = "test_files/failure/invalid45.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG46 = "test_files/failure/invalid46.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG47 = "test_files/failure/invalid47.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG48 = "test_files/failure/invalid48.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG49 = "test_files/failure/invalid49.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG50 = "test_files/failure/invalid50.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG51 = "test_files/failure/invalid51.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG52 = "test_files/failure/invalid52.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG53 = "test_files/failure/invalid53.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG54 = "test_files/failure/invalid54.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG55 = "test_files/failure/invalid55.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG56 = "test_files/failure/invalid56.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG57 = "test_files/failure/invalid57.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG58 = "test_files/failure/invalid58.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG59 = "test_files/failure/invalid59.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG60 = "test_files/failure/invalid60.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG61 = "test_files/failure/invalid61.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG62 = "test_files/failure/invalid62.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG63 = "test_files/failure/invalid63.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG64 = "test_files/failure/invalid64.xml";

    /**
     * Represents the invalid config file path.
     */
    private static final String INVALID_CONFIG65 = "test_files/failure/invalid65.xml";

    /**
     * Represents the TeamHeader instance for testing.
     */
    private TeamHeader header = null;

    /**
     * Represents the TeamPosition instance for testing.
     */
    private TeamPosition position = null;

    /**
     * Represents the Offer instance for testing.
     */
    private Offer offer = null;

    /**
     * Represents the TeamServicesImpl instance for testing.
     */
    private TeamServices services = null;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.loadConfiguration(VALID_CONFIG);
        services = new TeamServicesImpl();
        header = new TeamHeader();
        position = new TeamPosition();
        offer = new Offer();
    }

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        services = null;
        header = null;
        position = null;
        offer = null;
    }

    /**
     * Tests TeamServicesImpl(String namespace) method without existence of namespace,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_UnknownNamespace() {
        try {
            TestHelper.clearConfiguration();
            new TeamServicesImpl();
            fail("testTeamServicesImpl_UnknownNamespace is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_UnknownNamespace.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig1() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig1 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig1.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig2() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig2 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig2.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig3() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig3 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig3.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig4() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig4 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig4.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig5() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig5 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig5.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig6() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig6 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig6.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig7() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig7 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig7.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig8() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig8 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig8.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig9() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG9);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig9 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig9.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig10() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG10);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig10 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig10.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig11() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG11);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig11 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig11.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig12() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG12);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig12 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig12.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig13() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG13);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig13 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig13.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig14() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG14);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig14 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig14.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig15() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG15);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig15 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig15.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig16() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG16);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig16 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig16.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig17() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG17);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig17 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig17.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig18() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG18);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig18 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig18.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig19() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG19);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig19 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig19.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig20() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG20);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig20 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig20.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig21() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG21);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig21 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig21.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig22() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG22);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig22 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig22.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig23() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG23);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig23 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig23.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig24() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG24);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig24 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig24.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig25() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG25);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig25 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig25.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig26() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG26);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig26 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig26.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig27() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG27);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig27 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig27.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig28() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG28);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig28 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig28.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig29() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG29);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig29 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig29.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig30() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG30);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig30 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig30.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig31() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG31);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig31 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig31.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig32() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG32);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig32 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig32.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig33() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG33);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig33 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig33.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig34() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG34);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig34 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig34.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig35() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG35);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig35 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig35.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig36() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG36);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig36 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig36.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig37() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG37);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig37 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig37.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig38() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG38);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig38 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig38.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig39() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG39);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig39 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig39.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig40() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG40);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig40 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig40.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig41() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG41);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig41 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig41.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig42() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG42);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig42 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig42.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig43() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG43);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig43 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig43.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig44() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG44);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig44 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig44.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig45() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG45);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig45 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig45.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig46() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG46);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig46 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig46.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig47() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG47);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig47 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig47.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig48() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG48);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig48 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig48.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig49() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG49);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig49 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig49.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig50() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG50);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig50 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig50.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig51() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG51);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig51 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig51.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig52() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG52);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig52 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig52.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig53() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG53);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig53 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig53.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig54() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG54);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig54 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig54.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig55() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG55);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig55 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig55.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig56() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG56);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig56 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig56.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig57() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG57);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig57 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig57.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig58() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG58);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig58 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig58.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig59() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG59);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig59 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig59.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig60() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG60);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig60 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass

        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig60.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig61() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG61);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig61 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig61.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig62() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG62);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig62 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig62.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig63() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG63);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig63 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig63.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig64() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG64);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig64 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig64.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with invalid configuration,
     * TeamServiceConfigurationException should be thrown.
     */
    public void testTeamServicesImpl_InvalidConfig65() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG65);
            new TeamServicesImpl();
            fail("testTeamServicesImpl_InvalidConfig65 is failure.");
        } catch (TeamServiceConfigurationException tsce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_InvalidConfig65.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testTeamServicesImpl_NullNamespace() {
        try {
            new TeamServicesImpl(null);
            fail("testTeamServicesImpl_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_NullNamespace.");
        }
    }

    /**
     * Tests TeamServicesImpl(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testTeamServicesImpl_EmptyNamespace() {
        try {
            new TeamServicesImpl("  ");
            fail("testTeamServicesImpl_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testTeamServicesImpl_EmptyNamespace.");
        }
    }

    /**
     * Tests createOrUpdateTeam(TeamHeader team, long userId) method with null TeamHeader team,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateOrUpdateTeam_NullTeam() {
        try {
            services.createOrUpdateTeam(null, 100);
            fail("testCreateOrUpdateTeam_NullTeam is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCreateOrUpdateTeam_NullTeam.");
        }
    }

    /**
     * Tests createOrUpdateTeam(TeamHeader team, long userId) method with negative userId,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateOrUpdateTeam_NegativeUserId() {
        try {
            services.createOrUpdateTeam(header, -1);
            fail("testCreateOrUpdateTeam_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCreateOrUpdateTeam_NegativeUserId.");
        }
    }

    /**
     * Tests createOrUpdateTeam(TeamHeader team, long userId) method with unknown teamId,
     * UnknownEntityException should be thrown.
     */
    public void testCreateOrUpdateTeam_UnknownEntityException() {
        try {
            header.setTeamId(1000);
            services.createOrUpdateTeam(header, 1234);
            fail("testCreateOrUpdateTeam_UnknownUserId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCreateOrUpdateTeam_UnknownUserId.");
        }
    }

    /**
     * Tests createOrUpdateTeam(TeamHeader team, long userId) method with TPE thrown,
     * TeamPersistenceException should be thrown.
     */
    public void testCreateOrUpdateTeam_TeamPersistenceException() {
        try {
            header.setTeamId(1001);
            services.createOrUpdateTeam(header, 1234);
            fail("testCreateOrUpdateTeam_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCreateOrUpdateTeam_TeamPersistenceException.");
        }
    }

    /**
     * Tests getTeams(long projectId) method with negative long projectId,
     * IllegalArgumentException should be thrown.
     */
    public void testGetTeams_NegativeProjectId() {
        try {
            services.getTeams(-1);
            fail("testGetTeams_NegativeProjectId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeams_NegativeProjectId.");
        }
    }

    /**
     * Tests getTeams(long projectId) method with unknown long projectId,
     * UnknownEntityException should be thrown.
     */
    public void testGetTeams_UnknownProjectId() {
        try {
            services.getTeams(1003);
            fail("testGetTeams_UnknownProjectId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeams_UnknownProjectId.");
        }
    }

    /**
     * Tests getTeams(long projectId) method with PSE thrown,
     * ProjectServicesException should be thrown.
     */
    public void testGetTeams_ProjectServicesException() {
        try {
            services.getTeams(1004);
            fail("testGetTeams_ProjectServicesException is failure.");
        } catch (ProjectServicesException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeams_ProjectServicesException.");
        }
    }

    /**
     * Tests getTeams(long projectId) method with TPE thrown,
     * TeamPersistenceException should be thrown.
     */
    public void testGetTeams_TeamPersistenceException() {
        try {
            services.getTeams(1001);
            fail("testGetTeams_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeams_TeamPersistenceException.");
        }
    }

    /**
     * Tests getTeamMembers(long teamId) method with negative long teamId,
     * IllegalArgumentException should be thrown.
     */
    public void testGetTeamMembers_NegativeTeamId() {
        try {
            services.getTeamMembers(-1);
            fail("testGetTeamMembers_NegativeTeamId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeamMembers_NegativeTeamId.");
        }
    }

    /**
     * Tests getTeamMembers(long teamId) method with unknown long teamId,
     * UnknownEntityException should be thrown.
     */
    public void testGetTeamMembers_UnknownTeamId() {
        try {
            services.getTeamMembers(1000);
            fail("testGetTeamMembers_UnknownTeamId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeamMembers_UnknownTeamId.");
        }
    }

    /**
     * Tests getTeamMembers(long teamId) method with TSE thrown,
     * TeamServicesException should be thrown.
     */
    public void testGetTeamMembers_TeamServicesException() {
        try {
            services.getTeamMembers(1001);
            fail("testGetTeamMembers_TeamServicesException is failure.");
        } catch (TeamServicesException tse) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeamMembers_TeamServicesException.");
        }
    }

    /**
     * Tests removeTeam(long teamId, long userId) method with negative long teamId,
     * IllegalArgumentException should be thrown.
     */
    public void testRemoveTeam_NegativeTeamId() {
        try {
            services.removeTeam(-1, 100);
            fail("testRemoveTeam_NegativeTeamId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemoveTeam_NegativeTeamId.");
        }
    }

    /**
     * Tests removeTeam(long teamId, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testRemoveTeam_NegativeUserId() {
        try {
            services.removeTeam(1000, -1);
            fail("testRemoveTeam_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemoveTeam_NegativeUserId.");
        }
    }

    /**
     * Tests removeTeam(long teamId, long userId) method with unknown teamId,
     * UnknownEntityException should be thrown.
     */
    public void testRemoveTeam_UnknownTeamId() {
        try {
            services.removeTeam(1000, 1);
            fail("testRemoveTeam_UnknownTeamId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemoveTeam_UnknownTeamId.");
        }
    }

    /**
     * Tests removeTeam(long teamId, long userId) method with OME thrown,
     * OfferManagerException should be thrown.
     */
    public void testRemoveTeam_OfferManagerException() {
        try {
            services.removeTeam(1005, 1);
            fail("testRemoveTeam_OfferManagerException is failure.");
        } catch (OfferManagerException ome) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemoveTeam_OfferManagerException.");
        }
    }

    /**
     * Tests getTeamPositionsDetails(long teamId) method with negative long teamId,
     * IllegalArgumentException should be thrown.
     */
    public void testGetTeamPositionsDetails_NegativeTeamId() {
        try {
            services.getTeamPositionsDetails(-1);
            fail("testGetTeamPositionsDetails_NegativeTeamId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeamPositionsDetails_NegativeTeamId.");
        }
    }

    /**
     * Tests getTeamPositionsDetails(long teamId) method with unknown teamId,
     * UnknownEntityException should be thrown.
     */
    public void testGetTeamPositionsDetails_UnknownTeamId() {
        try {
            services.getTeamPositionsDetails(1000);
            fail("testGetTeamPositionsDetails_UnknownTeamId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeamPositionsDetails_UnknownTeamId.");
        }
    }

    /**
     * Tests getTeamPositionsDetails(long teamId) method with TeamServicesException thrown,
     * TeamServicesException should be thrown.
     */
    public void testGetTeamPositionsDetails_TeamServicesException() {
        try {
            services.getTeamPositionsDetails(1002);
            fail("testGetTeamPositionsDetails_TeamServicesException is failure.");
        } catch (TeamServicesException tse) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetTeamPositionsDetails_TeamServicesException.");
        }
    }

    /**
     * Tests findFreeAgents(long projectId) method with negative long projectId,
     * IllegalArgumentException should be thrown.
     */
    public void testFindFreeAgents_NegativeProjectId() {
        try {
            services.findFreeAgents(-1);
            fail("testFindFreeAgents_NegativeProjectId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testFindFreeAgents_NegativeProjectId.");
        }
    }

    /**
     * Tests findFreeAgents(long projectId) method with unknown long projectId,
     * UnknownEntityException should be thrown.
     */
    public void testFindFreeAgents_UnknownProjectId() {
        try {
            services.findFreeAgents(1003);
            fail("testFindFreeAgents_UnknownProjectId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testFindFreeAgents_UnknownProjectId.");
        }
    }

    /**
     * Tests createOrUpdatePosition(TeamPosition position, long teamId, long userId) method
     *  with null TeamPosition position,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateOrUpdatePosition_NullPosition() {
        try {
            services.createOrUpdatePosition(null, 1000, 2000);
            fail("testCreateOrUpdatePosition_NullPosition is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCreateOrUpdatePosition_NullPosition.");
        }
    }

    /**
     * Tests createOrUpdatePosition(TeamPosition position, long teamId, long userId) method
     *  with negative long teamId,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateOrUpdatePosition_NegativeTeamId() {
        try {
            services.createOrUpdatePosition(position, -1, 1000);
            fail("testCreateOrUpdatePosition_NegativeTeamId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCreateOrUpdatePosition_NegativeTeamId.");
        }
    }

    /**
     * Tests createOrUpdatePosition(TeamPosition position, long teamId, long userId) method
     *  with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateOrUpdatePosition_NegativeUserId() {
        try {
            services.createOrUpdatePosition(position, 100, -1);
            fail("testCreateOrUpdatePosition_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCreateOrUpdatePosition_NegativeUserId.");
        }
    }

    /**
     * Tests getPosition(long positionId) method with negative long positionId,
     * IllegalArgumentException should be thrown.
     */
    public void testGetPosition_NegativePositionId() {
        try {
            services.getPosition(-1);
            fail("testGetPosition_NegativePositionId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetPosition_NegativePositionId.");
        }
    }

    /**
     * Tests getPosition(long positionId) method with unknown long positionId,
     * UnknownEntityException should be thrown.
     */
    public void testGetPosition_UnknownPositionId() {
        try {
            services.getPosition(1001);
            fail("testGetPosition_UnknownPositionId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetPosition_UnknownPositionId.");
        }
    }

    /**
     * Tests getPosition(long positionId) method with TPE thrown,
     * TeamPersistenceException should be thrown.
     */
    public void testGetPosition_TeamPersistenceException() {
        try {
            services.getPosition(1002);
            fail("testGetPosition_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetPosition_TeamPersistenceException.");
        }
    }

    /**
     * Tests removePosition(long positionId, long userId) method with negative long positionId,
     * IllegalArgumentException should be thrown.
     */
    public void testRemovePosition_NegativePositionId() {
        try {
            services.removePosition(-1, 100);
            fail("testRemovePosition_NegativePositionId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemovePosition_NegativePositionId.");
        }
    }

    /**
     * Tests removePosition(long positionId, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testRemovePosition_NegativeUserId() {
        try {
            services.removePosition(100, -1);
            fail("testRemovePosition_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemovePosition_NegativeUserId.");
        }
    }

    /**
     * Tests removePosition(long positionId, long userId) method with unknown long positionId,
     * UnknownEntityException should be thrown.
     */
    public void testRemovePosition_UnknownPositionId() {
        try {
            services.removePosition(1001, 1111);
            fail("testRemovePosition_UnknownPositionId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemovePosition_UnknownPositionId.");
        }
    }

    /**
     * Tests removePosition(long positionId, long userId) method with TPE thrown,
     * TeamPersistenceException should be thrown.
     */
    public void testRemovePosition_TeamPersistenceException() {
        try {
            services.removePosition(1002, 1111);
            fail("testRemovePosition_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemovePosition_TeamPersistenceException.");
        }
    }

    /**
     * Tests validateFinalization(long teamId, long userId) method with negative long teamId,
     * IllegalArgumentException should be thrown.
     */
    public void testValidateFinalization_NegativeTeamId() {
        try {
            services.validateFinalization(-1, 1000);
            fail("testValidateFinalization_NegativeTeamId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidateFinalization_NegativeTeamId.");
        }
    }

    /**
     * Tests validateFinalization(long teamId, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testValidateFinalization_NegativeUserId() {
        try {
            services.validateFinalization(1000, -1);
            fail("testValidateFinalization_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidateFinalization_NegativeUserId.");
        }
    }

    /**
     * Tests validateFinalization(long teamId, long userId) method with unknown long teamId,
     * UnknownEntityException should be thrown.
     */
    public void testValidateFinalization_UnknownTeamId() {
        try {
            services.validateFinalization(1000, 1111);
            fail("testValidateFinalization_UnknownTeamId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidateFinalization_UnknownTeamId.");
        }
    }

    /**
     * Tests finalizeTeam(long teamId, long userId) method with negative long teamId,
     * IllegalArgumentException should be thrown.
     */
    public void testFinalizeTeam_NegativeTeamId() {
        try {
            services.finalizeTeam(-1, 1000);
            fail("testFinalizeTeam_NegativeTeamId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testFinalizeTeam_NegativeTeamId.");
        }
    }

    /**
     * Tests finalizeTeam(long teamId, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testFinalizeTeam_NegativeUserId() {
        try {
            services.finalizeTeam(1000, -1);
            fail("testFinalizeTeam_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testFinalizeTeam_NegativeUserId.");
        }
    }

    /**
     * Tests finalizeTeam(long teamId, long userId) method with unknown long userId,
     * UnknownEntityException should be thrown.
     */
    public void testFinalizeTeam_UnknownTeamId() {
        try {
            services.finalizeTeam(1000, 1111);
            fail("testFinalizeTeam_UnknownTeamId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testFinalizeTeam_UnknownTeamId.");
        }
    }

    /**
     * Tests sendOffer(Offer offer) method with null Offer offer,
     * IllegalArgumentException should be thrown.
     */
    public void testSendOffer_NullOffer() {
        try {
            services.sendOffer(null);
            fail("testSendOffer_NullOffer is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSendOffer_NullOffer.");
        }
    }

    /**
     * Tests sendOffer(Offer offer) method when offer's id is less than 0,
     * IllegalArgumentException should be thrown.
     */
    public void testSendOffer_OfferIdLessThanZero() {
        try {
            offer.setOfferId(-1);
            services.sendOffer(offer);
            fail("testSendOffer_OfferIdLessThanZero is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSendOffer_OfferIdLessThanZero.");
        }
    }

    /**
     * Tests sendOffer(Offer offer) method when TPE thrown,
     * TeamPersistenceException should be thrown.
     */
    public void testSendOffer_TeamPersistenceException() {
        try {
            offer.setOfferId(1);
            offer.setPositionId(1002);
            services.sendOffer(offer);
            fail("testSendOffer_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSendOffer_TeamPersistenceException.");
        }
    }

    /**
     * Tests getOffers(long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testGetOffers_NegativeUserId() {
        try {
            services.getOffers(-1);
            fail("testGetOffers_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetOffers_NegativeUserId.");
        }
    }

    /**
     * Tests getOffers(long userId) method with OfferManagerException thrown,
     * OfferManagerException should be thrown.
     */
    public void testGetOffers_OfferManagerException() {
        try {
            services.getOffers(1005);
            fail("testGetOffers_OfferManagerException is failure.");
        } catch (OfferManagerException ome) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetOffers_OfferManagerException.");
        }
    }

    /**
     * Tests acceptOffer(long offerId, long userId) method with negative long offerId,
     * IllegalArgumentException should be thrown.
     */
    public void testAcceptOffer_NegativeOfferId() {
        try {
            services.acceptOffer(-1, 100);
            fail("testAcceptOffer_NegativeOfferId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAcceptOffer_NegativeOfferId.");
        }
    }

    /**
     * Tests acceptOffer(long offerId, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testAcceptOffer_NegativeUserId() {
        try {
            services.acceptOffer(100, -1);
            fail("testAcceptOffer_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAcceptOffer_NegativeUserId.");
        }
    }

    /**
     * Tests acceptOffer(long offerId, long userId) method with OfferManagerException thrown?
     * OfferManagerException should be thrown.
     */
    public void testAcceptOffer_OfferManagerException() {
        try {
            services.acceptOffer(1005, 1111);
            fail("testAcceptOffer_OfferManagerException is failure.");
        } catch (OfferManagerException ome) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAcceptOffer_OfferManagerException.");
        }
    }

    /**
     * Tests acceptOffer(long offerId, long userId) method with TeamPersistenceException thrown?
     * TeamPersistenceException should be thrown.
     */
    public void testAcceptOffer_TeamPersistenceException() {
        try {
            services.acceptOffer(1006, 1111);
            fail("testAcceptOffer_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAcceptOffer_TeamPersistenceException.");
        }
    }

    /**
     * Tests rejectOffer(long offerId, String cause, long userId) method with negative long offerId,
     * IllegalArgumentException should be thrown.
     */
    public void testRejectOffer_NegativeOfferId() {
        try {
            services.rejectOffer(-1, "some cause", 100);
            fail("testRejectOffer_NegativeOfferId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRejectOffer_NegativeOfferId.");
        }
    }

    /**
     * Tests rejectOffer(long offerId, String cause, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testRejectOffer_NegativeUserId() {
        try {
            services.rejectOffer(100, "some cause", -1);
            fail("testRejectOffer_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRejectOffer_NegativeUserId.");
        }
    }

    /**
     * Tests rejectOffer(long offerId, String cause, long userId) method with TeamPersistenceException thrown?
     * TeamPersistenceException should be thrown.
     */
    public void testRejectOffer_TeamPersistenceException() {
        try {
            services.rejectOffer(1006, "", 1111);
            fail("testAcceptOffer_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAcceptOffer_TeamPersistenceException.");
        }
    }

    /**
     * Tests rejectOffer(long offerId, String cause, long userId) method with OfferManagerException thrown?
     * OfferManagerException should be thrown.
     */
    public void testRejectOffer_OfferManagerException() {
        try {
            services.rejectOffer(1001, "", 1111);
            fail("testRejectOffer_OfferManagerException is failure.");
        } catch (OfferManagerException ome) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRejectOffer_OfferManagerException.");
        }
    }

    /**
     * Tests removeMember(long resourceId, long userId) method with negative long resourceId,
     * IllegalArgumentException should be thrown.
     */
    public void testRemoveMember_NegativeResourceId() {
        try {
            services.removeMember(-1, 100);
            fail("testRemoveMember_NegativeResourceId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemoveMember_NegativeResourceId.");
        }
    }

    /**
     * Tests removeMember(long resourceId, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testRemoveMember_NegativeUserId() {
        try {
            services.removeMember(100, -1);
            fail("testRemoveMember_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemoveMember_NegativeUserId.");
        }
    }

    /**
     * Tests removeMember(long resourceId, long userId) method with unknown long resourceId,
     * UnknownEntityException should be thrown.
     */
    public void testRemoveMember_UnknownResourceId() {
        try {
            services.removeMember(1008, 1111);
            fail("testRemoveMember_UnknownResourceId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRemoveMember_UnknownResourceId.");
        }
    }

    /**
     * Tests forceFinalization(long projectId, long userId) method with negative long projectId,
     * IllegalArgumentException should be thrown.
     */
    public void testForceFinalization_NegativeProjectId() {
        try {
            services.forceFinalization(-1, 100);
            fail("testForceFinalization_NegativeProjectId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testForceFinalization_NegativeProjectId.");
        }
    }

    /**
     * Tests forceFinalization(long projectId, long userId) method with negative long userId,
     * IllegalArgumentException should be thrown.
     */
    public void testForceFinalization_NegativeUserId() {
        try {
            services.forceFinalization(1000, -1);
            fail("testForceFinalization_NegativeUserId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testForceFinalization_NegativeUserId.");
        }
    }
    
    /**
     * Tests forceFinalization(long projectId, long userId) method with unknown long projectId,
     * UnknownEntityException should be thrown.
     */
    public void testForceFinalization_UnknownProjectId() {
        try {
            services.forceFinalization(1003, 1111);
            fail("testForceFinalization_UnknownProjectId is failure.");
        } catch (UnknownEntityException uee) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testForceFinalization_UnknownProjectId.");
        }
    }

    /**
     * Tests forceFinalization(long projectId, long userId) method with ProjectServicesException thrown,
     * ProjectServicesException should be thrown.
     */
    public void testForceFinalization_ProjectServicesException() {
        try {
            services.forceFinalization(1004, 1111);
            fail("testForceFinalization_ProjectServicesException is failure.");
        } catch (ProjectServicesException pse) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testForceFinalization_ProjectServicesException.");
        }
    }

    /**
     * Tests forceFinalization(long projectId, long userId) method with TeamPersistenceException thrown,
     * TeamPersistenceException should be thrown.
     */
    public void testForceFinalization_TeamPersistenceException() {
        try {
            services.forceFinalization(1005, 1111);
            fail("testForceFinalization_TeamPersistenceException is failure.");
        } catch (TeamPersistenceException tpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testForceFinalization_TeamPersistenceException.");
        }
    }
}