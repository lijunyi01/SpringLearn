package allcom.hello;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ljy on 15/11/3.
 * ok
 */
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
