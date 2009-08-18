/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.ejb;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.EntityNotFoundException;

import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;

import com.topcoder.service.pipeline.CompetitionType;
import com.topcoder.service.pipeline.ContestPipelineServiceException;
import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.entities.CompetitionChangeType;
import com.topcoder.service.pipeline.entities.CompetitionPipelineInfo;
import com.topcoder.service.pipeline.entities.SoftwareCompetitionPipelineInfo;
import com.topcoder.service.pipeline.entities.StudioCompetitionPipelineInfo;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.StudioService;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * This is an implementation of <code>ContestPipelineService</code> web service in form of stateless session EJB.
 * </p>
 * 
 * <p>
 * Updated for Pipeline Conversion Cockpit Integration Assembly 1 v1.0
 *      - Added method for getContestsByDate
 *      - Added method to retrieve change histories for array of contest ids and their types.
 * </p>
 * 
 * <p>
 * Thread-safty: This is an CMT bean, so it transaction is managed by the container.
 * </p>
 * 
 * @author waits, snow01
 * @version 1.0
 * @since Pipeline Conversion Service v1.0
 */
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestPipelineServiceBean implements ContestPipelineServiceRemote, ContestPipelineServiceLocal {
    /** Private constant specifying project type info's version id key. */
    private static final String PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY = "External Reference ID";

    /** Base SQL Query to fetch the studio change history. */
    private static final String GET_STUDIO_CHANGE_HISTORY_QUERY = "SELECT ch FROM StudioCompetitionChangeHistory ch WHERE ch.pipelineInfo.contestId=:contestId AND ch.changeType=:changeType";

    /** Base SQL Query to fetch the software change history. */
    private static final String GET_SOFTWARE_CHANGE_HISTORY_QUERY = "SELECT ch FROM SoftwareCompetitionChangeHistory ch where ch.pipelineInfo.componentId=:contestId AND ch.changeType=:changeType";

    /**
     * <p>
     * A <code>StudioService</code> providing access to available <code>Studio Service EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Studio Service</code> component.
     * </p>
     */
    @EJB(name = "ejb/StudioService")
    private StudioService studioService = null;

    /**
     * <p>
     * A <code>CatalogService</code> providing access to available <code>Category Services EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Category Services</code> component.
     * </p>
     */
    @EJB(name = "ejb/CatalogService")
    private CatalogService catalogService = null;

    /**
     * <p>
     * A <code>ProjectServices</code> providing access to available <code>Project Services EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Project Services</code> component.
     * </p>
     */
    @EJB(name = "ejb/ProjectServicesBean")
    private ProjectServices projectServices = null;

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "softwareUnitName")
    private String softwareUnitName;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "studioUnitName")
    private String studioUnitName;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * A default empty constructor.
     */
    public ContestPipelineServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    protected void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        // first record in logger
        logExit("init");
    }

    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     * 
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     * 
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistories(long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestDateChangeHistories(long[] contestIds, CompetitionType[] competitionTypes, CompetitionChangeType changeType) method");
        logger.log(Level.DEBUG, "The parameters[contestIds =" + Arrays.toString(contestIds) + ", competitionTypes = "
                + Arrays.toString(competitionTypes) + "].");

        try {
            return getContestChangeHistories(contestIds, competitionTypes, CompetitionChangeType.DATE);
        } finally {
            this.logExit("getContestDateChangeHistories");
        }
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     * 
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestPrizeChangeHistories(long[] contestIds, CompetitionType[] competitionTypes, CompetitionChangeType changeType) method");
        logger.log(Level.DEBUG, "The parameters[contestIds =" + Arrays.toString(contestIds) + ", competitionTypes = "
                + Arrays.toString(competitionTypes) + "].");

        try {
            return getContestChangeHistories(contestIds, competitionTypes, CompetitionChangeType.PRIZE);
        } finally {
            this.logExit("getContestPrizeChangeHistories");
        }
    }

    /**
     * <p>
     * Search the competition change history for the given contest ids and their competition types.
     * </p>
     * 
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CompetitionChangeHistory> getContestChangeHistories(long[] contestIds,
            String[] competitionTypes, CompetitionChangeType changeType)
            throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestChangeHistories(long[] contestIds, CompetitionType[] competitionTypes, CompetitionChangeType changeType) method");
        logger.log(Level.DEBUG, "The parameters[contestIds =" + Arrays.toString(contestIds) + ", competitionTypes = "
                + Arrays.toString(competitionTypes) + "].");

        ExceptionUtils.checkNull(contestIds, null, null, "contestIds is null");
        ExceptionUtils.checkNull(competitionTypes, null, null, "competitionTypes is null");
        if (contestIds.length != competitionTypes.length) {
            throw wrapContestPipelineServiceException("Both array contestIds and competitionTypes should be of same length");
        }

        try {
            List<CompetitionChangeHistory> results = new LinkedList<CompetitionChangeHistory>();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < contestIds.length; i++) {
                results.addAll(getHistory(contestIds[i], competitionTypes[i].equals("STUDIO") ? CompetitionType.STUDIO : CompetitionType.SOFTWARE, changeType));
            }

            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return results;
        } finally {
            this.logExit("getContestChangeHistories");
        }
    }

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistory(long contestId, CompetitionType competitionType)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG,
                "Enter getContestDateChangeHistory(long contestId, CompetitionType competitionType) method");
        logger.log(Level.DEBUG, "The parameters[contestId =" + contestId + ", competitionType = " + competitionType
                + "].");

        try {
            long startTime = System.currentTimeMillis();
            List<CompetitionChangeHistory> results = getHistory(contestId, competitionType, CompetitionChangeType.DATE);
            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return results;
        } finally {
            this.logExit("getContestDateChangeHistory");
        }
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     * 
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistory(long contestId, CompetitionType competitionType)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG,
                "Enter getContestPrizeChangeHistory(long contestId, CompetitionType competitionType) method");
        logger.log(Level.DEBUG, "The parameters[contestId =" + contestId + ", competitionType = " + competitionType
                + "].");

        try {
            long startTime = System.currentTimeMillis();
            List<CompetitionChangeHistory> results = getHistory(contestId, competitionType, CompetitionChangeType.PRIZE);
            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return results;
        } finally {
            this.logExit("getContestPrizeChangeHistory");
        }
    }

    /**
     * Gets the specified type of competition change history for the specified contest id of the specified competition
     * type.
     * 
     * @param contestId
     *            specified contest id.
     * @param competitionType
     *            specified competition type.
     * @param changeType
     *            specified change type.
     * 
     * @return the specified type of competition change history for the specified contest id of the specified
     *         competition type.
     * 
     * @throws ContestPipelineServiceException
     *             if any error occurs during persistence retrieval.
     */
    @SuppressWarnings("unchecked")
    private List<CompetitionChangeHistory> getHistory(long contestId, CompetitionType competitionType,
            CompetitionChangeType changeType) throws ContestPipelineServiceException {
        ExceptionUtils.checkNull(competitionType, null, null, "CompetitionType is null");

        try {
            Query query = null;
            
            if (competitionType == CompetitionType.STUDIO) {
                EntityManager em = getStudioEntityManager();
                query = em.createQuery(GET_STUDIO_CHANGE_HISTORY_QUERY);
            } else {
                EntityManager em = getSoftwareEntityManager();
                query = em.createQuery(GET_SOFTWARE_CHANGE_HISTORY_QUERY);
            }
            
            query.setParameter("contestId", contestId);
            query.setParameter("changeType", changeType);

			List<CompetitionChangeHistory> results = (List<CompetitionChangeHistory>) query.getResultList();
			
			for (CompetitionChangeHistory r : results) {
			    r.setContestId(contestId);
			}
            
            logDebug("The results are:" + Arrays.deepToString(results.toArray(new CompetitionChangeHistory[0])));

            return results;
        } catch (PersistenceException pe) {
            throw wrapContestPipelineServiceException(pe, "Fail to retrieve the change history.");
        } catch (RuntimeException re) {
            throw wrapContestPipelineServiceException(re, "Fail to retrieve the change history.");
        }
    }

    /**
     * Gets the list of competition for the specified date search criteria.
     * 
     * @param searchCriteria
     *            the date search criteria
     * 
     * @return the list of competition for the specified search criteria.
     * 
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     * 
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Competition> getContestsByDate(DateSearchCriteria criteria) throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getContestsByDate(ContestsSearchCriteria criteria) method.");
        logger.log(Level.DEBUG, "with parameter criteria:" + criteria);
        return getContests(criteria);
    }

    /**
     * Gets the list of competition for the specified search criteria.
     * 
     * @param searchCriteria
     *            the search criteria
     * 
     * @return the list of competition for the specified search criteria.
     * 
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Competition> getContests(ContestsSearchCriteria criteria) throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getContests(ContestsSearchCriteria criteria) method.");
        logger.log(Level.DEBUG, "with parameter criteria:" + criteria);
        ExceptionUtils.checkNull(criteria, null, null, "The criteria is null.");

        long startTime = System.currentTimeMillis();
        List<Competition> ret = new ArrayList<Competition>();

        try {
            ret.addAll(getSoftwareContests(criteria.getWhereClause(CompetitionType.SOFTWARE)));
            ret.addAll(getStudioContests(criteria.getWhereClause(CompetitionType.STUDIO)));
            logDebug("The results are:" + Arrays.deepToString(ret.toArray(new Competition[0])));

            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return ret;
        } catch (PersistenceException pe) {
            throw wrapContestPipelineServiceException(pe, "Fail to retrieve the contests.");
        } catch (RuntimeException re) {
            throw wrapContestPipelineServiceException(re, "Fail to retrieve the contests.");
        } finally {
            logExit("getContests");
        }
    }

    /**
     * Gets the list of software competition for the specified where clause.
     * 
     * @param whereClause
     *            the search criteria as where clause
     * 
     * @return the list of software competition for the specified where clause.
     * 
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    @SuppressWarnings( { "unchecked" })
    private List<Competition> getSoftwareContests(String whereClause) throws ContestPipelineServiceException {
        List<Competition> competitions = new ArrayList<Competition>();

        String queryStr = constructQuery(whereClause, CompetitionType.SOFTWARE);

        logDebug("Query: " + queryStr);

        EntityManager em = getSoftwareEntityManager();

        //for native query
        if (queryStr.indexOf("SELECT max(nvl(actual_end_time, scheduled_end_time)") != -1) {
            Query query = em.createNativeQuery(queryStr);
            List<Object[]> projectInfoIds = query.getResultList();

            for (Object[] ids : projectInfoIds) {
                Integer projectId = (Integer) ids[0];
                Integer infoId = (Integer) ids[1];
                SoftwareCompetitionPipelineInfo info = getSoftwareCompetitionPipelineInfoById(em, infoId);
                SoftwareCompetition c = getSoftwareContestByProjectId(projectId);
                if (c == null) {
                    throw wrapContestPipelineServiceException("No software competition found for pipeline info id: " +
                        infoId);
                }
                setSoftwarePipelineInfo(c, info);
                competitions.add(c);
            }
            return competitions;
        }
        

		//for jql query
        Query query = em.createQuery(queryStr);

        List<SoftwareCompetitionPipelineInfo> pipelineInfos = query.getResultList();

        for (SoftwareCompetitionPipelineInfo pipelineInfo : pipelineInfos) {
            SoftwareCompetition c = getSoftwareContestByProjectId(getProjectIdByInfo(em, pipelineInfo));
            if (c == null) {
                throw wrapContestPipelineServiceException("No software competition found for pipeline info id: "
                        + pipelineInfo.getPipelineInfoId());
            }

            setSoftwarePipelineInfo(c, pipelineInfo);
            competitions.add(c);
        }

        return competitions;
    }

    /**
     * Gets the project id by info id.
     * 
     * @param em Entity manager.
     * @param pipelineInfo the specified sofware pipeline info
     * @return project id
     */
    private Integer getProjectIdByInfo(EntityManager em, SoftwareCompetitionPipelineInfo pipelineInfo) {
    	String queryProjectIdByComponentId = 
    		"SELECT DISTINCT project_id FROM project_info p, software_competition_pipeline_info info " +
    		"WHERE p.project_info_type_id = 2 and info.component_id = p.value AND info.component_id = " + pipelineInfo.getComponentId() + 
    		" AND info.id = " + pipelineInfo.getPipelineInfoId();
    	Query query = em.createNativeQuery(queryProjectIdByComponentId);
        return (Integer) query.getSingleResult();
    }

    /**
     * Gets the software competition pipeline info by specified info id.
     * 
     * @param em the Entity Manager
     * @param infoId the info id.
     * @return the instance of SoftwareCompetitionPipelineInfo
     * @throws ContestPipelineServiceException if some exception occurs
     */
    private SoftwareCompetitionPipelineInfo getSoftwareCompetitionPipelineInfoById(EntityManager em, Integer infoId)
        throws ContestPipelineServiceException {
        return (SoftwareCompetitionPipelineInfo) em.createQuery(
            "SELECT info FROM SoftwareCompetitionPipelineInfo info WHERE info.id = " + infoId).getSingleResult();
    }

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id, the method wil get all OR project
     * related data, then from project property to get comp version id then to call getAssetByVersionId to get assetDTO,
     * please check create software contest to see what data need to be returned.
     * </p>
     * 
     * @param projectId
     *            the OR Project Id
     * 
     * @return SoftwareCompetition
     * 
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     * 
     * @since BURG-1716
     */
    private SoftwareCompetition getSoftwareContestByProjectId(long projectId) throws ContestPipelineServiceException {
        SoftwareCompetition contest = new SoftwareCompetition();

        try {
            FullProjectData fullProjectData = this.projectServices.getFullProjectData(projectId);
            Long compVersionId = Long.parseLong(fullProjectData.getProjectHeader().getProperty(
                    PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY));
            contest.setAssetDTO(this.catalogService.getAssetByVersionId(compVersionId));
            contest.setProjectHeader(fullProjectData.getProjectHeader());
            contest.setProjectData(fullProjectData);
            contest.setProjectPhases(fullProjectData);
            contest.getProjectPhases().setId(fullProjectData.getProjectHeader().getId());
            contest.setId(projectId);
            contest.setProjectResources(fullProjectData.getResources());

            com.topcoder.project.phases.Phase[] allPhases = fullProjectData.getAllPhases();

            // this is to avoid cycle
            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
                allPhases[i].clearDependencies();
            }

            // set project start date in production date
            contest.getAssetDTO().setProductionDate(getXMLGregorianCalendar(contest.getProjectPhases().getStartDate()));

            // set null to avoid cycle
            contest.getAssetDTO().setDependencies(null);

            if (contest.getAssetDTO().getForum() != null) {
                contest.getAssetDTO().getForum().setCompVersion(null);
            }

            if (contest.getAssetDTO().getLink() != null) {
                contest.getAssetDTO().getLink().setCompVersion(null);
            }

            if ((contest.getAssetDTO().getDocumentation() != null)
                    && (contest.getAssetDTO().getDocumentation().size() > 0)) {
                for (CompDocumentation doc : contest.getAssetDTO().getDocumentation()) {
                    doc.setCompVersion(null);
                }
            }

            return contest;
        } catch (ProjectServicesException pse) {
            throw wrapContestPipelineServiceException(pse, "Fail to get project data from project services.");
        } catch (NumberFormatException nfe) {
            throw wrapContestPipelineServiceException(nfe,
                    "the properites 'Version ID' is not of Long value in project.");
        } catch (EntityNotFoundException e) {
            throw wrapContestPipelineServiceException(e, "the version id does not exist.");
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            throw wrapContestPipelineServiceException(e, "Fail to get project asset.");
        }
    }

    /**
     * <p>
     * This methods sets the StudioCompetitionPipelineInfo to the StudioCompetition
     * </p>
     * 
     * @param competition
     *            the StudioCompetition
     * @param pipelineInfo
     *            the StudioCompetitionPipelineInfo
     */
    private void setStudioPipelineInfo(StudioCompetition competition, StudioCompetitionPipelineInfo pipelineInfo) {
        setPipelineInfo(competition, pipelineInfo);

        if (pipelineInfo.getResources() != null) {
            competition.setResources(pipelineInfo.getResources().toArray(
                    new com.topcoder.management.resource.Resource[0]));
        }
    }

    /**
     * <p>
     * This methods sets the SoftwareCompetitionPipelineInfo to the SoftwareCompetition
     * </p>
     * 
     * @param competition
     *            the SoftwareCompetition
     * @param pipelineInfo
     *            the SoftwareCompetitionPipelineInfo
     */
    private void setSoftwarePipelineInfo(SoftwareCompetition competition, SoftwareCompetitionPipelineInfo pipelineInfo) {
        setPipelineInfo(competition, pipelineInfo);
    }

    /**
     * <p>
     * This methods sets the CompetitionPipelineInfo to the Competition
     * </p>
     * 
     * @param competition
     *            the Competition
     * @param pipelineInfo
     *            the CompetitionPipelineInfo
     */
    private void setPipelineInfo(Competition competition, CompetitionPipelineInfo pipelineInfo) {
        competition.setReviewPayment(pipelineInfo.getReviewPayment());
        competition.setSpecificationReviewPayment(pipelineInfo.getSpecificationReviewPayment());
        competition.setContestFee(pipelineInfo.getContestFee());
        competition.setClientName(pipelineInfo.getClientName());
        competition.setConfidence(pipelineInfo.getConfidence());
        competition.setClientApproval(pipelineInfo.getClientApproval());
        competition.setPricingApproval(pipelineInfo.getPricingApproval());
        competition.setHasWikiSpecification(pipelineInfo.getHasWikiSpecification());
        competition.setPassedSpecReview(pipelineInfo.getPassedSpecReview());
        competition.setHasDependentCompetitions(pipelineInfo.getHasDependentCompetitions());
        competition.setWasReposted(pipelineInfo.getWasReposted());
        competition.setNotes(pipelineInfo.getNotes());
    }

    /**
     * Gets the list of studio competition for the specified where clause.
     * 
     * @param whereClause
     *            the search criteria as where clause
     * 
     * @return the list of studio competition for the specified where clause.
     * 
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    @SuppressWarnings("unchecked")
    private List<Competition> getStudioContests(String whereClause) throws ContestPipelineServiceException {
        String queryStr = constructQuery(whereClause, CompetitionType.STUDIO);

        logDebug("Query: " + queryStr);

        EntityManager em = getStudioEntityManager();
        Query query = em.createQuery(queryStr);

        List<Competition> competitions = new ArrayList<Competition>();

        //
        // changed to retrieve from mock, as in current state persistence layer to retrieve pipeline info was not
        // working.
        // since: Pipeline Conversion Cockpit Integration Assembly 1 v1.0
        // 
        //List<StudioCompetitionPipelineInfo> pipelineInfos = getMockStudioCompetitionPipelineInfo();
		List<StudioCompetitionPipelineInfo> pipelineInfos = (List<StudioCompetitionPipelineInfo>) query.getResultList();

        for (StudioCompetitionPipelineInfo pipelineInfo : pipelineInfos) {
            StudioCompetition c = getContest(pipelineInfo.getContestId());

            if (c == null) {
                throw wrapContestPipelineServiceException("No studio competition found for pipeline info id: "
                        + pipelineInfo.getPipelineInfoId());
            }

            setStudioPipelineInfo(c, pipelineInfo);
            competitions.add(c);
        }

        return competitions;
    }

    /**
     * <p>
     * Gets the contest referenced by the specified ID.
     * </p>
     * 
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get details for.
     * 
     * @return a <code>StudioCompetition</code> providing the data for the requested contest.
     * 
     * @throws ContestPipelineServiceException
     *             if some persistence errors occur.
     */
    private StudioCompetition getContest(long contestId) throws ContestPipelineServiceException {
        try {
            ContestData studioContest = this.studioService.getContest(contestId);
            StudioCompetition competition = (StudioCompetition) convertToCompetition(CompetionType.STUDIO,
                    studioContest);
            competition.getContestData().setPayments(getContestPayments(contestId));

            return competition;
        } catch (Exception e) {
            throw wrapContestPipelineServiceException(e, "Error in getting studio competition");
        }
    }

    /**
     * <p>
     * Converts the specified <code>ContestData</code> instance to <code>ContestData</code> instance which could be
     * passed from <code>Studio Service</code> to <code>Contest Service Facade</code>.
     * </p>
     * 
     * @param type
     *            a <code>CompetionType</code> specifying the type of the contest.
     * @param contestData
     *            a <code>ContestData</code> instance to be converted.
     * 
     * @return a <code>Competition</code> providing the converted data.
     */
    private Competition convertToCompetition(CompetionType type, ContestData contestData) {
        if (type == CompetionType.STUDIO) {
            StudioCompetition data = new StudioCompetition(contestData);
            data.setAdminFee(contestData.getContestAdministrationFee());
            data.setCompetitionId(contestData.getContestId());
            data.setId(contestData.getContestId());
            data.setStartTime(contestData.getLaunchDateAndTime());
            data.setEndTime(calculateEndTime(contestData));
            data.setProject(null); // Projects are not retrieved as for now
            data.setType(type);

            return data;
        } else {
            throw new IllegalArgumentWSException("Unsupported contest type", "Contest type is not supported: " + type);
        }
    }

    /**
     * <p>
     * Calculates the end time for the specified contest.
     * </p>
     * 
     * @param contest
     *            a <code>ContestData</code> representing the contest to calculate the end time for.
     * 
     * @return an <code>XMLGregorianCalendar</code> providing the end time for the specified contest.
     */
    private XMLGregorianCalendar calculateEndTime(ContestData contest) {
        Date startTime = getDate(contest.getLaunchDateAndTime());
        double durationInHours = contest.getDurationInHours();
        Date endTime = new Date(startTime.getTime() + (long) (durationInHours * 1000 * 60 * 60));

        return getXMLGregorianCalendar(endTime);
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into <code>XMLGregorianCalendar</code> instance.
     * </p>
     * 
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * 
     * @return a <code>XMLGregorianCalendar</code> providing the converted value of specified date or <code>null</code>
     *         if specified <code>date</code> is <code>null</code> or if it can't be converted to calendar.
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    /**
     * <p>
     * Converts specified <code>XMLGregorianCalendar</code> instance into <code>Date</code> instance.
     * </p>
     * 
     * @param calendar
     *            an <code>XMLGregorianCalendar</code> representing the date to be converted.
     * 
     * @return a <code>Date</code> providing the converted value of specified calendar or <code>null</code> if specified
     *         <code>calendar</code> is <code>null</code>.
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }

        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>
     * Gets the contest payment referenced by specified contest ID.
     * </p>
     * 
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get payment details for.
     * 
     * @return a <code>ContestPaymentData</code> representing the contest payment matching the specified ID; or
     *         <code>null</code> if there is no such contest.
     * 
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    private List<ContestPaymentData> getContestPayments(long contestId) throws Exception {
        return this.studioService.getContestPayments(contestId);
    }

    /**
     * Constructs the SQL clause for searching contests.
     * 
     * @param whereClause
     *            the ContestsSearchCriteria instance
     * 
     * @return type CompetitionType instance
     */
    private String constructQuery(String whereClause, CompetitionType type) {
        StringBuffer sb = new StringBuffer("SELECT ");

        if (type.equals(CompetitionType.SOFTWARE)) {
            sb.append("pinfo FROM SoftwareCompetitionPipelineInfo pinfo ");
        } else if (type.equals(CompetitionType.STUDIO)) {
            sb.append("pinfo FROM StudioCompetitionPipelineInfo pinfo ");
        }

        if ((whereClause == null) || (whereClause.trim().length() == 0)) {
            return sb.toString();
        }

        if (type.equals(CompetitionType.STUDIO)) {
            sb.append(getStudioFromClause(whereClause));
        } else if (type.equals(CompetitionType.SOFTWARE)) {
            sb.append(getSoftwareFromClause(whereClause));
        }

        if (sb.indexOf("DateSearchCriteria") == -1) {
            sb.append(" WHERE ");

            return sb.append(whereClause).toString();
        } else {
            return whereClause;
        }
    }

    /**
     * Format from clause.
     * 
     * @param whereClause
     *            the where clause from the criteria.
     * 
     * @return the from clause
     */
    private String getSoftwareFromClause(String whereClause) {
        if (whereClause.indexOf("end_date >= to_date") != -1) {
            return "DateSearchCriteria";
        }

        return "";
    }

    /**
     * Format from clause.
     * 
     * @param whereClause
     *            the where clause from the criteria.
     * 
     * @return the from clause
     */
    private String getStudioFromClause(String whereClause) {
        StringBuffer sb = new StringBuffer();

        if (whereClause.indexOf("resource.name") != -1) {
            sb.append(", IN (pinfo.resources) AS resource ");
        }

        if (whereClause.indexOf("prize_contests") != -1) {
            sb.append(", Prize prize, IN (prize.contests) AS prize_contests ");
        }

        if (whereClause.indexOf("contest.contestId") != -1) {
            sb.append(", Contest contest ");
        }

        return sb.toString();
    }

    /**
     * <p>
     * Creates a <code>ContestPipelineServiceException</code> with inner exception and message. It will log the
     * exception, and set the sessionContext to rollback only.
     * </p>
     * 
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * 
     * @return the created exception
     */
    private ContestPipelineServiceException wrapContestPipelineServiceException(Exception e, String message) {
        ContestPipelineServiceException ce = new ContestPipelineServiceException(message, e);
        logException(ce, message);

        return ce;
    }

    /**
     * <p>
     * Creates a <code>ContestPipelineServiceException</code> with inner exception and message. It will log the
     * exception, and set the sessionContext to rollback only.
     * </p>
     * 
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * 
     * @return the created exception
     */
    private ContestPipelineServiceException wrapContestPipelineServiceException(String message) {
        ContestPipelineServiceException ce = new ContestPipelineServiceException(message);
        logException(ce, message);

        return ce;
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     * 
     * @return the EntityManager looked up from the session context
     * 
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getSoftwareEntityManager() throws ContestPipelineServiceException {
        try {
            Object obj = sessionContext.lookup(softwareUnitName);

            if (obj == null) {
                throw wrapContestPipelineServiceException("The object for jndi name '" + softwareUnitName
                        + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapContestPipelineServiceException(e, "The jndi name for '" + softwareUnitName
                    + "' should be EntityManager instance.");
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     * 
     * @return the EntityManager looked up from the session context
     * 
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getStudioEntityManager() throws ContestPipelineServiceException {
        try {
            Object obj = sessionContext.lookup(studioUnitName);

            if (obj == null) {
                throw wrapContestPipelineServiceException("The object for jndi name '" + studioUnitName
                        + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapContestPipelineServiceException(e, "The jndi name for '" + studioUnitName
                    + "' should be EntityManager instance.");
        }
    }

    /**
     * This method is used to log the debug message.
     * 
     * @param msg
     *            the message string.
     */
    private void logDebug(String msg) {
        if (logger != null) {
            logger.log(Level.DEBUG, msg);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     * 
     * @param method
     *            name of the leaved method
     */
    private void logExit(String method) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     * 
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);

            while (e != null) {
                logger.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }
}
