/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {
    
    /**
     * <p>
     * A renderer for contest.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class ContestRenderer extends TotalRenderBase {
        override protected function getTotalLabel():String {
            return brk.contest.toString();
        }
        
        override protected function getNormalLabel():String {
            return brk.contest.toString();
        }
    }
}