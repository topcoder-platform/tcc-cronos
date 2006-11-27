/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.ImageInfo;

/**
 * <p>
 * The mock implemention of the Domain interface used for tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestDomain implements Domain {
	/**
	 * The domain name
	 */
	private String name;

	/**
	 * Get Id of the domain.
	 * @return the id of the domain
	 */
	public Long getId() {
		return null;
	}

	/**
	 * Get the sponsor id.
	 * @return the sponsor id.
	 */
	public long getSponsorId() {
		return 0;
	}

	/**
	 * Get the Domain name.
	 * @return the domain name
	 */
	public String getDomainName() {
		return name;
	}

	/**
	 * Set the domain name.
	 * @param name the name
	 */
	public void setDomainName(String name) {
	    this.name =	name;
	}

	/**
	 * Return the isApproved.
	 * @return the isApproved
	 */
	public Boolean isApproved() {
		return null;
	}

    /**
     * Get the images.
     * @return the images.
     */
	public ImageInfo[] getImages() {
		return null;
	}

}
