package netty.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.Session;
import netty.im.SessionUtil;
import netty.im.packet.LoginRequestPacket;
import netty.im.packet.LoginResponsePacket;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author chenyi
 * @date 2019/9/16
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            Session session = new Session();
            session.setUserId(loginResponsePacket.getUserId());
            session.setUserName(loginResponsePacket.getUsername());
            SessionUtil.bindSession(session,ctx.channel());
            System.out.println(new Date() + ": 客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 连接一旦建立，就登录
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        System.out.println(loginRequestPacket.getUserId());
        loginRequestPacket.setPassword("password");
        loginRequestPacket.setUsername("chenyi");
        System.out.println("send login request");
        ctx.channel().writeAndFlush(loginRequestPacket);
    }


}
