/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import java.util.Date;
import java.util.HashMap;

import com.orpheus.administration.persistence.PendingWinner;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;

/**
 * <p>
 * This class is the mock up of the database used as the back database of the ejb.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class DataProvider {
	/**
	 * The hashmap used to add the game instance.
	 */
	static HashMap imageTable = new HashMap();

	/**
	 * The hashmap used to add the pending winner instance.
	 */
	static HashMap winnerTable = new HashMap();

	/**
	 * The array of HostingSlot.
	 */
	static HostingSlot[] slots;

	/**
	 * The hashmap used to add the slot instance.
	 */
	static HashMap slotTable = new HashMap();

	/**
	 * The flag the slot have been deleted.
	 */
	static boolean isDeleted;

	/**
	 * The flag the domain approval
	 */
	static boolean domainApproval;

	/**
	 * The flag the domain approval
	 */
	static boolean imageApproval;

	/**
	 * The private constructor.
	 */
	private DataProvider() {
	}

	/**
	 * Add game instance to table.
	 * @param game the game to added.
	 */
	public static void addData(Game game) {
	}

	/**
	 * Dummy
	 * @param id the id
	 * @param approval the flag
	 */
	public static void setImageApproval(long id, boolean approval) {
	    imageTable.put(new Long(id), new Boolean(approval));
	    imageApproval = approval;
	}

	/**
	 * Dummy
	 * @param winner the winner
	 * @param date the date
	 */
	public static void approveWinner(PendingWinner winner, Date date) {
	    winnerTable.put(winner, date);
	}

	/**
	 * Dummy
	 * @param winner the winner
	 */
	public static void rejectWinner(PendingWinner winner) {
	    winnerTable.remove(winner);
	}

	/**
	 * Dummy
	 * @param id the id
	 */
	public static void deleteSlot(long id) {
	    isDeleted = true;   
	}

	/**
	 * Dummy
	 * @param slots the slots
	 */
	public static void updateSlots(HostingSlot[] slots) {
	    DataProvider.slots = slots;    
	}

	/**
	 * Dummy
	 * @param domainId the domainId
	 * @param approved the flag.
	 */
	public static void setDomainApproval(long domainId, boolean approved) {
	    domainApproval = approved;
	}
}
