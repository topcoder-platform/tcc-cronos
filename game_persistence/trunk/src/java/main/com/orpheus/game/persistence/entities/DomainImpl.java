/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.ImageInfo;


/**
 * <p>
 * Simple implementation of the Domain. Represents a hosting domain within the application.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DomainImpl implements Domain {
    /**
     * <p>
     * Represents the unique ID for this domain. Set in the constructor, can be null, and will not change. If not
     * null, must be positive.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * Represents the user ID number of the sponsor to whom this domain is assigned. Set in the constructor, can be
     * any positive number, and will not change.
     * </p>
     */
    private final long sponsorId;

    /**
     * <p>
     * Represents the name of this domain -- i.e. the DNS name of the host -- as a String. Set in the constructor,
     * cannot be null/empty, and will not change.
     * </p>
     */
    private final String domainName;

    /**
     * <p>
     * Represents the value of this domain's approval flag, or null if no approval decision has been made. Set in the
     * constructor and will not change.
     * </p>
     */
    private final Boolean approved;

    /**
     * <p>
     * Represents ImageInfo objects representing all the images associated with this domain. Set in the constructor,
     * cannot be null or have null elements, and will not change.
     * </p>
     */
    private final ImageInfo[] images;

    /**
     * Constructor. It will make a shallow copy of images array.
     *
     * @param id the id
     * @param sponsorId the sponsor id
     * @param domainName the domain name
     * @param approved true if approved, false otherwise, or null of not decided yet.
     * @param images the array of ImageInfo objects
     *
     * @throws IllegalArgumentException If id, if given, is not positive, or if sponsorId is not positive, or if
     *         domain name is null/empty, or images is null or contains null elements
     */
    public DomainImpl(Long id, long sponsorId, String domainName, Boolean approved, ImageInfo[] images) {
        if (id != null) {
            Helper.checkNotPositive(id.longValue(), "Id");
        }

        Helper.checkNotPositive(sponsorId, "sponsorId");
        Helper.checkNotNullOrEmpty(domainName, "domainName");
        Helper.checkNotNullOrContainNullElement(images, "images");

        this.id = id;
        this.sponsorId = sponsorId;
        this.domainName = domainName;
        this.approved = approved;

        //shallow copy of images
        this.images = (ImageInfo[]) images.clone();
    }

    /**
     * <p>
     * Returns the unique ID for this domain, or null if none has yet been assigned.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Returns the user ID number of the sponsor to whom this domain is assigned.
     * </p>
     *
     * @return the sponsor id
     */
    public long getSponsorId() {
        return this.sponsorId;
    }

    /**
     * <p>
     * Returns the name of this domain.
     * </p>
     *
     * @return the domain name
     */
    public String getDomainName() {
        return this.domainName;
    }

    /**
     * <p>
     * Returns the value of this domain's approval flag, or null if no approval decision has been made.
     * </p>
     *
     * @return true if approved, false otherwise, or null of not decided yet.
     */
    public Boolean isApproved() {
        return this.approved;
    }

    /**
     * <p>
     * Returns ImageInfo objects representing all the images associated with this domain. Returns a shallow copy.
     * </p>
     *
     * @return the array of ImageInfo objects
     */
    public ImageInfo[] getImages() {
        //return the shallow copy array
        return (ImageInfo[]) images.clone();
    }
}
