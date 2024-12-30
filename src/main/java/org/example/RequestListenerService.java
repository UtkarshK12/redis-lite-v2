package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestListenerService {

     Socket clientSocket;

    public RequestListenerService(Socket clientSocket){
        this.clientSocket=clientSocket;
    }

    public void listen() throws IOException {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputString;
            while ((inputString = in.readLine()) != null) {
                System.out.println("Received: " + inputString);  // Debug log
                if (inputString.equals("PING")) {
                    PingResponseService.replyPong(out);
                } else if (inputString.charAt(0) == '*') {
                    BulkStringProcessor.handleRequest(inputString, in, out);
                }
            }
        } finally {
            clientSocket.close();
        }
    }
}
