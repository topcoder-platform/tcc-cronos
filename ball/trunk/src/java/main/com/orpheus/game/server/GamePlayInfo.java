/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>A helper class maintaining a game play history for a single player. Such an object is put to session when a player
 * is logged to <code>Orpheus</code> browser plugin and collects the data for the events triggered while player is
 * playing the games in context of a single session.</p>
 *
 * @author isv
 * @version 1.0
 */
public class GamePlayInfo implements Serializable {

    /**
     * <p>A <code>Map</code> collecting the details for brainteasers which have been presented to player so far. Maps
     * the <code>Long</code> ID of a hosting slot to an <code>Integer</code> providing the index of latest brainteaser
     * for the slot which has been displayed to player.</p>
     */
    private final Map brainteaserIndexes;

    /**
     * <p>A <code>Map</code> collecting the details for brainteasers which have been presented to player so far. Maps
     * the <code>Long</code> ID of a hosting slot to a <code>Date</code> providing the time when the latest brainteaser
     * for the slot has been displayed to player.</p>
     */
    private final Map brainteaserTimes;

    /**
     * <p>A <code>Map</code> collecting the details for hunt targets which have been found by the players so far. Maps
     * <code>Long</code> ID of hosting slot to <code>LinkedHashSet</code> of <code>Long</code> sequence numbers for hunt
     * targets which have been found so far.</p>
     */
    private final Map foundHuntTargets;

    /**
     * <p>A <code>Map</code> collecting the details for puzzles which have been presented the player so far. Maps
     * <code>String</code> combining ID of game and a slot to <code>Date</code> providing the time when playe had
     * started to solve the <code>Win Game</code> puzzle for the game.</p>
     */
    private final Map puzzlesStartTimes;

    /**
     * <p>A <code>Set</code> holding the <code>Long</code> IDs of the games which the player is currently registered to.
     * </p>
     */
    private final Set registeredGames;

    /**
     * <p>A <code>Set</code> holding the <code>Long</code> IDs of the games which the player has already completed.</p>
     */
    private final Set completedGames;

    /**
     * <p>Constructs new <code>GamePlayInfo</code> instance with empty game play statistics.</p>
     */
    public GamePlayInfo() {
        this.brainteaserIndexes = new HashMap();
        this.brainteaserTimes = new HashMap();
        this.foundHuntTargets = new HashMap();
        this.puzzlesStartTimes = new HashMap();
        this.registeredGames = new HashSet();
        this.completedGames = new HashSet();
    }

    /**
     * <p>Checks </p>
     * 
     * @param hostingSlot a <code>HostingSlot</code> representing the hosting slot which currently hosts the ball. 
     * @return <code>true</code> if a brainteaser for specified host hasn't been displayed to player yet; <code>false
     *         </code> otherwise.
     */
    public boolean isFirstBrainteaserRequest(HostingSlot hostingSlot) {
        synchronized (this.brainteaserIndexes) {
            return !this.brainteaserIndexes.containsKey(hostingSlot.getId());
        }
    }

    /**
     * <p>Gets the time of displayng the latest brainteaser from a serie which has been displayed to player for
     * specified slot.</p>
     *
     * @param hostingSlot a <code>HostingSlot</code> representing a hosting slot which the brainteaser has been
     *        displayed for.
     * @return a <code>Date</code> providing the time of displaying the latest brainteaser from a serie which has been
     *         displayed to player for specified hosting slot.
     * @throws IllegalArgumentException if there were no brainteaser for specified hosting slot displayed to player.
     */
    public Date getLastBrainteaserUpdateTime(HostingSlot hostingSlot) {
        synchronized (this.brainteaserTimes) {
            Date time = (Date) this.brainteaserTimes.get(hostingSlot.getId());
            if (time != null) {
                return time;
            } else {
                throw new IllegalArgumentException("There were no brainteasers displayed to player for hosting slot ["
                                                   + hostingSlot.getId() + "]");
            }
        }
    }

    /**
     * <p>Gets the index of latest brainteaser from a serie which has been displayed to player for specified slot.</p>
     *
     * @param hostingSlot a <code>HostingSlot</code> representing a hosting slot which the brainteaser has been
     *        displayed for.
     * @return an <code>int</code> providing the index of latest brainteaser from a serie which has been displayed to
     *         player for specified hosting slot.
     * @throws IllegalArgumentException if there were no brainteaser for specified hosting slot displayed to player.
     */
    public int getLastBrainteaserIndex(HostingSlot hostingSlot) {
        synchronized (this.brainteaserIndexes) {
            Integer index = (Integer) this.brainteaserIndexes.get(hostingSlot.getId());
            if (index != null) {
                return index.intValue();
            } else {
                throw new IllegalArgumentException("There were no brainteasers displayed to player for hosting slot ["
                                                   + hostingSlot.getId() + "]");
            }
        }
    }

