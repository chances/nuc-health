package nucchallenge.utils;

import java.util.Timer;

public class CSVLoadThread implements Runnable {
    private Timer timer;
    private CSVLoadTask csvLoadTask;

    public CSVLoadThread(CSVLoadTask csvLoadTask) {
        this.csvLoadTask = csvLoadTask;
        this.timer = new Timer(true);
    }

    @Override
    public void run() {
       loadTask();
    }

    public void loadTask() {
        timer.scheduleAtFixedRate(csvLoadTask, 0, 10000);
    }
}
