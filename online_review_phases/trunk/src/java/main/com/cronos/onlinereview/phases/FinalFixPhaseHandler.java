/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.cronos.onlinereview.phases.logging.LogMessage;
import com.cronos.onlinereview.phases.lookup.UploadStatusLookupUtility;
import com.cronos.onlinereview.phases.lookup.UploadTypeLookupUtility;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.Review;

import com.topcoder.project.phases.Phase;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection and email sending. This class handle the
 * aggregation phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.</p>
 *  <p>The final fix phase can start as soon as the dependencies are met and can stop when the following conditions
 * met: the dependencies are met and The final fix has been uploaded.</p>
 *  <p>The additional logic for executing this phase is: When Final Fix is starting and Final Review worksheet is
 * not created, it should be created; otherwise it should be marked uncommitted. Previous final fix upload will be
 * deleted.</p>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class FinalFixPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.FinalFixPhaseHandler";

    /** constant for final fix phase type. */
    private static final String PHASE_TYPE_FINAL_FIX = "Final Fix";

    /** array of comment types to be copied from aggregation scorecard to new final review worksheet. */
    private static final String[] COMMENT_TYPES_TO_COPY = new String[] {"Comment",
        "Required", "Recommended", "Appeal", "Appeal Response", "Aggregation Comment", "Aggregation Review Comment",
        "Submitter Comment", "Final Fix Comment", "Final Review Comment", "Manager Comment"};

    /**
     * The logger instance.
     */
    private static final Log logger = LogFactory.getLog(FinalFixPhaseHandler.class.getName());
    
    /**
     * Create a new instance of FinalFixPhaseHandler using the default namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public FinalFixPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of FinalFixPhaseHandler using the given namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public FinalFixPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.</p>
     *  <p>If the input phase status is Scheduled, then it will check if the phase can be started using the
     * following conditions: The dependencies are met</p>
     *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the
     * following conditions: the dependencies are met and The final fix has been uploaded.</p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Final Fix" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_FINAL_FIX);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            //return true if all dependencies have stopped and start time has been reached.
            if (!PhasesHelper.canPhaseStart(phase)) {
                return false;
            }

            //Get nearest Final Review phase
            Phase finalReviewPhase = PhasesHelper.locatePhase(phase, "Final Review", true, false);
            if (finalReviewPhase == null) {
            	logger.log(Level.WARN, new LogMessage(new Long(phase.getId()), null,
            			"Can not start final fix because can not get the nearest final review phase."));
                return false;
            }
            Connection conn = null;

            try {
                conn = createConnection();

                Resource[] finalReviewer = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(),
                        conn, new String[] {"Final Reviewer"}, finalReviewPhase.getId());

                if ( finalReviewer.length != 1) {
                	logger.log(Level.WARN, new LogMessage(new Long(phase.getId()), null,
                			"Can not start final fix because can not found the final reviewer."));
                }
                //return true if there is a final reviewer
                return (finalReviewer.length == 1);
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        } else {
            //return true if all dependencies have stopped and final fix exists.
        	boolean met = PhasesHelper.arePhaseDependenciesMet(phase, false);
            if (!met) {
            	logger.log(Level.WARN, "Can not end final fix phase because the phase dependencies have not been met.");
            }
            boolean isFixed = getFinalFix(phase) != null;
            if (!isFixed) {
            	logger.log(Level.WARN, "Can not end final fix phase because no final fix can be found.");
            }
            return isFixed && met;
        }
    }

    /**
     * <p>Provides addtional logic to execute a phase. This method will be called by start() and end() methods
     * of PhaseManager implementations in Phase Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be send on start phase or end phase base
     * on configuration settings.</p>
     *  <p>If the input phase status is Scheduled, then it will perform the following additional logic to start
     * the phase: if Final Review worksheet is not created, it should be created; otherwise it should be marked
     * uncommitted. Previous final fix upload will be deleted.</p>
     *  <p>If the input phase status is Open, then it will do nothing.</p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Final Fix" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_FINAL_FIX);

        logger.log(Level.INFO, new LogMessage(new Long(phase.getId()), operator, 
        		"execute Final fix phase with some phase operation."));
        
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            checkFinalReviewWorksheet(phase, operator);
        }

        sendEmail(phase);
    }

    /**
     * This method is called from perform method when phase is starting. If Final Review worksheet is not
     * created, it is created; otherwise it is marked uncommitted. Previous final fix upload is deleted.
     *
     * @param phase phase instance.
     * @param operator operator name.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving/saving data.
     */
    private void checkFinalReviewWorksheet(Phase phase, String operator)
        throws PhaseHandlingException {
        //Check if the Final Review worksheet is created
        //Get nearest Final Review phase
        Phase finalReviewPhase = PhasesHelper.locatePhase(phase, "Final Review", true, true);
        Phase previousFinalReviewPhase = PhasesHelper.locatePhase(phase, "Final Review", false, false);

        Connection conn = null;

        try {
            conn = createConnection();

            //Search for id of the Final Reviewer
            Resource[] resources = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    new String[] { "Final Reviewer" }, finalReviewPhase.getId());

            if (resources.length == 0) {
            	logger.log(Level.ERROR,
            			new LogMessage(new Long(phase.getId()), operator, "No Final Reviewer found for phase: " + finalReviewPhase.getId()));
                throw new PhaseHandlingException("No Final Reviewer found for phase: " + finalReviewPhase.getId());
            }

            Review finalWorksheet = null;
            if (previousFinalReviewPhase != null) {
                finalWorksheet = PhasesHelper.getFinalReviewWorksheet(conn, getManagerHelper(),
                        previousFinalReviewPhase.getId());
            }

            if (finalWorksheet == null) {
                //create the final review worksheet...

                //create review object
                finalWorksheet = new Review();
                finalWorksheet.setAuthor(resources[0].getId());
                finalWorksheet.setCommitted(false);

                //Locate the nearest backward Aggregation phase
                Phase aggPhase = PhasesHelper.locatePhase(phase, "Aggregation", false, false);
                if (aggPhase != null) {
                    //Create final review from aggregation worksheet

                    //Search the aggregated review scorecard
                    Review aggWorksheet = PhasesHelper.getAggregationWorksheet(conn, getManagerHelper(), aggPhase.getId());

                    if (aggWorksheet == null) {
                    	logger.log(Level.ERROR,
                    			new LogMessage(new Long(phase.getId()), operator, "aggregation worksheet does not exist."));
                        throw new PhaseHandlingException("aggregation worksheet does not exist.");
                    }

                    //copy the comments and review items
                    PhasesHelper.copyComments(aggWorksheet, finalWorksheet, COMMENT_TYPES_TO_COPY, null);
                    PhasesHelper.copyFinalReviewItems(aggWorksheet, finalWorksheet);

                    //set the ID of a scorecard which the review scorecard corresponds to
                    finalWorksheet.setScorecard(aggWorksheet.getScorecard());
                    finalWorksheet.setSubmission(aggWorksheet.getSubmission());
                } else {
                    //Create final review from review scorecards

                    //copy the comments from review scorecards
                    Phase reviewPhase = PhasesHelper.locatePhase(phase, "Review", false, true);

                    //find winning submitter.
                    Resource winningSubmitter = PhasesHelper.getWinningSubmitter(getManagerHelper().getResourceManager(), getManagerHelper().getProjectManager(), 
                            conn, phase.getProject().getId());
                    if (winningSubmitter == null) {
                        throw new PhaseHandlingException("No winner for project with id" + phase.getProject().getId());
                    }

                    // find the winning submission
                    Filter filter = SubmissionFilterBuilder.createResourceIdFilter(winningSubmitter.getId());
                    Submission[] submissions = getManagerHelper().getUploadManager().searchSubmissions(filter);
                    if (submissions == null || submissions.length != 1) {
                    	logger.log(Level.ERROR,
                    			new LogMessage(new Long(phase.getId()), operator, "No winning submission for project with id"
                                        + phase.getProject().getId()));
                        throw new PhaseHandlingException("No winning submission for project with id"
                                + phase.getProject().getId());
                    }
                    Long winningSubmissionId = new Long(submissions[0].getId());

                    //Search all review scorecard from review phase for the winning submitter
                    Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                        reviewPhase.getId(), PhasesHelper.REVIEWER_ROLE_NAMES, winningSubmissionId);

                    for (int r = 0; r < reviews.length; r++) {
                        finalWorksheet.setScorecard(reviews[r].getScorecard());
                        finalWorksheet.setSubmission(reviews[r].getSubmission());
                        PhasesHelper.copyComments(reviews[r], finalWorksheet, COMMENT_TYPES_TO_COPY, null);
                        PhasesHelper.copyReviewItems(reviews[r], finalWorksheet, COMMENT_TYPES_TO_COPY);
                    }
                }
                logger.log(Level.INFO, new LogMessage(new Long(phase.getId()), operator, "create final review worksheet."));
                
                //persist the review
                getManagerHelper().getReviewManager().createReview(finalWorksheet, operator);
            } else {
                //Mark Final Review worksheet uncommitted
                finalWorksheet.setAuthor(resources[0].getId());
                finalWorksheet.setCommitted(false);

                //persist the copy
                finalWorksheet = PhasesHelper.cloneReview(finalWorksheet);
                getManagerHelper().getReviewManager().createReview(finalWorksheet, operator);
            }

            //delete the earlier final fix if it exists.
            Upload finalFix = getFinalFix(phase);
            if (finalFix != null) {
                finalFix.setUploadStatus(PhasesHelper.getUploadStatus(
                        getManagerHelper().getUploadManager(), "Deleted"));
                getManagerHelper().getUploadManager().updateUpload(finalFix, operator);
            }
        } catch (ReviewManagementException e) {
            throw new PhaseHandlingException("Problem when persisting review", e);
        } catch (SQLException e) {
        	logger.log(Level.ERROR,
        			new LogMessage(new Long(phase.getId()), operator, "Fail to get final review worksheet.", e));
            throw new PhaseHandlingException("Problem when looking up id", e);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Problem when persisting upload", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem when retrieving winning submission.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This retrieves the final fix upload. It returns null if none exist.
     *
     * @param phase phase instance.
     *
     * @return the final fix upload, null if none exist.
     *
     * @throws PhaseHandlingException if error occurs when retrieving, or if multiple uploads exist.
     */
    private Upload getFinalFix(Phase phase) throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();

            long uploadTypeId = UploadTypeLookupUtility.lookUpId(conn, "Final Fix");
            long uploadStatusId = UploadStatusLookupUtility.lookUpId(conn, "Active");

            //get final fix upload based on "Final Fix" type, "Active" status and winning submitter resource id filters.
            Filter uploadTypeFilter = UploadFilterBuilder.createUploadTypeIdFilter(uploadTypeId);
            Filter uploadStatusFilter = UploadFilterBuilder.createUploadStatusIdFilter(uploadStatusId);
            Resource winningSubmitter = PhasesHelper.getWinningSubmitter(getManagerHelper().getResourceManager(), getManagerHelper().getProjectManager(), conn,
                    phase.getProject().getId());
            Filter resourceIdFilter = UploadFilterBuilder.createResourceIdFilter(winningSubmitter.getId());
            Filter fullFilter = SearchBundle.buildAndFilter(SearchBundle.buildAndFilter(uploadTypeFilter,
                        uploadStatusFilter), resourceIdFilter);

            Upload[] uploads = getManagerHelper().getUploadManager().searchUploads(fullFilter);

            if (uploads.length == 0) {
                return null;
            } else if (uploads.length == 1) {
                return uploads[0];
            } else {
            	logger.log(Level.ERROR,
            			new LogMessage(new Long(phase.getId()), null, "There cannot be multiple final fix uploads"));
                throw new PhaseHandlingException("There cannot be multiple final fix uploads");
            }
        } catch (SQLException e) {
        	logger.log(Level.ERROR,
        			new LogMessage(new Long(phase.getId()), null, "Fail to get final fix submission.", e));
            throw new PhaseHandlingException("Problem when looking up id", e);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Problem when retrieving upload", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem with search builder", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
