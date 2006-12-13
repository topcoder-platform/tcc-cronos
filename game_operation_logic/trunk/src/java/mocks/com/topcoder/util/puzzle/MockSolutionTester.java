package com.topcoder.util.puzzle;

import java.util.Map;

public class MockSolutionTester implements SolutionTester {

	public boolean testSolution(Object solution) {
		Map map = (Map) solution;
		if (map.size() == 2) {
			return true;
		} else {
			return false;			
		}
	}

}
