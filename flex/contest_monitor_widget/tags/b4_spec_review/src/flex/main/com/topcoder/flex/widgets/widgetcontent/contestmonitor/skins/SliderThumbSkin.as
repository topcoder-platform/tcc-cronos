package com.topcoder.flex.widgets.widgetcontent.contestmonitor.skins
{
	import flash.display.GradientType;
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;
	
	public class SliderThumbSkin extends ProgrammaticSkin
	{
		public function SliderThumbSkin()
		{
			super();
		}
		
		override public function get measuredWidth():Number 
		{
	      return 5;
	    }
	 
	    override public function get measuredHeight():Number 
	    {
	      return 5;
	    }
	    
	    override protected  function updateDisplayList(w:Number, h:Number):void 
	    {
	      /*var g:Graphics = graphics;
	      g.beginFill(0xFFFFFF,1);
	      g.drawCircle(5,14,5)
	      g.endFill()*/
	    }

	}
}