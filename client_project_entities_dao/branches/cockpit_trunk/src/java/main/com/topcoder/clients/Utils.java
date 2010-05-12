/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients;

import java.util.List;

import com.topcoder.clients.model.ProjectContestFee;

/**
 * <p>
 * This is utility class. It provides some methods to be shared across.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class does not contain any changeable
 * fields, so is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Configurable Contest Fees v1.0 Assembly
 */
public final class Utils {
	/**
	 * Prevents this class to be created.
	 */
	private Utils() {
		// do nothing
	}

	/**
	 * Checks to make sure the name has no wildcard characers: % or *.
	 *
	 * @param name
	 *            the search name
	 * @throws IllegalArgumentException
	 *             if the name contains wildcard characters: % or *
	 */
	public static void checkSearchName(String name) {
		// name can not contains wildcard characters % or *
		if (name != null && (name.contains("%") || name.contains("*"))) {
			throw new IllegalArgumentException(
					"the name should not contain wildcard % or *");
		}
	}

	/**
	 * Checks contest fees parameter.
	 *
	 * @param contestFees
	 *            contest fees parameter. it could be null.
	 * @param projectId
	 *            the project id
	 *
	 * @throws IllegalArgumentException
	 *             if the associated project id is not equal to the one given or
	 *             the <code>contestFees</code> contains null element
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	public static void checkContestFees(List<ProjectContestFee> contestFees,
			long projectId) {
		if (contestFees == null) {
			return;
		}

		for (ProjectContestFee fee : contestFees) {
			if (fee == null) {
				throw new IllegalArgumentException(
						"contestFees should not contain null element.");
			}

			if (fee.getProjectId() != projectId) {
				throw new IllegalArgumentException(
						"contestFee in the list should belong to project of id "
								+ projectId);
			}
		}
	}
}
