/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data {

    /**
     * A DTO class for contest multi round information data.
     *
     * @author pulky
     * @version 1.0
     * @since 1.0 (Studio Multi-Rounds Assembly - Launch Contest)
     */
    public class ContestMultiRoundInformationData {

        /**
         * Default empty constructor
         */
        public function ContestMultiRoundInformationData() {
        }
        
        /**
         * The contest multi round information data id
         */
        public var id:Number=-1;
        
        /**
         * The contest multi round information data milestone date
         */
        public var milestoneDate:String;
        
        /**
         * The contest multi round information data submitter locked between rounds flag
         */
        public var submittersLockedBetweenRounds:Boolean;
        
        /**
         * The contest multi round information data round one introduction
         */
        public var roundOneIntroduction:String;
        
        /**
         * The contest multi round information data round two introduction
         */
        public var roundTwoIntroduction:String;
    }
}
