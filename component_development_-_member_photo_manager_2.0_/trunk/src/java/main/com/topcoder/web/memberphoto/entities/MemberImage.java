/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

/**
 * <p>
 * This class represents the MemberImage java bean. A MemberImage can contain a member identifier and an image. This
 * is a simple java bean (with a default no-arg constructor and for each property, a corresponding getter/setter
 * method). Any attribute in this bean is <i>optional</i> so no validation is performed here.
 * </p>
 *
 * <p>
 * This class is <code>Serializable</code>. The IoC should handle the validity if the passed values in setters.
 * </p>
 * <p>
 * Annotation : <code>Entity</code> - this annotation should be used to mark this java bean as an entity and to ease
 * its use with JPA.
 * </p>
 *
 * <p>
 * <em>Changes in 2.0:</em>
 * <ol>
 * <li>Added id, removed and auditing properties.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Thread safety: This class contains only mutable fields so therefore it is not thread safe.
 * </p>
 *
 * @author Mafy, cyberjag, saarixx, sparemax
 * @version 2.0
 */
@Entity
public class MemberImage implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -2348247796104048067L;

    /**
     * <p>
     * The ID of the MemberImage entity.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     *
     * @since 2.0
     */
    private long id;

    /**
     * <p>
     * This field represents the 'memberId' property of the MemberImage. Represents the identifier of the member.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not assigned. It is accessed through
     * corresponding getter/setter methods. There are no restrictions at this moment for valid values. It can take
     * any value.
     * </p>
     */
    private long memberId;

    /**
     * <p>
     * This field represents the 'image' property of the MemberImage. Represents the image of the member.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not assigned. It is accessed through
     * corresponding getter/setter methods. There are no restrictions at this moment for valid values. It can take
     * any value.
     * </p>
     */
    private Image image;

    /**<p>
     * The value indicating whether the member image is removed.
     * </p>
     *
     * <p>
     * Has getter and setter.
     * </p>
     *
     * @since 2.0
     */
    private boolean removed;

    /**<p>
     * The username of the user who created this entity.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     *
     * @since 2.0
     */
    private String createdBy;

    /**<p>
     * The creation date/time of the entity.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     *
     * @since 2.0
     */
    private Date createdDate;

    /**<p>
     * The username of the user who last updated this entity.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     *
     * @since 2.0
     */
    private String updatedBy;

    /**<p>
     * The last updating date/time of the entity.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     *
     * @since 2.0
     */
    private Date updatedDate;

    /**
     * Default no-arg constructor. Constructs a new <code>{@link MemberImage}</code> instance.
     */
    public MemberImage() {
    }

    /**
     * <p>
     * Retrieves the ID of the MemberImage entity.
     * </p>
     *
     * @return the ID of the MemberImage entity.
     *
     * @since 2.0
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the ID of the MemberImage entity.
     * </p>
     *
     * @param id
     *            the ID of the MemberImage entity.
     *
     * @since 2.0
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for 'memberId' property. Please refer to the related 'memberId' field for more information.
     *
     * @return the value of the 'memberId' property. It can be any value.
     */
    public long getMemberId() {
        return memberId;
    }

    /**
     * Setter for 'memberId' property. Please refer to the related 'memberId' field for more information.
     *
     * @param memberId
     *            the new memberId to be used for 'memberId' property. It can be any value.
     */
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    /**
     * Getter for 'image' property. Please refer to the related 'image' field for more information.
     *
     * @return the value of the 'image' property. It can be any value.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter for 'image' property. Please refer to the related 'image' field for more information.
     *
     * @param image
     *            the new image to be used for 'image' property. It can be any value.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * <p>
     * Retrieves the value indicating whether the member image is removed.
     * </p>
     *
     * @return the value indicating whether the member image is removed.
     *
     * @since 2.0
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * <p>
     * Sets the value indicating whether the member image is removed.
     * </p>
     *
     * @param removed
     *            the value indicating whether the member image is removed.
     *
     * @since 2.0
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    /**
     * <p>
     * Retrieves the username of the user who created this entity.
     * </p>
     *
     * @return the username of the user who created this entity.
     *
     * @since 2.0
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * <p>
     * Sets the username of the user who created this entity.
     * </p>
     *
     * @param createdBy
     *            the username of the user who created this entity.
     *
     * @since 2.0
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * <p>
     * Retrieves the creation date/time of the entity.
     * </p>
     *
     * @return the creation date/time of the entity.
     *
     * @since 2.0
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * <p>
     * Sets the creation date/time of the entity.
     * </p>
     *
     * @param createdDate
     *            the creation date/time of the entity.
     *
     * @since 2.0
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * <p>
     * Retrieves the username of the user who last updated this entity.
     * </p>
     *
     * @return the username of the user who last updated this entity.
     *
     * @since 2.0
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * <p>
     * Sets the username of the user who last updated this entity.
     * </p>
     *
     * @param updatedBy
     *            the username of the user who last updated this entity.
     *
     * @since 2.0
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * <p>
     * Retrieves the last updating date/time of the entity.
     * </p>
     *
     * @return the last updating date/time of the entity.
     *
     * @since 2.0
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * <p>
     * Sets the last updating date/time of the entity.
     * </p>
     *
     * @param updatedDate
     *            the last updating date/time of the entity.
     *
     * @since 2.0
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
