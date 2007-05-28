package com.topcoder.management.team;

import java.io.Serializable;

public class TeamHeader implements Serializable{

	/**
	 * <p>Represents the name of this team.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>It can be any value, including null/empty.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7a0c]
	 */
	    private String name = null;

	/**
	 * <p>Represents a flag whether the time details are finalized.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7a05]
	 */
	    private boolean finalized = false;

	/**
	 * <p>Represents the ID of the project this team is participating in.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be negative.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm79fe]
	 */
	    private long projectId = -1;

	/**
	 * <p>Represents the ID of this team.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be negative.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm79f8]
	 */
	    private long teamId = -1;

	/**
	 * <p>Represents the ID of the resource that represents the captain.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be negative.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm79f2]
	 */
	    private long captainResourceId = -1;

	/**
	 * <p>Represents the percentage of the payment the resource that represents the captain is assigned.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>It will be a number between 0 and 100, inclusive</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm79eb]
	 */
	    private int captainPaymentPercentage = -1;

	/**
	 * <p>Represents a map of custom properties of this team.</p>
	 * <p>The map is created on instantiation to a non-null Map, and this reference will never be null. It is accessed via the setProperty, getproperty, and getAllproperties methods. The last method call results in a shallow copy of this map being returned. It contains String keys, and java.io.Serializable values.</p>
	 * <p>The map can be empty. It will never contain null/empty keys, and the values will never be null as well. The keys will also be never than 255 characters long.</p>
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm79e1]
	 */
	    private final java.util.Map customProperties = new java.util.HashMap();

	/**
	 * <p>Represents the team desctription.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>It can be any value, including null/empty.</p>
	 * <p></p>
	 * 
	 * @poseidon-object-id [I444512b7m112014f7825mm27e1]
	 */
	    private String description = null;

	/**
	 * Default constructor. Does nothing.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm79da]
	 */
	    public  TeamHeader() {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * Full constructor. This convenience constructor allows for setting all values in one go.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm79ce]
	 * @param name the name of this team
	 * @param finalized a flag whether the time details are finalized
	 * @param projectId the ID of the project this team is participating in
	 * @param teamId the ID of this team
	 * @param captainResourceId the ID of the resource that represents the captain
	 * @param captainPaymentPercentage the percentage of the payment the resource that represents the captain is assigned
	 * @param description the team description
	 * @throws IllegalArgumentException If projectId, teamId, or captainResourceId is negative, or if captainPaymentPercentage is not between 0 and 100, inclusive
	 */
	    public  TeamHeader(String name, boolean finalized, long projectId, long teamId, long captainResourceId, int captainPaymentPercentage, String description) {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * @return Returns the captainPaymentPercentage.
	 */
	public int getCaptainPaymentPercentage() {
		return captainPaymentPercentage;
	}

	/**
	 * @param captainPaymentPercentage The captainPaymentPercentage to set.
	 */
	public void setCaptainPaymentPercentage(int captainPaymentPercentage) {
		this.captainPaymentPercentage = captainPaymentPercentage;
	}

	/**
	 * @return Returns the captainResourceId.
	 */
	public long getCaptainResourceId() {
		return captainResourceId;
	}

	/**
	 * @param captainResourceId The captainResourceId to set.
	 */
	public void setCaptainResourceId(long captainResourceId) {
		this.captainResourceId = captainResourceId;
	}

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
	 * @return Returns the finalized.
	 */
	public boolean isFinalized() {
		return finalized;
	}

	/**
	 * @param finalized The finalized to set.
	 */
	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
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
	 * @return Returns the projectId.
	 */
	public long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId The projectId to set.
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return Returns the teamId.
	 */
	public long getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId The teamId to set.
	 */
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	/**
	 * Puts a custom property in the customProperties map. A null value removes the property from the map if it is present.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm795a]
	 * @param key A String key of the property
	 * @param value A Serializable value of the property, or null if this is a remove action
	 * @throws IllegalArgumentException If key is null/empty or longer than 255 characters
	 */
	    public void setProperty(String key, java.io.Serializable value) {        /** lock-end */
	        this.customProperties.put(key,value);
	    } /** lock-begin */

	/**
	 * Gets a custom property from the customProperties map. Value will not be null.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7950]
	 * @return A Serializable value of the property. it will not be null
	 * @param key A String key of the property whose value is to be retrieved from the customProperties map.
	 * @throws IllegalArgumentException If key is null/empty or longer than 255 characters
	 */
	    public java.io.Serializable getProperty(String key) {        /** lock-end */
	 return (Serializable) this.customProperties.get(key); 
	    } /** lock-begin */

	/**
	 * Returns a shallow copy of the customProperties map.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7947]
	 * @return a shallow copy of the customProperties map.
	 */
	    public java.util.Map getAllProperties() {        /** lock-end */
	 return customProperties; 
	    } /** lock-begin */

}
