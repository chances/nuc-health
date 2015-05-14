package nucchallenge.utils;

import java.util.Timer;

/**
 * Created by nova on 5/14/15.
 */
public class BloodPressureCuffThread implements Runnable {
    private Timer timer;
    private BloodPressureCuffTask bloodPressureCuffTask;

    public BloodPressureCuffThread(BloodPressureCuffTask bloodPressureCuffTask) {
        this.bloodPressureCuffTask = bloodPressureCuffTask;
        this.timer = new Timer(true);
    }

    @Override
    public void run() {
        loadTask();

    }

    public void loadTask() {
        timer.scheduleAtFixedRate(bloodPressureCuffTask,0,10000);

    }
}
