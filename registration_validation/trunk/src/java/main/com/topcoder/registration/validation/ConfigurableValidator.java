/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * An extension of the Data Validation&#146;s Object Validator to add a
 * setter for the DataValidationRegistrationValidator so each validator in this
 * component has access to managers, services, and logger. This allows for one
 * centralized configuration. All implemented validators in this component
 * implement this interface, and all new validators will have to as well.
 * </p>
 * <p>
 * Thread Safety: In general, implementations should be thread-safe, but there
 * is no need to synchronize the id field.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface ConfigurableValidator extends ObjectValidator {
    /**
     * Sets the DataValidationRegistrationValidator so each validator has access
     * to the services of the RegistrationValidator. This may include access to
     * a Log, or other managers and services required to perform the validation
     *
     * @param dataValidationRegistrationValidator
     *            DataValidationRegistrationValidator
     * @throws IllegalArgumentException
     *             If dataValidationRegistrationValidator is null
     */
    public void setRegistrationValidator(
            DataValidationRegistrationValidator dataValidationRegistrationValidator);
}
