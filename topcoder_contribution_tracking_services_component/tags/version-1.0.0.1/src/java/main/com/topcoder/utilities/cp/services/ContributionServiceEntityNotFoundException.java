/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of the services when entity with the given ID does not exist in the
 * persistence.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ContributionServiceEntityNotFoundException extends ContributionServiceException {
    /**
     * The type name of the entity that doesn't exist. Is initialized in the constructor and never changed after that.
     * Can be any value. Has a getter.
     */
    private final String entityTypeName;

    /**
     * The id of the entity that doesn't exist. Is initialized in the constructor and never changed after that. Can be
     * any value. Has a getter.
     */
    private final long entityId;

    /**
     * <p>
     * Constructor with error message, entity type name and entity id.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param entityTypeName
     *            the type name of the entity that doesn't exist.
     * @param entityId
     *            the id of the entity that doesn't exist.
     */
    public ContributionServiceEntityNotFoundException(String message, String entityTypeName, long entityId) {
        super(message);

        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * <p>
     * Constructor with error message, inner cause, entity type name and entity id.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param cause
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception
     *            is nonexistent or unknown.
     * @param entityTypeName
     *            the type name of the entity that doesn't exist.
     * @param entityId
     *            the id of the entity that doesn't exist.
     */
    public ContributionServiceEntityNotFoundException(String message, Throwable cause, String entityTypeName,
        long entityId) {
        super(message, cause);

        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * <p>
     * Constructor with error message, exception data, entity type name and entity id.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param data
     *            the additional data to attach to the exception. If this is <code>null</code>, a new
     *            ExceptionData() will be automatically used instead.
     * @param entityTypeName
     *            the type name of the entity that doesn't exist.
     * @param entityId
     *            the id of the entity that doesn't exist.
     */
    public ContributionServiceEntityNotFoundException(String message, ExceptionData data, String entityTypeName,
        long entityId) {
        super(message, data);

        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * <p>
     * Constructor with error message, inner cause, exception data, entity type name and entity id.
     * </p>
     *
     * @param message
     *            the explanation of the error. Can be empty string or <code>null</code> (useless, but allowed).
     * @param cause
     *            the underlying cause of the error. Can be <code>null</code>, which means that initial exception
     *            is nonexistent or unknown.
     * @param data
     *            the additional data to attach to the exception. If this is <code>null</code>, a new
     *            ExceptionData() will be automatically used instead.
     * @param entityTypeName
     *            the type name of the entity that doesn't exist.
     * @param entityId
     *            the id of the entity that doesn't exist.
     */
    public ContributionServiceEntityNotFoundException(String message, Throwable cause, ExceptionData data,
        String entityTypeName, long entityId) {
        super(message, cause, data);

        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * Gets the type name of the entity that doesn't exist.
     *
     * @return the type name of the entity that doesn't exist.
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * Gets the id of the entity that doesn't exist.
     *
     * @return the id of the entity that doesn't exist.
     */
    public long getEntityId() {
        return entityId;
    }
}
