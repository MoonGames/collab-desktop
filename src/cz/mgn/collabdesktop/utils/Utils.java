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

package cz.mgn.collabdesktop.utils;

import cz.mgn.collabdesktop.menu.frames.ConnectServer;
import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class Utils {

    public static final String byteArrayToString(byte[] b, boolean binary) {
        String zeros = "00000000";
        StringBuilder string = new StringBuilder();
        string.append("[");
        for (int i = 0; i < b.length; i++) {
            int value = b[i] >= 0 ? b[i] : 256 + b[i];
            string.append("").append(value);
            if (binary) {
                String bv = Integer.toBinaryString(value);
                bv = zeros.substring(bv.length()) + bv;
                string.append(" (").append(bv).append(")");
            }
            if ((i + 1) < b.length) {
                string.append(", ");
            }
        }
        string.append("]");
        return string.toString();
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[]{
                    (byte) (value >>> 24),
                    (byte) (value >>> 16),
                    (byte) (value >>> 8),
                    (byte) value};
    }

    public static final int byteArrayToInt(byte[] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
    }

    public static final boolean byteArrayToBoolean(byte[] b) {
        return (b == null || b.length == 0) ? false : b[0] != 0x00;
    }

    public static final byte[] booleanToByteArray(boolean value) {
        return new byte[]{(byte) (value ? 0x01 : 0x00)};
    }

    public static String makeSHA256Hash(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] mdbytes = md.digest(origin.getBytes("UTF-8"));

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return origin;
    }

    public static int getLinesCount(String str) {
        String tempStr = str;
        int index;
        int lineCount = 0;

        while (tempStr.length() > 0) {
            index = tempStr.indexOf("\n");
            if (index != -1) {
                lineCount++;
                tempStr = tempStr.substring(index + 1);
            } else {
                break;
            }
        }
        return lineCount;
    }

    public static void browse(String address) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    try {
                        desktop.browse(new URI(address));
                    } catch (IOException ex) {
                        Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}
