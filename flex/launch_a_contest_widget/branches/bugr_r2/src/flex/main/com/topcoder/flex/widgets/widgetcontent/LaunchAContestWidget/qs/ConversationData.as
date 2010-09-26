/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.qs {
    
    /**
     * Defines conversation data DTO.
     *
     * @since Cockpit Launch Contest - Inline Spec Reviews - Part 1
     */
    [Bindable]
    public class ConversationData {
        /**
         * A default empty constructor
         */
        public function ConversationData() {
        }
        
        /**
         * The comment id
         * 
         * @since Cockpit Launch Contest - Inline Spec Reviews Part 2
         */
        public var commentId:Number=-1;
        
        /**
         * The time of conversation.
         */
        public var time:Date;
        
        /**
         * The user who added this conversation.
         */
        public var user:String;
        
        /**
         * The conversation message.
         */
        public var message:String;
        
        /**
         * A boolean flag that indices whether the user is reviewer or not.
         */
        public var reviewer:Boolean;
    }
}