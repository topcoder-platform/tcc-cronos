/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.topcoder.util.puzzle.PuzzleData;

import com.topcoder.web.frontcontroller.results.DownloadData;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;


/**
 * <p>
 * The (remote) component interface of the GameData EJB, which provides access to persistent information about games
 * managed by the application.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author argolite, waits
 * @version 1.0
 */
public interface GameData extends EJBObject {
    /**
     * <p>
     * Creates a new game entity in the persistent store, along with associated hosting blocks. Any game or block IDs
     * that are null will be automatically assigned acceptable values. No hosting slots are created for the game at
     * this time. The returned Game object will represent the persisted data, including any IDs assigned to the game
     * and blocks.
     * </p>
     *
     * @param game the game
     *
     * @return the game with the id
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If game.ballColor.id is not found in persistence
     * @throws IllegalArgumentException If game is null
     * @throws DuplicateEntryException If game.id is not null but already exists in persistence
     */
    Game createGame(Game game)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Creates hosting slots associates with the specified Bid IDs in the specified hosting block.
     * </p>
     *
     * @param blockId the block id
     * @param bidIds the bid ids
     *
     * @return array of hosting slots
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If blockId or any bidId doesn't exist in the persistence
     * @throws InvalidEntryException If any bidId does not belong to the blockId
     * @throws IllegalArgumentException If bidIds is null
     */
    HostingSlot[] createSlots(long blockId, long[] bidIds)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Creates a new persistent domain representation with the data from the provided Domain object and its nested
     * ImageInfo objects. Any null Domain or ImageIndo IDs are assigned appropriate values. The returned Domain will
     * reflect the persistent representation, including any automatically assigned IDs.
     * </p>
     *
     * @param domain the domain
     *
     * @return the domain with id
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If imageInfo.downloadId doesn't exist in persistence
     * @throws DuplicateEntryException If id or imageInfo.id is not null but already exists in persistence
     * @throws IllegalArgumentException If domain is null
     */
    Domain createDomain(Domain domain) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Creates a new, persistent hosting block for the specified game. The block will having an auto-assigned ID, the
     * next available sequence number after those of the game's existing blocks (or 1 if there are no other blocks),
     * no hosting slots, and the specified maximum hosting time per slot. It returns a HostingBlock object
     * representing the new block.
     * </p>
     *
     * @param gameId the game id
     * @param slotMaxHostingTime the max hosting time for the slot
     *
     * @return the hosting block
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If gameId is not in persistence
     */
    HostingBlock addBlock(long gameId, int slotMaxHostingTime)
        throws RemoteException, PersistenceException;

    /**
     * Retrieves a Game object representing the Game having the specified ID.
     *
     * @param gameId the game id
     *
     * @return the game
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If gameId is not in persistence
     */
    Game getGame(long gameId) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Retrieves a HostingBlock object representing the hosting block having the specified ID.
     * </p>
     *
     * @param blockId the block id
     *
     * @return the block
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If blockId is not in persistence
     */
    HostingBlock getBlock(long blockId) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Retrieves a HostingSlot object representing the slot having the specified ID.
     * </p>
     *
     * @param slotId the slot id
     *
     * @return the slot
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If slotId is not in persistence
     */
    HostingSlot getSlot(long slotId) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Retrieves the DownloadData object corresponding to the specified ID.
     * </p>
     *
     * @param id the download id
     *
     * @return the download data
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If id is not in persistence
     */
    DownloadData getDownloadData(long id) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Retrieves a Domain object representing the domain corresponding to the specified ID.
     * </p>
     *
     * @param domainId the domain id
     *
     * @return the domain
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If domainId is not in persistence
     */
    Domain getDomain(long domainId) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Returns the key text for the specified player's completions of the specified slots. The length of the returned
     * array is the same as the length of the slotIds argument, and their elements correspond: each string in the
     * returned array is the key text associated with the slot completion by the specified player of the slot whose
     * ID appears at the same index in the input slotIds. If the specified player has not completed any particular
     * slot specified among the slot IDs then the corresponding element or the returned array is null.
     * </p>
     *
     * @param playerId the player id
     * @param slotIds the slot ids
     *
     * @return the player's keys
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If playerId or any slotId is not in persistence
     * @throws IllegalArgumentException If slotIds is null
     */
    String[] getKeysForPlayer(long playerId, long[] slotIds)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Retrieves the PuzzleData associated with the specified puzzle ID.
     * </p>
     *
     * @param puzzleId the puzzle id
     *
     * @return the puzzle data
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If puzzleId is not in persistence
     */
    PuzzleData getPuzzle(long puzzleId) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Increments the download count for the plugin identified by the specified name.
     * </p>
     *
     * @param pluginName the plugin name
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If pluginName is not in persistence
     * @throws IllegalArgumentException If pluginName is null/empty
     */
    void recordPluginDownload(String pluginName)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Records the specified player's registration for the specified game.
     * </p>
     *
     * @param playerId the player id
     * @param gameId the game id
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws DuplicateEntryException If the player has been already registered for this game
     * @throws EntryNotFoundException If playerID or gameId is not in persistence
     */
    void recordRegistration(long playerId, long gameId)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Records the completion of the specified slot by the specified player at the specified date and time, and
     * generates a key for the player to associate with the completion.
     * </p>
     *
     * @param playerId the player id
     * @param slotId the slot id
     * @param date date of completion
     *
     * @return the slot completion entity
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws DuplicateEntryException If the slot has already been completed for this player
     * @throws EntryNotFoundException If playerID or slotId is not in persistence
     * @throws IllegalArgumentException If date is null
     */
    SlotCompletion recordSlotCompletion(long playerId, long slotId, java.util.Date date)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Records the fact that the specified player has completed the specified game. Whether or not such a player
     * actually wins the game depends on whether others have already completed the game, and on administrative
     * establishment of winner eligibility.
     * </p>
     *
     * @param playerId the player id
     * @param gameId the game id
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws DuplicateEntryException If the player has been already registered for this game
     * @throws EntryNotFoundException If playerID or gameId is not in persistence
     */
    void recordGameCompletion(long playerId, long gameId)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Records a binary object in the database, such as might later be retrieved by the custom DownloadSource. The ID
     * assigned to the binary object is returned.
     * </p>
     *
     * @param name the name of the object
     * @param mediaType the media type
     * @param content the content as a byte array
     *
     * @return the binary object
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If name or mediaType is null/empty, or content is null
     */
    long recordBinaryObject(String name, String mediaType, byte[] content)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Updates the persistent hosting slot information for the existing slots represented by the specified
     * HostingSlot objects, so that the persistent representation matches the provided objects. Nested DomainTarget
     * objects may or may not already be recorded in persistence; the component assumes that DomainTarget's with
     * null IDs are new, and that others already exist in the database. The component will assign IDs for new
     * DomainTargets as needed. This method will also update the following additional HostingSlot properties (only):
     * sequence number, hosting start, hosting end, brain teaser IDs, puzzle ID. It will return an array containing
     * the revised hosting slots.
     * </p>
     *
     * @param slots the hosting slots to update
     *
     * @return the updated hosting slots
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If HsotingSlot.id or DomainTarget.id, if not null, is not in persistence
     * @throws IllegalArgumentException If slots is null
     */
    HostingSlot[] updateSlots(HostingSlot[] slots)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Updates the persistent domain information for the specified Domain object to match the Domain object, where
     * the appropriate persistent record is identified by the Domain's ID.
     * </p>
     *
     * @param domain domain to update
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null
     * @throws EntryNotFoundException If Domain.id or ImageInfo.id, if not null, is not in persistence
     */
    void updateDomain(Domain domain)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Deletes the hosting slot having the specified ID.
     * </p>
     *
     * @param slotId slot id
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    void deleteSlot(long slotId) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Looks up all distinct domains hosting any slot in any active game, and returns an array of Domain objects
     * representing the results.
     * </p>
     *
     * @return array of active domains
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    Domain[] findActiveDomains() throws RemoteException, PersistenceException;

