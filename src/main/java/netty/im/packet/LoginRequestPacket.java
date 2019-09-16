package netty.im.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import netty.im.Command;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return Command.LOGIN_REQUEST;
    }
}
