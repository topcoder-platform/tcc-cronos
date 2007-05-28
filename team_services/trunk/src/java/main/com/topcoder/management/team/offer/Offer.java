package com.topcoder.management.team.offer;

import java.io.Serializable;

public class Offer implements Serializable {


	/**
	 * <p>Represents the offer Id.</p>
	 * 
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * <p>Once set, it will be a non-negative number</p>
	 * 
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77e6]
	 */
	    private long offerId = -1;

	/**
	 * <p>Represents the ID of the user that sends the offer.</p>
	 * 
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * <p>Once set, it will be a non-negative number</p>
	 * 
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77e0]
	 */
	    private long fromUserId = -1;

	/**
	 * <p>Represents the ID of the user that is the target of the offer.</p>
	 * 
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * <p>Once set, it will be a non-negative number</p>
	 * 
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77da]
	 */
	    private long toUserId = -1;

	/**
	 * <p>Represents the ID of the position that is being offered.</p>
	 * 
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * <p>Once set, it will be a non-negative number</p>
	 * 
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77d4]
	 */
	    private long positionId = -1;

	/**
	 * <p>Represents the status of this offer.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will be one of the existing OfferStatuses</p>
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77ce]
	 */
	    private OfferStatus status = null;

	/**
	 * <p>Represents the percentage of the project payment that is being offered.</p>
	 * 
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * <p>Once set, it will be a positive number</p>
	 * 
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77c8]
	 */
	    private int percentageOffered = 0;

	/**
	 * <p>Represents the message that comes with the offer.</p>
	 * 
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * <p>Can be null at any time. Empty is set to null. It will not exceed 4096 characters.</p>
	 * 
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77bc]
	 */
	    private String message = null;

	/**
	 * <p>Represents the cause of the rejection if this offer is being rejected.</p>
	 * 
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * <p>Can be null at any time. Empty is set to null. It will not exceed 4096 characters.</p>
	 * 
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77b6]
	 */
	    private String rejectionCause = null;

	/**
	 * Default constructor. Does nothing.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77af]
	 */
	    public  Offer() {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * Full constructor. This convenience constructor allows for setting all values in one go. Set empty message or rejectionCause to null.
	 * 
	 * @poseidon-object-id [I10b12509m11210c5471fmm77a1]
	 * @param offerId the offer Id
	 * @param fromUserId the ID of the user that sends the offer
	 * @param toUserId the ID of the user that is the target of the offer
	 * @param positionId the ID of the position that is being offered
	 * @param status the status of this offer
	 * @param percentageOffered the percentage of the project payment that is being offered
	 * @param message the message that comes with the offe
	 * @param rejectionCause the cause of the rejection if this offer is being rejected
	 * @throws IllegalArgumentException If any ID field is negative, or if status is null, or if percentageOffered is negative or larger than 100
	 */
	    public  Offer(long offerId, long fromUserId, long toUserId, long positionId, OfferStatus status, int percentageOffered, String message, String rejectionCause) {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * @return Returns the fromUserId.
	 */
	public long getFromUserId() {
		return fromUserId;
	}

	/**
	 * @param fromUserId The fromUserId to set.
	 */
	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the offerId.
	 */
	public long getOfferId() {
		return offerId;
	}

	/**
	 * @param offerId The offerId to set.
	 */
	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	/**
	 * @return Returns the percentageOffered.
	 */
	public int getPercentageOffered() {
		return percentageOffered;
	}

	/**
	 * @param percentageOffered The percentageOffered to set.
	 */
	public void setPercentageOffered(int percentageOffered) {
		this.percentageOffered = percentageOffered;
	}

	/**
	 * @return Returns the positionId.
	 */
	public long getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId The positionId to set.
	 */
	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return Returns the rejectionCause.
	 */
	public String getRejectionCause() {
		return rejectionCause;
	}

	/**
	 * @param rejectionCause The rejectionCause to set.
	 */
	public void setRejectionCause(String rejectionCause) {
		this.rejectionCause = rejectionCause;
	}

	/**
	 * @return Returns the status.
	 */
	public OfferStatus getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	/**
	 * @return Returns the toUserId.
	 */
	public long getToUserId() {
		return toUserId;
	}

	/**
	 * @param toUserId The toUserId to set.
	 */
	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}

}
