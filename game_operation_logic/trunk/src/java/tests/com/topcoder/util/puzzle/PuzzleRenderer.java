
package com.topcoder.util.puzzle;
import java.io.OutputStream;
/**
 * <p>An interface describing the behavior of an object that can render a puzzle of a specific type to a particular medium. 
 * Implementations are stateful, and typically not thread safe.
 * </p>
 * 
 * 
 * 
 */
public interface PuzzleRenderer {
/**
 * <p> Returns the name of the medium in which this renderer will render puzzles, for instance 'HTML', 'Postscript', or 'Flash'.
 * This is according to renderer configuration and not significant outside the scope of that configuration ¨C there is not
 * (currently) a defined dictionary of canonical medium names. This means that any name will be accepted and should be
 * expected.
 * </p>
 * 
 * 
 * @return the currently set medium type
 */
    public String getMediumName();
/**
 * <p> Renders the specified puzzle data to the provided byte stream in a manner appropriate to puzzle type and medium to 
 * which this renderer is specific, and returns a SolutionTester with which to test potential solutions to the rendered puzzle to
 * determine whether they are correct.  
 * If the rendering contains character data then this renderer will choose an encoding  based on configuration, attributes, or 
 * internal logic.
 *  
 * Implementations of this method are not required to be idempotent -- they are permitted to create different renderings (in the 
 * same medium) of the same puzzle data on different runs.
 * </p>
 * 
 * 
 * @param puzzle the actual puzzle
 * @param target the actual byte stream target on which we will be writing/rendering the puzzle
 * @return tester instance which can be used to tests potnetial solutions
 * @throws PuzzleException if a checked exception prevents the successful completion of this method
 */
    public com.topcoder.util.puzzle.SolutionTester renderPuzzle(com.topcoder.util.puzzle.PuzzleData puzzle, OutputStream target);
/**
 * <p> Renders the specified puzzle data to the provided character stream in a manner appropriate to puzzle type and medium 
 * to which this renderer is specific, and returns a SolutionTester with which to test potential solutions to the rendered puzzle
 * to determine whether they are correct.  
 * If the rendering contains non-character data then this method will throw an exception.
 * 
 * Implementations of this method are not required to be idempotent -- they are permitted to create different renderings (in the 
 * same medium) of the same puzzle data on different runs.
 * </p>
 * 
 * 
 * @param puzzle the actual puzzle
 * @param target the actual character stream target on which we will be writing/rendering the puzzle
 * @return tester instance which can be used to tests potnetial solutions
 * @throws PuzzleException rendering produces binary data or if a checked exception prevents the successful completion of this method.
 */
    public com.topcoder.util.puzzle.SolutionTester renderPuzzle(com.topcoder.util.puzzle.PuzzleData puzzle, com.topcoder.util.puzzle.Writer target);
/**
 * <p>Sets the named attribute of this PuzzleRenderer to the specified value object, provided that an attribute of the
 * specified name is supported and the value is of suitable type.
 * </p>
 * 
 * 
 * @param name attribute name
 * @param value value to be set for this attribute name
 * @throws IllegalArgumentException if the value or name is null.
 * @throws PuzzleException if the named attribute is not supported, or if the specified value is of an unsupported type
 */
    public void setAttribute(String name, Object value);
}


