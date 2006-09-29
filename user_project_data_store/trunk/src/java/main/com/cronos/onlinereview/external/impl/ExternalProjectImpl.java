/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external.impl;

import com.cronos.onlinereview.external.ExternalProject;
import com.cronos.onlinereview.external.UserProjectDataStoreHelper;

/**
 * <p>Basic implementation of the ExternalProject interface.</p>
 * <p>If any field is already set and the user attempts to set them again, an exception will be thrown.
 * The unique id (project id) is described by the super class.</p>
 * <p>This class is not thread-safe.</p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public class ExternalProjectImpl extends ExternalObjectImpl implements ExternalProject {

    /**
     * <p>The version number of project as set in the constructor and accessed by getVersionId.</p>
     * <p>Will always be positive.</p>
     */
    private final long versionId;

    /**
     * <p>The text of the version of project as set in the constructor and accessed by getVersion.</p>
     * <p>Will never be null, but may be empty after trim.</p>
     */
    private final String version;

    /**
     * <p>The id of the component that project is for as set in setComponentId and accessed by
     * getComponentId.</p>
     * <p>May be -1 if no component is assigned.</p>
     */
    private long componentId = -1;

    /**
     * <p>The id of the forum for project as set in setForumId and accessed by getForumId.</p>
     * <p>May be -1 if no forum is configured.</p>
     */
    private long forumId = -1;

    /**
     * <p>The id of the catalog of the component of project as set in setCatalogId and accessed by
     * getCatalogId.</p>
     * <p>May be -1 if there is no component.</p>
     */
    private long catalogId = -1;

    /**
     * <p>The name of component of this project as set by setName and accessed by getName.</p>
     * <p>May be null if there is no component, and may be empty even after trim if set.</p>
     */
    private String name = null;

    /**
     * <p>The description of the component of project as set setDescription and accessed by
     * getDescription.</p>
     * <p>May be null.</p>
     */
    private String description = null;

    /**
     * <p>The comments associated with this project as set by setComments and accessed by getComments.</p>
     * <p>May be null.</p>
     */
    private String comments = null;

    /**
     * <p>Constructs this object with the given parameters by copying to the appropriate fields.</p>
     *
     * @param id the identifier of this project.
     * @param versionId the version id of this project.
     * @param version the version text of this project. This value may be empty, even after trim,
     * but may never be null.
     *
     * @throws IllegalArgumentException if id or versionId is negative, or if version is null.
     */
    public ExternalProjectImpl(long id, long versionId, String version) {
        super(id);

        UserProjectDataStoreHelper.validateNegative(versionId, "versionId");
        UserProjectDataStoreHelper.validateNull(version, "version");

        this.versionId = versionId;
        this.version = version;
    }

    /**
     * <p>Returns the catalog id of the component of this project from the 'catalogId' field, as set
     * in the setCatalogId method.</p>
     *
     * @return the catalog id of the component of this project. May be -1 if there is no component.
     */
    public long getCatalogId() {
        return this.catalogId;
    }

    /**
     * <p>Returns the comments of this project from the 'comments' field, as set in the setComments
     * method.</p>
     *
     * @return the comments of this project. May be null.
     */
    public String getComments() {
        return this.comments;
    }

    /**
     * <p>Returns the id of the component of this project from the 'componentId' field, as set in the
     * setComponentId method.</p>
     *
     * @return the id of the component of this project. May be -1 if there is no component.
     */
    public long getComponentId() {
        return this.componentId;
    }

    /**
     * <p>Returns the description of the component of this project from the 'description' field, as set
     * in the setDescription method.<p>
     *
     * @return Returns the description of the component of this project. May be null if there is no
     * component or no description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>Returns the id of the forum of this project from the 'forumId' field, as set in the
     * setForumtId method.</p>
     *
     * @return the id of the forum of this project. May be -1 if there is no forum.
     */
    public long getForumId() {
        return this.forumId;
    }

    /**
     * <p>Returns the name of the component of this project from the 'name' field, as set in the
     * setName method.</p>
     *
     * @return Returns the name of the component of this project. May be null if there is no
     * component or no name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>Returns the version text of this project from the 'version' field, as set in the constructor.</p>
     *
     * @return Returns the version text of this project. Will never be null, but may be empty.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * <p>Returns the version number of this project from the 'versionId' field, as set in the
     * constructor.</p>
     *
     * @return Returns the version id of this project. Will never be negative.
     */
    public long getVersionId() {
        return this.versionId;
    }

    /**
     * <p>Sets the catalog id of the component of this project by copying to the 'catalogId' field.</p>
     *
     * @param catalogId The id of the catalog of the component.
     *
     * @throws IllegalArgumentException if the catalogId parameter is negative or the catalog id is
     * already set.
     */
    public void setCatalogId(long catalogId) {
        UserProjectDataStoreHelper.validateNegative(catalogId, "catalogId");
        UserProjectDataStoreHelper.validateFieldAlreadySet(this.catalogId, "catalogId");

        this.catalogId = catalogId;
    }

    /**
     * <p>Sets the comments associated with this project by copying to the 'comments' field.</p>
     *
     * @param comments The comments of this project. Note that this may be null or empty, even after trim.
     *
     * @throws IllegalArgumentException if the comments parameter is null or the comments is already set.
     */
    public void setComments(String comments) {
        UserProjectDataStoreHelper.validateNull(comments, "comments");
        UserProjectDataStoreHelper.validateFieldAlreadySet(this.comments, "comments");

        this.comments = comments;
    }

    /**
     * <p>Sets the id of the component of this project by copying to the 'componentId' field.</p>
     *
     * @param componentId The id of the component of this project.
     *
     * @throws IllegalArgumentException if the componentId parameter is negative or the component id is
     * already set.
     */
    public void setComponentId(long componentId) {
        UserProjectDataStoreHelper.validateNegative(componentId, "componentId");
        UserProjectDataStoreHelper.validateFieldAlreadySet(this.componentId, "componentId");

        this.componentId = componentId;
    }

    /**
     * <p>Set the description of the component of this project by copying to the 'description' field.</p>
     *
     * @param description The description of the component of this project.
     *
     * @throws IllegalArgumentException if the description parameter is null or the description is already
     * set.
     */
    public void setDescription(String description) {
        UserProjectDataStoreHelper.validateNull(description, "description");
        UserProjectDataStoreHelper.validateFieldAlreadySet(this.description, "description");

        this.description = description;
    }

    /**
     * <p>Sets the id of the forum of this project by copying to the 'forumId' field.</p>
     *
     * @param forumId The id of the forum of the component.
     *
     * @throws IllegalArgumentException if the forumId parameter is negative or the forum id is already set.
     */
    public void setForumId(long forumId) {
        UserProjectDataStoreHelper.validateNegative(forumId, "forumId");
        UserProjectDataStoreHelper.validateFieldAlreadySet(this.forumId, "forumId");

        this.forumId = forumId;
    }

    /**
     * <p>Set the name of the component of this project by copying to the 'name' field.</p>
     *
     * @param name The name of the component of this project.
     *
     * @throws IllegalArgumentException if the name parameter is null or the name is already set.
     */
    public void setName(String name) {
        UserProjectDataStoreHelper.validateNull(name, "name");
        UserProjectDataStoreHelper.validateFieldAlreadySet(this.name, "name");

        this.name = name;
    }
}
