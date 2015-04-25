package nucchallenge.utils;

/**
 * Created by nova on 4/24/15.
 */
public class DBUpdateLoop extends Bash implements Runnable {
    private int sleepTime;

    public DBUpdateLoop(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void run() {
        try {
            updateLoop();
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public void updateLoop() throws InterruptedException {
        while (true) {
            System.out.println("Syncing Databases");
            syncDatabases("eegtest.load");
            Thread.sleep(sleepTime);
        }
    }

}
