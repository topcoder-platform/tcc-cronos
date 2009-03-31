/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.resource {

    /**
    * Software Resource DTO class.
    * 
    * @author TCSDEVELOPER
    * 
    * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
    */
    public class SoftwareResource extends AuditableResourceStructure {

        /**
        * A default empty constructor.
        */
        public function SoftwareResource() {
        }

        /**
         * The identfier of this resource.
         */
        public var id:Number=0;

        /**
         * The phase that the resource is associated with.
         */
        public var phase:Number;

        /**
         * The identifier of the project that the resource belongs to
         */
        public var project:Number;

        /**
         * The role of the resource
         */
        public var resourceRole:SoftwareResourceRole=null;

        /**
         * A set of submissions that the resource is associated with.
         */
        [ArrayElementType("Number")] 
        public var submissions:Array=null;
        
        /**
        * Map of properties.
        * 
        * Map of properties at facade end gets transferred as array of entry.
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.MapEntry")]
        public var properties:Array=null;

    }
}