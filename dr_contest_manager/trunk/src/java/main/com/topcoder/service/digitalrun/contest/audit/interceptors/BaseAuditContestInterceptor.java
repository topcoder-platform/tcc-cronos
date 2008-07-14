/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.audit.interceptors;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.object.auditor.Auditor;
import com.topcoder.object.auditor.AuditorHelper;
import com.topcoder.service.digitalrun.contest.ContestManagerConfigurationException;
import com.topcoder.service.digitalrun.contest.Helper;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This is a base class for all the audit interceptors.
 * </p>
 *
 * <p>
 * It presents a simplified base which provides automatically the auditor which will audit the actions
 * as well as the auditing user.
 * </p>
 *
 * <p>
 * Additionally it provides access to JPA based <code>EntityManager</code>.
 * </p>
 *
 * <p>
 * It is initialized with the <code>Auditor</code> instance which would be used by all implementations to
 * audit the actual actions.
 * </p>
 *
 * <p>
 * It is also initialized with the auditing user as well as the unit name used to fetch the
 * entity manager used to communicate with JPA.
 * </p>
 *
 * <p>
 *     <strong>Sample ejb-jar.xml:</strong>
 *     <pre>
 *  &lt;assembly-descriptor&gt;
 *    &lt;interceptor-binding&gt;
 *      &lt;ejb-name&gt;DigitalRunContestManagerBean&lt;/ejb-name&gt;
 *      &lt;interceptor-class&gt;
 *      com.topcoder.service.digitalrun.contest.audit.interceptors.AuditCreateContestInterceptor
 *      &lt;/interceptor-class&gt;
 *      &lt;method&gt;
 *        &lt;method-name&gt;createTrackContest&lt;/method-name&gt;
 *        &lt;method-params&gt;
 *          &lt;method-param&gt;com.topcoder.service.digitalrun.entity.TrackContest&lt;/method-param&gt;
 *        &lt;/method-params&gt;
 *      &lt;/method&gt;
 *    &lt;/interceptor-binding&gt;
 *    &lt;interceptor-binding&gt;
 *      &lt;ejb-name&gt;DigitalRunContestManagerBean&lt;/ejb-name&gt;
 *      &lt;interceptor-class&gt;
 *      com.topcoder.service.digitalrun.contest.audit.interceptors.AuditUpdateContestInterceptor
 *      &lt;/interceptor-class&gt;
 *      &lt;method&gt;
 *        &lt;method-name&gt;updateTrackContest&lt;/method-name&gt;
 *        &lt;method-params&gt;
 *          &lt;method-param&gt;com.topcoder.service.digitalrun.entity.TrackContest&lt;/method-param&gt;
 *        &lt;/method-params&gt;
 *      &lt;/method&gt;
 *    &lt;/interceptor-binding&gt;
 *    &lt;interceptor-binding&gt;
 *      &lt;ejb-name&gt;DigitalRunContestManagerBean&lt;/ejb-name&gt;
 *      &lt;interceptor-class&gt;
 *      com.topcoder.service.digitalrun.contest.audit.interceptors.AuditRemoveContestInterceptor
 *      &lt;/interceptor-class&gt;
 *      &lt;method&gt;
 *        &lt;method-name&gt;removeTrackContest&lt;/method-name&gt;
 *        &lt;method-params&gt;
 *          &lt;method-param&gt;long&lt;/method-param&gt;
 *        &lt;/method-params&gt;
 *      &lt;/method&gt;
 *    &lt;/interceptor-binding&gt;
 *  &lt;/assembly-descriptor&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Sample configuration:</strong>
 *     <pre>
 *        &lt;Property name="auditor_user"&gt;
 *            &lt;Value&gt;TCSDEVELOPER&lt;/Value&gt;
 *        &lt;/Property&gt;
 *
 *        &lt;Property name="auditor_config"&gt;
 *            &lt;Property name="audit_dao_key"&gt;
 *                &lt;Value&gt;AuditDAO&lt;/Value&gt;
 *            &lt;/Property&gt;
 *
 *            &lt;Property name="dao_object_factory_config"&gt;
 *                &lt;Property name="AuditDAO"&gt;
 *                    &lt;Property name="type"&gt;
 *                        &lt;Value&gt;com.topcoder.service.digitalrun.contest.MockAuditDAO&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                &lt;/Property&gt;
 *            &lt;/Property&gt;
 *        &lt;/Property&gt;
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     It is thread safe to use since it is immutable after initialization.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseAuditContestInterceptor {

    /**
     * <p>
     * The "auditor_config" configuration child.
     * </p>
     */
    private static final String AUDITOR_CONFIG = "auditor_config";

    /**
     * <p>
     * The "auditor_user" configuration property.
     * </p>
     */
    private static final String AUDITOR_USER = "auditor_user";

    /**
     * <p>
     * This is the <code>Auditor</code> instance that will be used to audit the actions of the
     * <code>DigitalRunContestManagerBean</code> class.
     * </p>
     *
     * <p>
     * This is initialized through the <code>initialize()</code> method, and never changed afterwards.
     * </p>
     *
     * <p>
     * Cannot be null.
     * </p>
     *
     * <p>
     * Used to audit Create/Update/Delete actions in <code>DigitalRunContestManagerBean</code> class.
     * </p>
     */
    private Auditor auditor;

    /**
     * <p>
     * This is the user who is auditing and will be used with the <code>Auditor</code>.
     * </p>
     *
     * <p>
     * This is initialized through the <code>initialize()</code> method, and never changed afterwards.
     * </p>
     *
     * <p>
     * It must be non-null, non-empty string.
     * </p>
     *
     * <p>
     * Used to specify the user who audited Create/Update/Delete actions in <code>DigitalRunContestManagerBean
     * </code> class.
     */
    private String auditorUser;

    /**
     * <p>
     * Represents the persistence unit name to lookup the <code>EntityManager</code> from
     * <code>SessionContext</code>.
     * </p>
     *
     * <p>
     * This is initialized through the <code>initialize()</code> method, and never changed afterwards.
     * </p>
     *
     * <p>
     * It must be non-null, non-empty string.
     * </p>
     *
     * <p>
     * This is used in the <code>getEntityManager()</code> method.
     * </p>
     */
    private String unitName;

    /**
     * <p>
     * Represents the <code>SessionContext</code> injected by the EJB container automatically.
     * </p>
     *
     * <p>
     * It is marked with "@Resource" annotation.
     * </p>
     *
     * <p>
     * It's non-null after injected when this bean is instantiated. And its reference is not changed afterwards.
     * </p>
     *
     * <p>
     * It can be used for example in the <code>initialize()</code> method to lookup JNDI resources.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    protected BaseAuditContestInterceptor() {
        // empty
    }

    /**
     * <p>
     * Configures this interceptor with values from the JNDI ENC.
     * </p>
     *
     * <p>
     * This method is marked with "@PostConstruct" annotation and is called after this bean is constructed by
     * the EJB container.
     * </p>
     *
     * @param invocationContext The invocation context.
     *
     * @throws ContestManagerConfigurationException If "unitName" or "config_namespace" or "config_file_name"
     *         parameter in the JNDI ENC is missing or invalid, or if there are errors while obtaining the
     *         <code>Auditor</code>, or if the "auditor_user" is missing or invalid.
     * @throws Exception If the proceeding fails.
     */
    @PostConstruct
    public void initialize(InvocationContext invocationContext) throws Exception {
        unitName = Helper.lookupENC(sessionContext, Helper.UNIT_NAME);

        String configFile = Helper.lookupENC(sessionContext, Helper.CONFIG_FILE_NAME);
        String namespace = Helper.lookupENC(sessionContext, Helper.CONFIG_NAMESPACE);

        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager(configFile);

            // The ConfigurationFileManager#getConfiguration never returns null
            // It throws UnrecognizedNamespaceException if given namespace does not exist

            ConfigurationObject co = Helper.getChild(cfm.getConfiguration(namespace), namespace);

            // Get value of auditorUser
            auditorUser = Helper.getConfigString(co, AUDITOR_USER, true);

            ConfigurationObject ao = Helper.getChild(co, AUDITOR_CONFIG);

            // Create Auditor
            auditor = AuditorHelper.createAuditor(ao);

        } catch (BaseException e) {
            throw new ContestManagerConfigurationException("Error occurs while initializing " + this.getClass(), e);
        } catch (IOException e) {
            throw new ContestManagerConfigurationException("I/O Error occurs while initializing " + this.getClass(), e);
        }

        // This is very important, MUST proceed the PostConstruct initialization.
        // Otherwise the EJBean will not be initialized.
        invocationContext.proceed();
    }

    /**
     * <p>
     * This is a utility method to obtain the <code>EntityManager</code> from the JNDI ENC via the session context.
     * </p>
     *
     * @return The <code>EntityManager</code> obtained.
     *
     * @throws Exception If we can't obtain the manager instance.
     */
    protected EntityManager getEntityManager() throws Exception {
        return Helper.getEntityManager(sessionContext, unitName);
    }

    /**
     * <p>
     * Get the <code>Auditor</code>.
     * </p>
     *
     * @return <code>Auditor</code>.
     */
    protected Auditor getAuditor() {
        return auditor;
    }

    /**
     * <p>
     * Get the auditor user.
     * </p>
     *
     * @return auditor user.
     */
    protected String getAuditorUser() {
        return auditorUser;
    }
}
