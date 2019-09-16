package net.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class BIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        while (true) {
            // 阻塞等待接收客户端的连接
            Socket socket= serverSocket.accept();

            // 为每一个新的连接创建一个线程，负责读取数据
            new Thread(()->{

                int len;
                byte [] data = new byte[1024];
                try {
                    InputStream in = socket.getInputStream();
                    while ((len = in.read(data)) != -1) {
                        System.out.println("客户端["+socket.getInetAddress().getHostAddress()+": "+socket.getPort()+"]说：" + new String(data,0,len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
