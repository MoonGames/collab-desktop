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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.network.commands;

import cz.mgn.collabdesktop.utils.Utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author indy
 */
public class CommandGenerator {

    public static final byte OUT_COMMAND_PAINT = 1;
    public static final byte OUT_COMMAND_REMOVE = 2;
    public static final byte OUT_COMMAND_ADD_LAYER = 3;
    public static final byte OUT_COMMAND_REMOVE_LAYER = 4;
    public static final byte OUT_COMMAND_SET_LAYER_LOCATION = 5;
    public static final byte OUT_COMMAND_SET_LAYER_NAME = 6;
    public static final byte OUT_COMMAND_GET_ROOMS_LIST = 7;
    public static final byte OUT_COMMAND_CREATE_ROOM = 8;
    public static final byte OUT_COMMAND_JOIN_ROOM = 9;
    public static final byte OUT_COMMAND_CHAT = 10;
    public static final byte OUT_COMMAND_SET_NICK = 11;
    public static final byte OUT_COMMAND_DISCONNECT_FROM_ROOM = 12;
    public static final byte OUT_COMMAND_MAKE_HTTP_IMAGE = 13;

    protected static byte[] splitDataAndCommand(byte[] data, byte command) {
        byte[] result = new byte[1 + data.length];
        result[0] = command;
        System.arraycopy(data, 0, result, 1, data.length);
        return result;
    }

    protected static byte[] splitByteArrays(ArrayList<byte[]> arrays) {
        int len = 0;
        for (byte[] array : arrays) {
            len += array.length;
        }
        byte[] data = new byte[len];
        int index = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, data, index, array.length);
            index += array.length;
        }
        return data;
    }

    protected static byte[] generatePaintRemoveCommandData(ImageData image) {
        try {
            byte[] identificatorData = Utils.intToByteArray(image.getIdentificator());
            byte[] layerIDData = Utils.intToByteArray(image.getLayerID());
            byte[] xData = Utils.intToByteArray(image.getX());
            byte[] yData = Utils.intToByteArray(image.getY());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image.getImage(), "PNG", baos);
            byte[] imageData = baos.toByteArray();

            byte[] data = new byte[identificatorData.length + layerIDData.length + xData.length + yData.length + imageData.length];
            int index = 0;
            for (int i = 0; i < identificatorData.length; i++) {
                data[index++] = identificatorData[i];
            }
            for (int i = 0; i < layerIDData.length; i++) {
                data[index++] = layerIDData[i];
            }
            for (int i = 0; i < xData.length; i++) {
                data[index++] = xData[i];
            }
            for (int i = 0; i < yData.length; i++) {
                data[index++] = yData[i];
            }
            for (int i = 0; i < imageData.length; i++) {
                data[index++] = imageData[i];
            }

            return data;
        } catch (IOException ex) {
            Logger.getLogger(CommandGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] generatePaintCommand(ImageData image) {
        return splitDataAndCommand(generatePaintRemoveCommandData(image), OUT_COMMAND_PAINT);
    }

    public static byte[] generateRemoveCommand(ImageData image) {
        return splitDataAndCommand(generatePaintRemoveCommandData(image), OUT_COMMAND_REMOVE);
    }

    public static byte[] generateSetNickCommand(String nick) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        try {
            byte[] nickData = nick.getBytes("UTF-8");
            data.add(nickData);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommandGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_SET_NICK);
    }

    public static byte[] generateDisconnectFromRoomCommand() {
        return splitDataAndCommand(new byte[]{}, OUT_COMMAND_DISCONNECT_FROM_ROOM);
    }

    public static byte[] generateMakeHTTPImageCommand() {
        return splitDataAndCommand(new byte[]{}, OUT_COMMAND_MAKE_HTTP_IMAGE);
    }

    public static byte[] generateAddLayerCommand(int layerLocation, int commandIdentificator, String layerName) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        data.add(Utils.intToByteArray(layerLocation));
        data.add(Utils.intToByteArray(commandIdentificator));
        try {
            data.add(layerName.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommandGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_ADD_LAYER);
    }

    public static byte[] generateRemoveLayerCommand(int layerID) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        data.add(Utils.intToByteArray(layerID));
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_REMOVE_LAYER);
    }

    public static byte[] generateSetLayerLocationCommand(int layerID, int newLocation) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        data.add(Utils.intToByteArray(layerID));
        data.add(Utils.intToByteArray(newLocation));
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_SET_LAYER_LOCATION);
    }

    public static byte[] generateSetLayerNameCommand(int layerID, String name) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        data.add(Utils.intToByteArray(layerID));
        try {
            data.add(name.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommandGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_SET_LAYER_NAME);
    }

    public static byte[] generateGetRoomsListCommand() {
        return splitDataAndCommand(new byte[]{}, OUT_COMMAND_GET_ROOMS_LIST);
    }

    public static byte[] generateCreateRoomCommand(int width, int height, String password, String name) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        data.add(Utils.intToByteArray(width));
        data.add(Utils.intToByteArray(height));
        try {
            if (!password.isEmpty()) {
                password = Utils.makeSHA256Hash(password);
            }
            byte[] passwordData = password.getBytes("UTF-8");
            data.add(Utils.intToByteArray(passwordData.length));
            data.add(passwordData);
            data.add(name.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommandGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_CREATE_ROOM);
    }

    public static byte[] generateJoinRoomCommand(int roomID, String password) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        data.add(Utils.intToByteArray(roomID));
        try {
            if (!password.isEmpty()) {
                password = Utils.makeSHA256Hash(password);
            }
            data.add(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommandGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_JOIN_ROOM);
    }

    public static byte[] generateChatCommand(String message) {
        ArrayList<byte[]> data = new ArrayList<byte[]>();
        try {
            data.add(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommandGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return splitDataAndCommand(splitByteArrays(data), OUT_COMMAND_CHAT);
    }
}
