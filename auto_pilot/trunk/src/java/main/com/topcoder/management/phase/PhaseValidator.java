/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;

/**
 * <p>
 * This is a validation contract for phase objects. Implementations will apply some validation
 * criteria and will signal validation issues with a PhaseValidationException being thrown.
 * </p>
 * <p>
 * Thread-Safety Implementations should strive to ensure thread safety. A validator should act as a
 * stateless utility.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface PhaseValidator {
    /**
     * <p>
     * Will be implemented to validate the input phase. This will usually test if the phase data is
     * consistent with some specific rules (for example when certain fields can not be empty)
     * </p>
     * <p>
     * Exception Handling
     * @throws PhaseValidationException if validation fails
     * @throws IllegalArgumentException if phase input is null.
     *             </p>
     * @param phase phase to validate
     */
    public void validate(Phase phase) throws PhaseValidationException;
}
