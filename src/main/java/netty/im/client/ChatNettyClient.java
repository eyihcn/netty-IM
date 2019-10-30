package netty.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.im.PacketCodeC;
import netty.im.SessionUtil;
import netty.im.handler.*;
import netty.im.packet.MessageRequestPacket;

import java.util.Scanner;

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
                        .addLast(new MessageResponseHandler())
                        .addLast(new PacketEncoder());
            }
        });
        bootstrap.connect("localhost", 8000).addListener(future -> {
            if (future.isSuccess()) {
                startConsoleThread(((ChannelFuture)future).channel());
                System.out.println("连接成功");
            } else {
                System.out.println("连接失败");
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        System.out.println(channel.id());
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (SessionUtil.hasLogin(channel)) {
                    System.out.println("输入接受人：");
                    Scanner sc = new Scanner(System.in);
                    String toUser = sc.nextLine();
                    System.out.println("输入消息发送至服务端: ");
                    String msg = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setToUserId(toUser);
                    packet.setMessage(msg);

                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc().buffer(), packet);
                    channel.writeAndFlush(byteBuf);
                }else {
//                    System.out.println("未登录");
                }
            }
        }).start();
    }
}
