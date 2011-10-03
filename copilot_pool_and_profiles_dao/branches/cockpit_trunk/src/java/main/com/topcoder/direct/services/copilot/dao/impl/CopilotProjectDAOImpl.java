/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfo;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import org.hibernate.HibernateException;

import java.text.MessageFormat;
import java.util.List;

/**
 * <p>This class is an implementation of CopilotProjectDAO that uses Hibernate session to access entities in
 * persistence. It extends GenericDAOImpl&lt;CopilotProject&gt; that provides common functionality for
 * CopilotProfileDAO, CopilotProjectDAO and CopilotProjectPlanDAO implementations provided in this component. This class
 * uses Logging Wrapper logger to log errors and debug information.</p>
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file, sample bean definition:
 *
 * <pre>
 * &lt;bean id="copilotProjectDAO"
 *        class="com.topcoder.direct.services.copilot.dao.impl.CopilotProjectDAOImpl"&gt;
 *      &lt;property name="loggerName" value="myLogger"/&gt;
 *      &lt;property name="sessionFactory" ref="tcsCatalogSessionFactory"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p><strong>Sample usage:</strong>
 * <pre>
 * // Retrieves CopilotProjectDAO from the Spring application context
 * ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
 * CopilotProjectDAO copilotProjectDAO =
 * (CopilotProjectDAO) context.getBean("copilotProjectDAO");
 *
 * // Creates CopilotProjectProject instance and fill it with data
 * CopilotProject copilotProject = new CopilotProject();
 *
 * // Saves the CopilotProject in persistence
 * int id = copilotProjectDAO.create(copilotProject);
 *
 * // Updates the CopilotProject
 * copilotProjectDAO.update(copilotProject);
 *
 * // Deletes the CopilotProject by id
 * copilotProjectDAO.delete(1);
 *
 * // Retrieves CopilotProject by id
 * CopilotProject copilotProject = copilotProjectDAO.retrieve(1);
 *
 * // Retrieves all CopilotProjects
 * List&lt;CopilotProject&gt; copilotProjects = copilotProjectDAO.retrieveAll();
 * </pre>
 * </p>
 *
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectDAOImpl extends GenericDAOImpl<CopilotProject> implements CopilotProjectDAO {

    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "CopilotProjectDAOImpl";

    /**
     * <p>Represents a hql query used for retrieving {@link CopilotProject} by copilotProfileId.</p>
     */
    private static final String COPILOT_PROJECT_QUERY =
            "from CopilotProject p where p.copilotProfileId = :copilotProfileId";

    /**
     * <p>Creates new instance of <code>{@link CopilotProjectDAOImpl}</code> class.</p>
     */
    public CopilotProjectDAOImpl() {
        // empty constructor
    }

    /**
     * <p>Retrieves the copilot projects for the copilot with the specified profile ID. Returns an empty list if the
     * copilot has no associated projects.</p>
     *
     * @param copilotProfileId the ID of the copilot profile
     *
     * @return the copilot projects for the specified copilot (not null, doesn't contain null)
     *
     * @throws IllegalArgumentException if copilotProfileId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    @SuppressWarnings("unchecked")
    public List<CopilotProject> getCopilotProjects(long copilotProfileId) throws CopilotDAOException {
        final String methodName = "getCopilotProjects";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"copilotProfileId"},
                new Object[]{copilotProfileId});

        Helper.checkIsPositive(getLog(), copilotProfileId, "copilotProfileId", CLASS_NAME, methodName);

        try {
            // execute query and retrieves result as list
            List<CopilotProject> result = getSession()
                    .createQuery(COPILOT_PROJECT_QUERY)
                    .setParameter("copilotProfileId", copilotProfileId)
                    .list();

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // returns result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(
                    MessageFormat.format("Exception occurred when retrieving CopilotProject with copilotProfileId {0}.",
                            copilotProfileId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Updates the creation/modification timestamp property of the given entity with the current date/time.</p>
     *
     * @param entity the entity to be updated
     * @param create true if entity will be created in persistence, false - if entity will be updated
     *
     * @throws IllegalArgumentException if entity is null
     */
    protected void updateAuditTimestamp(IdentifiableEntity entity, boolean create) {
        // delegate to base class method
        // propagates IllegalArgumentException
        super.updateAuditTimestamp(entity, create);

        if (entity instanceof CopilotProject) {
            CopilotProject copilotProject = (CopilotProject) entity;

            // iterate over all ProjectInfo and update them
            if (copilotProject.getProjectInfos() != null) {
                for (CopilotProjectInfo copilotProjectInfo : copilotProject.getProjectInfos()) {
                    updateAuditTimestamp(copilotProjectInfo, copilotProjectInfo.getId() == 0);
                }
            }
        }
    }
}

