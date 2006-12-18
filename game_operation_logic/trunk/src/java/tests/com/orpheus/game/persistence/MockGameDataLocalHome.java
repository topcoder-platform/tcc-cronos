package com.orpheus.game.persistence;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.Handle;
import javax.ejb.RemoveException;

import com.orpheus.game.GameDataException;
import com.orpheus.game.persistence.entities.BallColorImpl;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import com.orpheus.game.persistence.entities.GameImpl;
import com.orpheus.game.persistence.entities.HostingBlockImpl;
import com.orpheus.game.persistence.entities.HostingSlotImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.orpheus.game.persistence.entities.SlotCompletionImpl;
import com.topcoder.util.puzzle.MockPuzzleData;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.web.frontcontroller.results.DownloadData;

public class MockGameDataLocalHome implements GameDataLocalHome {

	public static final long[] GAME_REGISTRATIONS = new long[] { 1, 2, 3, 4 };

	private HostingSlot[] slots;

	public Game createGame(Game game) {
		return null;
	}

	public HostingSlot[] createSlots(long blockId, long[] bidIds) {
		return null;
	}

	public Domain createDomain(Domain domain) {
		return null;
	}

	public HostingBlock addBlock(long gameId, int slotMaxHostingTime) {
		return null;
	}

	public Game getGame(long gameId) {
		// Initialize a Game
		// Image Info
		ImageInfo[] images = new ImageInfo[] { new ImageInfoImpl(new Long(2),
				0, "img1", Boolean.TRUE) };
		// Domain Targets
		DomainTarget[] domainTargets = new DomainTarget[] { new DomainTargetImpl(
				new Long(5), 0, "www.topcoder.com/game", "id", "abc", 0) };
		// Hosting Slot
		Domain domain = new DomainImpl(new Long(7), 0, "topcoder",
				Boolean.TRUE, images);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -30);
		Date hostingStart = calendar.getTime();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 30);
		Date hostingEnd = calendar.getTime();
		int winningBid = 1000;
		slots = new HostingSlotImpl[] { new HostingSlotImpl(new Long(17),
				domain, 0, new long[] { 0 }, new Long(19), 0, domainTargets,
				winningBid, hostingStart, hostingEnd) };
		// Hosting Block
		HostingBlock[] blocks = new HostingBlock[] { new HostingBlockImpl(
				new Long(13), 0, slots, 86400) };
		// Game
		return new GameImpl(new Long(gameId), new BallColorImpl(new Long(1985),
				"testBall", 0), 0, new Date(), new Date(), blocks);
	}

	public HostingBlock getBlock(long blockId) {
		return null;
	}

	public HostingSlot getSlot(long slotId) {
		return slots[0];
	}

	public Domain getDomain(long domainId) {
		return null;
	}

	public String[] getKeysForPlayer(long playerId, long[] slotIds) {
		return new String[] { "1" };
	}

	public PuzzleData getPuzzle(long puzzleId) {
		return new MockPuzzleData();
	}

	public void recordPluginDownload(String pluginName) {

	}

	public void recordRegistration(long playerId, long gameId) {

	}

	public SlotCompletion recordSlotCompletion(long playerId, long slotId,
			Date date) {
		return new SlotCompletionImpl(playerId, slotId, date,
				"TestSlotCompletion", 0);
	}

	public void recordGameCompletion(long playerId, long gameId) {

	}

	public long recordBinaryObject(String name, String mediaType, byte[] content) {
		return 0;
	}

	public HostingSlot[] updateSlots(HostingSlot[] slots) {
		return null;
	}

	public void updateDomain(Domain domain) {

	}

	public void deleteSlot(long slotId) {

	}

	public Domain[] findActiveDomains() {
		return null;
	}

	public Game[] findGamesByDomain(String domain, long playerId) {
		return null;
	}

	public HostingSlot[] findCompletedSlots(long gameId) {
		return slots;
	}

	public SlotCompletion[] findSlotCompletingPlayers(long gameId, long slotId)
			throws RemoteException, GameDataException {
		return null;
	}

	public Game[] findGames(Boolean isStarted, Boolean isEnded) {
		return null;
	}

	public long[] findGameRegistrations(long playerId) {

		return GAME_REGISTRATIONS;
	}

	public Domain[] findDomainsForSponsor(long sponsorId) {
		return null;
	}

	public HostingSlot findSlotForDomain(long gameId, long playerId,
			String domain) {
		return null;
	}

	public BallColor[] findAllBallColors() {
		return null;
	}

	public Object getPrimaryKey() {
		return null;
	}

	public Handle getHandle() throws RemoteException {
		return null;
	}

	public SlotCompletion[] findSlotCompletions(long l1, long l2) {
		return new SlotCompletion[0];
	}

	public DownloadData getDownloadData(long downloadId) {
		return null;
	}

	public GameDataLocal create() throws CreateException {
		return new MockGameDataLocal();
	}

	public void remove(Object arg0) throws RemoveException, EJBException {
	}
}
