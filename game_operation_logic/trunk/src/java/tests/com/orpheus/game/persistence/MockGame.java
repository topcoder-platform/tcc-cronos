package com.orpheus.game.persistence;

import java.util.Date;

public class MockGame implements Game {
	
	private long id;
	private String name;
	private Date start;
	private Date end;
	
	public MockGame(long id, String name, Date start, Date end){
		this.id = id;
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public MockGame(long id, String name){
		this(id, name, new Date(), new Date());
	}

	public Long getId() {
		return new Long(id);
	}

	public String getName() {
		return name;
	}

	public BallColor getBallColor() {
		return null;
	}

	public int getKeyCount() {
		return 0;
	}

	public Date getStartDate() {
		return start;
	}

	public Date getEndDate() {
		return end;
	}

	public HostingBlock[] getBlocks() {
		return new MockHostingBlock[] { new MockHostingBlock() };
	}

}
