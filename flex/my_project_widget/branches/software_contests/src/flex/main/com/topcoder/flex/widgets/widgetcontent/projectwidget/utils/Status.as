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
        public static const STATUS_SORT_ORDER:Array=[DANGER, ACTION_REQUIRED, ACTIVE, COMPLETED, NO_WINNER_CHOSEN, ABANDONED, DRAFT, SCHEDULED];
        
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
    }
}
