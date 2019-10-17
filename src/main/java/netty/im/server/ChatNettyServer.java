package netty.im.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.im.ConnetionCounter;
import netty.im.handler.*;

import java.util.concurrent.TimeUnit;


/**
 * Discards any incoming data.
 */
public class ChatNettyServer {


    public static void main(String[] args) throws InterruptedException {

        // 定时打印连接数

        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new Spliter())
                                    .addLast(new LifeCyCleTestHandler())
                                    .addLast(new PacketDecoder())
                                    .addLast(new LoginRequestHandler())
                                    .addLast(new PacketEncoder());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            ChannelFuture f = b.bind(8000).sync(); // (7)

            b.config().group().scheduleAtFixedRate(ConnetionCounter.INSTANCE::printConnetionCount,0,1, TimeUnit.MINUTES);

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
