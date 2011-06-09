/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl.entity;

import java.io.Serializable;

/**
 * <p>
 * This class is simply the Java mapped class for table 'upload', so that this table can be used in HQL.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class Upload implements Serializable {
    /**
     * <p>
     * The upload id. It has both getter and setter. It can be any value. It does not need to be initialized
     * when the instance is created. It is used in getUploadId(), setUploadId().
     * </p>
     */
    private long uploadId;

    /**
     * <p>
     * The project id. It has both getter and setter. It can be any value. It does not need to be initialized
     * when the instance is created. It is used in setProjectId(), getProjectId().
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public Upload() {
        // Empty
    }

    /**
     * <p>
     * Getter method for uploadId, simply return the value of the namesake field.
     * </p>
     *
     * @return the uploadId
     */
    public long getUploadId() {
        return uploadId;
    }

    /**
     * <p>
     * Setter method for the uploadId, simply set the value to the namesake field.
     * </p>
     *
     * @param uploadId
     *            the uploadId to set
     */
    public void setUploadId(long uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * <p>
     * Getter method for projectId, simply return the value of the namesake field.
     * </p>
     *
     * @return the projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Setter method for the projectId, simply set the value to the namesake field.
     * </p>
     *
     * @param projectId
     *            the projectId to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
