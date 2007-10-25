/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import java.io.Serializable;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamPosition;

/**
 * <p>
 * The interface that defines the information about the position a resource occupies in a team, as
 * well as providing the resource info itself. Extends <code>java.io.Serializable</code> so it can
 * be used in a remote environment.
 * </p>
 * <p>
 * This interface follows java bean conventions for defining setters and getters for these
 * properties. The implementations are to provide the implementations of these and at least an empty
 * constructor.
 * </p>
 * <p>
 * It has one implementation: <code>ResourcePositionImpl</code>.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface ResourcePosition extends Serializable {

    /**
     * <p>
     * Gets the <code>memberResource</code> field value.
     * </p>
     * @return The memberResource field value
     */
    public Resource getMemberResource();

    /**
     * <p>
     * Sets the <code>memberResource</code> field value.
     * </p>
     * @param memberResource
     *            The memberResource field value
     * @throws IllegalArgumentException
     *             If memberResource is null
     */
    public void setMemberResource(Resource memberResource);

    /**
     * <p>
     * Gets the position field value.
     * </p>
     * @return The position field value
     */
    public TeamPosition getPosition();

    /**
     * <p>
     * Sets the position field value.
     * </p>
     * @param position
     *            The position field value
     * @throws IllegalArgumentException
     *             If position is null
     */
    public void setPosition(TeamPosition position);
}
