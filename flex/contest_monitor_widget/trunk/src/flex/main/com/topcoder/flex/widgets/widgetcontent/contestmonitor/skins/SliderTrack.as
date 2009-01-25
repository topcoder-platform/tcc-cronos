package com.topcoder.flex.widgets.widgetcontent.contestmonitor.skins
{
	import com.topcoder.flex.widgets.widgetcontent.contestmonitor.ContestMonitorWidget;
	
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;
	
	public class SliderTrack extends ProgrammaticSkin
	{
		private var _w:Number;
		private var _h:Number;
		
		public var _update:Function = update;
		
		public function SliderTrack()
		{
			super();
		}
				
	    override public function get measuredHeight():Number 
	    {
	      return 3;
	    }
	    
	    public function update():void {
	    	trace("Updating Display List");
	    	updateDisplayList(width, height);
	    }
	    
	    override protected  function updateDisplayList(w:Number, h:Number):void 
	    {
	    	trace("REFRESHING TRACK: " + w);
	    	ContestMonitorWidget.TRACK_WIDTH = width;
	    	if( ContestMonitorWidget.SLIDER_TICK_LENGTH != 0 && ContestMonitorWidget.TRACK_WIDTH != 0) {
	    		var trackDiff:Number = ContestMonitorWidget.TRACK_WIDTH /(ContestMonitorWidget.SLIDER_TICK_LENGTH - 1);
	    	}
	    	
    		var g:Graphics = graphics;
		    g.clear();
		    g.beginFill(0xb9b9b9,1);
		    
		    for( var i:int = 0; i < ContestMonitorWidget.SLIDER_TICK_LENGTH; i++)
		    {
		    	 g.drawCircle(i*trackDiff, 9, 5);
		    }
	        g.endFill();
	        g.beginFill(0xb9b9b9,1);
	        g.drawRect(2,8,w,h);
	        g.endFill();
	    }

	}
}