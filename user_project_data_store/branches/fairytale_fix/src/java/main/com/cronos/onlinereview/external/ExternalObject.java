/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external;

import java.io.Serializable;

/**
 * <p>This simple interface merely captures common information (the id) for the other two entities
 * in the User Project Data Store component, namely ExternalUser and ExternalProject.</p>
 * <p>It also implements Serializable so that when subclasses are used in a web application, they
 * can be serialized for session replication.</p>
 * <p>Implementations of this interface would just be thread-safe.</p>
 *
 * @author dplass, oodinary
 * @version 1.0
 */
public interface ExternalObject extends Serializable {

    /**
     * <p>Returns the unique id of this object. Note that uniqueness only applies across objects of
     * the same base type. In other words, the same id may be re-used for a user as for a project,
     * but since they are stored separately, it is not an issue.</p>
     *
     * @return the unique id of this object. Will never be negative.
     */
    public long getId();
}


