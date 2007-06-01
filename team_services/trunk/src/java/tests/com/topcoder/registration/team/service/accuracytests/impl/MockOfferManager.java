/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.accuracytests.impl;

import com.topcoder.management.team.offer.Offer;
import com.topcoder.management.team.offer.OfferManager;
import com.topcoder.management.team.offer.OfferStatus;
import com.topcoder.management.team.offer.OfferStatusType;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of <code>OfferManager</code>.
 * </p>
 * @author 80x86
 * @version 1.0
 */
public class MockOfferManager implements OfferManager {

    /**
     * Sends an offer to a resource, inviting them to join a team.
     * @param offer
     *            The extended offer
     * @throws IllegalArgumentException
     *             If offer is null, or offer.toUser, offer.toUser, or offer.positionId is negative
     * @throws InvalidOfferDataException
     *             If offer violates any validation rules
     * @throws OfferManagerException
     *             If any other offer-related issue comes up, such as inability to save the offer
     */
    public void sendOffer(Offer offer) {

    }

    /**
     * Sends an acceptance to the open offer with the given ID. All other open offers to this
     * position are expired.
     * <p>
     * An open offer is one with a status type of OFFERED.
     * </p>
     * @param offerId
     *            The ID of the offer to accept
     * @throws IllegalArgumentException
     *             If offerId is negative
     * @throws UnknownOfferException
     *             If an offer with the given ID is not found
     * @throws OfferManagerException
     *             If any other offer-related issue comes up, such as inability to retrieve the
     *             offer
     * @throws IllegalOfferStatusChangeException
     *             If the offer being accepted does not have a status type of OFFERED.
     */
    public void acceptOffer(long offerId) {
    }

    /**
     * Sends a rejection to the offer with the given ID.
     * <p>
     * An open offer is one with a status type of OFFERED.
     * </p>
     * @param offerId
     *            The ID of the offer to reject
     * @param cause
     *            The rejection cause
     * @throws IllegalArgumentException
     *             If offerId is negative, or if offer message has more than 4096 characters
     * @throws InvalidOfferDataException
     *             If cause violates any validation rules
     * @throws UnknownOfferException
     *             If an offer with the given ID is not found
     * @throws OfferManagerException
     *             If any other offer-related issue comes up, such as inability to retrieve the
     *             offer
     * @throws IllegalOfferStatusChangeException
     *             If the offer being rejected does not have a status type of OFFERED.
     */
    public void rejectOffer(long offerId, String cause) {
    }

    /**
     * Expires an existing offer.
     * <p>
     * An open offer is one with a status type of OFFERED.
     * </p>
     * @param offerId
     *            The ID of the offer to expire
     * @throws IllegalArgumentException
     *             If offerId is negative
     * @throws UnknownOfferException
     *             If an offer with the given ID is not found
     * @throws OfferManagerException
     *             If any other offer-related issue comes up, such as inability to retrieve the
     *             offer
     * @throws IllegalOfferStatusChangeException
     *             If the offer being expired does not have a status type of OFFERED.
     */
    public void expireOffer(long offerId) {
    }

    /**
     * Searches for all Offers that match the given search criteria. Returns an empty array if none
     * found
     * @return An array of matching Offers, or empty if no matches found
     * @param filter
     *            The filter criteria to match Offers
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws OfferManagerException
     *             If any offer-related issue comes up, such as a failure to retrieve the offers
     */
    public Offer[] findOffers(Filter filter) {
        Offer offer = new Offer();
        offer.setOfferId(1);
        offer.setPositionId(1);
        offer.setFromUserId(1);
        offer.setToUserId(1);
        offer.setPercentageOffered(0);
        OfferStatus status = new OfferStatus();
        status.setStatusType(OfferStatusType.OFFERED);
        offer.setStatus(status);
        return new Offer[] {offer};
    }
}
