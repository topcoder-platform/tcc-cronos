/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external;

/**
 * <p>This interface describes a project within the User Project Data Store component.</p>
 * <p>The component id, version id, forum id and catalog id are all included, as well as textual
 * descriptions of the project and the component itself. The unique id (project id) is described by
 * the super interface.</p>
 * <p>Implementations of this interface are not required to be thread-safe.</p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public interface ExternalProject extends ExternalObject {

    /**
     * <p>Returns the id of the component that this project is for.</p>
     *
     * @return the id of the component that this project is for. May be -1 if no component is assigned.
     */
    public long getComponentId();

    /**
     * <p>Returns the version number of this project.</p>
     *
     * @return the version number of this project. Will always be positive.
     */
    public long getVersionId();

    /**
     * <p>Returns the id of the current forum for this project.</p>
     *
     * @return the id of the current forum for this project. May be -1 if no forum is configured.
     */
    public long getForumId();

    /**
     * <p>Returns the catalog id of the component that this project is for.</p>
     *
     * @return the catalog id of the component of this project. May be -1 if there is no component.
     */
    public long getCatalogId();

    /**
     * <p>Returns the name of the component of this project.</p>
     *
     * @return the name of the component of this project. May be null or empty after trim if there is
     * no component.
     */
    public String getName();

    /**
     * <p>Returns a String representation of the version of the component that this project is for.</p>
     *
     * @return the version of this project. Will never be null but may be empty after trim.
     */
    public String getVersion();

    /**
     * <p>Returns the description of the component that this project is for.</p>
     *
     * @return the description of the component of this project. May be null or empty after trim.
     */
    public String getDescription();

    /**
     * <p>Returns the comments of this project.</p>
     *
     * @return the comments of this project. May be null or empty after trim.
     */
    public String getComments();
}


