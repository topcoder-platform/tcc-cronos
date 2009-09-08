/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview {
    
    /**
     * Defines DTO class for Spec Reviews
     * 
     * Version 1.0.1 (Spec Reviews Finishing Touches) Change Notes:
     *    - SpecReview contains a array of SpecSectionReview
     *    - It also contains the reviewerId, if assigned. 
     *
     * @author TCSASSEMBLER
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 2
     * @version 1.0.1
     */
    public class SpecReview {
        /**
         * A default empty constructor
         */
        public function SpecReview() {
        }
        
        /**
         * Spec review identifier
         */
        public var specReviewId:Number;
        
        /**
         * Contest Id for this spec review.
         */
        public var contestId:Number;
        
        /**
         * Indicates whether the spec review is for studio contest or not.
         */
        public var studio:Boolean;
        
        /**
         * Review status for this spec review.
         */
        public var reviewStatus:SpecReviewStatus;
        
        /**
         * Array of objects of type SpecReviewComment
         */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview.SpecSectionReview")]
        public var sectionReviews:Array;
        
        /**
         * Represents the identifier for reviewer, if any
         */
        public var reviewerId:Number;
    }
}
