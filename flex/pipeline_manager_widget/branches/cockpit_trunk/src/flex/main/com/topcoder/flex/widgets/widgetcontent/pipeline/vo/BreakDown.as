/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    
    /**
     * <p>
     * This is the data class that holds breakdown details.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class BreakDown {
        public function BreakDown() {
            contest=0;
            totalContest=0;
        }
        
        public var name:String;
        
        public var contest:int;
        
        public var totalContest:int;
        
        public var isTotal:Boolean=false;
    
    }
}