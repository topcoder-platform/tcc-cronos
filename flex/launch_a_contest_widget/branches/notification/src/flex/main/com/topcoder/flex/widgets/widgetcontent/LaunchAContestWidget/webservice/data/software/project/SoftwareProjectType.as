/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project {

    /**
     * Each project category must belong to a project type. This class is used in
     * ProjectCategory class to specify the project type of the project category.
     */
    public class SoftwareProjectType {

        public function SoftwareProjectType() {
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

    }
}