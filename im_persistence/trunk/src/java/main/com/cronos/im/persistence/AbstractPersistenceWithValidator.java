/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>A common base class for all persistence implementations that use an <code>ObjectValidator</code>.
 *
 * <p>Note that this class is not intended to be used directly by clients of this component, but it needs to be
 * public in order to be accessible to the <i>rolecategories</i> subpackage.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public abstract class AbstractPersistenceWithValidator extends AbstractPersistence {
    /**
     * Object that will perform validation of the chat user profile. This member is initialized at construction, is
     * immutable, and may be <code>null</code> if no validation is required.
     */
    private final ObjectValidator validator;

    /**
     * Constructs a new <code>AbstractPersistenceWithValidator</code> from the specified configuration namespace.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws ConfigurationException if the namespace is invalid or a required property is missing
     */
    protected AbstractPersistenceWithValidator(String namespace) throws ConfigurationException {
        super(namespace);

        String specNamespace = getSpecNamespace();
        String vdKey = ConfigHelpers.getOptionalProperty(namespace, "validator_key");
        // create the optional validator
        try {
            this.validator = (vdKey == null
                              ? null : (ObjectValidator) ConfigHelpers.createObject(specNamespace, vdKey));
        } catch (ClassCastException ex) {
            throw new ConfigurationException("invalid type for validator_key");
        }
    }

    /**
     * Constructs a new <code>AbstractPersistenceWithValidator</code> with the specified DB connection factory,
     * connection name, and object validator.
     *
     * @param connectionFactory the DB connection factory
     * @param connectionName the connection name
     * @param validator the object validator (or <code>null</code> if no validation is required)
     * @throws IllegalArgumentException if <code>connectionFactory</code> is <code>null</code> or
     *   <code>connectionName</code> is <code>null</code> or an empty string
     */
    protected AbstractPersistenceWithValidator(DBConnectionFactory connectionFactory, String connectionName,
                                               ObjectValidator validator) {
        super(connectionFactory, connectionName);

        this.validator = validator;
    }

    /**
     * Returns the object validator or <code>null</code> if no validation is required.
     *
     * @return the object validator or <code>null</code> if no validation is required
     */
    protected ObjectValidator getValidator() {
        return validator;
    }
}
