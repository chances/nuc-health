package nucchallenge.utils;

import java.time.LocalDateTime;

public class Pulse extends Metric
{
    private int oxygen, pulse;

    public Pulse(int oxygen, int pulse)
    {
        this.oxygen = oxygen;
        this.pulse = pulse;
    }

    public Pulse(LocalDateTime timeRecorded, int oxygen, int pulse)
    {
        this(oxygen, pulse);

        this.setTimeRecorded(timeRecorded);
    }

    public int getOxygen()
    {
        return oxygen;
    }

    public int getPulse()
    {
        return pulse;
    }
}
