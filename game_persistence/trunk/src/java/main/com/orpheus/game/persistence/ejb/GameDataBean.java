/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.ejb;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DuplicateEntryException;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameDataDAO;
import com.orpheus.game.persistence.GameDataDAOFactory;
import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.InstantiationException;
import com.orpheus.game.persistence.InvalidEntryException;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;

import com.topcoder.util.puzzle.PuzzleData;

import com.topcoder.web.frontcontroller.results.DownloadData;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * <p>
 * The EJB that handles the actual client requests. It accepts all client operations, but simply delegates all
 * operations to the GameDataDAO it obtains from the GameDataDAOFactory.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b>This object is mutable and thread-safe, as the container handles this.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class GameDataBean implements SessionBean {
    /**
     * <p>
     * Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs. Set in the
     * setSessionContext() method by the container.
     * </p>
     */
    private SessionContext sessionContext = null;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public GameDataBean() {
    }

    /**
     * <p>
     * Creates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbCreate() {
    }

    /**
     * <p>
     * Removes the bean. This is an empty implementation.
     * </p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>
     * Sets the session context.
     * </p>
     *
     * @param ctx The session context
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

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
     * @throws EntryNotFoundException If game.ballColor.id is not found in persistence
     * @throws DuplicateEntryException If game.id is not null but already exists in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If game is null
     */
    public Game createGame(Game game) throws PersistenceException {
        Helper.checkNotNull(game, "Game");

        try {
            return getDAO().createGame(game);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } 
    }

    /**
     * <p>
     * Creates hosting slots associates with the specified Bid IDs in the specified hosting block Uses the
     * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * </p>
     *
     * @param blockId the block id
     * @param bidIds the bid ids
     *
     * @return array of hosting slots
     *
     * @throws EntryNotFoundException If blockId or any bidId doesn't exist in the persistence
     * @throws IllegalEntryException If any bidId does not belong to the blockId
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If bidIds is null
     */
    public HostingSlot[] createSlots(long blockId, long[] bidIds)
        throws PersistenceException {
        Helper.checkNotNull(bidIds, "bidIds");

        try {
            return getDAO().createSlots(blockId, bidIds);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } 
    }

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
     * @throws EntryNotFoundException If imageInfo.downloadId doesn't exist in persistence
     * @throws DuplicateEntryException If id or imageInfo.id is not null but already exists in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null
     */
    public Domain createDomain(Domain domain) throws PersistenceException {
        Helper.checkNotNull(domain, "Domain");

        try {
            return getDAO().createDomain(domain);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } 
    }

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
     * @throws EntryNotFoundException If gameId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingBlock addBlock(long gameId, int slotMaxHostingTime)
        throws PersistenceException {
        try {
            return getDAO().addBlock(gameId, slotMaxHostingTime);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } 
    }

    /**
     * <p>
     * Retrieves a Game object representing the Game having the specified ID.
     * </p>
     *
     * @param gameId the game id
     *
     * @return the game
     *
     * @throws EntryNotFoundException If gameId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Game getGame(long gameId) throws PersistenceException {
        try {
            return getDAO().getGame(gameId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a HostingBlock object representing the hosting block having the specified ID.
     * </p>
     *
     * @param blockId the block id
     *
     * @return the block
     *
     * @throws EntryNotFoundException If blockId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingBlock getBlock(long blockId) throws PersistenceException {
        try {
            return getDAO().getBlock(blockId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a HostingSlot object representing the slot having the specified ID.
     * </p>
     *
     * @param slotId the slot id
     *
     * @return the slot
     *
     * @throws EntryNotFoundException If slotId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingSlot getSlot(long slotId) throws PersistenceException {
        try {
            return getDAO().getSlot(slotId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the DownloadData object corresponding to the specified ID.
     * </p>
     *
     * @param id the download id
     *
     * @return the download data
     *
     * @throws EntryNotFoundException If id is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public DownloadData getDownloadData(long id) throws PersistenceException {
        try {
            return getDAO().getDownloadData(id);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a Domain object representing the domain corresponding to the specified ID.
     * </p>
     *
     * @param domainId the domain id
     *
     * @return the domain
     *
     * @throws EntryNotFoundException If domainId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain getDomain(long domainId) throws PersistenceException {
        try {
            return getDAO().getDomain(domainId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If playerId or any slotId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If slotIds is null
     */
    public String[] getKeysForPlayer(long playerId, long[] slotIds)
        throws PersistenceException {
        Helper.checkNotNull(slotIds, "SlotIds");

        try {
            return getDAO().getKeysForPlayer(playerId, slotIds);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the PuzzleData associated with the specified puzzle ID.
     * </p>
     *
     * @param puzzleId the puzzle id
     *
     * @return the puzzle data
     *
     * @throws EntryNotFoundException If puzzleId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public PuzzleData getPuzzle(long puzzleId) throws PersistenceException {
        try {
            return getDAO().getPuzzle(puzzleId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Increments the download count for the plugin identified by the specified name.
     * </p>
     *
     * @param pluginName the plugin name
     *
     * @throws EntryNotFoundException If pluginName is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If pluginName is null/empty
     */
    public void recordPluginDownload(String pluginName) throws PersistenceException {
        Helper.checkNotNullOrEmpty(pluginName, "PluginName");

        try {
            getDAO().recordPluginDownload(pluginName);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Records the specified player's registration for the specified game.
     * </p>
     *
     * @param playerId the player id
     * @param gameId the game id
     *
     * @throws EntryNotFoundException If playerID or gameId is not in persistence
     * @throws DuplicateEntryException If the player has been already registered for this game
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void recordRegistration(long playerId, long gameId)
        throws PersistenceException {
        try {
            getDAO().recordRegistration(playerId, gameId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If playerID or slotId is not in persistence
     * @throws DuplicateEntryException If the slot has already been completed for this player
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If date is null
     */
    public SlotCompletion recordSlotCompletion(long playerId, long slotId, java.util.Date date)
        throws PersistenceException {
        Helper.checkNotNull(date, "Date");

        try {
            return getDAO().recordSlotCompletion(playerId, slotId, date);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If playerID or gameId is not in persistence
     * @throws DuplicateEntryException If the player has been already registered for this game
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void recordGameCompletion(long playerId, long gameId)
        throws PersistenceException {
        try {
            getDAO().recordGameCompletion(playerId, gameId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If name or mediaType is null/empty, or content is null
     */
    public long recordBinaryObject(String name, String mediaType, byte[] content)
        throws PersistenceException {
        Helper.checkNotNullOrEmpty(name, "name");
        Helper.checkNotNullOrEmpty(mediaType, "mediaType");
        Helper.checkNotNull(content, "content");

        try {
            return getDAO().recordBinaryObject(name, mediaType, content);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If HsotingSlot.id or DomainTarget.id, if not null, is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If slots is null
     */
    public HostingSlot[] updateSlots(HostingSlot[] slots) throws PersistenceException {
        Helper.checkNotNull(slots, "Slots");

        try {
            return getDAO().updateSlots(slots);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Updates the persistent domain information for the specified Domain object to match the Domain object, where
     * the appropriate persistent record is identified by the Domain's ID.
     * </p>
     *
     * @param domain domain to update
     *
     * @throws EntryNotFoundException If Domain.id or ImageInfo.id, if not null, is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null
     */
    public void updateDomain(Domain domain) throws PersistenceException {
        Helper.checkNotNull(domain,"domain");
        try {
            getDAO().updateDomain(domain);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Deletes the hosting slot having the specified ID.
     * </p>
     *
     * @param slotId slot id
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void deleteSlot(long slotId) throws PersistenceException {
        try {
            getDAO().deleteSlot(slotId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Looks up all distinct domains hosting any slot in any active game, and returns an array of Domain objects
     * representing the results.
     * </p>
     *
     * @return array of active domains
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain[] findActiveDomains() throws PersistenceException {
        try {
            return getDAO().findActiveDomains();
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If there is no domain with the given name, or no player with such an id in
     *         persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null/empty
     */
    public Game[] findGamesByDomain(String domain, long playerId)
        throws PersistenceException {
        Helper.checkNotNullOrEmpty(domain, "domain Name");

        try {
            return getDAO().findGamesByDomain(domain, playerId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If gameId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingSlot[] findCompletedSlots(long gameId) throws PersistenceException {
        try {
            return getDAO().findCompletedSlots(gameId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If gameId or slotId is not in persistence
     * @throws InvalidEntryException If slotId is not part of the game indicated by gameId
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public SlotCompletion[] findSlotCompletions(long gameId, long slotId)
        throws PersistenceException {
        try {
            return getDAO().findSlotCompletions(gameId, slotId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If isStarted == false and isEnded == true (impossible combo)
     */
    public Game[] findGames(Boolean isStarted, Boolean isEnded)
        throws PersistenceException {
        if ((isStarted != null) && (isEnded != null) && !isStarted.booleanValue() && isEnded.booleanValue()) {
            throw new IllegalArgumentException("Not started but already ended. Impossible Combo.");
        }

        try {
            return getDAO().findGames(isStarted, isEnded);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Looks up all the games for which the specified player is registered, and returns an array of their IDs.
     * </p>
     *
     * @param playerId the player id
     *
     * @return the array of game ids
     *
     * @throws EntryNotFoundException If playerId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public long[] findGameRegistrations(long playerId) throws PersistenceException {
        try {
            return getDAO().findGameRegistrations(playerId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If sponsorId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain[] findDomainsForSponsor(long sponsorId) throws PersistenceException {
        try {
            return getDAO().findDomainsForSponsor(sponsorId);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
     * @throws EntryNotFoundException If there is no domain with the given name, or no player or game with such an id
     *         in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null/empty
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId, String domain)
        throws PersistenceException {
        Helper.checkNotNullOrEmpty(domain, "domainName");

        try {
            return getDAO().findSlotForDomain(gameId, playerId, domain);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Provides information about all ball colors available to the application.
     * </p>
     *
     * @return array of ball colors
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public BallColor[] findAllBallColors() throws PersistenceException {
        try {
            return getDAO().findAllBallColors();
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } 
    }
    
    /**
     * <p>Helper class to get the GameDataDAO from GameDataDAOFactory.</p>
     * @return GameDataDAO instance
     * @throws PersistenceException fail to create GameDataDAO instance from GameDataDAOFactory
     */
    private GameDataDAO getDAO()throws PersistenceException{
        try {
            return GameDataDAOFactory.getGameDataDAO();
        } catch (InstantiationException e) {
            throw new PersistenceException("Fail to initantiate the GameDataDAO instance through factory.", e);
        }
    }
}