    /**
     * <p>
     * Looks up all ongoing games in which a domain matching the specified string is a host in a slot that the
     * specified player has not yet completed, and returns an array of all such games.
     * </p>
     *
     * @param domain the domain
     * @param playerId the player id
     *
     * @return array of games
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If there is no domain with the given name, or no player with such an id in
     *         persistence
     * @throws IllegalArgumentException If domain is null/empty
     */
    Game[] findGamesByDomain(String domain, long playerId)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Looks up all hosting slots completed by any player in the specified game, and returns the results as an array
     * of HostingSlot objects. Returned slots are in ascending order by first completion time, or equivalently, in
     * ascending order by hosting block sequence number and hosting slot sequence number.
     * </p>
     *
     * @param gameId the game id
     *
     * @return array of hosting slots
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If gameId is not in persistence
     */
    HostingSlot[] findCompletedSlots(long gameId)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Looks up all recorded completion events that have the specified hosting slot in the specified game.
     * </p>
     *
     * @param gameId the game id
     * @param slotId the slot id
     *
     * @return array of slot competition events
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws InvalidEntryException If slotId is not part of the game indicated by gameId
     * @throws EntryNotFoundException If gameId or slotId is not in persistence
     */
    SlotCompletion[] findSlotCompletions(long gameId, long slotId)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Retrieves game information for games meeting the specified game status criteria.
     * </p>
     *
     * @param isStarted a Boolean having value true to restrict to games that have started or false to restrict to
     *        games that have not yet started; null to ignore whether games have started
     * @param isEnded a Boolean having value true to restrict to games that have ended or false to restrict to games
     *        that have not yet ended; null to ignore whether games have ended
     *
     * @return an array of Game objects representing the games found
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If isStarted == false and isEnded == true (impossible combo)
     */
    Game[] findGames(Boolean isStarted, Boolean isEnded)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Looks up all the games for which the specified player is registered, and returns an array of their IDs.
     * </p>
     *
     * @param playerId the player id
     *
     * @return the array of game ids
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If playerId is not in persistence
     */
    long[] findGameRegistrations(long playerId) throws RemoteException, PersistenceException;

    /**
     * <p>
     * Looks up all domains associated with the specified sponsor and returns an array of Domain objects representing
     * them.
     * </p>
     *
     * @param sponsorId the sponsor id
     *
     * @return array of domains
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If sponsorId is not in persistence
     */
    Domain[] findDomainsForSponsor(long sponsorId)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Finds the first HostingSlot in the hosting sequence for the specified game that is assigned the specified
     * domain and has not yet been completed by the specified player.
     * </p>
     *
     * @param gameId the game id
     * @param playerId the player id
     * @param domain the domain
     *
     * @return hosting slot
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null/empty
     * @throws EntryNotFoundException If there is no domain with the given name, or no player or game with such an id
     *         in persistence
     */
    HostingSlot findSlotForDomain(long gameId, long playerId, String domain)
        throws RemoteException, PersistenceException;

    /**
     * <p>
     * Provides information about all ball colors available to the application.
     * </p>
     *
     * @return array of ball colors
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    BallColor[] findAllBallColors() throws RemoteException, PersistenceException;
}
