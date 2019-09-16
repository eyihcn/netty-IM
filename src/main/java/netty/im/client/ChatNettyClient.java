package netty.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.im.handler.LoginResponseHandler;
import netty.im.handler.PacketDecoder;
import netty.im.handler.PacketEncoder;
import netty.im.handler.Spliter;

/**
 * @author chenyi
 * @date 2019/9/16
 */
public class ChatNettyClient {


    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        // 指定线程模型
        bootstrap.group(workGroup);
        // 指定IO类型
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new Spliter())
                        .addLast(new PacketDecoder())
                        .addLast(new LoginResponseHandler())
                        .addLast(new PacketEncoder());
            }
        });
        bootstrap.connect("localhost", 8000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else {
                System.out.println("连接失败");
            }
        });
    }

}
