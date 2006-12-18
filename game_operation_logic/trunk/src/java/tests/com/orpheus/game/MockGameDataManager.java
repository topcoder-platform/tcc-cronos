package com.orpheus.game;

import com.orpheus.game.persistence.HostingSlot;

public class MockGameDataManager implements GameDataManager {

	public void advanceHostingSlot(long slotId) {
		System.out.println("MockGameDataManager#advanceHostingSlot("+slotId+")");
	}

	public void recordWinningBids(long blockId, long[] bidIds) throws GameDataException {
	}

	public boolean testUpcomingDomain(HostingSlot slot) throws GameDataException {
		return false;
	}

}
