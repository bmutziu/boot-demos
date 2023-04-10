package instance.doublecheck;

import instance.TicketHandler;
import instance.hunger.HungerTicketHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author kangqing
 * @since 2023/4/10 07:32
 */
public class DoubleCheckTest {
    public static void main(String[] args) {
        // 用户人数
        int userNum = 30000;

        // 线程集合
        Set<Thread> threadSet = new HashSet<>();
        // 存放实例对象
        List<TicketHandler> ticketHandlers = new CopyOnWriteArrayList<>(new ArrayList<>(userNum));
        // 保存生产的车票
        List<Long> ticketList = new CopyOnWriteArrayList<>(new ArrayList<>(userNum));
        // 定义购票线程，一个线程模拟一个用户
        for (int i = 0; i < userNum; i++) {
            Thread t = new Thread(() -> {
                // 生产懒加载单例
                TicketHandler handler = DoubleCheckTicketHandler.getInstance();
                // 增加实例对象到list
                ticketHandlers.add(handler);
                // 生产车票号
                final Long nextTicket = handler.getNextTicket();
                ticketList.add(nextTicket);
            });
            threadSet.add(t);
        }

        System.out.println("当前购票人数 = " + threadSet.size());
        // 上面的add还没有结束，这里如果使用 foreach循环则会出现 ConcurrentModificationException
        // java8 的 Foreach（e -> e.start()）也会有出现此异常的机会
        // 迭代器也不行，也会出现异常,因为ArrayList甚至Vector也不行，在多线程情况下，一遍添加，一边遍历会失败
        // 这里使用并发容器 CopyOnWriteArrayList 解决
        for (Thread thread : threadSet) {
            thread.start();
        }
        // 这里会有一点问题，如果上面的线程过多，未处理完成就去统计的话，结果不准确
        System.out.println("车票生产类实例对象数量 = " + new HashSet(ticketHandlers).size());

        System.out.println("共出票数量 = " + ticketList.size());

        System.out.println("实际出票数量 = " + new HashSet(ticketList).size());
    }
}
