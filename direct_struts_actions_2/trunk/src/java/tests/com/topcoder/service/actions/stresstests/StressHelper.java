/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

/**
 * <p>
 * This is a stress test helper.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0.0
 */
public class StressHelper {

    /**
     * Field. For executing many times test .
     */
    public static int EXCUTIMES = 100;

    /**
     * <p>
     * The thread_num.
     * </p>
     */
    public static final int THREAD_NUM = 50;

    /**
     * <p>
     * The client_num.
     * </p>
     */
    public static final int CLIENT_NUM = 100;

    /**
     * <p>
     * The Constant THREAD_SLEEP.
     * </p>
     */
    public static final int THREAD_SLEEP = 10000;

    /**
     * Field.
     */
    private File file;

    /**
     * Field.
     */
    private String fileName = "stressText.txt";

    /**
     * Field for memorizing the start time.
     */
    private long startTime;

    /**
     * Field for memorizing the end time.
     */
    private long endTime;

    /**
     * Field for printing result.
     */
    private String message1 = "Calling method {0}#{1} " + "--({2}) {3} times takes: {4} ms, Avg: {5} ms.";

    /**
     * Field for printing result.
     */
    private String message2 = "Calling method {0}#{1} " + "-- {2} times takes: {3} ms, Avg: {4} ms.";

    /**
     * Default construct, for create file by file name.
     */
    public StressHelper() {

        file = new File(fileName);
    }

    /**
     * Get test`s start time.
     *
     * @return system current time
     */
    public long getStartTime() {

        startTime = System.currentTimeMillis();
        return startTime;
    }

    /**
     * Get test`s end time.
     *
     * @return system current time
     */
    public long getEndTime() {

        endTime = System.currentTimeMillis();
        return endTime;
    }

    /**
     * Set the temporary file name.
     *
     * @param fileName
     *            the file name
     */
    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    /**
     * Print the information.
     *
     * @param className
     *            the class name string
     * @param methodName
     *            the method name of the className param
     * @param note
     *            the other information
     */
    public void printResultToFile(String className, String methodName, String note) {

        long time = (endTime - startTime);
        double avgTime = time / (double) EXCUTIMES;
        String message;
        if (note == null || note.length() == 0) {
            message = MessageFormat.format(message2, className, methodName, EXCUTIMES, time, avgTime);
        } else {
            message = MessageFormat.format(message1, className, methodName, note, EXCUTIMES, time, avgTime);
        }
        file = new File(fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(message.getBytes());
            out.write(new byte[]{'\r', '\n' });
        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                // ignore
            }

        }

    }

    /**
     * Clear the file content.
     */
    public void initStressFile() {

        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * Destroy the temporary file. and the result will output.
     */
    public void showAndDestroyFile() {

        BufferedReader input = null;
        String str;
        try {
            input = new BufferedReader(new FileReader(file));
            while ((str = input.readLine()) != null) {
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        } finally {
            try {
                if (input != null) {
                    input.close();
                    input = null;
                }
                initStressFile();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Write to file.
     * </p>
     *
     * @param str
     *            the string message
     */
    public void writeToFile(String str) {

        OutputStream output;
        try {
            output = new FileOutputStream(file, true);
            BufferedOutputStream buffout = new BufferedOutputStream(output);
            buffout.write(str.getBytes());
            buffout.write("\r\n".getBytes());
            buffout.flush();
            output.close();
            buffout.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
