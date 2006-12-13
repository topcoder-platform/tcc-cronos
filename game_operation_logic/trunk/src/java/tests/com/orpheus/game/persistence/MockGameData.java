package com.orpheus.game.persistence;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;

import com.orpheus.game.GameDataException;
import com.topcoder.util.puzzle.MockPuzzleData;
import com.topcoder.util.puzzle.PuzzleData;

public class MockGameData implements GameData {

	public static final long[] GAME_REGISTRATIONS = new long[]{1,2,3,4};

	public Game createGame(Game game) throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public HostingSlot[] createSlots(long blockId, long[] bidIds)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public Domain createDomain(Domain domain) throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public HostingBlock addBlock(long gameId, int slotMaxHostingTime)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public Game getGame(long gameId) throws RemoteException, GameDataException {
		return new MockGame(gameId, gameId+"");
	}

	public HostingBlock getBlock(long blockId) throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public HostingSlot getSlot(long slotId) throws RemoteException,
			GameDataException {
		return new MockHostingSlot();
	}

	public Domain getDomain(long domainId) throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getKeysForPlayer(long playerId, long[] slotIds)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return new String[]{"1"};
	}

	public PuzzleData getPuzzle(long puzzleId) throws RemoteException,
			GameDataException {
		return new MockPuzzleData();
	}

	public void recordPluginDownload(String pluginName) throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub

	}

	public void recordRegistration(long playerId, long gameId)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub

	}

	public SlotCompletion recordSlotCompletion(long playerId, long slotId,
			Date date) throws RemoteException, GameDataException {
		return new MockSlotCompletion();
	}

	public void recordGameCompletion(long playerId, long gameId)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub

	}

	public long recordBinaryObject(String name, String mediaType, byte[] content)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return 0;
	}

	public HostingSlot[] updateSlots(HostingSlot[] slots)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateDomain(Domain domain) throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub

	}

	public void deleteSlot(long slotId) throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub

	}

	public Domain[] findActiveDomains() throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public Game[] findGamesByDomain(String domain, long playerId)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public HostingSlot[] findCompletedSlots(long gameId)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return new HostingSlot[]{new MockHostingSlot()};
	}

	public SlotCompletion[] findSlotCompletingPlayers(long gameId, long slotId)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public Game[] findGames(Boolean isStarted, Boolean isEnded)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public long[] findGameRegistrations(long playerId) throws RemoteException,
			GameDataException {

		return GAME_REGISTRATIONS;
	}

	public Domain[] findDomainsForSponsor(long sponsorId)
			throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public HostingSlot findSlotForDomain(long gameId, long playerId,
			String domain) throws RemoteException, GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public BallColor[] findAllBallColors() throws RemoteException,
			GameDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove() throws RemoteException, RemoveException {
		// TODO Auto-generated method stub

	}

	public Object getPrimaryKey() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public EJBHome getEJBHome() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIdentical(EJBObject arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public Handle getHandle() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
