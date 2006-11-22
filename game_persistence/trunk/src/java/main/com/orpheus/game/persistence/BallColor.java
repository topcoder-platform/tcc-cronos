/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.io.Serializable;


/**
 * <p>
 * A BallColor object represents a supported Barooka Ball color.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: Implementations should strive to be thread-safe, but they can expect to be used in a
 * thread-safe manner.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface BallColor extends Serializable {
    /**
     * <p>
     * Returns the unique ID of this BallColor.
     * </p>
     *
     * @return the id
     */
    Long getId();

    /**
     * <p>
     * Gets the color name, such as &quot;RED&quot;, &quot;BLUE&quot;, or &quot;TANGERINE&quot;.
     * </p>
     *
     * @return the name
     */
    String getName();

    /**
     * <p>
     * Returns the ID of a downloadable object that contains the ball image corresponding to this BallColor.
     * </p>
     *
     * @return the image id
     */
    long getImageId();
}
