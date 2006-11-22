package com.topcoder.util.puzzle;

public class MockPuzzleTypeSource implements PuzzleTypeSource {

	public PuzzleType getPuzzleType(String name) {
		return new MockPuzzleType();
	}

	public boolean hasPuzzleType(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
