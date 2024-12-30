package org.example;

import java.io.PrintWriter;

public class PingResponseService {
    public static void replyPong(PrintWriter out){
        out.println("+PONG");
    }
}
