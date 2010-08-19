/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data {
    
    /**
     * <p>
     * Billing (Time Tracker) Project DTO.
     * </p>
     * 
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * 
     * Version 1.0.1 (Cockpit Release Assembly 7 v1.0) Change Notes:
     *    - Added manualPrizeSetting property
     * 
     * @author TCSASSEMBLER
     * @version 1.0.1
     * @since 1.0
     */
    public class ClientProject extends AuditableEntity {
        public function ClientProject() {
        }
        
        public var company:Company;
        public var active:Boolean;
        public var salesTax:Number;
        public var pOBoxNumber:String;
        public var paymentTermsId:Number;
        public var description:String;
        public var projectStatus:ClientProjectStatus;
        public var client:Client;
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.ClientProject")]
        public var childProjects:Array;
        public var parentProjectId:Number;

        /**
         * Represents whether this Billing Project supports/requires manual prize setting.
         * 
         * @since 1.0.1
         */ 
        public var manualPrizeSetting:Boolean;
    }
}
