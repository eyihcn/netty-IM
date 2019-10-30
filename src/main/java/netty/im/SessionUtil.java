package netty.im;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * Description:
 * </pre>
 *
 * @author chenyi
 * @date 2019/10/17
 */
public class SessionUtil {


    // userId --> channel的映射
    private static final Map<String,Channel> userIdToChannelMap = new ConcurrentHashMap<>();


    public static void bindSession(Session session,Channel channel){

        userIdToChannelMap.put(session.getUserId(),channel);
        channel.attr(Attributes.SESSION).set(session);

    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            String userId = getSession(channel).getUserId();
            System.out.println("移除用户["+userId+"]的通道映射和用户session");
            userIdToChannelMap.remove(userId);
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdToChannelMap.get(userId);
    }

    public static boolean hasLogin(Channel channel) {

        return channel.attr(Attributes.SESSION).get() != null;
    }
}
