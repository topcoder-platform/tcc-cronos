package com.topcoder.management.team.offer;

import com.topcoder.util.collection.typesafeenum.Enum;

public class OfferStatusType extends Enum {

	/**
	 * Represents an offer status type of offered. This type is used when an offer has been offered.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm7607]
	 */
	    public static final OfferStatusType OFFERED = new OfferStatusType(1,"Offered");

	/**
	 * Represents an offer status type of rejected. This type is used when an offer has been rejected.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm7601]
	 */
	    public static final OfferStatusType REJECTED = new OfferStatusType(2,"Rejected");

	/**
	 * Represents an offer status type of accepted. This type is used when an offer has been accepted.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm75fb]
	 */
	    public static final OfferStatusType ACCEPTED = new OfferStatusType(3,"Accepted");

	/**
	 * Represents an offer status type of expired. This type is used when an offer has been expired.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm75f5]
	 */
	    public static final OfferStatusType EXPIRED = new OfferStatusType(4,"Expired");

	/**
	 * The ID of this offer status type. It is set in the constructor to a positive number and will never change.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm75ef]
	 */
	    private final int offerStatusTypeId;

		private final String offerStatusTypeName;

	/**
	 * Creates a new OfferStatusType with the given ID
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm75e8]
	 * @param offerStatusTypeId The assigned offer status type ID
	 */
	    private  OfferStatusType(int offerStatusTypeId, String name) {        /** lock-end */
	        this.offerStatusTypeId = offerStatusTypeId;
			this.offerStatusTypeName=name;
	    } /** lock-begin */

	/**
	 * Retrieves the ID of this offer status type.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm75e1]
	 * @return the offer status type ID
	 */
	    public int getOfferStatusTypeId() {        /** lock-end */
	 return offerStatusTypeId; 
	    } /** lock-begin */

		public String getOfferStatusTypeName(){
			return offerStatusTypeName;
		}

}
