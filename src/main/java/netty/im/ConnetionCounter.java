package netty.im;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * Description: 连接计数器
 * </pre>
 *
 * @author chenyi
 * @date 2019/9/17
 */
public class ConnetionCounter {

    public static final ConnetionCounter INSTANCE = new ConnetionCounter();


    private AtomicInteger count = new AtomicInteger();

    private ConnetionCounter(){
    }

    public  void printConnetionCount() {
        System.out.println("当前连接数：" + getCount());
    }

    public void incrementCount() {
        count.incrementAndGet();
    }

    public void decrementCount() {
        count.decrementAndGet();
    }

    public int getCount(){
        return count.get();
    }

}
