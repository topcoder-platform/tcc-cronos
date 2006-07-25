/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.Date;

/**
 * <p>
 * The Deliverable class is the one of the main modeling classes of this
 * component. It represents an item that must be delivered for the project. The
 * Deliverable class is simply a container for a few basic data fields, but
 * unlike the Upload and Submission class, a deliverable is largely immutable.
 * The data fields (except for completion date) have only get methods.
 * </p>
 * <p>
 * This class should be very easy to implement, as all the methods just get or
 * set the underlying fields.
 * </p>
 * <p>
 * This class is highly mutable. All fields can be changed.
 * </p>
 * <ID="UML note. note-id=[Im1b757125m10bd7582c99mm41ab] ">Also uses all the
 * other modeling classes, Upload, Submission, etc. Unfortunately, Poseidon has
 * to think for about 5 minutes (quite literally) to add each one to this
 * diagram. <BR>
 * </ID>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class Deliverable extends NamedDeliverableStructure {

    /**
     * project: The identifier of the project the Deliverable is associated
     * with. This field is set in the constructor and is immutable. This field
     * must always be > 0. The value of this field can be retrieved through the
     * getProject method.
     */
    private final long project;

    /**
     * phase: The identifier of the phase the Deliverable is associated with.
     * This field is set in the constructor and is immutable. This field must
     * always be > 0. The value of this field can be retrieved through the
     * getResource method.
     */
    private final long phase;

    /**
     * phase: The identifier of the resource the Deliverable is associated with.
     * This field is set in the constructor and is immutable. This field must
     * always be > 0. The value of this field can be retrieved through the
     * getPhase method.
     */
    private final long resource;

    /**
     * submission: The identifier of the submission that is associated with the
     * deliverable. This field is set in the constructor and is immutable. This
     * field may be null or non-null. If non-null, it must be > 0. The value of
     * this field can be retrieved through the getSubmission method. A
     * deliverable can be associated with a submission only if it is a "per
     * submission" deliverable.
     */
    private final Long submission;

    /**
     * required: Tells whether the deliverable is required to be completed for
     * the project phase to end. This field is set in the constructor and is
     * immutable. The value of this field can be retrieved through the
     * getRequired method.
     */
    private final boolean required;

    /**
     * completionDate: The date on which the deliverable was completed. If the
     * deliverable has not been completed, this field will be null. This field
     * is set in the constructor, can be null or non-null, and is mutable. The
     * value can be set through the setCompletionDate method and retrieved
     * through the getCompletionDate method. It is also used in the isCompleted
     * method.
     */
    private Date completionDate = null;

    /**
     * Creates a new Deliverable. Sets all fields to the given values.
     * @param project
     *            The project that the deliverable is associated with
     * @param phase
     *            The phase that the deliverable is associated with
     * @param resource
     *            The resource that the deliverable is associated with
     * @param submission
     *            The submission that the deliverable is associated with, can be
     *            null
     * @param required
     *            True if the deliverable is required for the project phase to
     *            end
     * @throws IllegalArgumentException
     *             If project, phase, resource is <= 0
     * @throws IllegalArgumentException
     *             If submission is not null and but its value is <= 0
     */
    public Deliverable(long project, long phase, long resource, Long submission, boolean required) {
        super();

        Helper.assertLongPositive(project, "project");
        Helper.assertLongPositive(phase, "phase");
        Helper.assertLongPositive(resource, "resource");
        if (submission != null) {
            Helper.assertLongPositive(submission.longValue(), "submission value");
        }

        this.project = project;
        this.phase = phase;
        this.resource = resource;
        this.submission = submission;
        this.required = required;
    }

    /**
     * Gets the project that the deliverable is associated with. This method
     * will return a value > 0.
     * @return The project the deliverable is associated with
     */
    public long getProject() {
        return project;
    }

    /**
     * Gets the phase that the deliverable is associated with. This method will
     * return a value > 0.
     * @return The phase the deliverable is associated with
     */
    public long getPhase() {
        return phase;
    }

    /**
     * Gets the identifier of the resource that the deliverable is related to.
     * This method will return a value > 0.
     * @return The identifier of the resource the deliverable is related to
     */
    public long getResource() {
        return resource;
    }

    /**
     * Gets the submission that is associated with this deliverable. If no
     * submission is associated with the deliverable, this method will return
     * null, otherwise the return value will be > 0.
     * @return The submission the deliverable is associated with
     */
    public Long getSubmission() {
        return submission;
    }

    /**
     * Tells whether the deliverable is required for the project phase to end.
     * @return True if deliverable is required, false if not.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Marks the deliverable as being completed on the given date.
     * @param completionDate
     *            The date the deliverable was completed.
     * @throws IllegalArgumentException
     *             If completionDate is null.
     */
    public void setCompletionDate(Date completionDate) {
        Helper.assertObjectNotNull(completionDate, "completionDate");
        this.completionDate = completionDate;
    }

    /**
     * Gets the completion date of the deliverable. If the deliverable is not
     * complete, the return will be null.
     * @return The completion date of the deliverable.
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Tells whether the deliverable is complete. A deliverable is complete if
     * its completion date has been set (completionDate is not null).
     * @return True if the deliverable is complete, false if not complete.
     */
    public boolean isComplete() {
        return completionDate != null;
    }

    /**
     * Tells whether the deliverable is required for each submission. A
     * deliverable is "per submission" if and only if the submission field is
     * non-null.
     * @return True if the deliverable is required per submission.
     */
    public boolean isPerSubmission() {
        return submission != null;
    }
}
