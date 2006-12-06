/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.plugin.firefox.stresstests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * This class loader is hacked to load the JSObject from stresstests directory in test_files.
 *
 * @author assistant
 * @version 1.0
 *
 */
public class FakeClassLoader extends ClassLoader {

    /**
     * The base dir to load the class.
     */
    private static final CharSequence BASE_DIR = "test_files/stresstests";

    /**
     * Load class using name.
     */
    protected synchronized Class loadClass(String name, boolean resolve)
        throws ClassNotFoundException {

        // only the JSObject is specified to load from another source
        if ("netscape.javascript.JSObject".equals(name)) {
            return loadJSObjectFromFile(name);
        } else {
            return super.loadClass(name, resolve);
        }
    }

    /**
     * Load JSObect class from a ".class" file.
     *
     * @param className the class name
     * @return the Class object loaded
     * @throws ClassNotFoundException if the class can't be found.
     */
    private Class loadJSObjectFromFile(String className) throws ClassNotFoundException {
        try {
            String classFile = getClassFile(className);
            FileInputStream fis = new FileInputStream(classFile);
            FileChannel fileC = fis.getChannel();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WritableByteChannel outC = Channels.newChannel(baos);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                int i = fileC.read(buffer);
                if (i == 0 || i == -1) {
                    break;
                }
                buffer.flip();
                outC.write(buffer);
                buffer.clear();
            }
            fis.close();
            byte[] bytes = baos.toByteArray();
            return defineClass(className, bytes, 0, bytes.length);
        } catch (IOException fnfe) {
            throw new ClassNotFoundException(className);
        }
    }

    /**
     * Get the file location of the class.
     * @param name the class name
     * @return the file location of the class
     */
    private String getClassFile(String name) {
        StringBuffer sb = new StringBuffer(BASE_DIR);
        name = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + name);
        return sb.toString();
    } 
}
