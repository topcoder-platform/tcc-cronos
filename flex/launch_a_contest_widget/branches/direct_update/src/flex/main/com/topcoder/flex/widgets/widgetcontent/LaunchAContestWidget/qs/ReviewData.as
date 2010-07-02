/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.qs {
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.com.Review;
    
    import mx.collections.ArrayCollection;
    
    /**
     * Defines review data DTO.
     *
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 1
     */
    [Bindable]
    public class ReviewData {
        /**
         * A default empty constructor
         */
        public function ReviewData() {
        }
        
        /**
         * The section id for the review data.
         */ 
        public var sectionId:String="";
        
        /**
         * The section name for the review data.
         */
        public var sectionName:String="";
        
        /**
         * The review status. This value can be one of Pending/Failed/Passed
         */
        public var reviewStatus:String;
        
        /**
         * A boolean flag that indicates whether a new comment is available for seeing or not.
         */
        public var newConversation:Boolean=false;
        
        /**
         * The list of conversations for this ReviewData
         */
        public var conversations:ArrayCollection=new ArrayCollection();
    }
}