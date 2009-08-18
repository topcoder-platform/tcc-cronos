/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    
    /**
     * <p>
     * This is the data class that holds project info.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class Project {
        public function Project() {
        }
        
        public var name:String;
        
        public var scheduled:int=0;
        
        public var posted:int=0;
        
        public var uid:String;
    }
}