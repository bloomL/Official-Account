package learn_7_20;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @author liguo
 * @Description ThreadLocal 局部变量表，每个线程独享的本地变量，线程隔离
 * 场景1：每个线程需要一个独享的对象，通常是工具类，比如典型的SimpleDateFormat和Random
 * @date 2020/7/20/020 13:00
 */
public class ThreadLocalUsage01 {
    public static ExecutorService THREAD_POOL = new ThreadPoolExecutor(10,10,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int MAX_CYCLE = 1000;

    public static void main(String[] args) {
        for (int i = 0; i < MAX_CYCLE; i++) {
            final int finalI = i;
            THREAD_POOL.submit(new Runnable() {
                public void run() {
                    String date = new ThreadLocalUsage01().date(finalI);
                    System.out.println(date);
                }
            });
        }
        //关闭线程池，不再接受新的任务提交，等待现有队列中的任务全部执行完毕之后关闭
        THREAD_POOL.shutdown();
    }

    /**
     * 线程不安全
     * 实例对象在多线程环境下作为共享数据，会发生线程不安全问题。
     * 加锁，造成性能下降
     * @param seconds 毫秒数
     * @return date
     */
    private String date(int seconds){
        Date date = new Date(1000 * seconds);

        // 1.线程不安全 return DATE_FORMAT.format(date);
        /* 2.使用synchronized 线程安全 String format;
        synchronized (ThreadLocalUsage01.class){
            format = DATE_FORMAT.format(date);
        }
        return format;*/

        /* 3.使用ThreadLocal确保线程安全  为了避免创建1000个SimpleDateFormat对象，且在不使用锁的情况下保证线程安全
          多个线程内部都有一个SimpleDateFormat对象副本，每个线程使用自己的SimpleDateFormat */
        SimpleDateFormat simpleDateFormat = ThreadSafeDateFormatter.dateFormatThreadLocal.get();
        return simpleDateFormat.format(date);
    }
}
 class ThreadSafeDateFormatter{
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>(){
        /**
         * 每个线程具备其一个副本
         * @return
         */
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

}
