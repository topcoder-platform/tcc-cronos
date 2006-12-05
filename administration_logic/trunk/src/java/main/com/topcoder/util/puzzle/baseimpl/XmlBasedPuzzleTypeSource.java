
package com.topcoder.util.puzzle.baseimpl;
import java.util.Map;

import com.topcoder.util.objectfactory.*;
import com.topcoder.util.puzzle.PuzzleConfigurationException;
import com.topcoder.util.puzzle.PuzzleException;

/**
 * <p> This is an implementation of the PuzzleTypeResource interface. This implementation relies on  xml configuration
 * through a specific xml format which uses Object Factory to instantiate the PuzzleData instances that the source will provide
 * to the user.
 * 
 * This implementation stores all the mappings of names to types and thus caches the data. This data will be come
 * stale at some point at which time the user might opt to reload() the whole configuration.
 * </p>
 * 
 * <p> Thread-Safety
 * This implementation should be thread safe because of the reload() method.
 * We need to synchronize access to nameToPuzzleTypeMap access for each method that uses it.
 * </p>
 * 
 * 
 */
public class XmlBasedPuzzleTypeSource implements com.topcoder.util.puzzle.PuzzleTypeSource {


/**
 * <p>Creates a new instance of this class with default values for config file name and for namespace
 * </p>
 * <p> Implementation Details
 *     // delegate to the main ctor.
 *     1. this(puzzle_type_resource_config.xml, "");   // "" is a valid namespace here.
 * </p>
 * 
 * 
 * @throws PuzzleConfigurationException if there are issues with reading data due to wrong configuration, file IO, or xml issues.
 */
    public  XmlBasedPuzzleTypeSource() {        
        // your code here
    } 

/**
 * <p>Creates a new instance of this class with supplied value for config file name and default for namespace
 * </p>
 * <p> Implementation Details
 *     // delegate to the main ctor.
 *     1. this(configFileName, "");   // "" is a valid namespace here.
 * </p>
 * 
 * 
 * @param configFileName configuration file name
 * @throws PuzzleConfigurationException if there are issues with reading data due to wrong configuration, file IO, or xml issues.
 */
    public  XmlBasedPuzzleTypeSource(String configFileName) {        
        // your code here
    } 

/**
 * <p>Creates a new instance of this class with supplied value for config file name and for namespace
 * </p>
 * <p> Implementation Details
 *     // set teh member variables
 *     1. this.configFileName = configFileName;
 *     2. this.namespace = namespace;
 * 
 *     // read xml details
 *     1. Delegate to loadConfigData() helper method
 * </p>
 * 
 * 
 * @param configFileName configuration file name
 * @param namespace namespace for the configuration
 * @throws PuzzleConfigurationException if there are issues with reading data due to wrong configuration, file IO, or xml issues.
 */
    public  XmlBasedPuzzleTypeSource(String configFileName, String namespace) {        
        // your code here
    } 

/**
 * <p>Provides the named PuzzleType object, if it is available.
 * User can check availability through the hasPuzzleType method first.
 * </p>
 * <p> Implementation Notes
 *     1. return nameToPuzzleTypeMap.get(name)
 * </p>
 * 
 * 
 * 
 * @param name name of the mapped PuzzleType.
 * @return named PuzzleType
 * @throws PuzzleException if the named puzzle type does not exist or cannot be provided because of a checked exception
 * @throws IllegalArgumentException if the argument is null
 */
    public com.topcoder.util.puzzle.PuzzleType getPuzzleType(String name) {        
        // your code here
        return null;
    } 

/**
 * <p>Determines whether a puzzle type corresponding to the specified name is available from this source.
 * </p>
 * <p> Implementation Notes
 *     1. return nameToPuzzleTypeMap.containsKey(name)
 * </p>
 * 
 * 
 * @param name name of the puzzle type
 * @return true if a puzzle type corresponding to the given name is available from this resource
 * @throws PuzzleException if a checked exception prevents this method from completing successfully.
 * @throws IllegalArgumentException if the argument is null
 */
    public boolean hasPuzzleType(String name) {        
        // your code here
        return false;
    } 

/**
 * <p>Reloads the contents of this class.
 * </p>
 * <p> Implementation Notes
 *     1. clear nameToPuzzleTypeMap data
 *     2. delegate to loadConfigData();
 * </p>
 * 
 * 
 * @throws PuzzleConfigurationException if there are issues with reading data due to wrong configuration, file IO, or xml issues.
 */
    public void reload() {        
        // your code here
    } 

/**
 * <p>This is a configuration loading helper method (internal) which will attempt to read the specified configuration and 
 * initialize all the data.</p>
 * 
 * <p> Implementation Notes
 *     // read the xml
 *     1. We open the xml file (this.configFilename) and fetch its (use DOM) <puzzle-type-list> elements
 *     2. We find the one with the specified namespace=this.namespace attribute.
 *         (a) If not found and namespace is an empty string (i.e. "") we simply look for <puzzle-type-list> element with NO 
 *              namespace attribute.
 *     3. Once we have the correct <puzzle-type-list> element we read the following:
 *         (a) For each <puzzle-type> we do the following:
 *               (i) Read in the <type-name> child element
 *              (ii) Read <object-factory-token> element
 *     4. Using all the values we do the follwoing
 *         (a) for each <puzzle-type> and <type-name> child element that we read in we use the <object-factory-token> to 
 *              create a new instance of the PuzzleType type using ObjectFactory (using the provided namespace)
 *         (b) We then associate the name to the created instance in the nameToPuzzleTypeMap variable.
 * 
 * NOTE: Configuration file will contain a objectFactoryNameSpace argument as follows:
 *     <puzzle-type-list namespace="some.name.space" objectFactoryNameSpace="some.OF.name.space">
 * 
 * We use this objectFactoryNameSpace as follows:
 * 
 *       // instantiate factory with specification factory
 *       ObjectFactory factory = 
 *       new ObjectFactory( new ConfigManagerSpecificationFactory(objectFactoryNameSpace));
 *       // obtain the OF object
 *       Puzzletype myPuzzleType =  (PuzzleType) factory.createObject(puzzleTypeToken);
 * 
 * Of course if the objectFactoryNameSpace doesn't exist (it is optional) then we simply use a default as follows:
 * If this namespace is missing then we simply default to this.namespace + ¡±_ObjectFactory¡±
 * 
 *    
 * </p>
 * 
 * 
 * @throws PuzzleConfigurationException if there are issues with reading data due to wrong configuration, file IO, or xml issues.
 */
    private void loadConfigData() {        
        // your code here
    } 
}
