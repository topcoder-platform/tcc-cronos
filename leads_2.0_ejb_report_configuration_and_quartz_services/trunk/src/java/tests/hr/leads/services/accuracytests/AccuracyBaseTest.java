/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.model.jpa.SystemConfigurationProperty;

import java.io.FileReader;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import junit.framework.TestCase;

/**
 * The base class for the accuracy test.
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyBaseTest extends TestCase {

    /**
     * The entity manager factory.
     */
    private static EntityManagerFactory entityManagerFactory;

    /**
     * Set the value of private field.
     *
     * @param type the type of the object
     * @param object the object
     * @param fieldName the name of the field
     * @param value the value to be set
     * @throws Exception to JUnit
     */
    public static void setField(Class<?> type, Object object, String fieldName, Object value)
        throws Exception {
        Field field = type.getDeclaredField(fieldName);
        field.setAccessible(true);
        try {
            field.set(object, value);
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * Create a new instance of entity manager.
     *
     * @return a new instance of entity manager
     */
    public static EntityManager createEntityManager() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        }

        return entityManagerFactory.createEntityManager();
    }

    /**
     * Clear database.
     *
     * @throws Exception to JUnit
     */
    public static void clearDB() throws Exception {
        runSqlFile("test_files/accuracytests/clear.sql", true);
    }

    /**
     * Read and run all SQL statements in the given file.
     *
     * @param file the file
     * @param ignoreError whether errors will be ignored
     * @throws Exception to JUnit
     */
    private static void runSqlFile(String file, boolean ignoreError) throws Exception {
        StringBuilder sb = new StringBuilder();
        FileReader reader = new FileReader(file);
        try {
            char[] buffer = new char[4096];
            int len = reader.read(buffer);
            while (len != -1) {
                sb.append(buffer, 0, len);
                len = reader.read(buffer);
            }
        } finally {
            reader.close();
        }

        EntityManager entityManager = createEntityManager();
        for (String statement : sb.toString().split(";")) {
            if (!statement.trim().isEmpty()) {
                try {
                    entityManager.getTransaction().begin();
                    entityManager.createNativeQuery(statement).executeUpdate();
                    entityManager.getTransaction().commit();
                } catch (PersistenceException e) {
                    if (!ignoreError) {
                        throw e;
                    }

                    if (entityManager.getTransaction().isActive()) {
                        entityManager.getTransaction().rollback();
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Sets up the data in the database.
     * </p>
     *
     * @param entityManager the entity manager.
     */
    public static void setupTest(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        SystemConfigurationProperty property = new SystemConfigurationProperty();
        property.setName("name");
        property.setValue("value");
        entityManager.persist(property);

        transaction.commit();
    }
}
