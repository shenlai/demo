package com.sl.cache.service;


import java.util.stream.IntStream;

import com.sl.cache.config.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisLockService {

    //@Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static String key = "lock-test";

    private static int count=10;

    public void test() {
        RedisLock lock = new RedisLock(redisTemplate, key, 10000, 20000);
        try {
            if (lock.lock()) {
                //log.info(Thread.currentThread().getName()+"------>>>>begin");
                System.out.println(Thread.currentThread().getName()+"------>>>>begin");
                Thread.sleep(200);
                if(count>=1){
                    count--;
                    System.out.println("count:{"+count+"}");
                }
                //log.info(Thread.currentThread().getName()+"------>>>>end");
                System.out.println(Thread.currentThread().getName()+"------>>>>end");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            // 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了.
            if(!lock.isExpired()){
                lock.unlock();
            }

        }
    }

    public void run(){
        IntStream.range(0, 100).forEach(i->{
            Thread run=new Thread(new Runnable() {
                @Override
                public void run() {
                    //System.out.println("第{"+i+"}次运行");
                    test();
                }
            });
            run.start();
        });
    }

}
