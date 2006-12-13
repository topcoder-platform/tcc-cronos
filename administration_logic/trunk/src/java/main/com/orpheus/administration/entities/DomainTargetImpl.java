/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.orpheus.game.persistence.DomainTarget;

/**
 * The implementation of DomainTarget which Represents an object to be found on
 * an Orpheus host site. <br/> No validation is done for any of the fields as
 * this is a plain POJO class meant to be used as a data transfer object between
 * remote calls.<br/> Thread-Safety: this class is not thread safe as it is
 * mutable.
 *
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class DomainTargetImpl implements DomainTarget {

    /**
     * will hold the identifier for this target.<br/> It can be retrieved/set
     * using corresponding getter/setter.<br/> It may be null.
     *
     */
    private Long id;

    /**
     * will hold the sequence number of this target among those.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be any
     * value.
     *
     */
    private int sequenceNumber;

    /**
     * will hold the path and file parts of the URI at which the target.<br/>
     * It can be retrieved/set using corresponding getter/setter.<br/> It may
     * be null.
     *
     */
    private String uriPath;

    /**
     * will hold the plain text identifier of the target.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be null.
     *
     */
    private String identifierText;

    /**
     * will hold a hash of the target's identifier.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be null.
     *
     */
    private String identifierHash;

    /**
     * will hold the unique identifier of a downloadable object constituting an
     * image to be presented to users as the clue for this target.<br/> It can
     * be retrieved/set using corresponding getter/setter.<br/> It may be any
     * value.
     *
     */
    private long clueImageId;

    /**
     * Empty constructor.
     *
     */
    public DomainTargetImpl() {

    }

    /**
     * Retrieves the unique identifier of a downloadable object constituting an
     * image to be presented to users as the clue for this target.
     *
     *
     * @return the unique identifier of a downloadable object constituting an
     *         image to be presented to users as the clue for this target.
     */
    public long getClueImageId() {
        return clueImageId;
    }

    /**
     * Sets the unique identifier of a downloadable object constituting an image
     * to be presented to users as the clue for this target.
     *
     *
     * @param clueImageId
     *            The clueImageId to set.
     */
    public void setClueImageId(long clueImageId) {
        this.clueImageId = clueImageId;
    }

    /**
     * Retrieves the unique identifier of this target.
     *
     *
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this target.
     *
     *
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the hash of the target's identifier.
     *
     *
     * @return Returns the identifierHash.
     */
    public String getIdentifierHash() {
        return identifierHash;
    }

    /**
     * Sets the hash of the target's identifier.
     *
     *
     * @param identifierHash
     *            The identifierHash to set.
     */
    public void setIdentifierHash(String identifierHash) {
        this.identifierHash = identifierHash;
    }

    /**
     * Returns the plain text identifier of the target.
     *
     *
     * @return Returns the identifierText.
     */
    public String getIdentifierText() {
        return identifierText;
    }

    /**
     * Sets the plain text identifier of the target.
     *
     * @param identifierText
     *            The identifierText to set.
     */
    public void setIdentifierText(String identifierText) {
        this.identifierText = identifierText;
    }

    /**
     * Returns the sequence number of this target among those assigned to the
     * same hosting slot.
     *
     *
     * @return Returns the sequenceNumber.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the sequence number of this target among those assigned to the same
     * hosting slot.
     *
     *
     * @param sequenceNumber
     *            The sequenceNumber to set.
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Returns the path and file parts of the URI at which the target is
     * located.
     *
     *
     * @return Returns the uriPath.
     */
    public String getUriPath() {
        return uriPath;
    }

    /**
     * Sets the path and file parts of the URI at which the target is located.
     *
     *
     * @param uriPath
     *            The uriPath to set.
     */
    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }
}
