package nucchallenge.ui;

import nucchallenge.utils.Pulse;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OxygenGraph extends LineGraph
{
    public OxygenGraph()
    {
        super("Seconds Ago", "Oxygen Level (SpO2 %)", 1.0, 10.0);

        this.setShowingScaleLabels(true);

        this.invertXAxisLabels = true;

        this.maxXValue = 16;
        this.maxYValue = 100;
    }

    public void setPulseData(ArrayList<Pulse> pulseData)
    {
        getLinePoints().clear();

        long currentTime = System.currentTimeMillis();

        double threshold = 0.25;
        double diff = 1.0;

        for (Pulse dataPoint: pulseData) {
            Instant timeRecordedInstant = dataPoint.getTimeRecorded().atZone(ZoneId.systemDefault()).toInstant();
            long timeRecorded = timeRecordedInstant.toEpochMilli();
            double timeAgo = (currentTime / 1000.0) - (timeRecorded / 1000.0);

            double xTimeAgo = (0 - timeAgo) + maxXValue;

            if (xTimeAgo >= minXValue && diff >= threshold) {
                diff = 0.0;

                getLinePoints().add(new Point.Double(xTimeAgo, dataPoint.getOxygen()));
            }

            diff = diff + timeAgo;
        }

        this.updateGraph();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);


    }
}
