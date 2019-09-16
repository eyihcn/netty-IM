package netty.im.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.im.PacketCodeC;
import netty.im.packet.Packet;

/**
 * @author chenyi
 * @date 2019/9/16
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        out.writeBytes(PacketCodeC.INSTANCE.encode(out,msg));
    }

}
