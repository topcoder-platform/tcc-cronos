/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project {

    /**
     * Maps to ContestSaleData DTO of back-end server.
     *
     * @author TCSDEVELOPER
     *
     * @since Cockpit Release Assembly 1 v1.0
     */
    public class SoftwareProjectSaleData {

        /**
        * A default empty constructor.
        */
        public function SoftwareProjectSaleData() {
        }

        public var contestSaleId:Number=0;
        
        public var contestId:Number=0;
        
        public var saleStatusId:Number=0;
        
        public var paypalOrderId:String;
        
        public var price:Number=0;
        
        public var saleTypeId:Number=0;
        
        public var saleReferenceId:String;
    }
}