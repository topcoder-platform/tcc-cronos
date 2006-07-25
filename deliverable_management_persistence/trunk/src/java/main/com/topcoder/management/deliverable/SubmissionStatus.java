/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The SubmissionStatus class is a support class in the modeling classes. It is
 * used to tag a submission as having a certain status.
 * </p>
 * <p>
 * Thread Safety: This class is mutable because its base class is mutable and
 * NOT thread safe.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class SubmissionStatus extends NamedDeliverableStructure {

    /**
     * Creates a new SubmissionStatus.
     */
    public SubmissionStatus() {

    }

    /**
     * Creates a new SubmissionStatus, passing the parameters to the base class
     * constructor.
     * @param id
     *            The id of the submission status
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public SubmissionStatus(long id) {
        super(id);
    }

    /**
     * Creates a new SubmissionStatus, passing the parameters to the base class
     * constructor.
     * @param id
     *            The id of the submission status
     * @param name
     *            The name of the submission status
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null
     */
    public SubmissionStatus(long id, String name) {
        super(id, name);
    }
}
