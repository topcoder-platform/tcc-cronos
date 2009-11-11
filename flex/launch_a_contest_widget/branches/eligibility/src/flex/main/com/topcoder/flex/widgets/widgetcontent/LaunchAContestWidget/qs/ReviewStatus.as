/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.qs {
    
    /**
     * Defines enumeration constants for review status.
     *
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 1
     */
    [Bindable]
    public class ReviewStatus {
        /**
         * A default empty constructor
         */
        public function ReviewStatus() {
        }
        
        /**
         * Failed review status.
         */
        public static const FAILED:String="FAILED";
        
        /**
         * Passed review status.
         */
        public static const PASSED:String="PASSED";
        
        /**
         * Pending review status.
         */
        public static const PENDING:String="PENDING";
    
    }
}