/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project {

    /**
     * Software Project Header DTO class.
     *
     * @author TCSDEVELOPER
     *
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    public class SoftwareProjectSpec extends AuditableObject {

        /**
         * A default empty constructor.
         */
        public function SoftwareProjectSpec() {
        }

        /**
         * Represents the detailed requirements of this instance. Null values are not
         * allowed.
         */
        public var detailedRequirements:String=null;
        
        /**
         * Represents the submission deliverables of this instance. Null values are not
         * allowed.
         */
        public var submissionDeliverables:String=null;
        
        /**
         * Represents the environment setup instructions of this instance. Null values are not
         * allowed.
         */
        public var environmentSetupInstructions:String=null;
        
        /**
         * Represents the final submission guidelines of this instance. Null values are not
         * allowed.
         */
        public var finalSubmissionGuidelines:String=null;
        
        /**
         * Represents the project id for this project spec.
         */
        public var projectId:Number=0;
        
        /**
         * Represents the project spec id for this project spec.
         */
        public var projectSpecId:Number=0;
        
        /**
         * Represents the version number for this project spec.
         */
        public var version:Number=0;
    }
}
