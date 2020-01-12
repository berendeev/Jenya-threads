import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculateThread extends MyThread {
    private boolean isEnd;

    public CalculateThread(Integer[] array, int delayTime, AtomicInteger createdElementsCount, JPanel view) {
        super(array, delayTime, createdElementsCount, view);
        isEnd = false;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    @Override
    public void run() {
        showText("Calculate run");
        while (!isEnd) {
            synchronized (array) {
                double expectedValue = calculateExpectedValue();
                showText("Calculate: expected value is " + expectedValue);

                double standardDeviation = calculateStandardDeviation(expectedValue);
                showText("Calculate: standard deviation is " + standardDeviation);
            }

            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double calculateStandardDeviation(double expectedValue) {
        showText("Calculate: start calculating standard deviation");
        double sum = Arrays.stream(array)
                .limit(createdElementsCount.get())
                .mapToDouble(item -> item - expectedValue)
                .map(item -> item * item)
                .sum();

        return Math.sqrt(sum / createdElementsCount.get());
    }

    private double calculateExpectedValue() {
        showText("Calculate: start calculating expected value");

        int sum = Arrays.stream(array)
                .limit(createdElementsCount.get())
                .reduce(0, (integer, integer2) -> integer + integer2);

        return (double) sum / createdElementsCount.get();
    }
}
