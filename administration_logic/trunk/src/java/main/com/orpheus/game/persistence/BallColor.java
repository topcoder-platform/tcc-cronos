package com.orpheus.game.persistence;

import java.io.Serializable;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.*;
import com.topcoder.web.frontcontroller.results.*;

/**
 * A BallColor object represents a supported Barooka Ball color
 * <p><strong>Thread Safety</strong></p>
 * <p>Implementations should strive to be thread-safe, but they can expect to be used in a thread-safe manner.</p>
 * 
 */
public interface BallColor extends Serializable {
    /**
     * Returns the unique ID of this BallColor
     * 
     * 
     * @return the id
     */
    public Long getId();

    /**
     * Gets the color name, such as &quot;RED&quot;, &quot;BLUE&quot;, or &quot;TANGERINE&quot;.
     * 
     * 
     * @return the name
     */
    public String getName();

    /**
     * Returns the ID of a downloadable object that contains the ball
     * image corresponding to this BallColor.
     * 
     * 
     * @return the image id
     */
    public long getImageId();
}
