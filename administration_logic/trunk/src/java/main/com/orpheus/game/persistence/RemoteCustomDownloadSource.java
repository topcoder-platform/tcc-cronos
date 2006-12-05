
package com.orpheus.game.persistence;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.*;
import com.topcoder.web.frontcontroller.results.*;

/**
 * Implements the abstract ejbXXX method to work with the remote game data EJB. Simply defers all calls to the EJB. It uses the ConfigManager and Object Factory to initialize the JNDI EJB reference to obtain the handle to the EJB interface itself.
 * <p><strong>Thread Safety</strong></p>
 * <p>This class is immutable and thread-safe.</p>
 * 
 */
public class RemoteCustomDownloadSource extends CustomDownloadSource {

/**
 * <p>Represents the remote ejb instance used for all calls. Created in the consructor, will not be null,
 * and will not change.</p>
 * 
 * 
 */
    private final GameData gameDataEJB;

/**
 * <p>Instantiates new RemoteCustomDownloadSource instance from the given namespace. It will use ConfigManager to obtain a reference to the EJB GameData.</p>
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>call super(namespace)</li>
 * <li>Obtain jndi ejb reference from ConfigManager.</li>
 * <li>InitialContext ic = new InitialContext()</li>
 * <li>Object lookup = ic.lookup(reference)</li>
 * <li>GameDataHome home = (GameDataHome) PortableRemoteObject.narrow(lookup, GameDataHome.class)</li>
 * <li>GameData gameDataEJB = home.create()</li>
 * <li>Set to field.</li>
 * </ul>
 * 
 * 
 * @param namespace configuration namespace
 * @throws InstantiationException If there is an error with construction
 * @throws IllegalArgumentException If namespace is null or empty
 */
    public  RemoteCustomDownloadSource(String namespace) {        
        gameDataEJB = null;
    } 

/**
 * Determines and returns the download data and metadata associated with the specified ID string from persistence using the applicable EJB, or returns null if it cannot associate a download with the specified ID.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Call and return gameDataEJB.getDownloadData(id):DownloadData</li>
 * </ul>
 * 
 * 
 * @return DownloadData The download data for the id
 * @param id The id
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    protected DownloadData ejbGetDownloadData(String id) {        
        return null;
    } 
 }
