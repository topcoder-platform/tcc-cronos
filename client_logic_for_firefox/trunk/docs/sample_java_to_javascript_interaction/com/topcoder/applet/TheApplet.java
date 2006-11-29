package com.topcoder.applet;

import java.applet.*;
import netscape.javascript.*;

public class TheApplet extends Applet
{

	public void sayHi()
	{
		JSObject window=JSObject.getWindow(this);
		window.eval("alert('Hello from Java!');");
		System.out.println("Success!");
	}
	
	public void init()
	{
		System.out.println("Success!");
	}
}

