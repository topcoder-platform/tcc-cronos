/**
 * CreditCardPaymentData.as
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
    
    public class CreditCardPaymentData extends com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices.PaymentData
    {
        /**
         * Constructor, initializes the type class
         */
        public function CreditCardPaymentData() {
            xsiType=new QName("http://ejb.contest.facade.service.topcoder.com/", "creditCardPaymentData"); 
        }
                
       public var address:String;
       public var amount:String;
       public var cardExpiryMonth:String;
       public var cardExpiryYear:String;
       public var cardNumber:String;
       public var cardType:String;
       public var city:String;
       public var country:String;
       public var email:String;
       public var firstName:String;
       public var ipAddress:String;
       public var lastName:String;
       public var phone:String;
       public var sessionId:String;
       public var state:String;
       public var zipCode:String;
       public var csc:String; //BUGR-1398
   }
}
