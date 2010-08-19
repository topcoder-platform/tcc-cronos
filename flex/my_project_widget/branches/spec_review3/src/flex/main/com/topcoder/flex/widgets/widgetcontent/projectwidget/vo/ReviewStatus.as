/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget.vo {
   
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
        public static const FAILED:String="Failed";
        
        /**
         * Passed review status.
         */
        public static const PASSED:String="Passed";
        
        /**
         * Pending review status.
         */
        public static const PENDING:String="Pending";
    
    }
}