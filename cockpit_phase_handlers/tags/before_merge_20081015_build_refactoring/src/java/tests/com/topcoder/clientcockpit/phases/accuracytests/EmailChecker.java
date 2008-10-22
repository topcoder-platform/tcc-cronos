/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.accuracytests;

import com.topcoder.smtp.server.test.CommandHandler;
import com.topcoder.smtp.server.test.SMTPState;
import com.topcoder.smtp.server.test.State;

/**
 * <p>
 * This class implements <code>CommandHandler</code> to check which email sent by comparing the email subject.
 * See /test_files/PhaseHandler.xml.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailChecker implements CommandHandler {

    /**
     * <p>
     * Indicates whether to print out received message.
     * </p>
     */
    private boolean printReceivedMessage;

    /**
     * <p>
     * Indicates whether start email is sent.
     * </p>
     */
    private boolean startEmailSent;

    /**
     * <p>
     * Indicates whether end email is sent.
     * </p>
     */
    private boolean endEmailSent;

    /**
     * <p>
     * Indicates whether one hour email is sent.
     * </p>
     */
    private boolean oneHourEmailSent;

    /**
     * <p>
     * Indicates whether eight hours email is sent.
     * </p>
     */
    private boolean eightHoursEmailSent;

    /**
     * <p>
     * The wrapped <code>CommandHandler</code> to handle email message.
     * </p>
     */
    private final CommandHandler wrapped;

    /**
     * <p>
     * The received email raw content.
     * </p>
     */
    private String received = "";

    /**
     * <p>
     * Constructor with given <code>CommandHandler</code> to be wrapped.
     * </p>
     *
     * @param wrapped <code>CommandHandler</code> to be wrapped.
     */
    public EmailChecker(CommandHandler wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * <p>
     * Check the input content. Then delegate to wrapped <code>CommandHandler</code>.
     * </p>
     *
     * @param inputLine a non-null, possibly empty line of text to be handled.
     * @param state a non-null state representing the current state for the connection.
     *
     * @return a null if the handler cannot handle the line, an empty string representing the text.
     *         being handled but nothing to send back to the client or a non-empty string representing the
     *         text being handled with the specified text to send to the client.
     */
    public String handle(String inputLine, SMTPState state) {

        if (this.printReceivedMessage && state.state.getOrdinal() == State.DATA.getOrdinal()) {
            System.out.println(inputLine);
        }

        if (inputLine.indexOf("eightHoursEmailSubject") > -1) {
            this.eightHoursEmailSent = true;
        } else if (inputLine.indexOf("oneHourEmailSubject") > -1) {
            this.oneHourEmailSent = true;
        } else if (inputLine.indexOf("startEmailSubject") > -1) {
            this.startEmailSent = true;
        } else if (inputLine.indexOf("endEmailSubject") > -1) {
            this.endEmailSent = true;
        }

        this.received += inputLine;

        //Delegate to wrapped handler
        return this.wrapped.handle(inputLine, state);
    }

    /**
     * <p>
     * Return true if eight hours email is sent.
     * </p>
     *
     * @return true if eight hours email is sent.
     */
    public boolean isEightHoursEmailSent() {
        return eightHoursEmailSent;
    }

    /**
     * <p>
     * Return true if end email is sent.
     * </p>
     *
     * @return true if end email is sent.
     */
    public boolean isEndEmailSent() {
        return endEmailSent;
    }

    /**
     * <p>
     * Return true if one hour email is sent.
     * </p>
     *
     * @return true if one hour email is sent.
     */
    public boolean isOneHourEmailSent() {
        return oneHourEmailSent;
    }

    /**
     * <p>
     * Return true if start email is sent.
     * </p>
     *
     * @return true if start email is sent.
     */
    public boolean isStartEmailSent() {
        return startEmailSent;
    }

    /**
     * <p>
     * Return the value of <code>printReceivedMessage</code> property.
     * </p>
     *
     * @return the value of <code>printReceivedMessage</code> property.
     */
    public boolean isPrintReceivedMessage() {
        return printReceivedMessage;
    }

    /**
     * <p>
     * Set the value of <code>printReceivedMessage</code> property.
     * </p>
     *
     * @param printReceivedMessage the value of <code>printReceivedMessage</code> property to set.
     */
    public void setPrintReceivedMessage(boolean printReceivedMessage) {
        this.printReceivedMessage = printReceivedMessage;
    }

    /**
     * <p>
     * Get the email raw content received.
     * </p>
     *
     * @return the email raw content received.
     */
    public String getReceived() {
        return received;
    }

    /**
     * <p>
     * Clear the check results.
     * </p>
     */
    public void clear() {
        this.received = "";
        this.startEmailSent = false;
        this.endEmailSent = false;
        this.oneHourEmailSent = false;
        this.eightHoursEmailSent = false;
        this.printReceivedMessage = false;
    }
}
