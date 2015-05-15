package nucchallenge.utils;

import java.time.LocalDateTime;

public class BloodPressure extends Metric
{
    private int systolic, diastolic, pulse;

    public BloodPressure(int systolic, int diastolic, int pulse)
    {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }

    public BloodPressure(LocalDateTime timeRecorded, int systalic, int diastolic, int pulse)
    {
        this(systalic, diastolic, pulse);

        this.setTimeRecorded(timeRecorded);
    }

    public int getSystolic()
    {
        return systolic;
    }

    public int getDiastolic()
    {
        return diastolic;
    }

    public int getPulse()
    {
        return pulse;
    }
}
