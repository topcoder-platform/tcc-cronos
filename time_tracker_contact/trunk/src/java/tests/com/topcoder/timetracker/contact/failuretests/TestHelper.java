/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;


import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
import com.topcoder.util.config.ConfigManager;


import java.sql.Connection;
import java.sql.Statement;

import java.util.Iterator;



/**
 * Test helper class.
 */
public class TestHelper {
    /** namespace for db connection. */
    public static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** Config file for the component. */
    public static final String CONFIG_FILE = "failure" + java.io.File.separator+ "config.xml";

    /**
     * The objectfactory naemspace.
     */
    public static final String OF_NAMESPACE = "objectfactory";
    /**
     * The search bundle configuration file.
     */
    public static final String SEARCH_BUILDER_FILE = "failure" + java.io.File.separator + "SearchBundle.xml";
    /**
     * namespace for address dao.
     */
    public static final String ADDRESS_DAO_NS = "com.topcoder.timetracker.contact.persistence.InformixAddressDAO";
    /**
     * namespace for contact dao.
     */
    public static final String CONTACT_DAO_NS = "com.topcoder.timetracker.contact.persistence.InformixContactDAO";
    	
    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. The private constructor prevents the creation of a new
     * instance.
     * </p>
     */
    private TestHelper() {
    }

    
    /**
     * <p>
     * Add the config file.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public static void setUpConfiguration() throws Exception {
        ConfigManager.getInstance().add(CONFIG_FILE);
        ConfigManager.getInstance().add(SEARCH_BUILDER_FILE);
        //        ConfigManager.getInstance()
//                     .add("com.topcoder.naming.jndiutility", "accuracy/JNDIUtils.properties",
//            ConfigManager.CONFIG_PROPERTIES_FORMAT);
    }

    /**
     * Insert data for testing.
     * @param conn database connection
     * @throws Exception into JUnit
     */
    public static void insertData(Connection conn) throws Exception{
    	 TestHelper.clearTable(conn);
    	 
    	 Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.execute("insert into id_sequences (name, next_block_start, block_size, exhausted) values ('address', 1, 1, 0)");
             stmt.execute("insert into id_sequences (name, next_block_start, block_size, exhausted) values ('contact', 1, 1, 0)");
             stmt.execute("insert into country_name(country_name_id,name,creation_date,creation_user,modification_date,modification_user)" +
             		"values(1,'usa',CURRENT,USER,CURRENT,USER)");
             stmt.execute("insert into state_name(state_name_id,name,abbreviation,creation_date,creation_user,modification_date,modification_user)" +
             		"values(1,'MA','MA',CURRENT,USER,CURRENT,USER)");
             //insert a company
             stmt.execute("insert into company(company_id,name,passcode,creation_date,creation_user,modification_date,modification_user)" +
             		"values(1,'topcoder','topcoder',CURRENT,USER,CURRENT,USER)");
             stmt.execute("insert into company(company_id,name,passcode,creation_date,creation_user,modification_date,modification_user)" +
      			"values(2,'topcoder2','topcoder2',CURRENT,USER,CURRENT,USER)");
             stmt.execute("insert into address_type(address_type_id,description,creation_date,creation_user,modification_date,modification_user)" +
             	"values(3,'company',CURRENT,USER,CURRENT,USER)");
             stmt.execute("insert into contact_type(contact_type_id,description,creation_date,creation_user,modification_date,modification_user)" +
          	"values(3,'company',CURRENT,USER,CURRENT,USER)");
             
         } catch(Exception e){
        	 e.printStackTrace();
         }finally {
             if (stmt != null) {
                 stmt.close();
             }           
         }
    }
    
    /**
     * Create database connection.
     * @return Connection
     * @throws Exception into Junit
     */
    public static Connection getConnection() throws Exception{
    	return new DBConnectionFactoryImpl(DB_CONNECTION_NAMESPACE).createConnection();
    }
    /**
     * Clear the tables.
     * @param conn Database connection
     * @throws Exception into JUnit
     */
    public static void clearTable(Connection conn) throws Exception {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("delete from address_relation");
            stmt.execute("delete from contact_relation");
            stmt.execute("delete from address_type");
            stmt.execute("delete from contact_type");
            stmt.execute("delete from address");
            stmt.execute("delete from contact");
            stmt.execute("delete from state_name");
            stmt.execute("delete from country_name");
            stmt.execute("delete from audit_detail");
            stmt.execute("delete from audit");
            stmt.execute("delete from client");
            stmt.execute("delete from project");
            stmt.execute("delete from company");
            stmt.execute("delete from id_sequences");
        } catch(Exception e){e.printStackTrace();}finally {
            if (stmt != null) {
                stmt.close();
            }           
        }
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfiguration() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }
    
    /**
     * <p>
     * Create a new instance of <code>Contact</code>.
     * The returned contact will have type set as {@link ContactType#PROJECT}.
     * </p>
     *
     * @return A new instance of <code>Contact</code> created
     */
    public static Contact getContact(int type) {
        Contact contact = new Contact();
        if ( type != 1){
        	contact.setFirstName("topcoder");
        }
        if ( type != 2){
        	contact.setLastName("usa");	
        }
        if ( type != 3){
        	contact.setEmailAddress("service@topcoder.com");	
        }
        if ( type != 4){
        	contact.setPhoneNumber("111111");	
        }
        if ( type != 5){
        	contact.setContactType(ContactType.COMPANY);	
        }
        if ( type != 6){
        	contact.setCreationUser("ivern");	
        }
        if ( type != 7){
        	contact.setModificationUser("tomerk");	
        }
        return contact;
    }
    
    /**
     * <p>
     * Create a new instance of <code>Address</code>.
     * The returned address will have type set as {@link AddressType#COMPANY}.
     * </p>
     *
     * @return A new instance of <code>Address</code> created
     */
    public static Address getAddress(int type) {
        Address address = new Address();
        if ( type != 20){
        	address.setAddressType(AddressType.COMPANY);
        }
        if ( type != 8){
        	address.setCity("Boston");
        }
        long id = 1;
        Country country = new Country();
        country.setId(id);
        State state = new State();
        state.setId(id);
        if ( type != 6){
        	address.setState(state);
        }
        if ( type != 7){
        	address.setCountry(country);	
        }
        
        if ( type !=1){
        	address.setPostalCode("123456");
        }
        if ( type != 2){
        	address.setLine1("line1");	
        }
        if ( type !=3){
        	address.setLine2("line2");	
        }
        if ( type != 4){
        	address.setCreationUser("user");
        }
        if ( type != 5){
        	address.setModificationUser("user");
        }
        return address;
    }

}
