import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MyThread extends Thread {

    protected final Integer[] array;
    protected final int delayTime;
    protected final AtomicInteger createdElementsCount;


    public MyThread(Integer[] array, int delayTime, AtomicInteger createdElementsCount ) {
        super();

        this.array = array;
        this.delayTime = delayTime;
        this.createdElementsCount = createdElementsCount;
    }

    protected void showText(String text) {
        System.out.println(text);
    }
}
