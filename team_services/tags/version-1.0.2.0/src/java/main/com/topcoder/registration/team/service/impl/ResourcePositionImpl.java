/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.registration.team.service.ResourcePosition;

/**
 * <p>
 * Simple implementation of the <code>ResourcePosition</code> interface. Implements all methods in
 * that interface, and provides the a default constructor if the user wants to set all values via
 * the setters, and one full constructor to set these values in one go
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ResourcePositionImpl implements ResourcePosition {

    /**
     * <p>
     * Represents the resource that occupies the position.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, it will not be null.
     * </p>
     */
    private Resource memberResource = null;

    /**
     * <p>
     * Represents the information about the position that the resource occupies.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, it will not be null.
     * </p>
     */
    private TeamPosition position = null;

    /**
     * Default constructor.
     */
    public ResourcePositionImpl() {
    }

    /**
     * <p>
     * Full constructor.
     * </p>
     * <p>
     * This convenience constructor allows for setting all values in one go.
     * </p>
     * @param memberResource
     *            the resource that occupies the position
     * @param position
     *            the information about the position that the resource occupies
     * @throws IllegalArgumentException
     *             If either parameter is null
     */
    public ResourcePositionImpl(Resource memberResource, TeamPosition position) {
        Util.checkObjNotNull(memberResource, "memberResource");
        Util.checkObjNotNull(position, "position");
        this.memberResource = memberResource;
        this.position = position;
    }

    /**
     * <p>
     * Gets the <code>memberResource</code> field value.
     * </p>
     * @return The memberResource field value
     */
    public Resource getMemberResource() {
        return memberResource;
    }

    /**
     * <p>
     * Sets the <code>memberResource</code> field value.
     * </p>
     * @param memberResource
     *            The memberResource field value
     * @throws IllegalArgumentException
     *             If memberResource is null
     */
    public void setMemberResource(Resource memberResource) {
        Util.checkObjNotNull(memberResource, "memberResource");
        this.memberResource = memberResource;
    }

    /**
     * <p>
     * Gets the position field value.
     * </p>
     * @return The position field value
     */
    public TeamPosition getPosition() {
        return position;
    }

    /**
     * <p>
     * Sets the position field value.
     * </p>
     * @param position
     *            The position field value
     * @throws IllegalArgumentException
     *             If position is null
     */
    public void setPosition(TeamPosition position) {
        Util.checkObjNotNull(position, "position");
        this.position = position;
    }
}
