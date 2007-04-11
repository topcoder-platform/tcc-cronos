/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;

import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>A common base class for persistence implementations that use an ID generator.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

abstract class AbstractPersistenceWithGenerator extends AbstractPersistenceWithValidator {
    /**
     * The ID generator used to create IDs for new profiles. This member is initialized at construction, is
     * immutable, and will never be <code>null</code>.
     */
    private final IDGenerator idGenerator;

    /**
     * Constructs a new <code>AbstractPersistenceWithGenerator</code> from the specified configuration namespace.
     *
     * @param namespace the configuration namespace
     * @throws ConfigurationException if the namespace is invalid or a required property is missing
     */
    AbstractPersistenceWithGenerator(String namespace) throws ConfigurationException {
        super(namespace);

        String idGen = ConfigHelpers.getRequiredProperty(namespace, "id_generator");

        this.idGenerator = ConfigHelpers.createIDGenerator(idGen);
    }

    /**
     * Constructs a new <code>AbstractPersistenceWithGenerator</code> with the specified attributes.
     *
     * @param connectionFactory the DB connection factory
     * @param connectionName the connection name
     * @param idGenerator the ID generator
     * @param validator the object validator (or <code>null</code> if no validation is required)
     * @throws IllegalArgumentException if <code>connectionFactory</code> is <code>null</code> or
     *   <code>connectionName</code> is <code>null</code> or an empty string or <code>idGenerator</code> is
     *   <code>null</code>
     */
    AbstractPersistenceWithGenerator(DBConnectionFactory connectionFactory, String connectionName,
                                     IDGenerator idGenerator, ObjectValidator validator) {
        super(connectionFactory, connectionName, validator);

        ParameterHelpers.checkParameter(idGenerator, "ID generator");

        this.idGenerator = idGenerator;
    }

    /**
     * Returns the configured ID generator.
     *
     * @return the configured ID generator
     */
    IDGenerator getGenerator() {
        return this.idGenerator;
    }
}
