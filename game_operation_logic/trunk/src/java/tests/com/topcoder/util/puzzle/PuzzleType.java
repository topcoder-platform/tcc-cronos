
package com.topcoder.util.puzzle;
import com.topcoder.util.puzzle.PuzzleException;
/**
 * <p> An interface defining the behavior of objects that represent the semantics of particular types of puzzles.
 * This will acts like a mini-manager/factory of renderes and generators for puzzles represented by this type.
 * 
 * Implementations are not required to be thread safe.
 * </p>
 * 
 * 
 * 
 */
public interface PuzzleType {
/**
 * <p>Returns the name of this puzzle type, which should be considered an opaque label, significant only within the scope
 * spanned by this component's configuration.
 * Names are not required to be unique amongst puzzles.
 * </p>
 * 
 * 
 * @return name of this puzzle type
 */
    public String getName();
/**
 * <p>Creates and returns a new PuzzleGenerator that can prepare puzzle data appropriate for this puzzle type.
 * </p>
 * 
 * 
 * @return a newly created puzzle generator
 * @throws PuzzleException if a checked exception prevents this method from completing successfully
 */
    public com.topcoder.util.puzzle.PuzzleGenerator createGenerator();
/**
 * <p>Creates and returns a new puzzle renderer appropriate for rendering puzzles of this type in the named medium.  
 * Which medium names are supported by this type can be determined via getSupportedMedia(), but the names should 
 * themselves be considered opaque labels, significant only within the scope of this components configuration.
 * This means that the names are more like private indicators to the specific implementation rather than globally significant
 * names.
 * </p>
 * 
 * 
 * @param medium named medium for which we will create the renderer
 * @return newly created puzzle render
 * @throws PuzzleException if the medium is not supported or if a checked exception prevents this method from completing normally
 */
    public com.topcoder.util.puzzle.PuzzleRenderer createRenderer(String medium);
/**
 * <p>Returns an array of the medium names supported by this puzzle  type
 * </p>
 * 
 * 
 * @return all the medium names (types) supported by this puzzle type
 */
    public String[] getSupportedMedia();
/**
 * <p>Tests whether the specified puzzle data is consistent with this puzzle type.  The result is determined on a best-effort
 * basis, so an affirmative response should not be interpreted as a guarantee that the data conform to this type, or that any of
 * this type's renderers would produce a sensible output from them.
 * in other words a false is always decisive but a true might be a false-positive true.
 * </p>
 * 
 * 
 * @param puzzle puzzle to check
 * @return true (on best effort basis) if the data is consistent with the puzzle type; false otherwise
 * @throws PuzzleException if a checked exception prevents successful completion of this method           
 */
    public boolean isPuzzleValid(com.topcoder.util.puzzle.PuzzleData puzzle);
}


