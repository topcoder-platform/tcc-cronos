/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices
{
    /**
     * A DTO class for submission payment data.
     *
     * Version 1.1 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI) Change Notes:
     *    - Added awardMilestonePrize attribute
     *
     * @author pulky
     * @version 1.1
     * @since 1.0
     */
    public class SubmissionPaymentData
    {
        public function SubmissionPaymentData()
        {
        }
        
        public var id:Number;
        
        public var rank:Number;
        
        public var amount:Number;

        /**
         * The award milestone prize flag
         *
         * @since 1.1
         */
        public var awardMilestonePrize:Boolean;

    }
}
