package com.topcoder.service.studio.contest;

public class User {

	/**
	 * Generated serial version id.
	 */
	private static final long serialVersionUID = -6993488651979864257L;

	/**
	 * Represents the contest id.
	 */
	private Long userId;

	/**
	 * Represents the user handle.
	 */
	private String handle;

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
