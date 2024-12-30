import Processors.BulkStringProcessor;
import Services.PingResponseService;
import memoryStore.InMemDataStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class RequestListenerService {

     Socket clientSocket;
     InMemDataStore inMemDataStore;

    public RequestListenerService(Socket clientSocket, InMemDataStore inMemDataStore){
        this.clientSocket=clientSocket;
        this.inMemDataStore=inMemDataStore;
    }

    public void listen() throws IOException {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String inputString;
            while ((inputString = in.readLine()) != null) {
                System.out.println("Received: " + inputString);  // Debug log
                if (inputString.equals("PING")) {
                    PingResponseService.replyPong(out);
                } else if (inputString.charAt(0) == '*') {
                    BulkStringProcessor.handleRequest(inputString, in, out, inMemDataStore);
                }
            }
        } finally {
            clientSocket.close();
        }
    }
}
