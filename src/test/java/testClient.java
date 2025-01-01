import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
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
        socket.close();

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
                                    System.out.println("Response 1: " + response1);

                                    // Second PING
                                    out.println("PING");
                                    String response2 = in.readLine();
                                    System.out.println("Response 2: " + response2);

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
            pool.shutdownNow();
        }
    }


    @Test
    public void testGetSet() throws IOException {
        // Test SET
        String addRequest = "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\nbar\r";
        Socket socket = new Socket("localhost", 6379);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(addRequest);
        String response = in.readLine();
        Assert.assertEquals("+OK", response);

        // Test GET
        String getRequest = "*2\r\n$3\r\nGET\r\n$3\r\nfoo\r";
        out.println(getRequest);
        String lengthResponse = in.readLine();
        String valueResponse = in.readLine();

        Assert.assertEquals("$3", lengthResponse);
        Assert.assertEquals("bar", valueResponse);

        socket.close();
    }
    @Test
    public void testGetSetWithTimeout() throws IOException, InterruptedException {

        String addRequest = "*5\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\nbar\r\n$2\r\npx\r\n$4\r\n1000\r";
        Socket socket = new Socket("localhost", 6379);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(addRequest);
        String response = in.readLine();
        Assert.assertEquals("+OK", response);

        // Test GET
        String getRequest = "*2\r\n$3\r\nGET\r\n$3\r\nfoo\r";
        out.println(getRequest);
        String lengthResponse = in.readLine();
        String valueResponse = in.readLine();

        Assert.assertEquals("$3", lengthResponse);
        Assert.assertEquals("bar", valueResponse);

        //waiting 1000ms till the cache is invalidated
        Thread.sleep(1000);

        out.println(getRequest);
        response = in.readLine();

        Assert.assertEquals("$-1", response);  // since the data is invalidated we will receive empty

        socket.close();

    }

    @Test
    public void testConfigGet() throws IOException {
        Socket socket = new Socket("localhost", 6379);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String dirConfigRequest = "*3\r\n$6\r\nCONFIG\r\n$3\r\nGET\r\n$3\r\ndir\r";
        out.println(dirConfigRequest);

        String arrayLengthResponse = in.readLine();
        String keyLengthResponse = in.readLine();
        String keyResponse = in.readLine();
        String valueLengthResponse = in.readLine();
        String valueResponse = in.readLine();

        Assert.assertEquals("*2", arrayLengthResponse);
        Assert.assertEquals("$3", keyLengthResponse);
        Assert.assertEquals("dir", keyResponse);
        Assert.assertEquals("$4", valueLengthResponse);
        Assert.assertEquals("/tmp", valueResponse);

        String dbFilenameConfigRequest = "*3\r\n$6\r\nCONFIG\r\n$3\r\nGET\r\n$9\r\ndbfilename\r";
        out.println(dbFilenameConfigRequest);

        arrayLengthResponse = in.readLine();
        keyLengthResponse = in.readLine();
        keyResponse = in.readLine();
        valueLengthResponse = in.readLine();
        valueResponse = in.readLine();

        Assert.assertEquals("*2", arrayLengthResponse);
        Assert.assertEquals("$10", keyLengthResponse);
        Assert.assertEquals("dbfilename", keyResponse);
        Assert.assertEquals("$4", valueLengthResponse);
        Assert.assertEquals("temp", valueResponse);

        socket.close();
    }

}
