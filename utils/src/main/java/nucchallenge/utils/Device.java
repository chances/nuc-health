package nucchallenge.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import gnu.io.*;
import sun.misc.Version;

public class Device /*implements SerialPortEventListener*/ {
    //this is the object that contains the opened port
    private CommPortIdentifier selectedPortIdentifier = null;
    private SerialPort serialPort = null;

    //input and output streams for sending and receiving data
    protected InputStream input = null;

    //just a boolean flag that i use for enabling
    //and disabling buttons depending on whether the program
    //is connected to a serial port or not
    private boolean connected = false;

    private int baudRate;
    private int byteSize;
    private int stopBits;
    private int parity;

    public Device() {

    }

    public void setParams(int baudRate, int byteSize,int stopBits, int parity) {
        this.baudRate = baudRate;
        this.byteSize = byteSize;
        this.stopBits = stopBits;
        this.parity = parity;
    }

    //connect to serial device
    public void connect(String selectedPort) {
        CommPort commPort = null;

        try {
           selectedPortIdentifier = CommPortIdentifier.getPortIdentifier(selectedPort);
            if(selectedPortIdentifier.isCurrentlyOwned()) {
                System.err.println("Error: Port is currently in use");
            } else {
                //2000 is timeout
                commPort = selectedPortIdentifier.open(this.getClass().getName(),2000);
                if(commPort instanceof SerialPort) {
                    serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(baudRate,byteSize,stopBits,parity);
                    input = serialPort.getInputStream();
                    connected = true;
                    System.err.println("CONNECTED:");
                    if(input != null) {
                        System.err.println("Input != null");
                    }
                } else {
                    System.err.println("Error: Only SerialPorts are handled.");
                }
            }
        } catch(NoSuchPortException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.WARNING, e.getMessage(), e);

        } catch(PortInUseException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.WARNING, e.getMessage(), e);

        } catch(UnsupportedCommOperationException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);

        } catch(IOException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public boolean isConnected() {
        return connected;
    }

}
