/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.UploadExternalServices;
import com.cronos.onlinereview.services.uploads.UploadServicesException;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices;
import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.EntityNotFoundException;
import com.topcoder.management.project.Project;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.PaymentType;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.payment.paypal.PayPalPaymentProcessor;
import com.topcoder.service.payment.paypal.PayflowProPaymentProcessor;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.Competition;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.StudioService;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.SubmissionFeedback;
import com.topcoder.service.studio.SubmissionPaymentData;
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
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFilter;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.PrizeData;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;
import com.topcoder.management.resource.ResourceRole;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.activation.DataHandler;
import javax.persistence.EntityManager;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;

import org.jboss.logging.Logger;
import org.jboss.ws.annotation.EndpointConfig;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Set;
import java.util.Arrays;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * <p>This is an implementation of <code>Contest Service Facade</code> web service in form of stateless session EJB. It
 * holds a reference to {@link StudioService} which is delegated the fulfillment of requests.</p>
 * 
 * <p>
 * TopCoder Service Layer Integration 3 Assembly change: expose the methods of Category Services, Project Services and
 * Online Review Upload Services.
 * </p>
 *
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: new methods added to support processing contest
 * sale for software contest.
 * </p>
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
     * <p>A <code>CatalogService</code> providing access to available <code>Category Services EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Category Services</code> component.</p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/CatalogService")
    private CatalogService catalogService = null;

    /**
     * <p>A <code>ProjectServices</code> providing access to available <code>Project Services EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Project Services</code> component.</p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/ProjectServicesBean")
    private ProjectServices projectServices = null;

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
     * PayPal API UserName
     */
    @Resource(name = "payPalApiUserName")
    private String apiUserName;

    /**
     * PayPal API Password
     */
    @Resource(name = "payPalApiPassword")
    private String apiPassword;

    /**
     * PayPal API Signature.
     */
    @Resource(name = "payPalApiSignature")
    private String apiSignature;

    /**
     * PayPal API Environment
     */
    @Resource(name = "payPalApiEnvironment")
    private String apiEnvironment;


	@Resource(name = "createForum")
    private boolean createForum = false;

	@Resource(name = "forumBeanProviderUrl")
    private String forumBeanProviderUrl;

	/**
	 * <p>
	 * A <code>PaymentProcessor</code> instance of payment processor
	 * implementing class. All payment requests are processed through this
	 * instance.
	 * </p>
	 */
	private PaymentProcessor paymentProcessor = null;

	/**
	 * <p>
	 * A <code>UploadExternalServices</code> instance of Online Review Upload Services to expose its methods.
	 * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
	 */
	private UploadExternalServices uploadExternalServices = null;

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
	 * Private constant specifying active & public status id.
	 */
	private static final long CONTEST_DETAILED_STATUS_SCHEDULED = 9;

	/**
	 * Private constant specifying active & public status id.
	 */
	private static final long CONTEST_PAYMENT_STATUS_PAID = 1;

	/**
	 * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
	 */
	private static final long CONTEST_SALE_STATUS_PAID = 1;

    private static final long CONTEST_COMPLETED_STATUS = 8;

    /**
     * Private constant specifying active & public status id.
     */
    private static final long PAYMENT_TYPE_PAYPAL_PAYFLOW = 1;

    /**
     * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final long SALE_TYPE_PAYPAL_PAYFLOW = 1;

	/**
	 * Private constant specifying active & public status id.
	 */
	private static final long PAYMENT_TYPE_TC_PURCHASE_ORDER = 2;

	/**
	 * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
	 */
	private static final long SALE_TYPE_TC_PURCHASE_ORDER = 2;

	/**
	 * Private constant specifying payments project info type.
     *
     * @since Module Contest Service Software Contest Sales Assembly
	 */
	private static final String PAYMENTS_PROJECT_INFO_TYPE = "Payments";



	/**
	 * Private constant specifying project type info's component id key.
	 * 
	 * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
	 */
	private static final String PROJECT_TYPE_INFO_COMPONENT_ID_KEY = "Component ID";
	
	/**
     * Private constant specifying project type info's version id key.
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_VERSION_ID_KEY = "Version ID";

    /**
     * Private constant specifying project type info's version id key.
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY = "External Reference ID";

	/**
     * Private constant specifying project type info's forum id key.
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_DEVELOPER_FORUM_ID_KEY = "Developer Forum ID";
	
	/**
     * Private constant specifying project type info's SVN Module key.
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_SVN_MODULE_KEY = "SVN Module";

	/**
     * Private constant specifying project type info's Notes key.
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String PROJECT_TYPE_INFO_NOTES_KEY = "Notes";


	/**
     * Private constant specifying resource role manager id 
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final long RESOURCE_ROLE_MANAGER_ID = 13;

	/**
     * Private constant specifying resource role manager name 
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_ROLE_MANAGER_NAME = "Manger";

	/**
     * Private constant specifying resource role manager desc 
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_ROLE_MANAGER_DESC = "Manger";

	/**
     * Private constant specifying resource ext ref id 
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_EXTERNAL_REFERENCE_ID = "External Reference ID";

	/**
     * Private constant specifying resource ext ref id 
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_EXTERNAL_REFERENCE_ID_APPLICATIONS = "22770213";

	/**
     * Private constant specifying resource handle
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_HANDLE = "Handle";

	/**
     * Private constant specifying resource handle
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_HANDLE_APPLICATIONS = "Applications";

	/**
     * Private constant specifying resource pay
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS = "Payment Status";

	/**
     * Private constant specifying resource pay
     * 
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS_NA = "N/A";

	

    /**
     * Host address. Use pilot-payflowpro.paypal.com for testing and payflowpro.paypal.com for production.
     * 
     * @since BUGR-1239
     */
    @Resource(name = "payFlowHostAddress")
    private String payFlowHostAddress;

    /**
     * PayFlow username.
     * 
     * @since BUGR-1239
     */
    @Resource(name = "payFlowUser")
    private String payFlowUser;

    /**
     * PayFlow partner name.
     * 
     * @since BUGR-1239
     */
    @Resource(name = "payFlowPartner")
    private String payFlowPartner;

    /**
     * PayFlow vendor name.
     * 
     * @since BUGR-1239
     */
    @Resource(name = "payFlowVendor")
    private String payFlowVendor;

    /**
     * PayFlow password.
     * 
     * @since BUGR-1239
     */
    @Resource(name = "payFlowPassword")
    private String payFlowPassword;

    /**
     * <p>
     * Constructs new <code>ContestServiceFacadeBean</code> instance. This implementation instantiates new instance of
     * payment processor. Current implementation just support processing through PayPalCreditCard. When multiple
     * processors are desired the implementation should use factory design pattern to get the right instance of the
     * payment processor.
     * </p>
     * 
     * @throws PaymentException
     *             exception when instantiating PaymentProcessor. PaymentProcessor usually do merchant authentication
     *             etc at initialization time, if this fails it is thrown as exception.
     */
    public ContestServiceFacadeBean() throws PaymentException {

    }

    /**
     * <p>
     * This initializes the API Profile to the <code>CallerServices</code>. The API profile are the merchant's (in this
     * case TopCoder) PayPal API details.
     * </p>
     * 
     * <p>
     * TopCoder Service Layer Integration 3 Assembly change: new instance of the DefaultUploadServices for exposing its methods.
     * </p>
     * 
     * @throws IllegalStateException
     *             it throws this exception on any issues during caller services initialization. Issues can be: wrong
     *             authentication information, invalid information etc.
     */
    @PostConstruct
    public void init() {
        Logger.getLogger(this.getClass()).debug("Initializing PayflowProPaymentProcessor");
        // BUGR-1239
        paymentProcessor = new PayflowProPaymentProcessor(payFlowHostAddress, payFlowUser, payFlowPartner,
                payFlowVendor, payFlowPassword);

        // TopCoder Service Layer Integration 3 Assembly
        try {
        	uploadExternalServices = new DefaultUploadExternalServices();
		} catch (ConfigurationException e) {
			throw new IllegalStateException("Failed to create the DefaultUploadExternalServices instance.", e);
		}

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

		if(contestData.getLaunchDateAndTime() == null) { // BUGR-1445
            /*
               - start: current time + 1 hour (round the minutes up to the nearest 15) 
               - end: start time + 3 days 
             */
		    GregorianCalendar startDate = new GregorianCalendar();
		    startDate.setTime(new Date());
		    startDate.add(Calendar.HOUR, 1);
		    int m = startDate.get(Calendar.MINUTE);
		    startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
		    contestData.setLaunchDateAndTime(getXMLGregorianCalendar(startDate.getTime()));
		    contestData.setDurationInHours(24 * 3); // 3 days
		}
		
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
        // BUGR-1363
        StudioCompetition competition = (StudioCompetition) convertToCompetition(CompetionType.STUDIO, studioContest);
        competition.getContestData().setPayments(getContestPayments(contestId));
        Logger.getLogger(this.getClass()).info("for contest " + contestId + ", payments #: " + competition.getContestData().getPayments().size());
        return competition;
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
    	
    	// BUGR-1363
        double total = 0;
        for (PrizeData prize : studioContest.getPrizes()) {
            total += prize.getAmount();
        }
        studioContest.setContestAdministrationFee(total * 0.2);
        studioContest.setDrPoints(total * 0.1);
        
    	Date startDate = getDate(studioContest.getLaunchDateAndTime());
        Date endDate = new Date((long) (startDate.getTime() + 60l * 60 * 1000 * studioContest.getDurationInHours()));
        Date winnerAnnouncementDeadlineDate;

		if(studioContest.getDurationInHours() <= 24) {
	        winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() + 60l * 60 * 1000 * 24));

		} else {
	        winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() + 60l * 60 * 1000 * studioContest.getDurationInHours()));
		}

		studioContest.setWinnerAnnoucementDeadline(getXMLGregorianCalendar(winnerAnnouncementDeadlineDate));

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
        return this.studioService.retrieveSubmissionData(submissionId);
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
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission referenced by specified ID has been paid
     * </p>
     * 
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission which has been paid for.
     * @param submissionPaymentData
     *            a <code>SubmissionPaymentData</code> providing the data of successfully purchased submission.
     * @param securityToken
     *            a <code>String</code> providing the security token to be used for tracking the payment and prevent
     *            fraud.
     * @throws PersistenceException
     *             if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException
     *             if specified <code>submissionId</code> is negative.
     */
    public void purchaseSubmission(long submissionId, SubmissionPaymentData submissionPaymentData, String securityToken)
            throws PersistenceException {
        this.studioService.purchaseSubmission(submissionId, submissionPaymentData, securityToken);
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
        return this.studioService.createContestPayment(contestPayment, securityToken);
    }

    /**
     * <p>
     * Gets the contest payment referenced by specified contest ID.
     * </p>
     * 
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get payment details for.
     * @return a <code>ContestPaymentData</code> representing the contest payment matching the specified ID; or
     *         <code>null</code> if there is no such contest.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<ContestPaymentData> getContestPayments(long contestId) throws PersistenceException {
        return this.studioService.getContestPayments(contestId);
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
     * <li>Checks contest id to decide whether to create new contest or update existing contest</li>
     * <li>It processes the payment through <code>PaymentProcessor</code></li>
     * <li>On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * @param <code>ContestData</code> data that recognizes a contest.
     * @param <code>PaymentData</code> payment information (credit card/po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws ContestNotFoundException
     *             if contest is not found while update.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if it is not supported by implementor.
     *  @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    public ContestPaymentResult processContestCreditCardPayment(StudioCompetition competition,
            CreditCardPaymentData paymentData) throws PersistenceException, PaymentException, ContestNotFoundException {

        Logger.getLogger(this.getClass()).info("StudioCompetition: " + competition);
        Logger.getLogger(this.getClass()).info("PaymentData: " + paymentData);

        return processContestPaymentInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update existing contest</li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * @param <code>ContestData</code> data that recognizes a contest.
     * @param <code>PaymentData</code> payment information (credit card/po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws ContestNotFoundException
     *             if contest is not found while update.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if it is not supported by implementor.
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(StudioCompetition competition,
            TCPurhcaseOrderPaymentData paymentData) throws PersistenceException, PaymentException,
            ContestNotFoundException {

        Logger.getLogger(this.getClass()).info("StudioCompetition: " + competition);
        Logger.getLogger(this.getClass()).info("PaymentData: " + paymentData);

        return processContestPaymentInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update existing contest</li>
     * <li>If payment type is credit card then it processes the payment through <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * @param <code>ContestData</code> data that recognizes a contest.
     * @param <code>PaymentData</code> payment information (credit card/po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws ContestNotFoundException
     *             if contest is not found while update.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if it is not supported by implementor.
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    private ContestPaymentResult processContestPaymentInternal(StudioCompetition competition, PaymentData paymentData)
            throws PersistenceException, PaymentException, ContestNotFoundException {

        Logger.getLogger(this.getClass()).info("StudioCompetition: " + competition);
        Logger.getLogger(this.getClass()).info("PaymentData: " + paymentData);

		try
		{
			 long contestId = competition.getContestData().getContestId();

			StudioCompetition tobeUpdatedCompetition = null;

			if (contestId >= 0) {
				try {
					tobeUpdatedCompetition = getContest(contestId);
				} catch (ContestNotFoundException cnfe) {
					// if not contest is found then simply ignore it.
				}
			}

			if (tobeUpdatedCompetition == null) {
				tobeUpdatedCompetition = createContest(competition, competition.getContestData().getTcDirectProjectId());
			}
			else
			{
				tobeUpdatedCompetition = competition;
			}

			// BUGR-1363
			List<ContestPaymentData> payments =  tobeUpdatedCompetition.getContestData().getPayments();
			double paymentAmount;
            // how much user already paid
			double paidFee = 0.0;
			for(ContestPaymentData cpd : payments) {
                paidFee += cpd.getPrice();
            }
			// calculate current contest fee
            double currentFee = 0.0;
            for (PrizeData prize : competition.getContestData().getPrizes()) {
                currentFee += prize.getAmount();
            }
            currentFee *= 0.2;
            // calculate the difference which user has to pay
            paymentAmount = currentFee - paidFee;
            Logger.getLogger(this.getClass()).info("extra payment is: " + paymentAmount);
            if (Double.compare(paymentAmount, 0.0) <= 0) {
                throw new PersistenceException("cannot decrease prize amount at this time", "");
            }

            
			PaymentResult result = null;

        if (paymentData instanceof TCPurhcaseOrderPaymentData) {
            // processing purchase order is not in scope of this assembly.
            result = new PaymentResult();
            result.setReferenceNumber(((TCPurhcaseOrderPaymentData)paymentData).getPoNumber());
        } else if (paymentData instanceof CreditCardPaymentData) {
            // ideally client should be sending the amount,
            // but as client has some inconsistency
            // so in this case we would use the amount from contest data.
            CreditCardPaymentData creditCardPaymentData = (CreditCardPaymentData) paymentData;
            

            creditCardPaymentData.setAmount(Double.toString(paymentAmount)); // BUGR-1363
            if (Double.compare(paidFee, 0.0) > 0) { // BUGR-1363
                creditCardPaymentData.setComment1("Contest Fee (Prize Adjustment)");
            } else {
                // BUGR-1239
                creditCardPaymentData.setComment1("Contest Fee");
            }
            creditCardPaymentData.setComment2(String.valueOf(tobeUpdatedCompetition.getContestData().getContestId()));
            result = paymentProcessor.process(paymentData);
		}

			tobeUpdatedCompetition.getContestData().setStatusId(CONTEST_STATUS_ACTIVE_PUBLIC);
			tobeUpdatedCompetition.getContestData().setDetailedStatusId(CONTEST_DETAILED_STATUS_SCHEDULED);

			ContestPaymentData contestPaymentData = new ContestPaymentData();
			contestPaymentData.setPaypalOrderId(result.getReferenceNumber());
			contestPaymentData.setPaymentReferenceId(result.getReferenceNumber());
			if (paymentData instanceof TCPurhcaseOrderPaymentData)
			{
				contestPaymentData.setPaymentTypeId(PAYMENT_TYPE_TC_PURCHASE_ORDER);
			}
			// TODO, how relate to payflow
			else if (paymentData instanceof CreditCardPaymentData)
			{
				contestPaymentData.setPaymentTypeId(PAYMENT_TYPE_PAYPAL_PAYFLOW);
			}
			contestPaymentData.setContestId(tobeUpdatedCompetition.getContestData().getContestId());
			contestPaymentData.setPaymentStatusId(CONTEST_PAYMENT_STATUS_PAID);
			contestPaymentData.setPrice(paymentAmount);

			UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
			String userId = Long.toString(p.getUserId());

			createContestPayment(contestPaymentData, userId);

			// DONOT create for now
			// create forum for the contest. 
			//long forumid = this.studioService.createForum(tobeUpdatedCompetition.getContestData().getName(), p.getUserId());
			//tobeUpdatedCompetition.getContestData().setForumId(forumid);

			// update contest.
			updateContest(tobeUpdatedCompetition);

			// BUGR-1494
			ContestPaymentResult contestPaymentResult = new ContestPaymentResult();
			contestPaymentResult.setPaymentResult(result);
			contestPaymentResult.setContestData(getContest(tobeUpdatedCompetition.getContestData().getContestId()).getContestData());
			return contestPaymentResult;
		}
		catch (PersistenceException e)
		{
			sessionContext.setRollbackOnly();
			throw e;
		}
		catch (PaymentException e)
		{
			sessionContext.setRollbackOnly();
			throw e;
		}
		catch (ContestNotFoundException e)
		{
			sessionContext.setRollbackOnly();
			throw e;
		}
		catch (Exception e)
		{
			sessionContext.setRollbackOnly();
			throw new PaymentException(e.getMessage(), e);
		}
       
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public PaymentResult processContestCreditCardSale(SoftwareCompetition competition, CreditCardPaymentData paymentData)
        throws ContestServiceException {
        return processContestSaleInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public PaymentResult processContestPurchaseOrderSale(SoftwareCompetition competition,
        TCPurhcaseOrderPaymentData paymentData) throws ContestServiceException {
        return processContestSaleInternal(competition, paymentData);
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private PaymentResult processContestSaleInternal(SoftwareCompetition competition, PaymentData paymentData)
        throws ContestServiceException {
        Logger.getLogger(this.getClass()).info("SoftwareCompetition: " + competition);
        Logger.getLogger(this.getClass()).info("PaymentData: " + paymentData);

        try {
        	long contestId = competition.getProjectHeader().getId();

            SoftwareCompetition tobeUpdatedCompetition = null;

            if (contestId >= 0) {
                tobeUpdatedCompetition = this.getFullProjectData(contestId);
            }

            if (tobeUpdatedCompetition == null) {
                tobeUpdatedCompetition = this.createSoftwareContest(competition, competition.getProjectHeader().getTcDirectProjectId());
            } else {
            	tobeUpdatedCompetition = competition;
            }

            Project contest = tobeUpdatedCompetition.getProjectHeader();

            PaymentResult result = null;
            String payments = (String) contest.getProperty(PAYMENTS_PROJECT_INFO_TYPE);
            double fee = payments == null ? 0 : Double.parseDouble(payments) * 0.2;

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(((TCPurhcaseOrderPaymentData) paymentData).getPoNumber());
            } else if (paymentData instanceof CreditCardPaymentData) {
                // ideally client should be sending the amount,
                // but as client has some inconsistency
                // so in this case we would use the amount from contest data.
                CreditCardPaymentData creditCardPaymentData = (CreditCardPaymentData) paymentData;

                creditCardPaymentData.setAmount(String.valueOf(fee));
                creditCardPaymentData.setComment1("Contest Fee");
                creditCardPaymentData.setComment2(String.valueOf(contest.getId()));
                result = paymentProcessor.process(paymentData);
            }

            // TODO, to be fixed later
			// tobeUpdatedCompetition.getContestData().setStatusId(CONTEST_STATUS_ACTIVE_PUBLIC);
			// tobeUpdatedCompetition.getContestData().setDetailedStatusId(CONTEST_DETAILED_STATUS_SCHEDULED);
			
            ContestSaleData contestSaleData = new ContestSaleData();
            contestSaleData.setPaypalOrderId(result.getReferenceNumber());
            contestSaleData.setSaleReferenceId(result.getReferenceNumber());

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
            	contestSaleData.setSaleTypeId(SALE_TYPE_TC_PURCHASE_ORDER);
            }
            // TODO, how relate to payflow
            else if (paymentData instanceof CreditCardPaymentData) {
                contestSaleData.setSaleTypeId(SALE_TYPE_PAYPAL_PAYFLOW);
            }

            contestSaleData.setContestId(contest.getId());
            contestSaleData.setSaleStatusId(CONTEST_SALE_STATUS_PAID);
            contestSaleData.setPrice(fee);

            this.projectServices.createContestSale(contestSaleData);

            // DONOT create for now
            // create forum for the contest. 
            //long forumid = this.studioService.createForum(tobeUpdatedCompetition.getContestData().getName(), p.getUserId());
            //tobeUpdatedCompetition.getContestData().setForumId(forumid);

			// update contest
            tobeUpdatedCompetition.setProjectHeaderReason("Updated for Contest Sale");
            this.updateSoftwareContest(tobeUpdatedCompetition, contest.getTcDirectProjectId());

            return result;
        } catch (ContestServiceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            throw new ContestServiceException(e.getMessage(), e);
        }
    }
    
    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionId to see if is available, if not then it throws PaymentException.</li>
     * <li>It processes the payment through <code>PaymentProcessor</code></li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>CreditCardPaymentData</code> payment information (credit card) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionCreditCardPayment(CompletedContestData completedContestData,
            CreditCardPaymentData paymentData) throws PaymentException, PersistenceException {

        return processSubmissionPaymentInternal(completedContestData, paymentData);
    }
    
    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionId to see if is available, if not then it throws PaymentException.</li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>TCPurhcaseOrderPaymentData</code> payment information (po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionPurchaseOrderPayment(CompletedContestData completedContestData,
            TCPurhcaseOrderPaymentData paymentData) throws PaymentException, PersistenceException {

        return processSubmissionPaymentInternal(completedContestData, paymentData);
    }
    
    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionId to see if is available, if not then it throws PaymentException.</li>
     * <li>If payment type is credit card then it processes the payment through <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>PaymentData</code> payment information (credit card/po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    private PaymentResult processSubmissionPaymentInternal(CompletedContestData completedContestData,
            PaymentData paymentData) throws PaymentException, PersistenceException {

        try {
            Logger.getLogger(this.getClass()).info("CompletedContestData: " + completedContestData);
            Logger.getLogger(this.getClass()).info("PaymentData: " + paymentData + "," + paymentData.getType());
            
            List<SubmissionData> submissionDatas = this.retrieveSubmissionsForContest(completedContestData.getContestId());
            int totalSubmissions = submissionDatas.size();

            for (int i = 0; i < completedContestData.getSubmissions().length; i++) {
                SubmissionPaymentData submissionPaymentData = completedContestData.getSubmissions()[i];
                long submissionId = submissionPaymentData.getId();
                
                int j = 0;
                for (SubmissionData s : submissionDatas) {
                    if (submissionId == s.getSubmissionId()) {
                        break;
                    }
                    
                    j++;
                }
                
                if (j >= totalSubmissions) {
                    throw new PaymentException("Error in processing payment for submission: " + submissionId
                            + ". Submission is not found");
                }
            }
            
            Logger.getLogger(this.getClass()).info("-------contest id ---" + completedContestData.getContestId());

            PaymentResult result = null;
            if (paymentData.getType().equals(PaymentType.TCPurchaseOrder)) {
                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(((TCPurhcaseOrderPaymentData) paymentData).getPoNumber());
            } else if (paymentData.getType().equals(PaymentType.PayPalCreditCard)) {
                // BUGR-1239
                CreditCardPaymentData creditCardPaymentData = (CreditCardPaymentData) paymentData;
                creditCardPaymentData.setComment1("Submission Fee");

                long[] submissionIds = new long[completedContestData.getSubmissions().length];
                for (int i = 0; i < completedContestData.getSubmissions().length; i++) {
                    SubmissionPaymentData submissionPaymentData = completedContestData.getSubmissions()[i];
                    long submissionId = submissionPaymentData.getId();
                    submissionIds[i] = submissionId;
                }

                creditCardPaymentData.setComment2(Arrays.toString(submissionIds));
                result = paymentProcessor.process(paymentData);
            }


            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            String userId = Long.toString(p.getUserId());

            // purchase or rank submission.
            for (int i = 0; i < completedContestData.getSubmissions().length; i++) {
                SubmissionPaymentData submissionPaymentData = completedContestData.getSubmissions()[i];
                long submissionId = submissionPaymentData.getId();
                
                if (submissionPaymentData.isPurchased()) {
                    this.markForPurchase(submissionId);
                    submissionPaymentData.setPaymentReferenceNumber(result.getReferenceNumber());

                    if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                        submissionPaymentData.setPaymentTypeId(PAYMENT_TYPE_TC_PURCHASE_ORDER);
                    }
                    // TODO, how relate to payflow
                    else if (paymentData instanceof CreditCardPaymentData) {
                        submissionPaymentData.setPaymentTypeId(PAYMENT_TYPE_PAYPAL_PAYFLOW);
                    }

					submissionPaymentData.setPaymentStatusId(CONTEST_PAYMENT_STATUS_PAID);
                }
                
                this.studioService.rankAndPurchaseSubmission(submissionId, 
                        submissionPaymentData.isRanked() ? submissionPaymentData.getRank() : 0, 
                                submissionPaymentData.isPurchased() ? submissionPaymentData : null, userId);
            }

			// update contest status to complete.
            this.updateContestStatus(completedContestData.getContestId(), CONTEST_COMPLETED_STATUS);

			return result;
		}
		catch (PersistenceException e)
		{
			sessionContext.setRollbackOnly();
			throw e;
		}
		catch (PaymentException e)
		{
			sessionContext.setRollbackOnly();
			throw e;
		}
		catch (Exception e)
		{
			sessionContext.setRollbackOnly();
			throw new PaymentException(e.getMessage(), e);
		}

    }
    
    /**
     * <p>
     * Ranks the submissions, given submission identifiers in the rank order.
     * </p>
     * 
     * @param submissionIdsInRankOrder
     *            an array of long submission identifier in the rank order.
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     */
    public boolean rankSubmissions(long[] submissionIdsInRankOrder) throws PersistenceException {
        try {
            for (int i = 0; i < submissionIdsInRankOrder.length; i++) {
                this.studioService.setSubmissionPlacement(submissionIdsInRankOrder[i], i + 1);
            }

            return true;
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Updates the submission feedback.
     * </p>
     * 
     * @param feedbacks
     *            an array of <code>SubmissionFeedback</code>
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     */
    public boolean updateSubmissionsFeedback(SubmissionFeedback[] feedbacks) throws PersistenceException {
        try {
            for (SubmissionFeedback f : feedbacks) {
                this.studioService.updateSubmissionFeedback(f);
            }

            return true;
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>Returns a list containing all active <code>Categories</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Category> getActiveCategories() throws ContestServiceException {
        try {
            return catalogService.getActiveCategories();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>Returns a list containing all active <code>Technologies</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Technology> getActiveTechnologies() throws ContestServiceException {
        try {
            return catalogService.getActiveTechnologies();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>Returns a list containing all <code>Phases</code>.</p>
     *
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Phase> getPhases() throws ContestServiceException {
        try {
            return catalogService.getPhases();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * Assigns a specified user to a specified <code>assetDTO</code>.
     * </p>
     * 
     * <p>
     * If the user already assigned to the asset, this method simply does nothing.
     * </p>
     *
     * @param userId the id of the user
     * @param assetId the id of the assetDTO
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void assignUserToAsset(long userId, long assetId) throws ContestServiceException {
        try {
            catalogService.assignUserToAsset(userId, assetId);
        } catch (EntityNotFoundException e) {
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * Removes a specified user from a specified <code>assetDTO</code>.
     * </p>
     *
     * @param userId the id of the user
     * @param assetId the id of the asset
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void removeUserFromAsset(long userId, long assetId) throws ContestServiceException {
        try {
            catalogService.removeUserFromAsset(userId, assetId);
        } catch (EntityNotFoundException e) {
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjects() throws ContestServiceException {
        try {
            Project[] projects = projectServices.findAllTcDirectProjects();

            SoftwareCompetition[] ret = new SoftwareCompetition[projects.length];
            for (int i = 0; i < projects.length; i++) {
                FullProjectData projectData = new FullProjectData(); 
                projectData.setProjectHeader(projects[i]);

            	ret[i] = new SoftwareCompetition();
                ret[i].setProjectData(projectData);
                ret[i].setType(CompetionType.SOFTWARE);
                ret[i].setId(projectData.getProjectHeader().getId());
            }
            return ret;
        } catch (ProjectServicesException e) {
            throw new ContestServiceException("Operation failed in the projectServices.", e);
        }
    }

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array if no projects found.
     * </p>
     *
     * @param operator The user to search for projects
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(String operator) throws ContestServiceException {
        try {
            Project[] projects = projectServices.findAllTcDirectProjectsForUser(operator);

            SoftwareCompetition[] ret = new SoftwareCompetition[projects.length];
            for (int i = 0; i < projects.length; i++) {
                FullProjectData projectData = new FullProjectData(); 
                projectData.setProjectHeader(projects[i]);

            	ret[i] = new SoftwareCompetition();
                ret[i].setProjectData(projectData);
                ret[i].setType(CompetionType.SOFTWARE);
                ret[i].setId(projectData.getProjectHeader().getId());
            }
            return ret;
        } catch (ProjectServicesException e) {
            throw new ContestServiceException("Operation failed in the projectServices.", e);
        }
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null if not found.
     * </p>
     *
     * @param projectId The ID of the project to retrieve
     *
     * @return the project along with all known associated information
     *
     * @throws IllegalArgumentException If projectId is negative
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition getFullProjectData(long projectId) throws ContestServiceException {
        try {
            FullProjectData projectData = projectServices.getFullProjectData(projectId);

            if (projectData == null) {
                return null;
            }

			com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
            }

            SoftwareCompetition contest = new SoftwareCompetition();
            contest.setProjectData(projectData);
            contest.setType(CompetionType.SOFTWARE);
            contest.setId(projectData.getProjectHeader().getId());

            return contest;
        } catch (ProjectServicesException e) {
            throw new ContestServiceException("Operation failed in the projectServices.", e);
        }
    }

    /**
     * <p>
     * Creates a new <code>SoftwareCompetition</code> in the persistence.
     * </p>
     *
     * @param contest the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId the TC direct project id.
     *
     * @return the created <code>SoftwareCompetition</code> as a contest
     *
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @SuppressWarnings({ "unchecked" })
    public SoftwareCompetition createSoftwareContest(SoftwareCompetition contest, long tcDirectProjectId)
            throws ContestServiceException

    {
        try {
            AssetDTO assetDTO = contest.getAssetDTO();
			long forumId = 0;

			UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();


            if (assetDTO != null) {

				if(assetDTO.getProductionDate() == null) { // BUGR-1445
				/*
				   - start: current time + 24 hour (round the minutes up to the nearest 15) 
				 */
					GregorianCalendar startDate = new GregorianCalendar();
					startDate.setTime(new Date());
					startDate.add(Calendar.HOUR, 24);
					int m = startDate.get(Calendar.MINUTE);
					startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
					assetDTO.setProductionDate(getXMLGregorianCalendar(startDate.getTime()));
				}
System.out.println("----------comments------"+assetDTO.getComments());

				if (contest.getProjectHeader() != null) 
				{
					// comp development, set phase to dev
					if (contest.getProjectHeader().getProjectCategory().getId() == 2)
					{
						assetDTO.setPhase("Development");
					} 
					// else set to design
					else 
					{
						assetDTO.setPhase("Design");
					}


				}
                assetDTO = this.catalogService.createAsset(assetDTO);
                contest.setAssetDTO(assetDTO);
				
				

				// create forum
				
				if (createForum)
				{
					forumId = createForum(assetDTO, p.getUserId());
				}

				// if forum created
				if (forumId > 0)
				{
					
					// create a comp forum 
					CompForum compForum = new CompForum();
					compForum.setJiveCategoryId(forumId);
					assetDTO.setForum(compForum);
					assetDTO = this.catalogService.updateAsset(assetDTO);
				}
				
            }

            if (contest.getProjectHeader() != null) {
                

                //
                // since: Flex Cockpit Launch Contest - Integrate Software Contests v1.0
                // set the project properties.
                //
                if (assetDTO != null) {
                    /*contest.getProjectHeader().setProperty(PROJECT_TYPE_INFO_VERSION_ID_KEY,
                            assetDTO.getVersionId().toString());*/
                    contest.getProjectHeader().setProperty(PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY,
                            assetDTO.getVersionId().toString());
                    contest.getProjectHeader().setProperty(PROJECT_TYPE_INFO_COMPONENT_ID_KEY,
                            assetDTO.getId().toString());
					contest.getProjectHeader().setProperty(PROJECT_TYPE_INFO_SVN_MODULE_KEY, "");
					contest.getProjectHeader().setProperty(PROJECT_TYPE_INFO_NOTES_KEY, "");
					if (forumId > 0)
					{
						contest.getProjectHeader().setProperty(PROJECT_TYPE_INFO_DEVELOPER_FORUM_ID_KEY,
                            String.valueOf(forumId));
					}

					contest.getProjectPhases().setStartDate(getDate(assetDTO.getProductionDate()));
					
                }

				com.topcoder.management.resource.Resource[] resources = new com.topcoder.management.resource.Resource[1];
				resources[0] = new com.topcoder.management.resource.Resource();
				resources[0].setId(com.topcoder.management.resource.Resource.UNSET_ID);
				ResourceRole role = new ResourceRole();
				role.setId(RESOURCE_ROLE_MANAGER_ID);
				role.setName(RESOURCE_ROLE_MANAGER_NAME);
				role.setDescription(RESOURCE_ROLE_MANAGER_DESC);
				resources[0].setResourceRole(role);
				resources[0].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(p.getUserId()));
				resources[0].setProperty(RESOURCE_INFO_HANDLE, String.valueOf(p.getName()));
				resources[0].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);

				// comment out for now
				/*resources[1] = new com.topcoder.management.resource.Resource();
				resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
				resources[1].setResourceRole(role);
				resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, RESOURCE_INFO_EXTERNAL_REFERENCE_ID_APPLICATIONS);
				resources[1].setProperty(RESOURCE_INFO_HANDLE, RESOURCE_INFO_HANDLE_APPLICATIONS);
				resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);*/

				contest.setProjectResources(resources);



                contest.getProjectHeader().setTcDirectProjectId(tcDirectProjectId);
                FullProjectData projectData = projectServices.createProjectWithTemplate(contest.getProjectHeader(),
                        contest.getProjectPhases(), contest.getProjectResources(), String.valueOf(p.getUserId()));

                com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();
				// TODO for now have to do these to avoid cycle
                for (int i = 0; i < allPhases.length; i++) {
                    allPhases[i].setProject(null);
                    allPhases[i].clearDependencies();
					allPhases[i].clearAttributes();
                }

                contest.setProjectHeader(projectData.getProjectHeader());
                contest.setProjectPhases(projectData);
                contest.setProjectResources(projectData.getResources());
                contest.setProjectData(projectData);
            }

            return contest;

		}
		catch (com.topcoder.catalog.service.PersistenceException e) 
		{
			sessionContext.setRollbackOnly();
            throw new ContestServiceException("Operation failed in the contest service facade.", e);
         }
		 catch (ProjectServicesException e) 
		 {
				sessionContext.setRollbackOnly();
                throw new ContestServiceException("Operation failed in the contest service facade.", e);
         }
		 catch (Exception e) 
		 {
				sessionContext.setRollbackOnly();
                throw new ContestServiceException("Operation failed in the contest service facade.", e);
         }
    }

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     *
     * @param contest the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId the TC direct project id.
     *
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void updateSoftwareContest(SoftwareCompetition contest, long tcDirectProjectId)
					throws ContestServiceException
	{
		try
		{

				if (contest.getAssetDTO() != null) 
				{
						this.catalogService.updateAsset(contest.getAssetDTO());
				}

				if (contest.getProjectHeader() != null) 
				{
					UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
					
						Set phaseset = contest.getProjectPhases().getPhases();
						com.topcoder.project.phases.Phase[] phases = 
							      (com.topcoder.project.phases.Phase[]) phaseset.toArray(new com.topcoder.project.phases.Phase[phaseset.size()]);
						// add back project on phase
						for (int i = 0; i < phases.length; i++) {
							phases[i].setProject(contest.getProjectPhases());
						}

						contest.getProjectHeader().setTcDirectProjectId(tcDirectProjectId);
						FullProjectData projectData = projectServices.updateProject(contest.getProjectHeader(), contest.getProjectHeaderReason(),
								contest.getProjectPhases(), contest.getProjectResources(), p.getName());
						com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();
						// this is to avoid cycle
						for (int i = 0; i < allPhases.length; i++) {
							allPhases[i].setProject(null);
							allPhases[i].clearDependencies();
						}
				}

		}
		catch (com.topcoder.catalog.service.PersistenceException e) 
		{
			sessionContext.setRollbackOnly();
            throw new ContestServiceException("Operation failed in the contest service facade.", e);
         }
		 catch (ProjectServicesException e) 
		 {
				sessionContext.setRollbackOnly();
                throw new ContestServiceException("Operation failed in the contest service facade.", e);
         }
		 catch (EntityNotFoundException e) 
		 {
				sessionContext.setRollbackOnly();
                throw new ContestServiceException("Operation failed in the contest service facade.", e);
         }
		 catch (Exception e) 
		 {
				sessionContext.setRollbackOnly();
                throw new ContestServiceException("Operation failed in the contest service facade.", e);
         }
    }

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * 
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param submission the submission file data
     *
     * @return the id of the new submission
     *
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadSubmission(long projectId, String filename, DataHandler submission)
        throws ContestServiceException {
        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            return uploadExternalServices.uploadSubmission(projectId, p.getUserId(), filename, submission);
        } catch (UploadServicesException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the previous
     * ones.
     * </p>
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param finalFix the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadFinalFix(long projectId, String filename, DataHandler finalFix)
        throws ContestServiceException {
        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            return uploadExternalServices.uploadFinalFix(projectId, p.getUserId(), filename, finalFix);
        } catch (UploadServicesException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the previous
     * ones.
     * </p>
     *
     * @param projectId the project's id
     * @param filename the file name to use
     * @param testCases the test cases data
     *
     * @return the id of the created test cases submission
     *
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadTestCases(long projectId, String filename, DataHandler testCases)
        throws ContestServiceException {
        try {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            return uploadExternalServices.uploadTestCases(projectId, p.getUserId(), filename, testCases);
        } catch (UploadServicesException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId the submission's id
     * @param submissionStatusId the submission status id
     * @param operator the operator which execute the operation
     *
     * @throws IllegalArgumentException if any id is &lt; 0 or if operator is null or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
        throws ContestServiceException {
        try {
            uploadExternalServices.setSubmissionStatus(submissionId, submissionStatusId, operator);
        } catch (UploadServicesException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * Adds the given user as a new submitter to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId the user to be added
     *
     * @return the added resource id
     *
     * @throws IllegalArgumentException if any id is &lt; 0
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long addSubmitter(long projectId, long userId) throws ContestServiceException {
        try {
            return uploadExternalServices.addSubmitter(projectId, userId);
        } catch (UploadServicesException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }


	public long createForum(AssetDTO asset, long userId) {
		long forumId = -1;
		try {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			p.put(Context.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
			p.put(Context.PROVIDER_URL, forumBeanProviderUrl);

			Context c = new InitialContext(p);
			ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

			Forums forums = forumsHome.create();

			long phaseId = 0;
			try
			{
				phaseId = Long.parseLong(asset.getPhase());
			}
			catch (Exception ee)
			{
			}
			
			forumId = forums.createSoftwareComponentForums(asset.getName(), asset.getId(), asset.getVersionId(), 
				                                                phaseId, Status.REQUESTED.getStatusId(), asset.getRootCategory().getId(), asset.getShortDescription(),
				                                                asset.getVersionText(), true);
			if (forumId < 0) throw new Exception("createStudioForum returned -1");

			Logger.getLogger(this.getClass()).error("Created forum " + forumId + " for " + asset.getName());

			forums.assignRole(userId, "Software_Moderators_" + forumId); // BUGR-1677
			forums.createForumWatch(userId, forumId);

			return forumId;
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("*** Could not create a forum for " + asset.getName());
			Logger.getLogger(this.getClass()).error(e);
			return forumId;
		}
	}


}
