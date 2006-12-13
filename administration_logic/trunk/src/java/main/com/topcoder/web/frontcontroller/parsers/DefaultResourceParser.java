
package com.topcoder.web.frontcontroller.parsers;
import java.util.ArrayList;
import java.util.HashMap;

import com.topcoder.web.frontcontroller.Action;
import com.topcoder.web.frontcontroller.ResourceParsingException;

/**
 * <p> DefaultResourceParser implements ResourceParser interface and parse all resource files in the given resource root to return an array of Action objects. This parser will first parse the 'global-resource.xml' file in the resource root to get global data used in all actions, and then parse the other resource files, each of them corresponds to an Action object. This class is not thread-safe, and it don't have to thread-safe, since it is called in FrontController init() method only once. 
 * If the members for logging ("logger" and "level") are set using the setLogging() method, then logging will be 
 * performed during the parse and the select operations as mentioned in section 1.3.14 of the component specification.
 * </p>
 * 
 */
public class DefaultResourceParser implements com.topcoder.web.frontcontroller.LogSupportResourceParser {

/**
 * <p>Represents the file name of the global resource file, which is parsed at first. </p>
 * 
 */
    private static final String GLOBAL_FILE = "global-resource.xml";

/**
 * <p>Represents mappings from action name to action Class object. The keys are action names (string values), and the values are action Class object. The keys must be non-null,non-empty string, and the values implement the Action interface. It is initialized in place. populated in the parseResource method from the actions-def element in global-resource file . Could be empty. Used in parseAction method to get action class. </p>
 * 
 */
    private final java.util.Map actionsDef = new HashMap();

/**
 * <p>Represents mappings from handler name to handler Class object. The keys are handler names (string values), and the values are handler Class object. The keys must be non-null,non-empty string, and the values implement the Handler interface. It is initialized in place. populated in the parseResource method from the handles-def element in the global-resource.xml . Could be empty. Used in parseHandler method to get handler class. </p>
 * 
 */
    private final java.util.Map handlersDef = new HashMap();

/**
 * <p>Represents mappings from result name to result Class object. The keys are result names (string values), and the values are result Class object. The keys must be non-null,non-empty string, and the values implement the Result interface. It is initialized in place. populated in the parseResource method from results-def element from global-resource.xml. Could be empty. Used in parseResult method to get result class. </p>
 * 
 */
    private final java.util.Map resultsDef = new HashMap();

/**
 * <p>Represents a list of global handlers. It is initialized in place to empty list, and populated in parseResource method. Could be empty. It is defined as an array since it is used internally. </p>
 * 
 */
    private final java.util.List globalHandlers = new ArrayList();

/**
 * <p>Represents mappings from result code to Result object. The keys are result codes (string values), and the values are Result object. The keys must be non-null,non-empty string. It is initialized in place. populated in the parseResource method. Could be empty.</p>
 * 
 */
    private final java.util.Map globalResults = new HashMap();

/**
 * <p>Represents the instance of Log to perform the Logging.
 * This instance member can be null and will be set when a call to the method setLogging is done.
 * </p>
 * 
 */
    private com.topcoder.util.log.Log logger;

/**
 * <p>Represents the instance of Level to indicate the Level of Logging to be done.
 * This instance member can be null and will be set when a call to the method setLogging is done.
 * </p>
 * 
 */
    private com.topcoder.util.log.Level level;

/**
 * <p>Empty constructor. </p>
 * 
 */
    public  DefaultResourceParser() {        
        // your code here
    } 

/**
 * <p>Parse resource files in the directory specified by the given resource root, and return an array of Action objects. It will: 1. call new URL(resourceRoot).getPath to get the directory path if the resourceRoot starts with: 'file:/', and then concat the file path with GLOBAL_FILE to get the global resource file path, parse this file using DocumentBuilder to get the Document object, parse it to get all: actions-def, handlers-def, results-def, global-handlers and global-results. The mappings defined in actions-def, handlers-def, results-def will be saved to actionsDef, handlersDef, and resultsDef respectively. And foreach handler element in global-handlers, it will call parseHandler to create the Handler object. foreach result element in global-results, it will call parseResult to create the Result object. 2. call new File(resource-path).listFiles() to get all resource files in this directory (skip the global-resource.xml). for each resource file, parse it to get the Document object, and then pass its root element to parseAction method to create an Action object, get the priority attribute of the root element. Finally order all Action objects by the priority in desending order and then return. </p>
 * 
 * 
 * @return an array of Action objects.
 * @param resourceRoot the directory containing all resource files.
 * @throws IllegalArgumentException if the arg is null or empty string.
 * @throws ResourceParsingException if fail to parse resource files in the root.
 */
    public Action[] parseResource(String resourceRoot) {        
        // your code here
        return null;
    } 

/**
 * <p>parse the Element to create an Action object. It will: 1. Extract the name, type, and urlPattern, find the action Class in actionsDef by the type value, and then create an Action object by calling constructor with name and urlPattern arguments. 2. for each handler sub element, call parseHandler to create Handler object. 3. create a list, append the global handlers (which should be executed at first), then append the Handler created in 2. then assign to this action. 4. for each result sub element, call parseResult to create the Result object, and get the result name to store the result into a map. 5.foreach entry in globalResults Map, add it to the map created in step 4 only the resultCode doesn't exist in that map. 6. return the created Action object. </p>
 * 
 * 
 * @return the created Action object.
 * @param actionElement the action element to parse.
 * @throws ResourceParsingException if fail to create the Action object from the element.
 */
    protected com.topcoder.web.frontcontroller.Action parseAction(org.w3c.dom.Element actionElement) {        
        // your code here
        return null;
    } 

/**
 * <p> Parse the handler element to create the Handler object. get the type attribute, and get corresponding Class object in handlersDef, create new instance by calling constructor with Element argument. </p>
 * 
 * 
 * @return the Handler object created from handler element.
 * @param handlerElement the handler element.
 * @throws ResourceParsingException if fail to create the Handler object from the element.
 */
    protected com.topcoder.web.frontcontroller.Handler parseHandler(org.w3c.dom.Element handlerElement) {        
        // your code here
        return null;
    } 

/**
 * <p> Parse the result element to create the Result object. get the type attribute, and get corresponding Class object in resultsDef, create new instance by calling constructor with Element argument. </p>
 * 
 * 
 * @return the Result object created from result element.
 * @param resultElement the result element.
 * @throws ResourceParsingException if fail to create the Result object from result element.
 */
    protected com.topcoder.web.frontcontroller.Result parseResult(org.w3c.dom.Element resultElement) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Sets the level of Logging to be performed and the Log instance to be used for performing the Logging.
 * Initializes the corresponding instance members from the value of the parameters passed.
 * Once set any futhur calls to this method is ignored.
 * </p>
 * 
 * 
 * @param logger the instance of Log to perform the Logging
 * @param loggingLevel the instance of Level to indicate the Level of Logging to be done.
 * @throws IllegalArgumentException: if any of the parameters passed is null
 */
    public void setLogging(com.topcoder.util.log.Log logger, com.topcoder.util.log.Level loggingLevel) {        
        // your code here
    } 
}