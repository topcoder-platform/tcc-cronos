/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data {

    /**
     * A DTO class for milestone prize data.
     *
     * @author pulky
     * @version 1.0
     * @since 1.0 (Studio Multi-Rounds Assembly - Launch Contest)
     */
    public class MilestonePrizeData {

        /**
         * Default empty constructor
         */
        public function MilestonePrizeData() {
        }

        /**
         * The milestone prize data id
         */
        public var id:Number=-1;

        /**
         * The milestone prize data creation date
         */
        public var createDate:String;

        /**
         * The milestone prize data amount
         */
        public var amount:Number;

        /**
         * The milestone prize data number of submissions
         */
        public var numberOfSubmissions:Number;
    }
}
