package com.orpheus.game.persistence;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBMetaData;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
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

public class MockGameDataHome implements GameDataHome {

	public static final long[] GAME_REGISTRATIONS = new long[] { 1, 2, 3, 4 };

	private HostingSlot[] slots;

	public Game createGame(Game game) throws RemoteException {
		return null;
	}

	public HostingSlot[] createSlots(long blockId, long[] bidIds)
			throws RemoteException {
		return null;
	}

	public Domain createDomain(Domain domain) throws RemoteException {
		return null;
	}

	public HostingBlock addBlock(long gameId, int slotMaxHostingTime)
			throws RemoteException {
		return null;
	}

	public Game getGame(long gameId) throws RemoteException {
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

	public HostingBlock getBlock(long blockId) throws RemoteException {
		return null;
	}

	public HostingSlot getSlot(long slotId) throws RemoteException {
		return slots[0];
	}

	public Domain getDomain(long domainId) throws RemoteException {
		return null;
	}

	public String[] getKeysForPlayer(long playerId, long[] slotIds)
			throws RemoteException {
		return new String[] { "1" };
	}

	public PuzzleData getPuzzle(long puzzleId) throws RemoteException {
		return new MockPuzzleData();
	}

	public void recordPluginDownload(String pluginName) throws RemoteException {

	}

	public void recordRegistration(long playerId, long gameId)
			throws RemoteException {

	}

	public SlotCompletion recordSlotCompletion(long playerId, long slotId,
			Date date) throws RemoteException {
		return new SlotCompletionImpl(playerId, slotId, date,
				"TestSlotCompletion", 0);
	}

	public void recordGameCompletion(long playerId, long gameId)
			throws RemoteException {

	}

	public long recordBinaryObject(String name, String mediaType, byte[] content)
			throws RemoteException {
		return 0;
	}

	public HostingSlot[] updateSlots(HostingSlot[] slots)
			throws RemoteException {
		return null;
	}

	public void updateDomain(Domain domain) throws RemoteException {

	}

	public void deleteSlot(long slotId) throws RemoteException {

	}

	public Domain[] findActiveDomains() throws RemoteException {
		return null;
	}

	public Game[] findGamesByDomain(String domain, long playerId)
			throws RemoteException {
		return null;
	}

	public HostingSlot[] findCompletedSlots(long gameId) throws RemoteException {
		return slots;
	}

	public SlotCompletion[] findSlotCompletingPlayers(long gameId, long slotId)
			throws RemoteException, GameDataException {
		return null;
	}

	public Game[] findGames(Boolean isStarted, Boolean isEnded)
			throws RemoteException {
		return null;
	}

	public long[] findGameRegistrations(long playerId) throws RemoteException {

		return GAME_REGISTRATIONS;
	}

	public Domain[] findDomainsForSponsor(long sponsorId)
			throws RemoteException {
		return null;
	}

	public HostingSlot findSlotForDomain(long gameId, long playerId,
			String domain) throws RemoteException {
		return null;
	}

	public BallColor[] findAllBallColors() throws RemoteException {
		return null;
	}

	public void remove() throws RemoteException, RemoveException {

	}

	public Object getPrimaryKey() throws RemoteException {
		return null;
	}

	public SlotCompletion[] findSlotCompletions(long l1, long l2)
			throws RemoteException {
		return new SlotCompletion[0];
	}

	public DownloadData getDownloadData(long downloadId) throws RemoteException {
		return null;
	}

	public GameData create() throws CreateException, RemoteException {
		return new MockGameData();
	}

	public EJBMetaData getEJBMetaData() throws RemoteException {
		return null;
	}

	public HomeHandle getHomeHandle() throws RemoteException {
		return null;
	}

	public void remove(Object arg0) throws RemoteException, RemoveException {
	}

	public void remove(Handle arg0) throws RemoteException, RemoveException {
	}
}
