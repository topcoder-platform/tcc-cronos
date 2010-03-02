/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {

    /**
     * <p>
     * A renderer for percentage value.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class PercentageRenderer extends TotalRenderBase {
        override protected function getTotalLabel():String {
            return "100";
        }
        
        override protected function getNormalLabel():String {
            return int(brk.contest / brk.totalContest * 100).toString();
        }
    }
}