/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Helper;


/**
 * <p>
 * Simple implementation of the BallColor. Represents a supported Barooka Ball color.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>:This class is immutable and thread-safe.
 * </p>
 * @author argolite, waits
 * @version 1.0
 */
public class BallColorImpl implements BallColor {
    /**
     * <p>
     * Represents the unique ID of this BallColor. Set in the constructor, can be null, and will not change. If not
     * null, must be positive.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * Represents the color name, such as "RED", "BLUE", or "TANGERINE". Set in the constructor, cannot be
     * null/empty, and will not change.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Represents the ID of a downloadable object that contains the ball image corresponding to this BallColor. Set
     * in the constructor, can be any positive number, and will not change.
     * </p>
     */
    private final long imageId;

    /**
     * Constructor.
     *
     * @param id the id can be null
     * @param name the color name
     * @param imageId the ID of a downloadable object that contains the ball image corresponding to this BallColor
     *
     * @throws IllegalArgumentException If id, if given, is not positive, or if name is null/empty, or imageId is not
     *         positive
     */
    public BallColorImpl(Long id, String name, long imageId) {
        Helper.checkNotNullOrEmpty(name, "Name");
        Helper.checkNotPositive(imageId, "ImageID");

        if (id != null) {
            Helper.checkNotPositive(id.longValue(), "id");
        }

        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    /**
     * <p>
     * Returns the unique ID of this BallColor.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Gets the color name, such as "RED", "BLUE", or "TANGERINE".
     * </p>
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Returns the ID of a downloadable object that contains the ball image corresponding to this BallColor.
     * </p>
     *
     * @return the image id
     */
    public long getImageId() {
        return this.imageId;
    }
}
