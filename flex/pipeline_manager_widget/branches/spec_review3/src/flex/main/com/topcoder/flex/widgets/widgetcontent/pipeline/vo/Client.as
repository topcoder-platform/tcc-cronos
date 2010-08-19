/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    
    /**
     * <p>
     * This is the data class that holds client info.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class Client {
        public function Client() {
        }
        
        public var client:String;
        
        public var contests:int=0;
        
        public var launched:int=0;
        
        public var uid:String;
    }
}