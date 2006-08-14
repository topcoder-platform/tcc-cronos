/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

/**
 * <p>
 * The CommentType class supports the Comment class, and allows a comment to be
 * tagged as being of a certain type/style. Unlike the other classes in this
 * component, which are likely to be created dynamically and frequently, only a
 * few CommentType instances are likely to be used in any application. For this
 * component, this consideration really has no impact on the design or
 * development of this class. Like the other classes in this component, it
 * consists of simple data fields, each of which has a 3 method get/set/reset
 * combination for manipulating that field.
 * </p>
 * <p>
 * Thread Safty: This class is highly mutable and is not thread safe. All fields
 * can be changed.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class CommentType implements Serializable {

    /**
     * id: The identifier of the CommentType. This field is initialized in the
     * constructor, is mutable, and can be > 0 or equal to -1. By default, this
     * field is initialized to -1. This value indicates that the id of the
     * CommentType has not yet been set through the constructor or a call to
     * setId() (or has been set back to this value by a call to resetId()).
     * Values > 0 indicate that the client using the component has assigned an
     * id (in the constructor or through the setId() method). It is expected
     * that all CommentType instances will have unique ids. This is up to the
     * client using the component to enforce - this component will not enforce
     * this condition. The id field can be set using the setId/resetId methods
     * and retrieved using the getId method.
     */
    private long id;

    /**
     * name: The name for the comment type. This field is set in the
     * constructor, is mutable, and can be null. The default for this field is
     * null, which indicates that no name has yet been associated with the type.
     * This field can be set through the setName/resetName methods and retrieved
     * through the getName method. No restrictions are applied to this field -
     * it may be the empty string, all whitespace, etc.
     */
    private String name;

    /**
     * <p>
     * Create a new CommentType, setting all the fields to the "unassigned
     * values".
     * </p>
     */
    public CommentType() {
        id = -1;
        name = null;
    }

    /**
     * <p>
     * Create a new CommentType, setting id to the given value and name to the
     * default, null.
     * </p>
     * @param id
     *            The id of the CommentType
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public CommentType(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
        name = null;
    }

    /**
     * <p>
     * Create a new CommentType, setting id and name to the given values.
     * </p>
     * @param id
     *            The id of the CommentType
     * @param name
     *            The name for the CommentType
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null
     */
    public CommentType(long id, String name) {
        Helper.assertLongPositive(id, "id");
        Helper.assertObjectNotNull(name, "name");

        this.id = id;
        this.name = name;
    }

    /**
     * <p>
     * Sets the unique identifier of the CommentType. The id value must be > 0.
     * </p>
     * @param id
     *            The identifier to assign to the comment
     * @throws IllegalArgumentException
     *             if id <= 0
     */
    public void setId(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
    }

    /**
     * <p>
     * Retrieves the id for this CommentType.
     * </p>
     * @return The id of the CommentType
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the id of the Review back to its "undefined" value, which is -1.
     * </p>
     */
    public void resetId() {
        id = -1;
    }

    /**
     * <p>
     * Sets the name of the CommentType. The name argument is permitted to be
     * null or any other string (empty string, all whitespace, etc).
     * </p>
     * @param name
     *            The name of the CommentType
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the name of the CommentType. This method may return null, indicating
     * that no name is associated with the CommentType.
     * </p>
     * @return The name of the comment type
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of the CommentType back to its default value, null.
     * </p>
     */
    public void resetName() {
        setName(null);
    }
}
