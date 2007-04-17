
package com.topcoder.timetracker.contact.persistence;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import java.sql.*;

/**
 * This class is enumeration of the column name's alais in client_project table. It make all the column names can be configured.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>The implementation of ClientDAO will get the actual column name from configuration file with the alias.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread safe by being immutable.</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m6325]
 */
public class ClientProjectColumnName extends com.topcoder.util.collection.typesafeenum.Enum {

/**
 * <p>Represents the name of the client_project column</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m63dd]
 */
    private final String name = null;

/**
 * <p>Represents the client id column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m6418]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName PROJECT_ID = new ClientColumnName("PROJECT_ID");

/**
 * <p>Represents the client id column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m6432]
 */
    public static com.topcoder.timetracker.contact.persistence.ClientColumnName CLIENT_ID = new ClientColumnName("CLIENT_ID");

/**
 * <p>Represents the modification date column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m644d]
 */
    public static com.topcoder.timetracker.contact.persistence.ClientColumnName MODIFICATION_DATE = new ClientColumnName("MODIFICATION_DATE");

/**
 * <p>Represents the modification user column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m6467]
 */
    public static com.topcoder.timetracker.contact.persistence.ClientColumnName MODIFICATION_USER = new ClientColumnName("MODIFICATION_USER");

/**
 * <p>Represents the creation date column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m6486]
 */
    public static com.topcoder.timetracker.contact.persistence.ClientColumnName CREATION_DATE = new ClientColumnName("CREATION_DATE");

/**
 * <p>Represents the creation user column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m64a0]
 */
    public static ClientColumnName CREATION_USER = new ClientColumnName("CREATION_USER");

/**
 * <p>Construts the ClientProjectColumnName.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Save name to the like named variable.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m633d]
 * @param name non null, non empty name
 */
    private  ClientProjectColumnName(String name) {        
    } 

/**
 * <p>Get the name</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return name</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m6387]
 * @return non null, non empty name
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>Get the name</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return name</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im7f2b3ef8m110c10f0507m63ac]
 * @return non null, non empty name
 */
    public String toString() {        
        // your code here
        return null;
    } 
 }
