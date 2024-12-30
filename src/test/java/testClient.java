import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class testClient {



    @Test
    public void testEcho() throws IOException {
        String inp="*2\r\n$4\r\nECHO\r\n$3\r\nhey\r\n";
        Socket socket = new Socket("localhost", 6379);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(inp);
        Assert.assertEquals(in.readLine(),"$3");
        Assert.assertEquals(in.readLine(),"hey");

    }

    @Test
    public void testConcurrentConnections() {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try {
            CompletableFuture.allOf(
                    IntStream.range(0, 10)
                            .mapToObj(i -> CompletableFuture.runAsync(() -> {
                                Socket socket = null;
                                try {
                                    socket = new Socket("localhost", 6379);
                                    socket.setSoTimeout(5000);  // 5 second timeout
                                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                                    // First PING
                                    out.println("PING");
                                    String response1 = in.readLine();
                                    System.out.println("Response 1: " + response1);  // Debug log

                                    // Second PING
                                    out.println("PING");
                                    String response2 = in.readLine();
                                    System.out.println("Response 2: " + response2);  // Debug log

                                    Assert.assertEquals("+PONG", response1);
                                    Assert.assertEquals("+PONG", response2);

                                } catch (IOException e) {
                                    throw new CompletionException(e);
                                } finally {
                                    if (socket != null) {
                                        try {
                                            socket.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }, pool))
                            .toArray(CompletableFuture[]::new)
            ).join();
        } finally {
            pool.shutdownNow();  // Force shutdown if test hangs
        }
    }



}
