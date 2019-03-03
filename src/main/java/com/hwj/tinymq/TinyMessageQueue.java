package com.hwj.tinymq;

import java.util.concurrent.*;

public class TinyMessageQueue {

    //properties
    private static final int CAPACITY_DEFAULT = 200000;
    private static final int N_PRODUCERS = 1;
    private static final int N_CONSUMERS = 1;
    private static int capacity;
    private static int nProducers;
    private static int nConsumers;
    private static ExecutorService producerPool;
    private static ExecutorService consumerPool;

    private static BlockingQueue<String> queue;

    //constructor
    public TinyMessageQueue() {
        this(1, 1);
    }


    public TinyMessageQueue(int nProducers, int nConsumers) {
        this(nProducers, nConsumers, CAPACITY_DEFAULT);
    }

    public TinyMessageQueue(int nProducers, int nConsumers, int capacity) {
        if (queue == null) {
            this.capacity = capacity < 1 ? CAPACITY_DEFAULT : capacity;
            this.nProducers = nProducers < 1 ? N_PRODUCERS : nProducers;
            this.nConsumers = nConsumers < 1 ? N_CONSUMERS : nConsumers;
            producerPool = Executors.newFixedThreadPool(this.nProducers);
            consumerPool = Executors.newFixedThreadPool(this.nConsumers);
            this.queue = new LinkedBlockingQueue(this.capacity);
        }
    }

    //method

    /**
     * 向线程池注册任务的方法，生产者任务添加到生产者线程池，消费者任务添加到消费者线程池，无返回结果
     * @param task 待注册的任务，实现ProductorTask 或者 ConsumerTask
     * @param <T> Runnable接口的子类或者实现
     */
    public static <T extends Runnable> void submit(T task) throws Exception {
        if (task instanceof ProductorTask)
            producerPool.submit(task);
        else if (task instanceof ConsumerTask)
            consumerPool.submit(task);
        else {
            throw new Exception("The parameter of method should be a class extends ProductorTask or ConsumerTask");
        }
    }

    /**
     * 向线程池注册任务的方法，生产者任务添加到生产者线程池，消费者任务添加到消费者线程池
     * @param task 待注册的任务，实现ProductorTaskAdapter 或者 ConsumerFutureTask
     * @param <T> ProductorFutureTask ConsumerFutureTask
     * @return 返回任务执行的结果
     */
    public static <T extends Callable<Integer>> Future<Integer> submit(T task) throws Exception {
        Future<Integer> result = null;
        if (task instanceof ProductorFutureTask)
            result = producerPool.submit(task);
        else if (task instanceof ConsumerFutureTask)
            result = consumerPool.submit(task);
        else {
            throw new Exception("The parameter of method should be a class extends ProductorFutureTask or ConsumerFutureTask");
        }
        return result;
    }

    /**
     * 消息队列put方法，当queue满时，当前线程会阻塞，直到queue不满
     * @param message
     * @throws Exception InterruptedException
     */
    public static void put(String message) throws Exception {
        queue.put(message);
    }

    /**
     * 消息队列take方法，当queue为空时，当前线程会阻塞，直到queue非空
     * @param message
     * @return 返回消息队列队首的message
     * @throws Exception InterruptedException
     */
    public static String take() throws Exception {
        return queue.take();
    }

    /**
     * 队列元素个数
     * @return 元素个数
     */
    public static int size() {
        return queue.size();
    }

    public static int nConsumers() {
        return nConsumers;
    }
}
