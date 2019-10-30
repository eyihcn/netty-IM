package netty.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.packet.MessageResponsePacket;

import java.util.Date;

/**
 * <pre>
 * Description:
 * </pre>
 *
 * @author chenyi
 * @date 2019/10/28
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(ctx.channel().id());
        System.out.println(new Date() + ": 收到["+msg.getFromUserId()+":"+msg.getFromUserName()+"]的消息: " + msg.getMessage());
    }
}
