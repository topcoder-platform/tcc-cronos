/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {
    import com.topcoder.flex.widgets.widgetcontent.pipeline.model.Model;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.Project;
    
    /**
     * <p>
     * A renderer for resource tab.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class ResourceTabRenderer extends RendererBase {
        override protected function doFilter():void {
            if (model) {
                model.filter.resourceItem=model.role;
                model.filter.resourceName=client.name;
                model.filterDetail();
            }
        }
        
        override protected function renderLabel():void {
            this.content.text=client.name;
        }
        
        private var client:Project;
        
        override public function set data(value:Object):void {
            super.data=value;
            if (data is Project) {
                client=data as Project;
                renderLabel();
                model=Model.getInstance(client.uid);
            }
        }
    
    }
}