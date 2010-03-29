/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview {
    
    /**
     * Defines DTO class for Spec Review Comment
     *
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 2
     */
    public class SpecReviewComment {
        /**
         * A default empty constructor
         */
        public function SpecReviewComment() {
        }
        
        /**
         * Comment Identifier.
         */
        public var commentId:Number;
        
        /**
         * Identifier of the spec review for this comment.
         */
        public var specReviewId:Number;
        
        /**
         * The comment text.
         */
        public var comment:String;
        
        /**
         * The create user for this comment.
         */
        public var createUser:String;
        
        /**
         * The create time for this comment.
         */
        public var createTime:Date;
        
        /**
         * The role type for this comment.
         */
        public var roleType:SpecReviewRoleType;
        
        /**
         * Has this comment been seen.
         */
        public var isViewed:Boolean;
    }
}