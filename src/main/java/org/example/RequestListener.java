package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestListener {

    Socket clientSocket;

    public RequestListener(Socket clientSocket){
        this.clientSocket=clientSocket;
    }

    public void listen() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
        RequestHandler requestHandler=new RequestHandler();

        String inputString;

        while((inputString=in.readLine())!=null){
           requestHandler.handleRequest(inputString, out);

        }
    }
}
