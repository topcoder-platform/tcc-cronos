package com.topcoder.management.team.offer;

import java.io.Serializable;
import java.util.Date;

public class OfferStatus implements Serializable {


	/**
	 * <p>Represents the type of the offer status.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be null.</p>
	 * 
	 * @poseidon-object-id [Im1e4cc5dfm1121c8d2706mm2e40]
	 */
	    private OfferStatusType statusType;

	/**
	 * <p>Represents the time this status was set with the given status type.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be null.</p>
	 * 
	 * @poseidon-object-id [Im1e4cc5dfm1121c8d2706mm2e38]
	 */
	    private Date timestamp;

	/**
	 * Default constructor. Does nothing.
	 * 
	 * @poseidon-object-id [Im1e4cc5dfm1121c8d2706mm2e32]
	 */
	    public  OfferStatus() {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * Full constructor. This convenience constructor allows for setting all values in one go.
	 * 
	 * @poseidon-object-id [Im1e4cc5dfm1121c8d2706mm2e2a]
	 * @param statusType the type of the offer status
	 * @param timestamp the time this status was set with the given status type
	 * @throws IllegalArgumentException If either parameter is null
	 */
	    public  OfferStatus(OfferStatusType statusType, Date timestamp) {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * @return Returns the statusType.
	 */
	public OfferStatusType getStatusType() {
		return statusType;
	}

	/**
	 * @param statusType The statusType to set.
	 */
	public void setStatusType(OfferStatusType statusType) {
		this.statusType = statusType;
	}

	/**
	 * @return Returns the timestamp.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp The timestamp to set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


}
