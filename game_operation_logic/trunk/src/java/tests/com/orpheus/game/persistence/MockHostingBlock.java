package com.orpheus.game.persistence;

public class MockHostingBlock implements HostingBlock {

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxHostingTimePerSlot() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSequenceNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public HostingSlot[] getSlots() {
		// TODO Auto-generated method stub
		return new MockHostingSlot[] { new MockHostingSlot() };
	}

}
