/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.impl.formatters.TextValidationOutputFormatter;


/**
 * <p>
 * Failure tests for <code>TextValidationOutputFormatter</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class TextValidationOutputFormatterFailureTests extends TestCase {
    /**
     * Failure test for <code>format(ValidationOutput[])</code>, with null output array, IAE expected.
     */
    public void testFormat_null_outputs() {
        try {
            (new TextValidationOutputFormatter()).format(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>format(ValidationOutput[])</code>, with null output contained in the array, IAE expected.
     */
    public void testFormat_null_output_contained() {
        ValidationOutput[] outputs = new ValidationOutput[1];
        outputs[0] = null;

        try {
            (new TextValidationOutputFormatter()).format(outputs);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
