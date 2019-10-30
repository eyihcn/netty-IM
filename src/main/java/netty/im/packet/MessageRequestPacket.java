package netty.im.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static netty.im.Command.MESSAGE_REQUEST;

/**
 * @author chenyi
 * @date 2019/9/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageRequestPacket extends Packet {

    private String toUserId; // 消息发送给谁
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
