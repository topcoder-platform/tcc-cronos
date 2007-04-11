/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import com.cronos.im.persistence.ConfigurationException;
import com.cronos.im.persistence.EntityStatusValidationException;
import com.cronos.im.persistence.InformixEntityStatusTracker;

import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.Status;
import com.topcoder.database.statustracker.persistence.RecordNotFoundException;
import com.topcoder.database.statustracker.persistence.StatusTrackerPersistenceException;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;


/**
 * <p>Failure test cases for InformixEntityStatusTracker.</p>
 * 
 * @author waits
 * @version 1.0
 */
public class InformixEntityStatusTrackerFailureTests extends BasePersistenceSupport {
    /** InformixEntityStatusTracker to test against. */
    private InformixEntityStatusTracker statusTracker = null;

    /**
     * Test the default ctor, the default ns does not exist,ConfigurationException expected.
     */
    public void testDefaultCtor() {
        try {
            new InformixEntityStatusTracker();
            fail("The default namespace does not exist.");
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
            new InformixEntityStatusTracker(null);
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
            new InformixEntityStatusTracker(" ");
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
            new InformixEntityStatusTracker("notExistNS");
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
            new InformixEntityStatusTracker(namespace);
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
        new InformixEntityStatusTracker("failure6");
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
     * Test the InformixEntityStatusTracker(DBConnectionFactory connectionFactory, String connectionName,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionFactory, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnFactory() throws Exception {
        ObjectValidator validator = createObjectValidator();

        try {
            new InformixEntityStatusTracker(null, CONN_NAME, validator);
            fail("The DBConnectionFactory instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixEntityStatusTracker(DBConnectionFactory connectionFactory, String connectionName,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with null connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixEntityStatusTracker(factory, null, validator);
            fail("The connectionName instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixEntityStatusTracker(DBConnectionFactory connectionFactory, String connectionName,
     * com.topcoder.util.datavalidator.ObjectValidator validator) method, with empty connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyConnName() throws Exception {
        ObjectValidator validator = createObjectValidator();
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixEntityStatusTracker(factory, " ", validator);
            fail("The connectionName instance is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who) with null newStatus instance, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_nullEntityKey() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.setStatus(null, new Status(1), "tomerk");
            fail("The EntityKey is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who) with null newStatus instance, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_nullStatus() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.setStatus(TestHelper.createEntityKey("entity"), null, "tomerk");
            fail("The Status is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who) with invalid newStatus instance, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_invalidStatus() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.setStatus(TestHelper.createEntityKey("entity"), new Status(100), "tomerk");
            fail("The Status is not the valid status of the EntityKey.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who) with null who instance, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_nullWho() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.setStatus(TestHelper.createEntityKey("entity"),
                TestHelper.createEntityKey("entity").getType().getValidStatuses()[0], null);
            fail("The user is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who) with empty who instance, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_emptyWho() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.setStatus(TestHelper.createEntityKey("entity"),
                TestHelper.createEntityKey("entity").getType().getValidStatuses()[0], "  ");
            fail("The user is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who). The entiy can not pass
     * validation.EntityStatusValidationException expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_validationError() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            this.statusTracker.setStatus(TestHelper.createEntityKey("notEntity"),
                TestHelper.createEntityKey("notEntity").getType().getValidStatuses()[0], "John");
            fail("The entity is invalid for validation.");
        } catch (EntityStatusValidationException e) {
            //good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who). The Entity type within the entity instance was
     * not previously registered.RecordNotFoundException expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_notRegister() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.setStatus(TestHelper.createEntityKey("entity"),
                TestHelper.createEntityKey("entity").getType().getValidStatuses()[0], "john");
            fail("Now no record is registered in database.");
        } catch (RecordNotFoundException e) {
            //good
        }
    }

    /**
     * Test setStatus(EntityKey instance, Status newStatus, String who). The Entity type within the entity instance was
     * not previously registered.RecordNotFoundException expected.
     *
     * @throws Exception into Junit
     */
    public void testSetStatus_invalidConnectionName() throws Exception {
        statusTracker = new InformixEntityStatusTracker("failure6");

        try {
            statusTracker.setStatus(TestHelper.createEntityKey("entity"),
                TestHelper.createEntityKey("entity").getType().getValidStatuses()[0], "john");
            fail("Now no record is registered in database.");
        } catch (StatusTrackerPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getCurrentStatus(EntityKey instance) method. The instance is null, iae expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetCurrentStatus_nullValue() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            this.statusTracker.getCurrentStatus(null);
            fail("The EntityKey instance to get is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getCurrentStatus(EntityKey instance) method. The instance does not exist in storage,
     * RecordNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetCurrentStatus_notExistInstance()
        throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.getCurrentStatus(TestHelper.createEntityKey("entity"));
            fail("The EntityKey instance to get does not exist in persistence storage.");
        } catch (RecordNotFoundException e) {
            //good
        }
    }

    /**
     * Test getStatusHistory(EntityKey key) method with null value key, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testGetStatusHistory_nullValue() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.getStatusHistory(null);
            fail("The EntityKey instance is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getStatusHistory(EntityKey key) method with not exist entity, RecordNotFoundException expected.
     *
     * @throws Exception into Junit
     */
    public void testGetStatusHistory_notExistEntity() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.getStatusHistory(TestHelper.createEntityKey("entity"));
            fail("The EntityKey instance to get does not exist in persistence storage.");
        } catch (RecordNotFoundException e) {
            //good
        }
    }

    /**
     * Test findByStatus(Entity type, Status[] statuses) method with null type value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testFindByStatus_nullType() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.findByStatus(null, TestHelper.createEntity("entity").getValidStatuses());
            fail("The entity type is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findByStatus(Entity type, Status[] statuses) method with null entity status array, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testFindByStatus_nullStatusArray() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.findByStatus(TestHelper.createEntity("entity"), null);
            fail("The entity status array is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findByStatus(Entity type, Status[] statuses) method with null element entity status array, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testFindByStatus_nullStatusElement() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            Entity type = TestHelper.createEntity("entity");
            statusTracker.findByStatus(type, new Status[] { type.getValidStatuses()[0], null });
            fail("The entity status array is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findByStatus(Entity type, Status[] statuses) method with empty entity status array, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testFindByStatus_emptyStatusArray() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            Entity type = TestHelper.createEntity("entity");
            statusTracker.findByStatus(type, new Status[] {  });
            fail("The entity status array is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findByStatus(Entity type, Status[] statuses) method with not valid entity status array, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testFindByStatus_notValideStatus() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            statusTracker.findByStatus(TestHelper.createEntity("entity"), new Status[] { new Status(100) });
            fail("The entity status array contains invalid status.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test findByStatus(Entity type, Status[] statuses) method with not exist entity type, RecordNotFoundException
     * expected.
     *
     * @throws Exception into Junit
     */
    public void testFindByStatus_notExistEntity() throws Exception {
        statusTracker = new InformixEntityStatusTracker(TestHelper.INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE);

        try {
            Entity type = TestHelper.createEntity("entity");
            statusTracker.findByStatus(type, new Status[] { type.getValidStatuses()[0] });
            fail("The entity type does not exist.");
        } catch (RecordNotFoundException e) {
            //good
        }
    }
}
