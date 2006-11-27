/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.persistence.DomainTarget;

/**
 *<p>
 * The mock implement DomainTarget interface.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestDomainTarget implements DomainTarget {
	/**
	 * The uriPath of the DomainTarget.
	 */
	private String uriPath;

	/**
	 * Get the id.
	 * @return the id
	 */
	public Long getId() {
		return null;
	}

	/**
	 * Get the sequence number.
	 * @return the sequence number
	 */
	public int getSequenceNumber() {
		return 0;
	}

	/**
	 * Get the uripath.
	 * @return the uripath.
	 */
	public String getUriPath() {
		return this.uriPath;
	}

	/**
	 * Set the uripath.
	 * @param path the path
	 */
	public void setUriPath(String path) {
	    this.uriPath = path;	
	}

	/**
	 * Get the identifier.
	 * @return the identifier
	 */
	public String getIdentifierText() {
		return null;
	}

	/**
	 * Get the identifier.
	 * @return the identifier hash
	 */
	public String getIdentifierHash() {
		return null;
	}

	/**
	 * Get the clue image id.
	 * @return the clue image id
	 */
	public long getClueImageId() {
		return 0;
	}

}
