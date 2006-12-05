package com.orpheus.game.persistence;

import java.rmi.RemoteException;

/**
 * The (remote) component interface of the GameData EJB, which provides access
 * to persistent information about games managed by the application.
 * <p>
 * <strong>Thread Safety</strong>
 * </p>
 * <p>
 * The container assumes all responsibility for thread-safety of these
 * implementations.
 * </p>
 * 
 */
public interface GameData extends javax.ejb.EJBObject {
    /**
     * Creates a new game entity in the persistent store, along with associated
     * hosting blocks. Any game or block IDs that are null will be automatically
     * assigned acceptable values. No hosting slots are created for the game at
     * this time. The returned Game object will represent the persisted data,
     * including any IDs assigned to the game and blocks.
     * 
     * 
     * @return the game with the id
     * @param game
     *            the game
     * @throws EntryNotFoundException
     *             If game.ballColor.id is not found in persistence
     * @throws DuplicateEntryException
     *             If game.id is not null but already exists in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If game is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Game createGame(Game game) throws EntryNotFoundException,
        DuplicateEntryException, PersistenceException, RemoteException;

    /**
     * Creates hosting slots associates with the specified Bid IDs in the
     * specified hosting block
     * 
     * 
     * @return array of hosting slots
     * @param blockId
     *            the block id
     * @param bidIds
     *            the bid ids
     * @throws EntryNotFoundException
     *             If blockId or any bidId doesn't exist in the persistence
     * @throws InvalidEntryException
     *             If any bidId does not belong to the blockId
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If bidIds is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public HostingSlot[] createSlots(long blockId, long[] bidIds)
        throws EntryNotFoundException, InvalidEntryException,
        PersistenceException, RemoteException;

    /**
     * Creates a new persistent domain representation with the data from the
     * provided Domain object and its nested ImageInfo objects. Any null Domain
     * or ImageIndo IDs are assigned appropriate values. The returned Domain
     * will reflect the persistent representation, including any automatically
     * assigned IDs.
     * 
     * 
     * @return the domain with id
     * @param domain
     *            the domain
     * @throws EntryNotFoundException
     *             If imageInfo.downloadId doesn't exist in persistence
     * @throws DuplicateEntryException
     *             If id or imageInfo.id is not null but already exists in
     *             persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If domain is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Domain createDomain(Domain domain) throws EntryNotFoundException,
        DuplicateEntryException, PersistenceException, RemoteException;;

    /**
     * Creates a new, persistent hosting block for the specified game. The block
     * will having an auto-assigned ID, the next available sequence number after
     * those of the game's existing blocks (or 1 if there are no other blocks),
     * no hosting slots, and the specified maximum hosting time per slot. It
     * returns a HostingBlock object representing the new block.
     * 
     * 
     * @return the hosting block
     * @param gameId
     *            the game id
     * @param slotMaxHostingTime
     *            the max hosting time for the slot
     * @throws EntryNotFoundException
     *             If gameId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public HostingBlock addBlock(long gameId, int slotMaxHostingTime)
        throws EntryNotFoundException, PersistenceException, RemoteException;

    /**
     * Retrieves a Game object representing the Game having the specified ID
     * 
     * 
     * @return the game
     * @param gameId
     *            the game id
     * @throws EntryNotFoundException
     *             If gameId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Game getGame(long gameId) throws EntryNotFoundException,
        PersistenceException, RemoteException;

    /**
     * Retrieves a HostingBlock object representing the hosting block having the
     * specified ID
     * 
     * 
     * @return the block
     * @param blockId
     *            the block id
     * @throws EntryNotFoundException
     *             If blockId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public HostingBlock getBlock(long blockId) throws EntryNotFoundException,
        PersistenceException, RemoteException;;

    /**
     * Retrieves a HostingSlot object representing the slot having the specified
     * ID
     * 
     * 
     * @return the slot
     * @param slotId
     *            the slot id
     * @throws EntryNotFoundException
     *             If slotId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public HostingSlot getSlot(long slotId) throws EntryNotFoundException,
        PersistenceException, RemoteException;;

    /**
     * Retrieves the DownloadData object corresponding to the specified ID
     * 
     * 
     * @return the download data
     * @param id
     *            the download id
     * @throws EntryNotFoundException
     *             If id is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public com.topcoder.web.frontcontroller.results.DownloadData getDownloadData(
            long id) throws EntryNotFoundException, PersistenceException,
        RemoteException;;

    /**
     * Retrieves a Domain object representing the domain corresponding to the
     * specified ID
     * 
     * 
     * @return the domain
     * @param domainId
     *            the domain id
     * @throws EntryNotFoundException
     *             If domainId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Domain getDomain(long domainId) throws EntryNotFoundException,
        PersistenceException, RemoteException;;

    /**
     * Returns the key text for the specified player's completions of the
     * specified slots. The length of the returned array is the same as the
     * length of the slotIds argument, and their elements correspond: each
     * string in the returned array is the key text associated with the slot
     * completion by the specified player of the slot whose ID appears at the
     * same index in the input slotIds. If the specified player has not
     * completed any particular slot specified among the slot IDs then the
     * corresponding element or the returned array is null.
     * 
     * 
     * @return the player's keys
     * @param playerId
     *            the player id
     * @param slotIds
     *            the slot ids
     * @throws EntryNotFoundException
     *             If playerId or any slotId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If slotIds is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public String[] getKeysForPlayer(long playerId, long[] slotIds)
        throws EntryNotFoundException, PersistenceException, RemoteException;;

    /**
     * Retrieves the PuzzleData associated with the specified puzzle ID.
     * 
     * 
     * @return the puzzle data
     * @param puzzleId
     *            the puzzle id
     * @throws EntryNotFoundException
     *             If puzzleId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public com.topcoder.util.puzzle.PuzzleData getPuzzle(long puzzleId)
        throws EntryNotFoundException, PersistenceException, RemoteException;;

    /**
     * Increments the download count for the plugin identified by the specified
     * name
     * 
     * 
     * @param pluginName
     *            the plugin name
     * @throws EntryNotFoundException
     *             If pluginName is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If pluginName is null/empty
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public void recordPluginDownload(String pluginName)
        throws EntryNotFoundException, PersistenceException, RemoteException;;

    /**
     * Records the specified player's registration for the specified game
     * 
     * 
     * @param playerId
     *            the player id
     * @param gameId
     *            the game id
     * @throws EntryNotFoundException
     *             If playerID or gameId is not in persistence
     * @throws DuplicateEntryException
     *             If the player has been already registered for this game
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public void recordRegistration(long playerId, long gameId)
        throws EntryNotFoundException, PersistenceException, RemoteException;;

    /**
     * Records the completion of the specified slot by the specified player at
     * the specified date and time, and generates a key for the player to
     * associate with the completion.
     * 
     * 
     * @return the slot completion entity
     * @param playerId
     *            the player id
     * @param slotId
     *            the slot id
     * @param date
     *            date of completion
     * @throws EntryNotFoundException
     *             If playerID or slotId is not in persistence
     * @throws DuplicateEntryException
     *             If the slot has already been completed for this player
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If date is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public SlotCompletion recordSlotCompletion(long playerId, long slotId,
            java.util.Date date) throws EntryNotFoundException,
        PersistenceException, RemoteException, DuplicateEntryException;

    /**
     * Records the fact that the specified player has completed the specified
     * game. Whether or not such a player actually wins the game depends on
     * whether others have already completed the game, and on administrative
     * establishment of winner eligibility.
     * 
     * 
     * @param playerId
     *            the player id
     * @param gameId
     *            the game id
     * @throws EntryNotFoundException
     *             If playerID or gameId is not in persistence
     * @throws DuplicateEntryException
     *             If the player has been already registered for this game
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public void recordGameCompletion(long playerId, long gameId)
        throws EntryNotFoundException, PersistenceException, RemoteException,
        DuplicateEntryException;

    /**
     * Records a binary object in the database, such as might later be retrieved
     * by the custom DownloadSource. The ID assigned to the binary object is
     * returned.
     * 
     * 
     * @return the binary object
     * @param name
     *            the name of the object
     * @param mediaType
     *            the media type
     * @param content
     *            the content as a byte array
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If name or mediaType is null/empty, or content is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public long recordBinaryObject(String name, String mediaType, byte[] content)
        throws PersistenceException, RemoteException;

    /**
     * Updates the persistent hosting slot information for the existing slots
     * represented by the specified HostingSlot objects, so that the persistent
     * representation matches the provided objects. Nested DomainTarget objects
     * may or may not already be recorded in persistence; the component assumes
     * that DomainTarget's with null IDs are new, and that others already exist
     * in the database. The component will assign IDs for new DomainTargets as
     * needed. This method will also update the following additional HostingSlot
     * properties (only): sequence number, hosting start, hosting end, brain
     * teaser IDs, puzzle ID. It will return an array containing the revised
     * hosting slots.
     * 
     * 
     * @return the updated hosting slots
     * @param slots
     *            the hosting slots to update
     * @throws EntryNotFoundException
     *             If HsotingSlot.id or DomainTarget.id, if not null, is not in
     *             persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If slots is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public HostingSlot[] updateSlots(HostingSlot[] slots)
        throws EntryNotFoundException, PersistenceException, RemoteException;;

    /**
     * Updates the persistent domain information for the specified Domain object
     * to match the Domain object, where the appropriate persistent record is
     * identified by the Domain's ID
     * 
     * 
     * @param domain
     *            domain to update
     * @throws EntryNotFoundException
     *             If Domain.id or ImageInfo.id, if not null, is not in
     *             persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If domain is null
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public void updateDomain(Domain domain) throws EntryNotFoundException,
        PersistenceException, RemoteException;

    /**
     * Deletes the hosting slot having the specified ID
     * 
     * 
     * @param slotId
     *            slot id
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public void deleteSlot(long slotId) throws PersistenceException,
        RemoteException;

    /**
     * Looks up all distinct domains hosting any slot in any active game, and
     * returns an array of Domain objects representing the results
     * 
     * 
     * @return array of active domains
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Domain[] findActiveDomains() throws PersistenceException,
        RemoteException;;

    /**
     * Looks up all ongoing games in which a domain matching the specified
     * string is a host in a slot that the specified player has not yet
     * completed, and returns an array of all such games
     * 
     * 
     * @return array of games
     * @param domain
     *            the domain
     * @param playerId
     *            the player id
     * @throws EntryNotFoundException
     *             If there is no domain with the given name, or no player with
     *             such an id in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If domain is null/empty
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Game[] findGamesByDomain(String domain, long playerId)
        throws EntryNotFoundException, PersistenceException, RemoteException;;

    /**
     * Looks up all hosting slots completed by any player in the specified game,
     * and returns the results as an array of HostingSlot objects. Returned
     * slots are in ascending order by first completion time, or equivalently,
     * in ascending order by hosting block sequence number and hosting slot
     * sequence number.
     * 
     * 
     * @return array of hosting slots
     * @param gameId
     *            the game id
     * @throws EntryNotFoundException
     *             If gameId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public HostingSlot[] findCompletedSlots(long gameId)
        throws EntryNotFoundException, PersistenceException, RemoteException;

    /**
     * Looks up all recorded completion events that have the specified hosting
     * slot in the specified game
     * 
     * 
     * @return array of slot competition events
     * @param gameId
     *            the game id
     * @param slotId
     *            the slot id
     * @throws EntryNotFoundException
     *             If gameId or slotId is not in persistence
     * @throws IllegalEntryException
     *             If slotId is not part of the game indicated by gameId
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public SlotCompletion[] findSlotCompletions(long gameId, long slotId)
        throws EntryNotFoundException, PersistenceException, RemoteException;

    /**
     * Retrieves game information for games meeting the specified game status
     * criteria
     * 
     * 
     * @return an array of Game objects representing the games found
     * @param isStarted
     *            a Boolean having value true to restrict to games that have
     *            started or false to restrict to games that have not yet
     *            started; null to ignore whether games have started
     * @param isEnded
     *            a Boolean having value true to restrict to games that have
     *            ended or false to restrict to games that have not yet ended;
     *            null to ignore whether games have ended
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If isStarted == false and isEnded == true (impossible combo)
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Game[] findGames(Boolean isStarted, Boolean isEnded)
        throws PersistenceException, RemoteException;

    /**
     * Looks up all the games for which the specified player is registered, and
     * returns an array of their IDs
     * 
     * 
     * @return the array of game ids
     * @param playerId
     *            the player id
     * @throws EntryNotFoundException
     *             If playerId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public long[] findGameRegistrations(long playerId)
        throws EntryNotFoundException, PersistenceException, RemoteException;

    /**
     * Looks up all domains associated with the specified sponsor and returns an
     * array of Domain objects representing them
     * 
     * 
     * @return array of domains
     * @param sponsorId
     *            the sponsor id
     * @throws EntryNotFoundException
     *             If sponsorId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public Domain[] findDomainsForSponsor(long sponsorId)
        throws EntryNotFoundException, PersistenceException, RemoteException;

    /**
     * Finds the first HostingSlot in the hosting sequence for the specified
     * game that is assigned the specified domain and has not yet been completed
     * by the specified player.
     * 
     * 
     * @return hosting slot
     * @param gameId
     *            the game id
     * @param playerId
     *            the player id
     * @param domain
     *            the domain
     * @throws EntryNotFoundException
     *             If there is no domain with the given name, or no player or
     *             game with such an id in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If domain is null/empty
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId,
            String domain) throws EntryNotFoundException, PersistenceException,
        RemoteException;

    /**
     * Provides information about all ball colors available to the application.
     * 
     * 
     * @return array of ball colors
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws RemoteException
     *             if a communication error occurs between client and EJB
     *             container
     */
    public BallColor[] findAllBallColors() throws PersistenceException,
        RemoteException;
}
