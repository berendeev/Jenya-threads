import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // GUI
        JPanel creatorPanel = new JPanel();
        JPanel sorterPanel = new JPanel();
        JPanel calculatorPanel = new JPanel();

        creatorPanel.setLayout(new BoxLayout(creatorPanel, BoxLayout.Y_AXIS));
        sorterPanel.setLayout(new BoxLayout(sorterPanel, BoxLayout.Y_AXIS));
        calculatorPanel.setLayout(new BoxLayout(calculatorPanel, BoxLayout.Y_AXIS));


        JFrame frame = new JFrame("Threads");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setBounds(300, 150, 800, 500);
        frame.setLayout(null);

        int spaceBetweenBlocks = 10;

        JScrollPane creatorScrollPane = new JScrollPane(creatorPanel);
        creatorScrollPane.setBounds(spaceBetweenBlocks, 75, 250, 300);

        JScrollPane sorterScrollPane = new JScrollPane(sorterPanel);
        sorterScrollPane.setBounds(spaceBetweenBlocks * 2 + 250, 75, 250, 300);

        JScrollPane calculatorScrollPane = new JScrollPane(calculatorPanel);
        calculatorScrollPane.setBounds(spaceBetweenBlocks * 3 + 250 * 2, 75, 250, 300);

        JLabel countOfElementLabel = new JLabel("Write count of element");
        Dimension countOfElementLabelPreferredSize = countOfElementLabel.getPreferredSize();
        countOfElementLabel.setBounds(10, 10,
                (int) countOfElementLabelPreferredSize.getWidth() + 10,
                (int) countOfElementLabelPreferredSize.getHeight() + 1);
        JTextField countOfElementView = new JTextField();
        countOfElementView.setBounds(20 + (int) countOfElementLabelPreferredSize.getWidth() + 1, 10,
                50, (int) countOfElementLabelPreferredSize.getHeight() + 1);

        JLabel delayLabel = new JLabel("Write delay (ms)");
        Dimension delayLabelPreferredSize = countOfElementLabel.getPreferredSize();
        delayLabel.setBounds(
                10,
                20 + (int) countOfElementLabelPreferredSize.getHeight() + 1,
                (int) delayLabelPreferredSize.getWidth() + 10,
                (int) delayLabelPreferredSize.getHeight() + 1);
        JTextField delayView = new JTextField();
        delayView.setBounds(
                20 + (int) delayLabelPreferredSize.getWidth() + 1,
                20 + (int) countOfElementLabelPreferredSize.getHeight() + 1,
                50,
                (int) delayLabelPreferredSize.getHeight() + 1);

        JButton startButton = new JButton("Start");
        startButton.setBounds(270, 18, 150, 25);

        startButton.addActionListener(e -> {
            int elementsCount = Integer.parseInt(countOfElementView.getText());
            int delay = Integer.parseInt(delayView.getText());

            new Thread(() -> {
                try {
                    start(creatorPanel ,sorterPanel ,calculatorPanel, elementsCount, delay);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }).start();

        });

        frame.add(creatorScrollPane);
        frame.add(sorterScrollPane);
        frame.add(calculatorScrollPane);
        frame.add(countOfElementLabel);
        frame.add(countOfElementView);
        frame.add(delayLabel);
        frame.add(delayView);
        frame.add(startButton);

        frame.setVisible(true);
    }

    private static void start(JPanel creatorView, JPanel sorterView, JPanel calculatorView, int elementsCount, int delay) throws InterruptedException {
        //CORE
        Integer array[] = new Integer[elementsCount];
        AtomicInteger createdElementsCount = new AtomicInteger(0);

        CreatorThread creatorThread = new CreatorThread(array, delay, createdElementsCount, creatorView);
        creatorThread.start();

        SortThread sortThread = new SortThread(array, delay, createdElementsCount, sorterView);
        sortThread.start();

        CalculateThread calculateThread = new CalculateThread(array, delay, createdElementsCount, calculatorView);
        calculateThread.start();

        creatorThread.join();
        Thread.sleep(delay);

        sortThread.setEnd(true);
        calculateThread.setEnd(true);
    }
}