    /**
     * <p>Saves the details for the latest brainteaser associated with specified hosting slot which has been displayed
     * to player.</p>
     *
     * @param hostingSlot a <code>HostingSlot</code> representing a hosting slot which the brainteaser has been
     *        displayed for. 
     * @param brainteaserIndex an <code>int</code> providing the index of latest brainteaser from a serie which has been
     *        displayed to player.
     * @param brainteaserUpdateDate a <code>Date</code> providing the time when the latest brainteaser from a serie has
     *        been displayed to player.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code> or if specified <code>
     *         brainteaserIndex</code> is negative. 
     */
    public void setLastDisplayedBrainteaser(HostingSlot hostingSlot, int brainteaserIndex, Date brainteaserUpdateDate) {
        if (hostingSlot == null) {
            throw new IllegalArgumentException("The parameter [hostingSlot] is NULL");
        }
        if (brainteaserUpdateDate == null) {
            throw new IllegalArgumentException("The parameter [brainteaserUpdateDate] is NULL");
        }
        if (brainteaserIndex < 0) {
            throw new IllegalArgumentException("The parameter [brainteaserIndex] is negative");
        }
        synchronized (this.brainteaserIndexes) {
            this.brainteaserIndexes.put(hostingSlot.getId(), new Integer(brainteaserIndex));
            this.brainteaserTimes.put(hostingSlot.getId(), brainteaserUpdateDate);
        }
    }

    /**
     * <p>Gets the next target available for hunt by the player in context of specified hosting slot.</p>
     *
     * @param hostingSlot a <code>HostingSlot</code> representing a hosting slot to get next available hunt target for.
     * @return a <code>DomainTarget</code> representing a next available hunt target for specified slot or <code>null
     *         </code> if there are no undiscovered domain targets left for that slot.    
     * @throws IllegalArgumentException if specified <code>hostingSlot</code> is <code>null</code>.
     */
    public DomainTarget getNextHuntTarget(HostingSlot hostingSlot) {
        if (hostingSlot == null) {
            throw new IllegalArgumentException("The parameter [hostingSlot] is NULL");
        }
        DomainTarget[] targets = hostingSlot.getDomainTargets();
        for (int i = 0; i < targets.length; i++) {
            if (!isHuntTargetFound(hostingSlot, targets[i])) {
                return targets[i];
            }
        }
        return null;
    }

    /**
     * <p>Records the fact of successful resolution of the hunt target identified by the specified sequence number for
     * specified hosting slot.</p>
     *
     * @param hostingSlot a <code>HostingSlot</code> representing a hosting slot specifying the context for game play.
     * @param sequenceNumber a <code>long</code> providing the sequence number for discovered hunt target.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code> or specified <code>
     *         sequenceNumber</code> is negative or there are no hunt targets with specified sequence number available
     *         for specified slot.
     */
    public void recordHuntTargetResolution(HostingSlot hostingSlot, long sequenceNumber) {
        if (hostingSlot == null) {
            throw new IllegalArgumentException("The parameter [hostingSlot] is NULL");
        }
        if (sequenceNumber < 0) {
            throw new IllegalArgumentException("The parameter [sequenceNumber] is negative");
        }
        boolean sequenceNumberValid = false;
        DomainTarget[] targets = hostingSlot.getDomainTargets();
        for (int i = 0; !sequenceNumberValid && (i < targets.length) ; i++) {
            if (targets[i].getSequenceNumber() == sequenceNumber) {
                sequenceNumberValid = true;
            }
        }
        if (!sequenceNumberValid) {
            throw new IllegalArgumentException("The hunt target with sequence number [" + sequenceNumber + "] is not "
                                               + "found for hsoting slot [" + hostingSlot.getId() +"]");
        }
        synchronized (this.foundHuntTargets) {
            LinkedHashSet foundTargets = (LinkedHashSet) this.foundHuntTargets.get(hostingSlot.getId());
            if (foundTargets == null) {
                foundTargets = new LinkedHashSet();
                this.foundHuntTargets.put(hostingSlot.getId(), foundTargets);
            }
            foundTargets.add(new Long(sequenceNumber));
        }
    }

    /**
     * <p>Checks if player has found all hunt targets which have been set for specified hosting slot.</p>
     *
     * @param hostingSlot a <code>HostingSlot</code> representing a hosting slot specifying the context for game play.
     * @return <code>true</code> if player has found all hunt targets for specified slot; <code>false</code> otherwise.
     * @throws IllegalArgumentException if specified <code>hostingSlot</code> is <code>null</code>.
     */
    public boolean hasFoundAllHuntTargets(HostingSlot hostingSlot) {
        if (hostingSlot == null) {
            throw new IllegalArgumentException("The parameter [hostingSlot] is NULL");
        }
        DomainTarget[] targets = hostingSlot.getDomainTargets();
        synchronized (this.foundHuntTargets) {
            LinkedHashSet foundTargets = (LinkedHashSet) this.foundHuntTargets.get(hostingSlot.getId());
            if (foundTargets != null) {
                // The statement is commented out as in the future the hunt targets may be deleted from the slot
                // by administrator after the player has already found the target
//                if (foundTargets.size() == targets.length) {
                    for (int i = 0; i < targets.length; i++) {
                        if (!foundTargets.contains(new Long(targets[i].getSequenceNumber()))) {
                            return false;
                        }
                    }
                    return true;
//                }
            }
        }
        return false;
    }

