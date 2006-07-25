/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The UploadType class is a support class in the modeling classes. It is used
 * to tag an upload as being of a certain type.
 * </p>
 * <p>
 * Thread Safety: This class is mutable because its base class is mutable and
 * NOT thread safe.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class UploadType extends NamedDeliverableStructure {

    /**
     * Creates a new UploadType.
     */
    public UploadType() {
    }

    /**
     * Creates a new UploadType, passing the parameters to the base class
     * constructor.
     * @param id
     *            The id of the upload type
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public UploadType(long id) {
        super(id);
    }

    /**
     * Creates a new UploadType, passing the parameters to the base class
     * constructor.
     * @param id
     *            The id of the upload type
     * @param name
     *            The name of the upload type
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null
     */
    public UploadType(long id, String name) {
        super(id, name);
    }
}
