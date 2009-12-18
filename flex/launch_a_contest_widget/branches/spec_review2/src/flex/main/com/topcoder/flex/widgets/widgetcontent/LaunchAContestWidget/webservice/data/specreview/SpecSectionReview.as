/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview {
    
    /**
     * Defines DTO class for Spec Section Review
     *
     * @author TCSASSEMBLER
     * @version 1.0
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 2
     */
    public class SpecSectionReview {
        /**
         * A default empty constructor
         */
        public function SpecSectionReview() {
        }
        
        /**
         * Spec section review identifier
         */
        public var specSectionReviewId:Number;
        
        /**
         * Spec review identifier
         */
        public var specReviewId:Number;
        
        /**
         * Review status for this spec review.
         */
        public var reviewStatus:SpecReviewStatus;
        
        /**
         * The section type for this spec review.
         */
        public var sectionType:SpecReviewSectionType;
        
        /**
         * Array of objects of type SpecReviewComment
         */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview.SpecReviewComment")]
        public var comments:Array;
    }
}