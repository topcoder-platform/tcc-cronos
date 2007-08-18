/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import java.io.IOException;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

/**
 * <p>
 * The helper class for unit test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class TestHepler {
    /**
     * <p>
     * private the constructor.
     * </p>
     */
    private TestHepler() {
        // do nothing.
    }

    /**
     * <p>
     * Check if the form contains StringItem with the same text as the given text.
     * </p>
     *
     * @param form
     *            the given form to check.
     * @param text
     *            the given string value of text.
     * @return true if the form contains StringItem with the same text as the given text and null label,
     *         else false.
     */
    public static final boolean checkStringExist(Form form, String text) {
        for (int i = 0; i < form.size(); i++) {
            Item item = form.get(i);
            if (item instanceof StringItem) {
                StringItem sItem = (StringItem) item;
                // if StringItem with the same text and null label, return true
                if ((sItem.getLabel() == null)
                        && ((text == null && sItem.getText() == null)
                                || (text != null && text.equals(sItem.getText())))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Check if the form contains ImageItem with the image the same as the given image.
     * </p>
     *
     * @param form
     *            the given form to check.
     * @param image
     *            the given value of image.
     * @return true if the form contains ImageItem with the image the same as the given image, else false.
     */
    public static final boolean checkImageExist(Form form, Image image) {
        for (int i = 0; i < form.size(); i++) {
            Item item = form.get(i);
            if (item instanceof ImageItem) {
                // if ImageItem with the same image, return true
                if (image.equals(((ImageItem) item).getImage())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Check if the form contains TextField with null string value and the same label as the given label.
     * </p>
     *
     * @param form
     *            the given form to check.
     * @param label
     *            the given string value of label.
     * @return true if the form contains TextField with null string value and the same label as the given label, else
     *         false.
     */
    public static final boolean checkTextFieldExist(Form form, String label) {
        for (int i = 0; i < form.size(); i++) {
            Item item = form.get(i);
            if (item instanceof TextField) {
                TextField field = (TextField) item;
                // if TextField with the same label and null string, return true
                if (label.equals(field.getLabel()) && "".equals(field.getString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Get the image from folder res. return null if image not existed.
     * </p>
     *
     * @param name
     *            the given file name.
     * @return image created with the given file name.
     */
    public static final Image getImage(String name) {
        return MidletMock.getImage(name);
    }

    /**
     * <p>
     * Private class used to get the image for test.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private abstract static class MidletMock extends MIDlet {
        /**
         * <p>
         * Get the image from folder res. return null if image not existed.
         * </p>
         *
         * @param name
         *            the given file name.
         * @return image created with the given file name.
         */
        public static final Image getImage(String name) {
            try {
                return Image.createImage(name);
            } catch (IOException e) {
                return null;
            }
        }
    }
}
