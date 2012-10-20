/*
 * Collab desktop - Software for shared drawing via internet in real-time
 * Copyright (C) 2012 Martin Indra <aktive@seznam.cz>
 *
 * This file is part of Collab desktop.
 *
 * Collab desktop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collab desktop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.
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
 * @author Martin Indra <aktive@seznam.cz>
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
