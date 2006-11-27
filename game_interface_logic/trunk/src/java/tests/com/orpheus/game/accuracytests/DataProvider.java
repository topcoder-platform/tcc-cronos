/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.util.HashMap;

import com.orpheus.game.persistence.Game;

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
	private static HashMap table = new HashMap();

	/**
	 * The objects array to hold an array objects.
	 */
	private static Object[] objects;

	/**
	 * The bids ids array.
	 */
	private static long[] bids;

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
	    table.put(game.getId(), game);
	}

	/**
	 * Get the game for the given id.
	 * @param id the id of the game
	 * @return the game for the given id
	 */
	public static Game getData(Long id) {
	    return (Game) table.get(id);	
	}

	/**
	 * Get all games.
	 * @return all games
	 */
	public static Game[] getAllData() {
	    return (Game[]) table.values().toArray(new Game[0]);
	}

	/**
	 * Set the objects array.
	 * @param objects the objects array
	 */
	public static void setObjects(Object[] objects) {
		DataProvider.objects = objects;	
	}

	/**
	 * Get the object array.
	 * @return the field
	 */
	public static Object[] getObjects() {
		return objects;
	}

	/**
	 * Set the field.
	 * @param bids the bids to set
	 */
	public static void setBids(long[] bids) {
		DataProvider.bids = bids;	
	}

	/**
	 * Get the bids array.
	 * @return the bids array
	 */
	public static long[] getBids() {
	    return bids;	
	}
}
