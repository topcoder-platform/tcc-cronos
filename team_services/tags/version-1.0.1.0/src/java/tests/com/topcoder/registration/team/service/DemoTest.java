/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import java.io.File;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.offer.Offer;
import com.topcoder.registration.team.service.impl.TeamServicesImpl;
import com.topcoder.registration.team.service.impl.TestHelper;

import junit.framework.TestCase;

/**
 * <p>
 * This is a demo for demonstrating the usage of this component.
 * </p>
 * <p>
 * <code>TeamServices</code> is the core class in this component, so this demo will concentrate on
 * usage of this class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends TestCase {

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
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "config.xml");
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
        TestHelper.clearConfig();
        services = null;
    }

    /**
     * <p>
     * Demonstrates the usage of this component.
     * </p>
     * <p>
     * Assumes there is project with ID 1, and that there are several teams registered, including
     * team ID 10. A pool of free agents may include resources with IDs 2-20.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // Create a team to project 1
        // new team with project ID = 1, captain resource #1, and 50 percent payment
        TeamHeader teamHeader = new TeamHeader();
        teamHeader.setTeamId(1);
        teamHeader.setCaptainResourceId(1);
        teamHeader.setCaptainPaymentPercentage(50);
        OperationResult result = services.createOrUpdateTeam(teamHeader, 1);
        // The result would indicate that the operation was successful. The team is created with ID
        // = 11.

        // Remove a team 10 from project 1
        result = services.removeTeam(11, 1);
        // Team with ID 10 is removed, and a message sent to the team members

        // We will search for free agents in this project, and select some to add to the positions.
        Resource[] freeAgents = services.findFreeAgents(1);
        // 19 resources would be returned.

        // We will now add positions to the new team
        TeamPosition position = new TeamPosition();
        position.setPositionId(2);
        position.setName("new position");
        position.setFilled(false);
        position.setMemberResourceId(1);
        result = services.createOrUpdatePosition(position, 1, 1);
        // another new position for resource # 3, 20 percent payment
        position = new TeamPosition();
        position.setName("good position");
        position.setMemberResourceId(3);
        position.setPaymentPercentage(20);
        result = services.createOrUpdatePosition(position, 11, 1);
        // The results would indicate that the operations were successful. The positions are created
        // with ID = 101 and 102, respectively.

        // We can retrieve a position and team we just created
        ResourcePosition[] detail = services.getTeamPositionsDetails(11);

        // We can also get the resources in this team (11)
        Resource[] resources = services.getTeamMembers(11);
        // We would get the three resources on this project: IDs 1, 2, and 3

        // If we want more details about the positions, we can use another service method
        ResourcePosition[] details = services.getTeamPositionsDetails(11);
        // We would get the two positions and the resources for resource IDs 2 and 3

        // Other bookkeeping service methods may include removing a position from another team.
        result = services.removePosition(40, 1);
        // position 40 would be removed

        // Suppose we want to remove resource 3 from the team, and send offers to other free agents.
        // We begin by removing the resource.
        result = services.removeMember(3, 1);
        // resource 3 is removed from position 102

        // We then send an offer to resource 4
        // offer to resource 4 to join team 11
        Offer newOffer = new Offer();
        newOffer.setToUserId(4);
        newOffer.setOfferId(1);
        result = services.sendOffer(newOffer);

        // unfortunately, resource 4 will reject it
        result = services.rejectOffer(1234, "Too Busy", 4);

        // we then try resource 5, and she accepts
        result = services.acceptOffer(1235, 5);

        // For other teams, we also check out offers
        Offer[] offers = services.getOffers(6);

        // We the proceed with finalizing the team 11, since all positions are filled. We first
        // validate
        result = services.validateFinalization(11, 1);

        // The result is ok, so we proceed to finalize
        result = services.finalizeTeam(11, 1);
        // Team 11 is now finalized

        // At this time, we may also finalize other teams in this project. We will just force them
        // to finalize, if possible
        result = services.forceFinalization(1, 1);
        // The result is ok, all teams in project 1 are finalized.

        // This demo has provided a typical scenario for the usage of the service.
    }
}
