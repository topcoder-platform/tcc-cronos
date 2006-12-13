/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import java.util.Date;

import javax.ejb.SessionBean;

import com.orpheus.administration.entities.DomainTargetImpl;
import com.orpheus.administration.entities.HostingBlockImpl;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.PersistenceException;
import com.topcoder.web.frontcontroller.results.DownloadData;

/**
 * Dummy GameDataBean, used for providing data for testing purpose.
 * <p>
 * <strong>Thread Safety</strong></p>
 * <p>This object is immutable and thread-safe</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DummyGameDataBean implements SessionBean {
    /**
     * Dummy implemention.
     * @return array of games
     * @param domain the domain
     * @param playerId the player id
     * @throws EntryNotFoundException If there is no domain with the given name, or no player with such an id in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null/empty
     */
    public Game[] findGamesByDomain(String domain, long playerId)
        throws EntryNotFoundException, PersistenceException {
            return null;
    }

    /**
     * Dummy implemention.
     * @return array of domains
     * @param sponsorId the sponsor id
     * @throws EntryNotFoundException If sponsorId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain[] findDomainsForSponsor(long sponsorId)
        throws EntryNotFoundException, PersistenceException {
        if (sponsorId == 1001) {
            return new Domain[] {new MockDomain(1001)};    
        } else {
            throw new EntryNotFoundException("entry does not exist", new Long(sponsorId));    
        }
    }

    /**
     * Retrieves a Domain object representing the domain
     * corresponding to the specified ID
     * 
     * 
     * @return the domain
     * @param domainId the domain id
     * @throws EntryNotFoundException If domainId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain getDomain(long domainId) throws EntryNotFoundException,
        PersistenceException {
        MockDomain ret = null;
        if (domainId == 1001) {
            ret = new MockDomain(domainId);
            ret.setSponsorId(domainId);
            ret.setImages(new MockImageInfo[] {new MockImageInfo(new Long(domainId))});
        } else {
            throw new EntryNotFoundException("EntryNotFoundException", new Long(domainId));   
        }
        
        return ret;
    }

    /**
     * Retrieves a Game object representing the Game having the
     * specified ID
     * 
     * 
     * @return the game
     * @param gameId the game id
     * @throws EntryNotFoundException If gameId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Game getGame(long gameId) throws EntryNotFoundException,
        PersistenceException {
        if (gameId == 1001) {
            MockGameImpl ret = new MockGameImpl(gameId);
            HostingSlotImpl slot1 = new HostingSlotImpl();
            HostingSlotImpl slot2 = new HostingSlotImpl();
            HostingSlotImpl slot3 = new HostingSlotImpl();
            slot1.setId(new Long(gameId));
            slot2.setId(new Long(1002));
            slot3.setId(new Long(1003));
            slot1.setSequenceNumber(1);
            slot2.setSequenceNumber(2);
            slot3.setSequenceNumber(3);
            Date now = new Date();
            slot1.setHostingStart(new Date(now.getTime() + 24000));
            slot1.setHostingEnd(new Date(now.getTime() + 36000));
            slot2.setHostingEnd(new Date(now.getTime() + 36000));
            slot2.setHostingStart(new Date(now.getTime() - 36000));
            slot3.setHostingStart(new Date(now.getTime() - 72000));
            slot3.setHostingEnd(new Date(now.getTime() - 36000));
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
     * Updates the persistent hosting slot information for the
     * existing slots represented by the specified HostingSlot
     * objects, so that the persistent representation matches the
     * provided objects. Nested DomainTarget objects may or may not
     * already be recorded in persistence; the component assumes that
     * DomainTarget's with null IDs are new, and that others already
     * exist in the database. The component will assign IDs for new
     * DomainTargets as needed.
     * This method will also update the following additional
     * HostingSlot properties (only): sequence number, hosting start,
     * hosting end, brain teaser IDs, puzzle ID.  It will return an
     * array containing the revised hosting slots.
     * 
     * 
     * @return the updated hosting slots
     * @param slots the hosting slots to update
     * @throws EntryNotFoundException If HsotingSlot.id or DomainTarget.id, if not null, is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If slots is null
     */
    public HostingSlot[] updateSlots(HostingSlot[] slots)
        throws EntryNotFoundException, PersistenceException {
        DataProvider.updateSlots(slots);
        return null;
    }

    /**
     * Deletes the hosting slot having the specified ID
     * 
     * 
     * @param slotId slot id
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public void deleteSlot(long slotId) throws PersistenceException {
        DataProvider.deleteSlot(slotId) ;   
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
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingSlot getSlot(long slotId) throws PersistenceException {
        HostingSlotImpl slot = new HostingSlotImpl();
        MockDomain domain = new MockDomain(slotId);
        domain.setDomainName("http://www.topcoder.com/index.htm");
        DomainTargetImpl domainTarget = new DomainTargetImpl();
        domainTarget.setId(new Long(slotId));
        domainTarget.setIdentifierText("identifier text");
        slot.setDomain(domain);
        slot.setDomainTargets(new DomainTargetImpl[] {domainTarget});
        slot.setId(new Long(slotId));
        slot.setImageId(slotId);
        return slot;
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
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public HostingBlock getBlock(long blockId) throws PersistenceException {
        HostingSlotImpl slot = new HostingSlotImpl();
        MockDomain domain = new MockDomain(blockId);
        domain.setDomainName("http://www.topcoder.com/index.htm");
        slot.setId(new Long(blockId));
        slot.setSequenceNumber(1);
        slot.setDomain(domain);
        Date now = new Date();
        slot.setHostingStart(new Date(now.getTime() + 24000));
        slot.setHostingEnd(new Date(now.getTime() + 36000));
        HostingBlockImpl block = new HostingBlockImpl();
        block.setId(new Long(blockId));
        block.setSlots(new HostingSlot[] {slot});
        return block;
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
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public DownloadData getDownloadData(long id) throws PersistenceException {
        return new MockDownloadData();
    }

    /**
     * <p>Creates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbCreate() {
    }

    /**
     * <p>Removes the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbRemove() {
    }

    /**
     * <p>Activates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbActivate() {
    }

    /**
     * <p>Passivates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbPassivate() {
    }

    /**
     * <p>Sets the session context. This is an empty implementation.</p>
     * 
     * 
     * @param ctx session context
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) {
    }
}
