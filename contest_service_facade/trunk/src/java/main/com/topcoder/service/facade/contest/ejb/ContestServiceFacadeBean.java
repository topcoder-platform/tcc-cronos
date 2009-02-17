/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.payment.paypal.PayPalPaymentProcessor;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.Competition;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.facade.contest.ContestServiceFilter;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.PrizeData;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;

import org.jboss.ws.annotation.EndpointConfig;

import java.util.List;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * <p>This is an implementation of <code>Contest Service Facade</code> web service in form of stateless session EJB. It
 * holds a reference to {@link StudioService} which is delegated the fulfillment of requests.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles({"Cockpit User" })
@RolesAllowed({"Cockpit User" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestServiceFacadeBean implements ContestServiceFacadeLocal, ContestServiceFacadeRemote {

    /**
     * <p>A <code>StudioService</code> providing access to available <code>Studio Service EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Studio Service</code> component.</p>
     */
    @EJB(name = "ejb/StudioService")
    private StudioService studioService = null;

	/**
	 * <p>
	 * A <code>SessionContext</code> providing access to available session
	 * information. The bean instance is injected through external mechanism
	 * (ejb).
	 * </p>
	 */
	@Resource
	private SessionContext sessionContext;

	/**
	 * <p>
	 * A <code>PaymentProcessor</code> instance of payment processor
	 * implementing class. All payment requests are processed through this
	 * instance.
	 * </p>
	 */
	private PaymentProcessor paymentProcessor = null;

	/**
	 * Private constant specifying non-active not yet published status id.
	 */
	private static final long CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED = 1;

	/**
	 * Private constant specifying draft status id.
	 */
	private static final long CONTEST_DETAILED_STATUS_DRAFT = 15;

	/**
	 * Private constant specifying active & public status id.
	 */
	private static final long CONTEST_STATUS_ACTIVE_PUBLIC = 2;

	/**
	 * Private constant specifying active & public status id.
	 */
	private static final long CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2;

	/**
	 * <p>
	 * Constructs new <code>ContestServiceFacadeBean</code> instance. This
	 * implementation instantiates new instance of payment processor.
	 * 
	 * Current implementation just support processing through PayPalCreditCard.
	 * When multiple processors are desired the implementation should use
	 * factory design pattern to get the right instance of the payment
	 * processor.
	 * </p>
	 * 
	 * @throws PaymentException
	 *             exception when instantiating PaymentProcessor.
	 *             PaymentProcessor usually do merchant authentication etc at
	 *             initialization time, if this fails it is thrown as exception.
	 */
	public ContestServiceFacadeBean() throws PaymentException {
		paymentProcessor = new PayPalPaymentProcessor();
	}

    /**
     * <p>Creates new contest for specified project. Upon creation an unique ID is generated and assigned to returned
     * contest.</p>
     *
     * @param contest a <code>StudioCompetition</code> providing the data for new contest to be created.
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project the new competition belongs to.
     * @return a <code>StudioCompetition</code> providing the data for created contest and having the ID auto-generated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>competition</code> is <code>null</code> or if the specified
     * <code>tcDirectProjectId</code> is negative.
     */
    public StudioCompetition createContest(StudioCompetition contest, long tcDirectProjectId)
        throws PersistenceException {
        ContestData contestData = convertToContestData(contest);
		//contestData.setStatusId(CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED);
		//contestData.setDetailedStatusId(CONTEST_DETAILED_STATUS_DRAFT);
		double total=0;
		for(PrizeData prize:contestData.getPrizes())
		{
			total+=prize.getAmount();
		}
		contestData.setContestAdministrationFee(total*0.2);
		contestData.setDrPoints(total*0.1);

		// BUGR-1088

        Date startDate = getDate(contestData.getLaunchDateAndTime());
        Date endDate = new Date((long) (startDate.getTime() + 60l * 60 * 1000 * contestData.getDurationInHours()));
        Date winnerAnnouncementDeadlineDate;

		if(contestData.getDurationInHours() <= 24) {
	        winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() + 60l * 60 * 1000 * 24));

		} else {
	        winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() + 60l * 60 * 1000 * contestData.getDurationInHours()));
		}

		contestData.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(winnerAnnouncementDeadlineDate));

        ContestData createdContestData = this.studioService.createContest(contestData, tcDirectProjectId);
        return (StudioCompetition) convertToCompetition(CompetionType.STUDIO, createdContestData);
    }

    /**
     * <p>Gets the contest referenced by the specified ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get details for.
     * @return a <code>StudioCompetition</code> providing the data for the requested contest.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contestId</code> is negative.
     */
    public StudioCompetition getContest(long contestId) throws PersistenceException, ContestNotFoundException {
        ContestData studioContest = this.studioService.getContest(contestId);
        return (StudioCompetition) convertToCompetition(CompetionType.STUDIO, studioContest);
    }

    /**
     * <p>Gets the contests for the given project.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project to get the list of associated contests
     *        for.
     * @return a <code>List</code> providing the contests for specified project. Empty list is returned if there are no
     *         such contests found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ProjectNotFoundException if requested project is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>tcDirectProjectId</code> is negative.
     */
    public List<StudioCompetition> getContestsForProject(long tcDirectProjectId)
        throws PersistenceException, ProjectNotFoundException {
        List<ContestData> studioContests = this.studioService.getContestsForProject(tcDirectProjectId);
        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>Updates the specified contest.</p>
     *
     * @param contest a <code>StudioCompetition</code> providing the contest data to update.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contest</code> is <code>null</code>.
     */
    public void updateContest(StudioCompetition contest) throws PersistenceException, ContestNotFoundException {
        ContestData studioContest = convertToContestData(contest);
        this.studioService.updateContest(studioContest);
    }

    /**
     * <p>Sets the status of contest referenced by the specified ID to specified value.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to update status for.
     * @param newStatusId a <code>long</code> providing the ID of a new contest status to set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws StatusNotAllowedException if the specified status is not allowed to be set for specified contest.
     * @throws StatusNotFoundException if specified status is not found.
     * @throws ContestNotFoundException if specified contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     */
    public void updateContestStatus(long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException {
        this.studioService.updateContestStatus(contestId, newStatusId);
    }

    /**
     * <p>Uploads the specified document and associates it with assigned contest.</p>
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     */
    public UploadedDocument uploadDocumentForContest(UploadedDocument uploadedDocument)
        throws PersistenceException, ContestNotFoundException {
        return this.studioService.uploadDocumentForContest(uploadedDocument);
    }

    /**
     * <p>Uploads the specified document without associating it with any contest.</p>
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     */
    public UploadedDocument uploadDocument(UploadedDocument uploadedDocument) throws PersistenceException {
        return this.studioService.uploadDocument(uploadedDocument);
    }

    /**
     * <p>Associates the specified document with specified contest.</p>
     *
     * @param documentId a <code>long</code> providing the ID of a document to be associated with specified contest.
     * @param contestId a <code>long</code> providing the ID of a contest to associate the specified document with.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if any of requested contest or document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     */
    public void addDocumentToContest(long documentId, long contestId) throws PersistenceException,
                                                                             ContestNotFoundException {
        this.studioService.addDocumentToContest(documentId, contestId);
    }

    /**
     * <p>Removes the specified document from specified contest.</p>
     *
     * @param document an <code>UploadedDocument</code> representing the document to be removed.
     * @throws PersistenceException if some persistence errors occur.
     * @throws DocumentNotFoundException if the specified document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     */
    public void removeDocumentFromContest(UploadedDocument document) throws PersistenceException,
                                                                            DocumentNotFoundException {
        this.studioService.removeDocumentFromContest(document);
    }

    /**
     * <p>Retrieves the list of submissions for the specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get the list of submissions for.
     * @return a <code>List</code> providing the details for the submissions associated with the specified contest.
     *         Empty list is returned if there are no submissions found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified ID is negative.
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId) throws PersistenceException,
                                                                                     ContestNotFoundException {
        return this.studioService.retrieveSubmissionsForContest(contestId);
    }

    /**
     * <p>Retrieves the list of submissions for the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of submissions for.
     * @return a <code>List</code> providing the details for the submissions associated with the specified user. Empty
     *         list is returned if there are no submissions found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified ID is negative.
     */
    public List<SubmissionData> retrieveAllSubmissionsByMember(long userId) throws PersistenceException,
                                                                                   UserNotAuthorizedException {
        return this.studioService.retrieveAllSubmissionsByMember(userId);
    }

    /**
     * <p>Updates specified submission.</p>
     *
     * @param submission a <code>SubmissionData</code> providing the data for submission to be updated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     */
    public void updateSubmission(SubmissionData submission) throws PersistenceException {
        this.studioService.updateSubmission(submission);
    }

    /**
     * <p>Removes specified submission.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to remove.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the <code>submissionId</code>  is negative.
     */
    public void removeSubmission(long submissionId) throws PersistenceException {
        this.studioService.removeSubmission(submissionId);
    }

    /**
     * <p>Gets existing contest statuses.</p>
     *
     * @return a <code>List</code> listing available contest statuses. Empty list is returned if there are no statuses.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException {
        return this.studioService.getStatusList();
    }

    /**
     * <p>Gets the list of existing submission types.</p>
     *
     * @return a <code>String</code> providing the comma-separated list of submission types.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     */
    public String getSubmissionFileTypes() throws PersistenceException {
        return this.studioService.getSubmissionFileTypes();
    }

    /**
     * <p>Gets the list of all existing contests.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<StudioCompetition> getAllContests() throws PersistenceException {
        List<ContestData> studioContests = this.studioService.getAllContests();
        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }
    /**
     * <p>Gets the list of all existing contests for contest monitor widget.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestData() throws PersistenceException {
        return  studioService.getSimpleContestData();
    }
    
    /**
     * <p>Gets the list of all existing contests related to given project for my project widget.</p>
     *
     * @param the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(long pid) throws PersistenceException
    {
    	return studioService.getSimpleProjectContestData(pid);
    }
    
    /**
     * <p>Gets the list of all existing contests related to given project for contest monitor widget .</p>
     *
     * @param pid the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestDataByPID(long pid) throws PersistenceException
    {
    	return studioService.getSimpleContestData(pid);
    }

	 /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnly() throws PersistenceException {
		return studioService.getContestDataOnly();
	}
    
    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget related to given project.
     * </p>
     * 
     * @param  the given project id
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnlyByPID(long pid) throws PersistenceException
    {
    	return studioService.getContestDataOnly(pid);
    }

    /**
     * <p>Gets the list of all existing contests for my project widget.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException {
        return  studioService.getSimpleProjectContestData();
    }

    /**
     * <p>Gets the list of existing contests matching the specified criteria.</p>
     *
     * @param filter a <code>Filter</code> providing the criteria for searching for contests.
     * @return a <code>List</code> listing all existing contests matching the specified criteria. Empty list is returned
     *         if there are no matching contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws IllegalArgumentException if specified <code>filter</code> is <code>null</code> or if it is not supported
     * by implementor.
     */
    public List<StudioCompetition> searchContests(ContestServiceFilter filter) throws PersistenceException {
        List<ContestData> studioContests = this.studioService.searchContests(filter.getFilter());
        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>Gets the list of existing contests associated with the client referenced by the specified ID.</p>
     *
     * @param clientId a <code>long</code> providing the ID of a client to get the associated contests for.
     * @return a <code>List</code> providing the contests associated with the requested client. Empty list is returned
     *         if there are no matching contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<StudioCompetition> getContestsForClient(long clientId) throws PersistenceException {
        List<ContestData> studioContests = this.studioService.getContestsForClient(clientId);
        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>Gets the submission referenced by the specified ID.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to get details for.
     * @return a <code>SubmissionData</code> providing details for the submission referenced by the specified ID or
     *         <code>null</code> if such a submission is not found.
     * @throws PersistenceException if any error occurs during the retrieval.
     */
    public SubmissionData retrieveSubmission(long submissionId) throws PersistenceException {
        return this.studioService.retrieveSubmission(submissionId);
    }

    /**
     * <p>Gets existing contest types.</p>
     *
     * @return a <code>List</code> listing available contest types. Empty list is returned if there are no types.
     * @throws PersistenceException if some persistence errors occur.
     */
    public List<ContestTypeData> getAllContestTypes() throws PersistenceException {
        return this.studioService.getAllContestTypes();
    }

    /**
     * <p>Removes the document referenced by the specified ID.</p>
     *
     * @param documentId a <code>long</code> providing the ID of a document to remove.
     * @return <code>true</code> if requested document was removed successfully; <code>false</code> otherwise.
     * @throws PersistenceException if some persistence errors occur.
     * @throws IllegalArgumentWSException if specified <code>documentId</code> is negative.
     */
    public boolean removeDocument(long documentId) throws PersistenceException {
        return this.studioService.removeDocument(documentId);
    }

    /**
     * <p>Gest the MIME type matching the specified context type.</p>
     *
     * @param contentType a <code>String</code> providing the content type to get the matching MIME type for.
     * @return a <code>long</code> providing the ID of MIME type matching the specified content type.
     * @throws PersistenceException if some persistence errors occur.
     * @throws IllegalArgumentWSException if the specified <code>contentType</code> is <code>null</code> or empty.
     */
    public long getMimeTypeId(String contentType) throws PersistenceException {
        return this.studioService.getMimeTypeId(contentType);
    }

    /**
     * <p>Purchases the specified submission. E.g. records a fact that submission referenced by specified ID has been
     * paid for in the course of payment transaction referenced by the specified <code>PayPal</code> order ID.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission which has been paid for.
     * @param payPalOrderId a <code>String</code> providing the <code>PayPal</code> order ID referencing the payment
     * transaction.
     * @param securityToken a <code>String</code> providing the security token to be used for tracking the payment and
     * prevent fraud.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if specified <code>submissionId</code> is negative.
     */
    public void purchaseSubmission(long submissionId, String payPalOrderId, String securityToken)
        throws PersistenceException {
        this.studioService.purchaseSubmission(submissionId, payPalOrderId, securityToken);
    }

    /**
     * <p>Creates a new contest payment. Upon creation an unique ID is generated and assigned to returned payment.</p>
     *
     * @param contestPayment a <code>ContestPaymentData</code> providing the data for the contest payment to be
     * created.
     * @param securityToken a <code>String</code> providing the security token to be used for tracking the payment and
     * prevent fraud.
     * @return a <code>ContestPaymentData</code> providing the details for created contest payment and having the ID for
     *         contest payment auto-generated and set.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public ContestPaymentData createContestPayment(ContestPaymentData contestPayment, String securityToken)
        throws PersistenceException {
		{

			ContestPaymentData payment =  this.studioService.createContestPayment(contestPayment, securityToken);
			

			// TEMP: create forum here, until we have process payment ready
			long contestId = contestPayment.getContestId();

			StudioCompetition competition = null;

			try {
			competition = getContest(contestId);
			} catch (ContestNotFoundException cnfe) {
				throw new PersistenceException("error getting contest", cnfe.getMessage());
			}

			if (competition.getContestData().getForumId() == 0 || competition.getContestData().getForumId() == -1)
			{
				UserProfilePrincipal p = (UserProfilePrincipal) sessionContext
					.getCallerPrincipal();
				long forumid = this.studioService.createForum(competition.getContestData()
					.getName(), p.getUserId());

				competition.getContestData().setForumId(forumid);
				
			}
			
			return payment;
		}
    }

    /**
     * <p>Gets the contest payment referenced by specified contest ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get payment details for.
     * @return a <code>ContestPaymentData</code> representing the contest payment matching the specified ID; or
     *         <code>null</code> if there is no such contest.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public ContestPaymentData getContestPayment(long contestId) throws PersistenceException {
        return this.studioService.getContestPayment(contestId);
    }

    /**
     * <p>Updates specified contest payment data.</p>
     *
     * @param contestPayment a <code>ContestPaymentData</code> providing the details for the contest payment to be
     * updated.
     * @throws PersistenceException if any error occurs when updating contest.
     * @throws IllegalArgumentException if the specified argument is <code>null</code>.
     */
    public void editContestPayment(ContestPaymentData contestPayment) throws PersistenceException {
        this.studioService.editContestPayment(contestPayment);
    }

    /**
     * <p>Removes the contest payment referenced by the specified contest ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to remove payment details for.
     * @return <code>true</code> if requested contest payment was removed successfully; <code>false</code> otherwise.
     * @throws PersistenceException if any error occurs when removing contest.
     */
    public boolean removeContestPayment(long contestId) throws PersistenceException {
        return this.studioService.removeContestPayment(contestId);
    }

    /**
     * <p>Gets existing medium types.</p>
     *
     * @return a <code>List</code> listing available medium types. Empty list is returned if there are no types.
     * @throws PersistenceException if some persistence errors occur.
     */
    public List<MediumData> getAllMediums() throws PersistenceException {
        return this.studioService.getAllMediums();
    }

    /**
     * <p>Sets the placement for the specified submission.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to set the placement for.
     * @param placement an <code>int</code> providing the submission placement.
     * @throws PersistenceException if any error occurs when setting placement.
     */
    public void setSubmissionPlacement(long submissionId, int placement) throws PersistenceException {
        this.studioService.setSubmissionPlacement(submissionId, placement);
    }

    /**
     * <p>Associates the specified submission with the specified prize.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @param prizeId a <code>long</code>  providing the ID of a prize.
     * @throws PersistenceException if any error occurs when setting submission prize.
     */
    public void setSubmissionPrize(long submissionId, long prizeId) throws PersistenceException {
        this.studioService.setSubmissionPlacement(submissionId, prizeId);
    }

    /**
     * <p>Marks the specified submission for purchse.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to be marked for purchase.
     * @throws PersistenceException if any error occurs when marking for purchase.
     */
    public void markForPurchase(long submissionId) throws PersistenceException {
        this.studioService.markForPurchase(submissionId);
    }

    /**
     * <p>Adss the specified list of history data for the associated contest.</p>
     *
     * @param history a <code>List</code> of history data for a contest.
     * @throws PersistenceException if any other error occurs.
     */
    public void addChangeHistory(List<ChangeHistoryData> history) throws PersistenceException {
        this.studioService.addChangeHistory(history);
    }

    /**
     * <p>Gets the history data for the specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for specified contest.
     * @throws PersistenceException if any other error occurs.
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId) throws PersistenceException {
        return this.studioService.getChangeHistory(contestId);
    }

    /**
     * <p>Gets the most history data for the most recent changes to specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for the most recent change for specified contest.
     * @throws PersistenceException if any other error occurs.
     */
    public List<ChangeHistoryData> getLatestChanges(long contestId) throws PersistenceException {
        return this.studioService.getLatestChanges(contestId);
    }

    /**
     * <p>Deletes the contest referenced by the specified ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to delete.
     * @throws PersistenceException if any other error occurs.
     */
    public void deleteContest(long contestId) throws PersistenceException {
        this.studioService.deleteContest(contestId);
    }

    /**
     * <p>Gets the list of all existing contests.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<StudioCompetition> getAllContestHeaders() throws PersistenceException {
        List<ContestData> studioContests = this.studioService.getAllContestHeaders();
        return convertToCompetitions(CompetionType.STUDIO, studioContests);
    }

    /**
     * <p>Sends payments to <code>PACTS</code> application for all unpaid submussions with a prize already assigned.
     * This service is not atomic. If it fails, you'll have to check what payments where actually done trough the
     * <code>submussion.paid</code> flag.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to process missing payments for.
     * @throws PersistenceException if any error occurs when processing a payment.
     */
    public void processMissingPayments(long contestId) throws PersistenceException {
        this.studioService.processMissingPayments(contestId);
    }

	/**
     * <p>
     * Gets all studio file types to return. If no studio file type exists,
     * return an empty list
     * </p>
     * 
     * @return a list of studio file types
     * @throws PersistenceException
     *             if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws PersistenceException {

		return this.studioService.getAllStudioFileTypes();
	}


	/**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     * 
     * @return the list of all available DocumentType
     * 
     * @throws PersistenceException
     *             if any error occurs when getting contest
     * 
     * @since 1.1.2
     */
    public List<DocumentType> getAllDocumentTypes() throws PersistenceException {

		return this.studioService.getAllDocumentTypes();
	}

    /**
     * <p>Converts the specified <code>ContestData</code> instance to <code>ContestData</code> instance which could be
     * passed from <code>Studio Service</code> to <code>Contest Service Facade</code>.</p>
     *
     * @param type a <code>CompetionType</code> specifying the type of the contest.
     * @param contestData a <code>ContestData</code> instance to be converted.
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
     * <p>Converts the specified <code>StudioCompetition</code> instance to <code>ContestData</code> instance which
     * could be passed to <code>Studio Service</code>.</p>
     *
     * @param contest a <code>Competition</code> instance to be converted.
     * @return a <code>ContestData</code> providing the converted data.
     */
    private ContestData convertToContestData(Competition contest) {
        if (contest.getType() == CompetionType.STUDIO) {
            StudioCompetition studioContest = (StudioCompetition) contest;
            return studioContest.getContestData();
        } else {
            throw new IllegalArgumentWSException("Unsupported contest type",
                                                 "Contest type is not supported: " + contest.getType());
        }
    }

    /**
     * <p>Calculates the end time for the specified contest.</p>
     *
     * @param contest a <code>ContestData</code> representing the contest to calculate the end time for.
     * @return an <code>XMLGregorianCalendar</code> providing the end time for the specified contest.
     */
    private XMLGregorianCalendar calculateEndTime(ContestData contest) {
        Date startTime = getDate(contest.getLaunchDateAndTime());
        double durationInHours = contest.getDurationInHours();
        Date endTime = new Date(startTime.getTime() + (long) (durationInHours * 1000 * 60 * 60));
        return getXMLGregorianCalendar(endTime);
    }

    /**
     * <p>Converts the specified contests to <code>StudioCompetition</code> objects.</p>
     *
     * @param type a <code>CompetionType</code> referencing the type of contests to be converted.
     * @param customContests a <code>List</code> providing the details for contests of specified type to be converted to
     *        <code>StudioCompetition</code> objects.
     * @return a <code>List</code> providing the converted data for specified contests.
     */
    private List<StudioCompetition> convertToCompetitions(CompetionType type, List<ContestData> customContests) {
        List<StudioCompetition> contests = new ArrayList<StudioCompetition>();
        for (ContestData studioContest : customContests) {
            StudioCompetition contest = (StudioCompetition) convertToCompetition(type, studioContest);
            contests.add(contest);
        }
        return contests;
    }

    /**
     * <p>Converts specified <code>XMLGregorianCalendar</code> instance into <code>Date</code> instance.</p>
     *
     * @param calendar an <code>XMLGregorianCalendar</code> representing the date to be converted.
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
	 * Converts specified <code>Date</code> instance into
	 * <code>XMLGregorianCalendar</code> instance.
	 * </p>
	 * 
	 * @param date
	 *            a <code>Date</code> representing the date to be converted.
	 * @return a <code>XMLGregorianCalendar</code> providing the converted value
	 *         of specified date or <code>null</code> if specified
	 *         <code>date</code> is <code>null</code> or if it can't be
	 *         converted to calendar.
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
	 * Processes the contest payment. It does following steps:
	 * <ul>
	 * <li>Checks contest id to decide whether to create new contest or update
	 * existing contest</li>
	 * <li>If payment type is credit card then it processes the payment through
	 * <code>PaymentProcessor</code></li>
	 * <li>Right-now this method doesn't process PO payments.</li>
	 * <li>
	 * On successful processing -
	 * <ul>
	 * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
	 * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
	 * <li>set payment reference number and type</li>
	 * <li>Creates new forum for the contest, forum name being contest name. It
	 * uses studio service for doing the same.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param <code>ContestData</code> data that recognizes a contest.
	 * @param <code>PaymentData</code> payment information (credit card/po
	 *        details) that need to be processed.
	 * @return a <code>PaymentResult</code> result of the payment processing.
	 * @throws PersistenceException
	 *             if any error occurs when getting contest.
	 * @throws ContestNotFoundException
	 * @throws IllegalArgumentException
	 *             if specified <code>filter</code> is <code>null</code> or if
	 *             it is not supported by implementor.
	 */
	public PaymentResult processContestPayment(ContestData contestData,
			PaymentData paymentData) throws PersistenceException,
			PaymentException, ContestNotFoundException {

		long contestId = contestData.getContestId();

		StudioCompetition competition = null;
		try {
			competition = getContest(contestId);
		} catch (ContestNotFoundException cnfe) {
			// if not contest is found then simply ignore it.
		}

		if (competition == null) {
			competition = createContest(
					(StudioCompetition) convertToCompetition(
							CompetionType.STUDIO, contestData), contestData
							.getTcDirectProjectId());
		}

		PaymentResult result = null;

		if (paymentData instanceof TCPurhcaseOrderPaymentData) {
			// processing purchase order is not in scope of this assembly.
			result = new PaymentResult();
			result.setReferenceNumber("NOT IN SCOPE FOR PO");
		} else if (paymentData instanceof CreditCardPaymentData) {
			result = paymentProcessor.process(paymentData, Double
					.toString(contestData.getContestAdministrationFee()));
			competition.getContestData().setStatusId(
					CONTEST_STATUS_ACTIVE_PUBLIC);
			competition.getContestData().setDetailedStatusId(
					CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC);

			ContestPaymentData contestPaymentData = new ContestPaymentData();
			contestPaymentData.setPaypalOrderId(result.getReferenceNumber());

			UserProfilePrincipal p = (UserProfilePrincipal) sessionContext
					.getCallerPrincipal();
			String userId = Long.toString(p.getUserId());

			createContestPayment(contestPaymentData, userId);

			// update contest.
			updateContest(competition);

			// create forum for the contest.
			this.studioService.createForum(competition.getContestData()
					.getName(), p.getUserId());
		}

		return result;
	}
}
