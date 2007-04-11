/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import java.sql.Statement;

import com.cronos.im.persistence.ConfigurationException;
import com.cronos.im.persistence.rolecategories.Category;
import com.cronos.im.persistence.rolecategories.CategoryNotFoundException;
import com.cronos.im.persistence.rolecategories.CategoryValidationException;
import com.cronos.im.persistence.rolecategories.InformixRoleCategoryPersistence;
import com.cronos.im.persistence.rolecategories.RoleCategoryPersistenceException;
import com.cronos.im.persistence.rolecategories.RoleNotFoundException;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;


/**
 * <p>Failure test cases for InformixRoleCategoryPersistence.</p>
 * 
 * @author waits
 * @version 1.0
 */
public class InformixRoleCategoryPersistenceFailureTests extends BasePersistenceSupport {
    /** InformixRoleCategoryPersistence instance for testing. */
    private InformixRoleCategoryPersistence persistence = null;

    /**
     * Test the default ctor, the namespace does not exist.ConfigurationException expected.
     */
    public void testDefaultCtor_notExistNS() {
        try {
            new InformixRoleCategoryPersistence();
            fail("The namespace pass in does not exist.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the ctor with null value namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new InformixRoleCategoryPersistence(null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the ctor with empty value namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new InformixRoleCategoryPersistence(" ");
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the ctor with not exist namespace, ConfigurationException expected.
     */
    public void testCtor_notExistNamespace() {
        try {
            new InformixRoleCategoryPersistence("notExistNS");
            fail("The namespace pass in does not exist.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Helper class for invalid configration test.
     *
     * @param namespace configuration namespace
     */
    private void invalidConfiguration(String namespace) {
        try {
            new InformixRoleCategoryPersistence(namespace);
            fail("The configuration is invalid.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property
     * 'specification_factory_namespace' is missing. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration1() {
        invalidConfiguration("failure1");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property
     * 'specification_factory_namespace' is invalid. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration2() {
        invalidConfiguration("failure2");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'db_connection_factory_key'
     * is missing. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration3() {
        invalidConfiguration("failure3");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'db_connection_factory_key'
     * is invalid. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration4() {
        invalidConfiguration("failure4");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'connection_name' is missing.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration5() {
        invalidConfiguration("failure5");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the required property 'connection_name' is invlaid.
     * But the ctor will not throw any exception.
     *
     * @throws Exception into Junit
     */
    public void testCtor_invalidConfiguration6() throws Exception {
        new InformixRoleCategoryPersistence("failure6");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the optional property 'validtor_key' is invalid.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration7() {
        invalidConfiguration("failure7");
    }

    /**
     * Test the ctor with namespace, the configurtation is invalid, the optional property 'validtor_key' value of the
     * namespace is invalid. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration8() {
        invalidConfiguration("failure8");
    }

    /**
     * Test the InformixRoleCategoryPersistence(DBConnectionFactory connectionFactory, String connectionName,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionFactory, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnFactory() throws Exception {
        ObjectValidator validator = createObjectValidator();

        try {
            new InformixRoleCategoryPersistence(null, CONN_NAME, validator);
            fail("The DBConnectionFactory instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixRoleCategoryPersistence(DBConnectionFactory connectionFactory, String connectionName,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixRoleCategoryPersistence(factory, null, validator);
            fail("The connectionName instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixRoleCategoryPersistence(DBConnectionFactory connectionFactory, String connectionName,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with empty connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixRoleCategoryPersistence(factory, " ", validator);
            fail("The connectionName instance is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the getUsers, the role is null, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetUsers_nullRole() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            persistence.getUsers(null);
            fail("The role is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the getUsers, the role is empty, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetUsers_emptyRole() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            persistence.getUsers(" ");
            fail("The role is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the getUsers, the role is empty, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetUsers_notExistRole() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            persistence.getUsers("notExist");
            fail("The role is empty.");
        } catch (RoleNotFoundException e) {
            //good
        }
    }

    /**
     * Test getUsers(role), the data in database is invalid,RoleCategoryPersistenceException is expected.
     */
    public void testGetUsers_invalidUserData() throws Exception{
    	 persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);
    	 Statement stmt = this.conn.createStatement();
    	 try{
    		 stmt.executeUpdate("insert into role (role_id, role_name) values(1,'pm')");
    	 	 stmt.executeUpdate("insert into principal(principal_id,principal_name) values(-1,'ivern')");
    		 stmt.executeUpdate("insert into principal_role(principal_id, role_id) values(-1,1)");
    	 }finally{
    		 stmt.close();
    	 }
    	 try{
    		 this.persistence.getUsers("pm");
    		 fail("The principal id should not be negative.");
    	 }catch(RoleCategoryPersistenceException e){
    		 //good
    	 }
    }
    /**
     * <p>
     * Test the getCategories(String manager) with null manager. iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetCategories_nullManager() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            persistence.getCategories(null);
            fail("The manager is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the getCategories(String manager) with empty manager. iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetCategories_emptyManager() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            persistence.getCategories(" ");
            fail("The manager is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    
    /**
     * Test the getUsers, the role is empty, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testGetCategories_invalidData() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        Statement stmt = conn.createStatement();
        try{
        	stmt.executeUpdate("insert into category values(-1,'component','component','Y',CURRENT,USER,CURRENT,USER)");
        }finally{
        	stmt.close();
        }
        
        try {
            persistence.getAllCategories();
            fail("The data in database is invalid.");
        } catch (RoleCategoryPersistenceException e) {
            //good
        }
    }

    /**
     * Test updateCategories(String manager, Category[] categories) method with null manager. IllegalArgumentException
     * expected.
     */
    public void testUpdateCategories_nullManager() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            this.persistence.updateCategories(null, new Category[] { new Category(1, "manager", "desc", true) });
            fail("The manager is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updateCategories(String manager, Category[] categories) method with empty manager. IllegalArgumentException
     * expected.
     */
    public void testUpdateCategories_emptyManager() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            this.persistence.updateCategories(" ", new Category[] { new Category(1, "manager", "desc", true) });
            fail("The manager is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updateCategories(String manager, Category[] categories) method with null categories.
     * IllegalArgumentException expected.
     */
    public void testUpdateCategories_nullCategory() throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            this.persistence.updateCategories("ivern", null);
            fail("The categories is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updateCategories(String manager, Category[] categories) method with null element categories.
     * IllegalArgumentException expected.
     */
    public void testUpdateCategories_nullElementCategory()
        throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            this.persistence.updateCategories("ivern", new Category[] { new Category(1, "manager", "desc", true), null });
            fail("The categories contains null element.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updateCategories(String manager, Category[] categories) method with null element categories.
     * IllegalArgumentException expected.
     */
    public void testUpdateCategories_invalidCategory()
        throws Exception {
        persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);

        try {
            this.persistence.updateCategories("ivern", new Category[] { new Category(1, "manager", "desc", true) });
            fail("The categories violate validate.");
        } catch (CategoryValidationException e) {
            //good
        }
    }
    
    /**
     * <p>Test updateCategories method. the category does not exist.CategoryNotFoundException expected.</p>
     * @throws Exception into JUnit
     */
    public void testUpdateCategory_notExistCategory()throws Exception{
    	 persistence = new InformixRoleCategoryPersistence(TestHelper.INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE);
    	 Statement stmt = this.conn.createStatement();
    	 stmt.execute("insert into principal values(1,'ivern')");
    	 
    	 try {
             this.persistence.updateCategories("ivern", new Category[] { new Category(1, "manager", "desc", true),new Category(2, "worker", "desc", true) });
             fail("The categories does not exist.");
         } catch (CategoryNotFoundException e) {
             //good
         }
    	 
    }
}
