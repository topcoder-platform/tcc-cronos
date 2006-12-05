package com.orpheus.game.persistence;

import java.io.Serializable;

/**
 * An interface representing a hosting domain within the application. A domain represents a website that is eligible to host an application. Each is associated with a specific sponsor, and will contain the base URL.
 * <p><strong>Thread Safety</strong></p>
 * <p>Implementations should strive to be thread-safe, but they can expect to be used in a thread-safe manner.</p>
 * 
 */
public interface Domain extends Serializable {
    /**
     * Returns the unique ID for this domain, or null if none has yet
     * been assigned
     * 
     * 
     * @return the id
     */
    public Long getId();

    /**
     * Returns the user ID number of the sponsor to whom this domain
     * is assigned
     * 
     * 
     * @return the sponsor id
     */
    public long getSponsorId();

    /**
     * Returns the name of this domain -- i.e. the DNS name of the
     * host -- as a String
     * 
     * 
     * @return the domain name
     */
    public String getDomainName();

    /**
     * Returns the value of this domain's approval flag, or null if
     * no approval decision has been made
     * 
     * 
     * @return true if approved, false otherwise, or null of not decided yet.
     */
    public Boolean isApproved();

    /**
     * Returns ImageInfo objects representing all the images
     * associated with this domain
     * 
     * 
     * @return the array of ImageInfo objects
     */
    public ImageInfo[] getImages();
}
