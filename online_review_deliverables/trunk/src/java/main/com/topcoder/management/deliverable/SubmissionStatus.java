package com.topcoder.management.deliverable;


/**
 * <p>
 * The SubmissionStatus class is a support class in the modeling classes. It is used to tag a submission as having
 * a certain status. For development, this class will be very simple to implement, as has no fields of its own and
 * simply delegates to the constructors of the base class.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable.
 * </p>
 *
 *
 */
public class SubmissionStatus extends NamedDeliverableStructure {

    /**
     * SubmissionStatus constructor: Creates a new SubmissionStatus.
     *
     */
    public SubmissionStatus() {

    }

    /**
     * SubmissionStatus constructor: Creates a new SubmissionStatus, passing the parameters to the base class
     * constructor.
     *
     *
     * @param id The id of the submission status
     * @throws IllegalArgumentException If id is <= 0
     */
    public SubmissionStatus(long id) {
        super(id);
    }

    /**
     * SubmissionStatus constructor: Creates a new SubmissionStatus, passing the parameters to the base class
     * constructor.
     *
     *
     * @param id The id of the submission status
     * @param name The name of the submission status
     * @throws IllegalArgumentException If id is <= 0
     * @throws IllegalArgumentException If name is null
     */
    public SubmissionStatus(long id, String name) {
        super(id, name);
    }
}
