/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data {
    import mx.utils.ObjectProxy;
    import flash.utils.ByteArray;
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class CompetionType {

        /**
         * Constructor, initializes the type class
         */
        public function CompetionType() {
        }
        
        /**
        * Competition type value.
        */
        [Inspectable(category="Generated values", enumeration="STUDIO,CONCEPTUALIZATION,SPECIFICATION,ARCHITECTURE,DESIGN,DEVELOPMENT,RIACOMPONENT,RIABUILD,UIPROTOTYPE,ASSEMBLY,TESTSUITES, TESTSCENARIOS ,SOFTWARE", type="String")]
        public var competionType:String;

        /**
        * Returns the string value of competition type.
        * 
        * @return string value of competition type.
        */
        public function toString():String {
            return competionType.toString();
        }

    }
}
