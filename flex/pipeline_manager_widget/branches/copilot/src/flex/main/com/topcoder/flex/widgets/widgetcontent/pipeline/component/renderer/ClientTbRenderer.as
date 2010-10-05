/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {
    import com.topcoder.flex.widgets.widgetcontent.pipeline.model.Model;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.Client;
    
    /**
     * <p>
     * A renderer for client table.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class ClientTbRenderer extends RendererBase {
        override protected function doFilter():void {
            if (model) {
                model.filter.client=client.client;
                model.filterDetail();
            }
        }
        
        override protected function renderLabel():void {
            this.content.text=client.client;
        }
        
        private var client:Client;
        
        override public function set data(value:Object):void {
            super.data=value;
            if (data is Client) {
                client=data as Client;
                renderLabel();
                model=Model.getInstance(client.uid);
            }
        }
    }
}