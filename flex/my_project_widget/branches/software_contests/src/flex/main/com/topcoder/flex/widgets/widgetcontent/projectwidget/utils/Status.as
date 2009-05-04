/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget.utils {

    /**
     * <p>
     * This class simply holds constants for contest status.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since My Project Overhaul Assembly.
     */
    public class Status {
        /**
        * Sort order of status groups.
        */
        public static const STATUS_SORT_ORDER:Array=[DANGER, ACTION_REQUIRED, ACTIVE, COMPLETED, NO_WINNER_CHOSEN, ABANDONED, DRAFT, SCHEDULED,
	                                             INACTIVE, DELETED, CANCELLED_FAILED_REVIEW, CANCELLED_FAILED_SCREENING, CANCELLED_ZERO_SUBMISSION,
						     REGISTRATION, SUBMISSION, SCREENING, REVIEW, APPEALS, APPEALS_RESPONSE, AGGREGATION,
						     AGGREGATION_REVIEW, FINAL_FIX, FINAL_REVIEW, APPROVAL];
        
        /**
         * Active contest status.
         */
        public static const ACTIVE:String="Active";
        
        /**
         * Action Required contest status.
         */
        public static const ACTION_REQUIRED:String="Action Required";

        /**
         * Completed contest status.
         */
        public static const COMPLETED:String="Completed";

        /**
         * Draft contest status.
         */
        public static const DRAFT:String="Draft";

        /**
         * Terminated contest status.
         */
        public static const TERMINATED:String="Terminated";
        
        /**
         * No Winner Chosen contest status.
         */
        public static const NO_WINNER_CHOSEN:String="No Winner Chosen";
        
        /**
         * Abandoned contest status.
         */
        public static const ABANDONED:String="Abandoned";
        
        /**
         * Scheduled contest status.
         */
        public static const SCHEDULED:String="Scheduled";

        /**
         * Danger contest status.
         */
        public static const DANGER:String="In Danger";


	/**
         * Inactive contest status.
         */
        public static const INACTIVE:String="Inactive";

	/**
         * Deleted contest status.
         */
        public static const DELETED:String="Deleted";

	/**
         * Cancelled - Failed Review contest status.
         */
        public static const CANCELLED_FAILED_REVIEW:String="Cancelled - Failed Review";

	/**
         * Cancelled - Failed Screening contest status.
         */
        public static const CANCELLED_FAILED_SCREENING:String="Cancelled - Failed Screening";

	/**
         * Cancelled - Zero Submission contest status.
         */
        public static const CANCELLED_ZERO_SUBMISSION:String="Cancelled - Zero Submission";


	/**
         * Registration contest status.
         */
        public static const REGISTRATION:String="Registration";


	/**
         * Submission contest status.
         */
        public static const SUBMISSION:String="Submission";

	/**
         * Screening contest status.
         */
        public static const SCREENING:String="Screening";

	/**
         * Review contest status.
         */
        public static const REVIEW:String="Review";

	/**
         * Appeals contest status.
         */
        public static const APPEALS:String="Appeals";


	/**
         * Appeals Response contest status.
         */
        public static const APPEALS_RESPONSE:String="Appeals Response";


	/**
         * Aggregation contest status.
         */
        public static const AGGREGATION:String="Aggregation";


	/**
         * Aggregation Review contest status.
         */
        public static const AGGREGATION_REVIEW:String="Aggregation Review";


	/**
         * Final Fix contest status.
         */
        public static const FINAL_FIX:String="Final Fix";


	/**
         * Final Review contest status.
         */
        public static const FINAL_REVIEW:String="Final Review";


	/**
         * Approval contest status.
         */
        public static const APPROVAL:String="Approval";
	
    }
}
