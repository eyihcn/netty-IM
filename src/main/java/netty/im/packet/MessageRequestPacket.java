package netty.im.packet;

import static netty.im.Command.MESSAGE_REQUEST;

/**
 * @author chenyi
 * @date 2019/9/16
 */
public class MessageRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
