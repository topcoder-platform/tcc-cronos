/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Defines a simple HTTP server, which would accept, record all client requests and give pre-recorded responses.
 * 
 * @author visualage
 * @version 1.0
 */
public class SimpleHttpServer extends Thread {
    /** Represents the port of this simple HTTP server. */
    private final int port;

    /** Represents the server socket used to accept clients. */
    private ServerSocket server = null;

    /** Represents the socket used to communicate with the client. */
    private Socket client = null;

    /** Represents all the requests recorded. */
    private List requests = new ArrayList();

    /** Represents all the re-recorded responses. */
    private List responses = new ArrayList();

    /**
     * Creates a new instance of <code>SimpleHttpServer</code>. The port of the server is given.
     * 
     * @param port the port of the HTTP server.
     */
    public SimpleHttpServer(int port) {
        this.port = port;
    }

    /**
     * Executes the HTTP server. It receives a request and send the pre-defined response.
     */
    public void run() {
        try {
            server = new ServerSocket(port, 0, InetAddress.getByName("localhost"));

            // For each responses, try to receive a request.
            for (Iterator iter = responses.iterator(); iter.hasNext();) {
                client = server.accept();

                // Read the request.
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String str;

                requests.add(sb);

                while (true) {
                    str = reader.readLine().trim();
                    sb.append(str);
                    sb.append('\n');
                    if (str.trim().length() == 0) {
                        break;
                    }
                }

                // Send the response
                String response = (String) iter.next();

                OutputStream os = client.getOutputStream();

                os.write(response.getBytes());
                os.close();

                if (sb.toString().indexOf("POST") >= 0) {
                    while (reader.ready()) {
                        sb.append((char) reader.read());
                    }
                }

                // Close the socket
                client.close();
            }
        } catch (IOException e) {
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                // ignore
            }
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * Stop the server. The server socket is closed immediately.
     */
    public void stopServer() {
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                // ignore
            }
        }

        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                // ignore
            }
        }

        interrupt();
    }

    /**
     * Resets the recorded requests and pre-defined responses. The server is stopped first.
     */
    public void reset() {
        stopServer();

        requests.clear();
        responses.clear();
    }

    /**
     * Adds a pre-defined response to the response list.
     * 
     * @param response the pre-defined response to be added.
     */
    public void addResponse(String response) {
        responses.add(response);
    }

    /**
     * Retrieves the recorded request list.
     * 
     * @return the recorded request list.
     */
    public List getRequests() {
        return requests;
    }
}
