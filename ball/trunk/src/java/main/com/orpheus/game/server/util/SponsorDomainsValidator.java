/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.datavalidator.registration.validators.UserNameValidator;
import com.topcoder.util.datavalidator.registration.ConfigurationException;
import com.topcoder.util.config.Property;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>A custom <code>Validator</code> implementation to be used for validating the submitted values for representing
 * synctactically valid domain names.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorDomainsValidator extends UserNameValidator {

    /**
     * <p>A <code>String</code> providing the reqular expression representing the domain names.</p>
     */
    private static final Pattern DOMAIN_NAME_PATTERN
        = Pattern.compile("[[a-zA-Z][a-zA-Z0-9-]*][.[a-zA-Z][a-zA-Z0-9-]*]*");

    /**
     * <p>Constructs new <code>SponsorDomainsValidator</code> instance. This implementation does nothing.</p>
     * 
     * @param property a <code>Property</code> providing the configuration parameters to configure this validator.
     * @throws ConfigurationException if property holds invalid configuration data.
     * @throws NullPointerException if property is <code>null</code> .
     */
    public SponsorDomainsValidator(Property property) throws ConfigurationException {
        super(property);
    }

    /**
     * <p>Validates the specified field (it's string representation) for providing a valid domain name.</p>
     *
     * @param field an <code>Object</code> providing the actual field to validate.
     * @param attributes a <code>Map</code> providing the attributes set as a context.
     * @return <code>true</code> if specified field represents a valid domain name; <code>false</code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public boolean validate(Object field, Map attributes) {
        if (field == null) {
            throw new IllegalArgumentException("The parameter [field] is NULL");
        }
        String domainName = field.toString();
        return super.validate(field, attributes) && (DOMAIN_NAME_PATTERN.matcher(domainName).matches());
    }
}

