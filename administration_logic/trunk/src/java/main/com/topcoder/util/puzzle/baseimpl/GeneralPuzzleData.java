
package com.topcoder.util.puzzle.baseimpl;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> General implementation of puzzle data, which is a named collection of named attributes and resources. 
 * Attributes are simply name / value pairs, whereas resources are named blocks of binary data bearing appropriate MIME 
 * media types that describe the resource MIME type (for rendering purposes).  
 * 
 * This PuzzleData implementation restricts attribute names, attribute values, and resource names to 
 * be strings of <= 256 characters.
 * </p>
 * 
 * <p> Thread-Safety
 * This implemantation is not thread-safe, since side-effects ffrom internal  references are possible.
 * Since return values are still pointing to the original data client can change it externally.
 * </p>
 * 
 * <p> Seializability
 * This class is Serializable but we will need to check if the passed in attributes. or resources map has the values
 * as serialiable (i.e. instanceOf) objects. If we encounter one that is not we will fail the instantiation with an IAE.
 * </p>
 * 
 */
public class GeneralPuzzleData implements java.io.Serializable, com.topcoder.util.puzzle.PuzzleData {



/**
 * <p>Creates a new instance of puzzle data with the provided name set.
 * </p>
 * <p> Implementation Notes
 *     1. this.attributes.put(this.PUZZLE_TYPE_ATTRIBUTE, name);
 * </p>
 * 
 * 
 * @param name puzzle type name
 * @throws IllegalArgumentException if the passed in name parameter is null; also thrown if input name.length() >256
 */
    public  GeneralPuzzleData(String name) {        
        // your code here
    } 

/**
 * <p>Creates a new instance of puzzle data with the provided name and attributes map set.
 * </p>
 * <p> Implementation Notes
 *     // we make a copy
 *     1. this.attributes = new HashMap(attributes);
 *     // we set the name
 *     2. this.attributes.put(this.PUZZLE_TYPE_ATTRIBUTE, name);
 *        If this attribute is already set in the map we override it.
 * </p>
 * 
 * <p> Seializability
 * This class is Serializable but we will need to check if the passed in attributes map has the values
 * as serialiable (i.e. instanceOf) objects. If we encounter one that is not we will fail the instantiation with an IAE.
 * We do this by looping through all teh values in teh map testing for instaceOf Serialiable.
 * </p>
 * 
 * 
 * @param name puzzle type name
 * @param attributes attributes for this puzzle data
 * @throws IllegalArgumentException if the passed in name or attributes/resources parameters is null; also thrown if input name.length() >256 or if any of the attribute keys are > 256 characters in length; We also throw this exception if the passed in attributes Map has a value that is NOT  instanceOf Serializable.
 */
    public  GeneralPuzzleData(String name, Map attributes) {        
        // your code here
    } 


/**
 * <p>Creates a new instance of puzzle data with the provided name, attributes map, and resources map set.
 * </p>
 * <p> Implementation Notes
 *     // we make a copy of resources
 *     1. this.resources = new HashMap(resources);
 *     // we make a copy of attributes
 *     1. this.attributes = new HashMap(attributes);
 *     // we set the name
 *     3. this.attributes.put(this.PUZZLE_TYPE_ATTRIBUTE, name);
 *        If this attribute is already set in the map we override it.
 * </p>
 * 
 * <p> Seializability
 * This class is Serializable but we will need to check if the passed in resources/attributes map has the values
 * as serialiable (i.e. instanceOf) objects. If we encounter one that is not we will fail the instantiation with an IAE.
 * We do this by looping through all teh values in teh map testing for instaceOf Serialiable.
 * </p>
 * 
 * 
 * @param name puzzle type name
 * @param attributes attributes for this puzzle data
 * @param resources resources for this puzzle data
 * @throws IllegalArgumentException if the passed in name, attributes, or resources parameters is null; also thrown if input name.length() >256 or if any of the attribute/resource keys or values are > 256 characters in length; We also throw this exception if the passed in resources/attributes Map has a value that is NOT  instanceOf Serializable.
 */
    public  GeneralPuzzleData(String name, Map attributes, Map resources) {        
        // your code here
    } 

/**
 * <p>Gets the name of the puzzle represented by this data.
 * </p>
 * <p> Implementation Notes
 *     1. return this.attributes.get(this.PUZZLE_TYPE_ATTRIBUTE);
 * </p>
 * 
 * 
 * @return name of the puzzle that represents this data
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>Returns the value of the named attribute of this puzzle data, or null if this data has no attribute of the specified name.
 * </p>
 * <p> Implementation Notes
 *     1. return this.attributes.get(name);
 * </p>
 * 
 * 
 * @param name attribute name
 * @return attribute value
 */
    public String getAttribute(String name) {        
        // your code here
        return null;
    } 

/**
 * <p>Returns all the attributes of this puzzle data in the form of an unmodifiable Map from attribute names to attribute values.
 * </p>
 *     1. return Collections.unmodifiableMap(this.attributes);
 * </p>
 * 
 * 
 * @return unmodifiable Map which maps attribute names to attribute values
 */
    public Map getAllAttributes() {        
        // your code here
        return null;
    } 

/**
 * <p>Returns the named resource of this puzzle data, or null if  this data has no resource of the specified name.
 * </p>
 * <p> Implementation Notes
 *     1. return this.resources.get(name);
 * </p>
 * 
 * 
 * @param name resource name
 * @return PuzzleResource instance mapped to this name
 */
    public com.topcoder.util.puzzle.PuzzleResource getResource(String name) {        
        // your code here
        return null;
    } 

/**
 * <p>Returns all the resources of this puzzle data in the form of an unmodifiable Map from resource names to 
 * PuzzleResource objects.
 * </p>
 * </p>
 *     1. return Collections.unmodifiableMap(this.resources);
 * </p>
 * 
 * 
 * @return unmodifiable map which maps resource names to PuzzleResource instances
 */
    public Map getAllResources() {        
        // your code here
        return null;
    } 
}
