
package com.orpheus.game.persistence;
/**
 * An interface representing a hosting domain within the application
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm3190]
 */
public interface Domain extends java.io.Serializable {
/**
 * Returns the unique ID for this domain, or null if none has yet been assigned
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f0a]
 * @return 
 */
    public Long getId();
/**
 * Returns the user ID number of the sponsor to whom this domain is assigned
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2f03]
 * @return 
 */
    public long getSponsorId();
/**
 * Returns the name of this domain -- i.e. the DNS name of the host -- as a String
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2efc]
 * @return 
 */
    public String getDomainName();
/**
 * Returns the value of this domain's approval flag, or null if no approval decision has been made
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2ef5]
 * @return 
 */
    public Boolean isApproved();
/**
 * Returns ImageInfo objects representing all the images associated with this domain
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm2eed]
 * @return 
 */
    public ImageInfo[] getImages();
}


