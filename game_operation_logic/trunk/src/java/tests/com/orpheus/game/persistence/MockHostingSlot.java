package com.orpheus.game.persistence;

import java.util.Date;

public class MockHostingSlot implements HostingSlot {

	private static final Long DEFAULT_PUZZLEID = new Long(123);

	public Long getId() {
		// TODO Auto-generated method stub
		return new Long(1);
	}

	public Domain getDomain() {
		// TODO Auto-generated method stub
		return new MockDomain();
	}

	public long getImageId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long[] getBrainTeaserIds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getPuzzleId() {
		return DEFAULT_PUZZLEID;
	}

	public int getSequenceNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public DomainTarget[] getDomainTargets() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getWinningBid() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Date getHostingStart() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getHostingEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
