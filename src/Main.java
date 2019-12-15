import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // todo GUI

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input count of elements in array");
        int countOfElements = scanner.nextInt();

        System.out.println("Input delay time(ms)");
        int delayTime = scanner.nextInt();

        Integer array[] = new Integer[countOfElements];
        AtomicInteger createdElementsCount = new AtomicInteger(0);

        CreatorThread creatorThread = new CreatorThread(array, delayTime, createdElementsCount);
        creatorThread.start();

        SortThread sortThread = new SortThread(array, delayTime, createdElementsCount);
        sortThread.start();

        CalculateThread calculateThread = new CalculateThread(array, delayTime, createdElementsCount);
        calculateThread.start();

        creatorThread.join();
        Thread.sleep(delayTime);

        sortThread.setEnd(true);
        calculateThread.setEnd(true);

    }
}
