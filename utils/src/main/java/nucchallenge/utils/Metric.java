package nucchallenge.utils;

import java.time.LocalDateTime;

public abstract class Metric
{
    private LocalDateTime timeRecorded;

    public Metric()
    {
        this.timeRecorded = null;
    }

    public LocalDateTime getTimeRecorded()
    {
        return timeRecorded;
    }

    public void setTimeRecorded(LocalDateTime timeRecorded)
    {
        this.timeRecorded = timeRecorded;
    }
}
