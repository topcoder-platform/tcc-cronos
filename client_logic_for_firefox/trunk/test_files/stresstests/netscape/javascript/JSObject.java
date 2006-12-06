/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package netscape.javascript;

import java.applet.Applet;

/**
 * @author assistant
 * @version 1.0
 *
 */
public class JSObject {

    static {
        System.out.println(Thread.currentThread().getContextClassLoader().getClass().getName());
    }

    /**
     * Get the member of this object.
     *
     * @param name the name
     * @return the value
     */
    public Object getMember(String name) {
        if (name.equals("opener")) {
            return new JSObject();
        } else {
            return "test";
        }
    }

    /**
     * Print the string
     * @param string
     * @return
     */
    public Object eval(String string) {
        System.out.println("eval is called with parameter : " + string);
        return null;
    }

    /**
     * Set the member.
     *
     * @param name the name of the member
     * @param value the value of the member
     */
    public void setMember(String name, Object value) {
        System.out.println("setMember is called with parameter : " + name + "," + value);
    }

    /**
     * Get window by applet.
     *
     * @param applet the applet
     * @return the js object
     */
    public static JSObject getWindow(Applet applet) {
        return new JSObject();
    }

    /**
     * Call a function in js.
     *
     * @param string the name
     * @param objects the parameters
     * @return the result
     */
    public Object call(String string, Object[] objects) {
        return new JSObject();
    }

}
