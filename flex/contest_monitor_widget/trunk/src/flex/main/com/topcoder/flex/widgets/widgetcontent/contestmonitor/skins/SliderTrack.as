package com.topcoder.flex.widgets.widgetcontent.contestmonitor.skins
{
	import com.topcoder.flex.widgets.widgetcontent.contestmonitor.ContestMonitorWidget;
	
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;
	
	public class SliderTrack extends ProgrammaticSkin
	{
		public function SliderTrack()
		{
			super();
		}
				
	    override public function get measuredHeight():Number 
	    {
	      return 3;
	    }
	    
	    override protected  function updateDisplayList(w:Number, h:Number):void 
	    {
	    	if( ContestMonitorWidget.SLIDER_TICK_LENGTH != 0 )
	    	{
	    		var trackDiff:Number =ContestMonitorWidget.TRACK_WIDTH/ContestMonitorWidget.SLIDER_TICK_LENGTH;
	    	}
    		var g:Graphics = graphics;
		    g.clear();
		    g.beginFill(0xb9b9b9,1);
		    
		    for( var i:int = 0; i<ContestMonitorWidget.SLIDER_TICK_LENGTH; i++)
		    {
		    	 g.drawCircle(i*trackDiff,9,5);
		    }
	        g.endFill();
	        g.beginFill(0xb9b9b9,1);
	        g.drawRect(2,8,w,h);
	        g.endFill();
    		
	    }

	}
}