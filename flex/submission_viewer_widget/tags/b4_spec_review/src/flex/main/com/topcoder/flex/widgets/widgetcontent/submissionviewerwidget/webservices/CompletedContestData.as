package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices
{
    public class CompletedContestData
    {
        public function CompletedContestData()
        {
        }
        
        
        public var contestId:Number;

        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices.SubmissionPaymentData")]
        public var submissions:Array;

    }
}