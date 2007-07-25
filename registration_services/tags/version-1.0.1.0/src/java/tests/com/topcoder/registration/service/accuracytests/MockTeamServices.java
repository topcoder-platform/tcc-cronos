/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.offer.Offer;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.ResourcePosition;
import com.topcoder.registration.team.service.TeamServices;
import com.topcoder.registration.team.service.impl.OperationResultImpl;

/**
 * <p>
 * A mock implementation of TeamServices.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockTeamServices implements TeamServices {

    /**
     * @see com.topcoder.registration.team.service.TeamServices#createOrUpdateTeam(
     *      com.topcoder.management.team.TeamHeader, long)
     */
    public OperationResult createOrUpdateTeam(TeamHeader team, long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#getTeams(long)
     */
    public TeamHeader[] getTeams(long projectId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#getTeamMembers(long)
     */
    public Resource[] getTeamMembers(long teamId) {
        return new Resource[] { new Resource(1) };
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#removeTeam(long, long)
     */
    public OperationResult removeTeam(long teamId, long userId) {
        OperationResultImpl result = new OperationResultImpl();
        result.setSuccessful(true);
        return result;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#getTeamPositionsDetails(long)
     */
    public ResourcePosition[] getTeamPositionsDetails(long teamId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#findFreeAgents(long)
     */
    public Resource[] findFreeAgents(long projectId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#createOrUpdatePosition(
     *      com.topcoder.management.team.TeamPosition, long, long)
     */
    public OperationResult createOrUpdatePosition(TeamPosition position, long teamId, long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#getPosition(long)
     */
    public TeamPosition getPosition(long positionId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#removePosition(long, long)
     */
    public OperationResult removePosition(long positionId, long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#validateFinalization(long, long)
     */
    public OperationResult validateFinalization(long teamId, long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#finalizeTeam(long, long)
     */
    public OperationResult finalizeTeam(long teamId, long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#sendOffer(
     *      com.topcoder.management.team.offer.Offer)
     */
    public OperationResult sendOffer(Offer offer) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#getOffers(long)
     */
    public Offer[] getOffers(long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#acceptOffer(long, long)
     */
    public OperationResult acceptOffer(long offerId, long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#rejectOffer(long, java.lang.String, long)
     */
    public OperationResult rejectOffer(long offerId, String cause, long userId) {
        return null;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#removeMember(long, long)
     */
    public OperationResult removeMember(long resourceId, long userId) {
        OperationResultImpl result = new OperationResultImpl();
        result.setSuccessful(true);
        return result;
    };

    /**
     * @see com.topcoder.registration.team.service.TeamServices#forceFinalization(long, long)
     */
    public OperationResult forceFinalization(long projectId, long userId) {
        return null;
    };

}
