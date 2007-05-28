package com.topcoder.management.team.offer;

import com.topcoder.search.builder.filter.Filter;

public interface OfferManager {

	/**
	 * Sends an offer to a resource, inviting them to join a team.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm766f]
	 * @param offer The extended offer
	 * @throws IllegalArgumentException If offer is null, or offer.toUser, offer.toUser, or offer.positionId is negative
	 * @throws InvalidOfferDataException If offer violates any validation rules
	 * @throws OfferManagerException If any other offer-related issue comes up, such as inability to save the offer
	 */
	    public void sendOffer(Offer offer);
	/**
	 * Sends an acceptance to the open offer with the given ID. All other open offers to this position are expired.
	 * <p>An open offer is one with a status type of OFFERED.</p>
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm7664]
	 * @param offerId The ID of the offer to accept
	 * @throws IllegalArgumentException If offerId is negative
	 * @throws UnknownOfferException If an offer with the given ID is not found
	 * @throws OfferManagerException If any other offer-related issue comes up, such as inability to retrieve the offer
	 * @throws IllegalOfferStatusChangeException If the offer being accepted does not have a status type of OFFERED.
	 */
	    public void acceptOffer(long offerId);
	/**
	 * Sends a rejection to the offer with the given ID.
	 * <p>An open offer is one with a status type of OFFERED.</p>
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm7658]
	 * @param offerId The ID of the offer to reject
	 * @param cause The rejection cause
	 * @throws IllegalArgumentException If offerId is negative, or if offer message has more than 4096 characters
	 * @throws InvalidOfferDataException If cause violates any validation rules
	 * @throws UnknownOfferException If an offer with the given ID is not found
	 * @throws OfferManagerException If any other offer-related issue comes up, such as inability to retrieve the offer
	 * @throws IllegalOfferStatusChangeException If the offer being rejected does not have a status type of OFFERED.
	 */
	    public void rejectOffer(long offerId, String cause);
	/**
	 * Expires an existing offer.
	 * <p>An open offer is one with a status type of OFFERED.</p>
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm764b]
	 * @param offerId The ID of the offer to expire
	 * @throws IllegalArgumentException If offerId is negative
	 * @throws UnknownOfferException If an offer with the given ID is not found
	 * @throws OfferManagerException If any other offer-related issue comes up, such as inability to retrieve the offer
	 * @throws IllegalOfferStatusChangeException If the offer being expired does not have a status type of OFFERED.
	 */
	    public void expireOffer(long offerId);
	/**
	 * Searches for all Offers that match the given search criteria. Returns an empty array if none found
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm763f]
	 * @return An array of matching Offers, or empty if no matches found
	 * @param filter The filter criteria to match Offers
	 * @throws IllegalArgumentException If filter is null
	 * @throws OfferManagerException If any offer-related issue comes up, such as a failure to retrieve the offers
	 */
	    public Offer[] findOffers(Filter filter);
}
