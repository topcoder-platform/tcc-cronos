/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.stresstests;

import com.topcoder.management.team.offer.Offer;
import com.topcoder.management.team.offer.OfferManager;
import com.topcoder.management.team.offer.OfferStatus;
import com.topcoder.management.team.offer.OfferStatusType;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of <code>OfferManager</code> interface used in testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockOfferManager implements OfferManager {

    /**
     * Sends an offer to a resource, inviting them to join a team.
     *
     * @param offer The extended offer
     */
    public void sendOffer(Offer offer) {

    }

    /**
     * Sends an acceptance to the open offer with the given ID. All other open offers to this
     * position are expired.
     * <p>
     * An open offer is one with a status type of OFFERED.
     * </p>
     *
     * @param offerId The ID of the offer to accept
     */
    public void acceptOffer(long offerId) {
    }

    /**
     * Sends a rejection to the offer with the given ID.
     * <p>
     * An open offer is one with a status type of OFFERED.
     * </p>
     *
     * @param offerId The ID of the offer to reject
     * @param cause   The rejection cause
     */
    public void rejectOffer(long offerId, String cause) {
    }

    /**
     * Expires an existing offer.
     * <p>
     * An open offer is one with a status type of OFFERED.
     * </p>
     *
     * @param offerId The ID of the offer to expire
     */
    public void expireOffer(long offerId) {
    }

    /**
     * Searches for all Offers that match the given search criteria.
     * This method will alwais return an offer.
     *
     * @param filter The filter criteria to match Offers
     * @return An array of matching Offers, or empty if no matches found
     */
    public Offer[] findOffers(Filter filter) {
        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(1);
        offer.setFromUserId(1);
        offer.setToUserId(1);
        OfferStatus status = new OfferStatus();
        status.setStatusType(OfferStatusType.OFFERED);
        offer.setStatus(status);
        return new Offer[]{offer};
    }
}
