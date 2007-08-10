/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.datavalidator.registration.validators.RegistrationValidator;
import com.topcoder.util.datavalidator.registration.ConfigurationException;
import com.topcoder.util.config.Property;

import java.util.Map;

/**
 * <p>A custom registration data validator which is responsible for verifying if the validated input field which is
 * expected to provide a confirmed value of some other input field actually matches the value of that original input
 * field (for example, password confirmation matches the password). Currently there is no such validator provided by any
 * of existing components.</p>
 *
 * <p>Sample Segment:</p>
 *
 * <pre>
 * &lt;Property name=&quot;validator&quot;&gt;
 *    &lt;Property name=&quot;class&quot;&gt;
 *       &lt;Value&gt;com.orpheus.game.server.util.InputFieldConfirmationValidator&lt;/Value&gt;
 *    &lt;/Property&gt;
 *    &lt;Property name=&quot;error_message&quot;&gt;
 *       &lt;Value&gt;Password confirmation does not match the original password.&lt;/Value&gt;
 *    &lt;/Property&gt;
 *    &lt;Property name=&quot;originalParamName&quot;&gt;
 *       &lt;Value&gt;password0&lt;/Value&gt;
 *    &lt;/Property&gt;
 * &lt;/Property&gt;
 * </pre>
 *
 * @author isv
 * @version 1.0
 */
public class InputFieldConfirmationValidator extends RegistrationValidator {

    /**
     * <p>A <code>String</code> providing the name of the original parameter of </p>
     */
    private final String originalParamName;

    /**
     * <p>Constructs new <code>InputFieldConfirmationValidator</code> instance to be used for validating the input
     * field for matching the value of specifie input field.</p>
     *
     * @param originalParamName a <code>String</code> providing the name of the original input field to match the
     *        validated value to.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code> or empty.
     */
    public InputFieldConfirmationValidator(String originalParamName) {
        if ((originalParamName == null) || (originalParamName.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [originalParamName] is not valid. ["
                                               + originalParamName + "]");
        }
        this.originalParamName = originalParamName;
    }

    /**
     * <p>Constructs new <code>InputFieldConfirmationValidator</code> instance initialized base on the configuration
     * parameters provided by the specified configuration property.</p>
     *
     * @param property a <code>Property</code> providing the configuration parameters to be used to configure this
     *        validator.
     * @throws NullPointerException if property is <code>null</code>.
     * @throws ConfigurationException if property holds invalid configuration data.
     */
    public InputFieldConfirmationValidator(Property property) throws ConfigurationException {
        if (property == null) {
            throw new NullPointerException("The parameter [property] is NULL");
        }
        String value = property.getValue("originalParamName");
        if ((value == null) || (value.trim().length() == 0)) {
            throw new ConfigurationException("originalParamName value is empty");
        }
        this.originalParamName = value;
    }

    /**
     * <p>Checks if the specified field matches the value of the original input field.</p>
     *
     * @param field an <code>Object</code> providing the actual field to validate.
     * @param attributes a <code>Map</code> providing the attribute set as a context.
     * @return <code>true</code> if the specified field is considered valid; <code>false</code> otherwise.
     * @throws NullPointerException if any of specified arguments is <code>null</code>.
     */
    public boolean validate(Object field, Map attributes) {
        if (field == null) {
            throw new NullPointerException("The parameter [field] is NULL");
        }
        if (attributes == null) {
            throw new NullPointerException("The parameter [attributes] is NULL");
        }
        return field.equals(attributes.get(this.originalParamName));
    }
}
