/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.util.log.Log;
import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.DirectProjectCPConfigService;

/**
 * <p>
 * This class is an implementation of DirectProjectCPConfigService. It uses Hibernate session to access entities in
 * persistence. This class uses Logging Wrapper logger to log errors and debug information.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *        xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *        xmlns:util=&quot;http://www.springframework.org/schema/util&quot;
 *        xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot;
 *        xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
 *        xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
 *        http://www.springframework.org/schema/util
 *        http://www.springframework.org/schema/util/spring-util-3.1.xsd
 *        http://www.springframework.org/schema/aop
 *        http://www.springframework.org/schema/aop/spring-aop-3.1.xsd
 *        http://www.springframework.org/schema/tx
 *        http://www.springframework.org/schema/tx/spring-tx-3.1.xsd&quot;&gt;
 *
 *   &lt;bean id=&quot;dataSource&quot;
 *         class=&quot;org.apache.commons.dbcp.BasicDataSource&quot;&gt;
 *     &lt;property name=&quot;driverClassName&quot; value=&quot;com.informix.jdbc.IfxDriver&quot; /&gt;
 *     &lt;property name=&quot;url&quot;
 *         value=&quot;jdbc:informix-sqli://localhost:1526/test:informixserver=ol_topcoder&quot; /&gt;
 *     &lt;property name=&quot;username&quot; value=&quot;informix&quot; /&gt;
 *     &lt;property name=&quot;password&quot; value=&quot;123456&quot; /&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;bean id=&quot;sessionFactory&quot;
 *         class=&quot;org.springframework.orm.hibernate3.LocalSessionFactoryBean&quot;&gt;
 *     &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;/&gt;
 *     &lt;property name=&quot;mappingResources&quot;&gt;
 *       &lt;list&gt;
 *         &lt;value&gt;mapping/DirectProjectCPConfig.hbm.xml&lt;/value&gt;
 *         &lt;value&gt;mapping/MemberContributionPoints.hbm.xml&lt;/value&gt;
 *         &lt;value&gt;mapping/ProjectContestCPConfig.hbm.xml&lt;/value&gt;
 *       &lt;/list&gt;
 *     &lt;/property&gt;
 *     &lt;property name=&quot;hibernateProperties&quot;&gt;
 *       &lt;props&gt;
 *         &lt;prop key=&quot;hibernate.dialect&quot;&gt;org.hibernate.dialect.InformixDialect&lt;/prop&gt;
 *         &lt;prop key=&quot;show_sql&quot;&gt;true&lt;/prop&gt;
 *       &lt;/props&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;bean id=&quot;directProjectCPConfigService&quot;
 *         class=&quot;com.topcoder.utilities.cp.services.impl.DirectProjectCPConfigServiceImpl&quot;&gt;
 *     &lt;property name=&quot;sessionFactory&quot; ref=&quot;sessionFactory&quot;/&gt;
 *     &lt;property name=&quot;loggerName&quot; value=&quot;com.topcoder.utilities.cp.services.imp.logger&quot;/&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;!-- transaction management configuration --&gt;
 *
 *   &lt;!-- enable the configuration of transactional behavior based on annotations --&gt;
 *   &lt;tx:annotation-driven transaction-manager=&quot;transactionManager&quot;/&gt;
 *
 *   &lt;bean id=&quot;transactionManager&quot;
 *         class=&quot;org.springframework.jdbc.datasource.DataSourceTransactionManager&quot;&gt;
 *     &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;/&gt;
 *   &lt;/bean&gt;
 *
 * &lt;/beans&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Load Spring XML file
 * ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(&quot;beans.xml&quot;);
 *
 * // Get DirectProjectCPConfigService from Spring context
 * DirectProjectCPConfigService directProjectCPConfigService = (DirectProjectCPConfigService) applicationContext
 *     .getBean(&quot;directProjectCPConfigService&quot;);
 *
 * DirectProjectCPConfig directProjectCPConfig = new DirectProjectCPConfig();
 * directProjectCPConfig.setDirectProjectId(1);
 * directProjectCPConfig.setUseCP(true);
 * directProjectCPConfig.setAvailableImmediateBudget(1);
 *
 * // Create the DirectProjectCPConfig entity
 * long directProjectCPConfigId = directProjectCPConfigService.create(directProjectCPConfig);
 *
 * // Get the DirectProjectCPConfig entity
 * directProjectCPConfig = directProjectCPConfigService.get(directProjectCPConfigId);
 *
 * // Update the DirectProjectCPConfig entity
 * directProjectCPConfig.setAvailableImmediateBudget(10);
 * directProjectCPConfigService.update(directProjectCPConfig);
 *
 * // Get the available initial payments
 * double availableInitialPayments = directProjectCPConfigService.getAvailableInitialPayments(directProjectCPConfigId);
 *
 * // Delete the DirectProjectCPConfig entity
 * directProjectCPConfigService.delete(directProjectCPConfigId);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is thread safe when the class is initialized by Spring right after
 * construction and its parameters are never changed after that, all entities passed to this class are used by the
 * caller in thread safe manner (accessed from a single thread only). This class assumes that transactions are managed
 * by Spring.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public class DirectProjectCPConfigServiceImpl extends BaseService implements DirectProjectCPConfigService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DirectProjectCPConfigServiceImpl.class.getName();

    /**
     * Creates an instance of DirectProjectCPConfigServiceImpl.
     */
    public DirectProjectCPConfigServiceImpl() {
        // Empty
    }
    /**
     * Creates the DirectProjectCPConfig entity.
     *
     * @param config
     *            the DirectProjectCPConfig entity.
     *
     * @return the ID of the created DirectProjectCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    @Transactional
    public long create(DirectProjectCPConfig config) throws ContributionServiceException {
        String signature = CLASS_NAME + ".create(DirectProjectCPConfig config)";

        return createEntity(signature, config, "config", config == null ? 0L : config.getDirectProjectId(),
            "config#directProjectId");
    }

    /**
     * Updates the DirectProjectCPConfig entity.
     *
     * @param config
     *            the DirectProjectCPConfig entity.
     *
     * @throws IllegalArgumentException
     *             if config is null or config#directProjectId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional
    public void update(DirectProjectCPConfig config) throws ContributionServiceException {
        String signature = CLASS_NAME + ".update(DirectProjectCPConfig config)";

        updateEntity(signature, config, "config", config == null ? 0L : config.getDirectProjectId(),
            "config#directProjectId");
    }

    /**
     * Deletes the DirectProjectCPConfig entity.
     *
     * @param directProjectId
     *            the ID of the direct project.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional
    public void delete(long directProjectId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".delete(long directProjectId)";

        deleteEntity(signature, DirectProjectCPConfig.class, directProjectId, "directProjectId");
    }

    /**
     * Gets the DirectProjectCPConfig entity by id.
     *
     * @param directProjectId
     *            the ID of the direct project.
     *
     * @return the DirectProjectCPConfig entity or <code>null</code> if the entity doesn't exist.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional(readOnly = true)
    public DirectProjectCPConfig get(long directProjectId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".get(long directProjectId)";

        return getEntity(signature, DirectProjectCPConfig.class, directProjectId, "directProjectId");
    }

    /**
     * Gets the available initial payments by id.
     *
     * @param directProjectId
     *            the ID of the direct project or <code>0</code> if the entity doesn't exist.
     *
     * @return the available initial payments.
     *
     * @throws IllegalArgumentException
     *             if directProjectId is not positive.
     * @throws ContributionServiceException
     *             if any other error occurs.
     */
    @Transactional(readOnly = true)
    public double getAvailableInitialPayments(long directProjectId) throws ContributionServiceException {
        String signature = CLASS_NAME + ".getAvailableInitialPayments(long directProjectId)";
        Log log = getLog();

        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"directProjectId"},
            new Object[] {directProjectId});

        try {
            ParameterCheckUtility.checkPositive(directProjectId, "directProjectId");

            Double result = (Double) getSessionFactory().getCurrentSession().getNamedQuery(
                "getAvailableInitialPayments").setLong("directProjectId", directProjectId).uniqueResult();
            if (result == null) {
                // The entity doesn't exist
                result = 0D;
            }

            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (HibernateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(
                "Failed to get the available initial payments.", e));
        }
    }
}
