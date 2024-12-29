package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
      ExecutorService threadPool= null;
        int port = 6379;
        try {
          serverSocket = new ServerSocket(port);
          serverSocket.setReuseAddress(true);

          threadPool = Executors.newFixedThreadPool(30);
          while((clientSocket=serverSocket.accept())!=null){
              Socket finalClientSocket = clientSocket;
              threadPool.submit(()->{
                  RequestListener requestReceiver = new RequestListener(finalClientSocket);
                  try {
                      requestReceiver.listen();
                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
              });
          }


        } catch (IOException e) {
          System.out.println("IOException: " + e.getMessage());
        } finally {
            if(threadPool!=null){
                threadPool.shutdown();
            }
          try {
            if (clientSocket != null) {
              clientSocket.close();
            }
          } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
          }
        }
  }
}