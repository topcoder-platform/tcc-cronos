/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project {

    /**
     * Software Project Header DTO class.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwareProjectHeader {

        /**
         * A default empty constructor.
         */
        public function SoftwareProjectHeader() {
        }

        /**
         * Represents the id of this instance. Only values greater than or equal to
         * zero is allowed.
         */
        public var id:Number=0;

        /**
         * Represents the project category of this instance. Null values are not
         * allowed.
         */
        public var projectCategory:SoftwareProjectCategory=null;

        /**
         * Represents the project status of this instance. Null values are not
         * allowed.
         */
        public var projectStatus:SoftwareProjectStatus=null;

        /**
         * It will be not zero if this project is associated with direct project.
         */
        public var tcDirectProjectId:Number=0;
        
        /**
        * Map of properties.
        * 
        * Map of properties at facade end gets transferred as array of entry.
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.MapEntry")]
        public var properties:Array=null;
    }
}