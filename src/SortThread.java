import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SortThread extends MyThread {

    private boolean isEnd;

    public SortThread(Integer[] array, int delayTime, AtomicInteger createdElementsCount, JPanel view) {
        super(array, delayTime, createdElementsCount, view);

        isEnd = false;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    @Override
    public void run() {
        showText("Sort run");

        while (!isEnd) {
            synchronized (array) {
                showText(String.format("Sort: start sorting %d created elements", createdElementsCount.get()));

                Arrays.sort(array, 0, createdElementsCount.get(), (o1, o2) -> -Integer.compare(Math.abs(o1), Math.abs(o2)));

                showText("Sort: start print array. Already created: " + createdElementsCount.get());
                for (int i = 0; i < createdElementsCount.get(); i++) {
                    showText(String.format("Sort: array[%d] = %d", i, array[i]));
                }
                showText("Sort: Finished printing");
            }

            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
