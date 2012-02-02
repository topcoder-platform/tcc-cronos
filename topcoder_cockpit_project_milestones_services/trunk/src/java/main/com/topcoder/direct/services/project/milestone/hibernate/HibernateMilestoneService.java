/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.milestone.EntityNotFoundException;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of MilestoneService.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Technically this class is not thread safe as it is mutable. But in practice, its state
 * won't change after the fields being injected through Spring, hence it is effectively thread safe when used in
 * Spring environment and initialized only once.
 * </p>
 * *
 * <p>
 * <b>Sample Config:</b>
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *     xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; xmlns:util=&quot;
 *     http://www.springframework.org/schema/util&quot;
 *     xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot; xmlns:tx=&quot;
 *     http://www.springframework.org/schema/tx&quot;
 *     xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
 *        http://www.springframework.org/schema/util
 *        http://www.springframework.org/schema/util/spring-util-2.5.xsd
 *        http://www.springframework.org/schema/aop
 *        http://www.springframework.org/schema/aop/spring-aop-2.5.xsd
 *        http://www.springframework.org/schema/tx
 *        http://www.springframework.org/schema/tx/spring-tx-2.5.xsd&quot;&gt;
 *
 *     &lt;bean id=&quot;dataSource&quot; class=&quot;org.apache.commons.dbcp.BasicDataSource&quot;&gt;
 *         &lt;property name=&quot;driverClassName&quot; value=&quot;com.informix.jdbc.IfxDriver&quot; /&gt;
 *         &lt;property name=&quot;url&quot;
 *             value=&quot;jdbc:informix-sqli://192.168.1.107:9088/Test:INFORMIXSERVER=ol_svr_custom&quot; /&gt;
 *         &lt;property name=&quot;username&quot; value=&quot;informix&quot; /&gt;
 *         &lt;property name=&quot;password&quot; value=&quot;123456&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;sessionFactory&quot;
 *         class=&quot;org.springframework.orm.hibernate3.LocalSessionFactoryBean&quot;&gt;
 *         &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot; /&gt;
 *         &lt;property name=&quot;mappingResources&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;mapping/ResponsiblePerson.hbm.xml&lt;/value&gt;
 *                 &lt;value&gt;mapping/Milestone.hbm.xml&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;hibernateProperties&quot;&gt;
 *             &lt;props&gt;
 *                 &lt;prop key=&quot;hibernate.dialect&quot;&gt;org.hibernate.dialect.InformixDialect&lt;/prop&gt;
 *                 &lt;!-- prop key=&quot;hibernate.show_sql&quot;&gt;true&lt;/prop --&gt;
 *             &lt;/props&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;logger&quot; class=&quot;com.topcoder.util.log.LogManager&quot;
 *         factory-method=&quot;getLog&quot;&gt;
 *         &lt;constructor-arg value=&quot;myLogger&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;milestoneService&quot;
 *         class=&quot;com.topcoder.direct.services.project.milestone.hibernate.HibernateMilestoneService&quot;&gt;
 *         &lt;property name=&quot;logger&quot; ref=&quot;logger&quot; /&gt;
 *         &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;!-- the transactional advice (what 'happens'; see the &lt;aop:advisor/&gt; bean
 *         below) --&gt;
 *     &lt;tx:advice id=&quot;txAdvice&quot; transaction-manager=&quot;txManager&quot;&gt;
 *         &lt;!-- the transactional semantics... --&gt;
 *         &lt;tx:attributes&gt;
 *             &lt;!-- all methods starting with 'get' are read-only --&gt;
 *             &lt;tx:method name=&quot;get*&quot; read-only=&quot;true&quot; /&gt;
 *             &lt;!-- other methods use the default transaction settings --&gt;
 *             &lt;tx:method name=&quot;*&quot; /&gt;
 *         &lt;/tx:attributes&gt;
 *     &lt;/tx:advice&gt;
 *
 *     &lt;bean id=&quot;txManager&quot;
 *         class=&quot;org.springframework.orm.hibernate3.HibernateTransactionManager&quot;&gt;
 *         &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot; /&gt;
 *     &lt;/bean&gt;
 *
 * &lt;/beans&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <b>Sample Usage:</b>
 *
 * <pre>
 * // MilestoneService
 * MilestoneService milestoneService = (MilestoneService) APP_CONTEXT.getBean(&quot;milestoneService&quot;);
 *
 * // Add
 * Milestone milestone1 = createMilestone(1);
 * long id1 = milestoneService.add(milestone1);
 *
 * // Add List
 * Milestone milestone2 = createMilestone(2);
 * milestone2.setDueDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
 * milestone2.setCompleted(false);
 * Milestone milestone3 = createMilestone(3);
 * milestone3.setCompleted(false);
 *
 * List&lt;Milestone&gt; milestones = Arrays.asList(milestone2, milestone3);
 *
 * milestoneService.add(milestones);
 *
 * // Get
 * Milestone oneResult = milestoneService.get(id1);
 *
 * // Get List
 * List&lt;Long&gt; ids = Arrays.asList(milestone2.getId(), milestone3.getId());
 * List&lt;Milestone&gt; listResult = milestoneService.get(ids);
 *
 * // Update
 * milestone1.setName(&quot;new_name1&quot;);
 * milestoneService.update(milestone1);
 *
 * // Update List
 * milestone2.setName(&quot;new_name2&quot;);
 * milestone3.setName(&quot;new_name3&quot;);
 * milestoneService.update(milestones);
 *
 * // Delete
 * milestoneService.delete(id1);
 *
 * // Delete List
 * milestoneService.delete(ids);
 *
 * // Get All
 * List&lt;MilestoneStatus&gt; requestedStatuses = new ArrayList&lt;MilestoneStatus&gt;();
 * requestedStatuses.add(MilestoneStatus.OVERDUE);
 * requestedStatuses.add(MilestoneStatus.UPCOMING);
 * requestedStatuses.add(MilestoneStatus.COMPLETED);
 * List&lt;Milestone&gt; result = milestoneService.getAll(1, requestedStatuses, SortOrder.ASCENDING);
 *
 * // Get All In Month
 * result = milestoneService.getAllInMonth(1, 12, 2011, requestedStatuses);
 * </pre>
 *
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class HibernateMilestoneService implements InitializingBean, MilestoneService {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = HibernateMilestoneService.class.getName();

    /**
     * <p>
     * Represent the name of query.
     * </p>
     */
    private static final String SQL_GET_LIST = "getList";

    /**
     * <p>
     * Represent the name of query.
     * </p>
     */
    private static final String SQL_CHECK_MILESTONE_ID = "checkMilestoneId";

    /**
     * <p>
     * Represent the name of query.
     * </p>
     */
    private static final String SQL_CHECK_MILESTONE_IDS = "checkMilestoneIds";

    /**
     * <p>
     * Represent the name of query.
     * </p>
     */
    private static final String SQL_GET_ALL = "getAll";

    /**
     * <p>
     * Represent the number: 12.
     * </p>
     */
    private static final int TWELVE = 12;

    /**
     * <p>
     * The logger to be used for logging errors and debug information.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * The Hibernate session factory to be used when retrieving session for accessing entities stored in database.
     * Cannot be null after initialization (validation is performed in afterPropertiesSet() method). Initialized by
     * Spring setter injection.
     * </p>
     */
    private SessionFactory sessionFactory;

    /**
     * Empty constructor.
     */
    public HibernateMilestoneService() {
        // Empty
    }

    /**
     * This method checks that all required injection fields are in fact provided.
     *
     * @throws ProjectMilestoneManagementConfigurationException
     *             If there are required injection fields that are not injected
     */
    public void afterPropertiesSet() {
        ValidationUtility.checkNotNull(logger, "logger", ProjectMilestoneManagementConfigurationException.class);
        ValidationUtility.checkNotNull(sessionFactory, "sessionFactory",
            ProjectMilestoneManagementConfigurationException.class);
    }

    /**
     * This method adds the given milestone.
     *
     * @param milestone
     *            the milestone to add
     * @return the ID of the milestone
     * @throws IllegalArgumentException
     *             If milestone is null
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public long add(Milestone milestone) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".add(Milestone milestone)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestone"}, new Object[] {milestone},
            true, Level.DEBUG);

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNull(milestone, "milestone");

            // Save the entity
            getSession().save(milestone);

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, new Object[] {milestone.getId()}, entranceTimestamp);

            // Return entity id
            return milestone.getId();
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }
    }

    /**
     * This method adds the given milestones.
     *
     * @param milestones
     *            the milestones to add
     * @throws IllegalArgumentException
     *             If milestones is null/empty, or has null elements
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void add(List<Milestone> milestones) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".add(List<Milestone> milestones)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestones"},
            new Object[] {milestones}, true, Level.DEBUG);

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNullNorEmpty(milestones, "milestones");
            ParameterCheckUtility.checkNotNullElements(milestones, "milestones");

            // Save the entities
            for (Milestone milestone : milestones) {
                getSession().save(milestone);
            }

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }
    }

    /**
     * This method updates the given milestone.
     *
     * @param milestone
     *            the milestone to update
     * @throws IllegalArgumentException
     *             If milestone is null
     * @throws EntityNotFoundException
     *             If the milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void update(Milestone milestone) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".update(Milestone milestone)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestone"}, new Object[] {milestone},
            true, Level.DEBUG);

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNull(milestone, "milestone");

            // Check entity existence
            checkEntityExist(milestone.getId());
            // Update the entity
            getSession().update(milestone);

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }
    }

    /**
     * Check the entity whether exists.
     *
     * @param milestoneId
     *            the milestone's id
     * @throws EntityNotFoundException
     *             If the milestone is not found
     * @throws HibernateException
     *             if any persistence error occurs
     */
    private void checkEntityExist(long milestoneId) throws EntityNotFoundException {
        Query query = getSession().getNamedQuery(SQL_CHECK_MILESTONE_ID);
        query.setParameter("id", milestoneId);
        boolean entityNotExist = query.list().isEmpty();
        if (entityNotExist) {
            throw new EntityNotFoundException("The entity is not found with id[" + milestoneId + "]");
        }
    }

    /**
     * This method updates the given milestones.
     *
     * @param milestones
     *            the milestones to update
     * @throws IllegalArgumentException
     *             If milestones is null/empty, or has null elements
     * @throws EntityNotFoundException
     *             If any milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void update(List<Milestone> milestones) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".update(List<Milestone> milestones)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestones"},
            new Object[] {milestones}, true, Level.DEBUG);

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNullNorEmpty(milestones, "milestones");
            ParameterCheckUtility.checkNotNullElements(milestones, "milestones");

            // Get ids
            List<Long> ids = new ArrayList<Long>();
            for (Milestone milestone : milestones) {
                ids.add(milestone.getId());
            }
            // Check entity existence
            checkEntityExist(ids);
            // Update the entities
            for (Milestone milestone : milestones) {
                getSession().update(milestone);
            }

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }
    }

    /**
     * Check the entities whether exist.
     *
     * @param milestoneIds
     *            the milestones' id
     * @throws EntityNotFoundException
     *             If the milestone is not found
     * @throws HibernateException
     *             if any persistence error occurs
     */
    @SuppressWarnings("unchecked")
    private void checkEntityExist(List<Long> milestoneIds) throws EntityNotFoundException {
        Query query = getSession().getNamedQuery(SQL_CHECK_MILESTONE_IDS);
        query.setParameterList("ids", milestoneIds);
        List<Long> result = query.list();
        if (result.size() != milestoneIds.size()) {
            throw new EntityNotFoundException("Some entity is not found with ids[" + milestoneIds.toString() + "]");
        }
    }

    /**
     * This method deletes the given milestone.
     *
     * @param milestoneId
     *            the milestone's id to delete
     * @throws EntityNotFoundException
     *             If the milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void delete(long milestoneId) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".delete(long milestoneId)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestoneId"},
            new Object[] {milestoneId}, true, Level.DEBUG);

        try {
            // Get the entity
            Milestone milestone = (Milestone) getSession().get(Milestone.class, milestoneId);
            if (milestone == null) {
                // throw not found exception
                throw new EntityNotFoundException("The entity is not found with id[" + milestoneId + "]");
            }
            // Delete entity
            getSession().delete(milestone);

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, null, entranceTimestamp);
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }

    }

    /**
     * This method deletes the given milestones.
     *
     * @param milestoneIds
     *            the ids of milestones to delete
     * @throws IllegalArgumentException
     *             If milestoneIds is null/empty, or has null elements
     * @throws EntityNotFoundException
     *             If any milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void delete(List<Long> milestoneIds) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".delete(List<Long> milestoneIds)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestoneIds"},
            new Object[] {milestoneIds}, true, Level.DEBUG);

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNullNorEmpty(milestoneIds, "milestoneIds");
            ParameterCheckUtility.checkNotNullElements(milestoneIds, "milestoneIds");

            // Get the entities
            List<Milestone> milestones = new ArrayList<Milestone>();
            for (Long id : milestoneIds) {
                Milestone milestone = (Milestone) getSession().get(Milestone.class, id);
                if (milestone == null) {
                    // throw not found exception
                    throw new EntityNotFoundException("The entity is not found with id[" + id + "]");
                }
                milestones.add(milestone);
            }
            // Delete entities
            for (Milestone milestone : milestones) {
                getSession().delete(milestone);
            }

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }
    }

    /**
     * This method gets the milestone with the given ID. If not found, returns null.
     *
     * @param milestoneId
     *            the ID of the milestone to get
     * @return the milestone
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public Milestone get(long milestoneId) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".get(long milestoneId)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestoneId"},
            new Object[] {milestoneId}, true, Level.DEBUG);

        try {
            // Get the entity
            Milestone res = (Milestone) getSession().get(Milestone.class, milestoneId);
            // set status
            setStatus(res);

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, new Object[] {res}, entranceTimestamp);

            // Return entity
            return res;
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.DEBUG);
        }
    }

    /**
     * This method gets the milestones with the given IDs. If any given milestone is not found, in its index a null
     * is returned, so the return list has the same size as the input list.
     *
     * @param milestoneIds
     *            the IDs of the milestones to get
     * @return the milestones
     * @throws IllegalArgumentException
     *             If milestoneIds is null/empty, or has null elements
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    @SuppressWarnings("unchecked")
    public List<Milestone> get(List<Long> milestoneIds) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".get(List<Long> milestoneIds)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"milestoneIds"},
            new Object[] {milestoneIds}, true, Level.DEBUG);

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNullNorEmpty(milestoneIds, "milestoneIds");
            ParameterCheckUtility.checkNotNullElements(milestoneIds, "milestoneIds");

            // Get the named query
            Query query = getSession().getNamedQuery(SQL_GET_LIST);
            // Set the parameters
            query.setParameterList("ids", milestoneIds);
            // Get the entities
            List<Milestone> milestones = query.list();

            Map<Long, Milestone> container = new HashMap<Long, Milestone>();
            // Set status and store in new container
            for (Milestone milestone : milestones) {
                setStatus(milestone);
                container.put(milestone.getId(), milestone);
            }

            // Set order
            List<Milestone> res = new ArrayList<Milestone>(milestoneIds.size());
            for (Long id : milestoneIds) {
                res.add(container.get(id));
            }

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, new Object[] {res}, entranceTimestamp);

            // Return entity
            return res;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.DEBUG);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.DEBUG);
        }
    }

    /**
     * This method gets the milestones for the given project, for the given statuses, and sorted in the given
     * order. If none found, returns an empty list.
     *
     * @param sortOrder
     *            the order of the milestones grouped by status - default is ascending
     * @param requestedStatuses
     *            the statuses whose milestones are to be filtered by
     * @param projectId
     *            the ID of the project
     * @return the milestones
     * @throws IllegalArgumentException
     *             If requestedStatuses contains null or duplicate elements
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    @SuppressWarnings("unchecked")
    public List<Milestone> getAll(long projectId, List<MilestoneStatus> requestedStatuses, SortOrder sortOrder)
        throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".getAll(long projectId, List<MilestoneStatus> requestedStatuses,"
            + " SortOrder sortOrder)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"projectId", "requestedStatuses",
            "sortOrder"}, new Object[] {projectId, requestedStatuses, sortOrder}, true, Level.DEBUG);

        try {
            // Check parameter
            Set<MilestoneStatus> statuses = checkRequestedStatuses(requestedStatuses);

            // Get the named query
            Query query = getSession().getNamedQuery(SQL_GET_ALL);
            // Set the parameters
            query.setParameter("projectId", projectId);
            // Get the entities
            List<Milestone> milestones = query.list();

            // Get ordered milestones
            List<Milestone> res = getOrderedMileStone(requestedStatuses, sortOrder, statuses, milestones, -1, -1);

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, new Object[] {res}, entranceTimestamp);

            return res;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }
    }

    /**
     * Get ordered milestones.
     *
     * @param sortOrder
     *            the order of the milestones grouped by status - default is ascending
     * @param requestedStatuses
     *            the statuses whose milestones are to be filtered by
     * @param statuses
     *            the set of statuses
     * @param milestones
     *            the milestones with same project id
     * @param startTime
     *            the start time
     * @param endTime
     *            the end time
     * @return the ordered milestones
     */
    private List<Milestone> getOrderedMileStone(List<MilestoneStatus> requestedStatuses, SortOrder sortOrder,
        Set<MilestoneStatus> statuses, List<Milestone> milestones, long startTime, long endTime) {
        // Create statues filters
        boolean filterFlag = (requestedStatuses != null) && (!requestedStatuses.isEmpty());
        boolean filterOverdue = !filterFlag || statuses.contains(MilestoneStatus.OVERDUE);
        boolean filterUpcoming = !filterFlag || statuses.contains(MilestoneStatus.UPCOMING);
        boolean filterCompleted = !filterFlag || statuses.contains(MilestoneStatus.COMPLETED);

        List<Milestone> overdueGroup = new ArrayList<Milestone>();
        List<Milestone> upcomingGroup = new ArrayList<Milestone>();
        List<Milestone> completedGroup = new ArrayList<Milestone>();

        // Set status and filter
        for (Milestone milestone : milestones) {
            if (startTime != -1) {
                long dateTime = milestone.getDueDate().getTime();
                if (dateTime < startTime || dateTime >= endTime) {
                    continue;
                }
            }
            setStatus(milestone);
            if (filterOverdue && MilestoneStatus.OVERDUE.equals(milestone.getStatus())) {
                overdueGroup.add(milestone);
            } else if (filterUpcoming && MilestoneStatus.UPCOMING.equals(milestone.getStatus())) {
                upcomingGroup.add(milestone);
            } else if (filterCompleted && MilestoneStatus.COMPLETED.equals(milestone.getStatus())) {
                completedGroup.add(milestone);

            }
        }

        List<Milestone> res = new ArrayList<Milestone>();
        MilestoneComparable milestoneComparable = new MilestoneComparable(SortOrder.DESCENDING.equals(sortOrder));
        // Sort the order
        if (filterOverdue) {
            Collections.sort(overdueGroup, milestoneComparable);
            res.addAll(overdueGroup);
        }
        if (filterUpcoming) {
            Collections.sort(upcomingGroup, milestoneComparable);
            res.addAll(upcomingGroup);
        }
        if (filterCompleted) {
            Collections.sort(completedGroup, milestoneComparable);
            res.addAll(completedGroup);
        }
        return res;
    }

    /**
     * Check the requestedStatuses.
     *
     * @param requestedStatuses
     *            the requestedStatuses
     * @return the statues
     * @throws IllegalArgumentException
     *             If requestedStatuses contains null or duplicate elements
     */
    private Set<MilestoneStatus> checkRequestedStatuses(List<MilestoneStatus> requestedStatuses) {
        if (requestedStatuses == null) {
            return new HashSet<MilestoneStatus>();
        }
        ParameterCheckUtility.checkNotNullElements(requestedStatuses, "requestedStatuses");
        // Check duplicate
        Set<MilestoneStatus> statuses = new HashSet<MilestoneStatus>(requestedStatuses);
        if (statuses.size() != requestedStatuses.size()) {
            throw new IllegalArgumentException("requestedStatuses contain duplicate elements");
        }
        return statuses;
    }

    /**
     * This method gets the milestones for the given project, for the given statuses in the given month. If none
     * found, returns an empty list.
     *
     * @param month
     *            the 1-based index of the month
     * @param requestedStatuses
     *            the statuses whose milestones are to be filtered by
     * @param year
     *            the year number
     * @param projectId
     *            the ID of the project
     * @return the milestones
     * @throws IllegalArgumentException
     *             If requestedStatuses contains null or duplicate elements, or month is not in [1,12]
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    @SuppressWarnings("unchecked")
    public List<Milestone> getAllInMonth(long projectId, int month, int year,
        List<MilestoneStatus> requestedStatuses) throws ProjectMilestoneManagementException {
        final String signature = CLASS_NAME + ".getAllInMonth(long projectId, int month, int year,"
            + " List<MilestoneStatus> requestedStatuses)";
        final Date entranceTimestamp = new Date();
        // Log entrance
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"projectId", "month", "year",
            "requestedStatuses"}, new Object[] {projectId, month, year, requestedStatuses}, true, Level.DEBUG);

        try {
            // Check parameter
            Set<MilestoneStatus> statuses = checkRequestedStatuses(requestedStatuses);
            ParameterCheckUtility.checkInRange(month, 1, TWELVE, true, true, "month");

            // Get the named query
            Query query = getSession().getNamedQuery(SQL_GET_ALL);
            // Set the parameters
            query.setParameter("projectId", projectId);
            // Get the entities
            List<Milestone> milestones = query.list();

            // Calculate the start and end time
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(year, month - 1, 0);
            long startTime = calendar.getTimeInMillis();
            if (month != TWELVE) {
                calendar.set(year, month, 0);
            } else {
                calendar.set(year + 1, 0, 0);
            }
            long endTime = calendar.getTimeInMillis();

            // Get ordered milestones
            List<Milestone> res = getOrderedMileStone(requestedStatuses, null, statuses, milestones, startTime,
                endTime);

            // Log exit
            LoggingWrapperUtility.logExit(logger, signature, new Object[] {res}, entranceTimestamp);

            return res;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(logger, signature, e, true, Level.ERROR);
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(logger, signature, new ProjectMilestoneManagementException(
                "HibernateException occurs while accessing to db", e), true, Level.ERROR);
        }
    }

    /**
     * Set the status.
     *
     * @param milestone
     *            the milestone
     */
    private static void setStatus(Milestone milestone) {
        if (milestone == null) {
            return;
        }
        if (milestone.isCompleted()) {
            milestone.setStatus(MilestoneStatus.COMPLETED);
        } else {
            if (milestone.getDueDate().getTime() >= System.currentTimeMillis()) {
                milestone.setStatus(MilestoneStatus.UPCOMING);
            } else {
                milestone.setStatus(MilestoneStatus.OVERDUE);
            }
        }

    }

    /**
     * <p>
     * Getter method for logger, simply return the namesake instance variable.
     * </p>
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Setter method for logger, simply assign the value to the instance variable.
     * </p>
     *
     * @param logger
     *            the logger to set
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Getter method for sessionFactory, simply return the namesake instance variable.
     * </p>
     *
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * <p>
     * Setter method for sessionFactory, simply assign the value to the instance variable.
     * </p>
     *
     * @param sessionFactory
     *            the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * <p>
     * Retrieves the current session from the session factory.
     * </p>
     *
     * @return the retrieved current session (not null)
     * @throws HibernateException
     *             if any error occurred
     */
    private Session getSession() {
        // return current session from session factory
        return sessionFactory.getCurrentSession();
    }

    /**
     * <p>
     * This class for compare milestone.
     * </p>
     * <p>
     * <b>Thread Safety:</b> It's thread safe.
     * </p>
     *
     * @author argolite, TCSDEVELOPER
     * @version 1.0
     */
    private class MilestoneComparable implements Comparator<Milestone> {
        /**
         * <p>
         * The integer to decide order.
         * </p>
         */
        private final int g;

        /**
         * Constructor.
         *
         * @param ascending
         *            the flag of ascending
         */
        MilestoneComparable(boolean ascending) {
            g = ascending ? -1 : 1;
        }

        /**
         * Compare two milestones.
         *
         * @param o1
         *            the milestones
         * @param o2
         *            the milestones
         * @return the integer decide order
         */
        public int compare(Milestone o1, Milestone o2) {
            Long dateTime1 = o1.getDueDate().getTime();
            Long dateTime2 = o2.getDueDate().getTime();
            return dateTime1.compareTo(dateTime2) * g;
        }

    }

}
