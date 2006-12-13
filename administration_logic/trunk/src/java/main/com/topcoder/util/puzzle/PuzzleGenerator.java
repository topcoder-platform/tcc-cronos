
package com.topcoder.util.puzzle;
import com.topcoder.util.puzzle.PuzzleException;
/**
 * <p>An interface defining generic behavior of objects that create puzzle data for a particular puzzle type.  
 * Users set attributes on an instance to configure details of the puzzle to generate, then generate either a single puzzle or a 
 * series of related puzzles, as they wish.  
 * 
 * PuzzleGenerators are stateful, and typically not thread safe.
 * </p>
 * 
 * 
 * 
 */
public interface PuzzleGenerator {
/**
 * <p>Generates puzzle data for one puzzle, appropriate for use with the PuzzleType supported by this generator.
 * </p>
 * 
 * 
 * @return generated puzzle
 * @throws PuzzleException if a checked exception prevents this method from completing successfully.
 */
    public com.topcoder.util.puzzle.PuzzleData generatePuzzle()throws PuzzleException;
/**
 * <p>Generates puzzle data for a series of the specified number of puzzles, appropriate for use with the PuzzleType supported 
 * by this generator.  The puzzles may be variations on a theme, or even identical, depending on the implementation.
 * </p>
 * 
 * 
 * @param size size of the series (i.e. how many to generate)
 * @return size number of generated puzzles
 * @throws PuzzleException if a checked exception prevents this method from completing successfully
 */
    public com.topcoder.util.puzzle.PuzzleData[] generatePuzzleSeries(int size)throws PuzzleException;
/**
 * <p>Sets the named attribute of this PuzzleGenerator to the specified value object, provided that an attribute of the
 *  specified name is supported and the value is of suitable type.
 * </p>
 * 
 * 
 * @param name attribute name
 * @param value the attribute value to set
 * @throws PuzzleException if the named attribute is not supported, or if the specified value is of an unsupported type.
 */
    public void setAttribute(String name, Object value)throws PuzzleException;
}


