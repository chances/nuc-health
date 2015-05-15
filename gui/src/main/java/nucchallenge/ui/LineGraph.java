package nucchallenge.ui;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class LineGraph extends Graph
{
    private ArrayList<Point.Double> linePoints;
    private Color lineColor;

    public LineGraph(String xAxisLabel, String yAxisLabel, double xScale, double yScale)
    {
        super(xAxisLabel, yAxisLabel, xScale, yScale);

        this.linePoints = new ArrayList<>();
        this.lineColor = Color.red;
    }

    public ArrayList<Point.Double> getLinePoints()
    {
        return linePoints;
    }

    public void setLinePoints(ArrayList<Point.Double> linePoints)
    {
        this.linePoints = linePoints;
    }

    public Color getLineColor()
    {
        return lineColor;
    }

    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
    }

    private void paintLines(Graphics2D g)
    {
        int width = this.getWidth(),
            height = this.getHeight();

        int xAxisOffset = getxAxisOffset(),
            yAxisOffset = getyAxisOffset();

        double xDistance = maxXValue - minXValue;
        double yDistance = maxYValue - minYValue;

        double x = linePoints.get(0).getX();
        double y = linePoints.get(0).getY();

        int oldX = Math.round((float) (yAxisOffset + ((width - yAxisOffset) * (x / xDistance))));
        int oldY = height - Math.round((float) ((height - xAxisOffset) * (y / yDistance)));

        g.setColor(this.lineColor);

        for (int i = 1; i < linePoints.size(); i++) {
            x = linePoints.get(i).getX();
            y = linePoints.get(i).getY();

            int drawX = Math.round((float) (yAxisOffset + ((width - yAxisOffset) * (x / xDistance))));
            int drawY = height - Math.round((float) ((height - xAxisOffset) * (y / yDistance)));

            g.drawLine(oldX, oldY, drawX, drawY);

            oldX = drawX;
            oldY = drawY;
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        if (linePoints.size() > 0) {
            paintLines(graphics);
        }
    }
}
