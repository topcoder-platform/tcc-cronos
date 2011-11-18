/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataValidator;
import com.topcoder.direct.services.project.metadata.DirectProjectServiceException;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.PersistenceException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.CompositeFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyNameValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the default implementation of DirectProjectMetadataService. It extends
 * AbstractDirectProjectMetadataService to use entityManager and log. It is injected with
 * directProjectMetadataValidator to validate metadata before insert/update.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *      xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *      xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
 *      xsi:schemaLocation=&quot;
 *      http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
 *      http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd&quot;&gt;
 *     &lt;bean class=&quot;org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor&quot; /&gt;
 *     &lt;!--  Configuration for entityManager --&gt;
 *     &lt;bean id=&quot;entityManagerFactory&quot;
 *         class=&quot;org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean&quot;&gt;
 *         &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot; /&gt;
 *         &lt;property name=&quot;jpaVendorAdapter&quot;&gt;
 *             &lt;bean class=&quot;org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter&quot;&gt;
 *                 &lt;property name=&quot;database&quot; value=&quot;INFORMIX&quot; /&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;persistenceUnitManager&quot; ref=&quot;persistenceUnitManager&quot; /&gt;
 *         &lt;property name=&quot;persistenceUnitName&quot; value=&quot;applicationPersistence&quot; /&gt;
 *         &lt;property name=&quot;jpaPropertyMap&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;hibernate.cache.use_query_cache&quot; value=&quot;true&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *     &lt;!-- Data Source --&gt;
 *     &lt;bean id=&quot;dataSource&quot; class=&quot;org.apache.commons.dbcp.BasicDataSource&quot;
 *         destroy-method=&quot;close&quot;&gt;
 *         &lt;property name=&quot;driverClassName&quot; value=&quot;com.informix.jdbc.IfxDriver&quot; /&gt;
 *         &lt;property name=&quot;url&quot;
 *             value=&quot;jdbc:informix-sqli://localhost:1526/tcs_catalog:informixserver=ol_topcoder&quot; /&gt;
 *         &lt;property name=&quot;username&quot; value=&quot;informix&quot; /&gt;
 *         &lt;property name=&quot;password&quot; value=&quot;123456&quot; /&gt;
 *         &lt;property name=&quot;minIdle&quot; value=&quot;2&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;!--  Configuration for persistence unit manager --&gt;
 *     &lt;bean id=&quot;persistenceUnitManager&quot;
 *         class=&quot;org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager&quot;&gt;
 *         &lt;property name=&quot;persistenceXmlLocations&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;classpath*:META-INF/persistence.xml&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;dataSources&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;localDataSource&quot; value-ref=&quot;dataSource&quot; /&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;defaultDataSource&quot; ref=&quot;dataSource&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;!-- Configuration for transaction managers and annotation driven manager enable --&gt;
 *     &lt;bean id=&quot;transactionManager&quot;
 *              class=&quot;org.springframework.orm.jpa.JpaTransactionManager&quot;&gt;
 *         &lt;property name=&quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;tx:annotation-driven transaction-manager=&quot;transactionManager&quot; /&gt;
 *     &lt;!-- Validators configuration --&gt;
 *     &lt;bean id=&quot;directProjectMetadataKeyValidator&quot;
 *       class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyValidatorImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;directProjectMetadataValidator&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *         &lt;property name=&quot;validatorMapping&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;!-- Example for budget - positive integer value --&gt;
 *                 &lt;entry key=&quot;1&quot; value=&quot;&circ;0*[1-9][0-9]*$&quot;/&gt;
 *                 &lt;!-- Example for project svn address --&gt;
 *                 &lt;entry key=&quot;2&quot; value=&quot;&circ;xx$&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;predefinedKeys&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;!-- Example for choose from predefined list --&gt;
 *                 &lt;entry key=&quot;4&quot; value=&quot;true&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *     &lt;!-- Services configuration --&gt;
 *     &lt;bean id=&quot;directProjectMetadataService&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataServiceImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *         &lt;property name=&quot;directProjectMetadataValidator&quot;
 *                      ref=&quot;directProjectMetadataValidator&quot; /&gt;
 *         &lt;property name=&quot;auditActionTypeIdMap&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;create&quot; value=&quot;1&quot;/&gt;
 *                 &lt;entry key=&quot;update&quot; value=&quot;2&quot;/&gt;
 *                 &lt;entry key=&quot;delete&quot; value=&quot;3&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;directProjectMetadataKeyService&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyServiceImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *         &lt;property name=&quot;directProjectMetadataKeyValidator&quot;
 *                      ref=&quot;directProjectMetadataKeyValidator&quot; /&gt;
 *         &lt;property name=&quot;auditActionTypeIdMap&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;create&quot; value=&quot;4&quot;/&gt;
 *                 &lt;entry key=&quot;update&quot; value=&quot;5&quot;/&gt;
 *                 &lt;entry key=&quot;delete&quot; value=&quot;6&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * &lt;/beans&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Initialize application context
 * ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(&quot;applicationContext.xml&quot;);
 *
 * // Retrieve services
 * DirectProjectMetadataService metadataService = (DirectProjectMetadataService) ctx
 *     .getBean(&quot;directProjectMetadataService&quot;);
 * DirectProjectMetadataKeyService metadataKeyService = (DirectProjectMetadataKeyService) ctx
 *     .getBean(&quot;directProjectMetadataKeyService&quot;);
 *
 * // Consider the user id is retrieved from session
 * int userId = 1;
 *
 * // Persist key entity
 * DirectProjectMetadataKey key = new DirectProjectMetadataKey();
 * key.setName(&quot;name3&quot;);
 * key.setDescription(&quot;some text&quot;);
 * key.setGrouping(null);
 * key.setClientId(null);
 * key.setSingle(true);
 *
 * // Create project metadata key
 * long keyId = metadataKeyService.createProjectMetadataKey(key, userId);
 *
 * // Persist metadata entity
 * DirectProjectMetadata metadata = new DirectProjectMetadata();
 * metadata.setTcDirectProjectId(5);
 * metadata.setProjectMetadataKey(key);
 * metadata.setMetadataValue(&quot;value&quot;);
 *
 * // Create project metadata
 * long metadataId = metadataService.createProjectMetadata(metadata, userId);
 *
 * // Update metadata entity
 * metadata.setTcDirectProjectId(8);
 * metadataService.updateProjectMetadata(metadata, userId);
 *
 * // Delete metadata entity
 * metadataService.deleteProjectMetadata(1, userId);
 *
 * // Retrieve project metadata by id
 * metadataService.getProjectMetadata(metadataId);
 *
 * DirectProjectMetadataDTO projectMetadata = new DirectProjectMetadataDTO();
 * projectMetadata.setProjectMetadataKeyId(key.getId());
 * projectMetadata.setMetadataValue(&quot;value&quot;);
 * // Add project metadata list
 * metadataService.addProjectMetadata(new long[] {7, 9}, projectMetadata, userId);
 *
 * // Search projects
 * MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
 * filter.setProjectMetadataKeyName(&quot;name2&quot;);
 * filter.setMetadataValue(&quot;sec&quot;);
 * filter.setMetadataValueOperator(MetadataValueOperator.LIKE);
 * List&lt;TcDirectProject&gt; projects = metadataService.searchProjects(filter);
 *
 * // Project with id 1 should be returned
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The only non-thread safe part of class is configuration, but it is done in thread
 * safe manner by Spring IoC framework. The class is thread safe under these conditions.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
