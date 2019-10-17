package netty.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.im.ConnetionCounter;

/**
 * <pre>
 * Description:
 * ChannelHandler回调方法的执行顺序为
 * handlerAdded() -> channelRegistered() -> channelActive() -> channelRead() -> channelReadComplete()
 *
 * 渠道被关闭
 * channelInactive() -> channelUnregistered() -> handlerRemoved()
 * </pre>
 *
 * @author chenyi
 * @date 2019/9/17
 */
public class LifeCyCleTestHandler extends ChannelInboundHandlerAdapter {


    /**
     * handlerAdded()：指的是当检测到新连接之后，调用ch.pipeline().addLast(new LifeCyCleTestHandler());之后的回调，表示在当前的频道中，已经成功添加了一个处理器处理器。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("逻辑处理器被添加：handlerAdded()");
        super.handlerAdded(ctx);
    }

    /**
     * channelRegistered()：这个回调方法，表示当前的通道的所有的逻辑处理已经和某个NIO线程建立了绑定关系
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 绑定到线程(NioEventLoop)：channelRegistered()");
        super.channelRegistered(ctx);
    }

    /**
     * channelActive(): 当渠道的所有业务逻辑链准备完毕（也就是说渠道的管道中已经添加完所有的处理程序）以及绑定好一个NIO线程之后，这条连接算是真正激活了，接下来就会回调到此方法。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 准备就绪：channelActive()");
        ConnetionCounter.INSTANCE.incrementCount();
        super.channelActive(ctx);
    }

    /**
     * * channelRead()：客户端向服务端发来数据，每次都会回调此方法，表示有数据可读。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel 有数据可读：channelRead()");
        super.channelRead(ctx, msg);
    }

    /**
     * channelReadComplete()：服务端每次读完一次完整的数据之后，回调该方法，表示数据读取完毕。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 某次数据读完：channelReadComplete()");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 被关闭：channelInactive()");
        ConnetionCounter.INSTANCE.decrementCount();
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 取消线程(NioEventLoop) 的绑定: channelUnregistered()");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("逻辑处理器被移除：handlerRemoved()");
        super.handlerRemoved(ctx);
    }
}
