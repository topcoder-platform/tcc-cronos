/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan.ejb;

import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.GamePlanPersistenceException;
import com.topcoder.service.gameplan.GamePlanServiceConfigurationException;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>This class is an EJB that implements <code>GamePlanService</code> business interface. This bean uses <b>Logging
 * Wrapper</b> component to log exceptions and debug information. Also it uses injected JPA entity managers to access
 * software and studio projects data in the persistence.</p>
 *
 * <p>
 * Following is the sample usage for this component.
 *
 * <pre>
 * // Get game plan service
 * Context context = new InitialContext();
 * GamePlanService gamePlanService =
 *     (GamePlanServiceRemote) context.lookup("GamePlanServiceBean/remote");
 * // Create TCSubject instance for admin user
 * Set&lt;RolePrincipal&gt; principals = new HashSet&lt;RolePrincipal&gt;();
 * RolePrincipal adminRole = new RolePrincipal("Cockpit Administrator", 1);
 * principals.add(adminRole);
 * TCSubject tcSubject1 = new TCSubject(principals, 12345);
 * // Retrieve game plan data for all existing TC Direct projects
 * List&lt;TCDirectProjectGamePlanData&gt; gamePlanDataList =
 *     gamePlanService.retrieveGamePlanData(tcSubject1);
 * for (TCDirectProjectGamePlanData gamePlanData : gamePlanDataList) {
 * // Get data for software projects
 * List&lt;SoftwareProjectData&gt; softwareProjects = gamePlanData.getSoftwareProjects();
 * // Get data for studio projects
 * List&lt;StudioProjectData&gt; studioProjects = gamePlanData.getStudioProjects();
 * // Process projects data
 * // ...
 * }
 * // Create TCSubject instance for non-admin user
 * principals = new HashSet&lt;RolePrincipal&gt;();
 * TCSubject tcSubject2 = new TCSubject(principals, 23456);
 * // Retrieve game plan data for all TC Direct projects associated with user with ID=23456
 * gamePlanDataList = gamePlanService.retrieveGamePlanData(tcSubject2);
 *
 * // Retrieve the game plan data for the specific TC Direct Project associated with the current user.
 * TCDirectProjectGamePlanData gamePlanData = gamePlanService.retrieveGamePlanData(tcSubject2, 1L);
 * </pre>
 * </p>
 *
 * <p><b>Thread Safety</b>: This class is mutable and not thread safe. But it is always used in thread safe manner in
 * EJB container because its state doesn't change after initialization. Instances of EntityManager used by this class
 * are thread safe. This bean assumes that transactions are managed by the container.</p>
 *
 * <p>
 * Version 1.0.1 (TC Cockpit Contest Duration Calculation Updates Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #RETRIEVE_SOFTWARE_PROJECT_DATA} statement to exclude duration of <code>Approval</code>,
 *     <code>Specification Submission/Review</code> phases from contest duration.</li>
 *   </ol>
 * </p>
 *
 * @author saarixx, FireIce, isv
 * @version 1.0.1
 */
@Stateless
public class GamePlanServiceBean implements GamePlanServiceLocal, GamePlanServiceRemote {
    /**
     * Represents the sql to retrieve software project data. Any project with 'Deleted' status or belong to
     * 'Spec Review' category will be excluded.
     */
    private static final String RETRIEVE_SOFTWARE_PROJECT_DATA = "SELECT p.tc_direct_project_id, p.project_id,"
            + "(select pi.value from project_info pi where pi.project_id = p.project_id AND pi.project_info_type_id = 6) as project_name,"
            + "(SELECT MIN(NVL(actual_start_time, scheduled_start_time)) FROM project_phase ph"
            + "    WHERE ph.project_id = p.project_id and ph.phase_type_id = 1) as start_time,"
            + "(SELECT MAX(NVL(actual_end_time, scheduled_end_time)) FROM project_phase ph"
            + "    WHERE ph.project_id = p.project_id AND NOT ph.phase_type_id IN (11, 13, 14)) as end_time,"
            + "tcd.user_id, psl.name as project_status,"
            + "(SELECT ptl.name FROM phase_type_lu ptl WHERE phase_type_id = "
            + "    (SELECT MIN(phase_type_id) FROM project_phase ph "
            + "        WHERE ph.phase_status_id = 2 AND ph.project_id = p.project_id)) as current_phase,"
            + "pcl.name as project_type,"
            + "(SELECT COUNT(lpx.dest_project_id) FROM linked_project_xref lpx"
            + "    WHERE lpx.source_project_id = p.project_id AND lpx.link_type_id = 5) "
            + "FROM user_permission_grant upg, project p"
            + "  INNER JOIN tc_direct_project tcd ON tcd.project_id = p.tc_direct_project_id"
            + "  LEFT OUTER JOIN project_status_lu psl ON psl.project_status_id = p.project_status_id"
            + "  LEFT OUTER JOIN project_category_lu pcl ON pcl.project_category_id = p.project_category_id "
            + "WHERE p.project_status_id != 3 AND p.project_category_id != 27  "
            + " and tcd.project_id = upg.resource_id  and upg.permission_type_id in (1,2,3 ) ";

    /**
     * Represents the sql for retrieving studio project data. Any contest with 'Deleted' status will be excluded.
     */
    private static final String RETRIEVE_STUDIO_PROJECT_DATA_SQL = "SELECT p.project_id as tc_direct_project_id, "
            + "c.project_id, c.contest_id, p.name as project_name, c.name as contest_name, c.start_time, c.end_time, "
            + "p.user_id, cds.name as contest_status, "
            + "(select contest_type_desc from contest_type_lu where contest_type_id = c.contest_type_id) "
            + "FROM user_permission_grant upg, tc_direct_project p"
            + "  LEFT OUTER JOIN contest c ON c.tc_direct_project_id = p.project_id"
            + "  LEFT OUTER JOIN contest_detailed_status_lu cds"
            + "    on c.contest_detailed_status_id = cds.contest_detailed_status_id "
            + "WHERE (c.deleted IS NULL OR c.deleted = 0) AND"
            + "  (c.contest_detailed_status_id IS NULL OR c.contest_detailed_status_id != 3) AND (c.contest_id IS NOT NULL) "
            + " and  p.project_id = upg.resource_id and upg.permission_type_id in (1,2,3) ";

    /**
     * Represents the sql for retrieving IDs of dependency projects.
     */
    private static final String RETRIEVE_DEPENDENCY_PROJECT_IDS_SQL = 
            "SELECT  lp.dest_project_id FROM linked_project_xref lp, project p " +
			" WHERE lp.source_project_id = :projectId " + 
			" AND lp.source_project_id = p.project_id AND lp.link_type_id in (1, 4) " + 
			" AND p.tc_direct_project_id = (SELECT tc_direct_project_id FROM project WHERE project_id = lp.dest_project_id) " +
			" AND p.tc_direct_project_id IS NOT NULL";
                    
    /**
     * Represents the sql for retrieving tc direct project.
     */
    private static final String RETRIEVE_DIRECT_PROJECT_SQL =
            "SELECT project_id, name FROM user_permission_grant upg, tc_direct_project WHERE upg.resource_id = project_id and  upg.permission_type_id in (1,2,3 ) ";

    /**
     * Represents the name for active status.
     */
    private static final String ACTIVE_STATUS = "Active";

    /**
     * Represents the cockpit administrator role name.
     */
    private static final String COCKPIT_ADMINISTRATOR_ROLE = "Cockpit Administrator";

    /**
     * Represents the <b>EntityManager</b> instance to be used by this class for accessing software contests specific
     * data.
     *
     * Cannot be null and is not modified after initialization. Initialized by EJB container injection. Is used in
     * retrieveGamePlanData().
     */
    @PersistenceContext(name = "softwarePersistence")
    private EntityManager softwareEntityManager;

    /**
     * Represents the <b>EntityManager</b> instance to be used by this class for accessing studio contests specific
     * data.
     *
     * Cannot be null and is not modified after initialization. Initialized by EJB container injection. Is used in
     * retrieveGamePlanData().
     */
    @PersistenceContext(name = "studioPersistence")
    private EntityManager studioEntityManager;

    /**
     * The name of the logger to be used by this class.
     *
     * Can be set with EJB container injection. Is null when logging is not required. Must not be empty after injection.
     * Is used in initialize().
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * The logger instance to be used by this class.
     *
     * Can be set in initialize(). Is null if logging is not required. Is used in retrieveGamePlanData().
     */
    private Log log;

    /**
     * Creates a <code>GamePlanServiceBean</code> instance.
     */
    public GamePlanServiceBean() {
    }

    /**
     * Initializes this class. This method is called after the bean is constructed by the EJB container.
     *
     * @throws GamePlanServiceConfigurationException
     *          if any error occurred when initializing this class
     */
    @PostConstruct
    protected void initialize() {
        if (null != logName) {
            if (0 == logName.trim().length()) {
                throw new GamePlanServiceConfigurationException("The 'logName' configuration should not be empty");
            }

            log = LogManager.getLog(logName);
        }
    }

    /**
     * Retrieves the game plan data of TC Direct projects for the current user. If the current user is an admin, data
     * for all existing TC Direct projects are retrieved.
     *
     * @param tcSubject the TC subject
     * @return the retrieved TC Direct projects game plan data (not null, doesn't contain null; empty if there is no TC
     *         Direct projects for the current user)
     * @throws IllegalArgumentException     if tcSubject is null
     * @throws IllegalStateException        if softwareEntityManager or studioEntityManager is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     */
    public List<TCDirectProjectGamePlanData> retrieveGamePlanData(TCSubject tcSubject)
            throws GamePlanPersistenceException {
        logInfo(MessageFormat.format("Enter into GamePlanServiceBean#retrieveGamePlanData(TCSubject) with [{0}]",
                null == tcSubject ? null : "TCSubject with userId - " + tcSubject.getUserId()));
        final long start = System.currentTimeMillis();

        Long userId = getUserId(tcSubject);

        try {
            List<TCDirectProjectGamePlanData> result = retrieveGamePlanDataByUser(userId, null);

            final long end = System.currentTimeMillis();
            logInfo(MessageFormat.format(
                    "Exit GamePlanServiceBean#retrieveGamePlanData(TCSubject) with {0}, the method call takes {1}ms",
                    format(result), end - start));

            return result;
        } catch (GamePlanPersistenceException e) {
            throw logException(e);
        }
    }

    /**
     * Retrieves the game plan data of specified TC Direct project for the current user. If the current user is an
     * admin, any TC Direct project can be retrieved.
     *
     * @param tcSubject       the TC subject
     * @param directProjectId the TC Direct Project id
     * @return the retrieved TC Direct project game plan data (possibly null if there is no TC Direct projects for the
     *         current user)
     * @throws IllegalArgumentException     if tcSubject is null
     * @throws IllegalStateException        if softwareEntityManager or studioEntityManager is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     */
    public TCDirectProjectGamePlanData retrieveGamePlanData(TCSubject tcSubject, long directProjectId)
            throws GamePlanPersistenceException {
        logInfo(MessageFormat.format(
                "Enter into GamePlanServiceBean#retrieveGamePlanData(TCSubject, long) with [{0}] and [{1}]",
                null == tcSubject ? null : "TCSubject with userId - " + tcSubject.getUserId(), directProjectId));
        final long start = System.currentTimeMillis();

        Long userId = getUserId(tcSubject);

        try {
            List<TCDirectProjectGamePlanData> result = retrieveGamePlanDataByUser(userId, directProjectId);

            TCDirectProjectGamePlanData tcDirectProjectGamePlanData = null;
            if (result.size() != 0) {
                tcDirectProjectGamePlanData = result.get(0);
            }
            final long end = System.currentTimeMillis();
            logInfo(MessageFormat.format(
                    "Exit GamePlanServiceBean#retrieveGamePlanData(TCSubject, long) with {0},"
                            + " the method call takes {1}ms",
                    tcDirectProjectGamePlanData == null ? null :
                            "TCDirectProjectGamePlanData[id = " + tcDirectProjectGamePlanData.getTcDirectProjectId()
                                    + "]", end - start));

            return tcDirectProjectGamePlanData;
        } catch (GamePlanPersistenceException e) {
            throw logException(e);
        }
    }

    /**
     * Gets the user id from the TCSubject instance. If the current user is an admin, null will return.
     * @param tcSubject the TCSubject instance.
     * @return the current user id, or null If the current user is an admin.
     * @throws IllegalArgumentException if the tcSubject is null.
     */
    private Long getUserId(TCSubject tcSubject) {
        if (null == tcSubject) {
            throw logException(new IllegalArgumentException("The tcSubject should not be null."));
        }

        // checks whether the current user is an admin
        boolean isAdmin = false;
        Set<TCPrincipal> roles = tcSubject.getPrincipals();

        if (roles != null) {
            for (TCPrincipal role : roles) {
                String roleName = role.getName();

                if (COCKPIT_ADMINISTRATOR_ROLE.equalsIgnoreCase(roleName)) {
                    isAdmin = true;
                    break;
                }
            }
        }

        // if current user is not admin, retrieve the user id to retrieve game plan data.
        Long userId = null;
        if (!isAdmin) {
            userId = tcSubject.getUserId();
        }
        return userId;
    }

    /**
     * Retrieves the game plan data of TC Direct projects for the specified user. If userId is null, data for all
     * existing TC Direct projects are retrieved.
     *
     * @param userId the ID of the user associated with TC Direct projects (null if data for all existing TC Direct
     *               projects must be retrieved)
     * @param directProjectId the specific TC Direct Project id.
     * @return the retrieved TC Direct projects game plan data (not null, doesn't contain null; empty if there is no TC
     *         Direct projects for the specified user)
     * @throws IllegalStateException        if softwareEntityManager or studioEntityManager is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     */
    private List<TCDirectProjectGamePlanData> retrieveGamePlanDataByUser(Long userId, Long directProjectId)
            throws GamePlanPersistenceException {
        if (null == softwareEntityManager) {
            throw logException(new IllegalStateException("The softwareEntityManager is not properly initialized."));
        } else if (null == studioEntityManager) {
            throw logException(new IllegalStateException("The studioEntityManager is not properly initialized."));
        }

        Date now = new Date();
        // Create mapping from TC Direct Project ID to TCDirectProjectGamePlanData instance
        Map<Long, TCDirectProjectGamePlanData> tcDirectProjectsMap = new HashMap<Long, TCDirectProjectGamePlanData>();
        
        String directProjectQuery = RETRIEVE_DIRECT_PROJECT_SQL; 
        
        if (userId != null) {
            directProjectQuery += " AND upg.user_id = :userId ";
        }

        if (directProjectId != null) {
            directProjectQuery += " AND project_id = :directProjectId";
        }
        
        // Create query for retrieving direct projects data
        Query query = studioEntityManager.createNativeQuery(directProjectQuery);
        
        // set the parameter value if present
        if (userId != null) {
            query.setParameter("userId", userId);
        }

        if (directProjectId != null) {
            query.setParameter("directProjectId", directProjectId);
        }

        List<Object[]> resultList = query.getResultList();
        
        for (Object[] row : resultList) {
        	TCDirectProjectGamePlanData data = new TCDirectProjectGamePlanData();
        	long tcDirectProjectId = (Integer) row[0];
        	String tcDirectProjectName = (String) row[1];
        	
            data.setTcDirectProjectId(tcDirectProjectId);
            data.setTcDirectProjectName(tcDirectProjectName);
            
            tcDirectProjectsMap.put(tcDirectProjectId, data); 
        }
        
        try {
            retrieveSoftwareProjectData(userId, directProjectId, now, tcDirectProjectsMap);

            retrieveStudioProjectData(userId, directProjectId, now, tcDirectProjectsMap);

            return new ArrayList<TCDirectProjectGamePlanData>(tcDirectProjectsMap.values());
        } catch (ClassCastException e) {
            throw new GamePlanPersistenceException("Fail to convert the retrieved data.", e);
        } catch (PersistenceException e) {
            throw new GamePlanPersistenceException("Error occurred when accessing the persistence", e);
        }
    }

    /**
     * Retrieves the studio project data.
     *
     * @param userId              the user id that the project associated with.
     * @param now                 the current retrieval time.
     * @param tcDirectProjectsMap the map to hold the <b>TCDirectProjectGamePlanData</b> instance.
     * @throws ClassCastException If fail to convert the retrieved data.
     * @throws PersistenceException If any problem to access the persistence.
     */
    private void retrieveStudioProjectData(Long userId, Long directProjectId, Date now,
                                           Map<Long, TCDirectProjectGamePlanData> tcDirectProjectsMap) {
        String queryStr = RETRIEVE_STUDIO_PROJECT_DATA_SQL;
        if (userId != null) {
            queryStr += " AND upg.user_id = :userId";
        }

        if (directProjectId != null) {
            queryStr += " AND p.project_id = :directProjectId";
        }

        // Create query for retrieving studio projects data
        Query query = studioEntityManager.createNativeQuery(queryStr);

        // set the parameter value if present
        if (userId != null) {
            query.setParameter("userId", userId);
        }

        if (directProjectId != null) {
            query.setParameter("directProjectId", directProjectId);
        }

        List<Object[]> resultList = query.getResultList();

        // extract the studio project data
        for (Object[] row : resultList) {
            StudioProjectData studioProjectData = new StudioProjectData();
            // primary key, never null.
            long tcDirectProjectId = (Integer) row[0];
            studioProjectData.setTcDirectProjectId(tcDirectProjectId);
            // project_id can be null since LEFT JOIN is used.
            if (null != row[1]) {
                studioProjectData.setProjectId(new Long((Integer) row[1]));
            }

            // possible null since LEFT JOIN is used.
            if (null != row[2]) {
                studioProjectData.setContestId(((BigDecimal) row[2]).longValue());
            }
            studioProjectData.setProjectName((String) row[3]);
            studioProjectData.setContestName((String) row[4]);
            studioProjectData.setStartDate((Date) row[5]);
            studioProjectData.setEndDate((Date) row[6]);
            // user_id can not be null.
            studioProjectData.setCreateUserId(new Long((Integer) row[7]));
            studioProjectData.setContestStatus((String) row[8]);
            studioProjectData.setContestType((String) row[9]);
            if (studioProjectData.getStartDate() != null && studioProjectData.getStartDate().before(now) &&
                    studioProjectData.getContestStatus() != null &&
                    studioProjectData.getContestStatus().startsWith(ACTIVE_STATUS)) {
                studioProjectData.setStarted(true);
            }
            if (studioProjectData.getEndDate() != null && studioProjectData.getEndDate().before(now)) {
                studioProjectData.setFinished(true);
            }

            // save the studio project data into the corresponding game plan data.
            TCDirectProjectGamePlanData tcDirectProjectData =
                    getTCDirectProjectGamePlanData(tcDirectProjectsMap, tcDirectProjectId);
            tcDirectProjectData.getStudioProjects().add(studioProjectData);
        }
    }

    /**
     * Updates the IDs of dependency projects for the given list of <b>SoftwareProjectData</b>.
     *
     * @param softwareProjects the list of SoftwareProjectData instance to update dependency project ids.
     * @throws PersistenceException If any problem to access the persistence.
     */
    private void updateDependencyProjectIds(List<SoftwareProjectData> softwareProjects) {
        // Create query for retrieving dependency project IDs
        Query dependencyQuery = softwareEntityManager.createNativeQuery(RETRIEVE_DEPENDENCY_PROJECT_IDS_SQL);
        for (SoftwareProjectData softwareProjectData : softwareProjects) {
            // Set project ID to the query
            dependencyQuery.setParameter("projectId", softwareProjectData.getProjectId());
            List<Integer> dependencyProjectIdsList = dependencyQuery.getResultList();

            // extract the IDs of dependency projects
            long[] dependencyProjectIds = new long[dependencyProjectIdsList.size()];
            for (int i = 0; i < dependencyProjectIds.length; i++) {
                dependencyProjectIds[i] = dependencyProjectIdsList.get(i);
            }
            softwareProjectData.setDependencyProjectIds(dependencyProjectIds);
        }
    }

    /**
     * Retrieves the software project data.
     *
     * @param userId              the user id that the project associated with.
     * @param now                 the current retrieval time.
     * @param tcDirectProjectsMap the map to hold the TCDirectProjectGamePlanData instance.
     * @throws ClassCastException If fail to convert the retrieved data.
     * @throws PersistenceException If any problem to access the persistence.
     */
    private void retrieveSoftwareProjectData(Long userId, Long directProjectId, Date now,
                                             Map<Long, TCDirectProjectGamePlanData> tcDirectProjectsMap) {
        // Create list to hold all software projects
        List<SoftwareProjectData> allSoftwareProjects = new ArrayList<SoftwareProjectData>();

        String queryStr = RETRIEVE_SOFTWARE_PROJECT_DATA;
        if (userId != null) {
            queryStr += " AND upg.user_id = :userId";
        }

        if (directProjectId != null) {
            queryStr += " AND p.tc_direct_project_id = :directProjectId";
        }

        // Create query to retrieve software project data
        Query query = softwareEntityManager.createNativeQuery(queryStr);
        if (userId != null) {
            query.setParameter("userId", userId);
        }

        if (directProjectId != null) {
            query.setParameter("directProjectId", directProjectId);
        }

        List<Object[]> resultList = query.getResultList();

        // extract the software project data.
        for (Object[] row : resultList) {
            SoftwareProjectData softwareProjectData = new SoftwareProjectData();
            // Since INNER JOIN is applied to project and tc_direct_project, tc_direct_project_id will never null.
            long tcDirectProjectId = (Integer) row[0];
            softwareProjectData.setTcDirectProjectId(tcDirectProjectId);
            // project_id is the primary key, so will never null.
            softwareProjectData.setProjectId((Integer) row[1]);
            softwareProjectData.setProjectName((String) row[2]);
            softwareProjectData.setStartDate((Date) row[3]);
            softwareProjectData.setEndDate((Date) row[4]);
            // user_id is not null in tc_direct_project table, so cast is safe.
            softwareProjectData.setCreateUserId(new Long((Integer) row[5]));
            softwareProjectData.setProjectStatus((String) row[6]);
            softwareProjectData.setCurrentPhase((String) row[7]);
            softwareProjectData.setProjectType((String) row[8]);
            // the count function will never return null, so cast is safe.
            softwareProjectData.setRepost(((BigDecimal) row[9]).intValue() > 0);

            if (softwareProjectData.getStartDate() != null && softwareProjectData.getStartDate().before(now) &&
                    ACTIVE_STATUS.equals(softwareProjectData.getProjectStatus())) {
                softwareProjectData.setStarted(true);
            }
            if (softwareProjectData.getEndDate() != null && softwareProjectData.getEndDate().before(now)) {
                softwareProjectData.setFinished(true);
            }

            // Get list of software projects for this TC Direct project and add it.
            getTCDirectProjectGamePlanData(tcDirectProjectsMap, tcDirectProjectId).getSoftwareProjects()
                    .add(softwareProjectData);
            allSoftwareProjects.add(softwareProjectData);
        }

        updateDependencyProjectIds(allSoftwareProjects);
    }

    /**
     * <p>Gets the <code>TCDirectProjectGamePlanData</code> instance associated with the given
     * <code>tcDirectProjectId</code>. If not present, create one and save in the map.</p>
     *
     * @param tcDirectProjectsMap the map holds the mapping from tcDirectProjectId to TCDirectProjectGamePlanData
     *                            instance.
     * @param tcDirectProjectId   the TC Direct project id.
     * @return the associated <code>TCDirectProjectGamePlanData</code> instance
     */
    private TCDirectProjectGamePlanData getTCDirectProjectGamePlanData(
            Map<Long, TCDirectProjectGamePlanData> tcDirectProjectsMap,
            long tcDirectProjectId) {
        // Retrieve TC Direct project data from the map
        TCDirectProjectGamePlanData tcDirectProjectData = tcDirectProjectsMap.get(tcDirectProjectId);
        if (tcDirectProjectData == null) {
            // Create new TC Direct project game plan data instance and saved in the map.
            tcDirectProjectData = new TCDirectProjectGamePlanData();
            tcDirectProjectData.setTcDirectProjectId(tcDirectProjectId);
            
            tcDirectProjectsMap.put(tcDirectProjectId, tcDirectProjectData);
        }
        return tcDirectProjectData;
    }

    /**
     * <p>Logs the message at INFO level.</p>
     *
     * @param message the log message
     */
    private void logInfo(String message) {
        if (null != log) {
            log.log(Level.INFO, message);
        }
    }

    /**
     * <p>Logs the exception at ERROR level.</p>
     *
     * @param e the error exception
     * @return the error exception
     */
    private <T extends Throwable> T logException(T e) {
        if (null != log) {
            log.log(Level.ERROR, e, e.getMessage());
        }

        return e;
    }

    /**
     * <p>Formats the List&lt;TCDirectProjectGamePlanData&gt; instance with string representation.</p>
     *
     * @param tcDirectProjectGamePlanDataList
     *         the list of TCDirectProjectGamePlanData.
     * @return the string representation of List&lt;TCDirectProjectGamePlanData&gt; instance
     */
    private String format(List<TCDirectProjectGamePlanData> tcDirectProjectGamePlanDataList) {

        List<Long> ids = new ArrayList<Long>();
        for (TCDirectProjectGamePlanData tcDirectProjectGamePlanData : tcDirectProjectGamePlanDataList) {
            ids.add(tcDirectProjectGamePlanData.getTcDirectProjectId());
        }
        StringBuilder stringBuilder = new StringBuilder("list of TCDirectProjectGamePlanData with tcDirectProjectIds ");
        stringBuilder.append(ids.toString());

        return stringBuilder.toString();
    }
}

