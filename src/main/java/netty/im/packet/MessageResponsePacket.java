package netty.im.packet;

import netty.im.Command;

/**
 * @author chenyi
 * @date 2019/9/16
 */
public class MessageResponsePacket  extends  Packet{
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
