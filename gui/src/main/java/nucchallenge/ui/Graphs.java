package nucchallenge.ui;

import nucchallenge.utils.ConfigManager;
import nucchallenge.utils.Postgres;
import nucchallenge.utils.Pulse;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author chancesnow
 */
public class Graphs extends JFrame
{
    private ConfigManager configs;
    private Postgres psql;

    private OxygenGraph oxygenGraph;

    private JPanel contentPane;
    private JLabel pulseLabel;
    private JLabel bloodPressureLabel;
    private JLabel oxygenLabel;
    private JPanel graphsPanel;

    public Graphs(ConfigManager configs)
    {
        this.configs = configs;

        this.psql = new Postgres(configs.getPsqlHost(), configs.getPsqlUser(), configs.getPsqlPasswd());
        this.psql.connectToDatabase();

        setContentPane(contentPane);

        this.setMinimumSize(new Dimension(400, 340));

        graphsPanel.setLayout(null);
        graphsPanel.setBorder(new Border()
        {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
            {}

            @Override
            public Insets getBorderInsets(Component c)
            {
                return new Insets(5, 5, 5, 5);
            }

            @Override
            public boolean isBorderOpaque()
            {
                return false;
            }
        });

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                Insets insets = graphsPanel.getInsets();
                oxygenGraph.setBounds(insets.left, insets.top,
                        graphsPanel.getSize().width - insets.left - insets.right,
                        graphsPanel.getSize().height - insets.top - insets.bottom);
            }
        });

        oxygenGraph = new OxygenGraph();
        oxygenGraph.setFont(this.getFont());
        Insets insets = graphsPanel.getInsets();
        oxygenGraph.setBounds(insets.left, insets.top,
                graphsPanel.getPreferredSize().width - insets.left - insets.right,
                graphsPanel.getPreferredSize().height - insets.top - insets.bottom);

        graphsPanel.add(oxygenGraph);

        Timer timer = new Timer(500, e -> {
            ArrayList<Pulse> pulseData = psql.selectPuleOx(Math.round((float)oxygenGraph.getMaxXValue()) * 55);

            oxygenGraph.setPulseData(pulseData);

            pulseLabel.setText("Pulse: " + pulseData.get(pulseData.size() - 1).getPulse() + "bpm");
            oxygenLabel.setText("    Oxygen: " + pulseData.get(pulseData.size() - 1).getOxygen() + "%");
        });

//        ArrayList<Pulse> pulseData = psql.selectPuleOx(Math.round((float)oxygenGraph.getMaxXValue()));
//
//        oxygenGraph.setPulseData(pulseData);

        timer.start();
    }

    /**
     * Create the GUI and show it.
     * <p/>
     * For thread safety, this method should be invoked from a separate
     * event-dispatching thread.
     */
    public static void showFrame(ConfigManager configs)
    {
        // Create and set up the window.
        Graphs graphsFrame = new Graphs(configs);

        // Exit the app when closed
        graphsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        graphsFrame.pack();
        // Ensure frame appears centered on screen.
        // (http://stackoverflow.com/a/2442614/1363247)
        graphsFrame.setLocationRelativeTo(null);

        // Display the window.
        graphsFrame.setVisible(true);
    }


}
