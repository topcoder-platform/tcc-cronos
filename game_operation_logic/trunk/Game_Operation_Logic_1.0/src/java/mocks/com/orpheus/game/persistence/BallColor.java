
package com.orpheus.game.persistence;
/**
 * A BallColor object represents a supported Barooka Ball color
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm3191]
 */
public interface BallColor extends java.io.Serializable {
/**
 * Returns the unique ID of this BallColor
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f88]
 * @return 
 */
    public Long getId();
/**
 * Gets the color name, such as "RED", "BLUE", or "TANGERINE".
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f81]
 * @return 
 */
    public String getName();
/**
 * Returns the ID of a downloadable object that contains the ball image corresponding to this BallColor.
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f7a]
 * @return 
 */
    public long getImageId();
}


