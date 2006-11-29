package com.topcoder.applet;

import java.applet.*;
import netscape.javascript.*;

public class TheApplet extends Applet
{

	public void sayHi()
	{
		JSObject window=JSObject.getWindow(this);

		//Call back out to the browser Javascript

		window.eval("alert('Hello from Java!');");
	}
}

