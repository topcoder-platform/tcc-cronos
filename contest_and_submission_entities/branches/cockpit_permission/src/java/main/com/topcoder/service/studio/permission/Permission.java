/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.permission;

import com.topcoder.service.studio.contest.Helper;

import java.io.Serializable;


/**
 * Represents the entity class for permission.
 *
 * @author PE
 * @version 1.0
 *
 * @since Module Cockpit Contest Service Enhancement Assembly
 */
@SuppressWarnings("serial")
public class Permission implements Serializable {
    /** Represents the entity id. */
    private Long permissionId;

    /** Represents the user id. */
    private Long userId;
    
    /**
     * Represents the user handle corresponding to user id.
     * 
     * @since Cockpit Share Submission Integration Assembly v1.0
     */
    private String userHandle;

    /** Represents the project id. */
    private Long projectId;
    
    /**
     * Represents the name of the project corresponding to given project id
     * 
     * @since Cockpit Share Submission Integration Assembly v1.0
     */
    private String projectName;

    /** Represents the permission type. */
    private PermissionType permissionType;

    /**
     * Default constructor.
     */
    public Permission() {
        // empty
    }

    /**
     * Gets the permissionId.
     *
     * @return the permissionId.
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * Sets the permissionId.
     *
     * @param permissionId the permissionId to set.
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * Gets the userId.
     *
     * @return the userId.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the userId.
     *
     * @param userId the userId to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the projectId.
     *
     * @return the projectId.
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets the projectId.
     *
     * @param projectId the projectId to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the permissionType.
     *
     * @return the permissionType.
     */
    public PermissionType getPermissionType() {
        return permissionType;
    }

    /**
     * Sets the permissionType.
     *
     * @param permissionType the permissionType to set.
     */
    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
    
    /**
     * Gets the handle of the user for this permission.
     * 
     * @return the userHandle
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public String getUserHandle() {
        return this.userHandle;
    }

    /**
     * Sets the handle of the user for this permission.
     * 
     * @param userHandle the userHandle to set
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    /**
     * Gets the project name for this permission.
     * 
     * @return the projectName
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Sets the project name for this permission.
     * 
     * @param projectName the projectName to set
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj the {@code Object} to compare to this one
     *
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Permission) {
            if (getPermissionId() == null) {
                return (((Permission) obj).getPermissionId() == null);
            }

            return getPermissionId().equals(((Permission) obj).getPermissionId());
        }

        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}}
     * method.
     *
     * @return a hash code for this {@code Permission}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(permissionId);
    }
}
