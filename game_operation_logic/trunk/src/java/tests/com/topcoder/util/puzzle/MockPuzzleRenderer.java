package com.topcoder.util.puzzle;

import java.io.OutputStream;
import java.io.Writer;

public class MockPuzzleRenderer implements PuzzleRenderer {

	public String getMediumName() {
		// TODO Auto-generated method stub
		return null;
	}

	public SolutionTester renderPuzzle(PuzzleData puzzle, OutputStream target) {
		// TODO Auto-generated method stub
		return null;
	}

	public SolutionTester renderPuzzle(PuzzleData puzzle, Writer target) {
		return new MockSolutionTester();
	}

	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub

	}

}
