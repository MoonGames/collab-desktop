/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.network;

import cz.mgn.collabdesktop.utils.Utils;
import cz.mgn.collabdesktop.utils.settings.Settings;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author indy
 */
public class Client extends Thread {

    protected String serverAddress = "";
    protected Socket socket = null;
    protected InputStream in = null;
    protected OutputStream out = null;
    protected ArrayList<DataInterface> dataInterfaces = new ArrayList<DataInterface>();
    protected ConnectionInterface connectionInterface = null;
    protected static byte msStart = 111;

    public Client(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverAddress, Settings.defaultPort);
            in = socket.getInputStream();
            out = socket.getOutputStream();

            if (connectionInterface != null) {
                connectionInterface.connectionSuccessful();
            }

            while (readNext(in)) {
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            if (connectionInterface != null) {
                connectionInterface.connectionError();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            if (connectionInterface != null) {
                connectionInterface.connectionError();
            }
        } finally {
            close();
        }
    }

    protected boolean readNext(InputStream in) throws IOException {
        byte[] ms = new byte[1];
        if (in.read(ms) == -1) {
            return false;
        }
        if (ms[0] == msStart) {
            byte[] len = new byte[4];
            if (!read(len, in)) {
                return false;
            }
            int length = Utils.byteArrayToInt(len);
            byte[] data = new byte[length];
            if (!read(data, in)) {
                return false;
            }
            dataReaded(data);
        }
        return true;
    }

    protected boolean read(byte[] bytes, InputStream in) throws IOException {
        byte[] r = new byte[1];
        for (int i = 0; i < bytes.length; i++) {
            if (in.read(r) == -1) {
                return false;
            }
            bytes[i] = r[0];
        }
        return true;
    }

    protected void dataReaded(byte[] bytes) {
        if (bytes.length > 0) {
            byte command = bytes[0];
            byte[] realData = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, realData, 0, realData.length);
            for (DataInterface dataInterface : dataInterfaces) {
                dataInterface.dataReaded(realData, command);
            }
        }
    }

    public void addDataInterface(DataInterface dataInterface) {
        dataInterfaces.add(dataInterface);
    }

    public void removeDataInterface(DataInterface dataInterface) {
        dataInterfaces.remove(dataInterface);
    }

    public void setConnectionInterface(ConnectionInterface connectionInterface) {
        this.connectionInterface = connectionInterface;
    }

    public void send(byte[] bytes) {
        synchronized (out) {
            try {
                if (!socket.isOutputShutdown()) {
                    out.write(msStart);
                    byte[] len = Utils.intToByteArray(bytes.length);
                    out.write(len);
                    out.write(bytes);
                    out.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                if (connectionInterface != null) {
                    connectionInterface.connectionError();
                }
            }
        }
    }

    public boolean isConnectionValid() {
        if (socket != null) {
            return socket.isConnected();
        }
        return false;
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
            if (connectionInterface != null) {
                connectionInterface.connectionClosed();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
