package net.test;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost",8000);
        while (true) {
            String hello =  new Date()+" hello server";
            System.out.println(hello);
            socket.getOutputStream().write(hello.getBytes());
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
