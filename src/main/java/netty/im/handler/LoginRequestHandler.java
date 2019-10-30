package netty.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.im.Session;
import netty.im.SessionUtil;
import netty.im.packet.LoginRequestPacket;
import netty.im.packet.LoginResponsePacket;

/**
 * @author chenyi
 * @date 2019/9/16
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        // 创建登录响应包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setUserId(loginRequestPacket.getUserId());
            loginResponsePacket.setUsername(loginRequestPacket.getUsername());
            // 登录成功记录登录标记
            Session session = new Session();
            session.setUserId(loginRequestPacket.getUserId());
            session.setUserName(loginRequestPacket.getUsername());
            SessionUtil.bindSession(session,ctx.channel());
            System.out.println("login successs");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
        }
        System.out.println("response login ");
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
