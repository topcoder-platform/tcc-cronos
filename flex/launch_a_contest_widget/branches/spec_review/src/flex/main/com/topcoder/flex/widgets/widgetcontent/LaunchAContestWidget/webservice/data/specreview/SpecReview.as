/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview {
    
    /**
     * Defines DTO class for Spec Reviews
     *
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 2
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
         * Review status for this spec review.
         */
        public var reviewStatus:SpecReviewStatus;
        
        /**
         * The section type for this spec review.
         */
        public var sectionType:SpecReviewSectionType;
        
        /**
         * Indicates whether the spec review is for studio contest or not.
         */
        public var studio:Boolean;
        
        /**
         * Array of objects of type SpecReviewComment
         */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview.SpecReviewComment")]
        public var comments:Array;
    
    }
}