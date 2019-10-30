package netty.im.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.PacketCodeC;
import netty.im.Session;
import netty.im.SessionUtil;
import netty.im.packet.MessageRequestPacket;
import netty.im.packet.MessageResponsePacket;

import java.util.Date;

/**
 *
 * @author chenyi
 * @date 2019/10/28
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        // 处理消息
        Channel channel = SessionUtil.getChannel(msg.getToUserId());
        if (channel==null) {
            System.out.println("用户[:"+msg.getToUserId()+"]不在线");
            return;
        }
        Session session = SessionUtil.getSession(channel);
        if (session == null) {
            System.out.println("用户[:"+msg.getToUserId()+"]不在线");
            return;
        }
        System.out.println(new Date() + ": 收到客户端["+session.getUserName()+":"+session.getUserId()+"]消息: " + msg.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage(msg.getMessage());
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().buffer(), messageResponsePacket);
        channel.writeAndFlush(responseByteBuf);
    }

}
