package com.orpheus.administration.persistence;

/**
 * Static factory for supplying the DAO instances to the EJBs. It uses synchronized lazy instantiation to get the initial instance of each DAO. Supports the creation of the AdminDataDAO and MessageDAO.
 * <p><strong>Thread Safety</strong></p>
 * <p>This object is effectively immutable and thread-safe, by virtua of being sycnhrhonized</p>
 * 
 */
public class DAOFactory {

    /**
     * <p>Represents the namespace used to retrieve the dao instances in the getter methods.</p>
     * 
     */
    public static final String NAMESPACE = "com.orpheus.administration.persistence.DAOFactory";

    /**
     * <p>Represents the AdminDataDAO instance. Created when the instance is first requested, and will not change or be null after that.</p>
     * 
     */
    private static com.orpheus.administration.persistence.AdminDataDAO adminDataDAO;

    /**
     * <p>Represents the MessageDAO instance. Created when the instance is first requested, and will not change or be null after that.</p>
     * 
     */
    private static com.orpheus.administration.persistence.MessageDAO messageDAO;

    /**
     * <p>Empty constructor.</p>
     * 
     */
    private DAOFactory() {
        // your code here
    }

    /**
     * <p>Obtains the AdminDataDAO instance. If it does not exist yet, it will be created using ConfigManager and ObjectFactory. Synchronized to avoid threading&nbsp; issues with lazy instantiation.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>
     * If field is null:
     * <ul type="disc">
     * <li>Instantiate a new ObjectFactory with a ConfogManagerSpecificationFactory with a namespace obtained from ConfigManager</li>
     * <li>Obtain the key for the dao from ConfgManager and use it to obtain a new AdminDataDAO from ObjectFactory.</li>
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
     * @return An AdminDataDAO
     */
    public static synchronized com.orpheus.administration.persistence.AdminDataDAO getAdminDataDAO() {
        return adminDataDAO;
    }

    /**
     * <p>Obtains the MessageDAO instance. If it does not exist yet, it will be created using ConfigManager and ObjectFactory. Synchronized to avoid threading&nbsp; issues with lazy instantiation.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>
     * If field is null:
     * <ul type="disc">
     * <li>Instantiate a new ObjectFactory with a ConfogManagerSpecificationFactory with a namespace obtained from ConfigManager</li>
     * <li>Obtain the key for the dao from ConfigManager and use it to obtain a new MessageDAO from ObjectFactory.</li>
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
     * @return A MessageDAO
     */
    public static synchronized com.orpheus.administration.persistence.MessageDAO getMessageDAO() {
        return messageDAO;
    }
}
