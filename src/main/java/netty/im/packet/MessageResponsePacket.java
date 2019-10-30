package netty.im.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import netty.im.Command;

/**
 * @author chenyi
 * @date 2019/9/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageResponsePacket  extends  Packet{

    private String fromUserId;
    private String fromUserName;
    private String message;


    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
