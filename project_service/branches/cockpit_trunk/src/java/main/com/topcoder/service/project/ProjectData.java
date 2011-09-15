/*
 * Copyright (C) 2008 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 * <p>
 * This data object serves to communicate between project data between the bean and its client. It also serves as a base
 * class for the Project class.
 * </p>
 * <p>
 * We simply define three properties and getter/setters for these properties. Note that this is a dumb DTO - no
 * validation is done.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit Project Status Management changes:
 *     <li>Add property <code>projectStatusId</code> and sql result set mapping for it.</li>
 * </p>
 * <p>
 *     Version 1.2 - add the property project forum category id.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe as it has mutable state.
 * </p>
 *
 * @author humblefool, FireIce, GreatKevin
 * @version 1.2
 */
@SqlResultSetMapping(
		name="GetProjectsResult",
		entities={@EntityResult(entityClass=ProjectData.class,
				fields={@FieldResult(name="projectId",      column="project_id"),
			            @FieldResult(name="name", column="name"),
			            @FieldResult(name="description", column="description"),
                        @FieldResult(name="projectStatusId", column="project_status_id"),
                        @FieldResult(name="forumCategoryId", column="project_forum_id")}
		)})
@Entity
public class ProjectData implements Serializable {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 9053041638081689882L;

    /**
     * <p>
     * Represents the ID of this project.
     * </p>
     * <p>
     * It uses <code>Long</code> type instead of <code>long</code> type to allow for null values to be set before
     * entity creation in persistence. This variable is mutable and is retrieved by the {@link #getProjectId()} method
     * and set by the {@link #setProjectId(Long)} method. It is initialized to null, and may be set to ANY value.
     * </p>
     */
	@Id
    private Long projectId;

    /**
     * <p>
     * Represents the name of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getName()} method and set by the
     * {@link #setName(String)} method. It is initialized to null, and may be set to ANY value, including null/empty
     * string.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the description of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the getDescription method and set by the setDescription method. It
     * is initialized to null, and may be set to ANY value, including null/empty string.
     * </p>
     */
    private String description;

     /**
     * <p>
     * Represents project status id
     * </p>
     * <p>
     * It uses <code>Long</code> type instead of <code>long</code> type to allow for null values to be set before
     * entity creation in persistence.
     * </p>
      *@since 1.1
     */
    private Long projectStatusId;


    /**
     * The project forum category id.
     * @since 1.2
     */
    private String forumCategoryId;

    /**
     * <p>
     * Gets the project status id
     * </p>
     *
     * @return The status id of the project.
     * @since 1.1
     */
    public Long getProjectStatusId() {
        return projectStatusId;
    }

    /**
     * <p>
     * Sets the project status id
     * </p>
     *
     * @param projectStatusId The project status ID of this project.
     * @since 1.1
     */
    public void setProjectStatusId(Long projectStatusId) {
        this.projectStatusId = projectStatusId;
    }
  

    /**
     * <p>
     * Constructs a <code>ProjectData</code> instance.
     * </p>
     */
    public ProjectData() {
    }

    /**
     * <p>
     * Gets the ID of the project.
     * </p>
     *
     * @return The ID of the project.
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the ID of this project.
     * </p>
     *
     * @param projectId
     *            The desired ID of this project. ANY value.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the name of the project.
     * </p>
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of this project.
     * </p>
     *
     * @param name
     *            The desired name of this project. ANY value.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the description of the project.
     * </p>
     *
     * @return The description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of this project.
     * </p>
     *
     * @param description
     *            The desired description of this project. ANY value.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the project forum category id.
     *
     * @return the project forum category id.
     * @since 1.2
     */
    public String getForumCategoryId() {
        return forumCategoryId;
    }

    /**
     * Sets the project forum category id
     *
     * @param forumCategoryId the project forum category id
     * @since 1.2
     */
    public void setForumCategoryId(String forumCategoryId) {
        this.forumCategoryId = forumCategoryId;
    }
}
