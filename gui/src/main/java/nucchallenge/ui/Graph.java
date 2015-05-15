package nucchallenge.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

public abstract class Graph extends JPanel
{
    int MARGIN = 3;
    int SCALE_MARKER_SIZE = 4;
    //private static Font font = new Font("Helvetica", Font.PLAIN, 10);
    private static DecimalFormat SCALE_LABEL_FORMAT = new DecimalFormat("#.##");

    private String xAxisLabel;
    private String yAxisLabel;
    protected boolean invertXAxisLabels;
    protected boolean invertYAxisLabels;

    protected double xScale;
    protected double yScale;

    protected double minXValue;
    protected double maxXValue;
    protected double minYValue;
    protected double maxYValue;

    private boolean showingScaleMarkers;
    private boolean showingScaleLabels;

    public Graph()
    {
        this(null, null, 1, 1);
    }

    public Graph(String xAxisLabel, String yAxisLabel, double xScale, double yScale)
    {
        super(true);

        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.invertXAxisLabels = false;
        this.invertYAxisLabels = false;

        this.xScale = xScale;
        this.yScale = yScale;

        this.minXValue = 0;
        this.maxXValue = 10;
        this.minYValue = 0;
        this.maxYValue = 10;

        this.showingScaleMarkers = true;
        this.showingScaleLabels = false;
    }

    public String getxAxisLabel()
    {
        return xAxisLabel;
    }

    public void setxAxisLabel(String xAxisLabel)
    {
        this.xAxisLabel = xAxisLabel;
    }

    public String getyAxisLabel()
    {
        return yAxisLabel;
    }

    public void setyAxisLabel(String yAxisLabel)
    {
        this.yAxisLabel = yAxisLabel;
    }

    protected int getxAxisOffset()
    {
        this.getGraphics().setFont(this.getFont());

        int fontHeight = this.getGraphics().getFontMetrics().getHeight();
        int offset = 0;

        if (xAxisLabel != null) offset += fontHeight + MARGIN;
        if (showingScaleLabels) {
            offset += fontHeight;
        }
        if (showingScaleMarkers) {
            offset += SCALE_MARKER_SIZE + MARGIN;
        }

        return offset;
    }

    protected int getyAxisOffset()
    {
        this.getGraphics().setFont(this.getFont());

        int fontHeight = this.getGraphics().getFontMetrics().getHeight();
        int offset = 0;

        if (yAxisLabel != null) offset += fontHeight + MARGIN;
        if (showingScaleLabels) {
            offset += fontHeight + MARGIN * 2;
        }
        if (showingScaleMarkers) {
            offset += SCALE_MARKER_SIZE + MARGIN;
        }

        return offset;
    }

    public double getMinXValue()
    {
        return minXValue;
    }

    public double getMaxXValue()
    {
        return maxXValue;
    }

    public double getMinYValue()
    {
        return minYValue;
    }

    public double getMaxYValue()
    {
        return maxYValue;
    }

    public double getxScale()
    {
        return xScale;
    }

    public void setxScale(double xScale)
    {
        this.xScale = xScale;
    }

    public double getyScale()
    {
        return yScale;
    }

    public void setyScale(double yScale)
    {
        this.yScale = yScale;
    }

    public boolean isShowingScaleMarkers()
    {
        return showingScaleMarkers;
    }

    public void setShowingScaleMarkers(boolean showingScaleMarkers)
    {
        this.showingScaleMarkers = showingScaleMarkers;
    }

    public boolean isShowingScaleLabels()
    {
        return showingScaleLabels;
    }

    public void setShowingScaleLabels(boolean showingScaleLabels)
    {
        this.showingScaleLabels = showingScaleLabels;
    }

    public void updateGraph()
    {
        this.paintImmediately(this.getBounds());
    }

    private void paintGraphContainer(Graphics2D g)
    {
        int width = this.getWidth(),
            height = this.getHeight();

        int xAxisOffset = getxAxisOffset(),
            yAxisOffset = getyAxisOffset();

        double xDistance = maxXValue - minXValue;
        double yDistance = maxYValue - minYValue;

        AffineTransform originalTransform = g.getTransform();

        int stringWidth;

        g.setFont(this.getFont());

        int fontHeight = g.getFontMetrics().getHeight();

        // Draw axis labels
        g.setColor(this.getForeground());

        if (xAxisLabel != null) {
            stringWidth = g.getFontMetrics().stringWidth(xAxisLabel);
            g.drawString(xAxisLabel,
                    Math.round((float)((width / 2.0) - (stringWidth / 2.0))), height - MARGIN);
        }

        if (yAxisLabel != null) {
            stringWidth = g.getFontMetrics().stringWidth(yAxisLabel);
            g.rotate(Math.toRadians(-90));
            g.drawString(yAxisLabel, 0 - Math.round((float)((height / 2.0) + (stringWidth / 2.0))), fontHeight);
            g.setTransform(originalTransform);
        }

        g.setColor(Color.gray);

        // Draw scale labels
        if (showingScaleLabels) {
            // x-axis
            for (double x = minXValue + xScale; x < maxXValue; x += xScale) {
                String label = null;
                if (invertXAxisLabels) {
                    label = SCALE_LABEL_FORMAT.format(maxXValue - x);
                } else {
                    label = SCALE_LABEL_FORMAT.format(x);
                }
                stringWidth = g.getFontMetrics().stringWidth(label);
                int labelX = Math.round((float)((yAxisOffset + ((width - yAxisOffset) * (x / xDistance))) - (stringWidth / 2.0)));
                int labelY = height - fontHeight - MARGIN;
                g.drawString(label, labelX, labelY);
            }

            g.rotate(Math.toRadians(-90));

            // y-axis
            for (double y = minYValue + yScale; y < maxYValue; y += yScale) {
                String label = null;
                if (!invertYAxisLabels) {
                    label = SCALE_LABEL_FORMAT.format(maxYValue - y);
                } else {
                    label = SCALE_LABEL_FORMAT.format(y);
                }
                stringWidth = g.getFontMetrics().stringWidth(label);
                int labelX = 0 - Math.round((float)((height / 2.0) + (stringWidth / 2.0)));

                g.drawString(label,
                        0 - Math.round((float)((((height - xAxisOffset) * (y / yDistance))) + (stringWidth / 2.0))),
                        fontHeight * 2 + MARGIN);
            }

            g.setTransform(originalTransform);
        }

        // Draw scale markers
        g.setColor(Color.gray);

        if (showingScaleMarkers) {
            // x-axis
            for (double x = minXValue + xScale; x <= maxXValue; x += xScale) {
                int lineX = Math.round((float) (yAxisOffset + ((width - yAxisOffset) * (x / xDistance))));
                int lineY = height - xAxisOffset;
                g.drawLine(lineX, lineY, lineX, lineY + SCALE_MARKER_SIZE);
            }

            // y-axis
            for (double y = minYValue + yScale; y < maxYValue; y += yScale) {
                int lineX = yAxisOffset - SCALE_MARKER_SIZE;
                int lineY = Math.round((float) ((height - xAxisOffset) * (y / yDistance)));
                g.drawLine(lineX, lineY, lineX + SCALE_MARKER_SIZE, lineY);
            }
        }

        // Draw x-axis
        g.drawLine(yAxisOffset, height - xAxisOffset, width, height - xAxisOffset);

        // Draw y-axis
        g.drawLine(yAxisOffset, 0, yAxisOffset, height - xAxisOffset);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        // Hint at text antialiasing
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHints(rh);

        // Hint at general antialiasing
        rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHints(rh);

        paintGraphContainer(graphics);
    }
}
