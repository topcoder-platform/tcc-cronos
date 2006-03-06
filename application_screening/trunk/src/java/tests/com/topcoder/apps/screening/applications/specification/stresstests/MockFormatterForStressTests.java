/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.apps.screening.applications.specification.stresstests;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;

/**
 * <p>
 * Custom formatter implementation for stress tests.
 * </p>
 * 
 * @author nhzp339
 * @version 1.0
 */
public class MockFormatterForStressTests implements ValidationOutputFormatter {
	/**
	 * <p>
	 * The empty constructor.
	 * </p>
	 */
	public MockFormatterForStressTests() {
	}

	/**
	 * <p>
	 * Output summary only.
	 * </p>
	 * 
	 * @param output
	 *            validation result.
	 * @return formatted validation result to string.
	 */
	public String[] format(ValidationOutput[] output) {
		String[] ret = new String[2];
		int countErrors = 0;
		for (int i = 0; i < output.length; i++) {
			if (output[i].getType() == ValidationOutputType.ERROR) {
				countErrors++;
			}
		}
		if (countErrors == 0) {
			ret[0] = "The submission is valid.";
			ret[1] = "Errors found: 0.";
		} else {
			ret[0] = "The submission is invalid.";
			ret[1] = "Errors found: " + String.valueOf(countErrors) + ".";
		}
		return ret;
	}
}