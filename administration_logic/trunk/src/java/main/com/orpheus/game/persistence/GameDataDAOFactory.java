
package com.orpheus.game.persistence;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.*;
import com.topcoder.web.frontcontroller.results.*;

/**
 * Static factory for supplying the game data DAO instance to the EJBs. It uses synchronized lazy instantiation to get the initial instance of the DAO. Supports the creation of the GameDataDAO.
 * <p><strong>Thread Safety</strong></p>
 * <p>This class is mutable and thread-safe.</p>
 * 
 */
public class GameDataDAOFactory {

/**
 * <p>Represents the game data&nbsp; DAO instance. Created when the instance is first requested, and will not change or be null after that.</p>
 * 
 */
    private static com.orpheus.game.persistence.GameDataDAO gameDataDAO;

/**
 * <p>Represents the namespace used to retrieve the dao instance in the getter method.</p>
 * 
 */
    private static final String NAMESPACE = "com.orpheus.game.persistence.GameDataDAOFactory";

/**
 * <p>Empty constructor.</p>
 * 
 */
    private  GameDataDAOFactory() {        
        // your code here
    } 

/**
 * <p>Obtains the GameDataDAO instance. If it does not exist yet, it will be created using ConfigManager and ObjectFactory. Synchronized to avoid threading&nbsp; issues with lazy instantiation.</p>
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>
 * If field is null:
 * <ul type="disc">
 * <li>Instantiate a new ObjectFactory with a ConfogManagerSpecificationFactory with a namespace obtained from ConfigManager</li>
 * <li>Obtain the key for the dao from ConfgManager and use it to obtain a new GameDataDAO from ObjectFactory.</li>
 * </ul>
 * </li>
 * <li>Return the instance</li>
 * </ul>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>InstantiationException If there is an error with construction</li>
 * </ul>
 * 
 * 
 * @return GameDataDAO instance
 */
    public static synchronized com.orpheus.game.persistence.GameDataDAO getGameDataDAO() {        
        return gameDataDAO;
    } 
}