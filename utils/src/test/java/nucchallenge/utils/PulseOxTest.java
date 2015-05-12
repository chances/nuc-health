package nucchallenge.utils;

import gnu.io.SerialPort;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PulseOxTest {
    private PulseOx pulseOx;

    @Before
    public void setUp() {
        pulseOx = new PulseOx();
        pulseOx.setParams(19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_ODD);

        pulseOx.connect("/dev/ttyUSB0");
    }

    @Test
    public void TestRead() {
        while (pulseOx.isConnected()) {
            try {
                pulseOx.read();
            } catch(IOException e) {
                System.err.println("Error: Read IOEXCEPTIOn error");
            }

        }
    }

}
