/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for information about a single comment evaluation type. Evaluation type is expected to
 * represent the validity and importance of the comment. It is a simple JavaBean (POJO) that provides getters and
 * setters for all private attributes and performs no argument validation in the setters. For consistency with existing
 * entities it additionally provides reset methods for all attributes.
 * </p>
 * <p>
 * <strong> Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 * @author saarixx, TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class EvaluationType implements Serializable {
    /**
     * <p>
     * The ID of the evaluation type. Has getter and setter. It can only be set as positive.
     * </p>
     * @since 1.1
     */
    private long id = -1;

    /**
     * <p>
     * The name of the evaluation type. Can be any value. Has getter and setter.
     * </p>
     * @since 1.1
     */
    private String name = null;

    /**
     * <p>
     * The description of the evaluation type. Can be any value. Has getter and setter.
     * </p>
     * @since 1.1
     */
    private String description = null;

    /**
     * <p>
     * Creates an instance of EvaluationType.
     * </p>
     * @since 1.1
     */
    public EvaluationType() {
    }

    /**
     * <p>
     * EvaluationType constructor: Create a new EvaluationType, setting id to the given value.
     * </p>
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @param id
     *            The id of the EvaluationType
     * @since 1.1
     */
    public EvaluationType(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id should be positive.");
        }
        this.id = id;
    }

    /**
     * <p>
     * EvaluationType constructor: Create a new EvaluationType, setting id and name to the given values.
     * </p>
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null
     * @param id
     *            The id of the EvaluationType
     * @param name
     *            The name for the EvaluationType
     * @since 1.1
     */
    public EvaluationType(long id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("id should be positive.");
        }
        Helper.checkNull(name, "name");
        this.id = id;
        this.name = name;
    }

    /**
     * <p>
     * EvaluationType constructor: Create a new EvaluationType, setting id, name and description to the given values.
     * </p>
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null or description is null
     * @param id
     *            The id of the EvaluationType
     * @param description
     *            The description for the EvaluationType
     * @param name
     *            The name for the EvaluationType
     * @since 1.1
     */
    public EvaluationType(long id, String name, String description) {
        if (id <= 0) {
            throw new IllegalArgumentException("id should be positive.");
        }
        Helper.checkNull(name, "name");
        Helper.checkNull(description, "description");
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * <p>
     * Retrieves the ID of the evaluation type.
     * </p>
     * @return
     *         the ID of the evaluation type
     * @since 1.1
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the ID of the evaluation type.
     * </p>
     * @param id
     *            the ID of the evaluation type
     * @since 1.1
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Sets the id of the EvaluationType back to its "undefined" value, which is -1.
     * </p>
     * @since 1.1
     */
    public void resetId() {
        this.id = -1;
    }

    /**
     * <p>
     * Retrieves the name of the evaluation type.
     * </p>
     * @return the name of the evaluation type
     * @since 1.1
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of the evaluation type.
     * </p>
     * @param name
     *            the name of the evaluation type
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Sets the name of the EvaluationType back to its default value, null.
     * </p>
     */
    public void resetName() {
        this.name = null;
    }

    /**
     * <p>
     * Retrieves the description of the evaluation type.
     * </p>
     * @return
     *         the description of the evaluation type
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of the evaluation type.
     * </p>
     * @param description
     *            the description of the evaluation type
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Sets the description of the EvaluationType back to its default value, null.
     * </p>
     */
    public void resetDescription() {
        this.description = null;
    }
}
