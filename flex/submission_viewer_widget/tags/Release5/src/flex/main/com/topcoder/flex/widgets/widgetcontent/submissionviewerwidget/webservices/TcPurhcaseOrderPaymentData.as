/**
 * TcPurhcaseOrderPaymentData.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices {
    import mx.utils.ObjectProxy;
    import flash.utils.ByteArray;
    import mx.rpc.soap.types.*;
    /**
     * Wrapper class for a operation required type
     */
    
    public class TcPurhcaseOrderPaymentData extends com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices.PaymentData
    {
        /**
         * Constructor, initializes the type class
         */
        public function TcPurhcaseOrderPaymentData() 
        {
            xsiType=new QName("http://ejb.contest.facade.service.topcoder.com/", "tcPurhcaseOrderPaymentData"); 
        }
                
       public var poNumber:String;
       public var projectId:Number;
       public var projectName:String;
       public var clientId:Number;
       public var clientName:String;
   }
}
