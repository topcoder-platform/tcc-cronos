package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data
{
    public class ClientProject extends AuditableEntity
    {
        public function ClientProject()
        {
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


    }
}