/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview {
    
    /**
     * Defines DTO class for Spec Review Section Type
     *
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 2
     */
    public class SpecReviewSectionType {
        /**
         * A default empty constructor
         */
        public function SpecReviewSectionType() {
        }
        
        /**
         * The identifier for section type.
         */
        public var sectionTypeId:Number;
        
        /**
         * The name of the section.
         */
        public var name:String;
        
        /**
         * Indicates whether this section type is for studio contest or not.
         */
        public var studio:Boolean;
    
    }
}