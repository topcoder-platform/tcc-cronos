/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import java.util.Date;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.orpheus.administration.entities.DomainTargetImpl;
import com.orpheus.administration.entities.GameImpl;
import com.orpheus.administration.entities.HostingBlockImpl;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.DuplicateEntryException;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.web.frontcontroller.results.DownloadData;

/**
 * <p>
 * A dummy class of <code>AdminDataBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGameDataBean implements SessionBean {
    /**
     * The slots of block.
     */
    private static HostingSlot[] slots = null;

    /**
     * A flag to check if slot is deleted.
     */
    private static boolean isDeleted = false;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public MockGameDataBean() {
    }

    /**
     * <p>
     * Sets the session context. This is an empty implementation.
     * </p>
     *
     * @param sessionContext
     *            session context
     */
    public void setSessionContext(SessionContext sessionContext) {
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
     * Creates a new game entity in the persistent store, along with associated
     * hosting blocks. Any game or block IDs that are null will be automatically
     * assigned acceptable values. No hosting slots are created for the game at
     * this time. The returned Game object will represent the persisted data,
     * including any IDs assigned to the game and blocks. Uses the
     * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
     *
     * @return the game with the id
     * @param game
     *            the game
     * @throws IllegalArgumentException
     *             If game is null
     */
    public Game createGame(Game game) {
        return null;
    }

    /**
     * Creates hosting slots associates with the specified Bid IDs in the
     * specified hosting block Uses the GameDataDAOFActory to obtain the
     * GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.createSlots(blockId,
     * bidIds):HostingSlot[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @return array of hosting slots
     * @param blockId
     *            the block id
     * @param bidIds
     *            the bid ids
     * @throws IllegalEntryException
     *             If any bidId does not belong to the blockId
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If bidIds is null
     */
    public HostingSlot[] createSlots(long blockId, long[] bidIds) {
        return null;
    }

    /**
     * Creates a new persistent domain representation with the data from the
     * provided Domain object and its nested ImageInfo objects. Any null Domain
     * or ImageIndo IDs are assigned appropriate values. The returned Domain
     * will reflect the persistent representation, including any automatically
     * assigned IDs. Uses the GameDataDAOFActory to obtain the GameDataDAO to
     * perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.createDomain(domain):Domain</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public Domain createDomain(Domain domain) {
        return null;
    }

    /**
     * Creates a new, persistent hosting block for the specified game. The block
     * will having an auto-assigned ID, the next available sequence number after
     * those of the game's existing blocks (or 1 if there are no other blocks),
     * no hosting slots, and the specified maximum hosting time per slot. It
     * returns a HostingBlock object representing the new block. Uses the
     * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.addBlock(gameId,
     * slotMaxHostingTime):HostingBlock</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public HostingBlock addBlock(long gameId, int slotMaxHostingTime) {
        return null;
    }

    /**
     * Retrieves a Game object representing the Game having the specified ID.
     * Uses the GameDataDAOFActory to obtain the GameDataDAO to perform this
     * action.
     *
     * @return the game
     * @param gameId
     *            the game id
     * @throws EntryNotFoundException
     *             If gameId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public Game getGame(long gameId) throws EntryNotFoundException,
        PersistenceException {
        // create a new GameImpl
        if (gameId == 1) {
            // for accuracy test
            GameImpl ret = new GameImpl();
            HostingSlotImpl slot1 = new HostingSlotImpl();
            HostingSlotImpl slot2 = new HostingSlotImpl();
            HostingSlotImpl slot3 = new HostingSlotImpl();
            slot1.setId(new Long(1));
            slot2.setId(new Long(2));
            slot3.setId(new Long(3));
            slot1.setSequenceNumber(1);
            slot2.setSequenceNumber(2);
            slot3.setSequenceNumber(3);
            Date now = new Date();
            slot1.setHostingStart(new Date(now.getTime() + 80000));
            slot1.setHostingEnd(new Date(now.getTime() + 90000));
            slot2.setHostingStart(new Date(now.getTime() + 80000));
            slot2.setHostingEnd(new Date(now.getTime() + 90000));
            slot3.setHostingStart(new Date(now.getTime() + 80000));
            slot3.setHostingEnd(new Date(now.getTime() + 90000));
            HostingBlockImpl block = new HostingBlockImpl();
            block.setId(new Long(gameId));
            block.setSlots(new HostingSlot[] {slot1, slot2, slot3});
            ret.setBlocks(new HostingBlock[] {block});
            return ret;
        } else if (gameId == 2) {
            // for DeleteSlotHandler
            GameImpl ret = new GameImpl();
            HostingSlotImpl slot1 = new HostingSlotImpl();
            HostingSlotImpl slot2 = new HostingSlotImpl();
            HostingSlotImpl slot3 = new HostingSlotImpl();
            slot1.setId(new Long(1));
            slot2.setId(new Long(2));
            slot3.setId(new Long(3));
            slot1.setSequenceNumber(1);
            slot2.setSequenceNumber(2);
            slot3.setSequenceNumber(3);
            Date now = new Date();
            slot1.setHostingStart(new Date(now.getTime() + 80000));
            slot1.setHostingEnd(new Date(now.getTime() + 90000));
            slot2.setHostingStart(new Date(now.getTime() - 80000));
            slot2.setHostingEnd(new Date(now.getTime() + 90000));
            slot3.setHostingStart(new Date(now.getTime() + 80000));
            slot3.setHostingEnd(new Date(now.getTime() - 90000));
            HostingBlockImpl block = new HostingBlockImpl();
            block.setId(new Long(gameId));
            block.setSlots(new HostingSlot[] {slot1, slot2, slot3});
            ret.setBlocks(new HostingBlock[] {block});
            return ret;
        } else {
            throw new EntryNotFoundException("", new Long(gameId));
        }
    }

    /**
     * Retrieves a HostingBlock object representing the hosting block having the
     * specified ID. Uses the GameDataDAOFActory to obtain the GameDataDAO to
     * perform this action.
     *
     * @return the block
     * @param blockId
     *            the block id
     * @throws EntryNotFoundException
     *             If blockId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public HostingBlock getBlock(long blockId) {
        HostingSlotImpl slot1 = new HostingSlotImpl();
        HostingSlotImpl slot2 = new HostingSlotImpl();
        HostingSlotImpl slot3 = new HostingSlotImpl();
        slot1.setId(new Long(1));
        slot2.setId(new Long(2));
        slot3.setId(new Long(3));
        slot1.setSequenceNumber(1);
        slot2.setSequenceNumber(2);
        slot3.setSequenceNumber(3);
        Date now = new Date();
        slot1.setHostingStart(new Date(now.getTime() + 80000));
        slot1.setHostingEnd(new Date(now.getTime() + 90000));
        slot2.setHostingStart(new Date(now.getTime() + 80000));
        slot2.setHostingEnd(new Date(now.getTime() + 90000));
        slot3.setHostingStart(new Date(now.getTime() + 80000));
        slot3.setHostingEnd(new Date(now.getTime() + 90000));
        HostingBlockImpl block = new HostingBlockImpl();
        block.setId(new Long(blockId));
        block.setSlots(new HostingSlot[] {slot1, slot2, slot3});

        return block;
    }

    /**
     * Retrieves a HostingSlot object representing the slot having the specified
     * ID. Uses the GameDataDAOFActory to obtain the GameDataDAO to perform this
     * action.
     *
     * @return the slot
     * @param slotId
     *            the slot id
     * @throws EntryNotFoundException
     *             If slotId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public HostingSlot getSlot(long slotId) {
        if (slots == null || slots.length == 0) {
            HostingSlotImpl hostingSlotImpl = new HostingSlotImpl();
            hostingSlotImpl.setId(new Long(slotId));
            hostingSlotImpl
                    .setHostingEnd(new Date(new Date().getTime() + 10000));
            hostingSlotImpl.setHostingStart(new Date(
                    new Date().getTime() + 20000));
            DomainTargetImpl domainTargetImpl = new DomainTargetImpl();
            domainTargetImpl.setIdentifierText("Identifier Text");
            hostingSlotImpl
                    .setDomainTargets(new DomainTarget[] {domainTargetImpl});
            DomainImpl domain = new DomainImpl();
            domain.setDomainName(TestHelper.DOMAIN_NAME);
            hostingSlotImpl.setDomain(domain);
            return hostingSlotImpl;
        } else {
            if (slots[0].getDomainTargets() == null
                    || slots[0].getDomainTargets()[0] == null) {
                DomainTargetImpl domainTargetImpl = new DomainTargetImpl();
                domainTargetImpl.setIdentifierText("Identifier Text");
                ((HostingSlotImpl) slots[0])
                        .setDomainTargets(new DomainTargetImpl[] {domainTargetImpl});
            }
            return slots[0];
        }
    }

    /**
     * Retrieves the DownloadData object corresponding to the specified ID. Uses
     * the GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.getDownloadData(id):DownloadData</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @return the download data
     * @param id
     *            the download id
     * @throws EntryNotFoundException
     *             If id is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public DownloadData getDownloadData(long id) {
        return new MockDownloadData();
    }

    /**
     * Retrieves a Domain object representing the domain corresponding to the
     * specified ID. Uses the GameDataDAOFActory to obtain the GameDataDAO to
     * perform this action.
     *
     * @return the domain
     * @param domainId
     *            the domain id
     * @throws EntryNotFoundException
     *             If domainId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public Domain getDomain(long domainId) throws EntryNotFoundException {
        if (domainId == 1) {
            DomainImpl domainImpl = new DomainImpl();
            domainImpl.setId(new Long(domainId));
            domainImpl.setSponsorId(domainId);
            ImageInfoImpl imageInfoImpl = new ImageInfoImpl();
            imageInfoImpl.setId(new Long(domainId));
            domainImpl.setImages(new ImageInfo[] {imageInfoImpl});
            return domainImpl;

        } else {
            throw new EntryNotFoundException("EntryNotFoundException",
                    new Long(domainId));
        }
    }

    /**
     * Returns the key text for the specified player's completions of the
     * specified slots. The length of the returned array is the same as the
     * length of the slotIds argument, and their elements correspond: each
     * string in the returned array is the key text associated with the slot
     * completion by the specified player of the slot whose ID appears at the
     * same index in the input slotIds. If the specified player has not
     * completed any particular slot specified among the slot IDs then the
     * corresponding element or the returned array is null. Uses the
     * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
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
     */
    public String[] getKeysForPlayer(long playerId, long[] slotIds) {
        return null;
    }

    /**
     * Retrieves the PuzzleData associated with the specified puzzle ID. Uses
     * the GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     *
     * @return the puzzle data
     * @param puzzleId
     *            the puzzle id
     * @throws EntryNotFoundException
     *             If puzzleId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public PuzzleData getPuzzle(long puzzleId) {
        return null;
    }

    /**
     * Increments the download count for the plugin identified by the specified
     * name. Uses the GameDataDAOFActory to obtain the GameDataDAO to perform
     * this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call gameDataDAO.recordPluginDownload(pluginName)</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @param pluginName
     *            the plugin name
     * @throws EntryNotFoundException
     *             If pluginName is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     * @throws IllegalArgumentException
     *             If pluginName is null/empty
     */
    public void recordPluginDownload(String pluginName) {
        return;
    }

    /**
     * Records the specified player's registration for the specified game. Uses
     * the GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call gameDataDAO.recordRegistration(playerId, gameId)</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public void recordRegistration(long playerId, long gameId) {
        return;
    }

    /**
     * Records the completion of the specified slot by the specified player at
     * the specified date and time, and generates a key for the player to
     * associate with the completion. Uses the GameDataDAOFActory to obtain the
     * GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.recordSlotCompletion(playerId, slotId,
     * Date date):SlotCompletion</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public SlotCompletion recordSlotCompletion(long playerId, long slotId,
            Date date) {
        return null;
    }

    /**
     * Records the fact that the specified player has completed the specified
     * game. Whether or not such a player actually wins the game depends on
     * whether others have already completed the game, and on administrative
     * establishment of winner eligibility. Uses the GameDataDAOFActory to
     * obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call gameDataDAO.recordGameCompletion(playerId, gameId)</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public void recordGameCompletion(long playerId, long gameId) {
        return;
    }

    /**
     * Records a binary object in the database, such as might later be retrieved
     * by the custom DownloadSource. The ID assigned to the binary object is
     * returned. Uses the GameDataDAOFActory to obtain the GameDataDAO to
     * perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.recordBinaryObject(name, mediaType,
     * content):long</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public long recordBinaryObject(String name, String mediaType, byte[] content) {
        return 1;
    }

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
     * hosting slots. Uses the GameDataDAOFActory to obtain the GameDataDAO to
     * perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.updateSlots(slots):HostingSlot[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public HostingSlot[] updateSlots(HostingSlot[] slots) {
        return MockGameDataBean.slots = slots;
    }

    /**
     * Updates the persistent domain information for the specified Domain object
     * to match the Domain object, where the appropriate persistent record is
     * identified by the Domain's ID. Uses the GameDataDAOFActory to obtain the
     * GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call gameDataDAO.updateDomain(domain)</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public void updateDomain(Domain domain) {
        return;
    }

    /**
     * Deletes the hosting slot having the specified ID. Uses the
     * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call gameDataDAO.deleteSlot(slotId)</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @param slotId
     *            slot id
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public void deleteSlot(long slotId) {
        isDeleted = true;
    }

    /**
     * Returns if deleteSlot() method is invoked.
     *
     * @return true if deleted.
     */
    public static boolean isDeleted() {
        try {
            return isDeleted;
        } finally {
            isDeleted = false;
        }
    }

    /**
     * Looks up all distinct domains hosting any slot in any active game, and
     * returns an array of Domain objects representing the results. Uses the
     * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findActiveDomains():Domain[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @return array of active domains
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public Domain[] findActiveDomains() {
        return null;
    }

    /**
     * Looks up all ongoing games in which a domain matching the specified
     * string is a host in a slot that the specified player has not yet
     * completed, and returns an array of all such games. Uses the
     * GameDataDAOFActory to obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findGamesByDomain(domain,
     * playerId):Game[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public Game[] findGamesByDomain(String domain, long playerId) {
        return null;
    }

    /**
     * Looks up all hosting slots completed by any player in the specified game,
     * and returns the results as an array of HostingSlot objects. Returned
     * slots are in ascending order by first completion time, or equivalently,
     * in ascending order by hosting block sequence number and hosting slot
     * sequence number. Uses the GameDataDAOFActory to obtain the GameDataDAO to
     * perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findCompletedSlots(gameId):HostingSlot[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @return array of hosting slots
     * @param gameId
     *            the game id
     * @throws EntryNotFoundException
     *             If gameId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public HostingSlot[] findCompletedSlots(long gameId) {
        return null;
    }

    /**
     * Looks up all recorded completion events that have the specified hosting
     * slot in the specified game. Uses the GameDataDAOFActory to obtain the
     * GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findSlotCompletions(gameId,
     * slotId):SlotCompletion[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public SlotCompletion[] findSlotCompletions(long gameId, long slotId) {
        return null;
    }

    /**
     * Retrieves game information for games meeting the specified game status
     * criteria. Uses the GameDataDAOFActory to obtain the GameDataDAO to
     * perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findGames(isStarted, isEnded):Game[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public Game[] findGames(Boolean isStarted, Boolean isEnded) {
        return null;
    }

    /**
     * Looks up all the games for which the specified player is registered, and
     * returns an array of their IDs. Uses the GameDataDAOFActory to obtain the
     * GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findGameRegistrations(playerId):long[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @return the array of game ids
     * @param playerId
     *            the player id
     * @throws EntryNotFoundException
     *             If playerId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public long[] findGameRegistrations(long playerId) {
        return null;
    }

    /**
     * Looks up all domains associated with the specified sponsor and returns an
     * array of Domain objects representing them. Uses the GameDataDAOFActory to
     * obtain the GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return
     * gameDataDAO.findDomainsForSponsor(sponsorId):Domain[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @return array of domains
     * @param sponsorId
     *            the sponsor id
     * @throws EntryNotFoundException
     *             If sponsorId is not in persistence
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public Domain[] findDomainsForSponsor(long sponsorId) {
        return null;
    }

    /**
     * Finds the first HostingSlot in the hosting sequence for the specified
     * game that is assigned the specified domain and has not yet been completed
     * by the specified player. Uses the GameDataDAOFActory to obtain the
     * GameDataDAO to perform this action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findSlotForDomain(gameId, playerId,
     * domain):HostingSlot</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
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
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId,
            String domain) {
        return null;
    }

    /**
     * Provides information about all ball colors available to the application.
     * Uses the GameDataDAOFActory to obtain the GameDataDAO to perform this
     * action.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: GameDataDAOFactory.getGameDataDAO()</li>
     * <li>Call and return gameDataDAO.findAllBallColors():BallColor[]</li>
     * <li>If exception thrown, call sessionContext.setRollbackOnly()</li>
     * </ul>
     *
     * @return array of ball colors
     * @throws PersistenceException
     *             If there is any problem in the persistence layer.
     */
    public BallColor[] findAllBallColors() {
        return null;
    }

    private class DomainImpl implements Domain {

        private Long id;

        private long sponsorId;

        private String domainName;

        private Boolean approved;

        private ImageInfo[] images;

        public void setApproved(Boolean approved) {
            this.approved = approved;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setImages(ImageInfo[] images) {
            this.images = images;
        }

        public void setSponsorId(long sponsorId) {
            this.sponsorId = sponsorId;
        }

        public Long getId() {
            return id;
        }

        public long getSponsorId() {
            return sponsorId;
        }

        public String getDomainName() {
            return domainName;
        }

        public Boolean isApproved() {
            return approved;
        }

        public ImageInfo[] getImages() {
            return images;
        }
    }

    private class ImageInfoImpl implements ImageInfo {

        private Long id;

        private long downloadId;

        private String description;

        private Boolean approved;

        public Long getId() {
            return id;
        }

        public long getDownloadId() {
            return downloadId;
        }

        public String getDescription() {
            return description;
        }

        public Boolean isApproved() {
            return approved;
        }

        public void setApproved(Boolean approved) {
            this.approved = approved;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setDownloadId(long downloadId) {
            this.downloadId = downloadId;
        }

        public void setId(Long id) {
            this.id = id;
        }

    }

    /**
     * This method returns slots.
     * @return
     */
    public static HostingSlot[] getSlots() {
        return slots;
    }

    /**
     * This method set slots.
     * @return
     */
    public static HostingSlot[] setSlots(HostingSlot[] hostingSlots) {
        return slots = hostingSlots;
    }
}
