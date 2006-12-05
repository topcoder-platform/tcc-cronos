/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;

/**
 * Represents a "block" of Barooka Ball hosts. Blocks serve as an organizational
 * unit for Ball hosting auctions, and furthermore help to obscure the specific
 * sequence of upcoming domains, even from sponsors privy to the auction
 * details.<br/> No validation is done for any of the fields as this is a plain
 * POJO class meant to be used as a data transfer object between remote calls.<br/>
 * Thread-Safety: this class is not thread safe as it is mutable.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class HostingBlockImpl implements HostingBlock {

    /**
     * will the ID of this block.<br/> It can be retrieved/set using
     * corresponding getter/setter.<br/> It may be null.
     *
     */
    private Long id;

    /**
     * will the sequence number of this block within its game.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be any
     * value.
     *
     */
    private int sequenceNumber;

    /**
     * will an array of HostingSlot objects representing all the slots
     * associated with this block.<br/> It can be retrieved/set using
     * corresponding getter/setter.<br/> It may be null.
     *
     */
    private HostingSlot[] slots;

    /**
     * will the maximum hosting time for this block, in minutes.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be any
     * value.
     *
     */
    private int maxHostingTimePerSlot;

    /**
     * Empty constructor.
     *
     */
    public HostingBlockImpl() {

    }

    /**
     * Retrieves the ID of this block.
     *
     *
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of this block.
     *
     *
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the maximum hosting time for this block, in minutes.
     *
     *
     * @return Returns the maxHostingTimePerSlot.
     */
    public int getMaxHostingTimePerSlot() {
        return maxHostingTimePerSlot;
    }

    /**
     * Sets the maximum hosting time for this block, in minutes.
     *
     *
     * @param maxHostingTimePerSlot
     *            The maxHostingTimePerSlot to set.
     */
    public void setMaxHostingTimePerSlot(int maxHostingTimePerSlot) {
        this.maxHostingTimePerSlot = maxHostingTimePerSlot;
    }

    /**
     * Retrieves the sequence number of this block within its game.
     *
     *
     * @return Returns the sequenceNumber.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the sequence number of this block within its game.
     *
     *
     * @param sequenceNumber
     *            The sequenceNumber to set.
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Retrieves an array of HostingSlot objects representing all the slots
     * associated with this block.
     *
     *
     * @return Returns the slots.
     */
    public HostingSlot[] getSlots() {
        return slots;
    }

    /**
     * Sets an array of HostingSlot objects representing all the slots
     * associated with this block.
     *
     *
     * @param slots
     *            The slots to set.
     */
    public void setSlots(HostingSlot[] slots) {
        this.slots = slots;
    }
}
