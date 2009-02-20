/**
 * PaymentData.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data{
    import mx.rpc.soap.types.*;
    import mx.rpc.xml.IXMLSchemaInstance;
    
    /**
     * Wrapper class for a operation required type
     */
    public class PaymentData implements IXMLSchemaInstance
    {
        /**
         * Constructor, initializes the type class
         */
        public function PaymentData() {}
        public var _xsiType:QName;

        public function get xsiType():QName
        {
            return _xsiType;
        }

        public function set xsiType(value:QName):void
        {
            _xsiType=value;
            return ;
        }

        public var type:com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.PaymentType;
    }
}