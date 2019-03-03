package com.hwj;

import com.hwj.tinymq.DeviceDataProductor;
import com.hwj.tinymq.ProductorTask;
import com.hwj.tinymq.TinyMessageQueue;
import org.junit.Assert;
import org.junit.Test;

public class TinyMessageQueueTest {

    static {
        new TinyMessageQueue(1,1,10);
    }


    @Test
    public void testPutAndTake() throws Exception{
        TinyMessageQueue.put("hwj");
        Assert.assertEquals("hwj", TinyMessageQueue.take());
    }

    @Test
    public void testSubmit() throws Exception {
        String content = "abcde";
        for (int i = 0; i < 10; i++) {
            DeviceDataProductor productor = new DeviceDataProductor(content);
            TinyMessageQueue.submit(productor);
        }
        DeviceDataProductor productor = new DeviceDataProductor("12345");
        TinyMessageQueue.submit(productor);
        Thread.sleep(5000);
        TinyMessageQueue.take();
        for (int i = 0; i < 10; i++) {
            String tmp = TinyMessageQueue.take();
            System.out.println(tmp);
        }
    }

    @Test
    public void testSize() throws Exception {
        TinyMessageQueue.put("hwj");
        Assert.assertEquals(1, TinyMessageQueue.size());
        Assert.assertEquals("hwj", TinyMessageQueue.take());
        Assert.assertEquals(0, TinyMessageQueue.size());
    }
}
