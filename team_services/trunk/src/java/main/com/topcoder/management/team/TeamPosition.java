package com.topcoder.management.team;

import java.io.Serializable;

public class TeamPosition {

	/**
	 * <p>Represents the position desctription.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>It can be any value, including null/empty.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7813]
	 */
	    private String description = null;

	/**
	 * <p>Represents a flag whether this position has been filled. Basically, the position is filled if memberResourceId is set.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm780d]
	 */
	    private boolean filled = false;

	/**
	 * <p>Represents the ID of the member resource that is taking this position.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be negative.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7807]
	 */
	    private long memberResourceId = -1;

	/**
	 * <p>Represents the percentage of the payment this position will pay.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>It will be a number between 0 and 100, inclusive</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7801]
	 */
	    private int paymentPercentage = 0;

	/**
	 * <p>Represents the name of this position.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>It can be any value, inlcuding null/empty.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm77fb]
	 */
	    private String name = null;

	/**
	 * <p>Represents the ID of this position.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be negative.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm77f5]
	 */
	    private long positionId = -1;

	/**
	 * <p>Represents a flag as to whether this position has been published and available.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm77ef]
	 */
	    private boolean published = false;

	/**
	 * <p>Represents a map of custom properties of this position.</p>
	 * <p>The map is created on instantiation to a non-null Map, and this reference will never be null. It is accessed via the setProperty, getproperty, and getAllproperties methods. The last method call results in a shallow copy of this map being returned. It contains String keys, and java.io.Serializable values.</p>
	 * <p>The map can be empty. It will never contain null/empty keys, and the values will never be null as well. The keys will also be never than 255 characters long.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm77e9]
	 */
	    private final java.util.Map customProperties = new java.util.HashMap();

	/**
	 * Default constructor. Does nothing.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm77e2]
	 */
	    public  TeamPosition() {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * Full constructor. This convenience constructor allows for setting all values in one go.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm77d5]
	 * @param description the position desctription
	 * @param filled a flag whether this position has been filled
	 * @param memberResourceId the ID of the member resource that is taking this position. Can be -1 if not yet set.
	 * @param paymentPercentage the percentage of the payment this position will pay
	 * @param name the name of this position
	 * @param positionId the ID of this position
	 * @param published a flag as to whether this position has been published and available
	 * @throws IllegalArgumentException If positionId is negative, or if paymentPercentage is not between 0 and 100, inclusive
	 */
	    public  TeamPosition(String description, boolean filled, long memberResourceId, int paymentPercentage, String name, long positionId, boolean published) {        /** lock-end */
	        // your code here
	    } /** lock-begin */


	/**
	 * Puts a custom property in the customProperties map. A null value removes the property from the map if it is present.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7759]
	 * @param key A String key of the property
	 * @param value A Serializable value of the property, or null if this is a remove action
	 * @throws IllegalArgumentException If key is null/empty or longer than 255 characters
	 */
	    public void setProperty(String key, java.io.Serializable value) {        /** lock-end */
	        customProperties.put(key,value);
	    } /** lock-begin */

	/**
	 * Gets a custom property from the customProperties map. Value will not be null.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm774f]
	 * @return A Serializable value of the property. it will not be null
	 * @param key A String key of the property whose value is to be retrieved from the customProperties map.
	 * @throws IllegalArgumentException If key is null/empty or longer than 255 characters
	 */
	    public java.io.Serializable getProperty(String key) {        /** lock-end */
	 return (Serializable) customProperties.get(key); 
	    } /** lock-begin */

	/**
	 * Returns a shallow copy of the customProperties map.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7746]
	 * @return a shallow copy of the customProperties map.
	 */
	    public java.util.Map getAllProperties() {        /** lock-end */
	 return customProperties; 
	    } /** lock-begin */

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the filled.
	 */
	public boolean getFilled() {
		return filled;
	}

	/**
	 * @param filled The filled to set.
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * @return Returns the memberResourceId.
	 */
	public long getMemberResourceId() {
		return memberResourceId;
	}

	/**
	 * @param memberResourceId The memberResourceId to set.
	 */
	public void setMemberResourceId(long memberResourceId) {
		this.memberResourceId = memberResourceId;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the paymentPercentage.
	 */
	public int getPaymentPercentage() {
		return paymentPercentage;
	}

	/**
	 * @param paymentPercentage The paymentPercentage to set.
	 */
	public void setPaymentPercentage(int paymentPercentage) {
		this.paymentPercentage = paymentPercentage;
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
	 * @return Returns the published.
	 */
	public boolean getPublished() {
		return published;
	}

	/**
	 * @param published The published to set.
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}

}
