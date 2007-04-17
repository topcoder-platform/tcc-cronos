
package com.topcoder.timetracker.contact.persistence;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import java.sql.*;

/**
 * This class is enumeration of the column name's alais in client table. It make all the column names can be configured.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>The implementation of ClientDAO will get the actual column name from configuration file with the alias. And the name of alias will also be used for SearchBundle to specify which fields whould be included in the search result.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread safe by being immutable.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mb4d]
 */
public class ClientColumnName extends com.topcoder.util.collection.typesafeenum.Enum {

/**
 * <p>Represents the name of the client column</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mc08]
 */
    private final String name = null;

/**
 * <p>Represents the name column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mc72]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName NAME = new ClientColumnName("NAME");

/**
 * <p>Represents the&nbsp;company id column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mca8]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName COMPANY_ID = new ClientColumnName("COMPANY_ID");

/**
 * <p>Represents the&nbsp;creation date column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mcf6]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName CREATION_DATE = new ClientColumnName("CREATION_DATE");

/**
 * <p>Represents the&nbsp;creation user column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85md10]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName CREATION_USER = new ClientColumnName("CREATION_USER");

/**
 * <p>Represents the&nbsp;modification date column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85md2a]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName MODIFICATION_DATE = new ClientColumnName("MODIFICATION_DATE");

/**
 * <p>Represents the&nbsp;modification user column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85md44]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName MODIFICATION_USER = new ClientColumnName("MODIFICATION_USER");

/**
 * <p>Represents the&nbsp;start date column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85md5e]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName START_DATE = new ClientColumnName("START_DATE");

/**
 * <p>Represents the&nbsp;end date column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85md78]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName END_DATE = new ClientColumnName("END_DATE");

/**
 * <p>Represents the&nbsp;sales tax column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85md92]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName SALES_TAX = new ClientColumnName("SALES_TAX");

/**
 * <p>Represents the&nbsp;active column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mdc0]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName ACTIVE = new ClientColumnName("ACTIVE");

/**
 * <p>Represents the&nbsp;payment term id column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mdda]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName PAYMENT_TERM_ID = new ClientColumnName("PAYMENT_TERM_ID");

/**
 * <p>Represents the&nbsp;all column in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85me28]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName ALL = new ClientColumnName("ALL");

/**
 * <p>Represents the&nbsp;id column name in client_project table. It will never be null. It will be referenced in ClientInformixDAO class.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mfcf]
 */
    public static final com.topcoder.timetracker.contact.persistence.ClientColumnName ID = new ClientColumnName("ID");

/**
 * <p>Construts the ClientColumnName.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Save name to the like named variable.</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mbb8]
 * @param name non null, non empty name
 */
    ClientColumnName(String name) {        
        // your code here
    } 

/**
 * <p>Get the name</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return name</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mc28]
 * @return non null, non empty name
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>Return the string presentation.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return name</p>
 * 
 * @poseidon-object-id [Im198a7d15m110b778bd85mc4d]
 * @return non null, non empty name
 */
    public String toString() {        
        // your code here
        return null;
    } 
 }
