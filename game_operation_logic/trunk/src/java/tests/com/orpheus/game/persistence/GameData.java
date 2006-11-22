
package com.orpheus.game.persistence;


/**
 * The (remote) component interface of the GameData EJB, which provides access to persistent information about games managed by the application
 * 
 * 
 */
public interface GameData extends javax.ejb.EJBObject {
/**
 * Creates a new game entity in the persistent store, along with associated hosting blocks. Any game or block IDs that are null will be automatically assigned acceptable values. No hosting slots are created for the game at this time. The returned Game object will represent the persisted data, including any IDs assigned to the game and blocks.
 * 
 * 
 * @return 
 * @param game 
 */
    public Game createGame(Game game) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Creates hosting slots associates with the specified Bid IDs in the specified hosting block
 * 
 * 
 * @return 
 * @param blockId 
 * @param bidIds 
 */
    public HostingSlot[] createSlots(long blockId, long[] bidIds) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Creates a new persistent domain representation with the data from the provided Domain object and its nested ImageInfo objects. Any null Domain or ImageIndo IDs are assigned appropriate values. The returned Domain will reflect the persistent representation, including any automatically assigned IDs.
 * 
 * 
 * @return 
 * @param domain 
 */
    public Domain createDomain(Domain domain) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Creates a new, persistent hosting block for the specified game. The block will having an auto-assigned ID, the next available sequence number after those of the game's existing blocks (or 1 if there are no other blocks), no hosting slots, and the specified maximum hosting time per slot. It returns a HostingBlock object representing the new block.
 * 
 * 
 * @return 
 * @param gameId 
 * @param slotMaxHostingTime 
 */
    public HostingBlock addBlock(long gameId, int slotMaxHostingTime) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Retrieves a Game object representing the Game having the specified ID
 * 
 * 
 * @return 
 * @param gameId 
 */
    public Game getGame(long gameId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Retrieves a HostingBlock object representing the hosting block having the specified ID
 * 
 * 
 * @return 
 * @param blockId 
 */
    public HostingBlock getBlock(long blockId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Retrieves a HostingSlot object reqpresenting the slot having the specified ID
 * 
 * 
 * @return 
 * @param slotId 
 */
    public HostingSlot getSlot(long slotId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;

    
/**
 * Retrieves a Domain object representing the domain corresponding to the specified ID
 * 
 * 
 * @return 
 * @param domainId 
 */
    public Domain getDomain(long domainId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Returns the key text for the specified player's completions of the specified slots. The length of the returned array is the same as the length of the slotIds argument, and their elements correspond: each string in the returned array is the key text associated with the slot completion by the specified player of the slot whose ID appears at the same index in the input slotIds. If the specified player has not completed any particular slot specified among the slot IDs then the corresponding element or the returned array is null.
 * 
 * 
 * @return 
 * @param playerId 
 * @param slotIds 
 */
    public String[] getKeysForPlayer(long playerId, long[] slotIds) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Retrieves the PuzzleData associated with the specified puzzle ID.
 * 
 * 
 * @return 
 * @param puzzleId 
 */
    public com.topcoder.util.puzzle.PuzzleData getPuzzle(long puzzleId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Increments the download count for the plugin identified by the specified name
 * 
 * 
 * @param pluginName 
 */
    public void recordPluginDownload(String pluginName) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Records the specified player's registration for the specified game
 * 
 * 
 * @param playerId 
 * @param gameId 
 */
    public void recordRegistration(long playerId, long gameId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Records the completion of the specified slot by the specified player at the specified date and time, and generates a key for the playe to associate with the completion.
 * 
 * 
 * @return 
 * @param playerId 
 * @param slotId 
 * @param date 
 */
    public SlotCompletion recordSlotCompletion(long playerId, long slotId, java.util.Date date) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Records the fact that the specified player has completed the specified game. Whether or not such a player actually wins the game depends on whether others have already completed the game, and on administrative establishment of winner eligibility.
 * 
 * 
 * @param playerId 
 * @param gameId 
 */
    public void recordGameCompletion(long playerId, long gameId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Records a binary object in the database, such as might later be retrieved by the custom DownloadSource. The ID assigned to the binary object is returned.
 * 
 * 
 * @return 
 * @param name 
 * @param mediaType 
 * @param content 
 */
    public long recordBinaryObject(String name, String mediaType, byte[] content) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Updates the persistent hosting slot information for the existing slots represented by the specified HostingSlot objects, so that the persistent representation matches the provided objects. Nested DomainTarget objects may or may not already be recorded in persistence; the component assumes that DomainTarget's with null IDs are new, and that others already exist in the database. The component will assign IDs for new DomainTargets as needed. * This method will also update the following additional HostingSlot properties (only): sequence number, hosting start, hosting end, brain teaser IDs, puzzle ID. It will return an array containing the revised hosting slots.
 * 
 * 
 * @return 
 * @param slots 
 */
    public HostingSlot[] updateSlots(HostingSlot[] slots) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Updates the persistent domain information for the specified Domain object to match the Domain object, where the appropriate persistent record is identified by the Domain's ID
 * 
 * 
 * @param domain 
 */
    public void updateDomain(Domain domain) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Deletes the hosting slot having the specified ID
 * 
 * 
 * @param slotId 
 */
    public void deleteSlot(long slotId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Looks up all distinct domains hosting any slot in any active game, and returns an array of Domain objects representing the results
 * 
 * 
 * @return 
 */
    public Domain[] findActiveDomains() throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Looks up all ongoing games in which a domain matching the specified string is a host in a slot that the specified player has not yet completed, and returns an array of all such games
 * 
 * 
 * @return 
 * @param domain 
 * @param playerId 
 */
    public Game[] findGamesByDomain(String domain, long playerId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Looks up all hosting slots completed by any player in the specified game, and returns the results as an array of HostingSlot objects. Returned slots are in ascending order by first completion time, or equivalently, in ascending order by hosting block sequence number and hosting slot sequence number.
 * 
 * 
 * @return 
 * @param gameId 
 */
    public HostingSlot[] findCompletedSlots(long gameId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Looks up all the players who are recorded as having completed the specified hosting slot in the specified game, and returns an array of their IDs.
 * 
 * 
 * @return 
 * @param gameId 
 * @param slotId 
 */
    public SlotCompletion[] findSlotCompletingPlayers(long gameId, long slotId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Retrieves game information for games meeting the specified game status criteria
 * 
 * 
 * @return an array of Game objects representing the games found
 * @param isStarted a Boolean having value true to restrict to games that have started or false to restrict to games that have not yet started; null to ignore whether games have started
 * @param isEnded a Boolean having value true to restrict to games that have ended or false to restrict to games that have not yet ended; null to ignore whether games have ended
 */
    public Game[] findGames(Boolean isStarted, Boolean isEnded) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Looks up all the games for which the specified player is registered, and returns an array of their IDs
 * 
 * 
 * @return 
 * @param playerId 
 */
    public long[] findGameRegistrations(long playerId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Looks up all domains associated with the specified sponsor and returns an array of Domain objects representing them
 * 
 * 
 * @return 
 * @param sponsorId 
 */
    public Domain[] findDomainsForSponsor(long sponsorId) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Finds the first HostingSlot in the hosting sequence for the specified game that is assigned the specified domain and has not yet been completed by the specified player.
 * 
 * 
 * @return 
 * @param gameId 
 * @param playerId 
 * @param domain 
 */
    public HostingSlot findSlotForDomain(long gameId, long playerId, String domain) throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
/**
 * Provides information about all ball colors available to the application.
 * 
 * 
 * @return 
 */
    public BallColor[] findAllBallColors() throws java.rmi.RemoteException, com.orpheus.game.GameDataException;
}


