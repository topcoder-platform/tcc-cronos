/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.stresstests;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.impl.TeamServicesImpl;
import junit.framework.TestCase;

import java.io.File;

/**
 * <p>
 * This class contains methods for testing stress conditions on <code>TeamServicesImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamServiceImplStressTests extends TestCase {

    /**
     * Configuration file for initializing <c>teamServices</c> field.
     */
    private static final String CONFIG_FILE = "test_files" + File.separator + "stresstests"
        + File.separator + "config.xml";

    /**
     * The number of times the tests will be executed.
     */
    private static final int times = 100;

    /**
     * <c>TeamServicesImpl</c> class instance used in testing.It is initialized
     * in <c>setUp()</c> method.
     */
    private TeamServicesImpl teamServices;

    /**
     * <p>
     * Sets up test environment by initializing <c>teamServices</c> field.
     * </p>
     *
     * @throws Exception Propagated to JUnit.
     */
    protected void setUp() throws Exception {
        StressTestsHelper.clearConfig();
        StressTestsHelper.loadConfig(CONFIG_FILE);
        teamServices = new TeamServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment by removing all namespaces from Configuration Manager.
     * </p>
     *
     * @throws Exception Propagated to JUnit.
     */
    protected void tearDown() throws Exception {
        StressTestsHelper.clearConfig();
    }


    /**
     * Tests for stress the method <c>createOrUpdateTeam(TeamHeader, long)</c>.
     */
    public void testCreateUpdateTeam() {
        for (int i = 0; i < times; i++) {
            TeamHeader team = new TeamHeader();
            team.setTeamId(-1);
            team.setName("create team" + i);
            OperationResult result = teamServices.createOrUpdateTeam(team, 1);
            assertTrue("Result should be successful.", result.isSuccessful());
        }
        for (int i = 0; i < times; i++) {
            TeamHeader team = new TeamHeader();
            team.setTeamId(2);
            team.setName("update team" + i);
            OperationResult result = teamServices.createOrUpdateTeam(team, 1);
            assertTrue("Result should be successful.", result.isSuccessful());
        }
    }

    /**
     * Tests for stress the method <c>getTeams(long)</c>.
     */
    public void testGetTeams() {
        for (int i = 0; i < times; i++) {
            TeamHeader[] teams = teamServices.getTeams(1);
            assertEquals("There should be only one team.", 1, teams.length);
            assertEquals("Team id should be 1.", 1, teams[0].getTeamId());
        }
    }

    /**
     * Tests for stress the method <c>findFreeAgents(long)</c>.
     */
    public void testFindFreeAgents() {
        for (int i = 0; i < times; i++) {
            Resource[] resource = teamServices.findFreeAgents(1);
            assertEquals("There should be one free agent.", 1, resource.length);
        }
    }

    /**
     * Tests for stress the method <c>createUpdatePosition(TeamPosition,long,long)</c>.
     */
    public void testCreateUpdatePosition() {
        for (int i = 0; i < times; i++) {
            TeamPosition pos = new TeamPosition();
            pos.setPositionId(-1);
            pos.setFilled(true);
            pos.setName("new position");
            pos.setMemberResourceId(1);

            OperationResult result = teamServices.createOrUpdatePosition(pos, 1, 1);
            assertTrue("Result should be successful.", result.isSuccessful());
        }
    }

    public void testValidateFinalization() {
        for (int i = 0; i < times; i++) {
            OperationResult result = teamServices.validateFinalization(19, 1);
            assertTrue("Result should be successful.", result.isSuccessful());
        }
    }
}
