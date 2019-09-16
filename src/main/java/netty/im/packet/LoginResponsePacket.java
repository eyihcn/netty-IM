package netty.im.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import netty.im.Command;

/**
 * @author chenyi
 * @date 2019/9/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {

    private  boolean success;
    private  String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
