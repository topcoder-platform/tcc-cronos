package com.topcoder.management.team;

import java.io.Serializable;

public interface Team extends Serializable {

	/**
	 * Gets the teamHeader field value.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7a33]
	 * @return The teamHeader field value
	 */
	    public TeamHeader getTeamHeader();
	/**
	 * Sets the teamHeader field value.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7a2a]
	 * @param teamHeader The teamHeader field value
	 */
	    public void setTeamHeader(TeamHeader teamHeader);
	/**
	 * Gets the positions field value.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7a22]
	 * @return The positions field value
	 */
	    public TeamPosition[] getPositions();
	/**
	 * Sets the positions field value.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7a1a]
	 * @param positions The positions field value
	 */
	    public void setPositions(TeamPosition[] positions);	    
}
