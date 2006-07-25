/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The UploadStatus class is a support class in the modeling classes. It is used
 * to tag an upload as having a certain status.
 * </p>
 * <p>
 * Thread Safety: This class is mutable because its base class is mutable and
 * NOT thread safe.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class UploadStatus extends NamedDeliverableStructure {

    /**
     * Creates a new UploadStatus.
     */
    public UploadStatus() {
    }

    /**
     * Creates a new UploadStatus, passing the parameters to the base class
     * constructor.
     * @param id
     *            The id of the upload status
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public UploadStatus(long id) {
        super(id);
    }

    /**
     * Creates a new UploadStatus, passing the parameters to the base class
     * constructor.
     * @param id
     *            The id of the upload status
     * @param name
     *            The name of the upload status
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null
     */
    public UploadStatus(long id, String name) {
        super(id, name);
    }
}
