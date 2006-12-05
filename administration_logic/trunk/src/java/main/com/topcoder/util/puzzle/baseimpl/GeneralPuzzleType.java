
package com.topcoder.util.puzzle.baseimpl;
import java.util.Map;

import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.objectfactory.*;
import com.topcoder.util.puzzle.PuzzleException;

/**
 * <p> This is a general PuzzleType implementation defining the behavior of objects that represent the semantics of particular 
 * types of puzzles.
 * This will acts like a mini-manager/factory of renderes and generators for puzzles represented by this type.
 * 
 * This version/implementation uses a configured xml file and a specific Object factory namespace to eventually load/create
 * the required instances of generator or renderers for example.
 * Uses ObjectFactory internally to do the instntation.
 * </p>
 * 
 * <p> Thread-Safety
 * This Implementations is  thread safe since it is not mutable.
 * </p>
 * 
 */
public class GeneralPuzzleType implements com.topcoder.util.puzzle.PuzzleType {

/**
 * <p>Represents name of this puzzle type, which should be considered an opaque label, significant only within the scope
 * spanned by this component's configuration. It is set in the constructor and not changed after that. Can not be null or empty.
 * Accessed through a corresponding getter.
 * This corresponds to the type-name element in the config zml file:
 *      <puzzle-type>
 *          <type-name>some name</type-name>
 * 
 * </p>
 * 
 */
    private final String name;

/**
 * <p>Represents a key/token to be used with Object Factory to create a new instance (based on configuration) of a 
 * PuzzleGenerator implementation for this PuzzleType.
 * Initialized in the constructor. Cannot change after that. Can not be null or empty.
 * The idea here is that the generator is created each time when createGenerator() method is called and thus we must have
 * a way of creating each instance from scratch.
 * </p>
 * 
 */
    private final String generatorObjectFactoryToken;

/**
 * <p>Represents a mapping of medium name to renderer instantiation token/key used with Object Factory.
 * medium name can not be null or empty.
 * This is created through constructor.
 * 
 * The idea here is that each renderer is created each time when createRenderer(medium) method is called and thus we must 
 * havea way of creating each instance from scratch.
 * </p>
 * 
 */
    private final Map namedRendererObjectFactoryTokenMap;

/**
 * <p>Represents the Object Factory namespace to use when using any tokens/keys to instantiate objects.
 * Initialized in the constructor and then never changed after that. Can not be null or empty.
 * </p>
 * 
 */
    private final String objectFactoryNameSpace;

/**
 * <p>Represents validator to be used in the isPuzzleValid(puzzle) method to verify that the puzzle data passed in is actually 
 * valid.
 * This will be instantiatied in the constructor (based on a key) and then will not change.
 * </p>
 * 
 */
    private final ObjectValidator validator;

/**
 * <p>Creates an instance initialized with provided data.
 * </p>
 * <p> Implementation Notes
 *     1. Assign the paramers to their corresponding variables.
 * </p>
 * 
 * 
 * @param namespace Object Factory namespace
 * @param name Name of this puzzle type
 * @param generatorKey key used to instantiate the generator via ObjectFactory
 * @param rendererKeyMap This is a mapping of names (media types) to ObjectFactory keys used to instantiate instance of specific PuzzleRendere instance.
 * @param validator puzzle validator object. 
 * @throws IllegalArgumentException if any if the input parameters is null; if any string is empty (except for namespace which CAN be empty); if the provided Map is empty.
 */
    public  GeneralPuzzleType(String namespace, String name, String generatorKey, Map rendererKeyMap, com.topcoder.util.datavalidator.ObjectValidator validator) {        
        this.name= null;
        this.generatorObjectFactoryToken= null;
        this.namedRendererObjectFactoryTokenMap= null;
        this.objectFactoryNameSpace= null;
        this.validator= null;
    } 

/**
 * <p>Returns the name of this puzzle type, which should be considered an opaque label, significant only within the scope
 * spanned by this component's configuration.
 * Names are not required to be unique amongst puzzles but have to be unique amongst PuzzleTypes configured within the 
 * same namespace.
 * </p>
 * <p> Implementation Notes
 *     1. return this.name
 * </p>
 * 
 * 
 * 
 * @return name of this type
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>Creates and returns a new PuzzleGenerator that can prepare puzzle data appropriate for this puzzle type.
 * </p>
 * <p> Implementation Notes
 *     1. Using the provided namespace and the generatorObjectFactoryToken we create a new instance of PuzzleGenerator
 *     // instantiate factory with specification factory
 *     ObjectFactory factory = new ObjectFactory(
 *               new ConfigManagerSpecificationFactory(nameSpace)
 *      );
 *       // obtain the OF object
 *       PuzzleGenerator generator = (PuzzleGenerator) factory.createObject(this.generatorObjectFactoryToken);
 *       return generator;
 * </p>
 * 
 * 
 * @return a newly created puzzle generator
 * @throws PuzzleException if a checked exception prevents this method from completing successfully
 */
    public com.topcoder.util.puzzle.PuzzleGenerator createGenerator() {        
        // your code here
        return null;
    } 

/**
 * <p>Creates and returns a new puzzle renderer appropriate for rendering puzzles of this type in the named medium.  
 * Which medium names are supported by this type can be determined via getSupportedMedia(), but the names should 
 * themselves be considered opaque labels, significant only within the scope of this components configuration.
 * This means that the names are more like private indicators to the specific implementation rather than globally significant
 * names.
 * </p>
 * <p> Implementation Notes
 *     // Lookup the needed token to create the renderer.
 *     1. token = namedRendererObjectFactoryTokenMap.get(medium);
 *     2. Using Object Factory instantiate the renderer.
 *     // instantiate factory with specification factory
 *     ObjectFactory factory = new ObjectFactory(
 *               new ConfigManagerSpecificationFactory(nameSpace)
 *      );
 *       // obtain the OF object
 *       PuzzleRenderer renderer = (PuzzleRenderer) factory.createObject(token);
 *       return renderer;
 * </p>
 * 
 * 
 * @param medium named medium for which we will create the renderer
 * @return newly created puzzle render
 * @throws PuzzleException if the medium is not supported or if a checked exception prevents this method from completing normally
 */
    public com.topcoder.util.puzzle.PuzzleRenderer createRenderer(String medium) {        
        // your code here
        return null;
    } 

/**
 * <p>Returns an array of the medium names supported by this puzzle  type
 * </p>
 * <p. Implementation Notes
 *     1. Simply return all the keys in the namedRendererObjectFactoryTokenMap
 * </p>
 * 
 * 
 * @return all the medium names (types) supported by this puzzle type
 */
    public String[] getSupportedMedia() {        
        // your code here
        return null;
    } 

/**
 * <p>Tests whether the specified puzzle data is consistent with this puzzle type.  The result is determined on a best-effort
 * basis, so an affirmative response should not be interpreted as a guarantee that the data conform to this type, or that any of
 * this type's renderers would produce a sensible output from them.
 * in other words a false is always decisive but a true might be a false-positive true.
 * </p>
 * <p> Implementation Notes
 *     1. return  validator.valid(puzzle);
 * </p>
 * 
 * 
 * @param puzzle puzzle data to check
 * @return true (on best effort basis) if the data is consistent with the puzzle type; false otherwise
 * @throws PuzzleException if a checked exception prevents successful completion of this method           
 */
    public boolean isPuzzleValid(com.topcoder.util.puzzle.PuzzleData puzzle) {        
        // your code here
        return false;
    } 
}
