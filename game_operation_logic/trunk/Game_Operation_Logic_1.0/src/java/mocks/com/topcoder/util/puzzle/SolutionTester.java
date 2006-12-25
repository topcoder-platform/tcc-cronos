
package com.topcoder.util.puzzle;
import com.topcoder.util.puzzle.PuzzleException;
/**
 * <p> An interface describing the behavior of objects that test prospective puzzle solutions for correctness.  Instances are
 * specific to particular renderings of specific puzzles, and it is recommended that they be thread safe.
 * </p>
 * 
 * 
 * 
 * @poseidon-object-id [Ifdeeecm10df11a47b1mm7dd0]
 */
public interface SolutionTester {
/**
 * <p> Tests the specified solution object to determine whether it is a correct solution, returning true if it is correct or false
 * if it is wrong </p>
 * 
 * 
 * @poseidon-object-id [Ifdeeecm10df11a47b1mm7da9]
 * @param solution solution 
 * @return true if the tester has sucesfully tested the solution and if passes; false otherwise
 * @throws PuzzleException if the solution object is of incorrect type, or if a checked exception prevents successful completion of this method
 */
    public boolean testSolution(Object solution);
}


