/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.topcodercatalog.vo {
    import flash.utils.getTimer;

    /**
     * <p>
     * This is the data class for storing details of a component.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since Cockpit Catalog Widget Integration
     */
    [Bindable]
    public class TCComponent {
        /**
         * Category type of the component.
         */
        public var categoryType:String;

        /**
         * Comma separated list of categories for this component.
         */
        public var categories:String;

        /**
         * Component name.
         */
        public var componentName:String;

        /**
         * Release date for this component.
         */
        public var releaseDate:Date;

        /**
         * Component progress phase status.
         */
        public var componentProgressStatus:String;

        /**
         * Short description for the component.
         */
        public var shortDescription:String;

        /**
         * Long description for the component.
         */
        public var longDescription:String;

        /**
         * More info url for the component.
         */
        public var moreInfoURL:String;

        /**
         * Number of times this version of the component has been downloaded.
         */
        public var versionDownloadCount:Number;

        /**
         * Number of times this component has been downloaded.
         */
        public var componentDownloadCount:Number;

        /**
         * The component download url.
         */
        public var downloadURL:String;

        /**
         * The component release date in UTC milliseconds.
         */
        public var longTime:Number;

        /**
         * The version text of the component.
         */
        public var versionText:String;

        /**
         * A simple default empty contructor.
         */
        public function TCComponent() {
        }
    }
}