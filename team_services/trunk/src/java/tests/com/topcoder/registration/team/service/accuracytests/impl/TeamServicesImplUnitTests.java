/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.accuracytests.impl;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.offer.Offer;
import com.topcoder.management.team.offer.OfferStatus;
import com.topcoder.management.team.offer.OfferStatusType;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.ResourcePosition;
import com.topcoder.registration.team.service.impl.TeamServicesImpl;

/**
 * <p>
 * Accuracy unit tests for <code>TeamServicesImpl</code> class.
 * </p>
 * <p>
 * Note: default namespace com.topcoder.registration.team.service.impl.TeamServicesImpl is always used in the tests.
 * </p>
 * @author 80x86
 * @version 1.0
 */
public class TeamServicesImplUnitTests extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>TeamServicesImpl</code> class.
     * </p>
     */
    private TeamServicesImpl services;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig("test_files/accuracy/" + File.separator + "configuration.xml");
        services = new TeamServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        services = null;
    }

    /**
     * <p>
     * Accuracy test for default constructor.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("'services' should not be null.", services);
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * team.id is negative and team having name already existing in this
     * project,expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeam_AccuracyWithDuplicatedTeamName() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(-1);
        team.setName("Robot Team");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * team.id is negative and team having name matching handle of certain resource in
     * this project,expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeam_AccuracyTeamNameMatchResourceHandle() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(-1);
        team.setName("argolite");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }


    /**
     * <p>
     * Accuracy test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * team.id=10000 and InvalidTeamException occurred when updating team using
     * TeamManager,expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeam_AccuracyInvalidTeamExceptionOccuring() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(10000);
        team.setName("name");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * With team.id=11 and team having name matching handle of certain resource in this
     * project,expects unsuccessful operation result.expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeam_AccuracyPositiveTeamIdAndDupName() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(2);
        team.setName("argolite");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * team.id=-1,expects successful operation result.expects
     * unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeam_Accuracy1() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(-10);
        team.setName("tests");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdateTeam(team, userId)</code> method.
     * </p>
     * <p>
     * With team.id=2,expects successful operation result.expects unsuccessful
     * operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdateTeam_Accuracy2() throws Exception {
        TeamHeader team = new TeamHeader();
        team.setTeamId(2);
        team.setName("update team");
        OperationResult result = services.createOrUpdateTeam(team, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>getTeams(projectId)</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeams_Accuracy() throws Exception {
        TeamHeader[] teams = services.getTeams(1);
        assertEquals("There should be only one team.", 1, teams.length);
        assertEquals("Team id should be 1.", 1, teams[0].getTeamId());
    }

    /**
     * <p>
     * Accuracy test for <code>getTeamMembers(teamId)</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamMembers_Accuracy() throws Exception {
        Resource[] rs = services.getTeamMembers(1);
        assertEquals("There should be three resources returned.", 3, rs.length);
    }

    /**
     * <p>
     * Accuracy test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * unknown userId, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeam_Accuracy0() throws Exception {
        OperationResult result = services.removeTeam(1, 11);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>removeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * teamId=1, team-1 should be removed successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveTeam_Accuracy1() throws Exception {
        OperationResult result = services.removeTeam(1, 2);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>getTeamPositionDetails(teamId)</code> method.
     * </p>
     * <p>
     * With teamid=1, an array containing two elements should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetTeamPositionDetails_Accuracy() throws Exception {
        ResourcePosition[] rp = services.getTeamPositionsDetails(1);
        assertEquals("There should be two results.", 2, rp.length);
    }

    /**
     * <p>
     * Accuracy test for <code>findFreeAgents(projectId)</code> method.
     * </p>
     * <p>
     * With projectId=1, there should be no free agents.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindFreeAgents_Accuracy() throws Exception {
        Resource[] resource = services.findFreeAgents(1);
        assertEquals("There should be no free agent.", 0, resource.length);
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With error occurred when retrieving user, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy0() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("exa");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 10);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position.id=-1, position.name already used. expects unsuccessful operation
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy1() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("Good Position");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position.id=-1, position.filled=true, but associated resource is not free
     * agent. expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy2() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("new position");
        pos.setFilled(true);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position name match one handle, expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy3() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("name");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 11);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position.id=1, position.name already used. expects unsuccessful operation
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy4() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("Good Position");
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position.id=2, position.filled=true, but associated resource is not free agent.
     * expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy5() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("new position");
        pos.setFilled(true);
        pos.setMemberResourceId(4);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position.id=2, position.filled=true, memberResourceId=1, expects unsuccessful
     * operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy6() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("new position");
        pos.setFilled(true);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position.id=-1, position.filled=false, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy7() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(-1);
        pos.setName("new position");
        pos.setFilled(false);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>createOrUpdatePosition(position,teamId,userId)</code> method.
     * </p>
     * <p>
     * With position.id=2, position.filled=false, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrUpdatePosition_Accuracy8() throws Exception {
        TeamPosition pos = new TeamPosition();
        pos.setPositionId(2);
        pos.setName("new position");
        pos.setFilled(false);
        pos.setMemberResourceId(1);

        OperationResult result = services.createOrUpdatePosition(pos, 1, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>getPosition(positionId)</code> method.
     * </p>
     * <p>
     * with positionId=2, the valid TeamPosition instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPosition_Accuracy() throws Exception {
        TeamPosition pos = services.getPosition(2);
        assertNotNull("'pos' should not be null.", pos);
        assertEquals("Name mismatched.", "Good Position", pos.getName());
    }

    /**
     * <p>
     * Accuracy test for <code>removePosition(positionId,userId)</code> method.
     * </p>
     * <p>
     * successful is expected. 
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemovePosition_Accuracy() throws Exception {
        OperationResult result = services.removePosition(2, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * team's project not active. expects unsuccessful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalization_AccuracyInactiveProject() throws Exception {
        OperationResult result = services.validateFinalization(2, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>validateFinalization(teamId,userId)</code> method.
     * </p>
     * <p>
     * user not team's captain. expects unsuccessful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFinalization_AccuracyUserIdNotCaptain() throws Exception {
        OperationResult result = services.validateFinalization(19, 2);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>finalizeTeam(teamId, userId)</code> method.
     * </p>
     * <p>
     * unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFinalizeTeam_Accuracy() throws Exception {
        OperationResult result = services.finalizeTeam(1, 1);
        assertFalse("Result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>sendOffer(offer)</code> method.
     * </p>
     * <p>
     * unknown position id. expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSendOffer_Accuracy_UnknownPositionId() throws Exception {
        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(100);

        OperationResult result = services.sendOffer(offer);
        assertFalse("result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>sendOffer(offer)</code> method.
     * </p>
     * <p>
     * position already filled. expects unsuccessful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSendOffer_Accuracy_PositionFilled() throws Exception {
        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(101);

        OperationResult result = services.sendOffer(offer);
        assertFalse("result should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>getOffers(userId)</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetOffers_Accuracy() throws Exception {
        Offer[] offers = services.getOffers(1);
        assertEquals("There should be one offer.", 1, offers.length);
    }

    /**
     * <p>
     * Accuracy test for <code>accepteOffer(offerId,userId)</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testAccepteOffer_Accuracy() throws Exception {
        OperationResult result = services.acceptOffer(1, 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>rejectOffer(offerId,cause,userId)</code> method.
     * </p>
     * <p>
     * offerId=1, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRejectOffer_Accuracy() throws Exception {
        OperationResult result = services.rejectOffer(1, "cause", 1);
        assertTrue("Result should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurring when retrieving resource.expects unsuccessful result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMember_Accuracy0() throws Exception {
        OperationResult result = services.removeMember(12, 1);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMember_Accuracy1() throws Exception {
        OperationResult result = services.removeMember(1, 1);
        assertTrue("'result' should be successful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with InvalidPositionException occurring when updating position. expects unsuccessful
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMember_Accuracy2() throws Exception {
        OperationResult result = services.removeMember(1, 100);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with PositionRemovalException occurring when removing position. expects unsuccessful
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMember_Accuracy3() throws Exception {
        OperationResult result = services.removeMember(1, 105);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>removeMember(resourceId, usreId)</code> method.
     * </p>
     * <p>
     * Tests it with UnknownEntityException occurring when removing position. expects unsuccessful
     * result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveMember_Accuracy4() throws Exception {
        OperationResult result = services.removeMember(1, 106);
        assertFalse("'result' should be unsuccessful.", result.isSuccessful());
    }
    /**
     * <p>
     * Accuracy test for <code>forceFinalization(projectId, userId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with projectId=1, expects successful operation result.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testForceFinalization_Accuracy() throws Exception {
        OperationResult result = services.forceFinalization(1, 1);
        assertTrue("'result' should be successful.", result.isSuccessful());
    }
}
