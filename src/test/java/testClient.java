import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class testClient {

    @Test
    public void testMain() throws IOException {

        Socket socket = new Socket("localhost", 6379);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        for(int i=0;i<10;i++){
            pool.submit(()->{
                out.println("PING");
                String response1 = null;
                try {
                    response1 = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                out.println("PING");
                String response2 = null;
                try {
                    response2 = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Assert.assertEquals("+PONG", response1);
                Assert.assertEquals("+PONG", response2);
            });
        }



        pool.shutdown();
        socket.close();
    }



}
