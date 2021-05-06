package com.sl.threadlearning.interruptAndWait;

import com.sl.threadlearning.waitAndnotify.InThrad;
import com.sl.threadlearning.waitAndnotify.OutThread;
import com.sl.threadlearning.waitAndnotify.Res;
import org.junit.Test;

import static org.junit.Assert.*;

public class InterruptAndWaitDemoTest {

    @Test
    public void testWaitAndNoitify() throws InterruptedException {

        Res lock = new Res();
        InterruptAndWaitDemo th = new InterruptAndWaitDemo(lock);

        th.start();


        Thread.sleep(5000);
        th.interrupt();

        Thread.sleep(5000);
    }

}