    /**
     * <p>Records the specified time when the player had started to solve the puzzle for winning the specified game.</p>
     *
     * @param gameId a <code>long</code> providing the ID of a game.
     * @param slotId a <code>long</code> providing the ID of a current slot which has been unlocked by the player.
     * @param date a <code>Date</code> providing the time whne the player had started to solve the puzzle.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>. 
     */
    public void recordWinGamePuzzleStart(long gameId, long slotId, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The parameter [date] is NULL");
        }
        synchronized (this.puzzlesStartTimes) {
            this.puzzlesStartTimes.put(gameId + "," + slotId, date);
        }
    }

    /**
     * <p>Gets the time when a player started to solve the puzzle for specified game.</p>
     *
     * @param gameId a <code>long</code> providing the ID of a game.
     * @param slotId a <code>long</code> providing the ID of a current slot which has been unlocked by the player.
     * @return a <code>Date</code> providing the time when the player has started to solve puzzle for a game or <code>
     *         null</code> if playe was not presented with a puzzle for requested game.  
     */
    public Date getWinGamePuzzleStartTime(long gameId, long slotId) {
        synchronized (this.puzzlesStartTimes) {
            return (Date) this.puzzlesStartTimes.get(gameId + "," + slotId);
        }
    }

    /**
     * <p>Checks if the player is registered to specified game.</p>
     *
     * @param gameId a <code>Long</code> providing the ID of requested game.
     * @return <code>true</code> if the player is already registered to specified game; <code>false</code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public boolean isRegisteredToGame(Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("The parameter [gameId] is NULL");
        }
        synchronized (this.registeredGames) {
            return this.registeredGames.contains(gameId);
        }
    }

    /**
     * <p>Sets the list of games which the player is currently registered to. The previous list of registered games is
     * cleared.</p>
     *
     * @param registeredGameIds a <code>long</code> array providing the IDs for the games which the player is currently
     *        registered to.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void setRegisteredGames(long[] registeredGameIds) {
        if (registeredGameIds == null) {
            throw new IllegalArgumentException("The parameter [registeredGameIds] is NULL");
        }
        synchronized (this.registeredGames) {
            this.registeredGames.clear();
            for (int i = 0; i < registeredGameIds.length; i++) {
                this.registeredGames.add(new Long(registeredGameIds[i]));
            }
        }
    }

    /**
     * <p>Checks if the player has already completed the specified game.</p>
     *
     * @param gameId a <code>Long</code> providing the ID of requested game.
     * @return <code>true</code> if the player has already completed the specified game; <code>false</code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public boolean isGameCompleted(Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("The parameter [gameId] is NULL");
        }
        synchronized (this.completedGames) {
            return this.completedGames.contains(gameId);
        }
    }

    /**
     * <p>Records the fact of completion of the game by the player.</p>
     *
     * @param gameId a <code>Long</code> providing the ID of requested game.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void recordGameCompletion(Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("The parameter [gameId] is NULL");
        }
        synchronized (this.completedGames) {
            this.completedGames.add(gameId);
        }
    }

    /**
     * <p>Sets the list of games which the player has already completed. The previous list of completed games is
     * cleared.</p>
     *
     * @param completedGameIds a <code>long</code> array providing the IDs for the games which the player has already
     *        completed.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void setCompletedGames(long[] completedGameIds) {
        if (completedGameIds == null) {
            throw new IllegalArgumentException("The parameter [completedGameIds] is NULL");
        }
        synchronized (this.completedGames) {
            this.completedGames.clear();
            for (int i = 0; i < completedGameIds.length; i++) {
                this.completedGames.add(new Long(completedGameIds[i]));
            }
        }
    }

    /**
     * <p>Checks if the specified hunt target has already been found by the player in context of specified hosting slot.
     * </p>
     *
     * @param slot a <code>HostingSlot</code> representing a hosting slot specifying the context for game play.
     * @param target a <code>DomainTarget</code> representing a hunt target to check. 
     * @return <code>true</code> if specified hunt target has been found by the player in context of specified slot;
     *         <code>false</code> otherwise.
     */
    private boolean isHuntTargetFound(HostingSlot slot, DomainTarget target) {
        synchronized (this.foundHuntTargets) {
            LinkedHashSet foundTargets = (LinkedHashSet) this.foundHuntTargets.get(slot.getId());
            return (foundTargets != null) && (foundTargets.contains(new Long(target.getSequenceNumber())));
        }
    }
}
