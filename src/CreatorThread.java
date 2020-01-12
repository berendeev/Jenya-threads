import javax.swing.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CreatorThread extends MyThread {

    private final Random random;


    public CreatorThread(Integer[] array, int delayTime, AtomicInteger createdElementsCount, JPanel view) {
        super(array, delayTime, createdElementsCount, view);

        random = new Random(11); // seed for test. todo delete seed
    }

    @Override
    public void run() {
        showText("Creator run");
        while (createdElementsCount.get() < array.length) {
            int newElement = random.nextInt(999 * 2) - 999;
            synchronized (array) {
                array[createdElementsCount.getAndIncrement()] = newElement;

                print();
            }

            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void print() {
        showText("Creator: start print array. Already created: " + createdElementsCount.get());
        for (int i = 0; i < createdElementsCount.get(); i++) {
            showText(String.format("Creator: array[%d] = %d", i, array[i]));
        }
        showText("Creator: Finished printing");
    }

}
