package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data
{
    public class Client extends AuditableEntity
    {
        public function Client()
        {
        }
        
        public var company:Company;
        public var paymentTermsId:Number;
        public var clientStatus:ClientStatus;
        public var salesTax:Number;
        public var startDate:String;
        public var endDate:String;
        public var codeName:String;

    }
}