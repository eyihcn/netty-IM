package example.discard;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String msg = "hello discard server, i am an client " + socketChannel.getLocalAddress() + "中";
        byte[] bytes = msg.getBytes();
        System.out.println(msg);
        System.out.println(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        socketChannel.read(readBuf);
        readBuf.flip();
        byte[] readByte = null;
        StringBuilder stringBuilder = new StringBuilder();
        int bufSize = 1024;
        int remaining = readBuf.remaining();
        while (remaining > 0) {
            if (remaining < 1024) {
                bufSize = remaining;
            }
            readByte = new byte[bufSize];
            readBuf.get(readByte);
            stringBuilder.append(new String(readByte));
            remaining = readBuf.remaining();
        }
        System.out.println(stringBuilder);

        socketChannel.close();
    }
}
