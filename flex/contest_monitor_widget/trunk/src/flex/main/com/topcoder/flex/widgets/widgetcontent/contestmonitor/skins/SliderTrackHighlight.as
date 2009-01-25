package com.topcoder.flex.widgets.widgetcontent.contestmonitor.skins
{
	import flash.display.GradientType;
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;
	
	import com.topcoder.flex.widgets.widgetcontent.contestmonitor.ContestMonitorWidget;
	
	public class SliderTrackHighlight extends ProgrammaticSkin
	{
		public function SliderTrackHighlight()
		{
			super();
		}
				
	    override public function get measuredHeight():Number {
	      return 3;
	    }
	    
	    override protected  function updateDisplayList(w:Number, h:Number):void 
	    {
	    	
	    	trace("REFRESHING HIGHLIGHTED TRACK: " + w + ", " + width);
			if( ContestMonitorWidget.SLIDER_TICK_LENGTH != 0 && ContestMonitorWidget.TRACK_WIDTH != 0)
			{
				var trackDiff:Number = ContestMonitorWidget.TRACK_WIDTH /(ContestMonitorWidget.SLIDER_TICK_LENGTH - 1);
			}
	    	
			var g:Graphics = graphics;
			g.clear();
			    
			g.beginFill(0xab0000,1);
			for( var i:int = 0; i < ContestMonitorWidget.SLIDER_TICK_LENGTH; i++) {
				if( i*trackDiff <= w ) {
			  		g.drawCircle(i*trackDiff,9,5);	
			  	}
			}
			
			g.endFill();
			g.beginFill(0xab0000,1);
			g.drawRect(2,8,w,h);
			g.endFill();
	    }

	}
}