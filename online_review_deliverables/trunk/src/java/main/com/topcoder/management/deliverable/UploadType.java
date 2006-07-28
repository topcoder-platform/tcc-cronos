package com.topcoder.management.deliverable;


/**
 * <p>
 * The UploadType class is a support class in the modeling classes. It is used to tag an upload as being of a
 * certain type. For development, this class will be very simple to implement, as has no fields of its own and
 * simply delegates to the constructors of the base class.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable.
 * </p>
 *
 *
 */
public class UploadType extends NamedDeliverableStructure {

    /**
     * UploadType constructor: Creates a new UploadType.
     *
     */
    public UploadType() {

    }

    /**
     * UploadType constructor: Creates a new UploadType, passing the parameters to the base class constructor.
     *
     *
     * @param id The id of the upload type
     * @throws IllegalArgumentException If id is <= 0
     */
    public UploadType(long id) {
        super(id);
    }

    /**
     * UploadType constructor: Creates a new UploadType, passing the parameters to the base class constructor.
     *
     *
     * @param id The id of the upload type
     * @param name The name of the upload type
     * @throws IllegalArgumentException If id is <= 0
     * @throws IllegalArgumentException If name is null
     */
    public UploadType(long id, String name) {
        super(id, name);

    }
}
