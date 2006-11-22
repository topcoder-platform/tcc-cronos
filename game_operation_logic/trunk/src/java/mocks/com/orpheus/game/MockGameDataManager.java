package com.orpheus.game;

public class MockGameDataManager implements GameDataManager {

	public void advanceHostingSlot(long slotId) {
		System.out.println("MockGameDataManager#advanceHostingSlot("+slotId+")");
	}

}