@Transactional(rollbackFor = DirectProjectServiceException.class)
public class DirectProjectMetadataServiceImpl extends AbstractDirectProjectMetadataService implements
    DirectProjectMetadataService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DirectProjectMetadataServiceImpl.class.getName();

    /**
     * <p>
     * Represents the SQL string to query the project metadata by project id.
     * </p>
     */
    private static final String QUERY_PROJECT_METADATA_BY_PROJECT =
        "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
        + " WHERE directProjectMetadata.tcDirectProjectId=:tcDirectProjectId";

    /**
     * <p>
     * Represents the SQL string to query the project id.
     * </p>
     */
    private static final String QUERY_PROJECT_ID =
        "SELECT distinct tc_direct_project_id FROM direct_project_metadata INNER JOIN direct_project_metadata_key"
        + " ON direct_project_metadata.project_metadata_key_id=direct_project_metadata_key.project_metadata_key_id WHERE ";

    /**
     * <p>
     * Represents the string '%'.
     * </p>
     */
    private static final String PERCENT_SIGN = "%";

    /**
     * <p>
     * The directProjectMetadataValidator to validate metadata before insert/update.
     * </p>
     *
     * <p>
     * The default value is null. It can not be null after configuration. It is accessed by getter and modified by
     * setter. Its' legality is checked in checkInitialization method.
     * </p>
     */
    private DirectProjectMetadataValidator directProjectMetadataValidator;

    /**
     * Creates an instance of DirectProjectMetadataServiceImpl.
     */
    public DirectProjectMetadataServiceImpl() {
        // Empty
    }

    /**
     * Creates project metadata and returns the generated id for the entity.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create.
     * @return the generated id for created entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long createProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException {
        String signature = CLASS_NAME + ".createProjectMetadata(DirectProjectMetadata projectMetadata, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadata", "userId"},
            new Object[] {Helper.toString(projectMetadata), userId});

        directProjectMetadataValidator.validate(projectMetadata);

        try {
            EntityManager entityManager = getEntityManager();
            entityManager.persist(projectMetadata);

            performAudit(projectMetadata, userId, getAuditActionTypeIdMap().get(Helper.ACTION_CREATE));

            long result = projectMetadata.getId();

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {result});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to create the project metadata.", e));
        }
    }

    /**
     * Updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to update.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void updateProjectMetadata(DirectProjectMetadata projectMetadata, long userId)
        throws EntityNotFoundException, ValidationException, PersistenceException {
        String signature = CLASS_NAME + ".updateProjectMetadata(DirectProjectMetadata projectMetadata, long userId) ";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadata", "userId"},
            new Object[] {Helper.toString(projectMetadata), userId});

        directProjectMetadataValidator.validate(projectMetadata);

        try {
            EntityManager entityManager = getEntityManager();
            // Check that the entity exists
            DirectProjectMetadata obj = entityManager
                .find(DirectProjectMetadata.class, projectMetadata.getId());
            if (obj == null) {
                throw new EntityNotFoundException("The requested entity with ID [" + projectMetadata.getId()
                    + "] is not found in db.");
            }

            // Update the entity
            entityManager.merge(projectMetadata);
            performAudit(projectMetadata, userId, getAuditActionTypeIdMap().get(Helper.ACTION_UPDATE));

            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to update the project metadata.", e));
        }
    }

    /**
     * Creates or updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create or update.
     *
     * @return the id of the entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long saveProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException {
        String signature = CLASS_NAME + ".saveProjectMetadata(DirectProjectMetadata projectMetadata, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadata", "userId"},
            new Object[] {Helper.toString(projectMetadata), userId});

        directProjectMetadataValidator.validate(projectMetadata);

        try {
            EntityManager entityManager = getEntityManager();
            // Update the entity
            DirectProjectMetadata mergedEntity = entityManager.merge(projectMetadata);
            performAudit(mergedEntity, userId, getAuditActionTypeIdMap().get(Helper.ACTION_UPDATE));

            long result = mergedEntity.getId();
            LoggingWrapperUtility.logExit(logger, signature, new Object[] {result});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to create or update the project metadata.", e));
        }
    }

    /**
     * Deletes project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataId
     *            the project metadata id to delete.
     *
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void deleteProjectMetadata(long projectMetadataId, long userId) throws EntityNotFoundException,
        PersistenceException {
        String signature = CLASS_NAME + ".deleteProjectMetadata(long projectMetadataId, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataId", "userId"},
            new Object[] {projectMetadataId, userId});

        try {
            EntityManager entityManager = getEntityManager();
            DirectProjectMetadata projectMetadata = getProjectMetadata(projectMetadataId);
            if (projectMetadata == null) {
                throw new EntityNotFoundException("The requested entity with ID [" + projectMetadataId
                    + "] is not found in db.");
            }

            performAudit(projectMetadata, userId, getAuditActionTypeIdMap().get(Helper.ACTION_DELETE));

            entityManager.remove(projectMetadata);

            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to delete the project metadata.", e));
        }
    }

    /**
     * Gets project metadata.
     *
     * @param projectMetadataId
     *            the project metadata id to get.
     *
     * @return the ProjectMetadata for the id or null if the entity is not found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public DirectProjectMetadata getProjectMetadata(long projectMetadataId) throws PersistenceException {
        String signature = CLASS_NAME + ".getProjectMetadata(long projectMetadataId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataId"},
            new Object[] {projectMetadataId});

        EntityManager entityManager = getEntityManager();

        DirectProjectMetadata result = entityManager.find(DirectProjectMetadata.class, projectMetadataId);

        LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
        return result;
    }

    /**
     * Gets project list of metadata by project id.
     *
     * @param tcDirectProjectId
     *            the topcoder direct project id to get project list of metadata.
     *
     * @return the List of ProjectMetadata entities for the id or empty list if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    @SuppressWarnings("unchecked")
    public List<DirectProjectMetadata> getProjectMetadataByProject(long tcDirectProjectId)
        throws PersistenceException {
        String signature = CLASS_NAME + ".getProjectMetadataByProject(long tcDirectProjectId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"tcDirectProjectId"},
            new Object[] {tcDirectProjectId});

        try {
            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery(QUERY_PROJECT_METADATA_BY_PROJECT);
            query.setParameter("tcDirectProjectId", tcDirectProjectId);

            List<DirectProjectMetadata> result = query.getResultList();

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to retrieve the project metadata.", e));
        }
    }

    /**
     * Adds list of project metadata to the given tc project.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataList
     *            the list of project metadata to add.
     * @param tcDirectProjectId
     *            the topcoder direct project id to add list of project metadata.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataList is null or contains null elements.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long tcDirectProjectId, List<DirectProjectMetadataDTO> projectMetadataList,
        long userId) throws ValidationException, PersistenceException {
        String signature = CLASS_NAME + ".addProjectMetadata(long tcDirectProjectId,"
            + " List<DirectProjectMetadataDTO> projectMetadataList, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"tcDirectProjectId", "projectMetadataList", "userId"},
            new Object[] {tcDirectProjectId, Helper.toString(projectMetadataList), userId});

        try {
            ParameterCheckUtility.checkNotNull(projectMetadataList, "projectMetadataList");
            ParameterCheckUtility.checkNotNullElements(projectMetadataList, "projectMetadataList");
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        }

        EntityManager entityManager = getEntityManager();
        for (DirectProjectMetadataDTO dto : projectMetadataList) {
            // Find the key
            DirectProjectMetadataKey projectMetadataKey =
                entityManager.find(DirectProjectMetadataKey.class, dto.getProjectMetadataKeyId());

            createProjectMetadata(
                getDirectProjectMetadata(tcDirectProjectId, dto.getMetadataValue(), projectMetadataKey),
                userId);
        }

        LoggingWrapperUtility.logExit(logger, signature, null);
    }

    /**
     * Adds project metadata to the given list of tc projects.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param tcDirectProjectIds
     *            the topcoder direct project ids to add project metadata.
     * @param projectMetadata
     *            the project metadata to add to projects.
     *
     * @throws IllegalArgumentException
     *             if tcDirectProjectIds or projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long userId)
        throws ValidationException, PersistenceException {
        String signature = CLASS_NAME
            + ".addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"tcDirectProjectIds", "projectMetadata", "userId"},
            new Object[] {toList(tcDirectProjectIds), Helper.toString(projectMetadata), userId});

        try {
            ParameterCheckUtility.checkNotNull(tcDirectProjectIds, "tcDirectProjectIds");
            ParameterCheckUtility.checkNotNull(projectMetadata, "projectMetadata");
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        }

        EntityManager entityManager = getEntityManager();

        // Find the key
        DirectProjectMetadataKey projectMetadataKey = entityManager.find(DirectProjectMetadataKey.class,
            projectMetadata.getProjectMetadataKeyId());
        if (projectMetadataKey == null) {
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Direct project metadata key with the id '" + projectMetadata.getProjectMetadataKeyId()
                    + "' cannot be found."));
        }

        for (long tcDirectProjectId : tcDirectProjectIds) {
            createProjectMetadata(
                getDirectProjectMetadata(tcDirectProjectId, projectMetadata.getMetadataValue(), projectMetadataKey),
                userId);
        }

        LoggingWrapperUtility.logExit(logger, signature, null);
    }

    /**
     * Searches the projects by the given search filter.
     *
     * @param filter
     *            the direct project filter to search projects.
     *
     * @return the List of projects for the filter or empty list of no entity was found.
     *
     * @throws IllegalArgumentException
     *             if filter is null or invalid.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    @SuppressWarnings("unchecked")
    public List<TcDirectProject> searchProjects(DirectProjectFilter filter) throws PersistenceException {
        String signature = CLASS_NAME + ".searchProjects(DirectProjectFilter filter)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"filter"},
            new Object[] {Helper.toString(filter)});

        try {
            ParameterCheckUtility.checkNotNull(filter, "filter");

            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createNativeQuery(QUERY_PROJECT_ID + createSearchWhereClause(filter));

            List<BigDecimal> projectIds = query.getResultList();
            List<TcDirectProject> result = new ArrayList<TcDirectProject>();
            for (BigDecimal tcDirectProjectId : projectIds) {
                result.add(entityManager.find(TcDirectProject.class, tcDirectProjectId.longValue()));
            }

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to search the projects.", e));
        }
    }

    /**
     * Sets the direct project metadata validator to validate metadata before insert/update.
     *
     * @param directProjectMetadataValidator
     *            the direct project metadata validator to validate metadata before insert/update.
     */
    public void setDirectProjectMetadataValidator(DirectProjectMetadataValidator directProjectMetadataValidator) {
        this.directProjectMetadataValidator = directProjectMetadataValidator;
    }

    /**
     * This method is used to check, whether the dependencies were properly initialized for the class.
     *
     * @throws ConfigurationException
     *             if entityManager or directProjectMetadataValidator is <code>null</code>;
     *             auditActionTypeIdMap is <code>null</code>, contains <code>null</code>/empty keys,
     *             <code>null</code> values or does not contain values for keys "create", "update", "delete".
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();

        ValidationUtility.checkNotNull(directProjectMetadataValidator, "directProjectMetadataValidator",
            ConfigurationException.class);
    }

    /**
     * Converts the array to a list.
     *
     * @param array
     *            the array.
     *
     * @return the list or <code>null</code> if array is <code>null</code>.
     */
    private static List<Long> toList(long[] array) {
        if (array == null) {
            return null;
        }
        List<Long> list = new ArrayList<Long>();

        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

        return list;
    }

    /**
     * Creates a DirectProjectMetadata instance.
     *
     * @param tcDirectProjectId
     *            the tc direct project id.
     * @param metadataValue
     *            the metadata value.
     * @param projectMetadataKey
     *            the project metadata key.
     *
     * @return the DirectProjectMetadata instance.
     */
    private static DirectProjectMetadata getDirectProjectMetadata(long tcDirectProjectId, String metadataValue,
        DirectProjectMetadataKey projectMetadataKey) {
        DirectProjectMetadata metadata = new DirectProjectMetadata();
        metadata.setTcDirectProjectId(tcDirectProjectId);
        metadata.setMetadataValue(metadataValue);

        metadata.setProjectMetadataKey(projectMetadataKey);

        return metadata;
    }

    /**
     * This is a helper method, which is used to create where clause for search projects query.
     *
     * @param filter
     *            the direct project filter.
     *
     * @return the where clause for search projects query.
     *
     * @throws IllegalArgumentException
     *             if the query can not be created(the filter parameter is invalid).
     */
    private static String createSearchWhereClause(DirectProjectFilter filter) {
        if (filter instanceof CompositeFilter) {
            CompositeFilter compositeFilter = (CompositeFilter) filter;
            List<DirectProjectFilter> projectFilters = compositeFilter.getProjectFilters();
            ParameterCheckUtility.checkNotNull(projectFilters, "compositeFilter.getProjectFilters()");
            if (projectFilters.size() != 2) {
                throw new IllegalArgumentException("Size of 'compositeFilter.getProjectFilters()' should be 2.");
            }

            DirectProjectFilter left = projectFilters.get(0);
            DirectProjectFilter right = projectFilters.get(1);
            return new StringBuilder().append("(" + createSearchWhereClause(left)).append(") ").append(
                compositeFilter.getCompositeOperator().name()).append(" (").append(createSearchWhereClause(right))
                .append(")").toString();
        }

        if (filter instanceof MetadataKeyIdValueFilter) {
            MetadataKeyIdValueFilter metadataKeyIdValueFilter = (MetadataKeyIdValueFilter) filter;

            if (metadataKeyIdValueFilter.getMetadataValueOperator() == MetadataValueOperator.EQUALS) {
                return new StringBuilder().append("direct_project_metadata.project_metadata_key_id=").append(
                    metadataKeyIdValueFilter.getProjectMetadataKeyId()).append(
                    " AND LOWER(direct_project_metadata.metadata_value)='").append(
                    checkMetadataValue(metadataKeyIdValueFilter.getMetadataValue().toLowerCase())).append("'").toString();
            }
            if (metadataKeyIdValueFilter.getMetadataValueOperator() == MetadataValueOperator.LIKE) {
                return new StringBuilder().append("direct_project_metadata.project_metadata_key_id=").append(
                    metadataKeyIdValueFilter.getProjectMetadataKeyId()).append(
                    " AND LOWER(direct_project_metadata.metadata_value) LIKE '%").append(
                    checkMetadataValue(metadataKeyIdValueFilter.getMetadataValue().toLowerCase())).append("%'").toString();
            }
        }

        if (filter instanceof MetadataKeyNameValueFilter) {
            MetadataKeyNameValueFilter metadataKeyNameValueFilter = (MetadataKeyNameValueFilter) filter;

            if (metadataKeyNameValueFilter.getMetadataValueOperator() == MetadataValueOperator.EQUALS) {
                return new StringBuilder().append("direct_project_metadata_key.name='").append(
                    metadataKeyNameValueFilter.getProjectMetadataKeyName()).append(
                    "' AND LOWER(direct_project_metadata.metadata_value)='").append(
                    checkMetadataValue(metadataKeyNameValueFilter.getMetadataValue().toLowerCase())).append("'").toString();
            }
            if (metadataKeyNameValueFilter.getMetadataValueOperator() == MetadataValueOperator.LIKE) {
                return new StringBuilder().append("direct_project_metadata_key.name='").append(
                    metadataKeyNameValueFilter.getProjectMetadataKeyName()).append(
                    "' AND LOWER(direct_project_metadata.metadata_value) LIKE '%").append(
                    checkMetadataValue(metadataKeyNameValueFilter.getMetadataValue().toLowerCase())).append("%'").toString();
            }
        }

        throw new IllegalArgumentException("'filter' is not of the expected type.");
    }

    /**
     * Checks if the value contains '%'.
     *
     * @param value
     *            the value.
     *
     * @return the value.
     *
     * @throws IllegalArgumentException
     *             if value contains '%'.
     */
    private static String checkMetadataValue(String value) {
        if ((value != null) && value.contains(PERCENT_SIGN)) {
            throw new IllegalArgumentException("The metadata value cannot contain '%'.");
        }

        return value;
    }

    /**
     * This is a helper method, which is used to perform audit in create/update/delete operations.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param auditActionTypeId
     *            the id of audit action type.
     * @param projectMetadata
     *            the direct project metadata.
     *
     * @throws javax.persistence.PersistenceException
     *             if any problem with persistence occurs.
     */
    private void performAudit(DirectProjectMetadata projectMetadata, long userId, int auditActionTypeId) {
        DirectProjectMetadataAudit audit = new DirectProjectMetadataAudit();

        audit.setAuditActionTypeId(auditActionTypeId);
        audit.setActionDate(new Date());
        audit.setActionUserId(userId);

        audit.setProjectMetadataId(projectMetadata.getId());
        audit.setTcDirectProjectId(projectMetadata.getTcDirectProjectId());
        audit.setProjectMetadataKeyId(projectMetadata.getProjectMetadataKey().getId());
        audit.setMetadataValue(projectMetadata.getMetadataValue());

        EntityManager entityManager = getEntityManager();
        entityManager.persist(audit);
    }
}
