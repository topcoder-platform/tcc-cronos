package com.topcoder.management.deliverable;


/**
 * <p>
 * The Submission class is the one of the main modeling classes of this component. It represents a submission,
 * which consists of an upload and a status. The Submission class is simply a container for a few basic data
 * fields. All data fields in this class are mutable and have get and set methods.
 * </p>
 *
 * <p>
 * This class should be very easy to implement, as all the methods just set the underlying fields.
 * </p>
 *
 * <p>
 * This class is highly mutable. All fields can be changed.
 * </p>
 *
 *
 */
public class Submission extends AuditedDeliverableStructure {

    /**
     * upload: The upload that is associated with the submission. This field can be null or non-null and is
     * mutable. The default value is null, which indicates that this field has not been set. This field can be set
     * through the setUpload method and retrieved through the getUpload method.
     *
     */
    private Upload upload = null;

    /**
     * submissionStatus: The status of the submission. This field can be null or non-null and is mutable. The
     * default value is null, which indicates that this field has not been set. This field can be set through the
     * setSubmissionStatus method and retrieved through the getSubmissionStatus method.
     *
     */
    private SubmissionStatus submissionStatus = null;

    /**
     * Submission constructor: Creates a new Submission.
     *
     */
    public Submission() {

    }

    /**
     * Submission constructor: Creates a new Submission, simply passing the argument to the base class constructor.
     *
     *
     * @param id The id of the submission
     * @throws IllegalArgumentException if id is <= 0
     */
    public Submission(long id) {
        super(id);
    }

    /**
     * setUpload: Sets the upload associated with the Submission. The parameter may be null or non-null.
     *
     *
     * @param upload The upload associated with the Submission.
     */
    public void setUpload(Upload upload) {
        this.upload = upload;
    }

    /**
     * getUpload: Gets the upload associated with the Submission. May return null or non-null.
     *
     *
     * @return The upload associated with the Submission.
     */
    public Upload getUpload() {
        return upload;
    }

    /**
     * setSubmissionStatus: Sets the submission status of the Submission. The parameter may be null or non-null.
     *
     *
     * @param submissionStatus The status of the submission.
     */
    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    /**
     * getSubmissionStatus: Gets the submission status of the Submission. May return null or non-null.
     *
     *
     * @return The status of the submission.
     */
    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    /**
     * isValidToPersist: Tells whether all the required fields of this Submission have values set. This method
     * returns true if all of the following are true id is not UNSET_ID upload is not null submissionStatus is not
     * null base.isValidTopPersist
     *
     *
     * @return True if all fields required for persistence are present
     */
    public boolean isValidToPersist() {

        return (getId() != UNSET_ID) && (upload != null) && (submissionStatus != null) && super.isValidToPersist();
    }
}
