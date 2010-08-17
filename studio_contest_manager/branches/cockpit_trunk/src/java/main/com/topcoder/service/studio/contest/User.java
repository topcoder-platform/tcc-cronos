/*
 * Copyright (C) 2008-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>An entity class representing a single user.</p>
 *
 * <p>
 * Version 1.1 (Direct Permissions Setting Back-end and Integration Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Marked the class as serializable.</li>
 *   </ol>
 * </p>

 * @version 1.1
 */
public class User implements Serializable {

	/**
	 * Generated serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represents the contest id.
	 */
	private Long userId;

	/**
	 * Represents the user handle.
	 */
	private String handle;

    /**
     * <p>Gets the user handle.</p>
     *
     * @return a <code>String</code> providing the user handle.
     */
	public String getHandle() {
		return handle;
	}

    /**
     * <p>Sets the user handle.</p>
     *
     * @param handle a <code>String</code> providing the user handle.
     */
	public void setHandle(String handle) {
		this.handle = handle;
	}

    /**
     * <p>Gets the user ID.</p>
     *
     * @return a <code>Long</code> providing the user ID.
     */
	public Long getUserId() {
		return userId;
	}

    /**
     * <p>Sets the user ID.</p>
     *
     * @param userId a <code>Long</code> providing the user ID.
     */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
