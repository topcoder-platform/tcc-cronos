/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.stresstests;

import com.topcoder.direct.actions.SpecificationReviewAction;

/**
 * The purpose of this class is to expose the protected method.
 *
 * @author moon.river
 * @version 1.0
 */
public class SpecificationReviewActionTester extends SpecificationReviewAction {
    /**
     * Stores a validation error in <code>AggregateDataModel</code> data map.
     *
     * @param propertyName the property name, cannot be null or empty
     * @param messages the validation error messages, cannot be null or empty, elements cannot be null or empty
     *
     * @throws IllegalArgumentException if propertyName is null or empty or if messages is null or empty or
     *         contains null or empty elements
     */
    public void addValidationError(String propertyName, String[] messages) {
        super.addValidationError(propertyName, messages);
    }
}
