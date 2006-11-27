/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;

/**
 * A stub class for HostingBlock.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestHostingBlock implements HostingBlock {
	/**
	 * The hostingSlot field.
	 */
	private HostingSlot[] slots;

	/**
	 * Get the id.
	 * @return the id
	 */
	public Long getId() {
		return null;
	}

	/**
	 * Get the sequence number.
	 * @return the sequence number
	 */
	public int getSequenceNumber() {
		return 0;
	}

	/**
	 * Get the slots.
	 * @return the slots
	 */
	public HostingSlot[] getSlots() {
		return slots;
	}

	/**
	 * Set the slots field.
	 * @param slots the slots
	 */
	public void setSlots(HostingSlot[] slots) {
	    this.slots = slots;
	}

	/**
	 * Get the max hosting time per slot.
	 * @return the number
	 */
	public int getMaxHostingTimePerSlot() {
		return 0;
	}

}
