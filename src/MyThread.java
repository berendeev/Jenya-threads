import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MyThread extends Thread {

    protected final Integer[] array;
    protected final int delayTime;
    protected final AtomicInteger createdElementsCount;
    private JPanel view;


    public MyThread(Integer[] array, int delayTime, AtomicInteger createdElementsCount, JPanel view) {
        super();

        this.array = array;
        this.delayTime = delayTime;
        this.createdElementsCount = createdElementsCount;
        this.view = view;
    }

    protected synchronized void showText(String text) {
        view.add(new JLabel(text));
        view.updateUI();
    }
}
