/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger.formatterimpl;

import com.cronos.im.messenger.ChatMessageFormatter;
import com.cronos.im.messenger.ChatMessageFormattingException;
import com.cronos.im.messenger.Helper;
import com.topcoder.document.highlight.ContentHighlighter;
import com.topcoder.document.highlight.Formatter;
import com.topcoder.document.highlight.IteratorException;
import com.topcoder.document.highlight.LineRegexIterator;
import com.topcoder.document.highlight.Token;
import com.topcoder.document.highlight.TokenIterator;
import com.topcoder.document.highlight.TokenType;

import java.io.StringReader;
import java.util.regex.PatternSyntaxException;

/**
 * <p>
 * This class uses the ContentHighlighter component to format the chat message.
 * The instance of this class will be created by <c>FormatterLoader</c>, and used by
 * <c>ChatMessage</c> to format the chat message.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is thread safe since it does not contain any mutable inner status.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ChatMessageFormatterImpl implements ChatMessageFormatter {

    /**
     * The user name formatter is used to format the user name.
     * It is set in the constructor and can't be changed later.It must be non-null after being set.
     */
    private final Formatter userNameFormatter;

    /**
     * The timestamp formatter used to format the timestamp.
     * It is set in the constructor and can't be changed later.It must be non-null after being set.
     */
    private final Formatter timestampFormatter;

    /**
     * The chatTextFormatter used to format the chat text as a whole
     * It is set in the constructor and can't be changed later.It must be non-null after being set.
     */
    private final Formatter chatTextFormatter;

    /**
     * The contentHighLighter used to format the url contained in the chat text
     * against the preconfigured url pattern.
     * It is set in the constructor and can't be changed later.It must be non-null after being set.
     */
    private final ContentHighlighter contentHighlighter;

    /**
     * The pattern is used to create token iterator, which is used by contentHighlighter to format
     * the chat text.It is set in the constructor and can't be changed later.It must be non-null after being set.
     */
    private final String pattern;

    /**
     * Create a new instance of <c>ChatMessageFormatterImpl</c> class.
     *
     * @param userNameFormatter  The user name formatter.
     * @param timestampFormatter The timestamp formatter.
     * @param chatTextFormatter  The chat text formatter.
     * @param chl                The content highlighter.
     * @param pattern            The pattern used to create a token iterator
     *                           , used after by the content highlighter
     * @throws IllegalArgumentException If any of the argument is null
     *                                  or if the <c>pattern</c> is an empty string.
     */
    public ChatMessageFormatterImpl(Formatter userNameFormatter, Formatter timestampFormatter
        , Formatter chatTextFormatter, ContentHighlighter chl, String pattern) {
        Helper.validateNotNull(userNameFormatter, "userNameFormatter");
        Helper.validateNotNull(timestampFormatter, "timestampFormatter");
        Helper.validateNotNull(chatTextFormatter, "chatTextFormatter");
        Helper.validateNotNull(chl, "chl");
        Helper.validateNotNullOrEmpty(pattern, "pattern");

        this.userNameFormatter = userNameFormatter;
        this.timestampFormatter = timestampFormatter;
        this.chatTextFormatter = chatTextFormatter;
        contentHighlighter = chl;
        this.pattern = pattern;
    }

    /**
     * Format the chat message from user name, timestamp and chat text with the formatters set in
     * the constructor.
     * Note that <c>chatText</c> will be formatted firstly by <c>contentHighlighter</c> field and
     * <c>chatTextFormatter</c> will format the result of this format operation in order to obtain
     * the final form for the formatted chat text.
     *
     * @param userName  The user name.
     * @param timestamp The timestamp.
     * @param chatText  The chat text.
     * @return The formatted message. The format of the result will be the following:
     *         [formatted user name]:[formatted timestamp]:[formatter chat text]
     * @throws IllegalArgumentException       If any of the arguments is null or empty string.
     * @throws ChatMessageFormattingException If any other error ocurred during formatting.
     */
    public String format(String userName, String timestamp, String chatText)
        throws ChatMessageFormattingException {
        Helper.validateNotNullOrEmpty(userName, "userName");
        Helper.validateNotNullOrEmpty(timestamp, "timestamp");
        Helper.validateNotNullOrEmpty(chatText, "chatText");

        try {
            // Format the userName by userNameFormatter.
            String formattedUserName =
                userNameFormatter.format(new Token(userName, TokenType.FORMAT)).getFullText();
            // Format the timestamp by timestampFormatter.
            String formattedTimestamp =
                timestampFormatter.format(new Token(timestamp, TokenType.FORMAT)).getFullText();
            // Format the chat text first by contentHighlighter and TokenIterator
            // and then format the output by chatTextFormatter.
            // Create the token iterator against the data.
            TokenIterator tokenIterator = new LineRegexIterator(pattern, new StringReader(chatText));
            String formattedChatText = contentHighlighter.format(tokenIterator);
            formattedChatText =
                chatTextFormatter.format(new Token(formattedChatText, TokenType.FORMAT)).getFullText();

            formattedChatText = formattedChatText.replaceAll("\\n", " <br />");

            // Build the result
            StringBuffer sb = new StringBuffer();
            sb.append("[").append(formattedUserName).append("]");
            sb.append(":[").append(formattedTimestamp).append("]");
            sb.append(":").append(formattedChatText);

            return sb.toString();
        } catch (IteratorException ex) {
            StringBuffer sbMessage = new StringBuffer();
            sbMessage.append("Exception during formatting of the chatText: ").append(ex.getMessage());
            throw new ChatMessageFormattingException(sbMessage.toString(), ex, chatText);
        } catch (PatternSyntaxException ex) {
            StringBuffer sbMessage = new StringBuffer();
            sbMessage.append("Exception during formatting of the chatText: ").append(ex.getMessage());
            throw new ChatMessageFormattingException(sbMessage.toString(), ex, chatText);
        }
    }
}
