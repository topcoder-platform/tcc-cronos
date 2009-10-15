/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project {

    /**
     * A project category instance contains id, name and description and a reference to
     * project type. This class is used in Project class to specify the project
     * category of a project.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwareProjectCategory {

        /**
        * A default empty constructor.
        */
        public function SoftwareProjectCategory() {
        }

        /**
         * Represents the id of this instance. Only values greater than zero is
         * allowed.
         */
        public var id:Number=0;

        /**
         * Represents the name of this instance. Null or empty values are not
         * allowed.
         */
        public var name:String=null;

        /**
         * Represents the description of this instance. Null value is not allowed.
         */
        public var description:String=null;

        /**
         * The project type instance associated with this instance. Null value is
         * not allowed.
         */
        public var projectType:SoftwareProjectType=null;

        public static const PROJECT_CATEGORY_DESIGN:Number = 1;

        public static const PROJECT_CATEGORY_DEV:Number = 2;



    }
}
