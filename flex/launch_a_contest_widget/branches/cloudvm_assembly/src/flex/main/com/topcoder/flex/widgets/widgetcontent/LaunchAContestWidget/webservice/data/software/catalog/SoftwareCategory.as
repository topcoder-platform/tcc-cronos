/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog {

    /**
     * A software category dto class.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwareCategory {

        /**
         * A default & emptry constructor.
         */
        public function SoftwareCategory() {
        }

        /**
         * Name of the catalog. Catalog name would be set only for root categories.
         */
        public var catalogName:String=null;

        /**
         * Description of the category.
         */
        public var description:String=null;

        /**
         * Identifier of the category.
         */
        public var id:Number=0;

        /**
         * Name of the category.
         */
        public var name:String=null;

        /**
         * If this is child category then it would be set to its parent.
         */
        public var parentCategory:SoftwareCategory=null;

        /**
         * Status of this category.
         */
        public var status:String=null;

        /**
         * Whether this category is viewable or not.
         */
        public var viewable:Boolean=true;

    }
}