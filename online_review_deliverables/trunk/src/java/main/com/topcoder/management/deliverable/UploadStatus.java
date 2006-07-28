package com.topcoder.management.deliverable;


/**
 * <p>
 * The UploadStatus class is a support class in the modeling classes. It is used to tag an upload as having a
 * certain status. For development, this class will be very simple to implement, as has no fields of its own and
 * simply delegates to the constructors of the base class.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable.
 * </p>
 *
 *
 */
public class UploadStatus extends NamedDeliverableStructure {

    /**
     * UploadStatus constructor: Creates a new UploadStatus.
     *
     */
    public UploadStatus() {

    }

    /**
     * UploadStatus constructor: Creates a new UploadStatus, passing the parameters to the base class constructor.
     *
     *
     * @param id The id of the upload status
     * @throws IllegalArgumentException If id is <= 0
     */
    public UploadStatus(long id) {
        super(id);
    }

    /**
     * UploadStatus constructor: Creates a new UploadStatus, passing the parameters to the base class constructor.
     *
     *
     * @param id The id of the upload status
     * @param name The name of the upload status
     * @throws IllegalArgumentException If id is <= 0
     * @throws IllegalArgumentException If name is null
     */
    public UploadStatus(long id, String name) {
        super(id, name);
    }
}
