package com.topcoder.util.puzzle;

public class MockPuzzleType implements PuzzleType {

	public String getName() {
		return "MockPuzzleType";
	}

	public PuzzleGenerator createGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public PuzzleRenderer createRenderer(String medium) {
		return new MockPuzzleRenderer();
	}

	public String[] getSupportedMedia() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPuzzleValid(PuzzleData puzzle) {
		// TODO Auto-generated method stub
		return false;
	}

}
