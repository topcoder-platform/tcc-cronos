/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The Upload class is the one of the main modeling classes of this component.
 * It represents an uploaded document.
 * </p>
 * <p>
 * The Upload class is simply a container for a few basic data fields. All data
 * fields in this class are mutable and have get and set methods.
 * </p>
 * <p>
 * Thread Safety: This class is highly mutable and NOT thread safe. All fields
 * can be changed.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class Upload extends AuditedDeliverableStructure {

    /**
     * UNSET_OWNER: The value that the owner field will have (and that the
     * getOwner method will return) when the owner field has not been through
     * the setOwner method. This field is public, static, and final.
     */
    public static final long UNSET_OWNER = -1;

    /**
     * UNSET_PROJECT: The value that the project field will have (and that the
     * getProject method will return) when the project field has not been
     * through the setProject method. This field is public, static, and final.
     */
    public static final long UNSET_PROJECT = -1;

    /**
     * uploadType: The type of the upload. This field can be null or non-null
     * and is mutable. The default value is null, which indicates that this
     * field has not been set. This field can be set through the setUploadType
     * method and retrieved through the getUploadType method.
     */
    private UploadType uploadType = null;

    /**
     * uploadStatus: The status of the upload. This field can be null or
     * non-null and is mutable. The default value is null, which indicates that
     * this field has not been set. This field can be set through the
     * setUploadStatus method and retrieved through the getUploadStatus method.
     */
    private UploadStatus uploadStatus = null;

    /**
     * owner: The owner of the upload, i.e. the person responsible for the
     * upload. This field can be > 0 or UNSET_OWNER and is mutable. The default
     * value is UNSET_OWNER, which indicates that this field has not been set or
     * that no owner is associated with the upload. This field can be set
     * through the setOwner method and retrieved through the getOwner method.
     */
    private long owner = UNSET_OWNER;

    /**
     * project: The project that the upload is associated with. This field can
     * be >0 or UNSET_PROJECT and is mutable. The default value is
     * UNSET_PROJECT, which indicates that this field has not been set. This
     * field can be set through the setProject method and retrieved through the
     * getProject method.
     */
    private long project = UNSET_PROJECT;

    /**
     * parameter: The parameter that identifies the uploaded file (and
     * implicitly contains information about how to get the uploaded file). This
     * field can be null or non-null and is mutable. The default value is null,
     * which indicates that this field has not been set. This field can be set
     * through the setParameter method and retrieved through the getParameter
     * method.
     */
    private String parameter = null;

    /**
     * Creates a new Upload.
     */
    public Upload() {
    }

    /**
     * Creates a new Upload, passing the argument to the base class constructor.
     * @param id
     *            The id of the Upload
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public Upload(long id) {
        super(id);
    }

    /**
     * Sets the upload type of the upload. The value can be either null or
     * non-null.
     * @param uploadType
     *            The type of the upload.
     */
    public void setUploadType(UploadType uploadType) {
        this.uploadType = uploadType;
    }

    /**
     * Gets the upload type of the upload. The return value may be null or
     * non-null.
     * @return The type of the upload
     */
    public UploadType getUploadType() {
        return uploadType;
    }

    /**
     * Sets the status of the upload. The value can be null or non-null.
     * @param uploadStatus
     *            The status of the upload.
     */
    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    /**
     * Gets the status of the upload. The return value may be null or non-null.
     * @return The status of the upload.
     */
    public UploadStatus getUploadStatus() {
        return uploadStatus;
    }

    /**
     * Sets the owner of the upload. This method does not allow the owner (once
     * set) to be unset, but it does allow the owner to be changed.
     * @param owner
     *            The owner of the upload
     * @throws IllegalArgumentException
     *             If owner is <= 0
     */
    public void setOwner(long owner) {
        Helper.assertLongPositive(owner, "owner");
        this.owner = owner;
    }

    /**
     * Gets the owner of the upload. This method will return UNSET_OWNER or a
     * value > 0.
     * @return The owner of the upload.
     */
    public long getOwner() {
        return owner;
    }

    /**
     * Sets the project the upload is associated with. This method does not
     * allow the project (once set) to be unset, but it does allow the project
     * to be changed.
     * @param project
     *            The project the upload is associated with
     * @throws IllegalArgumentException
     *             If project is <= 0
     */
    public void setProject(long project) {
        Helper.assertLongPositive(project, "project");
        this.project = project;
    }

    /**
     * getProject: Gets the project that the upload is associated with. This
     * method will return UNSET_PROJECT or a value > 0.
     * @return The project the upload is associated with.
     */
    public long getProject() {
        return project;
    }

    /**
     * Sets the parameter that identifies the uploaded file (tells where to find
     * it). The value may be null or non-null, and when non-null, any value is
     * allowed.
     * @param parameter
     *            The identifier for the uploaded file.
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * Gets the parameter that identifies the uploaded file (tells where it is).
     * The return value may be null or non-null.
     * @return The identifier for the uploaded file
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * <p>
     * Tells whether all the required fields of this Submission have values set.
     * </p>
     * <p>
     * This method returns true if all of the following are true:
     * base.isValidToPersist returns true, uploadType is not null,
     * uploadType.isValidToPersist returns true, uploadStatus is not null,
     * uplodStatus.isValidToPersist returns true, owner is not UNSET_OWNER,
     * project is not UNSET_PROJECT parameter is not null.
     * </p>
     * @return True if all fields required for persistence are present
     */
    public boolean isValidToPersist() {
        if (super.isValidToPersist() && uploadType != null && uploadType.isValidToPersist()
            && uploadStatus != null && uploadStatus.isValidToPersist() && owner != UNSET_OWNER
            && project != UNSET_PROJECT && parameter != null) {
            return true;
        } else {
            return false;
        }
    }
}
